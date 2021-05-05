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

        int offset = TimeZone.getTimeZone("UTC+02:00").getOffset(timestamp)+ TimeZone.getTimeZone("UTC+02:00").getDSTSavings();
        long now = System.currentTimeMillis() - offset;
        Log.e("timee", new Time(now).toString());
        return timestamp == null ? null : new Time(timestamp);

    }

    @TypeConverter
    public static Long fromTime(Time time) {
        return time == null ? null : time.toInstant().getEpochSecond();
    }

}