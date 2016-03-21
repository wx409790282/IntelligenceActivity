package com.intelligence.activity.kettle;

import java.util.Random;

import com.intelligence.activity.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

public class KQipaoImage extends RelativeLayout {
//	private QipaoView qipaoView;
	public boolean anstop = false;
	public KQipaoImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
//		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.kqipao_image, this);

	}

	public void startan(){
		int atime = 1000;

		AnimationSet animationSet = new AnimationSet(true);		

		Random r = new Random();  
		int n = r.nextInt(14)+1;
		TranslateAnimation translateAnimation =
				new TranslateAnimation(
						Animation.RELATIVE_TO_SELF,n,
						Animation.RELATIVE_TO_SELF,n,
						Animation.RELATIVE_TO_SELF,26f,
						Animation.RELATIVE_TO_SELF,0f);
		translateAnimation.setDuration(atime);
		animationSet.addAnimation(translateAnimation);

		animationSet.setAnimationListener(new MyAnimationListener(this));  
		this.startAnimation(animationSet);
	}

	class MyAnimationListener implements AnimationListener{

		KQipaoImage qipaoImage;

		public MyAnimationListener(KQipaoImage qipaoImage){
			this.qipaoImage = qipaoImage;
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			qipaoImage.setVisibility(View.INVISIBLE);
			anstop = true;
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
