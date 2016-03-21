package com.intelligence.activity.humidifier;


import java.util.ArrayList;
import java.util.List;

import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class QipaoView extends RelativeLayout {
	Context context;
	List<QipaoImage> qplist = new ArrayList<QipaoImage>();
	private boolean beginAn;
	public QipaoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public QipaoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		inflater.inflate(R.layout.qipao_view, this);

	}
	public void stopAn(){
		beginAn = false;
	}

	public void startAn(){
		beginAn = true;
		new Thread(new Runnable(){
			@Override
			public void run() {
				while (beginAn) {

					myHandler.sendEmptyMessage(0);
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	//结果处理
	public Handler myHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 0:
				addWuqi();
			}
		}
	};

	public void addWuqi(){

		if (qplist.size()==6) {
			QipaoImage qipaoImage = qplist.get(0);
			if(qipaoImage.anstop){
				this.removeView(qipaoImage);
				qplist.remove(0);
			}
		}

		if(qplist.size()<6) {
			QipaoImage qipaoImage = new QipaoImage(this.context);
			this.addView(qipaoImage);
			setLa(qipaoImage, WifiTools.dip2px(context, 100 - 25), WifiTools.dip2px(context, 20));
			qplist.add(qipaoImage);
			qipaoImage.startan();

		}

	}

	public void setLa(View view,int x,int y){
		MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
		margin.setMargins(x, y, x+margin.width, y+margin.height);
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(margin);
		view.setLayoutParams(param);
	}



}
