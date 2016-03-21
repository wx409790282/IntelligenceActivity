package com.intelligence.activity.addpart;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligence.activity.addpart.CopyOfMachineActivity;
import com.intelligence.activity.addpart.MachineActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.http.HttpUrl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class AddNewInfoAdpter extends BaseAdapter implements
		OnItemClickListener {

	private List<HashMap<String, Object>> mDataList;

	private Context context;

	private LayoutInflater inf;

	DisplayImageOptions options;

	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public AddNewInfoAdpter(Context context,
			List<HashMap<String, Object>> dataList) {
		options = new DisplayImageOptions.Builder()
		.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();

		mDataList = dataList;
		this.context = context;
		inf = LayoutInflater.from(context);
	}

	public void addData(HashMap<String, Object> bean) {
		mDataList.add(bean);
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
		// Holder holder;
		if (contentView == null) {
			// holder = new Holder();
			contentView = inf.inflate(R.layout.new_info_item, null);
			// holder.tx01 = (TextView)view.findViewById(R.id.plan_item_txt2);
			// holder.tx02 = (TextView)view.findViewById(R.id.plan_item_txt3);
			// holder.tx03 = (TextView)view.findViewById(R.id.plan_item_txt);
			// contentView.setTag(holder);
		}

		ImageView imageView_ItemImage = (ImageView) contentView
				.findViewById(R.id.imageView_ItemImage);
		TextView main_item_txt = (TextView) contentView
				.findViewById(R.id.main_item_txt);
		// main_item_txt.setVisibility(View.GONE);

		// StateViewHolder holder = (StateViewHolder) view.getTag();
		HashMap<String, Object> bean = mDataList.get(index);

		String machineid = null;
		if (bean.get("machineid") != null) {
			machineid = bean.get("machineid").toString();
		} else {
			machineid = bean.get("MACHINEID").toString();
		}

		String imageframe = machineid.substring(0, 6);
		ImageLoader.getInstance().displayImage(
				HttpUrl.IMGURL + imageframe + ".png", imageView_ItemImage,
				options, null);

		String dev_type = machineid.substring(0, 2);
		if (dev_type.equals("01")) {
			main_item_txt.setText("我的水壶");
		} else {
			main_item_txt.setText("我的加湿器");
		}

		try {
			// holder.tx01.setText(bean.getMuscle());
			// holder.tx02.setText(bean.getFat());
			// holder.tx03.setText(bean.getRecordDate());
			// holder.idString = bean.getCustomerStateId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentView;
	}

	public static class Holder {
		ImageView img;
		TextView tx01;
		TextView tx02;
		TextView tx03;
		public String idString;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		HashMap<String, Object> bean = mDataList.get(position);
		if (state == 1 || state == 0) {
			Intent intent = new Intent(context, CopyOfMachineActivity.class);
			intent.putExtra("IP", bean.get("IP").toString());

			intent.putExtra("state", state);
			context.startActivity(intent);
			((Activity) context).finish();
		} else if (state == 2) {
			Intent intent = new Intent(context, MachineActivity.class);
			String machineid = null;
			if (bean.get("machineid") != null) {
				machineid = bean.get("machineid").toString();
			} else {
				machineid = bean.get("MACHINEID").toString();
			}
			intent.putExtra("MACHINEID", machineid);
			context.startActivity(intent);
			((Activity) context).finish();
		}
	}

}
