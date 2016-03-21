package com.intelligence.activity.adpter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;

public class WifiSetAdapter extends BaseAdapter implements OnItemClickListener{
	private JSONArray list;
	private LayoutInflater mInflater;
	private Context context;


	public WifiSetAdapter(Context context, JSONArray list){
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return list.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (convertView == null) { 
			convertView = mInflater.inflate(R.layout.item_wifi_set, null); 
		}

		TextView wifiname = (TextView) convertView.findViewById(R.id.wifiname);
		JSONObject map;
		try {
			map = list.getJSONObject(position);
			wifiname.setText(map.getString("wifiname"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return convertView;
	}


	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		
		try {
			final JSONObject map = list.getJSONObject(position);
			
			final EditText et = new EditText(context);
			
			et.setText(map.getString("wifipwd"));	
			
			
			new AlertDialog.Builder(context).setTitle("修改密码")  
			.setIcon(android.R.drawable.ic_dialog_info)  
			.setView(et)  
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					if (et.getText() == null || et.getText().toString().equals("")) {
						
					}else{
						try {
							map.put("wifipwd", et.getText().toString());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}					
					}
					
					WifiTools.savewifilist(list.toString(), context);
				}
			})  
			.setNegativeButton("取消", null)  
			.show();  
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
}
