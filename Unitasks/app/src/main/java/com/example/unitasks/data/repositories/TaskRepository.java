package com.example.unitasks.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.unitasks.data.AppDatabase;
import com.example.unitasks.data.converters.DateConverter;
import com.example.unitasks.data.dao.TaskDao;
import com.example.unitasks.data.model.Task;

import java.util.Date;
import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> mAllTasks;
    
    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
        mAllTasks = taskDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public LiveData<List<Task>> getTasksByDate(Date date) {
        Log.e("date", String.valueOf(new DateConverter().fromDate(date)));
        return taskDao.loadByDate(new DateConverter().fromDate(date));
    }

    public LiveData<List<Task>> getTaskByName(String taskName) {
        return taskDao.loadByName(taskName);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertAll(task);
        });
    }

    public void update(Task task) {
        taskDao.updateTask(task);
    }

    public void delete(Task task) {
        taskDao.delete(task);
    }
}
