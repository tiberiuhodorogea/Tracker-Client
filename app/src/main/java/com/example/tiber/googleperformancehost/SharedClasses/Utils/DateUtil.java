package com.example.tiber.googleperformancehost.SharedClasses.Utils;

import java.util.Date;

//number of seconds from reference time(so I lose milis)
public class DateUtil {
	public static Date fromIntFormat(int date){
		return (new Date(((long)date)*1000L));
	}
	public static int nowIntFormat(){
		return (int) (new Date().getTime()/1000);
	}
}
