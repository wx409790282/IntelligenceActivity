package com.intelligence.activity;

import java.util.HashMap;

import android.os.Bundle;
import android.widget.TextView;

import com.intelligence.activity.adpter.HistoricalRecordsAdpter;
import com.intelligence.activity.utils.Utils;
import com.intelligence.activity.data.StateData;
import com.intelligence.activity.data.historicalData;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.json.JsonParser;
import com.intelligence.activity.view.JBListView;
/**
 * 历史记录界面
 * @author Administrator
 *
 */
public class HistoricalRecordsActivity extends BaseActivity{
	private JBListView  listview;
	
	private HistoricalRecordsAdpter adpter;
	
	private TextView state01,state02,state03;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historicalrecords);
		getIntentData();
		listview = (JBListView)findViewById(R.id.girdview_id);
		
		
		setTitle(this.getString(R.string.history_record));
		setLeftOnClick(null);
		setRigter(false, "");
		state01 = (TextView)findViewById(R.id.state_01);
		state02 = (TextView)findViewById(R.id.state_02);
		state03 = (TextView)findViewById(R.id.state_03);
		getState();
	}
	private int index = 1;
	private int MaxLength = 50;
	private void getList(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("appid", HttpUrl.APP_ID);
		map.put("machineid", MACHINEID);
		map.put("page", index);
		map.put("pagesize", MaxLength);
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler);
		httpUtil.create(0, Utils.getUrl(map, HttpUrl.GETACTIONLOG), null);
		httpUtil.setState(101);
	}
	
	private void getState(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("appid", HttpUrl.APP_ID);
		map.put("machineid", MACHINEID);
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler);
		httpUtil.create(0, Utils.getUrl(map, HttpUrl.STATE), null);
		httpUtil.setState(100);
	}
	/**
	 * 初始化列表数据
	 */
	private StateData data;
	private HttpHandler handler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			if(state  == 100){
				data = JsonParser.getInstance().revertJsonToObj(jObject,
						StateData.class);
				state01.setText(data.getData().getNum());
				state02.setText(data.getData().getLevel()+"L");
				state03.setText(data.getData().getTemp()+"°C");
				getList();
			}else{
				historicalData data = JsonParser.getInstance().revertJsonToObj(jObject,
						historicalData.class);
				if(data.getData().size() > 0){
					adpter = new HistoricalRecordsAdpter(HistoricalRecordsActivity.this, data.getData());
					listview.setAdapter(adpter);
				}
				
			}
			
		};
		
		protected void failed(String jObject,int state){
			super.failed(jObject,state);
		}
	};
	
	private String MACHINEID;
	private void getIntentData(){
		if(getIntent() != null){
			MACHINEID = getIntent().getStringExtra("MACHINEID");
		}
	}
}
