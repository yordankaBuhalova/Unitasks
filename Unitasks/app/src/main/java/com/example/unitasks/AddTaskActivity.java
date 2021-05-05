package com.example.unitasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.room.TypeConverters;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.unitasks.data.AppDatabase;
import com.example.unitasks.data.converters.TimeConverter;
import com.example.unitasks.data.model.Task;
import com.example.unitasks.data.repositories.TaskRepository;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button back;

    public static final String EXTRA_REPLY = "com.example.unitasks.AddTaskActivity.REPLY";
    private int MAIN_ACTIVITY_REQUEST_CODE = 2;
    private EditText course_name;
    private EditText professor_name;
    private EditText week_num;
    private EditText task_time;
    private EditText task_date;
    private DatePickerDialog picker_date;
    private TimePickerDialog picker;
    private EditText eText;
    private EditText dateText;
    private Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        course_name = findViewById(R.id.course_name);
        professor_name = findViewById(R.id.professor_name);
        week_num = findViewById(R.id.week_num);
        task_date = findViewById(R.id.date);
        task_time = findViewById(R.id.time);
        final Button save = findViewById(R.id.button_save);
        TaskRepository taskRepository = new TaskRepository((Application) getApplicationContext());
        String taskName = getIntent().getStringExtra("task");

        SimpleDateFormat formatter1= new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());

        if(taskName != null) {
            LiveData<List<Task>> tasks = taskRepository.getTaskByName(taskName);
            Observer<List<Task>> o = tasks1 -> {
                if(!tasks1.isEmpty()) {
                    Task tmp = tasks1.get(0);
                    task = tmp;
                    course_name.setText(task.course_name, TextView.BufferType.EDITABLE);
                    professor_name.setText(task.profesor_name, TextView.BufferType.EDITABLE);
                    task_date.setText(formatter1.format(task.date), TextView.BufferType.EDITABLE);
                    task_time.setText(timeFormat.format(task.time), TextView.BufferType.EDITABLE);
                }
            };
            tasks.observe(this, o);
        }

        save.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(course_name.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            }
            else {
                String course = course_name.getText().toString();
                String professor = professor_name.getText().toString();
                String num = week_num.getText().toString();
                Date date;
                Time time;
                try {
                    date = formatter1.parse((task_date.getText().toString()));
                    Date d1 = timeFormat.parse(task_time.getText().toString());
                    assert d1 != null;
                    time = new Time(d1.getTime());

                    if(task == null) {
                        task = new Task(course, professor, Integer.parseInt(num), date, time);
                        taskRepository.insert(task);
                    }
                    else {
                        task.course_name = course;
                        task.profesor_name = professor;
                        task.time = time;
                        task.date = date;
                        taskRepository.update(task);
                    }
                    Intent intent = new Intent(AddTaskActivity.this, TaskActivity.class);
                    intent.putExtra("task", task.course_name);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }
            finish();
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(view ->{
            Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
            startActivityForResult(intent, MAIN_ACTIVITY_REQUEST_CODE);
            finish();
        });

        eText = findViewById(R.id.time);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minutes = cldr.get(Calendar.MINUTE);
            // time picker dialog
            picker = new TimePickerDialog(AddTaskActivity.this,
                    (tp, sHour, sMinute) -> eText.setText(sHour + ":" + sMinute), hour, minutes, true);
            picker.show();
        });

        dateText=findViewById(R.id.date);
        dateText.setInputType(InputType.TYPE_NULL);
        dateText.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
            picker_date = new DatePickerDialog(AddTaskActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> dateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1), year, month, day);
            picker_date.show();
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
}