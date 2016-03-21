package com.intelligence.activity.humidifier;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.humidifier.SwitchButton.OnChangeListener;

public class HumTimingAdapter extends BaseAdapter implements OnItemClickListener{
	private List<HTiming> list;
	private LayoutInflater mInflater;
	private Context context;
	
	int editindex = -1;
	int moveWidth;
	int touchx = 0;
	int editmove = 20;


	public HumTimingAdapter(Context context, List<HTiming> list){
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (convertView == null) { 
			convertView = mInflater.inflate(R.layout.item_hum_timing, null); 
		}

		TextView timingtime = (TextView) convertView.findViewById(R.id.timingtime);
		TextView timingtitle = (TextView) convertView.findViewById(R.id.timingtitle);
		TextView timinglal = (TextView) convertView.findViewById(R.id.timinglal);
		SwitchButton wiperSwitch1 = (SwitchButton) convertView.findViewById(R.id.wiperSwitch1);
		wiperSwitch1.setOnChangeListener(new SwitchOnChangeListener(position));
		
		final Button delbtn = (Button) convertView.findViewById(R.id.delbtn);
		
		if (editindex == position) {
			delbtn.setVisibility(View.VISIBLE);	
		}else{
			delbtn.setVisibility(View.INVISIBLE);
		}
		
		
		
		HTiming hTiming = list.get(position);
		String stime = "";
		if (hTiming.c_hour<10) {
			stime = stime + "0" + hTiming.c_hour;
		}else{
			stime = stime + hTiming.c_hour;
		}
		
		stime = stime + ":";
		
		if (hTiming.c_min<10) {
			stime = stime + "0" + hTiming.c_min;
		}else{
			stime = stime + hTiming.c_min;
		}
		
		timingtime.setText(stime);
		if (hTiming.t_h_open == 1) {
			timingtitle.setText("开");	
		}else{
			timingtitle.setText("关");
		}
		
		timinglal.setText(hTiming.title+"      "+ WifiTools.weekname(hTiming.repeat));
		
		
		if (hTiming.t_open == 1) {
			wiperSwitch1.setmSwitchOn(true);
		}else{
			wiperSwitch1.setmSwitchOn(false);
		}
		
//		convertView.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				System.out.println("vvvv : "+event.getAction());
//				
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					touchx = (int) event.getX();
//					if (editindex > -1) {
//						editindex = 1;
//						notifyDataSetChanged();
//					}
//				}else if(event.getAction() == MotionEvent.ACTION_UP) {
//					
//				}else if(event.getAction() == MotionEvent.ACTION_MOVE) {
//					moveWidth = touchx - (int) event.getX();
//					if (moveWidth<0) {
//						moveWidth = moveWidth * (-1);
//					}
//					
//					System.out.println(moveWidth);
//					if (moveWidth>editmove) {
//						editindex = position;
//						delbtn.setVisibility(View.VISIBLE);
//					}else{
//						delbtn.setVisibility(View.INVISIBLE);
//						editindex = -1;
//					}
//				}
//				
//				return false;
//			}
//		});
		
		return convertView;
	}


	class SwitchOnChangeListener implements OnChangeListener{

		int index;
		
		public SwitchOnChangeListener(int index){
			this.index = index;
		}

		@Override
		public void onChange(SwitchButton sb, boolean state) {
			// TODO Auto-generated method stub
			
			HTiming hTiming = list.get(index);
			
			if (state) {
				hTiming.t_open = 1;
			}else{
				hTiming.t_open = 0;
			}
			
			((HumTimingActivity)context).sendmsg(index);
			
		}

	}
	class BtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
//		System.out.println("cccc");
//		if (editindex>-1) {
//			editindex = -1;
//			notifyDataSetChanged();
//		}
		
		HTiming hTiming = list.get(position);
		
		Intent intent = new Intent(context, HumTimingMakeActivity.class);
		Bundle bundle = new Bundle();  
        bundle.putSerializable("data", hTiming);  
        intent.putExtras(bundle); 
        ((Activity) context).startActivityForResult(intent,10);
	}
}
