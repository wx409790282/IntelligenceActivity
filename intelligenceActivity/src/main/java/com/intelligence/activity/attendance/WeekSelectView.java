package com.intelligence.activity.attendance;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.gc.materialdesign.views.CheckBox;
import com.intelligence.activity.R;

/**
 * Created by wx091 on 2016/3/4.
 * 选择星期的集体显示，返回值为1111111，从一到7，有1无0
 */
public class WeekSelectView extends RelativeLayout {

    private Context context;
    private CheckBox c1,c2,c3,c4,c5,c6,c7;
    public WeekSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        View.inflate(context, R.layout.week_select, this);
        c1= (CheckBox) this.findViewById(R.id.monday_switch);
        c2= (CheckBox) this.findViewById(R.id.tuesday_switch);
        c3= (CheckBox) this.findViewById(R.id.wednesday_switch);
        c4= (CheckBox) this.findViewById(R.id.thursday_switch);
        c5= (CheckBox) this.findViewById(R.id.friday_switch);
        c6= (CheckBox) this.findViewById(R.id.saturday_switch);
        c7= (CheckBox) this.findViewById(R.id.sunday_switch);
    }
    public String getWeekSet(){
        return getStringFromCheck(c1)+getStringFromCheck(c2)+getStringFromCheck(c3)+getStringFromCheck(c4)+
                getStringFromCheck(c5)+getStringFromCheck(c6)+getStringFromCheck(c7);
    }
    public String initWeekSet(){
        return "";
    }
    public String getStringFromCheck(CheckBox c){
        if(c.isCheck()){
            return "1";
        }
        return "0";
    }
}
