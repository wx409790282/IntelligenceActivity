package com.intelligence.activity.humidifier;



import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.intelligence.activity.R;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;



public class HumHistoryActivity extends Activity {

	String devId = null;
	HumStaHeadView humStaHeadView;
	List<HumWork> msglist = new ArrayList<HumWork>();
	HumStaAdapter humStaAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hum_sta);

		devId = getIntent().getStringExtra("devId");
		
		NavigationBar bar = (NavigationBar) findViewById(R.id.navigationbar);
		bar.setTitle("使用记录");
		bar.showLeftBtn(true);
		bar.setLeftOnClickListener(new MyOnClickListener() {  
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				finish();       
			}
		});

		ListView listView = (ListView)findViewById(R.id.listview);

//		List<Object> list = new ArrayList<Object>();
//		for (int i = 0; i < 20; i++) {
//			list.add(i+"");
//		}
		humStaAdapter = new HumStaAdapter(this, msglist);
		listView.setAdapter(humStaAdapter);
		
		
		
		humStaHeadView = new HumStaHeadView(this);
		listView.addHeaderView(humStaHeadView);
		
		loadmsg();
//		listView.setOnItemLongClickListener(new ListItemLongClickListener(this, msglist));
//		listView.setOnItemClickListener(new ListCilckListener(this, msglist));
	}

	public void loadmsg(){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
		list.add(new BasicNameValuePair("machineid",devId));
		
		
		httpHandler.setProcessing(false);
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(httpHandler);
		httpUtil.create(1, HttpUrl.H_STAT,list);
		httpUtil.setState(100);
	}
	
	public void loadlistmsg(){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
		list.add(new BasicNameValuePair("machineid",devId));
		list.add(new BasicNameValuePair("page","1"));
		list.add(new BasicNameValuePair("pagesize","50"));
		
		httpHandler.setProcessing(false);
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(httpHandler);
		httpUtil.create(1, HttpUrl.H_STAT_LIST,list);
		httpUtil.setState(101);
	}
	
	private HttpHandler httpHandler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			
			JSONObject jsonObject = null;
			try {
				jsonObject =  new JSONObject(jObject);
				System.out.println(jsonObject);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (state == 100) {
				try {
					humStaHeadView.setvalue(jsonObject.getJSONObject("data"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				loadlistmsg();
			}else{
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						HumWork humWork = new HumWork();
						humWork.setValue(object);
						msglist.add(humWork);
					}
					humStaAdapter.notifyDataSetChanged();
//					System.out.println(msglist);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//		getMenuInflater().inflate(R.menu.hum_sta, menu);
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
