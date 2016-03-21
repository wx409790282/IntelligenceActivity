package com.intelligence.activity.adpter;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.intelligence.activity.R;
import com.intelligence.activity.data.historicalData.historicalItemData;

public class HistoricalRecordsAdpter extends BaseAdapter implements OnItemClickListener{

	private List<historicalItemData> mDataList;

	private Context context;

	private LayoutInflater inf;

	public HistoricalRecordsAdpter(Context context, List<historicalItemData> dataList) {
		mDataList = dataList;
		this.context = context;
		inf = LayoutInflater.from(context);

	}

	public void cleanArray() {
		for (int i = 0; i < mDataList.size(); i++) {
			mDataList.clear();
		}
	}

	@Override
	public int getCount() {
		if (mDataList != null) {
			return mDataList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if (mDataList != null) {
			return mDataList.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int index, View contentView, ViewGroup parent) {
		ShopOrderHolder holder;
		if (contentView == null) {
			holder = new ShopOrderHolder();
			contentView = inf.inflate(R.layout.hc_item, null);
			 holder.tx01 = (TextView)contentView.findViewById(R.id.time_id_01);
			 holder.tx02 = (TextView)contentView.findViewById(R.id.time_id_02);
			 holder.tx03 = (TextView)contentView.findViewById(R.id.time_id_03);
			 holder.tx04 = (TextView)contentView.findViewById(R.id.time_id_04);
			 holder.tx05 = (TextView)contentView.findViewById(R.id.time_id_05);
			 holder.tx031 = (TextView)contentView.findViewById(R.id.time_id_031);
			 contentView.setTag(holder);
		} else {
			holder = (ShopOrderHolder)contentView.getTag();
		}

		historicalItemData bean = mDataList.get(index);
		try {
			 holder.tx01.setText(bean.getEndtime().subSequence(0, 2)+":"+bean.getEndtime().subSequence(2, 4) + ":"+bean.getEndtime().subSequence(4, 6));
			 
			 holder.tx02.setText(getString(bean.getCreatetime()));
			 holder.tx03.setText(bean.getLevel());
			 holder.tx031.setText(bean.getTemp().subSequence(0, bean.getTemp().length()-1)+"°C");
			 String string = "";
			 if(bean.getOperation().equals("1")){
				 string = "APP";
			 }else if(bean.getOperation().equals("0")){
				 string = "手动";
			 }else if(bean.getOperation().equals("2")){
				 string = "预约";
			 }
			 holder.tx05.setText(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentView;
	}
	
	private String getString(String time){
		try{
			 Calendar calendar=Calendar.getInstance();
			 calendar.setTimeInMillis(Long.parseLong(time));
			 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			 Date date = new Date(Long.parseLong(time)*1000L);
			 String aString = df.format(date);
			 return aString;
		}catch(Exception e){
			e.printStackTrace();
		}
		 return "";
	}

	public static class ShopOrderHolder {
		TextView tx01;
		TextView tx02;
		TextView tx03;
		TextView tx04;
		TextView tx05;
		TextView tx031;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}

}
