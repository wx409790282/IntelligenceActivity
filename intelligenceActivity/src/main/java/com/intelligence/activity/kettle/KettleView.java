package com.intelligence.activity.kettle;

import java.util.ArrayList;
import java.util.List;

import com.intelligence.activity.R;
import com.intelligence.activity.data.KettleState;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.http.HttpUrl;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KettleView extends RelativeLayout {

	private float max_lev = 1.7f;
	private float my_lev = 1.0f;
	private int temp_val = 34;
	Context context;
	List<KQipaoImage> qplist = new ArrayList<KQipaoImage>();
	private boolean beginAn;

	LinearLayout shlevzz = null;

	KettleAnView kettleAnView;

	RelativeLayout qibaobview = null;
	RelativeLayout rl = null;
	TextView levtext = null;
	TextView temptext = null;
	public ImageView zzimage = null;//水壶底座，水壶壶身，水壶状态
	public ImageView hsimage = null;
	public ImageView dzimage = null;

	KettleState itemData = null;
	Machine itemlistData = null;

	DisplayImageOptions options1;
	DisplayImageOptions options2;
	DisplayImageOptions options3;

	public String imageframe = null;
	int mtemp;

	public KettleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		options1 = new DisplayImageOptions.Builder()
		.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
		options2 = new DisplayImageOptions.Builder()
		.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
		options3 = new DisplayImageOptions.Builder()
		.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();

		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.kettle_view, this);

		qibaobview = (RelativeLayout) findViewById(R.id.qibaobview);
		rl = (RelativeLayout) findViewById(R.id.rl);
		levtext = (TextView) findViewById(R.id.levtext);
		temptext = (TextView) findViewById(R.id.temptext);
		zzimage = (ImageView) findViewById(R.id.zzimage);//zz seem not work here
		hsimage = (ImageView) findViewById(R.id.hsimage);
		kettleAnView = (KettleAnView) findViewById(R.id.kettleanview);
		shlevzz = (LinearLayout) findViewById(R.id.shlevzz);
		dzimage = (ImageView) findViewById(R.id.dzimage);

		setMy_lev(0);
		stopAn();
	}

	public void loadimage() {

		ImageLoader.getInstance()//ui is the main kettle
				.displayImage(HttpUrl.IMGURL + imageframe + "ui.png", zzimage,
						options3, null);

		ImageLoader.getInstance().displayImage(//ui is the main kettle with background
				HttpUrl.IMGURL + imageframe + "i.png", hsimage, options1, null);
		ImageLoader.getInstance()//bi is the base
				.displayImage(HttpUrl.IMGURL + imageframe + "bi.png", dzimage,
						options2, null);
		// 测试的，删除温度和数位
	}

	public void setshowview(int type) {//while it is not on the hub,
		if (type == 0) {
			qibaobview.setVisibility(View.INVISIBLE);
			levtext.setVisibility(View.INVISIBLE);
			temptext.setVisibility(View.INVISIBLE);
			zzimage.setVisibility(View.INVISIBLE);
			shlevzz.setVisibility(View.INVISIBLE);
			hsimage.setAlpha(80);
		} else {
			qibaobview.setVisibility(View.VISIBLE);
			levtext.setVisibility(View.VISIBLE);
			temptext.setVisibility(View.VISIBLE);
			zzimage.setVisibility(View.VISIBLE);
			shlevzz.setVisibility(View.VISIBLE);
			hsimage.setAlpha(255);
		}
	}

	public void setItemlistData(Machine itemlistData) {
		this.itemlistData = itemlistData;
	}

	public void setItemData(KettleState itemData) {
		this.itemData = itemData;
		mtemp = Integer.parseInt(itemData.getTemp().replace("C", ""));
		float level = Float.parseFloat(itemData.getLevel().replace("L", ""));
		setMy_lev(level);
		setTemp_val(mtemp);
		if (itemData.getState().equals("1") && itemData.getHub().equals("1")) {
			startAn();
		} else {
			stopAn();
		}
		setshowview(Integer.parseInt(itemData.getHub()));
	}

	public void setTemp_val(int temp_val) {
		this.temp_val = temp_val;
		temptext.setText(temp_val + "℃");
	}
	public void setMy_lev(float my_lev) {
		// 46 186

		this.my_lev = my_lev;

		int value_lev = (int) (186 * (1 - my_lev / max_lev));

		RelativeLayout.LayoutParams qibaobviewParams = (RelativeLayout.LayoutParams) qibaobview
				.getLayoutParams(); // 取控件textView当前的布局参数
		qibaobviewParams.height = WifiTools.dip2px(context, 186 - value_lev);// 控件的高强制设成20
		qibaobviewParams.topMargin = WifiTools.dip2px(context, 46 + value_lev);// 控件的高强制设成20

		RelativeLayout.LayoutParams kettleAnViewParams = (RelativeLayout.LayoutParams) kettleAnView
				.getLayoutParams(); // 取控件textView当前的布局参数
		kettleAnViewParams.height = WifiTools.dip2px(context, 186 - value_lev);// 控件的高强制设成20
		kettleAnViewParams.topMargin = WifiTools.dip2px(context, 46 + value_lev);// 控件的高强制设成20

		RelativeLayout.LayoutParams shlevzzParams = (RelativeLayout.LayoutParams) shlevzz
				.getLayoutParams(); // 取控件textView当前的布局参数
		shlevzzParams.topMargin = WifiTools.dip2px(context, 46 + value_lev - 10);// 控件的高强制设成20
		levtext.setText(my_lev + "L");
	}

	public void stopAn() {
		kettleAnView.setViewState(0);
	}

	public void startAn() {
		kettleAnView.setViewState(1);
	}


}
