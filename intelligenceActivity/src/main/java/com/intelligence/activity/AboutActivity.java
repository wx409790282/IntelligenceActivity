package com.intelligence.activity;

import android.os.Bundle;

public class AboutActivity extends BaseActivity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		setTitle(this.getString(R.string.about));
		setLeftOnClick(null);
		setRigter(false, this.getString(R.string.save));
	}
}
