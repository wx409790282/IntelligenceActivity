package com.intelligence.activity.data;

/**
 * Created by wx091 on 2016/2/27.
 */
public class HumidifierState {
    public String humidity;//100%
    public String level;
    public String state;//0 empty 1 working
    public String anion;
    public String laststarttime;

    public String getAnion() {
        return anion;
    }

    public void setAnion(String anion) {
        this.anion = anion;
    }

    public String lasttpappid;
    public String lastappid;
    public String grade;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public void init(){
        humidity="0";
        level="0";
        state="0";
    }
    public boolean isAnion(){
        if(anion.equals("1")){
            return true;
        }else {
            return false;
        }
    }
    public void changeAnion(){
        if(anion.equals("1")){
            anion="0";
        }else {
            anion="1";
        }
    }
}
