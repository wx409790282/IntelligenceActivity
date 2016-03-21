package com.intelligence.activity.view;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.intelligence.activity.view.SlideView.OnSlideListener;

public class SlideViewAdpter extends BaseAdapter implements OnSlideListener{

	protected List<SlideView> mSlideViewList = new ArrayList<SlideView>();

	public List<SlideView> getSlideViewList() {
		return mSlideViewList;
	}

	public void setSlideViewList(List<SlideView> mSlideViewList) {
		this.mSlideViewList = mSlideViewList;
	}

	private SlideView mLastSlideViewWithStatusOn;
	
	 @Override
	    public void onSlide(View view, int status) {
	        if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
	            mLastSlideViewWithStatusOn.shrink();
	        }

	        if (status == SLIDE_STATUS_ON) {
	            mLastSlideViewWithStatusOn = (SlideView) view;
	        }
	    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
}
