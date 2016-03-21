package com.intelligence.activity.data;


public class StateData {
	private StateItemData data;
	public StateItemData getData() {
		return data;
	}
	public void setData(StateItemData data) {
		this.data = data;
	}
	public class StateItemData{
		private String num;
		private String level;
		private String temp;
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
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
	}
}
