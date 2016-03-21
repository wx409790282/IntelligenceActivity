package com.intelligence.activity.attendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import com.gc.materialdesign.views.ButtonRectangle;
import com.intelligence.activity.R;
import com.intelligence.activity.view.PickerView;
import com.intelligence.activity.view.ScrollerNumberPicker;

import java.util.ArrayList;

public class ClockActivity extends Activity {
    private TimePicker provincePicker;

    ButtonRectangle confirmbtn;
    String time="01";
    String TAG="ClockActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        confirmbtn= (ButtonRectangle) findViewById(R.id.clock_confirm);
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result",time);
                //设置返回数据
                ClockActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                ClockActivity.this.finish();
            }
        });
        provincePicker = (TimePicker) findViewById(R.id.pickerview1);
        provincePicker.setIs24HourView(true);

        provincePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
               time=String.format("%02d:%02d",hourOfDay, minute);//必须返回00：00格式
            }
        });
//        cityPicker.setOnSelectListener(new PickerView.onSelectListener() {
//            @Override
//            public void onSelect(String text) {
//                minute = text;
//            }
//        });
        //update default time of this two selector
        Intent i=getIntent();
        if(i!=null){
            String defaultString=i.getStringExtra("time");//传入值必须是00：00格式
            Log.e(TAG, defaultString.substring(0,2));
            provincePicker.setCurrentHour(Integer.valueOf(defaultString.substring(0,2)));
            provincePicker.setCurrentMinute(Integer.valueOf(defaultString.substring(3)));
            //provincePicker.setDefault(Integer.valueOf(defaultString.substring(0,2)));
            //cityPicker.setDefault(Integer.valueOf(defaultString.substring(3)));
        }
    }

}
