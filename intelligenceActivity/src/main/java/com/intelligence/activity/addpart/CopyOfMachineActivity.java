package com.intelligence.activity.addpart;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.MainActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.controller.ConnHandler;
import com.intelligence.activity.controller.StateHandler;
import com.intelligence.activity.data.MachineStateData.MachineStateItemData;
import com.intelligence.activity.db.DBNullException;
import com.intelligence.activity.db.Machinenu_DBhelperManager;
import com.intelligence.activity.db.Machinenu_DBhelperManager.MACHINDATA;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;

/**
 * 设备编辑界面
 * @author Administrator
 *
 */
public class CopyOfMachineActivity extends BaseActivity implements OnClickListener{
	
	private Button connBt;
	private TextView wifiName;
	
	private EditText wifiPass;
	
	private StateHandler stateHandler = null;
	
	private int state;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_machine);
		
		wifiName = (TextView)findViewById(R.id.wifi_name_id);
		connBt = (Button)findViewById(R.id.wifi_conn_bt);
		wifiPass = (EditText)findViewById(R.id.wifi_pass_id);
		
		connBt.setOnClickListener(this);
		
		getIntentData();
		
        setTitle(this.getString(R.string.device_detail));
		setLeftOnClick(null);
		setRigter(false, this.getString(R.string.save));
		
		MACHINDATA data = Machinenu_DBhelperManager.getInstance(this).inquiryIP(IP);
		if(data == null){
			wifiPass.setText(this.getString(R.string.my_kettle));
			stateHandler = new StateHandler(this,IP);
			stateHandler.setTCPhandler(handler);
			stateHandler.sendEmptyMessage(1001);
			wifiName.setText(this.getString(R.string.connecting));
		}else{
			MACHINEID = data.M_ID;
			wifiName.setText(MACHINEID);
		}
	}
	

	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			MachineStateItemData map = (MachineStateItemData)msg.obj;
			MACHINEID = map.getId();
			wifiName.setText(MACHINEID);
		};
	};
	
	
	
	private HttpHandler handler1 = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			if(state  == 100){
				addDBData(0);
			}
			
			//=======测试
//			String devs = MACHINEID.substring(0, 2);
//			if (devs.equals("01")) {
//				Intent intent = new Intent(CopyOfMachineActivity.this,kettleActivity.class);
//				intent.putExtra("MACHINEID", MACHINEID);
//				startActivity(intent);
//			}else{
//				Intent intent = new Intent(CopyOfMachineActivity.this,HumActivity.class);
//				intent.putExtra("MACHINEID", MACHINEID);
//				startActivity(intent);
//			}
			//============
			
			//========正式
			Intent intent = new Intent(CopyOfMachineActivity.this,MainActivity.class);
			intent.putExtra("updata", true);
			startActivity(intent);
			finish();
			//========正式			
		};
		
		protected void failed(String jObject,int state){
			super.failed(jObject,-1);
		}
	};
	
	
	
	
	protected void onDestroy() {
//		if(connect != null)
//		connect.disconnect();
		if(stateHandler != null)
			stateHandler.destoy();
		super.onDestroy();
	};
	
	private String IP;
	private void getIntentData(){
		if(getIntent() != null ){
			IP = getIntent().getStringExtra("IP");
			state = getIntent().getIntExtra("state", 0);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v.getId() == R.id.wifi_conn_bt){
			if(state == 1){
				if(MACHINEID == null)
					return;
				if(!ConnHandler.isConn){
					addDBData(0);
					MainActivity.iswifi = true;
					Intent intent = new Intent(CopyOfMachineActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
				List<NameValuePair> data = new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
				data.add(new BasicNameValuePair("machineid",MACHINEID));
				HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler1);
				httpUtil.create(1, HttpUrl.BIND, data);
				httpUtil.setState(100);
			}else{
				addDBData(0);
				MainActivity.iswifi = true;
				Intent intent = new Intent(CopyOfMachineActivity.this,MainActivity.class);
				intent.putExtra("iswifi", false);
				startActivity(intent);
				finish();
			}
			
		}
	}
	private String MACHINEID;
	private void addDBData(int state){
		boolean b = false;
		try {
			b = Machinenu_DBhelperManager.getInstance(this).inquiryIsExist(MACHINEID);
		} catch (Exception e) {
			// TODO: handle exception
		}
		MACHINDATA data = new MACHINDATA();
		try {
			data.M_ID = MACHINEID;
			data.MNAME = wifiPass.getText().toString();
			data.IS_DELE = state;
			data.MAC=MachineActivity.getSsid(this);;
			if(IP == null)
				IP = "12";
			data.M_IP = IP;
			data.IS_UPDATA = 0;
			if(b){
				Machinenu_DBhelperManager.getInstance(this).update(data);
			}else{
				Machinenu_DBhelperManager.getInstance(this).insert(data);
			}
		} catch (DBNullException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
