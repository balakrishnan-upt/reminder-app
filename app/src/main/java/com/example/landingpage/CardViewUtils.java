package com.example.landingpage;

import android.util.Log;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Calendar;

public class CardViewUtils {

    public String getNextBirthdayAnniversary(Calendar cal){
        Period period;
        int givenMonth = cal.get(Calendar.MONTH);
        int givenDate = cal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Calendar currentCal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY , 0);
        cal.set(Calendar.MINUTE , 0);
        cal.set(Calendar.SECOND,0);
        int givenHour = cal.get(Calendar.HOUR_OF_DAY);
        int givenMinute = cal.get(Calendar.MINUTE);
        int currentHour = currentCal.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCal.get(Calendar.MINUTE);
        int currentYear = currentCal.get(Calendar.YEAR);

        if(givenMonth < currentMonth){
            cal.set(Calendar.YEAR,currentYear+1);
        }else if(givenMonth == currentMonth){
            if(givenDate<currentDate){
                cal.set(Calendar.YEAR,currentYear+1);
            }else if(givenDate == currentDate){
                if(givenHour < currentHour){
                    cal.set(Calendar.YEAR,currentYear+1);
                }else if(givenHour == currentHour){
                    if(givenMinute < currentMinute){
                        cal.set(Calendar.YEAR,currentYear+1);
                    }
                }
            }
        }
        period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
        return new DateAndTimeFromatter().periodDifferenceCalculation(period);
    }

    public String getOneTimeRemaining(String date, String time){
        String [] dateArray = date.split("/");
        String [] timeArray = time.split(":");
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateArray[0]));
        cal1.set(Calendar.MONTH,Integer.parseInt(dateArray[1])-1);
        cal1.set(Calendar.YEAR,Integer.parseInt(dateArray[2]));
        cal1.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeArray[0]));
        cal1.set(Calendar.MINUTE,Integer.parseInt(timeArray[1]));
        cal1.set(Calendar.SECOND,0);
        //below from joda-time-implemnatation
        Period period = new Period(Calendar.getInstance().getTimeInMillis(),cal1.getTimeInMillis(),PeriodType.yearMonthDayTime());
        return new DateAndTimeFromatter().periodDifferenceCalculation(period);

    }

    public String getNextDaily(Calendar cal){
        Period period;
        cal.set(Calendar.SECOND,0);
        int givenHour = cal.get(Calendar.HOUR_OF_DAY);
        int givenMinute = cal.get(Calendar.MINUTE);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        Calendar currentCal = Calendar.getInstance();

        if(givenHour < currentHour){
            cal.set(Calendar.DAY_OF_MONTH,currentCal.get(Calendar.DAY_OF_MONTH)+1);
            period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
        }else if(givenHour == currentHour){
            if(givenMinute < currentMinute){
                cal.set(Calendar.DAY_OF_MONTH,currentCal.get(Calendar.DAY_OF_MONTH)+1);
                period = new Period(currentCal.getTimeInMillis() ,cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
            }else{
                period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
            }
        }else {
            period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
        }
        return new DateAndTimeFromatter().periodDifferenceCalculation(period);
    }
    public String getNextMonthly(Calendar cal){
        Period period;
        cal.set(Calendar.SECOND,0);
        int givenDay = cal.get(Calendar.DAY_OF_MONTH);
        int givenHour = cal.get(Calendar.HOUR_OF_DAY);
        int givenMinute = cal.get(Calendar.MINUTE);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

        Calendar currentCal = Calendar.getInstance();

        if(givenDay < currentDay){
            cal.set(Calendar.MONTH,currentCal.get(Calendar.MONTH)+1);
            period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
        }else if(givenDay == currentDay){
                        if(givenHour < currentHour){
                            cal.set(Calendar.MONTH,currentCal.get(Calendar.MONTH)+1);
                            period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
                        }else if(givenHour == currentHour){
                            if(givenMinute < currentMinute){
                                cal.set(Calendar.MONTH,currentCal.get(Calendar.MONTH)+1);
                                period = new Period(currentCal.getTimeInMillis() ,cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
                            }else{
                                period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
                            }
                        }else {
                            period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
                        }
        }else {
            period = new Period(currentCal.getTimeInMillis() , cal.getTimeInMillis() , PeriodType.yearMonthDayTime());
        }
        return new DateAndTimeFromatter().periodDifferenceCalculation(period);
    }

}

