package com.intelligence.activity.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.intelligence.activity.BaseActivity;
import com.intelligence.activity.R;
import com.intelligence.activity.data.Machine;
import com.intelligence.activity.data.User;
import com.intelligence.activity.http.GsonGetRequest;
import com.intelligence.activity.http.HttpUrl;

import java.util.ArrayList;

public class AttendanceHistory extends BaseActivity {

    CalendarView calendarView;
    String TAG="AttendanceHistory";
    ArrayList<DayAttendanceBean> monthdata=new ArrayList<>();//0 normal 1absent 2late 3free
    ArrayList<HourAttendanceBean> daydata=new ArrayList<>();
    ArrayList<HourAttendanceBean> sectiondata=new ArrayList<>();
    User user;
    String onPaintDay;
    LinearLayout hourview;
    Machine data;
    LinearLayout hourview1;
    HorizontalScrollView scrollview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_history);

        setTitle(this.getString(R.string.attendance_history));
        setLeftOnClick(null);
        setRigter(true, R.drawable.setting);
        setRightOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceHistory.this, AttendanceMemberSetting.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        getData();

        //sectiondata.add(new HourAttendanceBean("0800","1200"));
        //daydata.add(new HourAttendanceBean("1500","1600"));
        //hourview= (LinearLayout) findViewById(R.id.attendance_history_dayview);
         hourview1= (LinearLayout) findViewById(R.id.attendance_history_dayview1);
        scrollview= (HorizontalScrollView) findViewById(R.id.attendance_history_scrollview);
        calendarView= (CalendarView) findViewById(R.id.attendance_history_calendar);
        //calendarView.attendanceList

        calendarView.setDayChangeListener(new CalendarView.OnDayChangeListener() {
            @Override
            public void onDayChange() {
                Log.e(TAG,"DayChange");
                getDayData();
            }
        });
        calendarView.setMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange() {
                Log.e(TAG, "MonthChange");
                getMonthData();
            }
        });
        getMonthData();
        getDayData();
    }

    private void getDayData() {
        volley.add((getHourAttendanceBeanArray(calendarView.day, new Response.Listener<ArrayList<HourAttendanceBean>>() {

            @Override
            public void onResponse(ArrayList<HourAttendanceBean> response) {
                if(response.size()!=0){
                    daydata=response;
                    //hourview1
                    hourview1.removeAllViews();
                    hourview1.addView(new HourRectView(AttendanceHistory.this,sectiondata,daydata));
                    //scrollview.addView(new HourRectView(AttendanceHistory.this,sectiondata,daydata));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })));
    }

    private void getMonthData() {
        volley.add(getDayAttendanceBeanArray(calendarView.month, new Response.Listener<ArrayList<DayAttendanceBean>>() {
            @Override
            public void onResponse(ArrayList<DayAttendanceBean> response) {
                if(response.size()!=0){
                    monthdata = response;
                    calendarView.repaintByDayAttendance(monthdata);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    private void getData(){
        Intent intent = getIntent();
        if(intent != null){
            user= (User) intent.getSerializableExtra("user");
            data= (Machine) intent.getSerializableExtra("machine");
        }
        //update title of this page,then begin to get month data from server

    }

    public GsonGetRequest<ArrayList<DayAttendanceBean>> getDayAttendanceBeanArray(String month,
            Response.Listener<ArrayList<DayAttendanceBean>> listener,
            Response.ErrorListener errorListener) {
        String url = HttpUrl.GET_MONTH_LIST;
        url+="/appid/"+user.appid;
        url+="/machineid/"+data.getMachineid();
        url+="/time/"+month ;
        Log.e(TAG,url);
        //url+="/machineid/"+data.getMachineid();
        final Gson gson = new GsonBuilder().create();
        return new GsonGetRequest<>(
                url,
                new TypeToken<ArrayList<DayAttendanceBean>>() {}.getType(),
                gson,
                listener,
                errorListener
        );
    }

    public GsonGetRequest<ArrayList<HourAttendanceBean>> getHourAttendanceBeanArray(String day,
                                                                                  Response.Listener<ArrayList<HourAttendanceBean>> listener,
                                                                                  Response.ErrorListener errorListener) {
        String url = HttpUrl.GET_DAY_LIST;
        url+="/appid/"+user.appid;
        url+="/machineid/"+data.getMachineid();
        url+="/time/"+day ;
        Log.e(TAG,url);;
        //url+="/machineid/"+data.getMachineid();
        final Gson gson = new GsonBuilder().create();
        return new GsonGetRequest<>(
                url,
                new TypeToken<ArrayList<HourAttendanceBean>>() {}.getType(),
                gson,
                listener,
                errorListener
        );
    }


    private void rePaintByHours(){//repaint by onpaintday,so remeber to reset onpainday everytime user click day

    }

}
