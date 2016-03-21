package com.intelligence.activity.humidifier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HumTimingMakeActivity extends Activity {

	private boolean scrolling = false;
	ListView listView;

	HTiming timing;
	
	HumTimingMakeAdapter timingMakeAdapter;
	String devId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hum_timing_make);

		
		timing = (HTiming)getIntent().getSerializableExtra("data");
		devId = getIntent().getStringExtra("devId");
		
		if (timing == null) {
			timing = new HTiming();
		}
		
		NavigationBar bar = (NavigationBar) findViewById(R.id.navigationbar);
		bar.setTitle("编辑预约");
		bar.showLeftBtn(true);
		bar.setLeftOnClickListener(new MyOnClickListener() {  
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				finish();       
			}
		});  
		bar.showRightbtn(0);
		bar.setRightText("保存");
		bar.setRight1OnClickListener(new MyOnClickListener() {
			
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				if(timing.t_id == 0){
					timing.sqlinster(devId);
				}else{
					timing.sqlupdate();
				}
				
				Dialog alertDialog = new AlertDialog.Builder(HumTimingMakeActivity.this).   
						setTitle("提示").   
						setMessage("保存成功").   
						setPositiveButton("确定", new DialogInterface.OnClickListener() {   
							@Override   
							public void onClick(DialogInterface dialog, int which) {   
								// TODO Auto-generated method stub 

								Intent it = new Intent();
						        Bundle b2 = new Bundle();
						        b2.putSerializable("timing", timing);
						        it.putExtras(b2);
						        setResult(RESULT_OK,it);
							
								finish();
							}   
						}). 
						create();   
				alertDialog.show(); 
				
			}
		});


		//============
		listView = (ListView)findViewById(R.id.listview);
		List<String> list = new ArrayList<String>();

		list.add("重复");
		list.add("标签");
		list.add("预约提醒");
//		list.add("结束提醒");

		timingMakeAdapter = new HumTimingMakeAdapter(this, list,timing);
		listView.setAdapter(timingMakeAdapter);
		listView.setOnItemClickListener(new ListViewItemClickListener());

		//=============



		//=============
		Calendar calendar = Calendar.getInstance();

		final WheelView hour = (WheelView) findViewById(R.id.hour);
		final WheelView min = (WheelView) findViewById(R.id.min);
		final WheelView coc = (WheelView) findViewById(R.id.coc);

		String hours[] = new String[24];
		for (int i = 0; i < 24; i++) {
			hours[i] = i+"";
		}
		hour.setViewAdapter(new DateArrayAdapter(this, hours, 0));
		hour.setCurrentItem(timing.c_hour);

		hour.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					timing.c_hour =  hour.getCurrentItem();
				}
			}
		});

		hour.addScrollingListener( new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}
			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				timing.c_hour =  hour.getCurrentItem();
			}
		});

		String mins[] = new String[60];
		for (int i = 0; i < 60; i++) {
			mins[i] = i+"";
		}
		min.setViewAdapter(new DateArrayAdapter(this, mins, 0));
		min.setCurrentItem(timing.c_min);
		min.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					timing.c_min =  min.getCurrentItem();
				}
			}
		});

		min.addScrollingListener( new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}
			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				timing.c_min =  min.getCurrentItem();
			}
		});


		String cocs[] = new String[2];
		cocs[0] = "关";
		cocs[1] = "开";
		

		coc.setViewAdapter(new DateArrayAdapter(this, cocs, 0));
		coc.setCurrentItem(timing.t_h_open);
		coc.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					timing.t_h_open =  coc.getCurrentItem();
				}
			}
		});

		coc.addScrollingListener( new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}
			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				timing.t_h_open =  coc.getCurrentItem();
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.hum_timing_make, menu);
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





	//===================
	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			int fsize = WifiTools.dip2px(context, 10);
			setTextSize(fsize);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}


	private class ListViewItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if (position == 0) {
				
				Intent intent = new Intent(HumTimingMakeActivity.this, WeekSelectActivity.class);
				Bundle bundle = new Bundle();  
                bundle.putSerializable("timing", timing);  
                intent.putExtras(bundle);  
                startActivityForResult(intent, 10);	
			}else if(position == 1){
				Intent intent = new Intent(HumTimingMakeActivity.this, MarkActivity.class);
				Bundle bundle = new Bundle();  
                bundle.putSerializable("timing", timing);  
                intent.putExtras(bundle);  
                startActivityForResult(intent, 10);	
				
			}

			//	    		if (position == 0) {
//				    			Intent mainIntent = new Intent(context, TimingModelActivity.class);
//				        		Bundle bundle = new Bundle();  
//				                bundle.putSerializable("timing", timing);  
//				                mainIntent.putExtras(bundle);  
//				                TimingMakeActivity.this.startActivityForResult(mainIntent, 10);	
			//				}else if(position == 1){
			//					Intent mainIntent = new Intent(context, TimingWeekActivity.class);
			//	        		Bundle bundle = new Bundle();  
			//	                bundle.putSerializable("timing", timing);  
			//	                mainIntent.putExtras(bundle);  
			//	                TimingMakeActivity.this.startActivityForResult(mainIntent, 10);
			//					
			//				}else if(position == 3 && timing.mode == 2){
			//					
			//					Intent mainIntent = new Intent(context, TimingCountActivity.class);
			//	        		Bundle bundle = new Bundle();  
			//	                bundle.putSerializable("timing", timing);  
			//	                mainIntent.putExtras(bundle);  
			//	                TimingMakeActivity.this.startActivityForResult(mainIntent, 10);
			//				}
		}
	}

	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  

		if (data == null)  
			return;  

		if(resultCode == RESULT_OK){
			Bundle b = data.getExtras();
			HTiming _tTiming = (HTiming) b.getSerializable("timing");
			timing.title = _tTiming.title;
			timing.repeat = _tTiming.repeat;
			timingMakeAdapter.notifyDataSetChanged();
		}



		super.onActivityResult(requestCode, resultCode, data);  
	} 
}
