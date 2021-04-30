package com.example.unitasks.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


import com.example.unitasks.data.converters.DateConverter;
import com.example.unitasks.data.converters.TimeConverter;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String course_name;
    public String profesor_name;
    public int week_num;
    @TypeConverters(TimeConverter.class)
    public Time time;
    @TypeConverters(DateConverter.class)
    public Date date;

    public Task(Serializable serializableExtra) {}

    public Task(String course_name, String profesor_name, int week_num, Date date, Time time) {
        this.course_name = course_name;
        this.profesor_name = profesor_name;
        this.week_num = week_num;
        this.date = date;
        this.time = time;
    }

    public String getTask() {
        return this.profesor_name;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", course_name='" + course_name + '\'' +
                ", profesor_name='" + profesor_name + '\'' +
                ", week_num=" + week_num +
                ", time=" + time +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                week_num == task.week_num &&
                course_name.equals(task.course_name) &&
                profesor_name.equals(task.profesor_name)&&
                date.equals(task.date)&&
                time.equals(task.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course_name, profesor_name, week_num, date, time);
    }
}
