package com.intelligence.activity.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.string;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelligence.activity.R;

public class XQAdpter extends BaseAdapter implements OnItemClickListener {

	private List<HashMap<String, Object>> mDataList = new ArrayList<HashMap<String, Object>>();

	private Context context;

	private LayoutInflater inf;

	String NZ_SW;

	private Handler handler;

	public XQAdpter(Context context, Handler handler, String NZ_SW) {
		this.context = context;
		this.NZ_SW = NZ_SW;
		inf = LayoutInflater.from(context);
		this.handler = handler;

		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("txt", "星期一");
		map1.put("boolean", !"0".equals(NZ_SW.charAt(0) + ""));
		mDataList.add(map1);

		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("txt", "星期二");
		map2.put("boolean", !"0".equals(NZ_SW.charAt(1) + ""));
		mDataList.add(map2);

		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("txt", "星期三");
		map3.put("boolean", !"0".equals(NZ_SW.charAt(2) + ""));
		mDataList.add(map3);

		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("txt", "星期四");
		map4.put("boolean", !"0".equals(NZ_SW.charAt(3) + ""));
		mDataList.add(map4);

		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("txt", "星期五");
		map5.put("boolean", !"0".equals(NZ_SW.charAt(4) + ""));
		mDataList.add(map5);

		HashMap<String, Object> map6 = new HashMap<String, Object>();
		map6.put("txt", "星期六");
		map6.put("boolean", !"0".equals(NZ_SW.charAt(5) + ""));
		mDataList.add(map6);

		HashMap<String, Object> map7 = new HashMap<String, Object>();
		map7.put("txt", "星期天");
		map7.put("boolean", !"0".equals(NZ_SW.charAt(6) + ""));
		mDataList.add(map7);

	}

	@Override
	public int getCount() {
		if (mDataList != null) {
			return mDataList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if (mDataList != null) {
			return mDataList.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int index, View contentView, ViewGroup parent) {

		ZDYHolder holder;
		if (contentView == null) {
			holder = new ZDYHolder();
			contentView = LayoutInflater.from(context).inflate(
					R.layout.xq_item, parent, false);
			holder.img = (ImageView) contentView
					.findViewById(R.id.toggleButton1);
			holder.tx01 = (TextView) contentView.findViewById(R.id.xq_txt);

			contentView.setTag(holder);
		} else {
			holder = (ZDYHolder) contentView.getTag();
			;
		}

		if ((Boolean) (mDataList.get(index).get("boolean"))) {
			holder.img.setBackgroundResource(R.drawable.auth_follow_cb_chd);
		} else {
			holder.img.setBackgroundResource(R.drawable.auth_follow_cb_unc);
		}

		try {

			holder.tx01.setText(mDataList.get(index).get("txt").toString());
		} catch (Exception e) {

		}
		return contentView;
	}

	public static class ZDYHolder {
		ImageView img;
		TextView tx01;
		// boolean isbt = false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ZDYHolder holder = (ZDYHolder) view.getTag();
		Boolean isbt = (Boolean) mDataList.get(position).get("boolean");
		isbt = !isbt;
		if (isbt) {
			holder.img.setBackgroundResource(R.drawable.auth_follow_cb_chd);
		} else {
			holder.img.setBackgroundResource(R.drawable.auth_follow_cb_unc);
		}
		mDataList.get(position).put("boolean", isbt);

		if (handler != null) {
			Message msg = new Message();
			msg.obj = getString();
			msg.what = 10;
			handler.sendMessage(msg);
		}
	}

	private String[] numberStrings = { "一", "二", "三", "四", "五", "六", "天", };

	private String getString() {
		StringBuffer stringBuffer = new StringBuffer();
		// stringBuffer.append("周");
		int isqb = 0;
		for (int i = 0; i < 7; i++) {
			HashMap<String, Object> map = mDataList.get(i);
			if (Boolean.parseBoolean(map.get("boolean").toString())) {
				isqb++;
				// stringBuffer.append(numberStrings[i]);
				// stringBuffer.append(",");
				stringBuffer.append("1");
			} else {
				stringBuffer.append("0");
			}
		}
		// if(isqb >= 6){
		// stringBuffer = new StringBuffer();
		// stringBuffer.append("全部");
		// }
		return stringBuffer.toString();
	}
}
