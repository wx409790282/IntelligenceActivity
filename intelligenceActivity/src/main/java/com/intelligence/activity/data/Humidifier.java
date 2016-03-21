package com.intelligence.activity.data;

import java.io.Serializable;

/**
 * Created by wx091 on 2016/2/27.
 */
public class Humidifier extends Machine implements Serializable{
    public HumidifierState humidifierState;

    public HumidifierState getHumidifierState() {
        return humidifierState;
    }

    public void setHumidifierState(HumidifierState humidifierState) {
        this.humidifierState = humidifierState;
    }
    public void initHUmidifierState(){
        this.humidifierState.init();
    }
    public Humidifier(Machine machine){
        super(machine);
        this.setType(Constants.machineType.humidifier);
    }
}
