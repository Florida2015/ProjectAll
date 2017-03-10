package com.framwork.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * scrollview
 * 冲突
 * Created by wwt on 2014/11/14.
 */
public class CustomScrollview extends ScrollView{


    GestureDetector gestureDetector;

    boolean flag=false;


    public CustomScrollview(Context context) {
        super(context);
    }

    public CustomScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setGestureDetector(GestureDetector gestureDetector){
        this.gestureDetector=gestureDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);//gestureDetector.onTouchEvent(ev)
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        gestureDetector.onTouchEvent(ev);
//        super.dispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);//
    }

    public void setDispatchTouchEvent(boolean flag){
        if (flag) this.flag = true;
        else this.flag = false;
    }

}
