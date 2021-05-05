package com.example.unitasks.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.unitasks.data.model.Task;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM task WHERE id IN (:taskIds)")
    LiveData<List<Task>> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM task WHERE course_name LIKE :taskNames LIMIT 1")
    LiveData<List<Task>> loadByName(String taskNames);

    @Query("SELECT * FROM task WHERE date=:date")
    LiveData<List<Task>> loadByDate(long date);

    @Insert
    void insertAll(Task... tasks);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void updateTask(Task task);

    @Query("DELETE FROM task")
    void deleteAll();
}
