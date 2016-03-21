package com.intelligence.activity.humidifier;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class HumCustomBean {
	public String cityname;
	public String citytemptop;
	public String citytempbottom;
	public String cityhumidity;

	public int g_min;//干
    
	public int z_min;//智能 小
	public int z_max;//智能 大
    
	public int s_max;//夏天 中
    
	public boolean nearbyOpen;//出现打开
	public boolean farawayClose;//离开关闭
    //开关提醒
	public boolean startOrStopRemind;
    //缺水提醒
	public boolean noWaterremind;
	//湿度
	public boolean tooDryRemind;
	
	
	public HumCustomBean(){
		z_min = 45;
		z_max = 65;
		g_min = 60;
		s_max = 140;
		cityname = "广州";
		citytemptop = "26℃";
		citytempbottom = "21℃";;
		cityhumidity = "80%";
		startOrStopRemind = true;
		noWaterremind = true;
	}

	public void mapToHCustom(JSONObject jsonObject){
		
		try {
			g_min = Integer.parseInt(jsonObject.getString("drymode").replace("%", ""));//干
			z_min = Integer.parseInt(jsonObject.getString("grade2humiditybottom").replace("%", ""));;//智能 小
			z_max = Integer.parseInt(jsonObject.getString("grade2humiditytop").replace("%", ""));;//智能 大
			s_max = Integer.parseInt(jsonObject.getString("wetmode").replace("%", ""));;//夏天 中
			nearbyOpen =convertIntToBool(jsonObject.getInt("enableusernearstart"));//出现打开
			farawayClose = convertIntToBool(jsonObject.getInt("enableuserfarstop"));//离开关闭
			cityname = jsonObject.getString("cityname");
			citytemptop = jsonObject.getString("citytemptop");
			citytempbottom = jsonObject.getString("citytempbottom");
			cityhumidity = jsonObject.getString("cityhumidity");
			//开关提醒
			startOrStopRemind = convertIntToBool(jsonObject.getInt("startandstopremind"));
			//缺水提醒
			noWaterremind = convertIntToBool(jsonObject.getInt("nowaterremind"));
			tooDryRemind = convertIntToBool(jsonObject.getInt("tooDryRemind"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean convertIntToBool(int i){
		if(i==1){
			return true;
		}
		return false;
	}
    
    
}
