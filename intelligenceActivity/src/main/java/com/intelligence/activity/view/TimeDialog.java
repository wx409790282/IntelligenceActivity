package com.intelligence.activity.view;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;

import com.intelligence.activity.R;
import com.ms.activity.widget.weelview.NumericWheelAdapter;
import com.ms.activity.widget.weelview.WheelView;

public class TimeDialog extends Dialog implements View.OnClickListener{
	private Context context;
	

	private Handler handler;
	public TimeDialog(Context context,Handler handler) {
		super(context,R.style.MyDialogStyleBottom);
		// TODO 自动生成的构造函数存根
		this.context = context;
		this.handler = handler;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_layout);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		
		
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);


		

		

		// 时
		final WheelView wv_hours = (WheelView) findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		// 分
		final WheelView wv_mins = (WheelView) findViewById(R.id.mins);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setCyclic(true);
		wv_mins.setCurrentItem(minute);

		

		// 根据屏幕密度来指定选择器字体的大小
		int textSize = 0;

		textSize = 30;

		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;

		Button btn_sure = (Button) findViewById(R.id.btn_datetime_sure);
		Button btn_cancel = (Button) findViewById(R.id.btn_datetime_cancel);
		// 确定
		btn_sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 如果是个数,则显示为"02"的样式
				String parten = "00";
//				DecimalFormat decimal = new DecimalFormat(parten);
				// 设置日期的显示
				// tv_time.setText((wv_year.getCurrentItem() + START_YEAR) + "-"
				// + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				// + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				// + decimal.format(wv_hours.getCurrentItem()) + ":"
				// + decimal.format(wv_mins.getCurrentItem()));

				if(handler != null){
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append(wv_hours.getCurrentItem());
					stringBuffer.append(":");
					stringBuffer.append(wv_mins.getCurrentItem());
					
					Message msg = new Message();
					msg.obj = stringBuffer.toString();
					msg.what = 101;
					handler.sendMessage(msg);
				}
				dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		
	}
}
