package com.intelligence.activity.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
/**
 * 工具类
 * @author Administrator
 *
 */
public class DateUtil {
	
	public static String getTimes(String time1,String time2){
		int t1 = Integer.parseInt(time1.substring(0,2));
		int t2 = Integer.parseInt(time2.substring(0,2));
		
		int t11 = Integer.parseInt(time1.substring(2,4));
		int t22 = Integer.parseInt(time2.substring(2,4));
		
		int t13 = Integer.parseInt(time1.substring(4,6));
		int t23 = Integer.parseInt(time2.substring(4,6));
		
		Log.e("aa", "t1:"+t1);
		Log.e("aa", "t2:"+t2);
		int s1 = t13+t11*60+t1*60*24;
		int s2 = t23+t22*60+t2*60*24;
		
		int s3 = s2 - s1;
		int s4 = s3/(60*24);
		if(s4 > 0)
			return s4+"天前";
		int s5 = s3/60;
		if(s5 > 0)
			return s5+"小时前";
		
		if(s3 >= 0){
			if(s3 == 0)
				s3 = 1;
			return s3+"分钟前";
		}
			
		
		return "1分钟前";
	}
	
	
	public static String _getFormalTimeYMD1(){
		return getFormat("yyyy-MM-dd");
	}
	// ���߷���
	private static String getFormat(String format) {
		Date de = new Date();
		DateFormat df = new SimpleDateFormat(format);
		return df.format(de);
	}

	public static String _getFormalTime(){
		return getFormat("yyyyMMddHHmmss");
	}
	
	
	public static String _getFormalTime1(){
		return getFormat("ddHHmm");
	}
	
	public static int getFormalTimeMM(){
		return Integer.parseInt(getFormat("MM"));
	}
	
	
	public static int _getFormalTimeDD(){
		return Integer.parseInt(getFormat("dd"));
	}
	
	public static int _getFormalTimeHH(){
		return Integer.parseInt(getFormat("HH"));
	}
	
	public static int _getFormalTimeH_M(){
		return Integer.parseInt(getFormat("mm"));
	}
	
	public static String _getFormalTimeYMD(){
		return getFormat("yyyyMMdd");
	}
	// /���ϵͳʱ�䷵��yyyy-MM-dd HH:mm:ss��ʽ����
	public static String getFormalTime() {
		return getFormat("yyyy-MM-dd HH:mm:ss");
	}

	// ���ϵͳʱ�䷵��yyyy-MM-dd��ʽ����
	public static String getFormaTimeByYMD() {
		return getFormat("yyyy-MM-dd");
	}

	// ��ݸ��ֶδ�����ת��������
	public static Date setDateByString(String date) {
		SimpleDateFormat simDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = null;
		try {
			newDate = simDateFormat.parse(date);
			return newDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;

	}

	// �õ�һ��ʱ���Ӻ��ǰ�Ƽ����ʱ��,nowdateΪʱ��,delayΪǰ�ƻ���ӵ�����
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String mdate = "";
			Date d = DateUtil.setDateByString(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * �õ�һ��ʱ���Ӻ��ǰ�Ƽ����ʱ�䣬��ʽΪ��YYYY-MM-DD
	 * @param nowdate ��ǰʱ��
	 * @param delay ǰ�ƣ�������ӣ�����
	 * @return
	 */
	public static String getNextDayByDate(String nowdate, int delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = DateUtil.setDateByString(nowdate);
			long myTime = (d.getTime() / 1000) + delay * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}
}


