package com.example.unitasks.data.views;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.unitasks.data.model.Task;
import com.example.unitasks.data.repositories.TaskRepository;


import java.util.Date;
import java.util.List;

public class TasksViewModel extends AndroidViewModel {
    private TaskRepository mRepository;
    private final LiveData<List<Task>> mAllTasks;

    public TasksViewModel (Application application) {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks; }

    public void insert(Task task) { mRepository.insert(task); }
}
