package com.intelligence.activity.adpter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.intelligence.activity.R;
/**
 * 列表格式弹出框的适配器
 * @author Administrator
 *
 */
public class ListDialogAdpter extends BaseAdapter implements OnItemClickListener{

	private List<HashMap<String, Object>> mDataList;

	private Context context;

	private LayoutInflater inf;
	
	private Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ListDialogAdpter(Context context, List<HashMap<String, Object>> dataList) {
		mDataList = dataList;
		this.context = context;
		inf = LayoutInflater.from(context);

	}

	public void cleanArray() {
		for (int i = 0; i < mDataList.size(); i++) {
			mDataList.clear();
		}
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
		final ListHolder holder;
		if (contentView == null) {
			holder = new ListHolder();
			contentView = inf.inflate(R.layout.listdialog_item, null);
			holder.tx01 = (TextView)contentView.findViewById(R.id.list_txt_id);
			contentView.setTag(holder);
		} else {
			holder = (ListHolder)contentView.getTag();
		}
		
		try {
			final HashMap<String, Object> bean = mDataList.get(index);
			String txt = bean.get("txt").toString();
			holder.tx01.setText(txt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentView;
	}

	public static class ListHolder {
		TextView tx01;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 自动生成的方法存根
		if(handler != null){
			
		}
	}

}
