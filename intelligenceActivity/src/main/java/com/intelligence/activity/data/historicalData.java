package com.intelligence.activity.data;

import java.util.List;

public class historicalData {
	private List<historicalItemData> data;
	public List<historicalItemData> getData() {
		return data;
	}
	public void setData(List<historicalItemData> data) {
		this.data = data;
	}
	public static class historicalItemData{
		private String machineid;
		private String operation;
		private String starttime;
		private String endtime;
		private String level;
		private String temp;
		private String boil;
		private String purify;
		private String createtime;
		private String keepwarm;
		private String energy;
		public String getMachineid() {
			return machineid;
		}
		public void setMachineid(String machineid) {
			this.machineid = machineid;
		}
		public String getOperation() {
			return operation;
		}
		public void setOperation(String operation) {
			this.operation = operation;
		}
		public String getStarttime() {
			return starttime;
		}
		public void setStarttime(String starttime) {
			this.starttime = starttime;
		}
		public String getEndtime() {
			return endtime;
		}
		public void setEndtime(String endtime) {
			this.endtime = endtime;
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
		public String getBoil() {
			return boil;
		}
		public void setBoil(String boil) {
			this.boil = boil;
		}
		public String getPurify() {
			return purify;
		}
		public void setPurify(String purify) {
			this.purify = purify;
		}
		public String getCreatetime() {
			return createtime;
		}
		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}
		public String getKeepwarm() {
			return keepwarm;
		}
		public void setKeepwarm(String keepwarm) {
			this.keepwarm = keepwarm;
		}
		public String getEnergy() {
			return energy;
		}
		public void setEnergy(String energy) {
			this.energy = energy;
		}
	}
	
	
}
