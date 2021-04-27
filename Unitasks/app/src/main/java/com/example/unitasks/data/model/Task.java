package com.example.unitasks.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    public String course_name;
    public String profesor_name;
    public int week_num;
    public Date date;
    public Time time;
    public Task() {}

    public Task(String course_name, String profesor_name, int week_num, Date date, Time time) {
        this.course_name = course_name;
        this.profesor_name = profesor_name;
        this.week_num = week_num;
        this.date = date;
        this.time = time;
    }

    public String getTask() {
        return this.course_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                week_num == task.week_num &&
                course_name.equals(task.course_name) &&
                profesor_name.equals(task.profesor_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, course_name, profesor_name, week_num);
    }
}
