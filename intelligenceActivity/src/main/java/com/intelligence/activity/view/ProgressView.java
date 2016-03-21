package com.intelligence.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class ProgressView extends View{
	
	private final int swipe_min_distance = 6;
    private final int swipe_max_off_path = 0;
    private final int swipe_threshold_veloicty = 5;
    
	private final  Paint paint;  
    private final Context context;  
    private int progress;
    private int ringWidth;
    private int startAngle = 80;
    private int endAngle = 250;
    private GestureDetector mGestureDetector;
	public ProgressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;  
		this.paint = new Paint();  
        this.paint.setAntiAlias(true); 
        this.ringWidth = dip2px(context, 12); 
        mGestureDetector = new GestureDetector(context, new MyOnGestureListener());
        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });
	}

	public ProgressView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public ProgressView(Context context) {
		this(context,null);
	}
	/**
	 * ���ü��ؽ�ȣ�ȡֵ��Χ��0~100֮��
	 * @param progress
	 */
	public void setProgress(int progress) {
		this.progress = progress;
		invalidate();
	}
	/**
	 * ����Բ���뾶
	 * @param ringWidth
	 */
	public void setRingWidthDip(int ringWidth) {
		this.ringWidth = dip2px(context, ringWidth);
	}
	private static final String BG_COLOR = "#949494";
	
	private static final String WRED_COLOR = "#00000000";
	
	private int angleOfMoveCircle = 120;// 移动小园的起始角度。
	/**
	 * ͨ��ϻ����ķ�ʽ���½��棬ʵ�ֽ�����
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		int center = getWidth()/2;  
        int radios = center-ringWidth/2 - 10;
        // 计算弧形的圆心和半径。
        int cx1 = (center+radios) / 2;
        int cy1 = (center+radios) / 2;
        int arcRadius = (center+radios) / 2;
        //����Բ��  
        this.paint.setStyle(Paint.Style.STROKE); //���ƿ���Բ   
        this.paint.setColor(Color.parseColor(WRED_COLOR));//Բ������
         
        canvas.drawCircle(center,center, radios, this.paint);  
        RectF oval = new RectF(center-radios, center-radios, center+radios, center+radios);
        this.paint.setStrokeWidth(ringWidth);
        this.paint.setColor(Color.parseColor(WRED_COLOR));//�����ɫ
        canvas.drawArc(oval, startAngle, endAngle, false, paint);
        
        
        this.paint.setColor(Color.parseColor("#ffffff"));//�����ɫ
        this.paint.setStrokeWidth(ringWidth);
//        if(progress>=100){
//        	  canvas.drawArc(oval, startAngle, endAngle, false, paint);
//        	  this.paint.setColor(Color.parseColor("#ffffffff"));
//        	  canvas.drawArc(oval, startAngle, endAngle*(progress-100)/100, false, paint);
//        }else{
//        	 canvas.drawArc(oval, startAngle, endAngle*progress/100, false, paint);
//        }
        
        
        canvas.drawArc(oval, startAngle + (startAngle+endAngle)*progress/100, startAngle+endAngle - ((startAngle+endAngle)*progress/100), false, paint);
//        this.paint.setColor(Color.parseColor("#ffff0000"));//�����ɫ
//        canvas.drawCircle(
//                (float) (cx1 + arcRadius
//                        * Math.cos(angleOfMoveCircle * 3.14 / (endAngle*progress/100))),
//                (float) (cy1 + arcRadius
//                        * Math.sin(angleOfMoveCircle * 3.14 / (endAngle*progress/100))),
//                6 , paint);// 小圆
	}
	
	public interface PressListener{
		public void pressListener(String press);
	}
	public PressListener listener;
    public PressListener getListener() {
		return listener;
	}

	public void setListener(PressListener listener) {
		this.listener = listener;
	}

	public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
    
    
    
    class MyOnGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        	// &&    && Math.abs(distanceX) > swipe_threshold_veloicty    Math.abs(distanceX) > swipe_threshold_veloicty
        	
        	if (e2.getX() - e1.getX() > swipe_min_distance )
            {
       		 progress +=2;
       		 if(progress >= 100){
       			progress = 100;
       		 }
       		listener.pressListener(progress+"");
       		 invalidate();
            }

            if(e1.getX() - e2.getX() > swipe_min_distance)
            {
       		 progress -=2;
       		if(progress <= 0){
       			progress = 0;
       		 }
       		listener.pressListener(progress+"");
       		 invalidate();
            }
        	return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        	return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }
    }
    
  
}
