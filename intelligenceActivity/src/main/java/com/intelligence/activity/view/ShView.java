package com.intelligence.activity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.utils.ImageUtil;
import com.intelligence.activity.controller.UICommand;
import com.intelligence.activity.controller.UIController;
import com.intelligence.activity.utils.Utils;
/**
 * 水壶详情的中心控件
 * @author Administrator
 *
 */
public class ShView extends View{

	/******* 当前控件的状态 ******/
	private int _state = 0;
	
	private Context _context;
	/** 画笔 */
	protected Paint _paint = null;
	
	/** 画笔 */
	protected Paint _paint1 = null;
	
	/** 底图*/
	protected Bitmap _cover = null;
	
	protected Bitmap _ss = null;
	
	protected Bitmap _ss1 = null;
	
	protected Bitmap pp = null;
	/** 封面地址 */
	protected String _coverUrl = null;
	/** app Id */
	protected int _id = 111;
	/** 封面宽度 */
	protected int _coverWidth = 0;
	/** 封面高度 */
	protected int _coverHeight = 0;
	/** 当前状态:-1:未知；0：启动下载；1：暂停下载；2：下载完成 */
	protected int _currentState = -1;
	
	private String shuiweiString;
	
	private int number = 20;
	private int[] X = new int[number];
	
	private int[] MOVEY = new int[number];
	
	private int[] MOVEY1 = new int[number];
	
	private int[] MOVEX1 = new int[number];
	
	
	public ShView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this._context = context;
		// TODO 自动生成的构造函数存根
		init();
	}

	private void init(){
		_cover = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.shuihu)).getBitmap();
		_coverWidth = _cover.getWidth();
		_coverHeight = _cover.getHeight();
		
		
		_ss = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.shuiwei_bg)).getBitmap();
		
		
		_ss1 = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.shuiwei_bg2)).getBitmap();
		
		
		pp = ((BitmapDrawable) this.getResources().getDrawable(R.drawable.shuipao)).getBitmap();
		
		pp = ImageUtil.zoomBitmap(pp, 12, 12);
		this._paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this._paint.setTextSize((18*BaseActivity.density));
		
		
		this._paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
		this._paint1.setTextSize(30*BaseActivity.density);
		
		
		X = getRandom(number,120);
		MOVEY = getRandom(number, 15);
		
		MOVEY1 = getRandom(number, 5);
		
		
		MOVEX1 = getRandom(number, 30);
		issw = false;
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				issw = true;
				if(stateHandler != null){
					stateHandler.sendEmptyMessage(10000);
				}
				postInvalidate();
			}}, 10000);
		
	}
	private int indexnum;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			issw = false;
			for(int i = 0 ; i < number ; i++){
				MOVEY1[i] += (MOVEY[i]+5);
				if(MOVEY1[i] >= (swheight -55)){
					MOVEY1[i] = 0;
					indexnum ++;
				}
			}
			postInvalidate();
		};
	};
	
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


	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		this._coverHeight = this._cover.getHeight();
		this._coverWidth = this._cover.getWidth() + 160;
		setMeasuredDimension(this._coverWidth, this._coverHeight);
	}
	private final int WIDTH = 60;
	private int swheight = 200;
	public void setSwheight(int swheight) {
		this.swheight = (int)(swheight*BaseActivity.density);
		postInvalidate();
	}

	private String lever = "1.0L";
	
	private String wd = "0°C";
	public void setLever(String lever) {
		this.lever = lever;
		postInvalidate();
	}

	public void setWd(String wd) {
		this.wd = wd.subSequence(0, 1)+"°C";
		
		postInvalidate();
	}

	private int drawState = 0;
	public void setDrawState(int drawState) {
		this.drawState = drawState;
		if(drawState == 1){
			_cover = Utils.setAlpha(_cover, 50);
		}else{
			_cover = Utils.setAlpha(_cover, 100);
		}
	}
	
	
	public void setGone(){
		if(!issw)
			return;
		issw = false;
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run() {
				issw = true;
				if(stateHandler != null){
					stateHandler.sendEmptyMessage(10000);
				}
				postInvalidate();
			}}, 10000);
	}
	
	private boolean issw;

	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(this._cover == null)
			return;
		int cx = (_coverWidth - _cover.getWidth()) /2;
		canvas.drawBitmap(this._cover, cx, 0, this._paint);
		if(drawState == 0){
			canvas.save();
			int h1 = _ss.getHeight() - (int)(swheight);
			int y = _cover.getHeight() - _ss.getHeight() - (WIDTH) + h1;
			int x = cx + (_cover.getWidth() - _ss.getWidth() )/2 - 30;
			_paint.setColor(_context.getResources().getColor(R.color.tianlan1));// 设置绿色  
			_paint.setStyle(Paint.Style.FILL);//设置填满  

			canvas.drawRect(x, y, _ss.getWidth()+x - 20, y +(swheight - 20), _paint);// 正方形  
			_paint.setColor(Color.BLACK);
			int tx = cx + (_cover.getHeight() - WIDTH*2 - 20)/2;
			canvas.drawText(lever + "一", 0, y+20, _paint);
			if(!issw){
				canvas.drawText(wd, tx - 20, y+20, _paint1);
			}
			if(_state == 1){
				canvas.save();
				for(int i = 0 ;i < number ; i++){
					canvas.drawBitmap(pp, tx+X[i] - MOVEX1[i], y +  swheight - 55 - MOVEY1[i], this._paint);
				}
			}
			
		}
		canvas.restore();
	}
	
	
	private UICommand cmd = new UICommand(handler,100);
	/**
	 * 设置app的状态
	 */
	public void setViewState(int state){
		
		if(state == 1){
			issw = false;
			postInvalidate();
			if(stateHandler != null){
				stateHandler.sendEmptyMessage(10001);
			}
			UIController.getInstance().addLoopCommand(cmd);
		}else{
			if(this._state == 0){
				return;
			}
			UIController.getInstance().removeLoopCmd(100);
			setGone();
		}
		
		this._state = state;
	}
	
	private Handler stateHandler;
	
	public void setStateHandler(Handler stateHandler) {
		this.stateHandler = stateHandler;
	}

	public int getViewState(){
		return this._state;
	}
	
	
	
}
