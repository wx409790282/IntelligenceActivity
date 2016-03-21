package com.intelligence.activity.kettle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.db.DBhelperManager;
import com.intelligence.activity.db.DBhelperManager.ZDYData;
import com.intelligence.activity.db.NZ_DBhelperManager;
import com.intelligence.activity.db.NZ_DBhelperManager.NZYData;
import com.intelligence.activity.setting.XQActivity;
import com.intelligence.activity.view.EditDialog;
import com.intelligence.activity.view.ListDialog;
import com.intelligence.activity.view.PromptDialog.DialogListener;
import com.intelligence.activity.view.ScrollerNumberPicker;
import com.intelligence.activity.view.ScrollerNumberPicker.OnSelectListener;
import com.intelligence.activity.view.SwitchButton;
import com.intelligence.activity.view.SwitchButton.OnChangeListener;
import com.intelligence.activity.view.TimeDialog;

/**
 * 闹钟编辑界面
 *
 * @author Administrator
 *
 */
public class NZEditActivity extends BaseActivity implements OnClickListener,
		DialogListener {

	private TextView order_time_layout, nz_moshi_layout, nz_eidt_txt,
			nz_time_txt_layout, nz_lever_txt, layout_01, layout_011;

	private RelativeLayout nz_edit_layout_id, nz_time_layout_id, nz_lever_id;

	/** 滑动控件 */
	private TimePicker timePicker;
	private ScrollerNumberPicker counyPicker;

	private CheckBox Switch1, Switch2, Switch3;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_nz);
		setTitle("编辑预约");
		setLeftOnClick(null);
		setRigter(true, "完成");

		numberStrings=new String[]{ this.getString(R.string.monday),getString(R.string.tuesday),getString(R.string.wednesday),
				getString(R.string.thursday),getString(R.string.friday),getString(R.string.saturday),getString(R.string.sunday)};

		initView();
		getIntentExtras();
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();

	}

	private void getIntentExtras() {
		if (getIntent() != null && getIntent().getExtras() != null) {
			data = (NZYData) getIntent().getExtras().getSerializable("data");
			order_time_layout.setText(data.NZ_TIME);
			for (int i = 0; i < mList.size(); i++) {
				ZDYData map = mList.get(i);
				if (map.ZDY_NAME.equals(data.NZ_MS)) {
					nz_moshi_layout.setText(map.ZDY_NAME);
					counyPicker.setDefault(i);
				}

			}
			nz_eidt_txt.setText(data.NZ_NAME);
			if (!data.NZ_SW.equals("0000000"))
				nz_time_txt_layout.setText(getWeekly(data.NZ_SW));

			if (data.NZ_START == 0) {
				Switch1.setChecked(true);
			}

			if (data.NZ_END == 0) {
				Switch2.setChecked(true);
			}

			if (!data.NZ_LELVER.equals("0.0L")) {
				Switch3.setChecked(true);
			}

			String[] strings = data.NZ_TIME.split(":");

			timePicker.setCurrentHour(Integer.valueOf(strings[0]));
			timePicker.setCurrentMinute(Integer.valueOf(strings[1]));

		} else {
			ZDYData map = mList.get(0);
			data.NZ_ZDYID = map.ZDY_ID;
			data.NZ_MS = map.ZDY_NAME;
		}
	}

	private NZYData data = new NZYData();

	public void setRightOnClick() {
		try {
			if (data.NZ_ID == null)
				showPromptDialog("是否确认添加新的预约", this, -1, false);
			else
				showPromptDialog("是否确认修改预约", this, -1, false);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	private List<ZDYData> mList;
	private List<HashMap<String, Object>> mDataList = new ArrayList<HashMap<String, Object>>();
	private ArrayList<String> mDataList2 = new ArrayList<String>();
	private List<HashMap<String, Object>> mDataList1 = new ArrayList<HashMap<String, Object>>();

	private void initView() {
		initData();
		nz_eidt_txt = (TextView) findViewById(R.id.nz_eidt_txt);
		nz_time_txt_layout = (TextView) findViewById(R.id.nz_time_txt_layout);
		order_time_layout = (TextView) findViewById(R.id.order_time_layout);
		order_time_layout.setOnClickListener(this);

		nz_moshi_layout = (TextView) findViewById(R.id.nz_moshi_layout);
		nz_moshi_layout.setOnClickListener(this);

		nz_edit_layout_id = (RelativeLayout) findViewById(R.id.nz_edit_layout_id);
		nz_time_layout_id = (RelativeLayout) findViewById(R.id.nz_time_layout_id);
		nz_time_layout_id.setOnClickListener(this);
		nz_edit_layout_id.setOnClickListener(this);

		nz_lever_id = (RelativeLayout) findViewById(R.id.lever_id);
		nz_lever_txt = (TextView) findViewById(R.id.lever_txt_id);
		nz_lever_id.setOnClickListener(this);
		Switch1 = (CheckBox) findViewById(R.id.Switch01);
		Switch2 = (CheckBox) findViewById(R.id.Switch02);
		Switch3 = (CheckBox) findViewById(R.id.Switch03);

		layout_01 = (TextView) findViewById(R.id.layout_01);
		layout_011 = (TextView) findViewById(R.id.layout_011);
		layout_01.setOnClickListener(this);
		layout_011.setOnClickListener(this);
		nz_time_txt_layout.setOnClickListener(this);
		nz_eidt_txt.setOnClickListener(this);

		nz_lever_txt.setOnClickListener(this);
		Switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO 自动生成的方法存根
				if (!isChecked) {
					data.NZ_START = 1;//打开时0，没打开时1
				} else {
					data.NZ_START = 0;
				}
			}
		});

		Switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO 自动生成的方法存根
				if (!isChecked) {
					data.NZ_END = 1;
				} else {
					data.NZ_END = 0;
				}
			}
		});

		Switch3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
										 boolean isChecked) {
				// TODO 自动生成的方法存根
				if (!isChecked) {
					data.NZ_LELVER = "0.0L";
					nz_lever_txt.setText("水位 ＜" + data.NZ_LELVER);
				}else{
					nz_lever_txt.setText("水位 ＜" + data.NZ_LELVER);
				}
			}
		});

		timePicker = (TimePicker) findViewById(R.id.timepicker);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(1);
		timePicker.setCurrentMinute(1);
		counyPicker = (ScrollerNumberPicker) findViewById(R.id.couny);

		counyPicker.setData(mDataList2);
		counyPicker.setDefault(0);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				time = String.valueOf(hourOfDay);
				time1 =String.valueOf(minute);
			}
		});

		counyPicker.setOnSelectListener(new OnSelectListener() {
			@Override
			public void endSelect(int id, String text) {
				// TODO 自动生成的方法存根
				ZDYData map = mList.get(id);
				data.NZ_ZDYID = map.ZDY_ID;
				data.NZ_MS = map.ZDY_NAME;
			}

			@Override
			public void selecting(int id, String text) {
				// TODO 自动生成的方法存根
			}

		});
	}

	private String time = "1", time1 = "1";

	private ArrayList<String> getData(int length) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < length; i++) {
			if (i < 10)
				list.add("0" + i + "");
			else
				list.add(i + "");
		}
		return list;
	}

	private void initData() {
		mList = DBhelperManager.getInstance(this).getAlermList(0);
		for (int i = 0; i < mList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("txt", mList.get(i).ZDY_NAME);
			map.put("id", mList.get(i).ZDY_ID);
			mDataList.add(map);
			mDataList2.add(mList.get(i).ZDY_NAME);
		}

		for (int i = 0; i < f.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("txt", f[i] + "L");
			mDataList1.add(map);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if (R.id.order_time_layout == v.getId()) {
			TimeDialog dialog = new TimeDialog(this, handler);
			dialog.show();
		} else if (R.id.nz_moshi_layout == v.getId()) {
			ListDialog dialog = new ListDialog(this, handler);
			dialog.setListData(mDataList, 100);

			dialog.show();
		} else if (R.id.nz_edit_layout_id == v.getId()
				|| R.id.layout_011 == v.getId()
				|| R.id.nz_eidt_txt == v.getId()) {
			EditDialog dialog = new EditDialog(this, nz_eidt_txt.getText()
					.toString());
			dialog.setHandler(handler);
			dialog.show();
		} else if (R.id.nz_time_layout_id == v.getId()
				|| R.id.layout_01 == v.getId()
				|| R.id.nz_time_txt_layout == v.getId()) {
			Intent intent = new Intent(this, XQActivity.class);
			intent.putExtra("NZ_SW", data.NZ_SW);
			startActivityForResult(intent, 11);
		} else if (R.id.lever_id == v.getId() || R.id.lever_txt_id == v.getId()) {

			if (!Switch3.isChecked()) {
				return;
			}

			ListDialog dialog = new ListDialog(this, handler);
			dialog.setListData(mDataList1, 103);
			dialog.show();
		}
	}

	float f[] = new float[] { 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f, 1.1f, 1.2f,
			1.3f, 1.4f, 1.5f, 1.6f, 1.7f, };

	protected void onActivityResult(int requestCode, int resultCode,
									Intent intent) {
		try {
			if (requestCode != 11) {
				return;
			}
			if (intent != null) {
				data.NZ_SW = intent.getStringExtra("txt");
				nz_time_txt_layout.setText(getWeekly(data.NZ_SW));
			}
		} catch (Exception e) {

		}
		super.onActivityResult(requestCode, resultCode, intent);
	}

	private String[] numberStrings ;
	private String getWeekly(String strings) {
		StringBuffer stringBuffer = new StringBuffer();
		int isqb = 0;
		for (int i = 0; i < 7; i++) {
			char c = strings.charAt(i);
			StringBuffer B = new StringBuffer();
			B.append(c);
			if (B.toString().equals("1")) {
				isqb++;
				if(isqb!=1){
					stringBuffer.append(",");
				}
				stringBuffer.append(numberStrings[i]);
			}
		}
		if (isqb >= 7) {
			stringBuffer = new StringBuffer();
			stringBuffer.append(this.getString(R.string.everyday));
		}
		if (isqb == 0) {
			stringBuffer = new StringBuffer();
			stringBuffer.append(this.getString(R.string.never));
		}
		return stringBuffer.toString();
	}

	@Override
	public void onBackPressed() {
		// TODO 自动生成的方法存根
		super.onBackPressed();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 101) {
				data.NZ_TIME = msg.obj.toString();
				order_time_layout.setText(data.NZ_TIME);
			} else if (msg.what == 100) {
				data.NZ_MS = msg.obj.toString();
				for (int i = 0; i < mList.size(); i++) {
					ZDYData map = mList.get(i);
					if (map.ZDY_ID.equals(data.NZ_MS)) {
						data.NZ_ZDYID = map.ZDY_ID;
						data.NZ_MS = map.ZDY_NAME;
						nz_moshi_layout.setText(data.NZ_MS);
						break;
					}
				}

			} else if (msg.what == 99) {
				data.NZ_NAME = msg.obj.toString();
				nz_eidt_txt.setText(data.NZ_NAME);
			} else if (msg.what == 103) {
				data.NZ_LELVER = msg.obj.toString();
				nz_lever_txt.setText(getString(R.string.volume)+"＜" + data.NZ_LELVER);
			}
		};
	};

	@Override
	public void doComfirm(int flag) {
		// TODO 自动生成的方法存根
		try {
			data.NZ_ISOPEN = 0;
			if (data.NZ_SW == null)//循环星期
				data.NZ_SW = "0000000";
			data.NZ_TX1 = 1;
			if (data.NZ_NAME == null)//预约名字
				data.NZ_NAME = nz_eidt_txt.getText().toString();
			if (data.NZ_LELVER == null) {
				data.NZ_LELVER = "0.0L";
			}
			data.NZ_TIME = time + ":" + time1;
			if (data.NZ_ID == null) {
				data.NZ_ID = System.currentTimeMillis() + "";
				NZ_DBhelperManager.getInstance(this).insert(data);
			} else {
				NZ_DBhelperManager.getInstance(this).update(data);
			}
			finish();
		} catch (Exception e) {

		}

	}

	@Override
	public void doCancel(int flag) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void doBack(int flag) {
		// TODO 自动生成的方法存根

	}

}