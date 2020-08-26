package com.example.landingpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class AddReminderActivity extends AppCompatActivity {
    private String SHORTDESC = null;
    private String ADDNOTES = null;
    private String REMINDERTYPE = null;
    private String SCHEDULETYPE = null;
    private String DATEPICKED = null;
    private String ONETIMEDATEPICKED = null;
    private String DAYINNUM = null;
    private String DAYINALPHA = null;
    private String FINALDBDATE = null;
    private String TIMEPICKED = null;

    private String viewDetailsIntentReminderType = null;
    private String viewDetailsIntentScheduleType = null;
    private String viewDetailsIntentDayInNum = null;
    private String viewDetailsIntentDayInAlpha = null;

    private Spinner spinner_type;
    private Spinner spinner_schedule;
    private Spinner spinner_monthly;
    private String txt_spinnner_types;

    private TextView tv_bday_date;
    private TextView tv_pick_time;
    private TextView tv_one_time_date;
    private TextView tv_validation_error;

    private EditText et_short_desc;
    private EditText et_add_notes;

    private LinearLayout layout_bday_picker;
    private LinearLayout layout_buisness_picker;
    private LinearLayout layout_one_time_date;
    private LinearLayout layout_pick_day;
    private LinearLayout layout_pick_time;
    private LinearLayout layout_validation_error;

    private DatePickerDialog.OnDateSetListener bdayDateSetListener;
    private DatePickerDialog.OnDateSetListener oneTimeDateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private Button btn_add_reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        spinner_type = findViewById(R.id.spinner_type);
        spinner_schedule = findViewById(R.id.spinner_schedule);
        spinner_monthly = findViewById(R.id.spinner_monthly);
        tv_bday_date = findViewById(R.id.tv_bday_date);
        tv_pick_time = findViewById(R.id.tv_pick_time);
        tv_one_time_date = findViewById(R.id.tv_one_time_date);
        tv_validation_error = findViewById(R.id.tv_validation_error);
        et_short_desc = findViewById(R.id.et_short_desc);
        et_add_notes = findViewById(R.id.et_add_notes);
        layout_bday_picker = findViewById(R.id.layout_bday_picker);
        layout_buisness_picker = findViewById(R.id.layout_buisness_picker);
        layout_one_time_date = findViewById(R.id.layout_one_time_date);
        layout_pick_day = findViewById(R.id.layout_pick_day);
        layout_pick_time = findViewById(R.id.layout_pick_time);
        layout_validation_error = findViewById(R.id.layout_validation_error);
        btn_add_reminder = findViewById(R.id.btn_add_reminder);
        layout_validation_error.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int id = extras.getInt("id");
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("RemindersData",MODE_PRIVATE,null);
            Cursor cursor = myDatabase.rawQuery("SELECT * FROM remindersdata  WHERE id=?" , new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            int descIndex = cursor.getColumnIndex("shortdesc");
            int notesIndex = cursor.getColumnIndex("addnotes");
            int remtypeIndex = cursor.getColumnIndex("remtype");
            int scheduletypeIndex = cursor.getColumnIndex("scheduletype");
            int dateIndex = cursor.getColumnIndex("date");
            int timeIndex = cursor.getColumnIndex("time");
            int statusIndex = cursor.getColumnIndex("status");
            et_short_desc.setText(cursor.getString(descIndex));
            et_add_notes.setText(cursor.getString(notesIndex));
            tv_one_time_date.setText(cursor.getString(dateIndex));
            tv_pick_time.setText(cursor.getString(timeIndex));
            viewDetailsIntentReminderType = cursor.getString(remtypeIndex);
            viewDetailsIntentScheduleType = viewDetailsIntentReminderType.equals("Buisness") || viewDetailsIntentReminderType.equals("Personal") ? cursor.getString(scheduletypeIndex) : null;
            viewDetailsIntentDayInNum = cursor.getString(scheduletypeIndex).equals("Monthly")?cursor.getString(dateIndex) : null;
            viewDetailsIntentDayInAlpha = cursor.getString(scheduletypeIndex).equals("Weekly")?cursor.getString(dateIndex) : null;


        }else{

        }
        setSpinnerReminderType();
        setSpinnerScheduleType();
        setBdayTvClickListener();
        setTvPickTimeClickListener();
        setTvOneTimeDateClickListener();

        btn_add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertReminder();
            }
        });

    }

    public void setSpinnerReminderType(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.types_array,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter);
        if(viewDetailsIntentReminderType != null){
            int position = adapter.getPosition(viewDetailsIntentReminderType);
            spinner_type.setSelection(position);
        }
        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_spinnner_types = (String) parent.getSelectedItem();
                if(txt_spinnner_types.equals("Birthday")|| txt_spinnner_types.equals("Anniversary")){
                    layout_bday_picker.setVisibility(View.VISIBLE);
                    layout_buisness_picker.setVisibility(View.GONE);
                }else{
                    layout_bday_picker.setVisibility(View.GONE);
                    layout_buisness_picker.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setSpinnerScheduleType(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.schedule_array,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_schedule.setAdapter(adapter);
        spinner_schedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                SCHEDULETYPE = selectedItem;
                if(selectedItem.equals("One Time")){
                    layout_one_time_date.setVisibility(View.VISIBLE);
                    layout_pick_day.setVisibility(View.GONE);
                }else if(selectedItem.equals("Daily")){
                    layout_one_time_date.setVisibility(View.GONE);
                    layout_pick_day.setVisibility(View.GONE);
                    layout_pick_time.setVisibility(View.VISIBLE);
                }else if(selectedItem.equals("Weekly") || selectedItem.equals("Monthly")){
                    layout_one_time_date.setVisibility(View.GONE);
                    layout_pick_day.setVisibility(View.VISIBLE);
                    layout_pick_time.setVisibility(View.VISIBLE);
                    setSpinnerWeeklyMonthly(selectedItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setBdayTvClickListener() {
        tv_bday_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DATE);
                DatePickerDialog dialog = new DatePickerDialog(AddReminderActivity.this,bdayDateSetListener,year,month,day);
                dialog.show();
            }
        });
        bdayDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = new DateAndTimeFromatter().dateFormatter(String.valueOf(dayOfMonth) +"/" +  String.valueOf(month) + "/" + String.valueOf(year), "TV_BIRTHDAY");
                tv_bday_date.setText(date);
            }
        };
    }

    public void setTvPickTimeClickListener() {
        tv_pick_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(AddReminderActivity.this,timeSetListener,hour,minute,true);
                dialog.show();
            }
        });
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String time = new DateAndTimeFromatter().timeFormatter(hourOfDay, minute);
                tv_pick_time.setText(time);
            }
        };
    }

    public void setTvOneTimeDateClickListener() {
        tv_one_time_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DATE);
                DatePickerDialog dialog = new DatePickerDialog(AddReminderActivity.this,oneTimeDateSetListener,year,month,day);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                dialog.show();
            }
        });
        oneTimeDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = new DateAndTimeFromatter().dateFormatter(String.valueOf(dayOfMonth) + "/"+ String.valueOf(month+1) +"/"+ String.valueOf(year) , "OTHERS");
                tv_one_time_date.setText(date);
            }
        };
    }

    public void setSpinnerWeeklyMonthly(final String weeklyOrMonthly){
        ArrayAdapter<CharSequence> adapter;
        if(weeklyOrMonthly.equals("Monthly")){
            adapter = ArrayAdapter.createFromResource(this,R.array.monthly_array,android.R.layout.simple_spinner_dropdown_item);
        }else {
            adapter = ArrayAdapter.createFromResource(this, R.array.weekly_array, android.R.layout.simple_spinner_dropdown_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_monthly.setAdapter(adapter);
    }

    public void insertReminder(){

        if(validation()){
            SHORTDESC = et_short_desc.getText().toString();
            ADDNOTES = et_add_notes.getText().toString();
            REMINDERTYPE = spinner_type.getSelectedItem().toString();
            SCHEDULETYPE = REMINDERTYPE.equals("Birthday") || REMINDERTYPE.equals("Anniversary") ? "" : spinner_schedule.getSelectedItem().toString();
            DATEPICKED = REMINDERTYPE.equals("Birthday") || REMINDERTYPE.equals("Anniversary") ? new DateAndTimeFromatter().dateFormatter(tv_bday_date.getText().toString() , "DATABASE_BDAY") : null;
            ONETIMEDATEPICKED = SCHEDULETYPE.equals("One Time") ? tv_one_time_date.getText().toString() : null;
            DAYINNUM = SCHEDULETYPE.equals("Monthly") ? spinner_monthly.getSelectedItem().toString() : null;
            DAYINALPHA = SCHEDULETYPE.equals("Weekly") ? spinner_monthly.getSelectedItem().toString() : null;
            TIMEPICKED = REMINDERTYPE.equals("Birthday") || REMINDERTYPE.equals("Anniversary") ? null : tv_pick_time.getText().toString();
            FINALDBDATE = DATEPICKED != null ? DATEPICKED : (ONETIMEDATEPICKED != null ? ONETIMEDATEPICKED : (DAYINNUM != null ? DAYINNUM : (DAYINALPHA != null ? DAYINALPHA : null)));

            SQLiteDatabase myDatabase = this.openOrCreateDatabase("RemindersData",MODE_PRIVATE,null);
            ContentValues values = new ContentValues();
            values.put("shortdesc" , SHORTDESC);
            values.put("addnotes" , ADDNOTES);
            values.put("remtype" , REMINDERTYPE);
            values.put("scheduletype" , SCHEDULETYPE);
            values.put("date" , FINALDBDATE);
            values.put("time" , TIMEPICKED);
            values.put("status" , "Pending");
            values.put("extra1" , "");
            values.put("extra2" , "");
            myDatabase.insert("remindersdata",null,values);
            Toast.makeText(getApplicationContext(), "Reminder Added Successfully", Toast.LENGTH_SHORT).show();
            final Intent intent = new Intent(this , MainActivity.class);
            startActivityForResult(intent,1);
            finish();
        }else{
            return;
        }

    }

    public boolean validation(){
        if(TextUtils.isEmpty(et_short_desc.getText()) || et_short_desc.getText().length() < 3){
            tv_validation_error.setText("Please enter valid short description");
            et_short_desc.requestFocus();
            layout_validation_error.setVisibility(View.VISIBLE);
            return false;
        }

        if(spinner_type.getSelectedItem().equals("Birthday") || spinner_type.getSelectedItem().equals("Anniversary")){
            if(TextUtils.isEmpty(tv_bday_date.getText())){
                tv_validation_error.setText("Please enter valid Date");
                tv_bday_date.requestFocus();
                layout_validation_error.setVisibility(View.VISIBLE);
                return false;
            }
        }else{
            if(spinner_schedule.getSelectedItem().equals("One Time")){
                if(TextUtils.isEmpty(tv_one_time_date.getText())){
                    tv_validation_error.setText("Please enter Date");
                    tv_one_time_date.requestFocus();
                    layout_validation_error.setVisibility(View.VISIBLE);
                    return false;
                }
                if(TextUtils.isEmpty(tv_pick_time.getText())){
                    tv_validation_error.setText("Please enter Time");
                    tv_pick_time.requestFocus();
                    layout_validation_error.setVisibility(View.VISIBLE);
                    return false;
                }
                String [] time = tv_pick_time.getText().toString().split(":");
                String [] date = tv_one_time_date.getText().toString().split("/");

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
                cal.set(Calendar.MINUTE , Integer.parseInt(time[1]));
                cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(date[0]));
                cal.set(Calendar.MONTH,Integer.parseInt(date[1])-1);
                cal.set(Calendar.YEAR,Integer.parseInt(date[2]));

                if(!new DateAndTimeFromatter().dateCompare(cal , Calendar.getInstance())){
                    tv_validation_error.setText("Please enter future time");
                    layout_validation_error.setVisibility(View.VISIBLE);
                    tv_pick_time.requestFocus();
                    return false;
                }

            }
            if(spinner_schedule.getSelectedItem().equals("Daily") || spinner_schedule.getSelectedItem().equals("Weekly") || spinner_schedule.getSelectedItem().equals("Monthly")){
                if(TextUtils.isEmpty(tv_pick_time.getText())){
                    tv_validation_error.setText("Please enter Time");
                    tv_pick_time.requestFocus();
                    layout_validation_error.setVisibility(View.VISIBLE);
                    return false;
                }

            }
        }



        return true;
    }
}