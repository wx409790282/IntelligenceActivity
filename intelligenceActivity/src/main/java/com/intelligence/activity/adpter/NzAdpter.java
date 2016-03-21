package com.intelligence.activity.adpter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligence.activity.kettle.NZEditActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.db.NZ_DBhelperManager;
import com.intelligence.activity.db.NZ_DBhelperManager.NZYData;
import com.intelligence.activity.view.MessageItem;
import com.intelligence.activity.view.SlideView;
import com.intelligence.activity.view.SlideView.OnSlideListener;

public class NzAdpter extends BaseAdapter implements OnItemClickListener,OnSlideListener{

	public class Items extends MessageItem{
		NZYData item;
	}
	private List<Items> listData = new ArrayList<Items>();
	
	private SlideView mLastSlideViewWithStatusOn;

	private List<NZYData> mDataList;

	private Context context;

	private LayoutInflater inf;

	private String[] numberStrings;
	private Handler handler;
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public NzAdpter(Context context, List<NZYData> dataList) {
		mDataList = dataList;
		this.context = context;
		inf = LayoutInflater.from(context);
		for (int i = 0; i < mDataList.size(); i++) {
			Items itemts = new Items();
			itemts.item = dataList.get(i);
			listData.add(itemts);
		}
		numberStrings=new String[]{ context.getString(R.string.monday),context.getString(R.string.tuesday),context.getString(R.string.wednesday),
				context.getString(R.string.thursday),context.getString(R.string.friday),context.getString(R.string.saturday),context.getString(R.string.sunday)};

	}
	
	public void addItemData(NZYData data){
		mDataList.add(data);
		Items itemts = new Items();
		itemts.item = data;
		listData.add(itemts);
		this.notifyDataSetInvalidated();
	}

	public void cleanArray() {
		mDataList.clear();
		listData.clear();
	}

	@Override
	public int getCount() {
		if (listData != null) {
			return listData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if (listData != null) {
			return listData.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(final int index, View contentView, ViewGroup parent) {
		
		SlideView sview = (SlideView)contentView;
		ZDYHolder holder;

		if (contentView == null) {
			holder = new ZDYHolder();
			sview = new SlideView(context);
			View view = LayoutInflater.from(context).inflate(
					R.layout.nz_layout_item, parent, false);
			
			holder.img = (ImageView)view.findViewById(R.id.toggleButton1);
			holder.tx01 = (TextView)view.findViewById(R.id.nz_time_id);
			holder.tx02 = (TextView)view.findViewById(R.id.nz_name_id);
			holder.tx03 = (TextView)view.findViewById(R.id.nz_xq_id);
			holder.tx04 = (TextView)view.findViewById(R.id.nz_txt_id);
			sview.setContentView(view);
			sview.setOnSlideListener(this);
			sview.setTag(holder);
		} else {
			holder = (ZDYHolder)sview.getTag();;
		}

		
		final Items item = listData.get(index);
		item.slideView = sview;
		item.slideView.reset();
		final NZYData bean = listData.get(index).item;
		try {
			sview.setButtonOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					NZ_DBhelperManager.getInstance(context).delete(bean.NZ_ID);
					listData.remove(item);
					notifyDataSetInvalidated();
				}
			});
			
			
			String stime[] = bean.NZ_TIME.split(":");
			String time = "";
			
			if (stime.length>1) {
				int time1 = Integer.parseInt(stime[0]);
				int time2 = Integer.parseInt(stime[1]);
				
				if (time1<10) {
					time = time + "0" + time1;
				}else{
					time = time + time1;
				}
				
				time = time + ":";
				
				if (time2<10) {
					time = time + "0" + time2;
				}else{
					time = time + time2;
				}
			}
			
			
			
			
			holder.tx01.setText(time);
			holder.tx02.setText(bean.NZ_MS);
			holder.tx03.setText(getWeekly(bean.NZ_SW));
			holder.tx04.setText(bean.NZ_NAME);
			
			if(bean.NZ_ISOPEN == 1){
				holder.img.setBackgroundResource(R.drawable.r1);
			}else{
				holder.img.setBackgroundResource(R.drawable.r2);
			}
			holder.img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					if(bean.NZ_ISOPEN == 1){
						v.setBackgroundResource(R.drawable.r1);
						if(handler != null){
							Message msg = new Message();
							msg.what = 12;
							msg.obj = bean;
							msg.arg1 = 0;
							handler.sendMessage(msg);
						}
					}else{
						if(handler != null){
							Message msg = new Message();
							msg.what = 11;
							msg.obj = bean;
							msg.arg1 = 1;
							handler.sendMessage(msg);
						}
						v.setBackgroundResource(R.drawable.r2);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sview;
	}





	private String getWeekly(String strings) {
		StringBuffer stringBuffer = new StringBuffer();
		int isqb = 0;
		for (int i = 0; i < 7; i++) {
			char c = strings.charAt(i);
			StringBuffer B = new StringBuffer();
			B.append(c);
			if (B.toString().equals("1")) {
				isqb++;
				if(isqb!=1){
					stringBuffer.append(",");
				}
				stringBuffer.append(numberStrings[i]);
			}
		}
		if (isqb >= 7) {
			stringBuffer = new StringBuffer();
			stringBuffer.append(context.getString(R.string.everyday));
		}
		if (isqb == 0) {
			stringBuffer = new StringBuffer();
			stringBuffer.append(context.getString(R.string.never));
		}
		return stringBuffer.toString();
	}
	
	public static class ZDYHolder {
		ImageView img;
		TextView tx01;
		TextView tx02;
		TextView tx03;
		TextView tx04;

	}
	
	
	
	@Override
	public void onSlide(View view, int status) {
		// TODO 自动生成的方法存根
		if (mLastSlideViewWithStatusOn != null
				&& mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();
		}
		
		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (SlideView) view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(context,NZEditActivity.class);
		Bundle bundle = new Bundle();
		NZYData data  = mDataList.get(position);
		bundle.putSerializable("data", data);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

}
