package com.intelligence.activity.attendance;

import android.annotation.SuppressLint;
import android.content.Context;

import com.intelligence.activity.R;
import com.intelligence.activity.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class CalendarUtil {
    
    public static final long  ONE_DAY = 24 * 3600 * 1000;
    
    public static final String FORMAT_DATEONLY_BY_SPLIT = "yyyy-MM-dd";
    
    public static final String FORMAT_DATE_DAY = "dd";
    
    public static final String FORMAT_CHINESE = "yyyy年MM月";
    public static final String FORMAT_month = "yyyyMM";
    public static final String FORMAT_day = "yyyyMMdd";

    
	public static String millis2Calendar(long millis, String format) {
		if (millis > 0) {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(millis);
	        try {
	            return simpleDateFormat.format(calendar.getTime());
	        } catch (Exception e) {
	            
	        } finally {
	            calendar = null;
	            simpleDateFormat = null;
	            format = null;
	        }
		}
        
        return "";
    }
    
    public static String getWeekDate(Context context, long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return CalendarUtil.millis2Calendar(millis, FORMAT_DATEONLY_BY_SPLIT) + " 星期" + getWeekStr(context, calendar.get(Calendar.DAY_OF_WEEK));
    }
    
    private static String getWeekStr(Context context, int index) {
        String week = StringUtil.EMPTY;
        if (null != context) {
            switch (index) {
                case 1:
                    week = context.getString(R.string.home_sunday);
                    break;
                case 2:
                    week = context.getString(R.string.home_monday);
                    break;
                case 3:
                    week = context.getString(R.string.home_tuesday);
                    break;
                case 4:
                    week = context.getString(R.string.home_wednesday);
                    break;
                case 5:
                    week = context.getString(R.string.home_thursday);
                    break;
                case 6:
                    week = context.getString(R.string.home_friday);
                    break;
                case 7:
                    week = context.getString(R.string.home_saturday);
                    break;
            }
        }
        return week;
    }
    
}
