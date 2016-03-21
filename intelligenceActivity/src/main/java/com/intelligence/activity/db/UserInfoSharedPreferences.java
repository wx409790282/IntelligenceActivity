package com.intelligence.activity.db;

import com.intelligence.activity.utils.WifiTools;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 用户基础数据的缓存类
 * @author Administrator
 *
 */
public class UserInfoSharedPreferences {
	public static final String NAME             = "userinfo";
	public static final String USER_ACCOUNT     = "userAccount";  //用户账号
	public static final String USER_PASS        = "userPass";  //用户账号
	public static final String USER_IMEI        = "userImei";
	public static final String IS_ONE           = "is_one";
	public static final String IS_AUTO_PHONE               = "isautophone";
	public static final String IS_AUTO_LOGIN               = "isautologin";
	public static final String USER_PHONE               = "phone";
	public static final String USER_PASSWORD             = "password";
	
	private static UserInfoSharedPreferences Instance;
	private SharedPreferences  sharedPreferences;
	
	private UserInfoSharedPreferences(){
		
	}
	public static UserInfoSharedPreferences getInstance(){
		if(Instance == null){
			Instance = new UserInfoSharedPreferences();
		}
		return Instance;
	}
	
	public String getUserInfoStringKey(Context context,String key){

		//========测试
//		return "0000000000000001";
		//========
		return WifiTools.readuuid();

//		sharedPreferences = context.getSharedPreferences(NAME, 0);
//		String value = sharedPreferences.getString(key, null);
//		return value;
	}
	public String getUserInfoString(Context context,String key){
		sharedPreferences = context.getSharedPreferences(NAME, 0);
		String value = sharedPreferences.getString(key, "");
		return value;
	}
	public int getUserInfoIntKey(Context context,String key){
		sharedPreferences = context.getSharedPreferences(NAME, 0); 
		int value = sharedPreferences.getInt(key, 0);
		return value;
	}
	public boolean getUserInfoBoolKey(Context context,String key){
		sharedPreferences = context.getSharedPreferences(NAME, 0);
		boolean value = sharedPreferences.getBoolean(key, false);
		return value;
	}
	
	/**
	 * 保存用户输入的手机号码  必须在登陆以后才保存
	 * @param context
	 * @param type
	 */
	public void setUserNumber(Context context,String numberName,String pass){
		sharedPreferences = context.getSharedPreferences(NAME, 0); 
		sharedPreferences.edit().putString(USER_ACCOUNT, numberName).commit();
		sharedPreferences.edit().putString(USER_PASS, pass).commit();
	}

	
	public void setUserImei(Context context,String imei){
		sharedPreferences = context.getSharedPreferences(NAME, 0); 
		sharedPreferences.edit().putString(USER_IMEI, imei).commit();
	}
	
	
	public void setUserip(Context context,String IP){
		sharedPreferences = context.getSharedPreferences(NAME, 0); 
		sharedPreferences.edit().putString(IP, IP).commit();
	}
	
	/**
	 * 保存用户输入的手机号码  必须在登陆以后才保存
	 * @param context
	 * @param type
	 */
	public void setUserIsOne(Context context,int state){
		sharedPreferences = context.getSharedPreferences(NAME, 0); 
		sharedPreferences.edit().putInt(IS_ONE, state).commit();
	}
	
	/**
	 * 清理用户登陆信息
	 * @param context
	 */
	
	public void clearAP(Context context){
		sharedPreferences = context.getSharedPreferences(NAME, 0); 
		sharedPreferences.edit().putString(USER_ACCOUNT, null).commit();
		sharedPreferences.edit().putString(USER_PASS, null).commit();
	}

	public void setUserAutoPhone(Context context,boolean state){
		sharedPreferences = context.getSharedPreferences(NAME, 0);
		sharedPreferences.edit().putBoolean(IS_AUTO_PHONE, state).commit();
	}
	public void setUserAutoLogin(Context context,boolean state){
		sharedPreferences = context.getSharedPreferences(NAME, 0);
		sharedPreferences.edit().putBoolean(IS_AUTO_LOGIN, state).commit();
	}
	public void setUserPhone(Context context,String IP){
		sharedPreferences = context.getSharedPreferences(NAME, 0);
		sharedPreferences.edit().putString(USER_PHONE, IP).commit();
	}
	public void setUserPassword(Context context,String IP){
		sharedPreferences = context.getSharedPreferences(NAME, 0);
		sharedPreferences.edit().putString(USER_PASSWORD, IP).commit();
	}
}
