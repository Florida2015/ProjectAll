package com.hxxc.user.app.widget.verticalpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.ScrollView;

import com.hxxc.user.app.utils.LogUtil;

/**
 * 只为顶部ScrollView使用
 * 如果使用了其它的可拖拽的控件，请仿照该类实现isAtBottom方法
 */
public class CustScrollView extends ScrollView {
    private static final int TOUCH_IDLE = 0;
    private static final int TOUCH_INNER_CONSIME = 1; // touch事件由ScrollView内部消费
    private static final int TOUCH_DRAG_LAYOUT = 2; // touch事件由上层的DragLayout去消费

    private boolean isAtTop;
    boolean isAtBottom; // 按下的时候是否在底部
    private int mTouchSlop = 4; // 判定为滑动的阈值，单位是像素
    private int scrollMode;
    private float downY;


    public CustScrollView(Context arg0) {
        this(arg0, null);
    }

    public CustScrollView(Context arg0, AttributeSet arg1) {
        this(arg0, arg1, 0);
    }

    public CustScrollView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }
    ViewParent parent  = getParent();
    public void setType(int type){
        parent = this;
        for (int a=0;a<type;a++){
            parent = parent.getParent();
        }
    }

    private int page = 1;//是上一页还是下一页
    public void setPager (int page){
        this.page = page;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downY = ev.getRawY();
            isAtBottom = isAtBottom();

            isAtTop = isAtTop();
            LogUtil.e("isAtBottom == " + isAtBottom+"**************isAtTop=="+isAtTop);
            scrollMode = TOUCH_IDLE;
            parent.requestDisallowInterceptTouchEvent(true);//消息必须向下传递

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (scrollMode == TOUCH_IDLE) {
                float yOffset = downY - ev.getRawY();
                float yDistance = Math.abs(yOffset);
                if (yDistance > mTouchSlop) {
                    if(page == 1){
                        if (yOffset > 0 && isAtBottom) {
                            scrollMode = TOUCH_DRAG_LAYOUT;
                            parent.requestDisallowInterceptTouchEvent(false);//消息可以不向下传递
                            return true;
                        }
                    }else{
                        if(yOffset<0 && isAtTop){
                            scrollMode = TOUCH_DRAG_LAYOUT;
                            parent.requestDisallowInterceptTouchEvent(false);//消息可以不向下传递
                            return true;
                        }
                    }
                    scrollMode = TOUCH_INNER_CONSIME;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollMode == TOUCH_DRAG_LAYOUT) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    private boolean isAtBottom() {
        return getScrollY() + getMeasuredHeight() >= computeVerticalScrollRange() - 2;
    }

    private boolean isAtTop() {
        LogUtil.e("ScrollY == " + getScrollY());
        return getScrollY() < 2;
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY/2);//快速滑动速度降低一半
    }
}
