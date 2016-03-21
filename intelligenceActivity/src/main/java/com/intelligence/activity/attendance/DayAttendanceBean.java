package com.intelligence.activity.attendance;

/**
 * Created by wx091 on 2016/3/10.
 */
public class DayAttendanceBean {
    String day;//2016-03-01,while we receive 2016-03-09from the server，实际上20160309也可以
    String time_total;//离开时间总数，秒
    String status;//0正常,1缺勤,2迟到，3不上班
    public DayAttendanceBean(String d, String a){
        this.day=d;this.status=a;
    }
    public boolean containTag(String tag){
        if(day.replace("-","").substring(6).equals(tag))
            return true;
        return false;
    }
}
