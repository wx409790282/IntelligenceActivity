package com.intelligence.activity.humidifier;

import java.util.List;

import com.intelligence.activity.R;
import com.intelligence.activity.humidifier.SwitchButton.OnChangeListener;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HumStaAdapter extends BaseAdapter{
	private List<HumWork> list;
	private LayoutInflater mInflater;
	private Context context;


	public HumStaAdapter(Context context, List<HumWork> list){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (convertView == null) { 
			convertView = mInflater.inflate(R.layout.item_hum_sta, null); 
		}

		TextView timelal = (TextView) convertView.findViewById(R.id.timelal);
		TextView sclal = (TextView) convertView.findViewById(R.id.sclal);
		TextView sdlal = (TextView) convertView.findViewById(R.id.sdlal);
		TextView czlal = (TextView) convertView.findViewById(R.id.czlal);

		HumWork humWork = list.get(position);

		int bs = humWork.getCosttime();
		int ss = bs % 60;

		int mm = (bs - ss)/60 % 60;

		int hh = (bs - ss - mm*60)/3600 % 24;

		int dd = (bs - ss - mm*60 - hh*3600)/(3600*24);

		String str = "";
		str =dd+"天 "+hh+"小时 "+mm+"分钟";

		if (dd == 0) {
			str = hh+"小时 "+mm+"分钟";
		}

		if (dd ==0 && hh == 0) {
			str = mm+"分钟";
		}

		if (dd ==0 && hh == 0 && mm==0) {
			str= "0分钟";//[NSString stringWithFormat:@"%is",ss];
		}

		if (dd ==0 && hh == 0 && mm==0 && ss ==0) {
			str= "0分钟";
		}

		timelal.setText(humWork.getStarttime());

		sclal.setText(str);

		sdlal.setText(humWork.getHumidity());

		if (humWork.getOperation() == 0) {
			czlal.setText("手动");	
		}else if (humWork.getOperation() == 1) {
			czlal.setText("App");	
		}else if (humWork.getOperation() == 2) {
			czlal.setText("定时");	
		}else if (humWork.getOperation() == 3) {
			czlal.setText("智能");	
		}


		return convertView;
	}


	class SwitchOnChangeListener implements OnChangeListener{



		@Override
		public void onChange(SwitchButton sb, boolean state) {
			// TODO Auto-generated method stub
		}

	}
	class BtnOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}

	}
}
