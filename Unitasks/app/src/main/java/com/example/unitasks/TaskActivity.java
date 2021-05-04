package com.example.unitasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.unitasks.data.model.Task;
import com.example.unitasks.data.repositories.TaskRepository;

import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Intent i = this.getIntent();
        String taskName = i.getStringExtra("task");
        TaskRepository taskRepository = new TaskRepository((Application) getApplicationContext());
        String hour = taskName.split("  ")[0];
        taskName = taskName.split("  ")[1];
        LiveData<List<Task>> tasks = taskRepository.getTaskByName(taskName);
        Observer<List<Task>> o = task -> {
            Log.e("tasks", task.toString());
        };
        tasks.observe(this, o);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }
        return true;
    }
}