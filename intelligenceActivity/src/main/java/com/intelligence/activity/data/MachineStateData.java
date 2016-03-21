package com.intelligence.activity.data;

import java.io.Serializable;
import java.util.List;

public class MachineStateData implements Serializable{
	private MachineStateItemData data; 
	
	public MachineStateItemData getData() {
		return data;
	}

	public void setData(MachineStateItemData data) {
		this.data = data;
	}

	public static class MachineStateItemData implements Serializable{
		public MachineStateItemData(){}
		
		private String level;
		private String temp;
		private String hub;
		private String state;
		private String lasttime;
		private String id;
		private String appid;
		
//		.{"status":1,"data":{"humidity":"70%","level":"0.0L","state":"0","laststarttime":"0","lasttpappid":"0","lastappid":""}}
		private String humidity;
		
		private int anion;//负离子
		
		
		
		
		public int getAnion() {
			return anion;
		}
		public void setAnion(int anion) {
			this.anion = anion;
		}
		public String getHumidity() {
			return humidity;
		}
		public void setHumidity(String humidity) {
			this.humidity = humidity;
		}
		public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public String getTemp() {
			return temp;
		}
		public void setTemp(String temp) {
			this.temp = temp;
		}
		public String getHub() {
			return hub;
		}
		public void setHub(String hub) {
			this.hub = hub;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getLasttime() {
			return lasttime;
		}
		public void setLasttime(String lasttime) {
			this.lasttime = lasttime;
		}
	}
}
