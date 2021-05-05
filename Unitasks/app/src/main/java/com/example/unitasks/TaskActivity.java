package com.example.unitasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.unitasks.data.model.Task;
import com.example.unitasks.data.repositories.TaskRepository;

import java.io.Serializable;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private int MAIN_ACTIVITY_REQUEST_CODE = 2;
    private Toolbar mToolbar;
    private Button back;
    private Button delete;
    private Button edit;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Intent i = this.getIntent();

        // Get task
        String taskName = i.getStringExtra("task");
        TaskRepository taskRepository = new TaskRepository((Application) getApplicationContext());
        if(taskName.contains("  ")) {
            String hour = taskName.split("  ")[0];
            taskName = taskName.split("  ")[1];
        }
        LiveData<List<Task>> tasks = taskRepository.getTaskByName(taskName);
        Observer<List<Task>> o = tasks1 -> {
            if(!tasks1.isEmpty()) {
                task = tasks1.get(0);

                // Set data from task in view
                //clock
                TextView v = (TextView) findViewById(R.id.textView9);
                v.setText(task.time.toString());

                // course name
                v = (TextView) findViewById(R.id.textView14);
                v.setText(task.course_name);

                // professor name
                v = (TextView) findViewById(R.id.textView16);
                v.setText(task.profesor_name);

                // Create delete dialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(TaskActivity.this);
                dialogBuilder.setMessage("Are you sure you want to delete this event?")
                        .setTitle("Delete event");
                dialogBuilder.setPositiveButton("OK", (dialog, id) -> {
                    taskRepository.delete(task);
                    Intent intent = new Intent(TaskActivity.this, MainActivity.class);
                    startActivityForResult(intent, MAIN_ACTIVITY_REQUEST_CODE);
                    dialog.dismiss();
                });
                dialogBuilder.setNegativeButton("Cancel", null);
                dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
                dialogBuilder.setCancelable(true);
                AlertDialog dialog = dialogBuilder.create();

                // delete button mapper
                delete = findViewById(R.id.delete);
                delete.setOnClickListener(view -> {
                    dialog.show();
                });

                // edit button mapper
                edit = findViewById(R.id.edit);
                edit.setOnClickListener(view -> {
                    Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
                    intent.putExtra("task", task.course_name);
                    startActivityForResult(intent, MAIN_ACTIVITY_REQUEST_CODE);
                });
            }
        };
        tasks.observe(this, o);

        // back button mapper
        back = findViewById(R.id.back_to_main);
        back.setOnClickListener(view ->{
            Intent intent = new Intent(TaskActivity.this, MainActivity.class);
            startActivityForResult(intent, MAIN_ACTIVITY_REQUEST_CODE);
            finish();
        });
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