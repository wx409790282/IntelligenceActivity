package com.intelligence.activity.view;


import com.intelligence.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RotateDrawView extends View{



	Point centerpoint = null;

	double fistAngle = 0;
	
	float angle = -295;
	
	double aaa = Math.PI * angle/180;
	
	float i1sx = -1;
	private Bitmap image1,image2,image3;

	public RotateDrawView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public RotateDrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// TODO Auto-generated constructor stub
		image1 = BitmapFactory.decodeResource(getResources(),R.drawable.wendupanu);
		image2 = BitmapFactory.decodeResource(getResources(),R.drawable.wendupanc);
		image3 = BitmapFactory.decodeResource(getResources(),R.drawable.wendupan);

		//		i1sx = this.getWidth()/image1.getWidth();
		//		System.out.println(this.getWidth());
	}

	public int getTemp(){
		return angleToTemp((int)angle);
	}
	
	public void setTemp(int temp){
		angle = tempToAngle(temp);
		aaa = Math.PI * angle/180;
		invalidate();
	}


	@Override  
	public void onDraw(Canvas canvas) {  
		// TODO Auto-generated method stub  

		System.out.println(this.getWidth());

		if (i1sx==-1) {

			i1sx = (this.getWidth()*1.0f)/(image3.getWidth()*1.0f);
			System.out.println(i1sx);
			image1 = small(image1, i1sx);
			image2 = small(image2, i1sx);
			image3 = small(image3, i1sx);
		}

		canvas.drawBitmap(image3, (this.getWidth()-image3.getWidth())/2, (this.getHeight()-image3.getHeight())/2, null);

		Paint paint = new Paint();  
		paint.setColor(Color.GRAY);  


		//让画出的图形是空心的  
		//		paint.setStyle(Paint.Style.STROKE);  
		//设置画出的线的 粗细程度  
		paint.setStrokeWidth(5);  

		int cw = image1.getWidth()/5;
		RectF oval1=new RectF(cw,cw,this.getWidth()-cw,this.getWidth()-cw);
		float ea = (float) (aaa*180/Math.PI);


		canvas.drawArc(oval1,-270, angle, true, paint);



		canvas.drawBitmap(image2, (this.getWidth()-image2.getWidth())/2, (this.getHeight()-image2.getHeight())/2, null);


		Matrix matrix = new Matrix();
		matrix.setTranslate((this.getWidth()-image1.getWidth())/2, (this.getHeight()-image1.getHeight())/2);     //设置图片的旋转中心，即绕（X,Y）这点进行中心旋转

		matrix.preRotate(angle-90, (float)image1.getWidth()/2, (float)image1.getHeight()/2);  //要旋转的角度
		canvas.drawBitmap(image1, matrix, null);


		drawtxt(angleToTemp((int)angle)+"℃", canvas);


		super.onDraw(canvas);  
	}  

	public void drawtxt(String str,Canvas canvas){
		Rect targetRect = new Rect(0, 0, this.getWidth(), this.getHeight());
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStrokeWidth(3);
		paint.setTextSize(this.getWidth()/8);
		String testString = str;
		paint.setColor(Color.TRANSPARENT);
		canvas.drawRect(targetRect, paint);
		paint.setColor(Color.RED);
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		// 转载请注明出处：http://blog.csdn.net/hursing
		int baseline = targetRect.top + (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
		// 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
		paint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(testString, targetRect.centerX(), baseline, paint);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		centerpoint = new Point(this.getWidth()/2,this.getHeight()/2);

		int x = (int)event.getX();
		int y = (int)event.getY();

		//		System.out.println(x+":"+y);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			fistAngle = Math.atan2(y - centerpoint.y,
					x - centerpoint.x);
			break;
		case MotionEvent.ACTION_MOVE:
			double ang = Math.atan2(y - centerpoint.y,
					x - centerpoint.x);


			double angleDiff = fistAngle - ang;

			aaa = aaa-angleDiff;

			double cc = aaa*180/Math.PI;



			int a = (int)cc;

			if (a<-360) {
				a = a + 360;
			}

			if (a>0) {
				a = a-360;
			}


			if (!(a<-65.0f && a>-295.0f)) {
				aaa = aaa+angleDiff;
				return true;
			}

			angle = a;
			System.out.println(angle);


			invalidate();

			fistAngle = ang;

			break;
		case MotionEvent.ACTION_UP:
			//			System.out.println(x+":"+y);
			break;
		case MotionEvent.ACTION_CANCEL:
			//			System.out.println(x+":"+y);
			break;
		default:
			break;
		}

		return true;
	}

	public static Bitmap small(Bitmap bitmap,float value) {
		Matrix matrix = new Matrix(); 
		matrix.postScale(value,value); //长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
		return resizeBmp;
	}

	Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree)
	{

		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
		float targetX, targetY;
		if (orientationDegree == 90) {
			targetX = bm.getHeight()/2;
			targetY = 0;
		} else {
			targetX = bm.getHeight()/2;
			targetY = bm.getWidth()/2;
		}

		final float[] values = new float[9];
		m.getValues(values);

		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];

		m.postTranslate(targetX - x1, targetY - y1);

		Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bm1);
		canvas.drawBitmap(bm, m, paint);

		return bm1;
	}
	
	public int angleToTemp(int ag){
		int temp = (int)((295+ag)/228.0*70);
		return temp+30;
	}
	
	public int tempToAngle(int temp){
		temp = temp - 30;
		int ag = -294+(int)(temp/70.0*229);//(int)((294+ag)/228.0*100);
		return ag;
	}
}
