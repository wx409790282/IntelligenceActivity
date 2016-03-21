package com.intelligence.activity.adpter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligence.activity.addpart.AddActivity;
import com.intelligence.activity.addpart.MachineActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.attendance.AttendanceMain;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.kettle.kettleActivity;
import com.intelligence.activity.switchs.SwitchsActivity;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.humidifier.HumActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MainItemAdpter extends BaseAdapter implements OnItemClickListener {

	private List<Machine> mDataList;

	private Context context;

	private LayoutInflater inf;

	private String state = "0";

	private boolean isEdited;

	DisplayImageOptions options;

	public boolean isEnd() {
		return isEdited;
	}

	public void setEnd(boolean isEnd) {
		this.isEdited = isEnd;
	}

	public void setState(String state) {
		this.state = state;
	}

	public MainItemAdpter(Context context, List<Machine> dataList) {
		options = new DisplayImageOptions.Builder()
				.showImageOnFail(R.drawable.error_img).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();

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
			return mDataList.size() + 1;
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

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		Holder holder;
		if (contentView == null) {
			holder = new Holder();
			contentView = inf.inflate(R.layout.main_item, null);
			holder.tx01 = (TextView) contentView
					.findViewById(R.id.main_item_txt);
			holder.iv = (ImageView) contentView
					.findViewById(R.id.imageView_ItemImage);
			contentView.setTag(holder);
		} else {
			holder = (Holder) contentView.getTag();
		}

		try {

			if (position < mDataList.size()) {

				Machine bean = mDataList.get(position);
				bean.setName(WifiTools.getdevname(bean.getMachineid(), context));
				holder.tx01.setText(bean.getName());

				if (bean.getIsonline().equals("offline")) {
					Drawable image = context.getResources().getDrawable(
							R.drawable.yd3_sh);
					image.mutate().setAlpha(100);
					holder.iv.setAlpha(100);
				} else {
					holder.iv.setAlpha(255);
				}
				String imageframe = bean.getMachineid().substring(0, 6);
				// ImageLoader.getInstance().displayImage(HttpUrl.IMGURL +
				// imageframe + ".png", holder.iv);
				ImageLoader.getInstance().displayImage(
						HttpUrl.IMGURL + imageframe + ".png", holder.iv,
						options, null);
			} else {

				holder.tx01.setText(context.getString(R.string.add_device));
				holder.iv.setImageResource(R.drawable.add_xiangpin_img_bg2_down);
				contentView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						Intent intent = new Intent(context, AddActivity.class);
						intent.putExtra("b", true);
						context.startActivity(intent);
					}
				});
				if (isEdited) {
					contentView.setVisibility(View.GONE);
				} else {
					contentView.setVisibility(View.VISIBLE);
				}
			}
		} catch (Exception e) {
			System.out.println("出错");
		}
		return contentView;
	}

	private String getUrl(String code) {
		String url = HttpUrl.IMGURL + code + ".png";
		Log.e("aa", url);
		return url;
	}

	public static class Holder {
		TextView tx01;
		public String idString;
		ImageView iv;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Machine bean = mDataList.get(position);
		if (state.equals("0")) {
			// if(bean.getIsonline().equals("online")){
			if (bean.getIsonline().equals("offline")) {
				Toast.makeText(context,context.getString(R.string.device_offline), Toast.LENGTH_SHORT).show();
				return;
			}
			String devs = bean.getMachineid().substring(0, 2);
			if (devs.equals("01")) {//shuihu
				Intent intent = new Intent(context, kettleActivity.class);
				intent.putExtra("data", bean);
				context.startActivity(intent);
			} else if (devs.equals("02")) {//jiashiqi
				Intent intent = new Intent(context, HumActivity.class);
				intent.putExtra("data", bean);
				context.startActivity(intent);
			}else if (devs.equals("03")) {//deng
				Intent intent = new Intent(context, HumActivity.class);
				intent.putExtra("data", bean);
				context.startActivity(intent);
			}else if (devs.equals("08")) {//kaoqinji
				Intent intent = new Intent(context, AttendanceMain.class);
				intent.putExtra("data", bean);
				context.startActivity(intent);
			}else if (devs.equals("13")) {//switchs
				Intent intent = new Intent(context, SwitchsActivity.class);
				intent.putExtra("data", bean);
				context.startActivity(intent);
			}
		} else {
			Intent intent = new Intent(context, MachineActivity.class);
			intent.putExtra("MACHINEID", bean.getMachineid());
			intent.putExtra("isDele", true);
			context.startActivity(intent);
		}
	}

}
