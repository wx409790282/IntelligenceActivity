package com.intelligence.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;

public class SetActivity extends BaseActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		
		setTitle(this.getString(R.string.setting));
		setLeftOnClick(null);
		setRigter(false, this.getString(R.string.save));
		
		RelativeLayout add_layout_id = (RelativeLayout) findViewById(R.id.add_layout_id);
		add_layout_id.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SetActivity.this, MsgNotActivity.class);
				startActivity(intent);
			}
		});
		
		
		RelativeLayout add_layout1_id = (RelativeLayout) findViewById(R.id.add_layout1_id);
		add_layout1_id.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SetActivity.this, WifiSetActivity.class);
				startActivity(intent);
			}
		});
		
		
	}
}
