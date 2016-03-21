package com.intelligence.activity.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.intelligence.activity.setting.ShareAdpter;

public class ShareHandler extends Handler{
	private Context context;
	
	public ShareHandler(Context context){
		this.context = context;
	}
	
	public void handleMessage(Message msg){
		super.handleMessage(msg);
		switch(msg.what){
		case ShareAdpter.WEIXIN:
			Toast.makeText(context, "分享微信", Toast.LENGTH_SHORT).show();
			break;
		case ShareAdpter.PENGYOU:
			Toast.makeText(context, "分享朋友圈", Toast.LENGTH_SHORT).show();
			break;
		case ShareAdpter.QQ:
			Toast.makeText(context, "分享QQ", Toast.LENGTH_SHORT).show();
			break;
		case ShareAdpter.XINLANG:
			Toast.makeText(context, "分享新浪", Toast.LENGTH_SHORT).show();
			break;
		case ShareAdpter.QQZ:
			Toast.makeText(context, "分享QQ空间", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
