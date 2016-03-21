package com.intelligence.activity.controller;

import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.utils.Utils;

public class ConnHandler extends Handler{
	public static final long MAX_TIME = 60 * 1000;
	private long starttime,starttime1 = 0;
	private long endtime;
	public static boolean isConn = false;
	private Context context;
	HashMap<String, Object> map = new HashMap<String, Object>();
	Thread tcpThread;
	public static int ConnState = 1; 
	public ConnHandler(Context context){
		this.context = context;
		starttime = System.currentTimeMillis();
		init();
	}
	
	
	@Override
	public void handleMessage(Message msg) {
		// TODO 自动生成的方法存根
		if(starttime1 >= MAX_TIME){
			starttime1 = 0;
			map.put("appid", HttpUrl.APP_ID);
			map.put("page", "1");
			map.put("pagesize", "20");
			HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler);
			handler.setProcessing(true);
			httpUtil.create(0, Utils.getUrl(map, HttpUrl.GET_MCHINELIST), null);
		}else{
			starttime1 += 100;
		}
		
		super.handleMessage(msg);
	}
	
	private HttpHandler handler;
	
	
	private void init(){
		handler = new HttpHandler(context){
			protected void succeed(String jObject,int state) {
				super.succeed(jObject,state);
				isConn = true;
				ConnState = 1;
				endtime = System.currentTimeMillis();
			};
			
			protected void failed(String jObject,int state){
				super.failed(jObject,state);
				isConn  = false;
				endtime = System.currentTimeMillis();
			}
			
			protected void connEerr() {
				ConnState = 2;
				endtime = System.currentTimeMillis();
				if(endtime - starttime >= MAX_TIME){
					isConn  = false;
				}
				
			}
		};
	}
	
}
