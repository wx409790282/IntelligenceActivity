package com.intelligence.activity.feedback;

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
import com.intelligence.activity.humidifier.MyOnClickListener;
import com.intelligence.activity.humidifier.NavigationBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class YJListActivity extends Activity {
	
	List<FaceBack> msglist = new ArrayList<FaceBack>();
	YJListAdapter yjListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_yjlist);
		
		NavigationBar bar = (NavigationBar) findViewById(R.id.navigationbar);
		bar.setTitle(this.getString(R.string.feedback_detail));
		bar.showLeftBtn(true);
		bar.setLeftOnClickListener(new MyOnClickListener() {  
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				finish();   
			}
		});

		
		ListView listView = (ListView)findViewById(R.id.listview);
		yjListAdapter = new YJListAdapter(this, msglist); 
		listView.setAdapter(yjListAdapter);
		
		loadlistmsg();
	}

	public void loadlistmsg(){
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
		list.add(new BasicNameValuePair("page","1"));
		list.add(new BasicNameValuePair("pagesize","50"));
		
		httpHandler.setProcessing(false);
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(httpHandler);
		httpUtil.create(1, HttpUrl.FEEDBACK_LIST,list);
		httpUtil.setState(100);
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
			
			
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.getJSONObject(i);
						FaceBack humWork = new FaceBack();
						humWork.mapToHCustom(object);
						msglist.add(humWork);
					}
					yjListAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
			
		};
		
		protected void failed(String jObject,int state){
			super.failed(jObject,-1);
			
			
			
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.yjlist, menu);
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
