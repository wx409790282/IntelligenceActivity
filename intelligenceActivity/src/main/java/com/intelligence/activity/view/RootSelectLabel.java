package com.intelligence.activity.view;

import com.intelligence.activity.R;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RootSelectLabel extends RelativeLayout{
	Button button;
	Handler handler;
	public RootSelectLabel(Context context,final Handler handler) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.handler = handler;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		inflater.inflate(R.layout.root_select_label, this);
		
		button = (Button) findViewById(R.id.btn);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("bbbbbbb");
				handler.sendEmptyMessage(0);
			}
		});
	}

	public void setTextSring(String str){
		button.setText(str);
	}
	
	public void setColor(int color){
		button.setTextColor(color);
	}
}
