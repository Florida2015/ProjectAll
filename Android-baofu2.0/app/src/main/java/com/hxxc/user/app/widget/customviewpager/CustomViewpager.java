package com.hxxc.user.app.widget.customviewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/2/15.
 */

public class CustomViewpager  extends ViewPager {

    private boolean isCanScroll =  false;//true为禁止滑动 false可以滑动

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
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScroll)return false;
        return super.onInterceptTouchEvent(arg0);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll)return false;// true为禁止滑动 false可以滑动
        return super.onTouchEvent(ev);
    }
}
