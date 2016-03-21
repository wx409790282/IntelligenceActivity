package com.intelligence.activity.humidifier;

import java.util.ArrayList;
import java.util.List;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

public class HumTimingActivity extends BaseActivity {

	String devId;
	List<HTiming> timinglist = new ArrayList<HTiming>();
	HumTimingAdapter timingAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hum_timing);

		devId = getIntent().getStringExtra("devId");

		setTitle(this.getString(R.string.hum_timing_name));
		setLeftOnClick(null);
		setRigter(true, this.getString(R.string.add));//and i will set right
		setRightOnClick(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HumTimingActivity.this, HumTimingMakeActivity.class);
				intent.putExtra("devId", devId);
				startActivity(intent);
			}
		});
		
		String sql = "select * from timing where machineid= '"+devId+"'";
		Cursor cursor = WifiTools.db.rawQuery(sql,null);
		while (cursor.moveToNext()) {
			HTiming timing = new HTiming();
			timing.setValue(cursor);
			timinglist.add(timing);
		}

		ListView listView = (ListView)findViewById(R.id.hum_timing_listview);
		
		timingAdapter = new HumTimingAdapter(this, timinglist);
		listView.setAdapter(timingAdapter);
		listView.setOnItemClickListener(timingAdapter);

	}

	public void sendmsg(int index){
		HTiming hTiming = timinglist.get(index);
		if (hTiming.t_open == 1) {
			hTiming.changeOrderId();
			
			httpHandler.setProcessing(false);
			HttpConnectionUtils httpUtil = new HttpConnectionUtils(httpHandler);
			httpUtil.create(1, HttpUrl.H_ORDER, hTiming.getHHttpMsgBySelect(devId));
			httpUtil.setState(index);
		}else{
			
			httpHandler.setProcessing(false);
			HttpConnectionUtils httpUtil = new HttpConnectionUtils(httpHandler);
			httpUtil.create(1, HttpUrl.H_CORDER, hTiming.cancleOrder(devId));
			httpUtil.setState(index);
		}
		
		
		
	}
	private HttpHandler httpHandler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			HTiming hTiming = timinglist.get(state);
			hTiming.sqlupdate();
		};
		
		protected void failed(String jObject,int state){
			super.failed(jObject,-1);
			
			HTiming hTiming = timinglist.get(state);
			if (hTiming.t_open == 1) {
				hTiming.t_open = 0;
			}else{
				hTiming.t_open = 1;
			}
			hTiming.sqlupdate();
			
			timingAdapter.notifyDataSetChanged();
			
		}
	};
	

	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  

		if (data == null)  
			return;  

		if(resultCode == RESULT_OK){
			Bundle b = data.getExtras();
			HTiming _tTiming = (HTiming) b.getSerializable("timing");
			
			boolean issame = false;
			for (HTiming hTiming : timinglist) {
				if (_tTiming.getT_id() == hTiming.getT_id()) {
					int index = timinglist.indexOf(hTiming);
					timinglist.remove(index);
					timinglist.add(index, _tTiming);
//					hTiming = _tTiming;
					issame = true;
					break;
				}
			}
			if (issame == false) {
				timinglist.add(_tTiming);
			}
			
			timingAdapter.notifyDataSetChanged();
		}



		super.onActivityResult(requestCode, resultCode, data);  
	} 
}
