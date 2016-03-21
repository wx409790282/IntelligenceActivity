package com.intelligence.activity.http;

import java.util.HashMap;

import org.json.JSONObject;

import android.util.Log;



public class SocketData {
	public String setConnData(String appid){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("&&KEY1=2&GET_HEAT_STATE&ID=");
		stringBuffer.append(appid+"\r\n");
		return stringBuffer.toString();
	}
	
	public String setConnType(String state){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("&&KEY1=5&MODE=" + state
				+ "\r\n");
		return stringBuffer.toString();
	} 
	
	
	public static HashMap<String, Object> getString(String string){
		string = string.replaceAll(" ","");
		string = string.replaceAll("\r\n","");
		HashMap<String, Object> map = new HashMap<String, Object>();
		int index = string.substring(0, 1).indexOf("&");
		if(index < 0){
			String[] strings = string.split("&");
			for(int i = 0 ; i < strings.length ; i++){
				addObject(map,strings[i]);
			}
			return map;
		}else{
			string = string.substring(index);
			string = string.substring(1);
			String[] strings = string.split("&");
			for(int i = 0 ; i < strings.length ; i++){
				addObject(map,strings[i]);
			}
			return map;
		}
	}
	
	
	
	public static String getString(String string,int length){
		string = string.replaceAll(" ","");
		string = string.replaceAll("\r\n","");
		JSONObject map = new JSONObject();
		int index = string.indexOf("&");
		if(index < 0){
			String[] strings = string.split("&");
			for(int i = 0 ; i < strings.length ; i++){
				addObject(map,strings[i]);
			}
			return map.toString();
		}else{
			string = string.substring(index);
			string = string.substring(1);
			String[] strings = string.split("&");
			for(int i = 0 ; i < strings.length ; i++){
				addObject(map,strings[i]);
			}
			return map.toString();
		}
	}
	
	private static void addObject(JSONObject map,String string){
		String[] strings = string.split("=");
		if(strings != null && strings.length >= 1){
			try{
				
				String key = strings[0];
				String value = strings[1];
				if(key == null){
					key = "00";
				}
				if(value == null){
					value = "00";
				}
				map.put(key, value);
			}catch(Exception e){
				
			}
			
		}
	}
	
	
	private static void addObject(HashMap<String, Object> map,String string){
		String[] strings = string.split("=");
		if(strings != null && strings.length >= 1){
			try{
				
				String key = strings[0];
				String value = strings[1];
				if(key == null){
					key = "00";
				}
				if(value == null){
					value = "00";
				}
				Log.e("aa", "key---------"+key);
				map.put(key, value);
			}catch(Exception e){
				
			}
			
		}
	}
}
