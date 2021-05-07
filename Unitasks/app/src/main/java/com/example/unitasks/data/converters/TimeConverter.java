package com.example.unitasks.data.converters;

import android.text.format.DateUtils;
import android.util.Log;

import androidx.room.TypeConverter;

import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.GregorianCalendar;
public class TimeConverter {
    @TypeConverter
    public static Time toTime(Long timestamp) {
        return timestamp == null ? null : new Time(timestamp * 1000L);

    }

    @TypeConverter
    public static Long fromTime(Time time) {
        return time == null ? null : time.toInstant().getEpochSecond();
    }

}