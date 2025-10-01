package com.shifterwebapp.shifter.external;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.shifterwebapp.shifter.exception.GoogleCalendarException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class GoogleCalendarService {

    private static final String APPLICATION_NAME = "Shifter App";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Getter
    private final String expertCalendarId = System.getProperty("GOOGLE_EXPERT_CALENDAR_ID");

    private final Calendar calendarService;

    public GoogleCalendarService() {
        this.calendarService = initService();
    }

    /**
     * Initialize Google Calendar service using the service-account.json
     */
    private Calendar initService() {
        try {
            InputStream serviceAccountStream = getClass().getResourceAsStream("/service-account.json");
            if (serviceAccountStream == null) {
                throw new GoogleCalendarException("Service account JSON not found in classpath", null, false);
            }

            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream)
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/calendar"));

            return new Calendar.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    new HttpCredentialsAdapter(credentials)
            ).setApplicationName(APPLICATION_NAME).build();

        } catch (Exception e) {
            throw new GoogleCalendarException("Failed to initialize Google Calendar service", e, false);
        }
    }

    /* ==================== FREE/BUSY & SLOTS ==================== */

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
        if (start.getDayOfWeek() == DayOfWeek.SATURDAY || start.getDayOfWeek() == DayOfWeek.SUNDAY
                || end.getDayOfWeek() == DayOfWeek.SATURDAY || end.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }

        var busyList = freeBusy.getCalendars().get(expertCalendarId).getBusy();
        for (var interval : busyList) {
            Instant busyStart = Instant.ofEpochMilli(interval.getStart().getValue());
            Instant busyEnd = Instant.ofEpochMilli(interval.getEnd().getValue());
            if (!(busyEnd.isBefore(start.toInstant()) || busyStart.isAfter(end.toInstant()))) {
                return false;
            }
        }
        return true;
    }

    public FreeBusyResponse queryFreeBusy(Instant start, Instant end) {
        try {
            FreeBusyRequest fbRequest = new FreeBusyRequest()
                    .setTimeMin(new DateTime(Date.from(start)))
                    .setTimeMax(new DateTime(Date.from(end)))
                    .setTimeZone("Europe/Skopje")
                    .setItems(Collections.singletonList(new FreeBusyRequestItem().setId(expertCalendarId)));

            return calendarService.freebusy().query(fbRequest).execute();
        } catch (Exception e) {
            throw new GoogleCalendarException("Failed to query free/busy times", e, false);
        }
    }

    public String createEvent(String title, Instant start, Instant end) {
        try {
            Event event = new Event()
                    .setSummary(title)
                    .setStart(new EventDateTime().setDateTime(new DateTime(start.toEpochMilli())).setTimeZone("Europe/Skopje"))
                    .setEnd(new EventDateTime().setDateTime(new DateTime(end.toEpochMilli())).setTimeZone("Europe/Skopje"));

            Event createdEvent = calendarService.events().insert(expertCalendarId, event).execute();
            return createdEvent.getId();
        } catch (Exception e) {
            throw new GoogleCalendarException("Failed to create event", e, false);
        }
    }

    public void deleteEvent(String eventId) {
        try {
            calendarService.events().delete(expertCalendarId, eventId).execute();
        } catch (Exception e) {
            throw new GoogleCalendarException("Failed to delete event with ID: " + eventId, e, false);
        }
    }

    /* ==================== TIME INTERVAL CLASS ==================== */

    public static class TimeInterval {
        private final Instant start;
        private final Instant end;

        public TimeInterval(Instant start, Instant end) {
            this.start = start;
            this.end = end;
        }

        public boolean overlaps(Instant otherStart, Instant otherEnd) {
            return !start.isAfter(otherEnd) && !end.isBefore(otherStart);
        }
    }
}
