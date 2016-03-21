package com.intelligence.activity.humidifier;

import java.util.Random;

import com.intelligence.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

public class QipaoImage extends RelativeLayout {
//	private QipaoView qipaoView;
	public boolean anstop = false;
	public QipaoImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
//		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.qipao_image, this);

	}

	public void startan(){
		int atime = 1000;

		AnimationSet animationSet = new AnimationSet(true);		
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(
				0, 1.5f,0,1.5f,
				Animation.RELATIVE_TO_SELF,0.5f,
				Animation.RELATIVE_TO_SELF,0.5f);
		scaleAnimation.setDuration(atime);
		
		animationSet.addAnimation(scaleAnimation);


		Random r = new Random();  
		int n = r.nextInt(20)+1;
		TranslateAnimation translateAnimation =
				new TranslateAnimation(
						Animation.RELATIVE_TO_SELF,0f,
						Animation.RELATIVE_TO_SELF,(n-10)/10.0f,
						Animation.RELATIVE_TO_SELF,1f,
						Animation.RELATIVE_TO_SELF,0f);
		translateAnimation.setDuration(atime);
		animationSet.addAnimation(translateAnimation);


		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		//设置动画执行的时间
		alphaAnimation.setDuration(atime);
		//将alphaAnimation对象添加到AnimationSet当中
		animationSet.addAnimation(alphaAnimation);

		animationSet.setAnimationListener(new MyAnimationListener(this));  
		this.startAnimation(animationSet);
	}

	class MyAnimationListener implements AnimationListener{

		QipaoImage qipaoImage;

		public MyAnimationListener(QipaoImage qipaoImage){
			this.qipaoImage = qipaoImage;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			qipaoImage.setVisibility(View.INVISIBLE);
			anstop = true;
//			startan();
//			System.out.println("onAnimationEnd");
//			System.out.println(context.animate());
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			System.out.println("onAnimationEnd");
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}
	}
}
