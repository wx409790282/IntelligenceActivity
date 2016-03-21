package com.intelligence.activity.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligence.activity.R;
import com.intelligence.activity.view.ShareHandler;

public class ShareAdpter extends BaseAdapter {
	public static final int WEIXIN = 0x00001;
	public static final int PENGYOU = 0x00002;
	public static final int QQ = 0x00003;
	public static final int XINLANG = 0x00004;
	public static final int QQZ = 0x00005;
	
	
	public static final int[] ID = {
						WEIXIN,
						PENGYOU,
						QQ,
						XINLANG,
						QQZ,
	};
	
	public List<ShareMsg> mListData = new ArrayList<ShareAdpter.ShareMsg>();

	private Context context;

	private LayoutInflater inf;
	
	public static final int[] ImgeID = {
			R.drawable.share_icon_weixin,
			R.drawable.share_icon_penyou,
			R.drawable.share_icon_qq,
			R.drawable.share_icon_xinlan,
			R.drawable.share_icon_qqzonq,
	};
	
	public static final String[] NameID = {
			"微信",
			"朋友圈",
			"QQ",
			"新浪微博",
			"QQ空间",
	};

	public ShareAdpter(Context context) {
		this.context = context;
		inf = LayoutInflater.from(context);

		for(int i = 0 ; i < 5 ; i++){
			ShareMsg msg = new ShareMsg();
			msg.id = ID[i];
			msg.ImgID = ImgeID[i];
			msg.txt = NameID[i];
			mListData.add(msg);
		}
	}

	@Override
	public int getCount() {
		
		return mListData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int index, View contentView, ViewGroup parent) {
		View view = null;
		if (contentView == null) {
			view = inf.inflate(R.layout.share_item, null);
			SharetHolder holder = new SharetHolder();
			 holder.img = (ImageView)view.findViewById(R.id.imageView_ItemImage);
			 holder.tx01 = (TextView)view.findViewById(R.id.txt_userAge);
			 view.setTag(holder);
		} else {
			view = contentView;
		}

		SharetHolder holder = (SharetHolder) view.getTag();
		ShareMsg msg = (ShareMsg)mListData.get(index);
		try {
			holder.img.setImageResource(msg.ImgID);
			holder.tx01.setText(msg.txt);
			holder.id = msg.id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	public static class SharetHolder {
		ImageView img;
		TextView tx01;
		public int id;
	}
	
	

	private ShareHandler handler;
	public void setHandler(ShareHandler handler) {
		this.handler = handler;
	}

	
	public static class ShareMsg{
		public int id;
		public int ImgID;
		public String txt;
		
	}
}
