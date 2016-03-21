package com.intelligence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MyService extends Service
{

	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public RequestQueue volley;
	
	AlarmManager mAlarmManager = null;
	PendingIntent mPendingIntent = null;
	String Tag="service";
	@Override
	public void onCreate()
	{
		Log.e(Tag,"service start");
		super.onCreate();
		//start the service through alarm repeatly
	    //Intent intent = new Intent(getApplicationContext(), MyService.class);
		//mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		//mPendingIntent = PendingIntent.getService(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		//long now = System.currentTimeMillis();
		//mAlarmManager.setInexactRepeating(AlarmManager.RTC, now, 60000, mPendingIntent);
		//maybe this is an alarm, get

		volley= Volley.newRequestQueue(getApplicationContext());
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		initLocation();
		mLocationClient.start();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
//		Toast.makeText(getApplicationContext(), "Callback Successed!", Toast.LENGTH_LONG).show();
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onDestroy() 
	{
		
		super.onDestroy();
	}

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		int span=60*1000;
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		//option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		//option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(true);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		//option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			//Toast.makeText(AttendanceHistory.this, "get location success", Toast.LENGTH_SHORT);
			StringBuffer sb = new StringBuffer(256);
			sb.append(location.getLatitude() + ":" + location.getLongitude());
			Log.e("service update",sb.toString());
			//String url=HttpUrl.PUT_ATTENDANCE_LOCATION;
			String url=HttpUrl.updatelocation;
			//url+="/machineid/"+ data.getMachineid();
			url+="/appid/"+ HttpUrl.APP_ID;
			//url+="/distance/"+ 50;
			url+="/longitude/"+ location.getLongitude();
			url+="/latitude/"+ "" + location.getLatitude();
			url+="/type/"+ "1";
			//Log.e("get location success", "success" + location.getLatitude() + ":" + location.getLongitude());
			//sendRequestWithHttpClient(location.getLatitude(), location.getLongitude());
			volley.add(new LocationPostResquest(url,location.getLatitude(), location.getLongitude(), new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					//just get an try
					Log.e("MyServiceLocation",response);
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					error.printStackTrace();
				}
			}));
		}
	}
	class LocationPostResquest extends StringRequest {
		double latitude;
		double longitude;
		public LocationPostResquest(String url,double l1,double l2,
									Response.Listener<String> listener, Response.ErrorListener errorListener) {

			super(Method.POST,url, listener, errorListener);
			Log.e("url",url);
			latitude=l1;
			longitude=l2;
		}
	}

}
