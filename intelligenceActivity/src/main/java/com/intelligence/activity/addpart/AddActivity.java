package com.intelligence.activity.addpart;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.MainActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.controller.ConnHandler;
import com.intelligence.activity.db.DBNullException;
import com.intelligence.activity.db.DBhelperManager;
import com.intelligence.activity.db.DBhelperManager.ZDYData;
import com.intelligence.activity.db.UserInfoSharedPreferences;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
/**
 * 添加设备界面
 * @author Administrator
 *
 */
public class AddActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout add_layout,add_layout1;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		getData();
		
		add_layout = (RelativeLayout)findViewById(R.id.add_layout_id);
		add_layout1 = (RelativeLayout)findViewById(R.id.add_layout1_id);
		add_layout.setOnClickListener(this);
		add_layout1.setOnClickListener(this);
		
		int state = UserInfoSharedPreferences.getInstance().getUserInfoIntKey(this, UserInfoSharedPreferences.IS_ONE);
		if(state == 0){//means it is the first running,and we need to register user by it imei id;
			//and write imei+0 as it's id to shareperaferance
			initSystemData();
			String imei = UserInfoSharedPreferences.getInstance().getUserInfoStringKey(this, UserInfoSharedPreferences.USER_IMEI);
			if(imei == null){
				imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
						.getDeviceId() + "0";
				UserInfoSharedPreferences.getInstance().setUserImei(this, imei);
			}
			HttpUrl.APP_ID = imei;
			List<NameValuePair> data = new ArrayList<NameValuePair>();
			data.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
			HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler);
			httpUtil.create(1, HttpUrl.REQ, data);
			UserInfoSharedPreferences.getInstance().setUserIsOne(this, 1);
			
		}else{
			HttpUrl.APP_ID = UserInfoSharedPreferences.getInstance().getUserInfoStringKey(this, UserInfoSharedPreferences.USER_IMEI);
//			HttpUrl.APP_ID = "8601730116061040";
			if(!b){
				Intent intent = new Intent(this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			setTitle(this.getString(R.string.add_device));
			setLeftOnClick(null);
			setRigter(false, this.getString(R.string.save));
			
		}
		
		
	}
	boolean b = false;//b stand for if there is an intent?
	private void getData(){
		Intent intent  = getIntent();
		if(intent != null){
			b = intent.getBooleanExtra("b", false);
		}
	}
	
	
	/**
	 * 第一次初始化添加的系统级自定义模式
	 * //set model to database
	 */
	private void initSystemData(){
		int isOne = UserInfoSharedPreferences.getInstance().getUserInfoIntKey(this, UserInfoSharedPreferences.IS_ONE);
		if(isOne == 0){

			try {
				ZDYData data = new ZDYData();
				data.ZDY_ID = (System.currentTimeMillis() + "");
				data.ZDY_ISDE = 1;
				data.ZDY_ISOPEN = 1;
				data.ZDY_NAME = "煮沸";
				data.ZDY_SW = "100°c";
				data.ZDY_TIME = "0";
				data.ZDY_TIME1 = "0";
				data.ZDY_ISZF = "0";
				DBhelperManager.getInstance(this).insert(data);
				
				
				
				ZDYData data1 = new ZDYData();
				data1.ZDY_ID = (System.currentTimeMillis() + "");
				data1.ZDY_ISDE = 0;
				data1.ZDY_ISOPEN = 1;
				data1.ZDY_NAME = "咖啡";
				data1.ZDY_SW = "85°c";
				data1.ZDY_TIME = "0";
				data1.ZDY_TIME1 = "0";
				data1.ZDY_ISZF = "0";
				DBhelperManager.getInstance(this).insert(data1);
				
				
				ZDYData data2 = new ZDYData();
				data2.ZDY_ID = (System.currentTimeMillis() + "");
				data2.ZDY_ISDE = 0;
				data2.ZDY_ISOPEN = 1;
				data2.ZDY_NAME = "茶";
				data2.ZDY_SW = "80°c";
				data2.ZDY_TIME = "0";
				data2.ZDY_TIME1 = "0";
				data2.ZDY_ISZF = "0";
				DBhelperManager.getInstance(this).insert(data2);
				
				
				ZDYData data3 = new ZDYData();
				data3.ZDY_ID = (System.currentTimeMillis() + "");
				data3.ZDY_ISDE = 0;
				data3.ZDY_ISOPEN = 1;
				data3.ZDY_NAME = "牛奶";
				data3.ZDY_SW = "45°c";
				data3.ZDY_TIME = "0";
				data3.ZDY_TIME1 = "0";
				data3.ZDY_ISZF = "0";
				DBhelperManager.getInstance(this).insert(data3);
			} catch (DBNullException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	private HttpHandler handler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,-1);
			UserInfoSharedPreferences.getInstance().setUserIsOne(AddActivity.this, 1);
			Intent intent = new Intent(AddActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		};
		
		protected void failed(String jObject,int state){
			super.failed(jObject, state);
			UserInfoSharedPreferences.getInstance().setUserIsOne(AddActivity.this, 1);
			Intent intent = new Intent(AddActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		
		protected void connEerr() {
			super.connEerr();
			ConnHandler.isConn = false;
			UserInfoSharedPreferences.getInstance().setUserIsOne(AddActivity.this, 1);
			Intent intent = new Intent(AddActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	};
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v.getId() == R.id.add_layout_id){
			Intent intent = new Intent(this,AddNewInfoActivity.class);
			startActivity(intent);
			finish();
		}else if(v.getId() == R.id.add_layout1_id){
			Intent intent = new Intent(this,AddOldInfoActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
