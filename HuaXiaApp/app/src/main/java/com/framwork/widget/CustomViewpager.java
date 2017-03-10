package com.framwork.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类说明：重写viewpager (修改viewpager是否滑动) (某个页面不可滑动)
 * 
 * @author houwen.lai 2014.11.03 17:41
 */
public class CustomViewpager extends ViewPager {

	private boolean isCanScroll =  true;//true为禁止滑动 false可以滑动

	public CustomViewpager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * true为禁止滑动 false可以滑动
	 * 
	 * @param isCanScroll
	 */
	public void setScanScroll(boolean isCanScroll) {
		// TODO Auto-generated method stub
		this.isCanScroll = isCanScroll;
	}

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll)return true;// true为禁止滑动 false可以滑动
        return super.onTouchEvent(ev);
    }

}
