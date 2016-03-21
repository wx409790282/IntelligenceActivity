package com.intelligence.activity.attendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.data.User;

public class AttendanceMemberSetting extends BaseActivity {
    User user;
    ButtonFlat startbtn,endbtn,startbtn1,endbtn1,startbtn2,endbtn2,addsectionbtn,delsectionbtn;
    LinearLayout l1,l2;
    WeekSelectView weekview;
    ButtonFlat currentbtn;
    String TAG="AttendanceMemberSetting";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_member_setting);

        getData();
        initview();

        setTitle(this.getString(R.string.attendance_group));
        setLeftOnClick(null);
        setRigter(true, this.getString(R.string.save));//and i will set right
        setRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initview() {
        startbtn= (ButtonFlat) findViewById(R.id.attendance_member_starttime);
        endbtn= (ButtonFlat) findViewById(R.id.attendance_member_endtime);
        startbtn1= (ButtonFlat) findViewById(R.id.attendance_member_starttime1);
        endbtn1= (ButtonFlat) findViewById(R.id.attendance_member_endtime1);
        startbtn2= (ButtonFlat) findViewById(R.id.attendance_member_starttime2);
        endbtn2= (ButtonFlat) findViewById(R.id.attendance_member_endtime2);
        weekview= (WeekSelectView) findViewById(R.id.attendance_member_weekview);
        timeOnClickListener timeOnClickListener=new timeOnClickListener();
        startbtn.setOnClickListener(timeOnClickListener);
        endbtn.setOnClickListener(timeOnClickListener);
        startbtn2.setOnClickListener(timeOnClickListener);
        endbtn2.setOnClickListener(timeOnClickListener);
        startbtn1.setOnClickListener(timeOnClickListener);
        endbtn1.setOnClickListener(timeOnClickListener);
        l1= (LinearLayout) findViewById(R.id.attendance_member_hour_setting_layout1);
        l2= (LinearLayout) findViewById(R.id.attendance_member_hour_setting_layout2);
        addsectionbtn= (ButtonFlat) findViewById(R.id.attendance_member_addsection);
        delsectionbtn= (ButtonFlat) findViewById(R.id.attendance_member_deletesection);
        //it is really very stupid to do this, add section by hide or show view.
        addsectionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(l1.getVisibility()==View.GONE){
                    l1.setVisibility(View.VISIBLE);
                }else if(l2.getVisibility()==View.GONE){
                    l2.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(AttendanceMemberSetting.this,AttendanceMemberSetting.this.getString(R.id.addsection_hint),Toast.LENGTH_SHORT).show();
                }
            }
        });
        delsectionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(l2.getVisibility()==View.VISIBLE){
                    l2.setVisibility(View.GONE);
                }else if(l1.getVisibility()==View.VISIBLE){
                    l1.setVisibility(View.GONE);
                }else{
                    Toast.makeText(AttendanceMemberSetting.this,AttendanceMemberSetting.this.getString(R.id.delsection_hint),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData(){
        Intent intent = getIntent();
        if(intent != null){
            user= (User) intent.getSerializableExtra("data");
        }
    }
    private class timeOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(AttendanceMemberSetting.this,ClockActivity.class);
            switch (v.getId()){
                case R.id.attendance_member_endtime:
                    currentbtn=endbtn;
                    break;
                case R.id.attendance_member_starttime:
                    currentbtn=startbtn;
                    break;
                case R.id.attendance_member_endtime1:
                    currentbtn=endbtn1;
                    break;
                case R.id.attendance_member_starttime1:
                    currentbtn=startbtn1;
                    break;
                case R.id.attendance_member_endtime2:
                    currentbtn=endbtn2;
                    break;
                case R.id.attendance_member_starttime2:
                    currentbtn=startbtn2;
                    break;
            }
            intent.putExtra("time",currentbtn.getText().toString());//09:00
            startActivityForResult(intent,0);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
            Log.e(TAG,result);
            currentbtn.setText(result);
        }
    }
}
