package com.intelligence.activity.humidifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.database.Cursor;

import com.intelligence.activity.utils.DateUtil;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.http.HttpUrl;

public class HTiming implements Serializable{
	public int t_id;//id
	public int custom_id;//自定义id
	public String repeat;//重复，1，2，3，4，5，6，7
	public String title;//标签
	public int begin_remind;//开始提醒
	public int end_remind;//结宿提醒
	public int water_remind;//水位提醒
	public float water_level;//水位高度
	public int t_open;
	public int c_hour;
	public int c_min;
	public int t_begin_time;//如果不循环，判断开始时间
	public String orderid;
	public int t_h_open;//加湿器开关


	public HTiming(){

		repeat = "1,2,3,4,5";//重复，1，2，3，4，5，6，7
		title = "预约";//标签
		begin_remind = 1;//开始提醒
		end_remind = 1;//结宿提醒

		t_open = 0;
		c_hour = 10;
		c_min = 10;

		t_h_open = 1;//加湿器开关
	}


	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public int getCustom_id() {
		return custom_id;
	}
	public void setCustom_id(int custom_id) {
		this.custom_id = custom_id;
	}
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getBegin_remind() {
		return begin_remind;
	}
	public void setBegin_remind(int begin_remind) {
		this.begin_remind = begin_remind;
	}
	public int getEnd_remind() {
		return end_remind;
	}
	public void setEnd_remind(int end_remind) {
		this.end_remind = end_remind;
	}
	public int getWater_remind() {
		return water_remind;
	}
	public void setWater_remind(int water_remind) {
		this.water_remind = water_remind;
	}
	public float getWater_level() {
		return water_level;
	}
	public void setWater_level(float water_level) {
		this.water_level = water_level;
	}
	public int getT_open() {
		return t_open;
	}
	public void setT_open(int t_open) {
		this.t_open = t_open;
	}
	public int getC_hour() {
		return c_hour;
	}
	public void setC_hour(int c_hour) {
		this.c_hour = c_hour;
	}
	public int getC_min() {
		return c_min;
	}
	public void setC_min(int c_min) {
		this.c_min = c_min;
	}
	public int getT_begin_time() {
		return t_begin_time;
	}
	public void setT_begin_time(int t_begin_time) {
		this.t_begin_time = t_begin_time;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public int getT_h_open() {
		return t_h_open;
	}
	public void setT_h_open(int t_h_open) {
		this.t_h_open = t_h_open;
	}

	public void sqlupdate(){
		String sql = "UPDATE timing SET repeat='"+repeat+"',title='"+title+"', begin_remind='"+begin_remind
				+"', end_remind='"+end_remind+"', water_remind='"+water_remind+"', water_level='"+water_level
				+"', custom_id='"+custom_id+"',t_open='"+t_open+"',c_hour='"+c_hour+"',c_min='"+c_min
				+"',t_h_open='"+t_h_open+"',t_begin_time='"+t_begin_time+"',orderid='"+orderid+"' WHERE id = '"+t_id+"'";
		WifiTools.db.execSQL(sql);
	}

	public void sqldelete(){
		String sql = "DELETE FROM timing WHERE id = "+t_id;
		WifiTools.db.execSQL(sql);
	}

	public void sqlinster(String machineid){
		Random random = new Random();
		int index = random.nextInt(89)+10;
		orderid = DateUtil._getFormalTime()+index;
		String sql = "INSERT INTO timing (repeat,title, begin_remind, end_remind, water_remind, water_level, custom_id,t_open,c_hour,c_min,t_h_open,t_begin_time,orderid,machineid) VALUES ('"+repeat+"','"+title+"','"+begin_remind+"','"+end_remind+"','"+water_remind+"','"+water_level+"','"+custom_id+"','"+t_open+"','"+c_hour+"','"+c_min+"','"+t_h_open+"','"+t_begin_time+"','"+orderid+"','"+machineid+"')";
		WifiTools.db.execSQL(sql);

		sql = "select last_insert_rowid() count";
		Cursor cursor = WifiTools.db.rawQuery(sql,null);
		while (cursor.moveToNext()) {
			t_id = cursor.getInt(cursor.getColumnIndex("count"));
		}
	}

	public void setValue(Cursor cursor){

		t_id = cursor.getInt(cursor.getColumnIndex("id"));// integer PRIMARY KEY AUTOINCREMENT,
		custom_id = cursor.getInt(cursor.getColumnIndex("custom_id"));// text,insulation integer DEFAULT 0,"
		repeat = cursor.getString(cursor.getColumnIndex("repeat"));//" integer DEFAULT 0,"
		title = cursor.getString(cursor.getColumnIndex("title"));//" integer DEFAULT 0,"
		begin_remind = cursor.getInt(cursor.getColumnIndex("begin_remind"));//" integer DEFAULT 0)
		end_remind = cursor.getInt(cursor.getColumnIndex("end_remind"));

		water_remind = cursor.getInt(cursor.getColumnIndex("water_remind"));
		water_level = cursor.getFloat(cursor.getColumnIndex("water_level"));

		t_open = cursor.getInt(cursor.getColumnIndex("t_open"));
		c_hour = cursor.getInt(cursor.getColumnIndex("c_hour"));
		c_min = cursor.getInt(cursor.getColumnIndex("c_min"));

		t_begin_time = cursor.getInt(cursor.getColumnIndex("t_begin_time"));
		orderid = cursor.getString(cursor.getColumnIndex("orderid"));
		t_h_open = cursor.getInt(cursor.getColumnIndex("t_h_open"));
	}
	public void changeOrderId(){
		Random random = new Random();
		int index = random.nextInt(89)+10;
		orderid = DateUtil._getFormalTime()+index;
		sqlupdate();
	}

	public List<NameValuePair> getHHttpMsgBySelect(String machineid){
		String begintime = "";
		if (c_hour<10) {
			begintime = "0"+c_hour;
		}else{
			begintime = begintime + c_hour;
		}
		if (c_min<10) {
			begintime = begintime + "0" + c_min;
		}else{
			begintime = begintime + c_min;
		}

		begintime = begintime + "00";
		
		String wlist[] = repeat.split(",");

		String val = "";
		for (int i=1;i<8;i++) {
			boolean on = false;
			for (String str : wlist) {
				if (i == Integer.parseInt(str)) {
					on = true;
					break;
				}
			}
			if (on) {
				val = val + "1";
			}else{
				val = val + "0";
			}

		}

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
		list.add(new BasicNameValuePair("machineid",machineid));
		list.add(new BasicNameValuePair("heattime",begintime));
		list.add(new BasicNameValuePair("grade",2+""));
		list.add(new BasicNameValuePair("week",val));

		if (t_h_open == 1) {
			list.add(new BasicNameValuePair("action","run"));
		}else{
			list.add(new BasicNameValuePair("action","stop"));
		}

		list.add(new BasicNameValuePair("orderid",orderid));
		list.add(new BasicNameValuePair("startremind",begin_remind+""));
		list.add(new BasicNameValuePair("endremind",end_remind+""));
		list.add(new BasicNameValuePair("nowaterremind",begin_remind+""));

		return list;
	}

	public List<NameValuePair> cancleOrder(String machineid){

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
		list.add(new BasicNameValuePair("machineid",machineid));
		list.add(new BasicNameValuePair("orderid",orderid));

		return list;
	}
}
