package com.intelligence.activity.adpter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligence.activity.R;
import com.intelligence.activity.kettle.ZDYEditActivity;
import com.intelligence.activity.db.DBhelperManager;
import com.intelligence.activity.db.DBhelperManager.ZDYData;
import com.intelligence.activity.view.MessageItem;
import com.intelligence.activity.view.SlideView;
import com.intelligence.activity.view.SlideView.OnSlideListener;

public class ZdyAdpter extends BaseAdapter implements OnItemClickListener,OnSlideListener{

	public class Items extends MessageItem{
		ZDYData item;
	}
	private List<Items> listData = new ArrayList<Items>();
	
	private SlideView mLastSlideViewWithStatusOn;
	
	
	private List<ZDYData> mDataList;

	private Context context;

	private LayoutInflater inf;

	private Handler handler;
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ZdyAdpter(Context context,List<ZDYData> dataList) {
		mDataList = dataList;
		this.context = context;
		inf = LayoutInflater.from(context);
		for (int i = 0; i < mDataList.size(); i++) {
			Items itemts = new Items();
			itemts.item = dataList.get(i);
			listData.add(itemts);
		}
	}
	
	public void addItemData(ZDYData data){
		mDataList.add(data);
		Items itemts = new Items();
		itemts.item = data;
		listData.add(itemts);
		this.notifyDataSetInvalidated();
	}

	public void cleanArray() {
		for (int i = 0; i < mDataList.size(); i++) {
			mDataList.clear();
		}
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
		SlideView sview = null;
		final ZDYHolder holder;
		sview = (SlideView)contentView;
		if (sview == null) {
			
			View view = LayoutInflater.from(context).inflate(
					R.layout.zdy_layout_item, parent, false);
			holder = new ZDYHolder(view);
			sview = new SlideView(context);
			sview.setContentView(view);
			sview.setTag(holder);
			sview.setOnSlideListener(this);
		} else {
			holder = (ZDYHolder)sview.getTag();;
		}

		
		try {
			final Items item = listData.get(index);
			item.slideView = sview;
			item.slideView.reset();
			final ZDYData bean = listData.get(index).item;
			if(index == 0){
				sview.setIsupdata(true);
			}
			
//			contentView.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO 自动生成的方法存根
//					try {
//						Intent intent = new Intent(context,ZDYEditActivity.class);
//						Bundle bundle = new Bundle();
//						bundle.putString("ZDY_ID",bean.ZDY_ID);
//						bundle.putString("ZDY_NAME",bean.ZDY_NAME);
//						bundle.putString("ZDY_SW",bean.ZDY_SW);
//						bundle.putString("ZDY_TIME",bean.ZDY_TIME);
//						bundle.putString("ZDY_TIME1",bean.ZDY_TIME1);
//						bundle.putInt("ZDY_ISDE",bean.ZDY_ISDE);
//						bundle.putInt("ZDY_ISOPEN",bean.ZDY_ISOPEN);
//						
//						bundle.putSerializable("data",bean);
//						intent.putExtras(bundle);
//						context.startActivity(intent);
//					} catch (Exception e) {
//						// TODO: handle exception
//					}
//				}
//			});
//			
			if(bean.ZDY_ISDE == 0){
				sview.setButtonOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						if(bean.ZDY_ISDE == 1 || index == 0)
							return;
						DBhelperManager.getInstance(context).delete(bean.ZDY_ID);
						listData.remove(item);
						notifyDataSetInvalidated();
						if(handler != null){
							handler.sendEmptyMessage(100);
						}
					}
				});
				
				holder.img.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						if(bean.ZDY_ISDE == 1)
							return;
						if(bean.ZDY_ISOPEN == 0){
							bean.ZDY_ISOPEN = 1;
							v.setBackgroundResource(R.drawable.r1);
						}else{
							bean.ZDY_ISOPEN = 0;
							v.setBackgroundResource(R.drawable.r2);
						}
						DBhelperManager.getInstance(context).update(bean);
						if(handler != null){
							handler.sendEmptyMessage(100);
						}
					}
				});
			}else{
				holder.img.setBackgroundResource(R.drawable.r2);
				holder.img.setOnClickListener(null);
			}
			holder.tx01.setText(bean.ZDY_NAME);
			if(bean.ZDY_TIME.equals("0")){
				holder.tx03.setVisibility(View.GONE);
			}else{
				holder.tx03.setVisibility(View.VISIBLE);
				holder.tx03.setText(bean.ZDY_TIME+"min");
			}
			if(bean.ZDY_TIME1.equals("0")){
				holder.tx04.setVisibility(View.GONE);
			}else{
				holder.tx04.setText(bean.ZDY_TIME1+"min");
			}
			if(bean.ZDY_ISZF.equals("1")){
				holder.zdy_img_id.setVisibility(View.VISIBLE);
			}else{
				holder.zdy_img_id.setVisibility(View.GONE);
			}
			holder.tx02.setText(bean.ZDY_SW);
			
			if(bean.ZDY_ISOPEN == 1){
				holder.img.setBackgroundResource(R.drawable.r1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sview;
	}

	public static class ZDYHolder {
		ImageView img;
		TextView tx01;
		TextView tx02;
		TextView tx03;
		TextView tx04;
		ImageView zdy_img_id;
		ZDYHolder(View view){
			zdy_img_id = (ImageView)view.findViewById(R.id.zdy_img_id);
			img = (ImageView)view.findViewById(R.id.toggleButton1);
			tx01 = (TextView)view.findViewById(R.id.zdy_txt_id);
			tx02 = (TextView)view.findViewById(R.id.wd_txt_id);
			tx03 = (TextView)view.findViewById(R.id.min_txt1_id);
			tx04 = (TextView)view.findViewById(R.id.min_txt2_id);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(position == 0)
			return;
		final ZDYData bean = listData.get(position).item;
		try {
		Intent intent = new Intent(context,ZDYEditActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("ZDY_ID",bean.ZDY_ID);
		bundle.putString("ZDY_NAME",bean.ZDY_NAME);
		bundle.putString("ZDY_SW",bean.ZDY_SW);
		bundle.putString("ZDY_TIME",bean.ZDY_TIME);
		bundle.putString("ZDY_TIME1",bean.ZDY_TIME1);
		bundle.putInt("ZDY_ISDE",bean.ZDY_ISDE);
		bundle.putInt("ZDY_ISOPEN",bean.ZDY_ISOPEN);
		
		bundle.putSerializable("data",bean);
		intent.putExtras(bundle);
		context.startActivity(intent);
		} catch (Exception e) {
		// TODO: handle exception
		}
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


}
