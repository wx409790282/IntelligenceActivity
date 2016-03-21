package com.intelligence.activity.kettle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.adpter.NzAdpter;
import com.intelligence.activity.controller.ConnHandler;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.utils.Utils;
import com.intelligence.activity.db.DBhelperManager;
import com.intelligence.activity.db.DBhelperManager.ZDYData;
import com.intelligence.activity.db.NZ_DBhelperManager;
import com.intelligence.activity.db.NZ_DBhelperManager.NZYData;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.view.ListViewCompat;
import com.intelligence.activity.view.PromptDialog.DialogListener;

/**
 * 闹钟列表界面
 * 
 * @author Administrator
 * 
 */
public class NzActivity extends BaseActivity implements DialogListener {
	private ListViewCompat listview;

	private NzAdpter adpter;

	private List<NZYData> mList;

	private String MACHINEID;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zdy);

		listview = (ListViewCompat) findViewById(R.id.girdview_id);

		setTitle(this.getString(R.string.alarm));
		setLeftOnClick(null);
		setRigter(true,this.getString(R.string.add));

		getOrdeList();

		findViewById(R.id.title_layout2).setVisibility(View.GONE);
		findViewById(R.id.title_layout3).setVisibility(View.GONE);
	}



	public void getOrdeList() {
		if (!ConnHandler.isConn)
			return;
		MACHINEID = getIntent().getStringExtra("MACHINEID");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("appid", HttpUrl.APP_ID);
		map.put("machineid", MACHINEID);
		map.put("page", 1);
		map.put("pagesize", 20);
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(jrhandler);
		httpUtil.create(0, Utils.getUrl(map, HttpUrl.getorderlist), null);
		httpUtil.setState(110);
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		mList = NZ_DBhelperManager.getInstance(this).getAlermList();
		Log.e("aa", "mList:------------" + mList.size());
		if (mList != null) {
			adpter = new NzAdpter(this, mList);
			adpter.setHandler(handler);
			listview.setAdapter(adpter);
			listview.setOnItemClickListener(adpter);
		}

	}

	NZYData data = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 11) {
				if (!ConnHandler.isConn) {
					showPromptDialog("只有在外网状态下才能启动");
					adpter.notifyDataSetChanged();
					return;
				} else {
					data = (NZYData) msg.obj;
					showPromptDialog(NzActivity.this.getString(R.string.alarm_set_confirm), NzActivity.this, 100, false);
				}
			} else if (msg.what == 12) {
				if (!Utils.isConnect(NzActivity.this)) {
					showPromptDialog("只有在外网状态下才能关闭");
					adpter.notifyDataSetChanged();
					return;
				} else {
					data = (NZYData) msg.obj;
					showPromptDialog(NzActivity.this.getString(R.string.alarm_delete_confirm), NzActivity.this, 101, false);
				}
			}
		};
	};

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

	@Override
	public void setRightOnClick() {
		Intent intent = new Intent(this, NZEditActivity.class);
		startActivity(intent);
	}

	private HttpHandler jrhandler = new HttpHandler(this) {
		protected void succeed(String jObject, int state) {
			super.succeed(jObject, state);

			if (state == 100) {
				showPromptDialog(NzActivity.this.getString(R.string.alarm_set_success));
				data.NZ_ISOPEN = 1;

				NZ_DBhelperManager.getInstance(NzActivity.this).update(data);
				adpter.notifyDataSetChanged();
				String NZ_ID = data.NZ_ID;
				try {
					JSONObject object = new JSONObject(jObject);
					JSONObject data1 = object.optJSONObject("data");
					String order_id = data1.getString("orderid");
					// Toast.makeText(NzActivity.this, order_id, 1).show();
					data.ORDER_ID = order_id;
					NZ_DBhelperManager.getInstance(NzActivity.this)
							.updateOrederId(order_id, NZ_ID);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// Toast.makeText(NzActivity.this, jObject, 1).show();
			} else if (state == 101) {
				// MachineStateData stateData = JsonParser.getInstance()
				// .revertJsonToObj(jObject, MachineStateData.class);
				// if (!stateData.getData().getState().equals("0")) {
				// showPromptDialog("服务器返回数据显示取消加热失败,赶紧去拔了电源吧");
				// }
				String status = "";
				try {
					JSONObject object = new JSONObject(jObject);
					status = object.getString("status");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if ("0".equals(status)) {
					showPromptDialog(NzActivity.this.getString(R.string.heating_cancel_failed));
				} else {
					showPromptDialog(NzActivity.this.getString(R.string.alarm_delete_success));
					data.NZ_ISOPEN = 0;
					NZ_DBhelperManager.getInstance(NzActivity.this)
							.update(data);
					adpter.notifyDataSetChanged();
				}
			}
		};

		protected void failed(String jObject, int state) {
			super.failed(jObject, state);
			if (state == 101) {
				showPromptDialog(NzActivity.this.getString(R.string.heating_cancel_failed));
			} else if (state == 100) {
				showPromptDialog(NzActivity.this.getString(R.string.alarm_set_failed) + jObject.toString());
			}
			mList = NZ_DBhelperManager.getInstance(NzActivity.this)
					.getAlermList();
			adpter.notifyDataSetChanged();
		}
	};

	List<NameValuePair> jr_list = new ArrayList<NameValuePair>();

	@Override
	public void doComfirm(int flag) {
		// 预约
		if (flag == 100) {
			String timeString = data.NZ_TIME.replace(":", "");
			ZDYData zdydata = DBhelperManager.getInstance(this).inquiry(
					data.NZ_ZDYID);
			jr_list.add(new BasicNameValuePair("appid", HttpUrl.APP_ID));
			jr_list.add(new BasicNameValuePair("machineid", MACHINEID));
			jr_list.add(new BasicNameValuePair("heattime", timeString + "00"));
			int times = Integer.parseInt(zdydata.ZDY_TIME) * 60
					+ Integer.parseInt(zdydata.ZDY_TIME1) * 60 + 360;
			jr_list.add(new BasicNameValuePair("costtime", times + ""));
			String sw = zdydata.ZDY_SW.replace("°c", "");
			jr_list.add(new BasicNameValuePair("temp", sw + "C"));
			jr_list.add(new BasicNameValuePair("purify", zdydata.ZDY_TIME));
			jr_list.add(new BasicNameValuePair("keepwarm", zdydata.ZDY_TIME1));
			jr_list.add(new BasicNameValuePair("boil", zdydata.ZDY_ISZF));
			jr_list.add(new BasicNameValuePair("week", data.NZ_SW));
			HttpConnectionUtils httpUtil = new HttpConnectionUtils(jrhandler);
			httpUtil.create(1, HttpUrl.HEAT, jr_list);
			httpUtil.setState(100);
		} // 取消预约
		else if (flag == 101) {
			if (TextUtils.isEmpty(data.ORDER_ID)) {
				showPromptDialog("没有获取到预约的ID，无法取消");
			} else {
				jr_list.add(new BasicNameValuePair("appid", HttpUrl.APP_ID));
				jr_list.add(new BasicNameValuePair("machineid", MACHINEID));
				jr_list.add(new BasicNameValuePair("orderid", data.ORDER_ID));
				// Toast.makeText(NzActivity.this, data.ORDER_ID, 1).show();
				HttpConnectionUtils httpUtil = new HttpConnectionUtils(
						jrhandler);
				httpUtil.create(1, HttpUrl.CANCELHEAT, jr_list);
				httpUtil.setState(101);
			}

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