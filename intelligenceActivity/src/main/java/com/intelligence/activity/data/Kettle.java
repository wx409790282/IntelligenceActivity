package com.intelligence.activity.data;

/**
 * Created by wx091 on 2016/2/27.
 */
public class Kettle extends Machine {
    public KettleState kettleState;

    public KettleState getKettleState() {
        return kettleState;
    }

    public void setKettleState(KettleState kettleState) {
        this.kettleState = kettleState;
    }
    public void initKettleState(){
        this.kettleState.init();
    }
    public Kettle(Machine machine){
        super(machine);
        this.setType(Constants.machineType.kettle);
        //initKettleState();
    }
}
