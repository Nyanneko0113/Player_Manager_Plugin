package org.nyanneko0113.player_manager.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class TimeUtil {

    public static Date toDate(String[] args) {
        LocalDateTime dates = LocalDateTime.now();
        int year = dates.getYear(),
                month = dates.getMonth().getValue(),
                day = dates.getDayOfMonth(),
                hour = dates.getHour(),
                min = dates.getMinute();

        if (args.length >= 8) year = Integer.parseInt(args[7]);
        if (args.length >= 7) month = Integer.parseInt(args[6]);
        if (args.length >= 6) day = Integer.parseInt(args[5]);
        if (args.length >= 5) hour = Integer.parseInt(args[4]);
        if (args.length >= 4) min = Integer.parseInt(args[3]);
        int sec = Integer.parseInt(args[2]);

        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime local_date = LocalDateTime.of(year, month, day, hour, min, sec);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(local_date, zone);
        Instant instant = zonedDateTime.toInstant();

        return Date.from(instant);
    }
}
