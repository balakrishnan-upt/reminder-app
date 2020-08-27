package com.example.landingpage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class GenerateCardView {
    private Context context;
    private Cursor cursor;
    private LinearLayout mainLayout;
    public GenerateCardView(Context context , Cursor cursor, LinearLayout mainLayout) {
        this.context = context;
        this.cursor = cursor;
        this.mainLayout=mainLayout;
    }

    public void generateView(){
        final int idIndex = cursor.getColumnIndex("id");
        int descIndex = cursor.getColumnIndex("shortdesc");
        int notesIndex = cursor.getColumnIndex("addnotes");
        int dateIndex = cursor.getColumnIndex("date");
        int timeIndex = cursor.getColumnIndex("time");
        int statusIndex = cursor.getColumnIndex("status");
        LayoutInflater inflator = LayoutInflater.from(context);
        cursor.moveToFirst();
        do{
            final View view = inflator.inflate(R.layout.cardview,null);
            final LinearLayout layout_main = view.findViewById(R.id.layout_main);
            TextView tv_short_desc = view.findViewById(R.id.tv_short_desc);
            TextView tv_status = view.findViewById(R.id.tv_status);
            TextView tv_remTime = view.findViewById(R.id.tv_remtime);
            TextView tv_expiryDate = view.findViewById(R.id.tv_expirydate);
            TextView tv_expiryTime = view.findViewById(R.id.tv_expirytime);
            Button btn_viewdetails = view.findViewById(R.id.btn_viewdetails);
            Button btn_status = view.findViewById(R.id.btn_status);
            Button btn_delete = view.findViewById(R.id.btn_delete);
            final LinearLayout layout_add_items = view.findViewById(R.id.layout_add_items);

            layout_add_items.setVisibility(View.GONE);
            tv_short_desc.setText(cursor.getString(descIndex));
            view.setId(Integer.parseInt(cursor.getString(idIndex)));
            tv_expiryDate.setText(cursor.getString(dateIndex));
            tv_status.setText(cursor.getString(statusIndex));
            tv_expiryTime.setText(cursor.getString(timeIndex));
            tv_remTime.setText(new CardViewUtils().getOneTimeRemaining(cursor.getString(dateIndex),cursor.getString(timeIndex)));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,String.valueOf(view.getId()),Toast.LENGTH_SHORT).show();
                    layout_add_items.setVisibility(layout_add_items.getVisibility()==view.GONE ? View.VISIBLE : View.GONE);

                }
            });
            btn_viewdetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context , AddReminderActivity.class);
                    i.putExtra("id" , view.getId());
                    context.startActivity(i);
                }
            });
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase myDatabase = context.openOrCreateDatabase("RemindersData",MODE_PRIVATE,null);
                    int a = myDatabase.delete("remindersdata" ,"id=?",new String[]{String.valueOf(view.getId())} );
                    if(a > 0){
                        Toast.makeText(context,"Reminder deleted successfully" , Toast.LENGTH_SHORT).show();
                        mainLayout.removeView(view);
                    }


                }
            });
            mainLayout.addView(view);

        }while(cursor.moveToNext());
    }
}

