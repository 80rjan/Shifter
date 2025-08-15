package com.shifterwebapp.shifter.meeting;
import org.springframework.stereotype.Component;
import java.time.*;

@Component
public class MeetingUtils {

    public static final ZoneId EXPERT_ZONE = ZoneId.of("Europe/Skopje");

    public ZonedDateTime[] getExpertStartEnd(String date, String time, String userTimeZone, int durationMinutes) {
        ZoneId userZone = ZoneId.of(userTimeZone);

        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        ZonedDateTime startUser = ZonedDateTime.of(localDate, localTime, userZone);

        ZonedDateTime startExpert = startUser.withZoneSameInstant(EXPERT_ZONE);
        ZonedDateTime endExpert = startExpert.plusMinutes(durationMinutes);

        return new ZonedDateTime[]{startExpert, endExpert};
    }

    public ZoneId parseZone(String zone) {
        try {
            return ZoneId.of(zone);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid time zone: " + zone, e);
        }
    }

    public ZonedDateTime calculateScheduleStart(ZonedDateTime now) {
        DayOfWeek dow = now.getDayOfWeek();
        int daysToAdd = switch (dow) {
            case THURSDAY, FRIDAY -> 4;
            case SATURDAY -> 3;
            default -> 2;
        };
        return now.withHour(8).withMinute(0).withSecond(0).withNano(0).plusDays(daysToAdd);
    }

    public ZonedDateTime calculateScheduleEnd(ZonedDateTime start) {
        return start.withHour(16).withMinute(30).withSecond(0).withNano(0).plusDays(9);
    }
}
