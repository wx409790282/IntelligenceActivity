package com.intelligence.activity.controller;

import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.intelligence.activity.data.MachineStateData.MachineStateItemData;
import com.intelligence.activity.db.DBhelperManager.ZDYData;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.http.SocketCallback;
import com.intelligence.activity.http.SocketConnect;
import com.intelligence.activity.http.SocketData;
import com.intelligence.activity.utils.Utils;

public class StateHandler extends Handler{
	HashMap<String, Object> map = new HashMap<String, Object>();
	Thread tcpThread;
	
	private Handler TCPhandler;
	
	private String ip = null;
	
	private boolean isAction = false;
	public StateHandler(Context context,String ip ){
		this.ip = ip;
		connect.setRemoteAddress(ip, 8080);
	}
	public void setTCPhandler(Handler tCPhandler) {
		TCPhandler = tCPhandler;
	}

	private boolean isState = true;
	private boolean isRed = false;
	private SocketConnect connect = new SocketConnect(new SocketCallback() {
        
        @Override
        public void receive(byte[] buffer) {
			String strings = new String(buffer);
           strings = Utils.replaceBlank(strings);
           if(strings.indexOf("&&KEY2=5") != -1){ //获取水壶当前状态返回
        	   if(!isRed){
        		   return;
        	   }
        	   isRed = false;
        	   HashMap<String, Object> map = SocketData.getString(strings);
        	   if(TCPhandler != null){
        		   isState = true;
        		   Message msg = new Message();
        		   msg.what = 1000;
        		   Log.e("aa", "TEMP---------"+map.get("TEMP").toString());
        		   MachineStateItemData data = new MachineStateItemData();
        		   data.setHub(map.get("HUB").toString());
        		   data.setLevel(map.get("LEVEL").toString());
        		   data.setLasttime(map.get("LASTTIME").toString());
        		   data.setState(map.get("STATE").toString());
        		   data.setTemp(map.get("TEMP").toString());
        		   data.setLasttime(map.get("LASTTIME").toString());
        		   data.setId(map.get("MACHINEID").toString());
        		   msg.obj = data;
        		   TCPhandler.sendMessage(msg);
        	   }
           }else if(strings.indexOf("&&KEY2=8") != -1){
        	   isAction = false;
           }else if(strings.indexOf("&&KEY2=1") != -1){//发送加热返回
        	   Log.e("aa", "strings KEY2 = 1:---------------"+strings);
        	   Message msg = new Message();
    		   msg.what = 1001;
        	   TCPhandler.sendMessage(msg);
        	   isAction = false;
           }else if(strings.indexOf("&&KEY2=6") != -1){//取消加热返回
        	   Log.e("aa", "strings KEY2 = 6:---------------"+strings);
        	   Message msg = new Message();
    		   msg.what = 1002;
        	   TCPhandler.sendMessage(msg);
        	   isAction = false;
           }else if(strings.indexOf("machineid=") != -1){
        	   if(!isRed){
        		   return;
        	   }
        	   isRed = false;
        	   strings = strings.substring(strings.indexOf("machineid"));
        	   HashMap<String, Object> map = SocketData.getString(strings);
        	   if(TCPhandler != null){
        		   isState = true;
        		   Message msg = new Message();
        		   msg.what = 1000;
        		   MachineStateItemData data = new MachineStateItemData();
        		   data.setId(map.get("machineid").toString());
        		   msg.obj = data;
        		   TCPhandler.sendMessage(msg);
        	   }
           }
        }
         
        @Override
        public void disconnect() {
             
        }
         
        @Override
        public void connected() {
             
        }
    });

	@Override
	public void handleMessage(Message msg) {
		// TODO 自动生成的方法存根
		super.handleMessage(msg);
		/**TCP
		 * 获取当前水壶状态
		 */
		if(msg.what == 1001){
			if(isAction){
				isAction = false;
				return;
			}
	        if(!isAction){
	        	if(isState){
	        		isState = false;
	        		isRed = true;
	        		tcpThread = new Thread(connect);
	  	        	tcpThread.start();
	  	        	connect.write(("&&KEY1=2&GET_HEAT_STATE&ID="+HttpUrl.APP_ID+"\r\n").getBytes());
	        	}
	        }
		
		}
		/**
		 * 设置内网模式
		 */
		else if(msg.what == 1000){
	        tcpThread = new Thread(connect);
	        tcpThread.start();
	        connect.write(("&&KEY1=5&MODE=0\r\n").getBytes());
		}
		/**
		 * 发送加热命令
		 */
		else if(msg.what == 1002){
			 isAction = true;
			ZDYData data = (ZDYData)msg.obj;
	        tcpThread = new Thread(connect);
	        tcpThread.start();
	        StringBuffer stringBuffer = new StringBuffer();
	        stringBuffer.append("&&KEY1=1");
	        stringBuffer.append("&TEMP="+data.ZDY_SW);
	        stringBuffer.append("&BOLL="+data.ZDY_ISZF);
	        stringBuffer.append("&PURIFY="+data.ZDY_TIME);
	        stringBuffer.append("&KEEPWARM="+data.ZDY_TIME1);
	        stringBuffer.append("&HEATTIME=0");
	        stringBuffer.append("&WEEK=0000000");
	        int times = Integer.parseInt(data.ZDY_TIME)*60
					+  Integer.parseInt(data.ZDY_TIME1)*60
					+ 360;
	        stringBuffer.append("&KEEPWARM="+times);
	        stringBuffer.append("&ID="+HttpUrl.APP_ID+"\r\n");
	        connect.write(stringBuffer.toString().getBytes());
		}
		
		/**
		 * 取消加热
		 */
		else if(msg.what == 1003){
			isAction = true;
	        tcpThread = new Thread(connect);
	        tcpThread.start();
	        connect.write(("&&KEY1=3&STOPHEAT\r\n").getBytes());
		}
	}
	
	public void destoy(){
		if(connect != null){
			connect.disconnect();
		}
	}
}
