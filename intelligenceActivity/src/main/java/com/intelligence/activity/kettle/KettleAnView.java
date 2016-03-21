package com.intelligence.activity.kettle;



import com.intelligence.activity.R;
import com.intelligence.activity.utils.WifiTools;
import com.intelligence.activity.controller.UICommand;
import com.intelligence.activity.controller.UIController;
import com.intelligence.activity.img.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class KettleAnView extends View{

	private int width = 130;
	private int height = 200;
	private int movel = 50;
	private int number = 20;
	private int speed = 15;


	private Paint _paint;

	private int[] X = new int[number];

	private int[] MOVEY = new int[number];

	private int[] MOVEY1 = new int[number];

	private int[] MOVEX1 = new int[number];

	private int indexnum;
	private int swheight;
	private int _state;

	private boolean stop = true;

	public void setSwheight(int swheight) {
		this.swheight = (int)(WifiTools.dip2px(context, swheight));
		postInvalidate();
	}
	Context context;

	Bitmap pp;

	public KettleAnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
		swheight = WifiTools.dip2px(context, height);
		init();
		setViewState(1);
	}

	private void init(){

		pp = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.shuipao)).getBitmap();

		try {
			pp = ImageUtil.zoomBitmap(pp, WifiTools.dip2px(context, 7), WifiTools.dip2px(context, 7));
		} catch (Exception e) {
			// TODO: handle exception
		}


		this._paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this._paint.setTextSize(WifiTools.dip2px(context, 18));



		X = getRandom(number, WifiTools.dip2px(context, width));
		MOVEY = getRandom(number, 15);

		MOVEY1 = getRandom(number, 5);

		MOVEX1 = getRandom(number, 30);

	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			for(int i = 0 ; i < number ; i++){
				MOVEY1[i] += (MOVEY[i]+speed);
				if(MOVEY1[i] >= swheight){
					MOVEY1[i] = 0;
					indexnum ++;
				}
			}
			postInvalidate();
		};
	};
	private UICommand cmd = new UICommand(handler,100);
	/**
	 * 设置app的状态
	 */
	public void setViewState(int state){
		if(this._state == state){
			return;
		}

		if(state == 1){
			setStop(false);
			postInvalidate();
			UIController.getInstance().addLoopCommand(cmd);
		}else{
			UIController.getInstance().removeLoopCmd(100);
			setStop(true);
		}

		this._state = state;
	}
	private int[] getRandom(int n,int max){
		int[] randoms=new int[n];//定义一个n位长的int数组 用来存储0打n之间的随机数
		String randomString="";//定义一个String 字符串 用来存储产生的随机数转换成String后的值
		for(int i=0;i<n;i++){
			int random=(int)(Math.random()*max);//产生0到n之间的随机数
			if(random == 0){
				random = 1;
			}
			randomString+=String.valueOf(random); 
			randoms[i]=random;//最后把产生的不重复的随机数 赋值给randoms数组
		}
		return randoms;//返回数组
	}



	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
		invalidate();
	}

	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);

		if (stop) {
			clear();
			return;
		}

		canvas.save();

		for(int i = 0 ;i < number ; i++){
			canvas.drawBitmap(pp, X[i] - MOVEX1[i]+ WifiTools.dip2px(context, movel),swheight-MOVEY1[i], this._paint);
		}

		canvas.restore();
	}

	public void clear(){
		Canvas canvas = new Canvas();
		Paint paint = new Paint();
		paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		canvas.drawPaint(paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
		invalidate();
	}

}
