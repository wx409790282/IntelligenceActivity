package com.intelligence.activity.humidifier;

import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.data.Constants;
import com.intelligence.activity.data.Humidifier;
import com.intelligence.activity.data.HumidifierState;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.http.GsonGetRequest;
import com.intelligence.activity.utils.MusicUtil;
import com.intelligence.activity.http.HttpUrl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HumActivity extends BaseActivity {
	private MusicUtil musicUtil=new MusicUtil(this);
	private QipaoView qipaoView = null;
	private ImageButton custonbtn,timingbtn = null;
	private TextView humsdlal,humsattslal = null;
	private ImageView humimage = null;
	private Button startbtn = null;
	ImageButton dxbtn1,dxbtn2,dxbtn3,flzbtn;
	String grade = "1";
	String anion="0";
	private boolean isUpdata = true;
	String imageframe = null;
	DisplayImageOptions options;
	int sendcount=0;
	Humidifier humidifier;
	String TAG="HumActivity";
	boolean isfirstrefview=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hum);
		options = new DisplayImageOptions.Builder()
		//		.showImageOnLoading(R.drawable.hum)
		//		.showImageForEmptyUri(R.drawable.hum)
		//		.showImageOnFail(R.drawable.hum)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();

		getData();

		setTitle(this.getString(R.string.hum_name));
		setLeftOnClick(null);
		setRigter(true, R.drawable.home_right_btn);//and i will set right
		setRightOnClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HumActivity.this, HumHistoryActivity.class);
				intent.putExtra("devId", humidifier.getMachineid());
				startActivity(intent);
			}
		});
		initView();

		ImageLoader.getInstance().displayImage(HttpUrl.IMGURL + imageframe + ".png", humimage, options, null);
		//		ImageLoader.getInstance().displayImage(HttpUrl.IMGURL+imageframe+".png",humimage);
		//update view per 5 seconds, and refresh view
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				while(isUpdata){
					try{
						if(sendcount>0){
							sendcount--;
							System.out.println(sendcount);
							Thread.sleep(500);
							continue;
						}
						sendcount=10;
						volley.add(getHumidifierState(new Response.Listener<HumidifierState>() {
							@Override
							public void onResponse(HumidifierState response) {
								humidifier.setHumidifierState(response);
								refview();
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								humidifier.initHUmidifierState();
								Toast.makeText(HumActivity.this,HumActivity.this.getString(R.string.hum_getstate_connerror),Toast.LENGTH_SHORT).show();
							}
						}));

					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	//private
	private void initView() {
		humsdlal = (TextView) findViewById(R.id.humsdlal);
		humsattslal = (TextView) findViewById(R.id.humsattslal);
		qipaoView = (QipaoView) findViewById(R.id.qipaoview);
		humimage = (ImageView) findViewById(R.id.humimage);
		startbtn = (Button) findViewById(R.id.startbtn);startbtn.setOnClickListener(humActivityOnClickListener);
		custonbtn = (ImageButton)findViewById(R.id.hcusbtn);custonbtn.setOnClickListener(humActivityOnClickListener);
		timingbtn = (ImageButton)findViewById(R.id.timingbtn);timingbtn.setOnClickListener(humActivityOnClickListener);
		dxbtn1 = (ImageButton) findViewById(R.id.dxbtn1);dxbtn1.setOnClickListener(modeOnClickListener);
		dxbtn2 = (ImageButton) findViewById(R.id.dxbtn2);dxbtn2.setOnClickListener(modeOnClickListener);
		dxbtn3 = (ImageButton) findViewById(R.id.dxbtn3);dxbtn3.setOnClickListener(modeOnClickListener);
		flzbtn = (ImageButton)findViewById(R.id.flzbtn);flzbtn.setOnClickListener(humActivityOnClickListener);
	}
	private OnClickListener humActivityOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			musicUtil.playmp3();
			switch (v.getId()){
				case R.id.flzbtn:
					if(anion.equals("0")){
						anion="1";
						flzbtn.setImageResource(R.drawable.flzo);
					}else{
						anion="0";
						flzbtn.setImageResource(R.drawable.flzc);
					}
					refview();
					break;
				case R.id.startbtn:
					startHum();
					break;
				case R.id.timingbtn:
					Intent intent = new Intent(HumActivity.this, HumTimingActivity.class);
					intent.putExtra("devId", humidifier.getMachineid());
					startActivity(intent);
					break;
				case R.id.hcusbtn:
					Intent intent2 = new Intent(HumActivity.this, HumCustomActivity.class);
					intent2.putExtra("devId", humidifier.getMachineid());
					startActivity(intent2);
					break;
			}
		}
	};
	private OnClickListener modeOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			musicUtil.playmp3();
			dxbtn1.setImageResource(R.drawable.sds_b);
			dxbtn2.setImageResource(R.drawable.sdm_b);
			dxbtn3.setImageResource(R.drawable.sdb_b);
			switch (v.getId()){
				case R.id.dxbtn1:
					dxbtn1.setImageResource(R.drawable.sds);
					grade="1";
					break;
				case R.id.dxbtn2:
					dxbtn2.setImageResource(R.drawable.sdm);
					grade="2";
					break;
				case R.id.dxbtn3:
					dxbtn3.setImageResource(R.drawable.sdb);
					grade="3";
					break;
			}
		}
	};

	@Override  
	protected void onStart() {
		super.onStart();
	}
	@Override  
	protected void onStop() {  
		super.onStop();  
		System.out.println("onStop");
		qipaoView.stopAn();
	}

	public void startHum(){
		//start
		if (!humidifier.getHumidifierState().getLevel().equals("1.0L") || humidifier.getHumidifierState().getState().equals("3")) {
			Dialog alertDialog = new AlertDialog.Builder(this).
					setTitle(this.getString(R.string.confirm)).
					setMessage(this.getString(R.string.hum_state_nowaterhint)).
					setPositiveButton(this.getString(R.string.confirm), new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					}).
					create();
			alertDialog.show();
			return;
		}
		sendcount=10;
		String startorstop="";
		if(humidifier.getHumidifierState().getState().equals("0")){
			startorstop=HttpUrl.H_START;
			humidifier.getHumidifierState().setGrade(grade);
			humidifier.getHumidifierState().setAnion(anion);
			humidifier.getHumidifierState().setState("1");
		}else{
			startorstop=HttpUrl.H_STOP;
			humidifier.getHumidifierState().setState("0");
		}
		volley.add(new HumiActivityPostResquest(startorstop, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.e(TAG, "start or stop" + response);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, error.toString());
			}
		}));
		refview();
	}

	//getdatafrom last activity, mainly machine id
	private void getData(){
		Intent intent = getIntent();
		if(intent != null){
			Machine data = (Machine)intent.getSerializableExtra("data");
			humidifier= new Humidifier( data);
			humidifier.type= Constants.machineType.humidifier;
		}
		imageframe = humidifier.getMachineid().substring(0, 6);
	}
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		isUpdata = false;
		qipaoView.stopAn();
	}

	public void refview(){
		if(humidifier.getHumidifierState().equals(null)){
			Toast.makeText(HumActivity.this,this.getString(R.string.hum_getstate_error),Toast.LENGTH_LONG).show();
			return;
		}
		if(isfirstrefview){
			//grade=humidifier.getHumidifierState().getGrade();
			anion=humidifier.getHumidifierState().getAnion();
			if(humidifier.getHumidifierState().isAnion()){
				flzbtn.setImageResource(R.drawable.flzo);
			}
//			}
			isfirstrefview=false;
		}
		humsdlal.setText(humidifier.getHumidifierState().getHumidity());
		switch (humidifier.getHumidifierState().getState()) {
			case "0":
				enablButtons(true);
				humsattslal.setText(HumActivity.this.getString(R.string.hum_state_empty));
				qipaoView.stopAn();
				ImageLoader.getInstance().displayImage(HttpUrl.IMGURL + imageframe + ".png", humimage, options, null);
				break;
			case "1":
				qipaoView.startAn();
				humsattslal.setText(HumActivity.this.getString(R.string.hum_state_work));
				ImageLoader.getInstance().displayImage(HttpUrl.IMGURL+imageframe+"o.png", humimage, options, null);
				enablButtons(false);
				break;
			case "2":
				humsattslal.setText(HumActivity.this.getString(R.string.hum_state_stay));
				qipaoView.stopAn();
				ImageLoader.getInstance().displayImage(HttpUrl.IMGURL+imageframe+"o.png", humimage, options, null);
				enablButtons(false);
				break;
			case "3":
				flzbtn.setEnabled(true);
				humsattslal.setText(HumActivity.this.getString(R.string.hum_state_nowater));
				qipaoView.stopAn();
				ImageLoader.getInstance().displayImage(HttpUrl.IMGURL + imageframe + ".png", humimage, options, null);
				break;

		}
	}
	//is 负离子 and mode button can be clicked
	private void enablButtons(boolean b) {
		if(b){
			flzbtn.setEnabled(true);
			dxbtn1.setEnabled(true);
			dxbtn2.setEnabled(true);
			dxbtn3.setEnabled(true);
		}else{
			flzbtn.setEnabled(false);
			dxbtn1.setEnabled(false);
			dxbtn2.setEnabled(false);
			dxbtn3.setEnabled(false);
		}
	}

	//post resquest to start or stop hum
	class HumiActivityPostResquest extends StringRequest {
		public HumiActivityPostResquest(String url,Response.Listener<String> listener, Response.ErrorListener errorListener) {
			super(Method.POST, url, listener, errorListener);
		}
		protected Map<String, String> getParams() throws AuthFailureError {
			Map<String, String> params = new HashMap<String, String>();
			params.put("machineid", humidifier.getMachineid());
			params.put("appid", HttpUrl.APP_ID);
			if(humidifier.getHumidifierState().getState().equals("1")){
				params.put("delay","0");
				params.put("grade",humidifier.getHumidifierState().getGrade());
				params.put("anion", humidifier.getHumidifierState().getAnion());
			}
			return params;

		}
	}
	//get hum start per 5 seconds,and save it to hum.humstate
	public GsonGetRequest<HumidifierState> getHumidifierState(Response.Listener<HumidifierState> listener,
					Response.ErrorListener errorListener)
	{
		String url = HttpUrl.H_GET_STATE;
		url+="/machineid/"+humidifier.getMachineid();
		url+="/appid/"+HttpUrl.APP_ID;
		final Gson gson = new GsonBuilder().create();
		return new GsonGetRequest<>(
						url,
						new TypeToken<HumidifierState>() {}.getType(),
						gson,
						listener,
						errorListener
		);
	}
}
