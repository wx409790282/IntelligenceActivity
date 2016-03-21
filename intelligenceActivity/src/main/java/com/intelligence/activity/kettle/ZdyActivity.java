package com.intelligence.activity.kettle;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.adpter.ZdyAdpter;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.db.DBhelperManager;
import com.intelligence.activity.db.DBhelperManager.ZDYData;
import com.intelligence.activity.view.ListViewCompat;

/**
 * @author Administrator
 *设置温度时间等 
 */
public class ZdyActivity extends BaseActivity {
	private ListViewCompat listview;

	private ZdyAdpter adpter;

	private List<ZDYData> mList;

	private LinearLayout title_layoutLayout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zdy);

		listview = (ListViewCompat) findViewById(R.id.girdview_id);

		setTitle(this.getString(R.string.custom));
		setLeftOnClick(null);
		setRigter(true, this.getString(R.string.add));
		title_layoutLayout = (LinearLayout) findViewById(R.id.title_list_layout);
	}

	private void initTitle() {
		title_layoutLayout.removeAllViews();
		List<ZDYData> mList = DBhelperManager.getInstance(this).getAlermList(1);
		int length = mList.size();
		if (length > 3) {
			findViewById(R.id.title_layout2).setVisibility(View.GONE);
			findViewById(R.id.title_layout3).setVisibility(View.VISIBLE);

			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					WifiTools.dip2px(this, 100), LayoutParams.FILL_PARENT);

			for (int i = 0; i < mList.size(); i++) {
				TextView view = new TextView(this);
				view.setLayoutParams(layoutParams);
				// layoutParams.setMargins(50, 25, 50, 25);
				view.setText(mList.get(i).ZDY_NAME);
				view.setTextColor(getResources().getColor(R.color.black));
				view.setTextSize(25);
				view.setGravity(Gravity.CENTER);
				ImageView iview = new ImageView(this);
				iview.setBackgroundResource(R.drawable.line_shu);
				iview.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
				title_layoutLayout.addView(view);
				title_layoutLayout.addView(iview);
			}
		} else {
			findViewById(R.id.title_layout3).setVisibility(View.GONE);
			findViewById(R.id.title_layout2).setVisibility(View.VISIBLE);
			TextView[] view = new TextView[3];
			view[0] = (TextView) findViewById(R.id.txt);
			view[1] = (TextView) findViewById(R.id.txt1);
			view[2] = (TextView) findViewById(R.id.txt2);

			for (int i = 0; i < 3; i++) {
				if (i < mList.size()) {
					view[i].setText(mList.get(i).ZDY_NAME);
					view[i].setVisibility(View.VISIBLE);
				} else {
					view[i].setVisibility(View.GONE);
				}
			}
		}
	}

	private Handler hanlder = new Handler() {
		public void handleMessage(android.os.Message msg) {
			initTitle();
		};
	};

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		mList = DBhelperManager.getInstance(this).getAlermList(0);
		if (mList != null) {
			adpter = new ZdyAdpter(this, mList);
			adpter.setHandler(hanlder);
			listview.setAdapter(adpter);
			listview.setOnItemClickListener(adpter);
		}
		initTitle();
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();

	}

	@Override
	protected void onRestart() {
		// TODO 自动生成的方法存根
		super.onRestart();

	}

	public void setRightOnClick() {
		Intent intent = new Intent(this, ZDYEditActivity.class);
		startActivity(intent);
	}
}
