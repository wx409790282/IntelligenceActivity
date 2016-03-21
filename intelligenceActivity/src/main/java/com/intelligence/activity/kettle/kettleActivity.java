package com.intelligence.activity.kettle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.HistoricalRecordsActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.controller.ConnHandler;
import com.intelligence.activity.controller.StateHandler;
import com.intelligence.activity.data.Constants;
import com.intelligence.activity.data.Kettle;
import com.intelligence.activity.data.KettleState;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.utils.Utils;
import com.intelligence.activity.db.DBhelperManager;
import com.intelligence.activity.db.DBhelperManager.ZDYData;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.json.JsonParser;
import com.intelligence.activity.view.DateUtil;
import com.intelligence.activity.view.RootSelectView;
import com.intelligence.activity.view.PromptDialog.DialogListener;

/**
 * 水壶界面
 * @author Administrator
 *
 */
@SuppressLint("ResourceAsColor")
public class kettleActivity extends BaseActivity implements OnClickListener{
	
	private ImageView zdy_iv,nz_iv,ivLine,ms_img1,ms_kuang_image;
	private TextView sh_txt_01,sh_txt_02,sh_txt_03,sh_txt_04,mac_state_id;
	private RelativeLayout ms_kuang_layout,rlKettle;
	private StateHandler stateHandler = null;
	private TextView last_timeTextView;
	private KettleView kettleView;
	private RootSelectView rootselectview;
	String imageframe;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout1);

		initView();
		getData();
		
		setTitle(WifiTools.getdevname(data.getMachineid(), this));
		setLeftOnClick(null);
		setRigter(true, R.drawable.home_right_btn);

		kettleView.setItemlistData(data);

		timer = new Timer(true);
		timer.schedule(task, 100);

		initData();
		imageframe = data.getMachineid().substring(0, 6);
		
		//改变背景

			rlKettle.setBackgroundResource(R.drawable.kettlebg);
			ms_img1.setBackgroundResource(R.drawable.ms_img1);
			last_timeTextView.setTextColor(getResources().getColor(R.color.white));
			sh_txt_04.setTextColor(getResources().getColor(R.color.white));
			ivLine.setBackgroundColor(getResources().getColor(R.color.white));
			rootselectview.ivRootSelect.setBackgroundResource(R.drawable.home2_btn);
			mac_state_id.setTextColor(getResources().getColor(R.color.white));

		
		
		kettleView.imageframe = imageframe;
		kettleView.loadimage();
		
		Message msg = new Message();
		msg.what = 100;
		msg.obj = mList.get(index).ZDY_ID;
		handler.sendMessage(msg);
//		ms_img1.setVisibility(View.INVISIBLE);
		
		
		if(data.getIpString() != null){
			stateHandler = new StateHandler(this,data.getIpString());
			if(!ConnHandler.isConn){
				stateHandler.sendEmptyMessage(1000);
			}
		}
			
	}

	private void initView() {
		ms_kuang_image = (ImageView) findViewById(R.id.ms_kuang_imagex);
		rootselectview = (RootSelectView) findViewById(R.id.rootselectview);
		rootselectview.setVisibility(View.INVISIBLE);
		rootselectview.handler = selectModeHandler;

		ms_kuang_layout = (RelativeLayout)findViewById(R.id.ms_kuang_layout);
		rlKettle = (RelativeLayout)findViewById(R.id.rlKettle);

		kettleView = (KettleView) findViewById(R.id.kettleview);

		zdy_iv = (ImageView)findViewById(R.id.zdy_layout_id);
		ivLine = (ImageView)findViewById(R.id.ivLine);
		zdy_iv.setOnClickListener(this);
		nz_iv = (ImageView)findViewById(R.id.nz_layout_id);
		nz_iv.setOnClickListener(this);
		mac_state_id = (TextView)findViewById(R.id.mac_state_id);

		last_timeTextView = (TextView)findViewById(R.id.last_time);
		ms_img1 = (ImageView)findViewById(R.id.ms1_id);

		ms_img1.setOnClickListener(this);
		sh_txt_01 = (TextView)findViewById(R.id.sh_txt_01);
		sh_txt_02 = (TextView)findViewById(R.id.sh_txt_02);
		sh_txt_03 = (TextView)findViewById(R.id.sh_txt_03);
		sh_txt_04 = (TextView)findViewById(R.id.sh_txt_04);
		sh_txt_04.setOnClickListener(this);

	}

	Handler selectModeHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
//			System.out.println("aaaddd");

			if (msg.what == 100) {
				Message msgs = new Message();
				msgs.what = 100;
				msgs.obj = ((ZDYData)msg.obj).ZDY_ID;
				handler.sendMessage(msgs);
			}else{
				ms_img1.setVisibility(View.VISIBLE);
				rootselectview.setVisibility(View.INVISIBLE);	
			}
		}
	};
	
	@Override
	protected void onStop() {
		// TODO 自动生成的方法存根
		super.onStop();
		isUpdata1 = false;
	}
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		isUpdata = false;
		if(stateHandler != null)
		stateHandler.destoy();
		kettleView.stopAn();
	}
	private boolean isUpdata = true;
	private boolean isUpdata1 = true;
	private Timer timer;
	private int timestate;
	private boolean isStart;
	/**
	 * 定时器启动心跳监听
	 */
	private TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while(isUpdata){
				try{
					if(isUpdata1){
						if(ConnHandler.isConn){
							if(timestate == 1){
								Thread.sleep(2000);
							}else{
								if(timestate == 0)
									timestate = 1;
							}
							Log.e("aa", "-----------");
							List<NameValuePair> list = new ArrayList<NameValuePair>();
							list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
							list.add(new BasicNameValuePair("machineid",data.getMachineid()));
							HttpConnectionUtils httpUtil = new HttpConnectionUtils(httpHandler);
							httpUtil.create(1, HttpUrl.GET_STATE, list);
							httpUtil.setState(100);
							httpHandler.setProcessing(true);
							Thread.sleep(3000);
						}else{
							if(timestate == 1){
								Thread.sleep(2000);
							}else{
								if(timestate == 0)
									timestate = 1;
							}
							stateHandler.setTCPhandler(handler);
							stateHandler.sendEmptyMessage(1001);
							Thread.sleep(4000);	
						}
						
					}else{
						Thread.sleep(3000);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	};

	private boolean isOne = false;
	private HttpHandler httpHandler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			if(state == 100){
				try{
					JSONObject result=new JSONObject(jObject);
					data.setKettleState(JsonParser.getInstance().revertJsonToObj(result.getString("data"),
							KettleState.class));
					initStateData();
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		};
		
		protected void failed(String jObject,int state){
			super.failed(jObject,-1);
		}
	};
	private boolean isOpne = true;

	private void initStateData(){
		try{
			ms_img1.setVisibility(View.INVISIBLE);
			String stateString = "";
			if(data.getKettleState().getState().equals("0")){
				stateString = "空闲中";
				if (ms_img1.getVisibility() == View.INVISIBLE && rootselectview.getVisibility() == View.INVISIBLE) {
					ms_img1.setVisibility(View.VISIBLE);
				}
			}else if(data.getKettleState().getState().equals("1")){
				stateString = this.getString(R.string.heating);
			}else if(data.getKettleState().getState().equals("2")){
				stateString = this.getString(R.string.purify);
			}else if(data.getKettleState().getState().equals("3")){
				stateString = this.getString(R.string.insulation);
			}else if(data.getKettleState().getState().equals("4")){
				stateString = this.getString(R.string.cooldown);
			}
			
			
			if(!data.getKettleState().getState().equals("0")){
				isState = true;
				timestate = 2;

				mac_state_id.setTextColor(getResources().getColor(R.color.white));
				mac_state_id.setText(stateString);
				last_timeTextView.setText("当前状态");
				if(data.getKettleState().getAppid() != null && data.getKettleState().getAppid().equals(HttpUrl.APP_ID)){
					sh_txt_04.setText("停止");
					sh_txt_04.setTextColor(getResources().getColor(R.color.red));
					ms_kuang_image.setBackgroundResource(R.drawable.home2_btn2);

					isOpne = true;
				}else{
					isOpne = false;
				}
			}else{
				isState = false;
				timestate = 1;
				mac_state_id.setTextColor(getResources().getColor(R.color.blue));
				if(ZDY_NAME != null)
					sh_txt_04.setText(ZDY_NAME);
				ms_kuang_image.setBackgroundResource(R.drawable.home2_btn);

				sh_txt_04.setTextColor(getResources().getColor(R.color.tianlan));

				String time1 = data.getKettleState().getLasttime().substring(6, 12);
				String time2 = DateUtil._getFormalTime1();
				mac_state_id.setTextColor(Color.WHITE);
				mac_state_id.setText(DateUtil.getTimes(time1, time2));
				last_timeTextView.setText("上次加热时间");
			}
			
			kettleView.setItemData(data.getKettleState());
			kettleView.loadimage();
//
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private Kettle data;
	private void getData(){
		Intent intent = getIntent();
		if(intent != null){
			data = new Kettle((Machine)intent.getSerializableExtra("data"));
			data.type= Constants.machineType.kettle;
		}
	}
	
	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		isUpdata1 = true;
		isNZ();
	}
	
	@Override
	public void setRightOnClick(){
		Intent intent = new Intent(this,HistoricalRecordsActivity.class);
		intent.putExtra("MACHINEID",data.getMachineid());
		startActivity(intent);
	}
	
	private List<ZDYData> mList;

	
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		initData();
	}
	private void initData(){
		mList = DBhelperManager.getInstance(this).getAlermList(1);
	}
	
	private boolean isMs = false;
	private int index = 0;
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v.getId() == R.id.zdy_layout_id){
			Intent intent = new Intent(this,ZdyActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out); 
		}else if(v.getId() == R.id.nz_layout_id){
			Intent intent = new Intent(this,NzActivity.class);
			intent.putExtra("MACHINEID",data.getMachineid());
			startActivity(intent);
		}
		else if(v.getId() == R.id.ms1_id){//this is the image under rootselect
			rootselectview.setmList(mList);
			rootselectview.setVisibility(View.VISIBLE);
			ms_img1.setVisibility(View.INVISIBLE);
		}else if(v.getId() == R.id.sh_txt_04){//this is the textview in the center of select region
			if(!isMs && !isState){
				showPromptDialog("请在选择模式后开启");
				return;
			}
			if(data.getKettleState()== null)
				return;
			if(data.getKettleState().getState().equals("0") && isOpne){
				String sw = zdydata.ZDY_SW.substring(0, zdydata.ZDY_SW.length() - 2);
				int times = Integer.parseInt(zdydata.ZDY_TIME)*60
						+  Integer.parseInt(zdydata.ZDY_TIME1)*60
						+ Utils.getTime(data.getKettleState().getLevel(), sw, zdydata.ZDY_ISZF.equals("0")?false:true);
				String s2 = data.getKettleState().getTemp().substring(0, data.getKettleState().getTemp().length() - 1);
				if(Integer.parseInt(s2) - Integer.parseInt(sw) > 0){
					showPromptDialog("当前水温高于设置问题");
					return;
				}
				times = times - Utils.getTime(data.getKettleState().getLevel(), s2, false);
				jr_list.add(new BasicNameValuePair("costtime",times+"2000"));
				showPromptDialog("预计工作时间"+times/60+"分"+times%60+"秒,是否开启设备进行工作", diaListener, 100, false);
			}else{
				if(!isOpne)
					return;
				showPromptDialog("是否关闭设备正在进行中的工作", diaListener, 101, false);
			}
		}
	}
	/**
	 * 弹出框的监听
	 */
	private DialogListener diaListener = new DialogListener() {
		
		@Override
		public void doComfirm(int flag) {
			// TODO 自动生成的方法存根
			if(flag == 100){
				if(data.getKettleState().getHub().equals("0") ){
					showPromptDialog("茶壶不在底座上");
					return;
				}
				
				timestate = 2;
				if(!ConnHandler.isConn){
					Message msg = new Message();
					msg.obj = zdydata;
					msg.what = 1002;
					stateHandler.sendMessage(msg);
					return;
				}
				
				
				
				jr_list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
				jr_list.add(new BasicNameValuePair("machineid",data.getMachineid()));
				jr_list.add(new BasicNameValuePair("heattime","0"));
				jr_list.add(new BasicNameValuePair("week","0000000"));
				
				jr_list.add(new BasicNameValuePair("boil","0"));
				jr_list.add(new BasicNameValuePair("startremind","0"));
				jr_list.add(new BasicNameValuePair("endremind","1"));
				jr_list.add(new BasicNameValuePair("nowaterremind","0"));
				jr_list.add(new BasicNameValuePair("nowaterremindthreshold","0.5"));
				
				HttpConnectionUtils httpUtil = new HttpConnectionUtils(jrhandler);
				httpUtil.create(1, HttpUrl.HEAT, jr_list);
				httpUtil.setState(100);
			}else if(flag == 101){
				if(!ConnHandler.isConn){
					Message msg = new Message();
					msg.what = 1003;
					stateHandler.sendMessage(msg);
					return;
				}
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				list.add(new BasicNameValuePair("appid",HttpUrl.APP_ID));
				list.add(new BasicNameValuePair("machineid",data.getMachineid()));
				HttpConnectionUtils httpUtil = new HttpConnectionUtils(jrhandler);
				httpUtil.create(1, HttpUrl.SHTOPHEAT, list);
				httpUtil.setState(101);
			}
		}
		
		@Override
		public void doCancel(int flag) {
			// TODO 自动生成的方法存根
			
		}
		
		@Override
		public void doBack(int flag) {
			// TODO 自动生成的方法存根
			
		}
	};
	
	
	
	
	private HttpHandler jrhandler = new HttpHandler(this){
		protected void succeed(String jObject,int state) {
			super.succeed(jObject,state);
			
			if(state == 100){
				handler.sendEmptyMessage(1001);
			}else if(state == 101){
				try {
					JSONObject result=new JSONObject(jObject);
					data.setKettleState(JsonParser.getInstance().revertJsonToObj(result.getString("data"),
							KettleState.class));
				} catch (JSONException e) {
					e.printStackTrace();
				}

				timestate = 0;
				if(!data.getKettleState().getState().equals("0")){
//					show("正在关闭中");
					sh_txt_04.setText("正在关闭中");
				}else{
					ms_kuang_image.setBackgroundResource(R.drawable.home2_btn);

					sh_txt_04.setTextColor(getResources().getColor(R.color.blue));
					if(ZDY_NAME != null){
						sh_txt_04.setText(ZDY_NAME);
					}else{
						sh_txt_04.setText("模式");
					}
				}
//				shview.setViewState(0);
				kettleView.setItemData(data.getKettleState());
			}
		};
		
		protected void failed(String jObject,int state){
			super.failed(jObject,state);
			if(state == 101){
//				showPromptDialog("服务器返回数据显示取消加热失败,赶紧去拔了电源吧");
			}
		}
	};
	private String ZDY_NAME;
	List<NameValuePair> jr_list = new ArrayList<NameValuePair>();
	private boolean isState = false;
	private ZDYData zdydata;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 100){
				isMs = true;
				for(int i = 0 ; i < mList.size() ; i++){
					ZDYData data = mList.get(i);
					if(data.ZDY_ID.equals(msg.obj.toString())){
						zdydata = data;
						sh_txt_04.setText(data.ZDY_NAME);
						ZDY_NAME = data.ZDY_NAME;
						sh_txt_01.setText(data.ZDY_SW);
						sh_txt_02.setText(kettleActivity.this.getString(R.string.purify)+data.ZDY_TIME+kettleActivity.this.getString(R.string.minute));
						sh_txt_03.setText(kettleActivity.this.getString(R.string.insulation) + data.ZDY_TIME1 + kettleActivity.this.getString(R.string.minute));
								jr_list.clear();
						jr_list = new ArrayList<NameValuePair>();
						String sw = data.ZDY_SW.replace("°c", "");
						jr_list.add(new BasicNameValuePair("temp",sw+"C"));
						jr_list.add(new BasicNameValuePair("purify",data.ZDY_TIME));
						jr_list.add(new BasicNameValuePair("keepwarm",data.ZDY_TIME1));
						
						break;
					}
				}
			}else if(msg.what == 1000){
				KettleState state = (KettleState)msg.obj;
				data.setKettleState( state);
				initStateData();
			}else if(msg.what == 1001){
				sh_txt_04.setTextColor(getResources().getColor(R.color.red));
				sh_txt_04.setText(kettleActivity.this.getString(R.string.stop));
				ms_kuang_image.setBackgroundResource(R.drawable.home2_btn2);

//				shview.setViewState(1);
				kettleView.startAn();
			}else if(msg.what == 1002){
				ms_kuang_image.setBackgroundResource(R.drawable.home2_btn);

				sh_txt_04.setTextColor(getResources().getColor(R.color.blue));
				if(ZDY_NAME != null){
					sh_txt_04.setText(ZDY_NAME);
				}else{
					sh_txt_04.setText(kettleActivity.this.getString(R.string.mode));
				}
//				shview.setViewState(0);
				kettleView.stopAn();
			}else if(msg.what == 10000){
				findViewById(R.id.layout_03).setVisibility(View.GONE);
			}else if(msg.what == 10001){
//				shview.postInvalidate();
				findViewById(R.id.layout_03).setVisibility(View.VISIBLE);

			}
		};
	};
}
