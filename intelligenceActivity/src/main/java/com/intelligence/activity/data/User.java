package com.intelligence.activity.data;

import java.io.Serializable;

/**
 * Created by wx091 on 2016/2/29.
 */
public class User implements Serializable{

    public String id;
    public String appid;
    public String parent_id;
    public String username;
    public String phonenumber;
    public String email;
    public String is_online;
    public String is_admin;
    public String is_accept;
    public String headurl;
    public String leaf;

    public User(String id,String username,String headurl){
        this.username=username;
        this.id=id;
        this.headurl=headurl;
    }

}
