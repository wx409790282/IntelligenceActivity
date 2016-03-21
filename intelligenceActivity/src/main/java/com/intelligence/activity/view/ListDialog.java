package com.intelligence.activity.view;

import java.util.HashMap;
import java.util.List;

import android.R.bool;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.intelligence.activity.R;
import com.intelligence.activity.adpter.ListDialogAdpter;

public class ListDialog extends Dialog implements View.OnClickListener,OnItemClickListener{
	
	private Context context;
	private Handler handler;
	
	private List<HashMap<String, Object>> mlistData;
	
	private ListView listView;
	
	private ListDialogAdpter adpter;
	
	public boolean returntag = false;
	public ListDialog(Context context,Handler handler) {
		super(context,R.style.MyDialogStyleBottom);
		// TODO 自动生成的构造函数存根
		this.context = context;
		this.handler = handler;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_dialog);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		listView = (ListView)findViewById(R.id.dialog_list_id);
		if(this.mlistData != null){
			adpter = new ListDialogAdpter(context,this.mlistData);
			adpter.setHandler(handler);
			listView.setAdapter(adpter);
			listView.setOnItemClickListener(this);
		}
		
		Button button = (Button)findViewById(R.id.clean_id2);
		button.setOnClickListener(this);
	}
	private int state;
	public void setListData(List<HashMap<String, Object>> mDataList,int state){
		this.mlistData = mDataList;
		this.state = state;
 		
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if(v.getId() == R.id.clean_id2){
			dismiss();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 自动生成的方法存根
		if(handler != null){
			Message msg = new Message();
			msg.what = state;
			if(mlistData.get(position).get("id") != null){
				msg.obj = mlistData.get(position).get("id").toString();
			}else{
				msg.obj = mlistData.get(position).get("txt").toString();
			}
			
			if (returntag) {
				msg.obj = position;
			}
			
			handler.sendMessage(msg);
		}
		dismiss();
	}
	
}