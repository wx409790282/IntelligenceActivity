package com.intelligence.activity.attendance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wx091 on 2016/3/10.
 * 用来画日图的方框，
 */
public class HourRectView extends View {

    int total=2400;
    int height=20;
    int textheight=50;
    String start="0000";
    String end="2400";
    ArrayList<HourAttendanceBean> sections=new ArrayList<HourAttendanceBean>();
    ArrayList<HourAttendanceBean> hourAttendances=new ArrayList<HourAttendanceBean>();
    //必须加入构造函数,也可以使用另外两个构造函数
    public HourRectView(Context context, ArrayList<HourAttendanceBean> sections, ArrayList<HourAttendanceBean> hourAttendances) {
        super(context);
        //下面两个设置聚焦和触摸模式，可以不设置，这里设置了，因为其他地方要加入这两个事件
        //setFocusable(true);
        //setFocusableInTouchMode(true);
        this.sections=sections;
        this.hourAttendances=hourAttendances;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint _Paint = new Paint();
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        _Paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, total, height, _Paint);//firstly ,draw an rectange red,means absent
        canvas.drawText("00:00", 0, textheight, textPaint);//draw start text
        canvas.drawText("24:00", total - 60, textheight, textPaint);

        double pix=((double)total)/countLength("0000", "2400");

        _Paint.setColor(Color.RED);//then change to green,repaint rectange with green
        for(HourAttendanceBean h:sections){
            String createtime=convertTimestamp(h.createtime);
            String endtime=convertTimestamp(h.last_active_time);
            int drawStart=(int)(countLength(start,createtime)*pix),drawEnd=(int)(countLength(start, endtime)*pix);
            canvas.drawText(createtime, drawStart-30, textheight, textPaint);//draw start text of h
            //-30 because i want to draw text in the center of two colour spit?
            canvas.translate(0,200);
            canvas.drawText(endtime,drawEnd-50,textheight,textPaint);
            //canvas.drawText(endtime,drawEnd-50,textheight+100,textPaint);//draw end text of h
            //-50 because i need to show that words

            canvas.drawRect(drawStart, 0, drawEnd, height, _Paint);
        }

        _Paint.setColor(Color.GREEN);//then change to green,repaint rectange with green
        for(HourAttendanceBean h:hourAttendances){
            String createtime=convertTimestamp(h.createtime);
            String endtime=convertTimestamp(h.last_active_time);
                int drawStart=(int)(countLength(start,createtime)*pix),drawEnd=(int)(countLength(start, endtime)*pix);
                canvas.drawText(createtime, drawStart-30, textheight, textPaint);//draw start text of h
                    //-30 because i want to draw text in the center of two colour spit?

                canvas.drawText(endtime,drawEnd-50,textheight,textPaint);//draw end text of h
                    //-50 because i need to show that words

                canvas.drawRect(drawStart, 0, drawEnd, height, _Paint);
        }

    }
    private int countLength(String s1,String s2){//0000,or 0900中间的距离，每个距离1dp
        try{
            int i1=Integer.valueOf(s1);
            int i2=Integer.valueOf(s2);
            return i2-i1;
        }catch(Exception e){

        }
        return 0;
    }
    private String convertTimestamp(String timestamp){//时间戳改为1930格式
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
// 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(timestamp);
        return  sdf.format(new Date(lcc_time));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)//因为有个scrollview在外面，不限定高度的话显示不出来
    {
        setMeasuredDimension(2400, 2400);
    }
}