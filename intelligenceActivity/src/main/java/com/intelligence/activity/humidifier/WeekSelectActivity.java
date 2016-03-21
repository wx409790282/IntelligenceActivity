package com.intelligence.activity.humidifier;

import java.util.ArrayList;
import java.util.List;

import com.intelligence.activity.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class WeekSelectActivity extends Activity {

	private ListView listView;
	private List<String> msglist = new ArrayList<String>();
	private HTiming timing;
	MyListAdapter myListAdapter;
	private List<String> weeklist = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_week_select);
		
		
		
		timing =  (HTiming)getIntent().getSerializableExtra("timing");
		
		NavigationBar bar = (NavigationBar) findViewById(R.id.navigationbar);
		bar.setTitle("星期");
		bar.showLeftBtn(true);
		bar.setLeftOnClickListener(new MyOnClickListener() {  
			@Override
			public void onClick(View btn) {
				// TODO Auto-generated method stub
				
				sort(weeklist);
				Intent it = new Intent();
		        Bundle b2 = new Bundle();
		        b2.putSerializable("timing", timing);
		        it.putExtras(b2);
		        setResult(RESULT_OK,it);
				
				finish();    
			}
        });
		
		
		String[] sweek = timing.repeat.split(",");
		for (String str : sweek) {
			weeklist.add(str);
		}
		
		
		
		msglist.add("星一");
		msglist.add("星二");
		msglist.add("星三");
		msglist.add("星四");
		msglist.add("星五");
		msglist.add("星六");
		msglist.add("星日");
		
		listView = (ListView) findViewById(R.id.listview);
		myListAdapter = new MyListAdapter(this, msglist);
		listView.setAdapter(myListAdapter);
		listView.setOnItemClickListener(new MyListCilckListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.week_select, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	class MyListAdapter  extends BaseAdapter{
		private List<String> list;
		private LayoutInflater mInflater;
		private Context context;


		public MyListAdapter(Context context, List<String> list){
			this.list = list;
			this.context = context;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			if (convertView == null) { 
				convertView = mInflater.inflate(R.layout.item_week, null); 
			}

			String msg = list.get(position);

			TextView itemtitle = (TextView) convertView.findViewById(R.id.itemtitle);
			itemtitle.setText(msg);

			ImageView dagou = (ImageView) convertView.findViewById(R.id.dagou);
			dagou.setVisibility(View.INVISIBLE);
			
			for (String str : weeklist) {
				if (Integer.parseInt(str)-1 == position) {
					dagou.setVisibility(View.VISIBLE);
					break;
				}
			}
			
			
			return convertView;
		}
	}

	
	public class MyListCilckListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			boolean issame = false;
			for (String str : weeklist) {
				if (Integer.parseInt(str)-1 == position) {
					issame = true;
					weeklist.remove(str);
					break;
				}
			}
			if(!issame){
				weeklist.add(position+1+"");
			}
			
			
			myListAdapter.notifyDataSetChanged();
		}
	}
	
	 public void sort(List<String> list){//小到大的排序

		int[] targetArr = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			targetArr[i] = Integer.parseInt(list.get(i).toString());
		}
		 
         int temp = 0;
         for(int i = 0;i<targetArr.length;i++){
             for(int j = i;j<targetArr.length;j++){

//            	 int ti = Integer.parseInt(targetArr.get(i).toString());
//            	 int tj = Integer.parseInt(targetArr.get(j).toString());
            	 
                 if(targetArr[i]>targetArr[j]){

                    //方法一：
                     temp = targetArr[i];
                     targetArr[i] = targetArr[j];
                     targetArr[j] = temp;

                     /*//方法二:
                     targetArr[i] = targetArr[i] + targetArr[j];
                     targetArr[j] = targetArr[i] - targetArr[j];
                     targetArr[i] = targetArr[i] - targetArr[j];*/
                    }
              }
         }
         String str = "";
         for (int i = 0; i < targetArr.length; i++) {
			if (str.equals("")) {
				str = targetArr[i]+"";
			}else{
				str =str +","+ targetArr[i]+"";
			}
		}
         timing.repeat = str;
     }
}
