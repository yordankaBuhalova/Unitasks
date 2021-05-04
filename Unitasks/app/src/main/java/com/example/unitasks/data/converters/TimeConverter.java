package com.example.unitasks.data.converters;

import androidx.room.TypeConverter;

import java.sql.Time;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.zone.ZoneOffsetTransition;
import java.util.Locale;
import java.util.TimeZone;

public class TimeConverter {
    @TypeConverter
    public static Time toTime(Long timestamp) {
        return timestamp == null ? null : new Time(timestamp);
    }

    @TypeConverter
    public static Long fromTime(Time time) {

        return time == null ? null : time.toInstant().getEpochSecond();
    }

}