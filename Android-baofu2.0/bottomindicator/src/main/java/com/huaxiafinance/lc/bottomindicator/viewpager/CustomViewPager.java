package com.huaxiafinance.lc.bottomindicator.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chenqun on 2016/8/18.
 */
public class CustomViewPager extends LazyViewPager {

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 事件拦截
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 事件处理
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
