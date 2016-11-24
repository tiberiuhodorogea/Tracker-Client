package com.example.tiber.googleperformancehost.SharedClasses.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

//number of seconds from reference time(so I lose milis)
public class DateUtil {
	public static Date fromIntFormat(int date){
		return (new Date(((long)date)*1000L));
	}
	public static int nowIntFormat(){
		return (int) (new Date().getTime()/1000);
	}
    public static String toUIString(Date date){
        SimpleDateFormat format = null;
        String ret = "";
        String sdfArg = "dd MMM, HH:MM";
        if(date.getYear() == new Date().getYear()){
            format = new SimpleDateFormat(sdfArg);
        }
        else{
            format = new SimpleDateFormat("yy," + sdfArg);
        }


        return format.format(date);
    }

    public static String toUIString(int dateInt){
        return DateUtil.toUIString(DateUtil.fromIntFormat(dateInt));
    }
}
