package com.intelligence.activity.attendance;

/**
 * Created by wx091 on 2016/3/10.
 */
public class HourAttendanceBean {
    public String id;
    public String tp_appid;
    public String tp_machineid;
    public String createtime;//,it is a timestamp，19700101以来的时间戳
    public String create_ip;
    public String type;
    public String longitude;
    public String latitude;
    public String distance;
    public String last_active_time;//,it is a timestamp，19700101以来的时间戳
    public String last_active_ip;
    public String last_active_type;
    public String last_active_distance;
    public boolean inSection(String s,String e){
        if(last_active_time.compareTo(s)<0 || createtime.compareTo(e)>0){
            return false;
        }
        return true;
    }
    public HourAttendanceBean(String s, String e){
        createtime=s;
        last_active_time=e;
    }
}
