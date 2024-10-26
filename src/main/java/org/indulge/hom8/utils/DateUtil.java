package org.indulge.hom8.utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public interface DateUtil {

    String DATE_TIME_FORMAT = "MM-dd-yyyy hh:mm:ss a";
    String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";


    static String formatLocalDateTimeToString(LocalDateTime localDateTime){
        if (localDateTime == null) return null;
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    static LocalDateTime timestampToLocalDateTime(long timestamp){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault());
    }

    static Boolean moreThanOneDateFallsWithinSixMonths(List<LocalDateTime> dateTimeList){
        return IntStream.range(0, dateTimeList.size() - 1)
                .anyMatch(i -> IntStream.range(i + 1, dateTimeList.size())
                        .anyMatch(j -> dateTimeList.get(i)
                                .minusMonths(6)
                                .isBefore(dateTimeList.get(j)))
                );
    }

    static LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    static String getTimeCreated(LocalDateTime createdAt) {
        PrettyTime prettyTime = new PrettyTime();
        Date createdDate = Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
        return prettyTime.format(createdDate);
    }

}
