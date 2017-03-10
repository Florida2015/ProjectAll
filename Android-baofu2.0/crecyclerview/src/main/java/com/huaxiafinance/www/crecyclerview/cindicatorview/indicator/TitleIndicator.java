package com.huaxiafinance.www.crecyclerview.cindicatorview.indicator;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class TitleIndicator extends BaseIndicator {
    private int mTextSize = 16;
    private int mTextColorResId;

    public TitleIndicator(Context context) {
        this(context, null);
    }

    public TitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getTabItemView(PagerAdapter adapter, int position) {
        TextView view = new TextView(getContext());
        view.setText(adapter.getPageTitle(position));
        view.setTextSize(mTextSize);
        view.setGravity(Gravity.CENTER);
        if (mTextColorResId != 0) {
            view.setTextColor(getResources().getColorStateList(mTextColorResId));
        }
        return view;
    }


    @Override
    protected void drawItemUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        if (paint != null) {
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    @Override
    protected void drawTabUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    protected void drawDivider(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    public int getTextColorResId() {
        return mTextColorResId;
    }

    public void setTextColorResId(int mTextColorResId) {
        this.mTextColorResId = mTextColorResId;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    @Override
    public void onTabSelected(View child, int i, int mCurrentPosition) {
        super.onTabSelected(child, i, mCurrentPosition);
        if(child instanceof TextView){
            TextView view = (TextView) child;
            if(i == mCurrentPosition){
                view.setTextSize(16);
            }else{
                view.setTextSize(mTextSize);
            }
        }
    }
}
