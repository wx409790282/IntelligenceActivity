package com.intelligence.activity.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	// ���߷���
	private static String getFormat(String format) {
		Date de = new Date();
		DateFormat df = new SimpleDateFormat(format);
		return df.format(de);
	}

	public static String _getFormalTime(){
		return getFormat("yyyyMMddHHmmss");
	}

}


