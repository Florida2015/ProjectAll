package com.hxxc.huaxing.app.wedgit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.hxxc.huaxing.app.utils.DisplayUtil;

/**
 * Created by chenqun on 2016/9/27.
 */

public class CircleView extends View {
    private float mAnimDuration = 3000;

    private final Context mContext;
    private Paint paint;
    private Paint   textPaint;
    private int CENTER_Y;
    private int CENTER_X;
    private RectF rectF = new RectF();
    private float strokeWidth ;

    private float progress = 0;
    private float newProgress;
    private float oriProgress = 0;
    private ViewAnim mViewAnim = new ViewAnim();
    private String mText = "抢购";

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        strokeWidth = DisplayUtil.dip2px(mContext,2);

        paint = new Paint();
        paint.setColor(Color.parseColor("#dddddd"));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor("#be7f24"));
        textPaint.setTextSize(DisplayUtil.dip2px(mContext,13));
        textPaint.setAntiAlias(true);

        padding = DisplayUtil.dip2px(mContext,5);
    }

    private int padding;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        CENTER_X = CENTER_Y = height / 2;
        rectF.set(padding,padding,height-padding,height-padding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.parseColor("#dddddd"));
        canvas.drawCircle(CENTER_X,CENTER_Y,CENTER_X-padding,paint);
        paint.setColor(Color.parseColor("#be7f24"));
        float degree = progress*360f/100f;
        canvas.drawArc(rectF,-90,degree,false,paint);

        int i = DisplayUtil.calcTextWidth(textPaint, mText);
        int i1 = DisplayUtil.calcTextHeight(textPaint, mText);
        canvas.drawText(mText,CENTER_X-i/2,CENTER_Y+i1/2-strokeWidth/2,textPaint);
    }

    public CircleView setText(String text){
        this.mText = text;
        if("抢购".equals(text) || "还款中".equals(text)){
            textPaint.setColor(Color.parseColor("#be7f24"));
        }else{
            textPaint.setColor(Color.parseColor("#dddddd"));
        }
        invalidate();
        return this;
    }

    public void setProgress(float prog) {
        float v = prog - this.progress;
        float newDuration;
        if(v<50){
            newDuration = mAnimDuration/2f;
        }else{
            newDuration = Math.abs(v * mAnimDuration / 100f);
        }

        this.newProgress = prog;
        this.oriProgress = progress;

        if (null != mViewAnim )
            clearAnimation();

        assert mViewAnim != null;
        mViewAnim.setDuration((long) newDuration);
        startAnimation(mViewAnim);
    }

    private class ViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float v = newProgress - oriProgress;
            progress = oriProgress + v*interpolatedTime;
            invalidate();
        }
    }
}
