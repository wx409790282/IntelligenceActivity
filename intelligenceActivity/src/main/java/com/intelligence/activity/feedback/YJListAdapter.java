package com.intelligence.activity.feedback;

import java.util.List;

import com.intelligence.activity.R;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class YJListAdapter extends BaseAdapter{
	private List<FaceBack> list;
	private LayoutInflater mInflater;
	private Context context;


	public YJListAdapter(Context context, List<FaceBack> list){
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
			convertView = mInflater.inflate(R.layout.item_yj_list, null); 
		}

		FaceBack faceBack = list.get(position);
		
		TextView namelal = (TextView)convertView.findViewById(R.id.namelal);
		
		TextView conlal1 = (TextView) convertView.findViewById(R.id.conlal1);
		TextView conlal2 = (TextView) convertView.findViewById(R.id.conlal2);
		
		if (faceBack.type == 0) {
			namelal.setText(context.getString(R.string.me));
			namelal.setGravity(Gravity.RIGHT);
			conlal1.setVisibility(View.INVISIBLE);
			conlal2.setVisibility(View.VISIBLE);
			conlal2.setText(faceBack.content);
		}else{
			namelal.setText(context.getString(R.string.customer_service));
			namelal.setGravity(Gravity.LEFT);
			conlal2.setVisibility(View.INVISIBLE);
			conlal1.setVisibility(View.VISIBLE);
			conlal1.setText(faceBack.content);
		}
		return convertView;
	}

}
