package com.example.landingpage;

import android.os.Build;
import android.util.Log;


import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTimeFromatter {

    public String dateFormatter(String date, String reminderType){
        String formattedDate = "";
        String [] dateArray;
        dateArray = date.split("/");
        if(reminderType.equals("TV_BIRTHDAY")){
            dateArray[0] = zeroFormatter(dateArray[0]);
            formattedDate = dateArray[0] + "/" + monthName(Integer.parseInt(dateArray[1]));
        }
        if(reminderType.equals("DATABASE_BDAY")){
            formattedDate = dateArray[0] + "/" + zeroFormatter(monthId(dateArray[1])) + "/" + Calendar.getInstance().get(Calendar.YEAR);
        }
        if(reminderType.equals("OTHERS")){
            formattedDate = zeroFormatter(dateArray[0]) + "/" + zeroFormatter(dateArray[1]) + "/" + dateArray[2];
        }
        return formattedDate;
    }

    public String timeFormatter(int hour , int minute){
        String formattedHour;
        String formattedMinute;
        String formattedTime;
        if(hour < 10){
            formattedHour = "0"+String.valueOf(hour);
        }else{
            formattedHour = String.valueOf(hour);
        }
        if(minute < 10){
            formattedMinute = "0" + String.valueOf(minute);
        }else {
            formattedMinute = String.valueOf(minute);
        }
        formattedTime = formattedHour +":"+ formattedMinute;
        return  formattedTime;
    }

    public String monthName(int month){
        switch (month){
            case 0 : return "January";
            case 1 : return "February";
            case 2 : return "March";
            case 3 : return "April";
            case 4 : return "May";
            case 5 : return "June";
            case 6 : return "July";
            case 7 : return "August";
            case 8 : return "September";
            case 9 : return "October";
            case 10 : return "November";
            case 11 : return "December";
            default: return "";
        }
    }

    public String monthId(String month){
        switch (month){
            case "January" : return "1";
            case "February" : return "2";
            case "March" : return "3";
            case "April" : return "4";
            case "May" : return "5";
            case "June" : return "6";
            case "July" : return "7";
            case "August" : return "8";
            case "September" : return "9";
            case "October" : return "10";
            case "November" : return "11";
            case "December" : return "12";
            default: return "";
        }
    }

    public String zeroFormatter(String dayOrMonth){
        if(Integer.valueOf(dayOrMonth) < 10){
            return "0" + dayOrMonth;
        }
        return dayOrMonth;
    }

    public boolean dateCompare(Calendar cal1 , Calendar cal2){
        if(cal1.getTimeInMillis() > cal2.getTimeInMillis()){
            return true;
        }else{
            return false;
        }
    }

    public String periodDifferenceCalculation(Period period){
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        int hours = period.getHours();
        int minutes = period.getMinutes();
        int seconds = period.getSeconds();

        if(years > 0){
        return (years > 1 ? years + " " + "years" : years + " " + "year") + " " + (months > 0 ? months + " " +"months" : months + " " +"month");
        }
        if(months > 0){
            return (months > 1 ? months + " " +"months" : months + " " +"month") + " " + (days > 0 ? days + " " +"days" : days + " " +"day");
        }
        if(days > 0){
            return (days > 1 ? days + " " +"days" : days + " " +"day") + " " + (hours > 0 ? hours + " " +"hours" : "");
        }
        if(hours > 0){
            return (hours > 1 ? hours + " " +"hours" : hours + " " +"hour") + " " + (minutes > 0 ? minutes + " " +"minutes" : "");
        }
        if(minutes > 0){
            return (minutes > 1 ? minutes + " " +"minutes" : minutes + " " +"minute") + " " + (seconds > 0 ? seconds + " " +"seconds" : "");
        }
        if(seconds > 0){
            return seconds > 1 ? seconds + " " +"seconds" : seconds + " " +"second";
        }
        return "Expired";
    }

}
