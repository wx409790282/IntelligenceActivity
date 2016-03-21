package com.intelligence.activity.data;

import java.io.Serializable;

/**
 * Created by wx091 on 2016/2/27.
 */
public class Machine implements Serializable {
    public String machineid;
    public Constants.machineType type;
    public String isonline;
    public String name;//it referance to tag set by users ,like light in bedroom,not meachine type
    public String ipString;
    public Machine(){
        this.machineid="0";
        this.isonline="0";
        this.name="0";
        this.ipString="0";
    }
    public Machine(Machine machine){
        this.machineid=machine.getMachineid();
        this.isonline=machine.getIsonline();
        this.name=machine.getName();
        this.ipString=machine.getIpString();
    }
    public Machine(String id,String name,String isonline){
        this.machineid=id;
        this.isonline=isonline;
        this.name=name;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public Constants.machineType getType() {
        return type;
    }

    public void setType(Constants.machineType type) {
        this.type = type;
    }

    public String getIsonline() {
        return isonline;
    }

    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }

    public boolean Isonline() {
        if(isonline.equals("1")){
            return true;
        }else
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpString() {
        return ipString;
    }

    public void setIpString(String ipString) {
        this.ipString = ipString;
    }

}
