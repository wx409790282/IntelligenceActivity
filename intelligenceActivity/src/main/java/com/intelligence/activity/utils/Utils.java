package com.intelligence.activity.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.string;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class Utils {
	private static final String S5 = "0.5L";
	private static final String S8 = "0.8L";
	private static final String S10 = "1.0L";
	private static final String S12 = "1.2L";
	private static final String S15 = "1.5L";
	private static final String S17 = "1.7L";
	
	public static int getTime(String leverl,String string,boolean b){
		int time = Integer.parseInt(string);
		
		if(time <= 42 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			if(b)
				return 8250;
			return 26;
		}else if(time <= 42 && leverl.equals(S8)){
			if(b)
				return 9980;
			return 46;
		}else if(time <= 42 && leverl.equals(S10)){
			if(b)
				return 10621;
			return 52;
		}else if(time <= 42 && leverl.equals(S12)){
			if(b)
				return 12841;
			return 57;
		}else if(time <= 42 && leverl.equals(S15)){
			if(b)
				return 15176;
			return 70;
		}else if(time <= 42 && leverl.equals(S17)){
			if(b)
				return 16252;
			return 62;
		}
		
		
		else if(time > 43 &&  time <= 47 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			if(b)
				return 6250;
			return 32;
		}else if(time > 43 &&  time <= 47 && leverl.equals(S8)){
			if(b)
				return 7684;
			return 53;
		}else if(time > 43 &&  time <= 47&& leverl.equals(S10)){
			if(b)
				return 8362;
			return 59;
		}else if(time > 43 &&  time <= 47 && leverl.equals(S12)){
			if(b)
				return 10288;
			return 66;
		}else if(time > 43 &&  time <= 47 && leverl.equals(S15)){
			if(b)
				return 11583;
			return 85;
		}else if(time > 43 &&  time <= 47 && leverl.equals(S17)){
			if(b)
				return 12618;
			return 72;
		}
		
		
		else if(time > 48 &&  time <= 55 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			if(b)
				return 4828;
			return 37;
		}else if(time > 48 &&  time <= 55 && leverl.equals(S8)){
			if(b)
				return 6120;
			return 70;
		}else if(time > 48 &&  time <= 55&& leverl.equals(S10)){
			if(b)
				return 6755;
			return 72;
		}else if(time > 48 &&  time <= 55 && leverl.equals(S12)){
			if(b)
				return 8168;
			return 81;
		}else if(time > 48 &&  time <= 55 && leverl.equals(S15)){
			if(b)
				return 9163;
			return 99;
		}else if(time > 48 &&  time <= 55 && leverl.equals(S17)){
			if(b)
				return 10076;
			return 92;
		}
		
		
		else if(time > 56 &&  time <= 65 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			if(b)
				return 2985;
			return 52;
		}else if(time > 56 &&  time <= 65 && leverl.equals(S8)){
			if(b)
				return 3911;
			return 88;
		}else if(time > 56 &&  time <= 65&& leverl.equals(S10)){
			if(b)
				return 4254;
			return 89;
		}else if(time > 56 &&  time <= 65 && leverl.equals(S12)){
			if(b)
				return 5225;
			return 104;
		}else if(time > 56 &&  time <= 65 && leverl.equals(S15)){
			if(b)
				return 5915;
			return 129;
		}else if(time > 56 &&  time <= 65 && leverl.equals(S17)){
			if(b)
				return 6529;
			return 116;
		}
		
		
		else if(time > 66 &&  time <= 75 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			if(b)
				return 1822;
			return 64;
		}else if(time > 66 &&  time <= 75 && leverl.equals(S8)){
			if(b)
				return 2363;
			return 106;
		}else if(time > 66 &&  time <= 75&& leverl.equals(S10)){
			if(b)
				return 2073;
			return 108;
		}else if(time > 66 &&  time <= 75 && leverl.equals(S12)){
			if(b)
				return 3273;
			return 127;
		}else if(time > 66 &&  time <= 75 && leverl.equals(S15)){
			if(b)
				return 3745;
			return 158;
		}else if(time > 66 &&  time <= 75 && leverl.equals(S17)){
			if(b)
				return 4133;
			return 142;
		}
		
		
		else if(time > 76 &&  time <= 82 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			if(b)
				return 1022;
			return 74;
		}else if(time > 76 &&  time <= 82 && leverl.equals(S8)){
			if(b)
				return 1260;
			return 121;
		}else if(time > 76 &&  time <= 82&& leverl.equals(S10)){
			if(b)
				return 1558;
			return 129;
		}else if(time > 76 &&  time <= 82 && leverl.equals(S12)){
			if(b)
				return 1861;
			return 154;
		}else if(time > 76 &&  time <= 82 && leverl.equals(S15)){
			if(b)
				return 2071;
			return 191;
		}else if(time > 76 &&  time <= 82 && leverl.equals(S17)){
			if(b)
				return 2354;
			return 178;
		}
		
		
		else if(time > 83 &&  time <= 87 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			if(b)
				return 712;
			return 82;
		}else if(time > 83 &&  time <= 87 && leverl.equals(S8)){
			if(b)
				return 851;
			return 131;
		}else if(time > 83 &&  time <= 87&& leverl.equals(S10)){
			if(b)
				return 1175;
			return 139;
		}else if(time > 83 &&  time <= 87 && leverl.equals(S12)){
			if(b)
				return 1247;
			return 163;
		}else if(time > 83 &&  time <= 87 && leverl.equals(S15)){
			if(b)
				return 1445;
			return 206;
		}else if(time > 83 &&  time <= 87 && leverl.equals(S17)){
			if(b)
				return 1600;
			return 201;
		}
		
		
		else if(time > 88 &&  time <= 94 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			if(b)
				return 446;
			return 87;
		}else if(time > 88 &&  time <= 94 && leverl.equals(S8)){
			if(b)
				return 503;
			return 140;
		}else if(time > 88 &&  time <= 94&& leverl.equals(S10)){
			if(b)
				return 664;
			return 152;
		}else if(time > 88 &&  time <= 94 && leverl.equals(S12)){
			if(b)
				return 813;
			return 178;
		}else if(time > 88 &&  time <= 94 && leverl.equals(S15)){
			if(b)
				return 1015;
			return 221;
		}else if(time > 88 &&  time <= 94 && leverl.equals(S17)){
			if(b)
				return 1019;
			return 219;
		}
		
		
		else if(time > 95 &&  time <= 100 && (leverl.equals(S5) || leverl.equals("0.6L") || leverl.equals("0.7L"))){
			return 110;
		}else if(time > 95 &&  time <= 100 && leverl.equals(S8)){
			return 161;
		}else if(time > 95 &&  time <= 100&& leverl.equals(S10)){
			return 176;
		}else if(time > 95 &&  time <= 100 && leverl.equals(S12)){
			return 201;
		}else if(time > 95 &&  time <= 100 && leverl.equals(S15)){
			return 247;
		}else if(time > 95 &&  time <= 100 && leverl.equals(S17)){
			return 260;
		}
		
		
		return 0;
	}
	/**
	 * 设置闹钟监听
	 * @param context
	 * @param timeInMillis
	 */
	public static void setAlarmTime(Context context) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("com.jb.android.alarm.action");
		
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		int interval = 60 * 1000;// 闹铃间隔， 这里设为1分钟闹一次，在第2步我们将每隔1分钟收到一次广播
		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, sender);
	}
	
	public static Bitmap setAlpha(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg

                        .getWidth(), sourceImg.getHeight());// 获得图片的ARGB值

        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {

        	argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF); 

        }

        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg

                        .getHeight(), Config.ARGB_8888);

        return sourceImg;

}
	
	public static boolean isWifi(Activity activity){
		WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);  		
		if (wifiManager.isWifiEnabled() == false)
		{
			return false;
		}
		return true;
	}
	public static String replaceBlank(String str) {  
        String dest = "";  
        if (str!=null) {  
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");  
                Matcher m = p.matcher(str);  
                dest = m.replaceAll("");  
        }  
        return dest;  
	}  
	
	public static String getUrl(HashMap<String, Object> map,String url){
		StringBuffer stringBuffer = new StringBuffer();
		
		Set<String> set = map.keySet();
		stringBuffer.append(url);
		for (String key : set) {
			stringBuffer.append(key+"/");
			stringBuffer.append(map.get(key).toString()+"/");
		}
	
		return stringBuffer.toString();
	}

	public static boolean isConnect(Context context){
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            
            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    Log.e("aa",i + "===状态===" + networkInfo[i].getState());
                    Log.e("aa",i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
