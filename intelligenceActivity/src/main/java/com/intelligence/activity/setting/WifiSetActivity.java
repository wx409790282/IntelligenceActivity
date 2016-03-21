package com.intelligence.activity.setting;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.intelligence.activity.R;
import com.intelligence.activity.adpter.WifiSetAdapter;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.humidifier.MyOnClickListener;
import com.intelligence.activity.humidifier.NavigationBar;

public class WifiSetActivity extends Activity {

	WifiSetAdapter wifiSetAdapter;
	JSONArray array;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_wifi_set);
		
		
		NavigationBar bar = (NavigationBar) findViewById(R.id.navigationbar);
		bar.setTitle(this.getString(R.string.wifi_setting));
		bar.showLeftBtn(true);
		bar.setLeftOnClickListener(new MyOnClickListener() {  
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				finish();   
			}
		});
		
		array = WifiTools.getwifilist(this);
//		if (array.length() == 0) {
//			for (int i = 0; i < 10; i++) {
//				JSONObject object = new JSONObject();
//				try {
//					object.put("wifiname", "00000"+i);
//					object.put("wifipwd", "123456");
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				array.put(object);
//			}
//		}
		
		
		ListView listView = (ListView)findViewById(R.id.listview);
		
		wifiSetAdapter = new WifiSetAdapter(this, array);
		listView.setAdapter(wifiSetAdapter);
		listView.setOnItemClickListener(wifiSetAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wifi_set, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
