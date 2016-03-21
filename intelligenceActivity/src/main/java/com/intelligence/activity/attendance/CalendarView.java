package com.intelligence.activity.attendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intelligence.activity.R;
import com.intelligence.activity.utils.StringUtil;

import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class CalendarView extends LinearLayout implements OnClickListener,View.OnTouchListener,GestureDetector.OnGestureListener {

    private Context context;
    
    private Calendar curCalendar;
    
    private OnDayChangeListener dayChangeListener;
    private OnMonthChangeListener monthChangeListener;

    private TextView resultTextView, curDayTextView;
    
    private Calendar toDayCalendar = Calendar.getInstance();
    
    private ImageView beforeMonthImageView, afterMonthImageView;

	private ArrayList<TextView> textViews = new ArrayList<TextView>();
    GestureDetector mGestureDetector;
    private static final int FLING_MIN_DISTANCE = 50;
    private static final int FLING_MIN_VELOCITY = 0;
    public ArrayList<String> attendanceList=new ArrayList<String>();
    public String month;
    public String day;
    public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		View.inflate(context, R.layout.calendar, this);

        mGestureDetector = new GestureDetector(this);
        LinearLayout ll=(LinearLayout)findViewById(R.id.calendar_view);
        ll.setOnTouchListener(this);
        ll.setLongClickable(true);


        curDayTextView = (TextView) this.findViewById(R.id.curDayTextView);
		afterMonthImageView = (ImageView) this.findViewById(R.id.afterMonthImageView);
		beforeMonthImageView = (ImageView) this.findViewById(R.id.beforeMonthImageView);
		afterMonthImageView.setOnClickListener(this);
		beforeMonthImageView.setOnClickListener(this);

		textViews.add((TextView) this.findViewById(R.id.day0TextView));
		textViews.add((TextView) this.findViewById(R.id.day1TextView));
		textViews.add((TextView) this.findViewById(R.id.day2TextView));
		textViews.add((TextView) this.findViewById(R.id.day3TextView));
		textViews.add((TextView) this.findViewById(R.id.day4TextView));
		textViews.add((TextView) this.findViewById(R.id.day5TextView));
		textViews.add((TextView) this.findViewById(R.id.day6TextView));
		textViews.add((TextView) this.findViewById(R.id.day7TextView));
		textViews.add((TextView) this.findViewById(R.id.day8TextView));
		textViews.add((TextView) this.findViewById(R.id.day9TextView));
		textViews.add((TextView) this.findViewById(R.id.day10TextView));
		textViews.add((TextView) this.findViewById(R.id.day11TextView));
		textViews.add((TextView) this.findViewById(R.id.day12TextView));
		textViews.add((TextView) this.findViewById(R.id.day13TextView));
		textViews.add((TextView) this.findViewById(R.id.day14TextView));
		textViews.add((TextView) this.findViewById(R.id.day15TextView));
		textViews.add((TextView) this.findViewById(R.id.day16TextView));
		textViews.add((TextView) this.findViewById(R.id.day17TextView));
		textViews.add((TextView) this.findViewById(R.id.day18TextView));
		textViews.add((TextView) this.findViewById(R.id.day19TextView));
		textViews.add((TextView) this.findViewById(R.id.day20TextView));
		textViews.add((TextView) this.findViewById(R.id.day21TextView));
		textViews.add((TextView) this.findViewById(R.id.day22TextView));
		textViews.add((TextView) this.findViewById(R.id.day23TextView));
		textViews.add((TextView) this.findViewById(R.id.day24TextView));
		textViews.add((TextView) this.findViewById(R.id.day25TextView));
		textViews.add((TextView) this.findViewById(R.id.day26TextView));
		textViews.add((TextView) this.findViewById(R.id.day27TextView));
		textViews.add((TextView) this.findViewById(R.id.day28TextView));
		textViews.add((TextView) this.findViewById(R.id.day29TextView));
		textViews.add((TextView) this.findViewById(R.id.day30TextView));
		textViews.add((TextView) this.findViewById(R.id.day31TextView));
		textViews.add((TextView) this.findViewById(R.id.day32TextView));
		textViews.add((TextView) this.findViewById(R.id.day33TextView));
		textViews.add((TextView) this.findViewById(R.id.day34TextView));
		textViews.add((TextView) this.findViewById(R.id.day35TextView));
		textViews.add((TextView) this.findViewById(R.id.day36TextView));
		textViews.add((TextView) this.findViewById(R.id.day37TextView));
		textViews.add((TextView) this.findViewById(R.id.day38TextView));
		textViews.add((TextView) this.findViewById(R.id.day39TextView));
		textViews.add((TextView) this.findViewById(R.id.day40TextView));
		textViews.add((TextView) this.findViewById(R.id.day41TextView));

		curCalendar = (Calendar) toDayCalendar.clone();
        day=CalendarUtil.millis2Calendar(curCalendar.getTimeInMillis(), CalendarUtil.FORMAT_day);
        month=CalendarUtil.millis2Calendar(curCalendar.getTimeInMillis(),CalendarUtil.FORMAT_month);
		setData();
	}
	
    public void setResultTextView(TextView resultTextView) {
        this.resultTextView = resultTextView;
    }
    
    public void setDayChangeListener(OnDayChangeListener dayChangeListener) {
        this.dayChangeListener = dayChangeListener;
    }
    public void setMonthChangeListener(OnMonthChangeListener monthChangeListener) {
        this.monthChangeListener = monthChangeListener;
    }
    
    private void resetTextViews() {
        int size = textViews.size();
        for (int i = 0; i < size; i++) {
            setTextView(textViews.get(i), StringUtil.EMPTY, R.color.common_calendar_gray, R.color.common_transparent, false);
        }
    }

    public void setData() {
        //attendanceList
        resetTextViews();
        int[] array = getIndexAndCount();
        int index = 1;
        for (int i = 0; i < array[0] + array[1]; i++) {
            if (i < array[0]) {
                setTextView(textViews.get(i), StringUtil.EMPTY, R.color.common_calendar_gray, R.color.common_transparent, false);
            } else {
                curCalendar.set(Calendar.DATE, index);//jump to the day want to draw
                TextView textView = textViews.get(i - 1);
                textView.setTag(curCalendar.getTimeInMillis());//using tag we can make sure what the date is this textview
                boolean enabled = true;
                String text = CalendarUtil.millis2Calendar(curCalendar.getTimeInMillis(), CalendarUtil.FORMAT_DATE_DAY);//get date
                int  bgColor = R.color.common_transparent;
                int textColor = R.color.common_calendar_black;
                setTextView(textView, text, textColor, bgColor, enabled);
                index++;
            }
        }
        setArrowEnable();
        curDayTextView.setText(CalendarUtil.millis2Calendar(curCalendar.getTimeInMillis(), CalendarUtil.FORMAT_CHINESE));
    }
    
    private void setTextView(TextView textView, String text, int textColor, int bgColor, boolean enabled) {
        textView.setText(text);
        textView.setEnabled(enabled);
        textView.setOnClickListener(enabled ? this : null);
        textView.setTextColor(getResources().getColor(textColor));
        textView.setBackgroundColor(getResources().getColor(bgColor));
    }

	public int[] getIndexAndCount() {
	    curCalendar.set(Calendar.DATE, 1);//get the first day of this month, calendar.date means dd in yyyymmdd
		int index = curCalendar.get(Calendar.DAY_OF_WEEK);//
		curCalendar.roll(Calendar.DATE, -1);//add -1 to date of this month? but the month will not change.with this we can get the last day of this monty
		int countDays = curCalendar.get(Calendar.DATE);
		return new int[] { index, countDays };
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.afterMonthImageView:
                curCalendar.setTimeInMillis(curCalendar.getTimeInMillis() + CalendarUtil.ONE_DAY);
                //month=
                month=CalendarUtil.millis2Calendar(curCalendar.getTimeInMillis(),CalendarUtil.FORMAT_month);
                setData();
                if (null != monthChangeListener) {
                    monthChangeListener.onMonthChange();
                }
                break;
            case R.id.beforeMonthImageView:
                curCalendar.set(Calendar.DATE, 1);
                curCalendar.setTimeInMillis(curCalendar.getTimeInMillis() - CalendarUtil.ONE_DAY);
                month=CalendarUtil.millis2Calendar(curCalendar.getTimeInMillis(),CalendarUtil.FORMAT_month);
                //day=
                setData();
                if (null != dayChangeListener) {
                    monthChangeListener.onMonthChange();
                }
                break;
            default:
                if (v instanceof TextView) {
//                    if (null != resultTextView) {
//                        TextView textView = (TextView) v;
//                        long millis = (Long) textView.getTag();
//                        resultTextView.setTag(millis);
//                        day=CalendarUtil.millis2Calendar(millis,CalendarUtil.FORMAT_day);
//                        resultTextView.setText(CalendarUtil.getWeekDate(context, millis));
//                    }
                    if (null != dayChangeListener) {
                        TextView textView = (TextView) v;
                        long millis = (Long) textView.getTag();
                        day=CalendarUtil.millis2Calendar(millis,CalendarUtil.FORMAT_day);
                        dayChangeListener.onDayChange();
                    }
                }
                break;
        }
    }
    
    private void setArrowEnable() {
        long curMillis = curCalendar.getTimeInMillis();
        long todayMillis = toDayCalendar.getTimeInMillis();
        afterMonthImageView.setEnabled( true );
        Calendar tempCalendar = (Calendar) curCalendar.clone();
        tempCalendar.set(Calendar.DATE, 1);
        beforeMonthImageView.setEnabled(true);
    }

    public void repaintByDayAttendance(ArrayList<DayAttendanceBean> days){
        int temp=0;
        for (DayAttendanceBean d:days) {
            for(int i=temp;i<textViews.size();i++){
                if(d.containTag(textViews.get(i).getText().toString())){
                    temp=i+1;
                    switch (d.status){
                        case "0":
                            textViews.get(i).setBackgroundColor(getResources().getColor(R.color.green));
                            break;
                        case "1":
                            textViews.get(i).setBackgroundColor(getResources().getColor(R.color.yellow));
                            break;
                        case "2":
                            textViews.get(i).setBackgroundColor(getResources().getColor(R.color.red));
                            break;
                    }
                }
            }
        }
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e("fling","fling");
        if (e1.getX()-e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // Fling left
            //Toast.makeText(this, "向左手势", Toast.LENGTH_SHORT).show();
            afterMonthImageView.performClick();
        } else if (e2.getX()-e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            // Fling right
            //Toast.makeText(this, "向右手势", Toast.LENGTH_SHORT).show();
            beforeMonthImageView.performClick();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public interface OnDayChangeListener {
        public void onDayChange();
    }
    public interface OnMonthChangeListener {
        public void onMonthChange();
    }

}
