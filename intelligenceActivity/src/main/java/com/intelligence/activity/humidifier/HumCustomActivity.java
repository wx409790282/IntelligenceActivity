package com.intelligence.activity.humidifier;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.humidifier.SwitchButton.OnChangeListener;
import com.intelligence.activity.view.ListDialog;
import com.gc.materialdesign.widgets.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Switch;
import android.widget.TextView;

public class HumCustomActivity extends BaseActivity {

	TextView sdtxt1,sdtxt2,sdtxt3;
	TextView cityname,citytmp,cityhum;
	SwitchButton sbtn1,sbtn3,sbtn4,sbtn5,sbtn2;
	List<HashMap<String, Object>> mDataList = new ArrayList<HashMap<String,Object>>();
	HumCustomBean custom =new HumCustomBean();;
	String machineId="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hum_custom);

		getData();
		initView();

		setTitle(this.getString(R.string.hum_custom_name));
		setLeftOnClick(null);
		setRigter(true, this.getString(R.string.save));//and i will set right
		setRightOnClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("appid", HttpUrl.APP_ID));
				list.add(new BasicNameValuePair("machineid", machineId));
				list.add(new BasicNameValuePair("drymode", custom.g_min + ""));
				list.add(new BasicNameValuePair("wetmode", custom.s_max + ""));

				list.add(new BasicNameValuePair("enableai", 0 + ""));
				list.add(new BasicNameValuePair("enableusernearstart", custom.nearbyOpen + ""));
				list.add(new BasicNameValuePair("enableuserfarstop", custom.farawayClose + ""));

				list.add(new BasicNameValuePair("startandstopremind", custom.startOrStopRemind + ""));
				list.add(new BasicNameValuePair("nowaterremind", custom.noWaterremind + ""));
				list.add(new BasicNameValuePair("tooDryRemind", custom.tooDryRemind + ""));
				startHandler.setProcessing(false);
				HttpConnectionUtils httpUtil = new HttpConnectionUtils(startHandler);
				httpUtil.create(1, HttpUrl.H_SCONFIG, list);
				httpUtil.setState(101);
			}
		});

		sdtxt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDataList.clear();
				for (int i = 0; i < 11; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					float sac = (i * 5 + 50) / 100.0f;
					map.put("txt", (int) (sac * custom.z_max - 20) + "-" + (int) (sac * custom.z_max) + "%             " + (i * 5 + 50));
					mDataList.add(map);
				}
				showlist(100);
			}
		});

		sdtxt3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDataList.clear();
				for (int i = 0; i < 11; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					float sac = (i * 5 + 100) / 100.0f;
					map.put("txt", (int) (sac * custom.z_max - 20) + "-" + (int) (sac * custom.z_max) + "%             " + (i * 5 + 100));
					mDataList.add(map);
				}
				showlist(101);
			}
		});



	}

	private void initView() {
		cityname = (TextView) findViewById(R.id.cityname);
		citytmp = (TextView) findViewById(R.id.citytmp);
		cityhum = (TextView) findViewById(R.id.cityhum);

		sbtn1 = (SwitchButton) findViewById(R.id.sbtn1);
		sbtn2 = (SwitchButton) findViewById(R.id.sbtn2);
		sbtn3 = (SwitchButton) findViewById(R.id.sbtn3);
		sbtn4 = (SwitchButton) findViewById(R.id.sbtn4);
		sbtn5 = (SwitchButton) findViewById(R.id.sbtn5);

		sbtn1.setOnChangeListener(new OnChangeListener() {

			@Override
			public void onChange(SwitchButton sb, boolean state) {
				// TODO Auto-generated method stub
				if (state) {
					custom.nearbyOpen=true;
				}else{
					custom.nearbyOpen=false;
				}
			}
		});

		sbtn2.setOnChangeListener(new OnChangeListener() {

			@Override
			public void onChange(SwitchButton sb, boolean state) {
				// TODO Auto-generated method stub
				if (state) {
					custom.farawayClose=true;
				}else{
					custom.farawayClose=false;
				}
			}
		});

		sbtn3.setOnChangeListener(new OnChangeListener() {

			@Override
			public void onChange(SwitchButton sb, boolean state) {
				// TODO Auto-generated method stub
				if (state) {
					custom.startOrStopRemind=true;
				}else{
					custom.startOrStopRemind=false;
				}
			}
		});

		sbtn4.setOnChangeListener(new OnChangeListener() {

			@Override
			public void onChange(SwitchButton sb, boolean state) {
				// TODO Auto-generated method stub
				if (state) {
					custom.noWaterremind = true;
				} else {
					custom.noWaterremind=false;
				}
			}
		});

		sbtn5.setOnChangeListener(new OnChangeListener() {

			@Override
			public void onChange(SwitchButton sb, boolean state) {
				// TODO Auto-generated method stub
				if (state) {
					custom.tooDryRemind = true;
				} else {
					custom.tooDryRemind = false;
				}
			}
		});

		sdtxt1 = (TextView) findViewById(R.id.sdtxt1);
		sdtxt2 = (TextView) findViewById(R.id.sdtxt2);
		sdtxt3 = (TextView) findViewById(R.id.sdtxt3);
	}

	public void state(){

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
		list.add(new BasicNameValuePair("machineid",machineId));

		startHandler.setProcessing(false);
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(startHandler);
		httpUtil.create(1, HttpUrl.H_CONFIG, list);
		httpUtil.setState(100);	

	}

	private void getData(){
		Intent intent = getIntent();
		if(intent != null){
			machineId = intent.getStringExtra("devId");
		}
	}
	private HttpHandler startHandler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			if(state == 100){
				try{
					System.out.println(jObject);
					JSONObject jsonObject = new JSONObject(jObject);
					if (jsonObject.getInt("status") == 1) {
						custom.mapToHCustom(jsonObject.getJSONObject("data"));
						refview();
					}
					//							mapToHCustom
				}catch(Exception e){
					e.printStackTrace();
				}
			}else if(state == 101){
				JSONObject jsonObject;
				try {

					jsonObject = new JSONObject(jObject);
					if (jsonObject.getInt("status") == 1) {
						final Dialog dialog=new Dialog(HumCustomActivity.this,HumCustomActivity.this.getString(R.string.hint),HumCustomActivity.this.getString(R.string.save_success));
						dialog.setOnAcceptButtonClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						dialog.show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};

		protected void failed(String jObject,int state){
			super.failed(jObject,-1);
		}
	};

	public void showlist(int tag){
		ListDialog dialog = new ListDialog(this, handler);
		dialog.returntag = true;
		dialog.setListData(mDataList, tag);
		dialog.show();
	}

	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			super.handleMessage(msg);

			int index = Integer.parseInt(msg.obj.toString());

			if(msg.what == 100){
//				float sac = ()/100.0f;
				custom.g_min = index*5 + 50;
			}else if(msg.what == 101){
//				float sac = (index*5 + 100)/100.0f;
				custom.s_max = index*5 + 100;
			}
			refview();
		}
	};


	public void refview(){
		sdtxt1.setText(custom.z_max*custom.g_min/100-20+"-"+custom.z_max*custom.g_min/100+"%");
		sdtxt2.setText(custom.z_min + "-" + custom.z_max + "%");
		sdtxt3.setText(custom.z_max * custom.s_max / 100 - 20 + "-" + custom.z_max * custom.s_max / 100 + "%");

		custom.citytemptop = custom.citytemptop.replace("C", "℃");
		custom.citytempbottom = custom.citytempbottom.replace("C", "℃");
		
		cityname.setText(custom.cityname);
		citytmp.setText(custom.citytemptop + "-" + custom.citytempbottom);
		cityhum.setText(custom.cityhumidity);

		if (custom.nearbyOpen) {
			sbtn1.setmSwitchOn(true);
		}else{
			sbtn1.setmSwitchOn(false);
		}

		if (custom.farawayClose) {
			sbtn2.setmSwitchOn(true);
		}else{
			sbtn2.setmSwitchOn(false);
		}

		if (custom.startOrStopRemind) {
			sbtn3.setmSwitchOn(true);
		}else{
			sbtn3.setmSwitchOn(false);
		}

		if (custom.noWaterremind) {
			sbtn4.setmSwitchOn(true);
		}else{
			sbtn4.setmSwitchOn(false);
		}

		if (custom.tooDryRemind) {
			sbtn5.setmSwitchOn(true);
		}else{
			sbtn5.setmSwitchOn(false);
		}
	}

}
