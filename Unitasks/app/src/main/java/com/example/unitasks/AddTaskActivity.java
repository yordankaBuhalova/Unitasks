package com.example.unitasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

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
import com.example.unitasks.data.model.Task;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private Button btnGet;
    private TextView tvw;
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
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(course_name.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            }
            else {
                SimpleDateFormat formatter1= new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
                String course = course_name.getText().toString();
                String professor = professor_name.getText().toString();
                String num = week_num.getText().toString();
                Date date = null;
                Time time = null;
                try {
                    date = formatter1.parse((task_date.getText().toString()));
                    Date d1 = timeFormat.parse(task_time.getText().toString());
                    assert d1 != null;
                    time = new Time(d1.getTime());

                    task = new Task(course, professor, Integer.parseInt(num), date, time);
                    Log.i("task", task.toString());

                    assert task != null;
                    AppDatabase.getDatabase(getApplicationContext()).taskDao().insert(task);
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
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                            eText.setText(sHour + ":" + sMinute);
                        }
                    }, hour, minutes, true);
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
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, year, month, day);
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