package com.example.unitasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Button back;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private int MAIN_ACTIVITY_REQUEST_CODE = 2;
    private EditText course_name;
    private EditText professor_name;
    private EditText week_num;
    private DatePickerDialog picker_date;
    private TimePickerDialog picker;
    private EditText eText;
    private EditText dateText;
    private Button btnGet;
    private TextView tvw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        course_name = findViewById(R.id.course_name);
        professor_name = findViewById(R.id.professor_name);
        week_num = findViewById(R.id.week_num);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(course_name.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            }
            else {
                String course = course_name.getText().toString();
                String professor = professor_name.getText().toString();
                String num = week_num.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, course);
                setResult(RESULT_OK, replyIntent);
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
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        dateText=findViewById(R.id.date);
        dateText.setInputType(InputType.TYPE_NULL);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
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