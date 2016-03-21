package com.intelligence.activity.view;





import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Path.Direction;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class SearchView extends View{

	Path path;
	private PathMeasure pathMeasure = null;
	int index = 0;
	Bitmap bitmap;
	
	private boolean beginan = false;
	Context context;
	int viewwidthdp = 200/2;
	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		
		path = new Path();
		path.addCircle(WifiTools.dip2px(context, viewwidthdp), WifiTools.dip2px(context, viewwidthdp), WifiTools.dip2px(context, 30), Direction.CW);

		bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.search);
		int i4pxx = WifiTools.dip2px(context, 50);
		float i4sx = i4pxx*1.0f/bitmap.getWidth();
		bitmap = WifiTools.small(bitmap, i4sx);
		
		pathMeasure = new PathMeasure(); 
		pathMeasure.setPath(path, false);

	}

	protected void onDraw(Canvas canvas){

		super.onDraw(canvas);

		Paint paint = new Paint();
		
		float[] pos = new float[2];
		pathMeasure.getPosTan(index, pos, null);
		
		paint.setColor(Color.RED);
		if (beginan == false) {
			canvas.drawBitmap(bitmap, WifiTools.dip2px(context, viewwidthdp)-bitmap.getWidth()/2, WifiTools.dip2px(context, viewwidthdp)-bitmap.getHeight()/2, paint);
		}else{
			canvas.drawBitmap(bitmap, pos[0]-bitmap.getWidth()/2, pos[1]-bitmap.getHeight()/2, paint);
		}
	}
	
	public void stopSearch(){
		beginan = false;
		invalidate();
	}
	
	public boolean isSearch(){
		return beginan;
	}
	
	public void beginSearch(){
		beginan = true;
		new Thread(new Runnable(){
            @Override
            public void run() {
            	while (beginan) {
            		handler.sendEmptyMessage(0);
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
            }
        }).start();
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if (index<pathMeasure.getLength()) {
				index++;
			}else{
				index = 0;
			}
			invalidate();
		}
	};
}
