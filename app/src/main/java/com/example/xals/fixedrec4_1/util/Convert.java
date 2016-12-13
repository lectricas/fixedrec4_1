package com.example.xals.fixedrec4_1.util;

import android.content.Context;


import com.example.xals.fixedrec4_1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public abstract class Convert {

    public static final int REQUEST_FINE_LOCATION = 1;
    public static final int REQUEST_TRACK_CLOSE = 2;

    public static String formatTime(Date date){
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return  timeFormat.format(date);
    }

    public static String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return  dateFormat.format(date);
    }


    public static String formatDateFile(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        return  dateFormat.format(date);
    }

    public static String formatDateAndTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH-mm");
        return  dateFormat.format(date);
    }

    public static Date dateFromMills(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return  calendar.getTime();
    }

    public static String getDateDiff(Date date1, Date date2, Context context) {
        long diffInMillies = date2.getTime() - date1.getTime();
        long minutes = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (minutes >= 60){
            return context.getString(R.string.hour, TimeUnit.HOURS.convert(minutes, TimeUnit.MINUTES));
        }else {
            return context.getString(R.string.minute, minutes);
        }
    }

    public static String getKmIfNeeded(float meters, Context context){
        if (meters > 999){
            return context.getString(R.string.km_with_put, meters/1000);
        }else{
            return  context.getString(R.string.m_with_put, meters);
        }
    }

    public static Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
}