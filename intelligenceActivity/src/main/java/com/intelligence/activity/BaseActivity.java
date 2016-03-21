package com.intelligence.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.intelligence.activity.utils.Utils;
import com.intelligence.activity.db.NZ_DBhelperManager;
import com.intelligence.activity.db.NZ_DBhelperManager.NZYData;
import com.intelligence.activity.view.PromptDialog;
import com.intelligence.activity.view.PromptDialog.DialogListener;

public class BaseActivity extends Activity {

	private TextView title;
	private Button right_bt;
	private Button left_bt;
	public static boolean isConn = false;
	public static float density = 0;
	public RequestQueue volley;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）

		volley= Volley.newRequestQueue(getApplicationContext());
//		left_bt= (Button)findViewById(R.id.LButton);
//		left_bt.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//finish();
//			}
//		});
//		right_bt = (Button)findViewById(R.id.RButton);
//		right_bt.setVisibility(View.GONE);
	}


	protected void setWifi(int imgString,View.OnClickListener onClickListener){//for some reason,is wifi is not availiable anymore,so we change it to another function
		ImageView img = (ImageView)findViewById(R.id.is_wifi);
		img.setVisibility(View.VISIBLE);
		img.setImageResource(imgString);
		img.setOnClickListener(onClickListener);

	}

	protected void isNZ(){
		List<NZYData> mList = NZ_DBhelperManager.getInstance(this).getAlermList();
		for(int i = 0 ; i < mList.size() ; i++){
			NZYData nzData = mList.get(i);
			if(nzData.NZ_ISOPEN == 1){
				ImageView img = (ImageView)findViewById(R.id.is_nz);
				img.setVisibility(View.VISIBLE);
				break;
			}
		}
	}

	protected void setTitle(String stitle){
		if(title == null){
			title = (TextView)findViewById(R.id.title);
		}
		title.setText(stitle);
	}

	protected void setRigter(boolean isSee,String rightString){

		if(right_bt == null){
			right_bt = (Button)findViewById(R.id.RButton);
			right_bt.setOnClickListener(onClickListener);

		}
		if(!isSee){
			right_bt.setVisibility(View.GONE);
			return;
		}
		right_bt.setText(rightString);

	}

	protected void setRigter(boolean isSee,int rightString){

		if(right_bt == null){
			right_bt = (Button)findViewById(R.id.RButton);
			right_bt.setOnClickListener(onClickListener);

		}
		if(!isSee){
			right_bt.setVisibility(View.GONE);
			return;
		}
		right_bt.setBackgroundResource(rightString);
		int inPixels= (int) this.getResources().getDimension(R.dimen.right_in_dp);
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(inPixels,inPixels);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		lp.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
		lp.setMargins(0,0,5,0);
		right_bt.setLayoutParams(lp);
		right_bt.setText("");

	}

	protected void setLeftOnClick(View.OnClickListener oncClickListener){
		left_bt = (Button)findViewById(R.id.LButton);
		//left_bt.setBackgroundResource(R.drawable.btn_pressed);

		if(oncClickListener != null){
			left_bt.setOnClickListener(oncClickListener);
		}else{
			left_bt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO �Զ���ɵķ������
					finish();
				}
			});
		}

	}

	protected void setLeftOnClick(int imgID,View.OnClickListener oncClickListener){
		left_bt = (Button)findViewById(R.id.LButton);
		left_bt.setBackgroundResource(imgID);
		if(oncClickListener != null){
			left_bt.setOnClickListener(oncClickListener);
		}else{
			left_bt.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO �Զ���ɵķ������
					finish();
				}
			});
		}

	}


	protected void setRightOnClick(){

	}

	protected void setRightOnClick(View.OnClickListener oncClickListener){
			right_bt = (Button)findViewById(R.id.RButton);
			right_bt.setOnClickListener(oncClickListener);
	}


	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO �Զ���ɵķ������
			if(v.equals(right_bt)){
				setRightOnClick();
			}else{

			}
		}
	};

	protected void show(String content){
		Toast.makeText(this, content, Toast.LENGTH_LONG).show();
	}

	/**
	 * 确定对话框 没有回调
	 * 
	 * @param showStr
	 * @param dialogListener
	 */
	public void showPromptDialog(String showStr) {
		PromptDialog.show(this, showStr, null);
	}

	/**
	 * 确定对话框 有回调
	 * 
	 * @param showStry
	 * @param dialogListener
	 */
	public void showPromptDialog(final String showStr,
			final DialogListener dialogListener, final int flag) {
		PromptDialog.show(this, showStr, dialogListener, flag);
	}

	public void showPromptDialog(final String showStr,
			final DialogListener dialogListener, final int flag,final boolean b) {
		PromptDialog.show(this, showStr, dialogListener, flag,b);
	}
}