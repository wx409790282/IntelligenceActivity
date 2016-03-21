package com.intelligence.activity.humidifier;

import java.util.List;

import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.humidifier.SwitchButton.OnChangeListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class HumTimingMakeAdapter extends BaseAdapter{
	private List<String> list;
	private LayoutInflater mInflater;
	private Context context;
	private HTiming timing;

	public HumTimingMakeAdapter(Context context, List<String> list,HTiming timing){
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.timing = timing;
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
			convertView = mInflater.inflate(R.layout.item_hum_timing_make, null); 
		}

		String msg = list.get(position).toString();

		//		ImageView mImageView = (ImageView) convertView.findViewById(R.id.itemImage);
		TextView itemtitle = (TextView) convertView.findViewById(R.id.itemtitle);
		TextView itemcon = (TextView) convertView.findViewById(R.id.itemcon);
		SwitchButton wiperSwitch1 = (SwitchButton) convertView.findViewById(R.id.wiperSwitch1);

		if (position > 1) {
			wiperSwitch1.setVisibility(View.VISIBLE);
			itemcon.setVisibility(View.INVISIBLE);
		}else{
			itemcon.setVisibility(View.VISIBLE);
			wiperSwitch1.setVisibility(View.INVISIBLE);
		}

		itemtitle.setText(msg);

		switch (position) {
		case 0:
			itemcon.setText(WifiTools.weekname(timing.repeat));
			break;
		case 1:
			itemcon.setText(timing.title);
			break;
		case 2:
			if (timing.begin_remind == 1) {
				wiperSwitch1.setmSwitchOn(true);
			}else{
				wiperSwitch1.setmSwitchOn(false);
			}
			wiperSwitch1.setTag(102);
			break;
		case 3:
			if (timing.end_remind == 1) {
				wiperSwitch1.setmSwitchOn(true);
			}else{
				wiperSwitch1.setmSwitchOn(false);
			}
			wiperSwitch1.setTag(103);
			break;
		default:
			break;
		}
		wiperSwitch1.setOnChangeListener(new SwitchOnChangeListener(timing));

		return convertView;
	}

	class SwitchOnChangeListener implements OnChangeListener{

		HTiming timing;
		public SwitchOnChangeListener(HTiming timing){
			this.timing = timing;
		}
		@Override
		public void onChange(SwitchButton sb, boolean state) {
			// TODO Auto-generated method stub
//			timing.setRepeat_open(state?1:0);
			
			if (Integer.parseInt(sb.getTag().toString()) == 102) {
				timing.begin_remind = state?1:0;
			}else if(Integer.parseInt(sb.getTag().toString()) == 103){
				timing.end_remind = state?1:0;
			}
			
		}

	}
}
