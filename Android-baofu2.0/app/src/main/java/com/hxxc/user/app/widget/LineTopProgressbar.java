package com.hxxc.user.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.hxxc.user.app.utils.LogUtil;

/**
 * Created by Administrator on 2016/11/1.
 */

public class LineTopProgressbar extends View {

    private float mAnimDuration = 3000;
    private float progress = 10;
    private Context mContext;
    private Paint mPaint ;// 渐变色环画笔
    private Paint mPaintBg ;
    private Paint mPaintText;


    private int textColorNomal = Color.parseColor("#b8d7f1");
    private int textColorLight = Color.parseColor("#ffb8d7f1");

    private final int[] mColors = new int[]{
            0xffb8d7f1, 0xffb8d7f1};// 渐变色环颜色
    private float mWidth;
    private float mHeight;

    private RectF rNew = new RectF();
    private RectF rBackground = new RectF();

    private LineTopProgressbar.ViewAnim mViewAnim;

    private int newProgress;
    private int mAlpha = 0;
    private float oriProgress = 0;

    public LineTopProgressbar(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public LineTopProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public LineTopProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }
    private void init() {
        mPaint = new Paint();
        mPaintBg = new Paint();
        mPaintText= new Paint();

        mViewAnim = new LineTopProgressbar.ViewAnim();
        mViewAnim.setDuration((long) mAnimDuration);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(40);
//        mPaint.setColor(mContext.getResources().getColor(R.color.grey_b8d7));

        mPaintBg.setAntiAlias(true);
        mPaintBg.setStyle(Paint.Style.FILL);
        mPaintBg.setStrokeWidth(40);
        mPaintBg.setColor(Color.parseColor("#ff4b9ada"));//Color.parseColor("#ffe6e6e6")

        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setStrokeWidth(4);
        mPaintText.setTextSize(dip2px(11));
        mPaintText.setColor(Color.parseColor("#ffb8d7f1"));
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;

        Shader shader = new LinearGradient(getPaddingLeft(), 0, mWidth-getPaddingRight(), mHeight, mColors, null, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private float padding = 0;//距离左右多少
    private float radio = 10;//圆边半径
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        padding = getPaddingLeft();
        rBackground.left = padding;
        rBackground.right = mWidth - padding;
//        rBackground.top = dip2px(15);
//        rBackground.bottom = dip2px(21);
        rBackground.top = dip2px(25);
        rBackground.bottom = dip2px(31);

        canvas.drawRoundRect(rBackground, radio, radio, mPaintBg);

        float right = (progress * (mWidth - padding - padding) / 100) + padding;
        rNew.left = padding;
        rNew.right = right;
//        rNew.top = dip2px(15);
//        rNew.bottom = dip2px(21);
        rNew.top = dip2px(25);
        rNew.bottom = dip2px(31);
        canvas.drawRoundRect(rNew, radio, radio, mPaint);

        mPaintText.setAlpha(255);
        mPaintText.setColor(textColorLight);
        String curlv = (int)progress + "%";

        float currWidth = (progress * (mWidth - padding - padding) / 100) + padding - getFontlength(mPaintText, curlv) / 2f;
//        canvas.drawText(curlv, currWidth, rNew.bottom + (rNew.bottom - rNew.top) + getFontHeight(mPaintText), mPaintText);
        canvas.drawText(curlv, currWidth, 5+ getFontHeight(mPaintText), mPaintText);
    }

    private class ViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float v = newProgress - oriProgress;
            progress = oriProgress + v*interpolatedTime;
            LogUtil.e("progress=="+progress+"***********interpolatedTime=="+interpolatedTime);
            invalidate();
        }
    }

    // 将dip或dp值转换为px值，保证尺寸大小不变
    public int dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    public float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }
    public float getProgress() {
        return progress;
    }

    public void setProgress(int prog) {
        Log.e("xxxx", "progress==" + prog);
        mAlpha = 0;
        float newDuration;
        float more = prog - progress;
        if(more<50){
            newDuration = mAnimDuration/2f;
        }else{
            newDuration = Math.abs(more * mAnimDuration / 100f);
        }

        this.newProgress = prog;
        this.oriProgress = progress;

        if (null != mViewAnim )
            clearAnimation();

        assert mViewAnim != null;
        mViewAnim.setDuration((long) newDuration);
        LogUtil.e("prog=="+prog+"***********time=="+newDuration);
        startAnimation(mViewAnim);
    }
}
