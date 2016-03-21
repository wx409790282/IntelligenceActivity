package com.intelligence.activity.humidifier;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class HumWork {
	
	int operation;
	int costtime;
	String humidity;
	String starttime;
	
	
	

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public int getCosttime() {
		return costtime;
	}

	public void setCosttime(int costtime) {
		this.costtime = costtime;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public void setValue(JSONObject list){


		try {
			operation = list.getInt("operation");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			costtime = list.getInt("costtime");
			humidity = list.getString("humidity");
			starttime = list.getString("starttime");
			String times[] = starttime.split(" ");
			if (times.length>1) {
				starttime = times[1] + "\n" + times[0];	
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
