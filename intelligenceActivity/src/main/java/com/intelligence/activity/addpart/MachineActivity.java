package com.intelligence.activity.addpart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.MainActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.controller.ConnHandler;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.utils.Utils;
import com.intelligence.activity.db.DBNullException;
import com.intelligence.activity.db.Machinenu_DBhelperManager;
import com.intelligence.activity.db.Machinenu_DBhelperManager.MACHINDATA;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * 主界面
 * 
 * @author Administrator
 * 
 */
public class MachineActivity extends BaseActivity implements OnClickListener {

	private Button connBt;
	private TextView wifiName;

	private EditText wifiPass;

	DisplayImageOptions options;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_machine);

		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.shhs)
				// .showImageForEmptyUri(R.drawable.shhs)
				// .showImageOnFail(R.drawable.shhs)
				.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();

		wifiName = (TextView) findViewById(R.id.wifi_name_id);
		connBt = (Button) findViewById(R.id.wifi_conn_bt);
		wifiPass = (EditText) findViewById(R.id.wifi_pass_id);

		connBt.setOnClickListener(this);

		getIntentData();

		setTitle(this.getString(R.string.device_detail));
		setLeftOnClick(null);
		setRigter(isDele, this.getString(R.string.save));

		if (isDele && MACHINEID != null) {
			wifiPass.setText(WifiTools.getdevname(MACHINEID, this));
			// MACHINDATA data =
			// Machinenu_DBhelperManager.getInstance(this).inquiry(MACHINEID);
			// if(data == null){
			// addDBData(0);
			// wifiPass.setText("我的水壶");
			// }else{
			// wifiPass.setText(data.MNAME);
			// }
		}

		ImageView sbimage = (ImageView) findViewById(R.id.sbimage);
		String imageframe = MACHINEID.substring(0, 6);
		ImageLoader.getInstance().displayImage(
				HttpUrl.IMGURL + imageframe + ".png", sbimage, options, null);
		// ImageLoader.getInstance().displayImage(HttpUrl.IMGURL+imageframe+".png",
		// sbimage);
	}

	@Override
	protected void setRightOnClick() {

		addDBData(0);
		MainActivity.iswifi = true;
		Intent intent = new Intent(MachineActivity.this, MainActivity.class);
		intent.putExtra("updata", true);
		startActivity(intent);
		finish();
	}

	protected void onDestroy() {
		super.onDestroy();
	};

	private String MACHINEID, IP;
	private boolean isDele;

	private void getIntentData() {
		if (getIntent() != null) {
			MACHINEID = getIntent().getStringExtra("MACHINEID");

			isDele = getIntent().getBooleanExtra("isDele", false);
			wifiName.setText(MACHINEID);
			if (isDele) {
				connBt.setText(this.getString(R.string.delete_device));
				connBt.setTextColor(getResources().getColor(R.color.white));
				connBt.setBackgroundResource(R.drawable.deldev);
			} else {
				IP = getIntent().getStringExtra("IP");
			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if (v.getId() == R.id.wifi_conn_bt) {
			if (!isDele) {
				if (!ConnHandler.isConn) {
					addDBData(0);
					MainActivity.iswifi = true;
					Intent intent = new Intent(MachineActivity.this,
							MainActivity.class);
					intent.putExtra("iswifi", false);
					startActivity(intent);
					finish();
					return;
				}
				List<NameValuePair> data = new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("appid", HttpUrl.APP_ID));
				data.add(new BasicNameValuePair("machineid", MACHINEID));
				HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler);
				httpUtil.create(1, HttpUrl.BIND, data);
				httpUtil.setState(100);
			} else {
				if (!ConnHandler.isConn) {
					addDBData(1);
					MainActivity.iswifi = true;
					Intent intent = new Intent(MachineActivity.this,
							MainActivity.class);
					intent.putExtra("iswifi", false);
					startActivity(intent);
					finish();
					return;
				}
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("appid", HttpUrl.APP_ID);
				map.put("machineid", MACHINEID);
				HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler);
				httpUtil.create(0, Utils.getUrl(map, HttpUrl.DELE), null);
				httpUtil.setState(101);
			}
		}
	}

	/**
	 * 更改或者添加设备信息
	 * 
	 * @param state
	 */
	private void addDBData(int state) {

		WifiTools.savedevname(wifiPass.getText().toString(), MACHINEID, this);

		boolean b = false;
		try {
			b = Machinenu_DBhelperManager.getInstance(this).inquiryIsExist(
					MACHINEID);
		} catch (Exception e) {
			// TODO: handle exception
		}
		MACHINDATA data = new MACHINDATA();
		try {
			data.M_ID = MACHINEID;
			data.MNAME = wifiPass.getText().toString();
			data.IS_DELE = state;
			data.MAC = getSsid(this);
			if (IP == null)
				IP = "12";
			data.M_IP = IP;
			data.IS_UPDATA = 1;
			if (b) {
				Machinenu_DBhelperManager.getInstance(this).update(data);
			} else {
				Machinenu_DBhelperManager.getInstance(this).insert(data);
			}
		} catch (DBNullException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public static boolean isEdited=true;
	private HttpHandler handler = new HttpHandler(this) {
		protected void succeed(String jObject, int state) {
			super.succeed(jObject, state);

			// =======测试
			// String devs = MACHINEID.substring(0, 2);
			// if (devs.equals("01")) {
			// Intent intent = new
			// Intent(MachineActivity.this,kettleActivity.class);
			// intent.putExtra("MACHINEID", MACHINEID);
			// startActivity(intent);
			// }else{
			// Intent intent = new
			// Intent(MachineActivity.this,HumActivity.class);
			// intent.putExtra("MACHINEID", MACHINEID);
			// startActivity(intent);
			// }
			// finish();
			// ============

			// ========正式
			if (state == 100) {
				addDBData(0);
				Toast.makeText(MachineActivity.this, MachineActivity.this.getString(R.string.add_device_success), Toast.LENGTH_LONG)
						.show();
			} else {
				addDBData(1);
				Toast.makeText(MachineActivity.this, MachineActivity.this.getString(R.string.delete_device_success), Toast.LENGTH_LONG)
						.show();
			}
			
			isEdited=true;
			// setResult(2);
			// finish();
			MainActivity.updata = true;
			Intent intent = new Intent(MachineActivity.this, MainActivity.class);
			intent.putExtra("updata", true);
			startActivity(intent);
			finish();
			// ========正式
		};

		protected void failed(String jObject, int state) {
			super.failed(jObject, -1);
			Toast.makeText(MachineActivity.this,MachineActivity.this.getString(R.string.add_device_failed), Toast.LENGTH_LONG)
					.show();
		}
	};

	public static String getSsid(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled() == false) {
			return "-1";
		}

		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		String ssidNow = wifiInfo.getSSID();
		Log.e("aa", "ssidNow----------" + ssidNow);
		return ssidNow;
	}

}
