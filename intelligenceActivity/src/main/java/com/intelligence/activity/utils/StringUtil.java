package com.intelligence.activity.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

public class StringUtil {
    //using in attendance, calendar
    public static final String EMPTY = "";
	public static boolean isEmpty(String param) {
		return null == param || "".equals(param);
	}

	//don't know when to user


	public static String getIMEI(Context context) {
		String imei = null;
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = telephonyManager.getDeviceId();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (imei == null) {
			return "0";
		}
		return imei;
	}

	public static String getIMSI(Context context) {
		String imsi = null;

		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = telephonyManager.getSubscriberId();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (imsi == null) {
			return "0";
		}
		return imsi;
	}

	public static String getVersionName(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}



	public static String getPhoneNumberType(Context context) {

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		// imsi 国际移动用户识别码
		String imsi = tm.getSubscriberId();
		if (imsi != null) {
			if (imsi.startsWith("46000") || imsi.startsWith("46002")
					|| imsi.startsWith("46007")) {
				// 中国移动
				return "中国移动";
			} else if (imsi.startsWith("46001")) {
				// 中国联通
				return "中国联通";
			} else if (imsi.startsWith("46003")) {
				// 中国电信
				return "中国电信";
			} else {
				// 无法判断
				return "无法识别";
			}

		} else {
			return null;
		}
	}
	
}
