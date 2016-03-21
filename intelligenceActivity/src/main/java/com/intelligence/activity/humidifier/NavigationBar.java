package com.intelligence.activity.humidifier;




import com.intelligence.activity.R;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NavigationBar extends LinearLayout{
	private TextView titletxt;
	private ImageButton nav_left_btn;
	private ImageButton nav_left_btn2;
	private Button nav_right_btn1;
	private ImageButton nav_right_btn2;
	private Button nav_right_btn3;
	private ImageButton nav_right_btn4;

	private MyOnClickListener leftlistener;
	private MyOnClickListener leftlistener2;
	private MyOnClickListener rightlistener1;
	private MyOnClickListener rightlistener2;
	private MyOnClickListener rightlistener3;
//	private MyOnClickListener rightlistener4;

	public NavigationBar(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub


		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		inflater.inflate(R.layout.navigationbar, this); 

		titletxt = (TextView) findViewById(R.id.nav_title);
		TextPaint tp = titletxt.getPaint();
		tp.setFakeBoldText(true);

		nav_left_btn = (ImageButton) findViewById(R.id.nav_btn_left);
		nav_left_btn.setVisibility(View.INVISIBLE);
		nav_left_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickleft((ImageButton)v);
			}
		});

		nav_left_btn2 = (ImageButton) findViewById(R.id.nav_btn_left2);
		nav_left_btn2.setVisibility(View.INVISIBLE);
		nav_left_btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickleft2((ImageButton)v);
			}
		});
		

		nav_right_btn1 = (Button) findViewById(R.id.nav_btn_right1);
		nav_right_btn1.setVisibility(View.INVISIBLE);
		nav_right_btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickright1((Button)v);
			}
		});

		nav_right_btn2 = (ImageButton) findViewById(R.id.nav_btn_right2);
		nav_right_btn2.setVisibility(View.INVISIBLE);
		nav_right_btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickright2((ImageButton)v);
			}
		});
		
		nav_right_btn3 = (Button) findViewById(R.id.nav_btn_right3);
		nav_right_btn3.setVisibility(View.INVISIBLE);
		nav_right_btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clickright3((Button)v);
			}
		});
		
		nav_right_btn4 = (ImageButton) findViewById(R.id.nav_btn_right4);
		nav_right_btn4.setVisibility(View.INVISIBLE);
//		nav_right_btn4.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				clickright4((Button)v);
//			}
//		});
	}

	public void showShuaxin(boolean show){
		if (show) {
			nav_left_btn2.setVisibility(View.VISIBLE);
		}else{
			nav_left_btn2.setVisibility(View.INVISIBLE);
		}
	}
	
	public void setShuaxinIcon(boolean isok){
//		if (isok) {
//			nav_left_btn2.setImageResource(R.drawable.shuaxinl);
//		}else{
//			nav_left_btn2.setImageResource(R.drawable.shuaxin);
//		}
	}
	
	public void setTitle(String str){
		titletxt.setText(str);
	}
	public void showLeftBtn(Boolean show){
		if (show) {
			nav_left_btn.setVisibility(View.VISIBLE);
		}else{
			nav_left_btn.setVisibility(View.INVISIBLE);
		}
	}

	public void showRightbtn(int type){
		switch (type) {
		case 0:
			nav_right_btn1.setVisibility(View.VISIBLE);
			nav_right_btn2.setVisibility(View.INVISIBLE);
			nav_right_btn3.setVisibility(View.INVISIBLE);
			nav_right_btn4.setVisibility(View.INVISIBLE);
			break;
		case 1:
			nav_right_btn2.setVisibility(View.VISIBLE);
			nav_right_btn1.setVisibility(View.INVISIBLE);
			nav_right_btn3.setVisibility(View.INVISIBLE);
			nav_right_btn4.setVisibility(View.INVISIBLE);
			break;
		case 2:
			nav_right_btn2.setVisibility(View.INVISIBLE);
			nav_right_btn1.setVisibility(View.INVISIBLE);
			nav_right_btn3.setVisibility(View.VISIBLE);
			nav_right_btn4.setVisibility(View.INVISIBLE);
			break;
		case 3:
			nav_right_btn2.setVisibility(View.INVISIBLE);
			nav_right_btn1.setVisibility(View.INVISIBLE);
			nav_right_btn3.setVisibility(View.INVISIBLE);
			nav_right_btn4.setVisibility(View.INVISIBLE);
			break;
		case 4:
			nav_right_btn2.setVisibility(View.VISIBLE);
			nav_right_btn4.setVisibility(View.VISIBLE);
			
			nav_right_btn1.setVisibility(View.INVISIBLE);
			nav_right_btn3.setVisibility(View.INVISIBLE);
			break;
		default:
			break;
		}
	}

	public void setRightText(String txt){
		nav_right_btn1.setText(txt);
	}


	public void setLeftBtn(String str) {

	}

	public void clickleft(ImageButton btn) {
		this.leftlistener.onClick(btn);
	}
	public void setLeftOnClickListener(MyOnClickListener listener) {
		this.leftlistener = listener;
	}

	
	public void clickleft2(ImageButton btn) {
		this.leftlistener2.onClick(btn);
	}
	public void setLeft2OnClickListener(MyOnClickListener listener) {
		this.leftlistener2 = listener;
	}

	
	public void clickright1(Button btn) {
		this.rightlistener1.onClick(btn);
	}
	public void setRight1OnClickListener(MyOnClickListener listener) {
		this.rightlistener1 = listener;
	}

	public void clickright2(ImageButton btn) {
		this.rightlistener2.onClick(btn);
	}
	public void setRight2OnClickListener(MyOnClickListener listener) {
		this.rightlistener2 = listener;
	}


	public void clickright3(Button btn) {
		this.rightlistener3.onClick(btn);
	}
	public void setRight3OnClickListener(MyOnClickListener listener) {
		this.rightlistener3 = listener;
	}

	
//	public void clickright4(Button btn) {
//		this.rightlistener4.onClick(btn);
//	}
//	public void setRight4OnClickListener(MyOnClickListener listener) {
//		this.rightlistener4 = listener;
//	}

}

