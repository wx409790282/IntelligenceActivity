package com.intelligence.activity.setting;

import android.os.Bundle;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;

public class MoreActivity extends BaseActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		
		setTitle(this.getString(R.string.more_product));
		setLeftOnClick(null);
		setRigter(false, this.getString(R.string.save));
	}
}

