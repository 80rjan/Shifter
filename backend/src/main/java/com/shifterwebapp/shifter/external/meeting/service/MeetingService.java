package com.shifterwebapp.shifter.external.meeting.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.services.calendar.model.FreeBusyResponse;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.exception.AccessDeniedException;
import com.shifterwebapp.shifter.exception.TimeSlotUnavailableException;
import com.shifterwebapp.shifter.external.email.EmailService;
import com.shifterwebapp.shifter.external.ZoomService;
import com.shifterwebapp.shifter.external.google.GoogleCalendarService;
import com.shifterwebapp.shifter.external.meeting.MeetingUtils;
import com.shifterwebapp.shifter.external.meeting.UserMeetingInfoRequest;
import com.shifterwebapp.shifter.external.meeting.ZoomMeetingRequest;
import com.shifterwebapp.shifter.scheduledemail.MeetingEmailReminder;
import com.shifterwebapp.shifter.scheduledemail.MeetingEmailReminderService;
import com.shifterwebapp.shifter.account.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MeetingService implements ImplMeetingService {

    private final GoogleCalendarService googleCalendarService;
    private final MeetingEmailReminderService meetingEmailReminderService;
    private final UserService userService;
    private final ZoomService zoomService;
    private final EmailService emailService;
    private final MeetingUtils meetingUtils;


    @Override
    public Map<String, List<String>> getExpertFreeTimeSlots(String userTimeZone) {
        ZoneId expertZone = ZoneId.of("Europe/Skopje");
        ZoneId userZone = meetingUtils.parseZone(userTimeZone);

        ZonedDateTime nowExpert = ZonedDateTime.now(expertZone);
        ZonedDateTime scheduleStart = meetingUtils.calculateScheduleStart(nowExpert);
        ZonedDateTime scheduleEnd = meetingUtils.calculateScheduleEnd(scheduleStart);

        FreeBusyResponse freeBusyResponse = googleCalendarService
                .queryFreeBusy(scheduleStart.toInstant(), scheduleEnd.toInstant());

        String calendarId = googleCalendarService.getExpertCalendarId();
        if (freeBusyResponse == null
                || freeBusyResponse.getCalendars() == null
                || freeBusyResponse.getCalendars().get(calendarId) == null
                || freeBusyResponse.getCalendars().get(calendarId).getBusy() == null) {
            throw new IllegalStateException("Invalid FreeBusyResponse from Google API");
        }

        List<GoogleCalendarService.TimeInterval> busyIntervals = freeBusyResponse
                .getCalendars()
                .get(calendarId)
                .getBusy()
                .stream()
                .map(i -> new GoogleCalendarService.TimeInterval(
                        Instant.ofEpochMilli(i.getStart().getValue()),
                        Instant.ofEpochMilli(i.getEnd().getValue())))
                .toList();

        return googleCalendarService.computeFreeSlotsByDate(
                busyIntervals, scheduleStart, scheduleEnd, 30, userZone
        );
    }

    @Override
    public void scheduleMeeting(String userDate, String userTime, String userTimeZone, UserMeetingInfoRequest userMeetingInfoRequest) {
        String userEmail = userMeetingInfoRequest.getEmail();

        if (userService.getUserHasUsedFreeConsultation(userEmail)) {
            throw new AccessDeniedException("User has already used free consultation");
        }

        User user = userService.getUserEntityByEmail(userEmail);

        ZonedDateTime[] expertTimes = meetingUtils.getExpertStartEnd(userDate, userTime, userTimeZone, 30);
        ZonedDateTime startExpert = expertTimes[0];
        ZonedDateTime endExpert = expertTimes[1];

        boolean calendarCreated = false;
        String calendarEventId = null;
        String meetingLink = null;
        String meetingId = null;

        try {
            FreeBusyResponse freeBusy = googleCalendarService.queryFreeBusy(
                    startExpert.toInstant(),
                    endExpert.toInstant()
            );
            if (!googleCalendarService.isSlotFree(freeBusy, startExpert, endExpert)) {
                throw new TimeSlotUnavailableException("Slot is no longer available");
            }

            calendarEventId = googleCalendarService.createEvent(
                    "Free Consultation",
                    startExpert.toInstant(),
                    endExpert.toInstant()
            );
            calendarCreated = true;

            String zoomStartTime = startExpert
                    .withZoneSameInstant(ZoneId.of("UTC"))
                    .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            JsonNode jsonNode = zoomService.createMeeting(ZoomMeetingRequest.builder()
                    .topic("Free Consultation Session")
                    .startTime(zoomStartTime)
                    .duration(30)
                    .timezone(MeetingUtils.EXPERT_ZONE.toString())
                    .agenda("""
                        This session is designed to understand your current challenges, goals, and preferences.
                        During this session, our expert will provide valuable insights based on your situation.
                        After the session, you will receive a personalized program recommendation tailored to your needs.
                        """)
                            .type(2)    // Scheduled meeting
                    .build());
            meetingLink = jsonNode.get("join_url").asText();
            meetingId = jsonNode.get("id").asText();

            emailService.sendFreeConsultationConfirmation(userEmail, userDate, userTime, meetingLink);
            emailService.sendExpertMeetingInformation(userMeetingInfoRequest, startExpert.toLocalTime().toString(), startExpert.toLocalDate().toString(), userTimeZone, meetingLink);

            LocalDateTime scheduledDayBefore = startExpert
                    .minusDays(1) // 1 day before
                    .withHour(8)  // 8 AM
                    .withMinute(0)
                    .withSecond(0)
                    .toLocalDateTime();
            meetingEmailReminderService.saveScheduledEmail(
                    MeetingEmailReminder.builder()
                            .user(user)
                            .meetingAt(LocalDateTime.parse(userDate + "T" + userTime))
                            .scheduledAt(scheduledDayBefore)
                            .meetingLink(meetingLink)
                            .sent(false)
                            .build()
            );

            LocalDateTime scheduledOnDay = startExpert.minusHours(2).toLocalDateTime(); // 2 hours before
            meetingEmailReminderService.saveScheduledEmail(
                    MeetingEmailReminder.builder()
                            .user(user)
                            .meetingAt(LocalDateTime.parse(userDate + "T" + userTime))
                            .scheduledAt(scheduledOnDay)
                            .meetingLink(meetingLink)
                            .sent(false)
                            .build()
            );

            userService.markUserAsUsedFreeConsultation(userEmail);

        } catch (Exception e) {
            // Rollback calendar if Zoom or email fails
            if (calendarCreated && calendarEventId != null) {
                try {
                    googleCalendarService.deleteEvent(calendarEventId);
                } catch (Exception ex) {
                    // log failure to delete calendar event
                    System.err.println("Failed to delete calendar event: " + ex.getMessage());
                }
            }

            if (meetingId != null) {
                try {
                    zoomService.deleteMeeting(meetingId);
                } catch (Exception ex) {
                    // log failure to delete Zoom meeting
                    System.err.println("Failed to delete Zoom meeting: " + ex.getMessage());
                }
            }

            // Optionally log or rethrow the exception
            throw new RuntimeException("Failed to schedule meeting: " + e.getMessage(), e);
        }
    }


}
