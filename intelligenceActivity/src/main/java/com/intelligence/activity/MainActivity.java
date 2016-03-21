package com.intelligence.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.intelligence.activity.addpart.MachineActivity;
import com.intelligence.activity.adpter.MainItemAdpter;
import com.intelligence.activity.controller.ConnHandler;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.mosquito.MosquitoActivity;
import com.intelligence.activity.switchs.SwitchsActivity;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.controller.UICommand;
import com.intelligence.activity.controller.UIController;
import com.intelligence.activity.utils.Utils;
import com.intelligence.activity.data.RequestAppListData;
import com.intelligence.activity.db.Machinenu_DBhelperManager;
import com.intelligence.activity.db.Machinenu_DBhelperManager.MACHINDATA;
import com.intelligence.activity.db.UserInfoSharedPreferences;
import com.intelligence.activity.feedback.YJActivity;
import com.intelligence.activity.http.HttpConnectionUtils;
import com.intelligence.activity.http.HttpHandler;
import com.intelligence.activity.http.HttpUrl;
import com.intelligence.activity.json.JsonParser;
import com.intelligence.activity.setting.SetActivity;
import com.intelligence.activity.view.PromptDialog.DialogListener;
import com.intelligence.activity.view.SlideMenu;
import com.intelligence.service.MyService;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.proguard.aD.e;

/**
 * 主界面
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends BaseActivity {

	private SlideMenu slideMenu;

	private PullToRefreshGridView gridView;

	private MainItemAdpter adpter;

	private List<MACHINDATA> list;

	private View addview;

	ConnHandler connHandler = null;

	public static boolean iswifi;
	ButtonRectangle tempbtn,tempbtn1,tempbtn2,tempbtn3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ConnHandler.isConn = true;
		if (HttpUrl.APP_ID == null) {
			HttpUrl.APP_ID = UserInfoSharedPreferences.getInstance()
					.getUserInfoStringKey(this,
							UserInfoSharedPreferences.USER_IMEI);
		}
		WifiTools.saveuuid(HttpUrl.APP_ID, this);
		Intent intent1 = new Intent(this, MyService.class);
		startService(intent1);
		// System.out.println(HttpUrl.APP_ID);
		//just for test jump
		tempbtn= (ButtonRectangle) findViewById(R.id.tempjump);
		tempbtn1= (ButtonRectangle) findViewById(R.id.tempjump1);
		tempbtn2= (ButtonRectangle) findViewById(R.id.tempjump2);
		tempbtn3= (ButtonRectangle) findViewById(R.id.tempjump3);
		tempbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SwitchsActivity.class);
				startActivity(intent);
			}
		});
		tempbtn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});tempbtn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,MosquitoActivity.class);
				startActivity(intent);
			}
		});

		slideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		WifiTools.db = WifiTools.getDb(this);
		final OnClickListener menuOnClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (v.getId() == R.id.layout_01) {
					slideMenu.closeMenu();
				} else if (v.getId() == R.id.layout_02) {
					slideMenu.closeMenu();
					Intent intent = new Intent(MainActivity.this,
							SetActivity.class);
					startActivity(intent);
				} else if (v.getId() == R.id.layout_04) {
					slideMenu.closeMenu();
					Intent intent = new Intent(MainActivity.this,
							YJActivity.class);
					startActivity(intent);
				}
				else if (v.getId() == R.id.layout_06) {
					slideMenu.closeMenu();
					showPromptDialog(MainActivity.this.getString(R.string.update_nonecessary), new DialogListener() {
						@Override
						public void doComfirm(int flag) {
							// TODO 自动生成的方法存根

						}

						@Override
						public void doCancel(int flag) {
							// TODO 自动生成的方法存根

						}

						@Override
						public void doBack(int flag) {
							// TODO 自动生成的方法存根

						}

					}, -1, false);
				}
			}
		};

		TextView layout = (TextView) slideMenu.findViewById(R.id.layout_01);
		layout.setOnClickListener(menuOnClickListener);

		TextView layout2 = (TextView) slideMenu.findViewById(R.id.layout_02);
		layout2.setOnClickListener(menuOnClickListener);

		TextView layout4 = (TextView) slideMenu.findViewById(R.id.layout_04);
		layout4.setOnClickListener(menuOnClickListener);

		TextView layout6 = (TextView) slideMenu.findViewById(R.id.layout_06);
		layout6.setOnClickListener(menuOnClickListener);

		gridView = (PullToRefreshGridView) findViewById(R.id.girdview_id);

		setTitle(this.getString(R.string.my_device));
		setLeftOnClick(R.drawable.home_left_bt, new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (slideMenu.isMainScreenShowing()) {
					slideMenu.openMenu();
				} else {
					slideMenu.closeMenu();
				}
			}
		});
		setRigter(true, this.getString(R.string.edit));

		addview = LayoutInflater.from(this).inflate(R.layout.addview, null);

		// shareView = (TextView)slideMenu.findViewById(R.id.user_share_id);
		// shareView.setOnClickListener(onClickListener);

		connHandler = new ConnHandler(this);
		UICommand cmd = new UICommand(connHandler);

		UIController.getInstance().addLoopCommand(cmd);

		Utils.setAlarmTime(this);

		gridView.setOnRefreshListener(new OnRefreshListener2<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				loadData();

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				loadData();
			}
		});
		initPus();

		handler1.setProcessing(true);
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("appid", HttpUrl.APP_ID));
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler1);
		httpUtil.setState(101);
		httpUtil.create(1, HttpUrl.REQ, data);
	}

	private PushAgent mPushAgent;

	/**
	 * 初始化通知栏的消息
	 */
	private void initPus() {
		mPushAgent = PushAgent.getInstance(this);
		Log.e("aa",
				"UmengRegistrar.isRegistered(MainActivity.this):------------"
						+ UmengRegistrar.isRegistered(MainActivity.this));
		if (!mPushAgent.isEnabled()) {

		}
		try {
			mPushAgent.enable();
			// mPushAgent.removeAlias(HttpUrl.APP_ID, ALIAS_TYPE.SINA_WEIBO);
			new AddAliasTask().execute();
			// boolean b = mPushAgent.addAlias(HttpUrl.APP_ID,
			// ALIAS_TYPE.BAIDU);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String uuid = HttpUrl.APP_ID;
				uuid = uuid.replace("-", "");
				uuid = uuid.toLowerCase();
				System.out.println(uuid);
				try {
					mPushAgent.addAlias(uuid, "appid");
				} catch (e e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		// String device_token = UmengRegistrar.getRegistrationId(this);
		Log.e("aa", "id---------------" + HttpUrl.APP_ID + "");
	}

	class AddAliasTask extends AsyncTask<Void, Void, Boolean> {

		String alias;

		public AddAliasTask() {
			// TODO Auto-generated constructor stub
			this.alias = HttpUrl.APP_ID;
		}

		protected Boolean doInBackground(Void... params) {
			try {
				return mPushAgent.addAlias(alias, "appid");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (Boolean.TRUE.equals(result))
				Log.e("aa", "alias was set successfully.");
			else {
				Log.e("aa", "1111111111alias was set successfully.");
			}
		}

	}

	protected void onDestroy() {
		super.onDestroy();
		// locationManager.removeUpdates(locationListener);
		UIController.getInstance().destroyAllThread();
		java.lang.System.exit(0);
	};

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			// if(v.getId() == R.id.user_share_id){
			// Intent intent = new
			// Intent(MainActivity.this,ShareActivity.class);
			// startActivity(intent);
			// }
			slideMenu.closeMenu();
		}
	};

	HashMap<String, Object> map = new HashMap<String, Object>();
	public static boolean updata = false;
	private boolean isOne = false;

	private void loadData() {
		list = Machinenu_DBhelperManager.getInstance(this).getAlermList(0);
		map.put("appid", HttpUrl.APP_ID);
		map.put("page", "1");
		map.put("pagesize", "20");
		handler.setProcessing(false);
		HttpConnectionUtils httpUtil = new HttpConnectionUtils(handler);
		httpUtil.create(0, Utils.getUrl(map, HttpUrl.GET_MCHINELIST), null);
	}

	private void initData() {
		if (ConnHandler.isConn && !isOne) {
			isOne = true;
			loadData();
		}
		// 加载进度条
		if (iswifi) {
			try {

				if (ConnHandler.isConn) {
					loadData();
					return;
				}
				iswifi = false;
				list = Machinenu_DBhelperManager.getInstance(this)
						.getAlermList(0);
				data = new RequestAppListData();
				List<Machine> listsDatas = new ArrayList<Machine>();
				data.setData(listsDatas);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).IS_DELE == 0) {
						Machine itemData = new Machine();
						itemData.setMachineid(list.get(i).M_ID);
						itemData.setName(list.get(i).MNAME);
						itemData.setIpString(list.get(i).M_IP);
						if (list.get(i).MAC.equals(MachineActivity
								.getSsid(MainActivity.this))) {
							itemData.setIsonline("online");
						} else {
							itemData.setIsonline("offline");
						}
						listsDatas.add(itemData);
					}

				}
				adpter = new MainItemAdpter(MainActivity.this, data.getData());

				gridView.setAdapter(adpter);
				gridView.setOnItemClickListener(adpter);
				adpter.setState(string);

				// setAdpter();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		if (updata) {
			updata = false;
			isEd = true;
			setAdpter();
			loadData();
			// isEd=false;
		}

		initData();

	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();

	}

	private HttpHandler handler1 = new HttpHandler(this) {

	};
	public RequestAppListData data;
	// private Machine itemData = new Machine();
	private HttpHandler handler = new HttpHandler(this) {
		protected void succeed(String jObject, int state) {
			super.succeed(jObject, state);

			if (state == 100) {
				Log.e("aa", "jObject---------------" + jObject);
			} else if (state == 101) {
				// loadData();
			} else {
				ConnHandler.isConn = true;
				data = JsonParser.getInstance().revertJsonToObj(jObject,
						RequestAppListData.class);
				try {
					for (int i = 0; i < list.size(); i++) {
						for (int j = 0; j < data.getData().size(); j++) {

							// =======测试
							// data.getData().get(j).setIsonline("online");
							// data.getData().get(j).setHub("1");
							// data.getData().get(j).setTemp("37");
							// data.getData().get(j).setLevel("0.8");

							if (data.getData().get(j).getMachineid()
									.equals(list.get(i).M_ID)) {
								data.getData().get(j)
										.setName(list.get(i).MNAME);
								data.getData().get(j)
										.setIpString(list.get(i).M_IP);
							}
						}
					}
					// =====测试
					// data.getData().clear();
					// ========
					adpter = new MainItemAdpter(MainActivity.this,
							data.getData());
					gridView.setAdapter(adpter);
					gridView.setOnItemClickListener(adpter);
					adpter.setState(string);
					adpter.notifyDataSetChanged();
					setAdpter();
					gridView.onRefreshComplete();
				} catch (Exception e) {

				}
			}

		};

		protected void failed(String jObject, int state) {
			super.failed(jObject, state);
		}

		protected void connEerr() {
			super.connEerr();
			if (ConnHandler.isConn) {
				ConnHandler.isConn = false;
				showPromptDialog("设备进入内网模式");

				try {
					gridView.onRefreshComplete();
					data = new RequestAppListData();
					List<Machine> listsDatas = new ArrayList<Machine>();
					data.setData(listsDatas);
					adpter = new MainItemAdpter(MainActivity.this,data.getData());
					gridView.setAdapter(adpter);
					gridView.setOnItemClickListener(adpter);
					adpter.setState(string);

					setAdpter();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	};
	String string = "0";
	private boolean isEd = true;

	// private MachineListItemData itemData = new MachineListItemData();
	public void setAdpter() {

		if (isEd) {
			// data.getData().add(itemData);
			adpter.setEnd(false);
			adpter.setState("0");
			adpter.setEnd(false);
			gridView.setAdapter(adpter);
			// adpter.notifyDataSetChanged();
		} else {
			// data.getData().remove(itemData);
			adpter.setEnd(true);
			adpter.setState("1");
			adpter.setEnd(true);
			gridView.setAdapter(adpter);
			// adpter.notifyDataSetChanged();
		}
	}

	@Override
	protected void setRightOnClick() {
		super.setRightOnClick();
		if (string.equals("1")) {
			string = "0";
			setRigter(true, this.getString(R.string.edit));
			isEd = true;
		} else {
			string = "1";
			setRigter(true, this.getString(R.string.finished));
			isEd = false;
		}

		setAdpter();

	}


}
