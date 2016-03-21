package com.intelligence.activity.humidifier;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.intelligence.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HumStaHeadView extends RelativeLayout {

	TextView timetitle;
	TextView pjtimelal;
	TextView zdsdlal;
	TextView zdczlal;
	
	public HumStaHeadView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		inflater.inflate(R.layout.hum_sta_head_view, this);
		
		Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
//        System.out.println("上个月是："+new SimpleDateFormat("yyyy年MM月").format(c.getTime()));
		
		 timetitle = (TextView) findViewById(R.id.timetitle);
		 pjtimelal = (TextView) findViewById(R.id.pjtimelal);
		 zdsdlal = (TextView) findViewById(R.id.zdsdlal);
		 zdczlal = (TextView) findViewById(R.id.zdczlal);
		 
		 timetitle.setText("上个月使用情况("+new SimpleDateFormat("yyyy-MM").format(c.getTime())+")");
		
	}

	public void setvalue(JSONObject object){
		int bs = 0;
	    try {
			zdsdlal.setText(object.getString("humidity")+"%");
			bs = object.getInt("second");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    int ss = bs % 60;
	    
	    int mm = (bs - ss)/60 % 60;
	    
	    int hh = (bs - ss - mm*60)/3600 % 24;
	    
	    int dd = (bs - ss - mm*60 - hh*3600)/(3600*24);
	    
	    String str = "";
	    str =dd+"天 "+hh+"小时 "+mm+"分钟";
	    
	    if (dd == 0) {
	    	str = hh+"小时 "+mm+"分钟";
	    }
	    
	    if (dd ==0 && hh == 0) {
	    	str = mm+"分钟";
	    }
	    
	    if (dd ==0 && hh == 0 && mm==0) {
	        str= "0分钟";//[NSString stringWithFormat:@"%is",ss];
	    }
	    
	    if (dd ==0 && hh == 0 && mm==0 && ss ==0) {
	        str= "0分钟";
	    }

	    
	    
	    pjtimelal.setText(str);
	   
	}
}
