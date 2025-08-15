package com.shifterwebapp.shifter.external;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.MemoryDataStoreFactory;

import java.io.StringReader;
import java.util.Collections;

import com.shifterwebapp.shifter.exception.GoogleCalendarException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "Shifter App";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final String clientId = System.getProperty("GOOGLE_CLIENT_ID");
    private final String clientSecret = System.getProperty("GOOGLE_CLIENT_SECRET");

    @Getter
    private final String expertCalendarId = System.getProperty("GOOGLE_EXPERT_CALENDAR_ID");

    @Getter
    @Setter
    private String refreshToken = System.getProperty("GOOGLE_REFRESH_TOKEN");


    /**
     * Builds Google Credential object with tokens.
     * Automatically refreshes access token if expired.
     */
    private Credential getCredential() throws Exception {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var jsonFactory = GsonFactory.getDefaultInstance();

        // Build client secrets
        String clientSecretsJson = String.format("""
        {
          "installed": {
            "client_id": "%s",
            "client_secret": "%s",
            "auth_uri": "https://accounts.google.com/o/oauth2/auth",
            "token_uri": "https://oauth2.googleapis.com/token"
          }
        }
        """, clientId, clientSecret);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new StringReader(clientSecretsJson));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport,
                jsonFactory,
                clientSecrets,
                Collections.singleton("https://www.googleapis.com/auth/calendar.events") // only this scope
        )
                .setDataStoreFactory(new MemoryDataStoreFactory())
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();

        // Create credential using refresh token
        var tokenResponse = new com.google.api.client.auth.oauth2.TokenResponse()
                .setRefreshToken(refreshToken);

        Credential credential = flow.createAndStoreCredential(tokenResponse, "user-id");

        // Refresh if needed
        if (credential.getExpiresInSeconds() == null || credential.getExpiresInSeconds() <= 60) {
            if (!credential.refreshToken()) {
                throw new RuntimeException("Failed to refresh Google access token");
            }
        }

        return credential;
    }



    public Map<String, List<String>> computeFreeSlotsByDate(
            List<TimeInterval> busySlots,
            ZonedDateTime scheduleStart,
            ZonedDateTime scheduleEnd,
            int slotDurationMinutes,
            ZoneId userZone) {

        Map<String, List<String>> freeSlotsByDate = new LinkedHashMap<>();

        ZonedDateTime cursorDay = scheduleStart.truncatedTo(ChronoUnit.DAYS);

        while (cursorDay.isBefore(scheduleEnd)) {
            DayOfWeek dayOfWeek = cursorDay.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                ZonedDateTime workdayStart = cursorDay.withHour(8).withMinute(0);
                ZonedDateTime workdayEnd = cursorDay.withHour(16).withMinute(30);

                ZonedDateTime cursorSlot = workdayStart;

                while (cursorSlot.plusMinutes(slotDurationMinutes).compareTo(workdayEnd) <= 0) {
                    ZonedDateTime slotEnd = cursorSlot.plusMinutes(slotDurationMinutes);

                    Instant slotStartInstant = cursorSlot.toInstant();
                    Instant slotEndInstant = slotEnd.toInstant();

                    boolean overlaps = busySlots.stream()
                            .anyMatch(busy -> busy.overlaps(slotStartInstant, slotEndInstant));

                    if (!overlaps && cursorSlot.isAfter(scheduleStart)) {
                        ZonedDateTime userTimeSlot = cursorSlot.withZoneSameInstant(userZone);
                        String date = userTimeSlot.toLocalDate().toString();
                        String time = userTimeSlot.toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString();

                        freeSlotsByDate.computeIfAbsent(date, k -> new ArrayList<>()).add(time);
                    }

                    cursorSlot = cursorSlot.plusMinutes(slotDurationMinutes);
                }
            }
            cursorDay = cursorDay.plusDays(1);
        }

        return freeSlotsByDate;
    }


    public boolean isSlotFree(FreeBusyResponse freeBusy, ZonedDateTime start, ZonedDateTime end) {
        DayOfWeek startDay = start.getDayOfWeek();
        DayOfWeek endDay = end.getDayOfWeek();
        if (startDay == DayOfWeek.SATURDAY || startDay == DayOfWeek.SUNDAY
                || endDay == DayOfWeek.SATURDAY || endDay == DayOfWeek.SUNDAY) {
            return false;
        }

        var busyList = freeBusy.getCalendars().get(getExpertCalendarId()).getBusy();
        for (var interval : busyList) {
            Instant busyStart = Instant.ofEpochMilli(interval.getStart().getValue());
            Instant busyEnd = Instant.ofEpochMilli(interval.getEnd().getValue());
            TimeInterval busyInterval = new TimeInterval(busyStart, busyEnd);
            if (busyInterval.overlaps(start.toInstant(), end.toInstant())) {
                return false;
            }
        }
        return true;
    }

    public FreeBusyResponse queryFreeBusy(Instant start, Instant end) {
        try {
            Calendar service = new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    getCredential()
            )
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            FreeBusyRequest fbRequest = new FreeBusyRequest();
            fbRequest.setTimeMin(new DateTime(Date.from(start)));
            fbRequest.setTimeMax(new DateTime(Date.from(end)));
            fbRequest.setTimeZone("Europe/Skopje");
            fbRequest.setItems(Collections.singletonList(
                    new FreeBusyRequestItem().setId(expertCalendarId)
            ));

            return service.freebusy().query(fbRequest).execute();

        } catch (Exception e) {
            throw new GoogleCalendarException("Failed to query free/busy times", e);
        }
    }

    public String createEvent(String title, Instant start, Instant end) {
        try {
            Calendar service = new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    getCredential()
            )
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            Event event = new Event()
                    .setSummary(title)
                    .setStart(new EventDateTime()
                            .setDateTime(new DateTime(start.toEpochMilli()))
                            .setTimeZone("Europe/Skopje"))
                    .setEnd(new EventDateTime()
                            .setDateTime(new DateTime(end.toEpochMilli()))
                            .setTimeZone("Europe/Skopje"));

            Event createdEvent = service.events().insert(getExpertCalendarId(), event).execute();

            return createdEvent.getId();
        } catch (Exception e) {
            throw new GoogleCalendarException("Failed to create event", e);
        }
    }

    public void deleteEvent(String eventId) {
        try {
            Calendar service = new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    getCredential()
            )
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            service.events().delete(getExpertCalendarId(), eventId).execute();
        } catch (Exception e) {
            throw new GoogleCalendarException("Failed to delete event with ID: " + eventId, e);
        }
    }



    /**
     * Helper class for time intervals
     */
    public static class TimeInterval {
        private final Instant start;
        private final Instant end;

        public TimeInterval(Instant start, Instant end) {
            this.start = start;
            this.end = end;
        }

        public boolean overlaps(Instant otherStart, Instant otherEnd) {
            return !start.isAfter(otherEnd) && !end.isBefore(otherStart); // This checks if the intervals overlap. If 12.00-12.30 is taken, 11.30-12.00 and 12.30-13.00 isnt free
        }
    }
}
