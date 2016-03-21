package com.intelligence.activity.kettle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.db.DBNullException;
import com.intelligence.activity.db.DBhelperManager;
import com.intelligence.activity.db.DBhelperManager.ZDYData;
import com.intelligence.activity.view.EditDialog;
import com.intelligence.activity.view.ListDialog;
import com.intelligence.activity.view.PromptDialog.DialogListener;
import com.intelligence.activity.view.RotateDrawView;

/**
 * 自定义编辑界面
 * 
 * @author Administrator
 * 
 */
public class ZDYEditActivity extends BaseActivity implements OnClickListener {

	// private SeekBar seeBar;
	// /******温度滚轮******/
	// private ProgressView progressView;
	//
	// private TextView edit_txt_01;

	private RotateDrawView drawView;

	private RelativeLayout edit_layout;

	private CheckBox Switch1, Switch2, Switch3;

	private RelativeLayout zdy_layout_01_id, zdy_layout_02_id,
			zdy_layout_03_id;

	private TextView zdy_txt_layout_02, zdy_txt_layout_03, zdy_txt_layout_04,
			zdy_txt_layout_05, zdy_txt_layout21, layout_01;
	List<HashMap<String, Object>> mDataList = new ArrayList<HashMap<String, Object>>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_zdy);

		// edit_txt_01 = (TextView)findViewById(R.id.edit_txt_01);
		// progressView = (ProgressView)findViewById(R.id.arcProgressbar1);
		// seeBar = (SeekBar) findViewById(R.id.seekBar1);

		drawView = (RotateDrawView) findViewById(R.id.rotatedrawview);

		setTitle(this.getString(R.string.custom));
		setLeftOnClick(null);
		setRigter(true, this.getString(R.string.finished));

		edit_layout = (RelativeLayout) findViewById(R.id.zdy_edit_layout_id);
		edit_layout.setOnClickListener(this);

		Switch1 = (CheckBox) findViewById(R.id.Switch1);
		Switch2 = (CheckBox) findViewById(R.id.Switch2);
		Switch3 = (CheckBox) findViewById(R.id.Switch3);

		zdy_layout_01_id = (RelativeLayout) findViewById(R.id.zdy_layout_01_id);
		zdy_layout_02_id = (RelativeLayout) findViewById(R.id.zdy_layout_02_id);
		zdy_layout_03_id = (RelativeLayout) findViewById(R.id.zdy_layout_03_id);
		zdy_layout_01_id.setOnClickListener(this);
		zdy_layout_02_id.setOnClickListener(this);
		zdy_layout_03_id.setOnClickListener(this);

		zdy_txt_layout_02 = (TextView) findViewById(R.id.zdy_txt_layout_02);
		zdy_txt_layout_03 = (TextView) findViewById(R.id.zdy_txt_layout_03);
		zdy_txt_layout_04 = (TextView) findViewById(R.id.zdy_txt_layout_04);
		zdy_txt_layout_05 = (TextView) findViewById(R.id.zdy_txt_layout_05);
		zdy_txt_layout21 = (TextView) findViewById(R.id.zdy_txt_layout_21);
		layout_01 = (TextView) findViewById(R.id.layout_01);

		zdy_txt_layout_03.setOnClickListener(this);
		zdy_txt_layout_04.setOnClickListener(this);
		layout_01.setOnClickListener(this);
		zdy_txt_layout_02.setOnClickListener(this);

		Switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO 自动生成的方法存根
				if (!isChecked) {
					Switch2.setChecked(false);
					data.ZDY_ISZF = "0";
					Switch2.setChecked(false);
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append(ZDYEditActivity.this.getString(R.string.purify_length));
					stringBuffer.append(0);
					stringBuffer.append(ZDYEditActivity.this.getString(R.string.minite));
					startTime = 0;
					zdy_txt_layout_03.setText(stringBuffer.toString());
					data.ZDY_TIME = "0";
					zdy_txt_layout21.setTextColor(getResources().getColor(
							R.color.gray));
					zdy_txt_layout_03.setTextColor(getResources().getColor(
							R.color.gray));
					zdy_txt_layout_05.setText(ZDYEditActivity.this.getString(R.string.caculate_time)
							+ (startTime + endTime) + ZDYEditActivity.this.getString(R.string.minite));
				} else {
					zdy_txt_layout21.setTextColor(getResources().getColor(
							R.color.black));
					data.ZDY_ISZF = "1";

					Switch2.setChecked(true);
				}
			}
		});

		Switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO 自动生成的方法存根
				if (!isChecked) {
					data.ZDY_TIME = "1";
					zdy_txt_layout_03.setTextColor(getResources().getColor(
							R.color.gray));
					startTime = 1;
					zdy_txt_layout_03.setText(ZDYEditActivity.this.getString(R.string.purify_length)+" 1 "+ZDYEditActivity.this.getString(R.string.minite));
				} else {
					if(!Switch1.isChecked()){
						Switch2.setChecked(false);
					}else{
						zdy_txt_layout_03.setTextColor(getResources().getColor(
								R.color.black));
					}
				}
				zdy_txt_layout_05.setText(ZDYEditActivity.this.getString(R.string.caculate_time) + (startTime + endTime)
						+ ZDYEditActivity.this.getString(R.string.minite));
			}
		});

		Switch3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO 自动生成的方法存根
				if (!isChecked) {
					data.ZDY_TIME1 = "0";
					zdy_txt_layout_04.setTextColor(getResources().getColor(
							R.color.gray));
					endTime = 1;
					zdy_txt_layout_04.setText(ZDYEditActivity.this.getString(R.string.insulation_length)+"1"+ ZDYEditActivity.this.getString(R.string.minite));
				} else {
					zdy_txt_layout_04.setTextColor(getResources().getColor(
							R.color.black));
				}
				zdy_txt_layout_05.setText(ZDYEditActivity.this.getString(R.string.caculate_time) + (startTime + endTime)
						+ ZDYEditActivity.this.getString(R.string.minite));
			}
		});

		setindex(0);

		getIntentE();
	}

	private void setindex(int state) {
		if (state == 0) {
			zdy_txt_layout21
					.setTextColor(getResources().getColor(R.color.gray));
			zdy_txt_layout_03.setTextColor(getResources()
					.getColor(R.color.gray));
			zdy_txt_layout_04.setTextColor(getResources()
					.getColor(R.color.gray));
		} else if (state == 1) {
			zdy_txt_layout21.setTextColor(getResources()
					.getColor(R.color.black));
			zdy_txt_layout_03.setTextColor(getResources()
					.getColor(R.color.gray));
			zdy_txt_layout_04.setTextColor(getResources()
					.getColor(R.color.gray));
		} else if (state == 2) {
			zdy_txt_layout21.setTextColor(getResources()
					.getColor(R.color.black));
			zdy_txt_layout_03.setTextColor(getResources().getColor(
					R.color.black));
			zdy_txt_layout_04.setTextColor(getResources()
					.getColor(R.color.gray));
		} else if (state == 3) {
			zdy_txt_layout21.setTextColor(getResources()
					.getColor(R.color.black));
			zdy_txt_layout_03.setTextColor(getResources().getColor(
					R.color.black));
			zdy_txt_layout_04.setTextColor(getResources().getColor(
					R.color.black));
		} else if (state == 3) {
			zdy_txt_layout21.setTextColor(getResources()
					.getColor(R.color.black));
			zdy_txt_layout_03.setTextColor(getResources()
					.getColor(R.color.gray));
			zdy_txt_layout_04.setTextColor(getResources().getColor(
					R.color.black));
		}

	}

	private void getIntentE() {
		if (getIntent() != null && getIntent().getExtras() != null) {
			Bundle bundle = getIntent().getExtras();
			data = new ZDYData();
			data = (ZDYData) bundle.getSerializable("data");
			if (data.ZDY_NAME != null) {
				zdy_txt_layout_02.setText(data.ZDY_NAME);
			}
			if (data.ZDY_ISZF != null && !data.ZDY_ISZF.equals("0")) {
				Switch1.setChecked(true);
			} else {
				Switch2.setChecked(false);
			}
			if (!data.ZDY_TIME.equals("0")) {
				startTime = Integer.parseInt(data.ZDY_TIME);
				Switch2.setChecked(true);
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(ZDYEditActivity.this.getString(R.string.purify_length));
				stringBuffer.append(startTime);
				stringBuffer.append(ZDYEditActivity.this.getString(R.string.minite));

				zdy_txt_layout_03.setText(stringBuffer.toString());
				setindex(2);
			}
			if (!data.ZDY_TIME1.equals("0")) {
				endTime = Integer.parseInt(data.ZDY_TIME1);
				Switch3.setChecked(true);
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(ZDYEditActivity.this.getString(R.string.insulation));
				stringBuffer.append(endTime);
				stringBuffer.append(ZDYEditActivity.this.getString(R.string.minite));
				setindex(4);
				zdy_txt_layout_04.setText(stringBuffer.toString());
			}

			zdy_txt_layout_05.setText(ZDYEditActivity.this.getString(R.string.caculate_time) + (startTime + endTime)
					+ ZDYEditActivity.this.getString(R.string.minite));

			data.ZDY_SW = data.ZDY_SW.replaceAll("°c", "");
			// =====注释
			drawView.setTemp(Integer.parseInt(data.ZDY_SW));
			// edit_txt_01.setText(data.ZDY_SW+"°c");

			int progress = Integer.parseInt(data.ZDY_SW);

			// seeBar.setProgress(progress);
			// progressView.setProgress(progress);
			index = 0;
		} else {
			index = 1;
			Switch2.setChecked(false);
		}
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();

	}

	private int index;

	private void initData(int length) {
		mDataList.clear();
		mDataList = new ArrayList<HashMap<String, Object>>();
		for (int i = 1; i < length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("txt", i);
			mDataList.add(map);
		}

	}

	/******
	 * ‘ 添加或者修改自定义的数据
	 * ******/
	private ZDYData data = new ZDYData();

	public void setRightOnClick() {
		showPromptDialog(ZDYEditActivity.this.getString(R.string.add_success), new DialogListener() {
			@Override
			public void doComfirm(int flag) {
				// TODO 自动生成的方法存根
				try {
					if (data.ZDY_NAME == null)
						data.ZDY_NAME = "牛奶";

					data.ZDY_ISDE = 0;
					if (data.ZDY_ISOPEN == 0)
						data.ZDY_ISOPEN = 0;
					if (data.ZDY_ISZF == null) {
						data.ZDY_ISZF = "0";
					}
					// ========注释
					data.ZDY_SW = drawView.getTemp() + "°c";// edit_txt_01.getText().toString();
					// if(data.ZDY_TIME == null)
					// data.ZDY_TIME = "0";
					// if(data.ZDY_TIME1 == null)
					// data.ZDY_TIME1 = "0";
					if (Switch2.isChecked()) {
						data.ZDY_TIME = zdy_txt_layout_03.getText().toString()
								.replace("净化时间", "").replace("分钟", "")
								.replaceAll(" ", "");
					} else {
						data.ZDY_TIME = "0";
					}
					if (Switch3.isChecked()) {
						data.ZDY_TIME1 = zdy_txt_layout_04.getText().toString()
								.replace("保温时间", "").replace("分钟", "")
								.replaceAll(" ", "");
					} else {
						data.ZDY_TIME1 = "0";
					}
					if (data.ZDY_ID == null) {
						data.ZDY_ID = System.currentTimeMillis() + "";
						DBhelperManager.getInstance(ZDYEditActivity.this)
								.insert(data);
					} else {
						DBhelperManager.getInstance(ZDYEditActivity.this)
								.update(data);
					}

				} catch (DBNullException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				finish();
			}

			@Override
			public void doCancel(int flag) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void doBack(int flag) {
				// TODO 自动生成的方法存根

			}

		}, 100);

	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		if (v.getId() == R.id.zdy_edit_layout_id
				|| v.getId() == R.id.layout_01
				|| v.getId() == R.id.zdy_txt_layout_02) { // 输入框按钮操作
			EditDialog dialog = new EditDialog(this, zdy_txt_layout_02
					.getText().toString());
			dialog.setHandler(handler);
			dialog.show();
		} else if (v.getId() == R.id.zdy_layout_02_id
				|| v.getId() == R.id.zdy_txt_layout_03) { // 净化时间选择
			if (Switch2.isChecked()) {
				initData(6);
				ListDialog dialog = new ListDialog(this, handler);
				dialog.setListData(mDataList, 100);
				dialog.show();
			}
		} else if (v.getId() == R.id.zdy_layout_03_id
				|| v.getId() == R.id.zdy_txt_layout_04) {// 保温时间选择
			if (Switch3.isChecked()) {
				initData(60);
				ListDialog dialog = new ListDialog(this, handler);
				dialog.setListData(mDataList, 101);
				dialog.show();
			}
		}
	}

	private int startTime, endTime;

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			super.handleMessage(msg);
			if (msg.what == 100) {
				startTime = Integer.parseInt(msg.obj.toString());
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("净化时间    ");
				stringBuffer.append(startTime);
				stringBuffer.append(" 分钟");

				zdy_txt_layout_03.setText(stringBuffer.toString());

				zdy_txt_layout_05.setText("预计时间    " + (startTime + endTime)
						+ " 分钟");
				data.ZDY_TIME = msg.obj.toString();
			} else if (msg.what == 101) {
				endTime = Integer.parseInt(msg.obj.toString());
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("保温时间    ");
				stringBuffer.append(endTime);
				stringBuffer.append(" 分钟");

				zdy_txt_layout_04.setText(stringBuffer.toString());
				zdy_txt_layout_05.setText("预计时间    " + (startTime + endTime)
						+ " 分钟");

				data.ZDY_TIME1 = msg.obj.toString();
			} else if (msg.what == 99) {
				data.ZDY_NAME = msg.obj.toString();
				zdy_txt_layout_02.setText(data.ZDY_NAME);
			}
		}
	};
}
