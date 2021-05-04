package com.example.unitasks.data.converters;

import androidx.room.TypeConverter;

import java.sql.Time;

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