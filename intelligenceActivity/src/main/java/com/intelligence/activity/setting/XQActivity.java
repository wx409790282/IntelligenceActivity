package com.intelligence.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ListView;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.kettle.NZEditActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.adpter.XQAdpter;

public class XQActivity extends BaseActivity {
	private ListView listview;

	private XQAdpter adpter;

	String NZ_SW = "0000000";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zdy1);

		listview = (ListView) findViewById(R.id.girdview_id);

		if (!TextUtils.isEmpty(getIntent().getStringExtra("NZ_SW"))) {
			NZ_SW = getIntent().getStringExtra("NZ_SW");
		} else {
			NZ_SW = "0000000";
		}

		setTitle(this.getString(R.string.weekly_repeat));
		setLeftOnClick(null);
		setRigter(true, this.getString(R.string.finished));

		adpter = new XQAdpter(this, handler, NZ_SW);
		listview.setAdapter(adpter);
		listview.setOnItemClickListener(adpter);
	}

	private String txtString;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			txtString = msg.obj.toString();
		};
	};

	public void setRightOnClick() {
		Intent intent = new Intent(this, NZEditActivity.class);
		intent.putExtra("txt", txtString);
		setResult(11, intent);
		finish();
	}
}