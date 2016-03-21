package com.intelligence.activity.view;

import java.util.ArrayList;
import java.util.List;

import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.db.DBhelperManager.ZDYData;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class RootSelectView extends RelativeLayout{

	LinearLayout linearLayout; 
	ScrollView scrollView;
	List<RootSelectLabel> lallist;
	int lalwidth = 50;
	private Context context;
	private List<ZDYData> mList;
	public Handler handler;
	public ImageView ivRootSelect;
	
	
	
	public RootSelectView(final Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		inflater.inflate(R.layout.root_select_view, this);
		lallist = new ArrayList<RootSelectLabel>();

		scrollView = (ScrollView) findViewById(R.id.scrollview);

		scrollView.setOnTouchListener(new OnTouchListener() {

			boolean tbegin = false;

			Handler myhandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);

					int mdp = WifiTools.px2dip(context, scrollView.getScrollY());
					int ys = mdp % lalwidth;
					if (ys>lalwidth/2) {
						mdp = mdp-ys + lalwidth;
					}else{
						mdp = mdp - ys;
					}
					scrollView.scrollTo(0, WifiTools.dip2px(context, mdp));


					for (int i = 0; i < mList.size()+2; i++) {
						RootSelectLabel rootSelectLabel = lallist.get(i);
						rootSelectLabel.setColor(Color.GRAY);
						int tag = mdp/lalwidth+1;
						if (i == tag) {
							if (i == 0 || i == mList.size()+1) {
								
							}else{
								ZDYData data = mList.get(i-1);
								Message msga = new Message();
								msga.obj = data;
								msga.what = 100;
								handler.sendMessage(msga);
							}
							rootSelectLabel.setColor(Color.BLUE);	
						}
					}
				}
			};

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(event.getAction()==MotionEvent.ACTION_UP){
					tbegin = true;

					new Thread(new Runnable(){
						int scoreY = 0;

						@Override
						public void run() {
							while (tbegin) {
								System.out.println(scoreY+":"+scrollView.getScrollY());
								if (scoreY == scrollView.getScrollY()) {
									myhandler.sendEmptyMessage(0);
									tbegin = false;
								}else{
									scoreY = scrollView.getScrollY();
								}

								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}).start();	
				}else if(event.getAction() == MotionEvent.ACTION_DOWN){
					tbegin = false;
				}
				return false;
			}
		});



		linearLayout = (LinearLayout) findViewById(R.id.slview);
		ivRootSelect = (ImageView) findViewById(R.id.ivRootSelect);
		

		
	}

	public List<ZDYData> getmList() {
		return mList;
	}

	public void setmList(List<ZDYData> mList) {
		this.mList = mList;
		linearLayout.removeAllViews();
		lallist.clear();
		for (int i = 0; i < mList.size()+2; i++) {
			RootSelectLabel label = new RootSelectLabel(context,handler);
			
			if (i == 0 || i == mList.size()+1) {
				label.setTextSring("");
			}else{
				ZDYData data = mList.get(i-1);
				label.setTextSring(data.ZDY_NAME);
			}
			
			lallist.add(label);
			linearLayout.addView(label);
		}
	}

	
}
