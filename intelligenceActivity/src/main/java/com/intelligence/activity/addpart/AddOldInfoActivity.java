package com.intelligence.activity.addpart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.controller.ConnHandler;
import com.intelligence.activity.data.OldData;
import com.intelligence.activity.db.Machinenu_DBhelperManager;
import com.intelligence.activity.db.Machinenu_DBhelperManager.MACHINDATA;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.json.JsonParser;
import com.xlwtech.util.XlwDevice;
import com.xlwtech.util.XlwDevice.XlwDeviceListener;
/**
 * 添加已有设备界面
 * @author Administrator
 *
 */
public class AddOldInfoActivity extends BaseActivity implements OnClickListener{
	private GridView gridView;
	
	private AddOldInfoAdpter adpter;
	
	View view;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
		
		list = Machinenu_DBhelperManager.getInstance(this).getAlermList(0);
		
		gridView = (GridView)findViewById(R.id.girdview_id);
		
		
		setTitle(this.getString(R.string.add_device));
		setLeftOnClick(null);
		setRigter(false, this.getString(R.string.save));
		
		view = LayoutInflater.from(this).inflate(R.layout.not_data, null);
		addContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		view.setVisibility(View.GONE);
		adpter = new AddOldInfoAdpter(this,m_listMac);
		gridView.setAdapter(adpter);
		gridView.setOnItemClickListener(adpter);
		Log.e("aa", ConnHandler.isConn+"");
		if(ConnHandler.isConn){  //判断当前是否外网模式
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
			list.add(new BasicNameValuePair("onlineflag","online"));
			list.add(new BasicNameValuePair("bindflag","all"));
			HttpConnectionUtils httpUtil = new HttpConnectionUtils(httpHandler);
			httpUtil.create(1, HttpUrl.getnearmachine, list);
			httpUtil.setState(100);
		}else{
			Log.e("aa", "nw");
			initWifi();
		}
//		initWifi();
	}
	
	public  ArrayList<HashMap<String, Object>> m_listMac = new ArrayList<HashMap<String, Object>>();
	
	private void initWifi(){
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);  		
		if (wifiManager.isWifiEnabled() == false)
		{
			return;
		}
		if(m_listMac.size() >= 1){
			return;
		}
		
		
		init();
	}
	
	
	private void addMacData(HashMap<String, Object> amap,String t){
		if(m_listMac.size() == 0){
			adpter.addData(amap);
			handler.sendEmptyMessage(101);
			return;
		}
		int index = 0;
		for(int i = 0 ; i < m_listMac.size() ; i++){
			HashMap<String, Object> data = m_listMac.get(i);
			if(!data.get("IP").toString().equals(amap.get("IP").toString())){
				index ++;
			}
		}
		if(index == list.size()){
			adpter.addData(amap);
		}else{
			handler.sendEmptyMessage(102);
		} 
	}
	
	
	private List<MACHINDATA> list;
	private void addMacData(HashMap<String, Object> amap){
		int index = 0;
		for(int i = 0 ; i < list.size() ; i++){
			MACHINDATA data = list.get(i);
			if(!data.M_ID.equals(amap.get("machineid")) && ConnHandler.isConn){
				index ++;
			}
		}
		if(index == list.size()){
			adpter.addData(amap);
		}else{
			handler.sendEmptyMessage(102);
		} 
		handler.sendEmptyMessage(101);
	}
	
	
	private Handler handler = new Handler(){
		public void handleMessage(final android.os.Message msg) {
			if(msg.what == 100){
			}else if(msg.what == 101){
//				if(connect != null)
//					connect.disconnect();
				
				adpter.notifyDataSetInvalidated();
			}else if(msg.what == 102){
				view.setVisibility(View.VISIBLE);
			}
		};
	};
	
	
	protected void onDestroy() {
		super.onDestroy();
//		if(connect != null)
//			connect.disconnect();
		XlwDevice.getInstance().SmartConfigStop();	
	};
	private void init(){
		XlwDevice.getInstance().SetXlwDeviceListener(new XlwDeviceListener()
		{
			@Override
			public boolean onSmartFound(String mac, String ip, String version, String capability)
			{
				Message msg = new Message();
				msg.obj = ip;
				msg.what = 100;
				handler.sendMessage(msg);
				Log.e("aa", ip+"-------------------");
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("IP", ip);
				addMacData(map,"");
				adpter.setState(1);
				return true;
			}
			@Override
			public boolean onSearchFound(String mac, String ip, String version, String capability, String ext) {
				
				return true;
			}

			@Override
			public void onStatusChange(String mac, int status)
			{
				Log.e("aa", "ip:----------------onStatusChange");
			}

			@Override
			public void onReceive(String mac, byte[] data, int length) 
			{
				String rsp = new String(data, 0, length);
				Log.e("aa", "ip:----------------onReceive");
			}
			
			@Override
			public void onSendError(String mac, int sn, int err) 
			{
				Log.e("aa", "ip:----------------onSendError");	
			}
		});		
		XlwDevice.getInstance().SetStatusCheck(3000);
		
		XlwDevice.getInstance().DeviceSearch();
	}
	
	@Override
	public void onBackPressed() {
		// TODO 自动生成的方法存根
		
		finish();
		super.onBackPressed();
	}
	
	
//	private Timer myTimer;  
//	 private void MySetTimer()
//	    {
//	    	myTimer = new Timer();
//	    	myTimer.schedule( 
//					new TimerTask() 
//					{
//						@Override
//						public void run() 
//						{	
////							if(m_listMac.size() >= 1){
////								return;
////							}else{
////								
////							}
//							try{
//								Thread.sleep(5000);
//								XlwDevice.getInstance().DeviceSearch();
//							}catch(Exception e){}
//						}
//					} , 0, 1000);   
//	    	
//	    }
	private HttpHandler httpHandler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			if(state == 100){
				try{
					OldData stateData = JsonParser.getInstance().revertJsonToObj(jObject,
							OldData.class);
					for(int i = 0 ; i < stateData.getData().length ; i++){
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("machineid", stateData.getData()[i]);
		            	addMacData(map);
					}
					adpter.setState(2);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
		};
		
		protected void failed(String jObject,int state){
			super.failed(jObject,-1);
		}
	};
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		
	}
}
