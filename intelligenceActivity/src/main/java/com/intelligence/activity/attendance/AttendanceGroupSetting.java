package com.intelligence.activity.attendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.data.Machine;

public class AttendanceGroupSetting extends BaseActivity {
    Machine data;
    EditText nameet,idet;
    ButtonFlat startbtn,endbtn,currentbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_group_setting);

        setTitle(this.getString(R.string.attendance_group_setting));
        setLeftOnClick(null);
        setRigter(true, this.getString(R.string.save));
        setRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initView();
        getData();



    }

    private void getData(){
        Intent intent = getIntent();
        if(intent != null){
            data= (Machine) intent.getSerializableExtra("data");
        }
        //after get data,we need to get group name ,id and hour seting from server
    }

    private void initView() {
        nameet= (EditText) findViewById(R.id.attendance_group_name);
        idet= (EditText) findViewById(R.id.attendance_group_id);
        startbtn= (ButtonFlat) findViewById(R.id.attendance_group_starttime);
        endbtn= (ButtonFlat) findViewById(R.id.attendance_group_endtime);
        startbtn.setOnClickListener(new timeOnClickListener());
        endbtn.setOnClickListener(new timeOnClickListener());

    }


    private class timeOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(AttendanceGroupSetting.this,ClockActivity.class);
            switch (v.getId()){
                case R.id.attendance_group_endtime:
                    currentbtn=endbtn;
                    break;
                case R.id.attendance_group_starttime:
                    currentbtn=startbtn;
                    break;
            }
            startActivityForResult(intent,0);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
            //Log.e(TAG, result);
            currentbtn.setText(result);
        }
    }
}
