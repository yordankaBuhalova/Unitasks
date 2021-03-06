package com.example.unitasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unitasks.data.converters.DateConverter;
import com.example.unitasks.data.model.Task;
import com.example.unitasks.data.views.TasksViewModel;
import com.example.unitasks.ui.adapters.TaskListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TasksViewModel tasksViewModel;
    private CalendarView calendar;
    private TextView day;
    private TaskListAdapter adapter;
    private Date date;

    public static final int NEW_TASK_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        calendar = findViewById(R.id.calendarView);


        long selectedDate = calendar.getDate();
        day = findViewById(R.id.day);
        String dayOfWeek = new SimpleDateFormat("EEEE").format(new Date(selectedDate));
        day.setText(dayOfWeek);

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            date = new GregorianCalendar(year, month, dayOfMonth).getTime();
            String newDayOfWeek = new SimpleDateFormat("EEEE").format(date);
            day.setText(newDayOfWeek);
            updateDate();
        });

        RecyclerView recyclerView = findViewById(R.id.rec_view);
        adapter = new TaskListAdapter(new TaskListAdapter.TaskDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(date == null) {
            // today
            Calendar today = new GregorianCalendar();
            // reset hour, minutes, seconds and millis
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);
            date = today.getTime();
        }
        updateDate();

        FloatingActionButton fab = findViewById(R.id.add_task);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, NEW_TASK_ACTIVITY_REQUEST_CODE);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TASK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Task task = new Task(data.getSerializableExtra(AddTaskActivity.EXTRA_REPLY));
            tasksViewModel.insert(task);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void updateDate() {
        tasksViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TasksViewModel.class);
        tasksViewModel.getTasksByDate(date).observe(this, tasks -> {
            // Update the cached copy of the tasks in the adapter.
            adapter.submitList(tasks);
        });
    }
}

