package com.example.landingpage;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class GenerateCardViewOthers {
    private Context context;
    private Cursor cursor;
    private LinearLayout mainLayout;
    private int REMTYPE;
    public GenerateCardViewOthers(Context context , Cursor cursor, LinearLayout mainLayout , String remType) {
        this.context = context;
        this.cursor = cursor;
        this.mainLayout=mainLayout;
        if(remType.equals("Anniversary") || remType.equals("Birthday")){
            this.REMTYPE = 0;
        }else if(remType.equals("Weekly")){
            this.REMTYPE = 1;
        }else if(remType.equals("Monthly")){
            this.REMTYPE = 2;
        }else {
            this.REMTYPE = 404;
        }
    }

    public void generateView(){
        int idIndex = cursor.getColumnIndex("id");
        int descIndex = cursor.getColumnIndex("shortdesc");
        int notesIndex = cursor.getColumnIndex("addnotes");
        int dateIndex = cursor.getColumnIndex("date");
        int timeIndex = cursor.getColumnIndex("time");
        LayoutInflater inflator = LayoutInflater.from(context);
        cursor.moveToFirst();
        do{
            final View view = inflator.inflate(R.layout.cardviewothers,null);
            final LinearLayout layout_main = view.findViewById(R.id.layout_main);
            TextView tv_short_desc = view.findViewById(R.id.tv_short_desc);
            TextView tv_date = view.findViewById(R.id.tv_date);
            TextView tv_date_label = view.findViewById(R.id.tv_date_label);
            TextView tv_time = view.findViewById(R.id.tv_time);
            TextView tv_time_label = view.findViewById(R.id.tv_time_label);
            TextView tv_remtime = view.findViewById(R.id.tv_remtime);
            final LinearLayout layout_add_items = view.findViewById(R.id.layout_add_items);

            layout_add_items.setVisibility(View.GONE);
            tv_short_desc.setText(cursor.getString(descIndex));
            view.setId(Integer.parseInt(cursor.getString(idIndex)));
            if(REMTYPE == 0){

                String [] date = cursor.getString(dateIndex).split("/");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH , Integer.parseInt(date[0]));
                cal.set(Calendar.MONTH , Integer.parseInt(date[1])-1);
                tv_date.setText(cursor.getString(dateIndex));
                tv_date_label.setText("Birth Date : ");
                tv_time.setVisibility(View.GONE);
                tv_time_label.setVisibility(View.GONE);
                tv_remtime.setText(new CardViewUtils().getNextBirthdayAnniversary(cal));
            }else if(REMTYPE == 2){
                String [] time = cursor.getString(timeIndex).split(":");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(cursor.getString(dateIndex)));
                cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
                cal.set(Calendar.MINUTE,Integer.parseInt(time[1]));
                tv_date.setText(cursor.getString(dateIndex));
                tv_date_label.setText("Montlhy Date : ");
                tv_time.setText(cursor.getString(timeIndex));
                tv_time_label.setText("Time : ");
                tv_remtime.setText(new CardViewUtils().getNextMonthly(cal));
            }else{
                String [] time = cursor.getString(timeIndex).split(":");
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(time[0]));
                cal.set(Calendar.MINUTE,Integer.parseInt(time[1]));
                tv_date.setVisibility(View.GONE);
                tv_time.setText(cursor.getString(timeIndex));
                tv_date_label.setText("Daily At : ");
                tv_remtime.setText(new CardViewUtils().getNextDaily(cal));
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,String.valueOf(view.getId()),Toast.LENGTH_SHORT).show();
                    layout_add_items.setVisibility(layout_add_items.getVisibility()==view.GONE ? View.VISIBLE : View.GONE);

                }
            });
            mainLayout.addView(view);

        }while(cursor.moveToNext());
    }
}

