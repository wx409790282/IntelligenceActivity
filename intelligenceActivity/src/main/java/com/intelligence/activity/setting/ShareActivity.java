package com.intelligence.activity.setting;

import android.os.Bundle;
import android.widget.GridView;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;

public class ShareActivity extends BaseActivity {
	private GridView  listview;
	
	private ShareAdpter adpter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		
		listview = (GridView)findViewById(R.id.grid_view_id);
		
		
		adpter = new ShareAdpter(this);
		listview.setAdapter(adpter);
		
		
		setTitle(this.getString(R.string.share));
		setLeftOnClick(null);
		setRigter(false, this.getString(R.string.save));
	}
	
	
	
}
