package com.example.landingpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btn_add_reminder;
    private TextView tv_no_reminders;
    private LinearLayout layout_today_reminder;
    private GenerateCardView generateCardView;
    private GenerateCardViewOthers generateCardViewOthers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0b126a")));
        getSupportActionBar().setTitle("");
        layout_today_reminder = findViewById(R.id.layout_today_reminder);
        btn_add_reminder = findViewById(R.id.btn_add_reminder);
        tv_no_reminders = findViewById(R.id.tv_no_reminders);
        final Intent intent = new Intent(this , AddReminderActivity.class);
        btn_add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("RemindersData",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS remindersdata (id  INTEGER PRIMARY KEY AUTOINCREMENT, shortdesc VARCHAR, addnotes VARCHAR, remtype VARCHAR, scheduletype VARCHAR, date VARCHAR, time VARCHAR, status VARCHAR , extra1 VARCHAR , extra2 VARCHAR)");
        Cursor myCursor = myDatabase.rawQuery("SELECT * FROM remindersdata WHERE scheduletype='One Time'" , null);
        int rowCount = myCursor.getCount();
        if(rowCount != 0){
            tv_no_reminders.setVisibility(View.GONE);
            myCursor.moveToFirst();
            generateCardView = new GenerateCardView(this,myCursor,layout_today_reminder);
            do{
                generateCardView.generateView();
            }while(myCursor.moveToNext());
        }

        Cursor myCursor2 = myDatabase.rawQuery("SELECT * FROM remindersdata WHERE remtype='Birthday' OR remType='Anniversary'" , null);
        int rowCount2 = myCursor2.getCount();
        if(rowCount2 != 0){
            tv_no_reminders.setVisibility(View.GONE);
            myCursor2.moveToFirst();
            generateCardViewOthers = new GenerateCardViewOthers(this,myCursor2,layout_today_reminder,"Birthday");
            do{
                generateCardViewOthers.generateView();
            }while(myCursor2.moveToNext());
        }

        Cursor myCursor3 = myDatabase.rawQuery("SELECT * FROM remindersdata WHERE scheduletype='Monthly'" , null);
        int rowCount3 = myCursor3.getCount();
        if(rowCount3 != 0){
            tv_no_reminders.setVisibility(View.GONE);
            myCursor3.moveToFirst();
            generateCardViewOthers = new GenerateCardViewOthers(this,myCursor3,layout_today_reminder,"Monthly");
            do{
                generateCardViewOthers.generateView();
            }while(myCursor3.moveToNext());
        }

        Cursor myCursor4 = myDatabase.rawQuery("SELECT * FROM remindersdata WHERE scheduletype='Daily'" , null);
        int rowCount4 = myCursor4.getCount();
        if(rowCount4 != 0){
            tv_no_reminders.setVisibility(View.GONE);
            myCursor4.moveToFirst();
            generateCardViewOthers = new GenerateCardViewOthers(this,myCursor4,layout_today_reminder,"Daily");
            do{
                generateCardViewOthers.generateView();
            }while(myCursor4.moveToNext());
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        layout_today_reminder.removeAllViews();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        layout_today_reminder.removeAllViews();
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("RemindersData",MODE_PRIVATE,null);
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS remindersdata (id  INTEGER PRIMARY KEY AUTOINCREMENT, shortdesc VARCHAR, addnotes VARCHAR, remtype VARCHAR, scheduletype VARCHAR, date VARCHAR, time VARCHAR, status VARCHAR , extra1 VARCHAR , extra2 VARCHAR)");
        Cursor myCursor = myDatabase.rawQuery("SELECT * FROM remindersdata  WHERE scheduletype='One Time'" , null);
        int rowCount = myCursor.getCount();
        if(rowCount != 0){
            tv_no_reminders.setVisibility(View.GONE);
            myCursor.moveToFirst();
            generateCardView = new GenerateCardView(this,myCursor,layout_today_reminder);
            do{
                generateCardView.generateView();
            }while(myCursor.moveToNext());
        }

        Cursor myCursor2 = myDatabase.rawQuery("SELECT * FROM remindersdata WHERE remtype='Birthday'" , null);
        int rowCount2 = myCursor2.getCount();
        if(rowCount2 != 0){
            tv_no_reminders.setVisibility(View.GONE);
            myCursor2.moveToFirst();
            generateCardViewOthers = new GenerateCardViewOthers(this,myCursor2,layout_today_reminder,"Birthday");
            do{
                generateCardViewOthers.generateView();
            }while(myCursor2.moveToNext());
        }

        Cursor myCursor3 = myDatabase.rawQuery("SELECT * FROM remindersdata WHERE scheduletype='Monthly'" , null);
        int rowCount3 = myCursor3.getCount();
        if(rowCount3 != 0){
            tv_no_reminders.setVisibility(View.GONE);
            myCursor3.moveToFirst();
            generateCardViewOthers = new GenerateCardViewOthers(this,myCursor3,layout_today_reminder,"Monthly");
            do{
                generateCardViewOthers.generateView();
            }while(myCursor3.moveToNext());
        }
    }
}