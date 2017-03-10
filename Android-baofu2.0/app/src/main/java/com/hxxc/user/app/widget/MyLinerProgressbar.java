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
 * Created by chenqun on 2016/8/5.
 */
public class MyLinerProgressbar extends View {

    private float mAnimDuration = 3000;
    private float progress = 0;
    private Context mContext;
    private Paint mPaint;// 渐变色环画笔
    private Paint mPaintBg;
    private Paint mPaintText;


    private int textColorNomal = Color.parseColor("#ffacacac");
    private int textColorLight = Color.parseColor("#ff0070ff");

    private final int[] mColors = new int[]{
            0xff1f80d1, 0xff1f80d1};// 渐变色环颜色
    private float mWidth;
    private float mHeight;

    private RectF rNew = new RectF();
    private RectF rBackground = new RectF();

    private ViewAnim mViewAnim;

    private int newProgress;
    private int mAlpha = 0;
    private float oriProgress = 0;

    public MyLinerProgressbar(Context context) {
        super(context, null);
    }

    public MyLinerProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MyLinerProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(int prog) {
        if (prog < 0) prog = 0;
        if (prog > 100) prog = 100;
        Log.e("chenqun", "progress==" + prog);
        mAlpha = 0;
        float newDuration;
        float more = prog - progress;
        if (more < 50) {
            newDuration = mAnimDuration / 2f;
        } else {
            newDuration = Math.abs(more * mAnimDuration / 100f);
        }

        this.newProgress = prog;
        this.oriProgress = progress;

        if (null != mViewAnim)
            clearAnimation();

        assert mViewAnim != null;
        mViewAnim.setDuration((long) newDuration);
        LogUtil.e("prog==" + prog + "***********time==" + newDuration);
        startAnimation(mViewAnim);
    }

    private void init() {
        mPaint = new Paint();
        mPaintBg = new Paint();
        mPaintText = new Paint();

        mViewAnim = new ViewAnim();
        mViewAnim.setDuration((long) mAnimDuration);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(40);


        mPaintBg.setAntiAlias(true);
        mPaintBg.setStyle(Paint.Style.FILL);
        mPaintBg.setStrokeWidth(40);
        mPaintBg.setColor(Color.parseColor("#ffe6e6e6"));

        mPaintText.setAntiAlias(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setStrokeWidth(4);
        mPaintText.setTextSize(dip2px(11));
        mPaintText.setColor(Color.parseColor("#ffacacac"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;

        Shader shader = new LinearGradient(getPaddingLeft(), 0, mWidth - getPaddingRight(), mHeight, mColors, null, Shader.TileMode.CLAMP);
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
        rBackground.top = dip2px(15);
        rBackground.bottom = dip2px(21);

        canvas.drawRoundRect(rBackground, radio, radio, mPaintBg);

        float right = (progress * (mWidth - padding - padding) / 100) + padding;
        rNew.left = padding;
        rNew.right = right;
        rNew.top = dip2px(15);
        rNew.bottom = dip2px(21);
        canvas.drawRoundRect(rNew, radio, radio, mPaint);

        mPaintText.setAlpha(255);
        mPaintText.setColor(textColorLight);
        String curlv = (int) progress + "%";

        float currWidth = (progress * (mWidth - padding - padding) / 100) + padding - getFontlength(mPaintText, curlv) * progress / 100f;
        canvas.drawText(curlv, currWidth, rNew.bottom + (rNew.bottom - rNew.top) + getFontHeight(mPaintText), mPaintText);
    }

    private class ViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float v = newProgress - oriProgress;
            progress = oriProgress + v * interpolatedTime;
            LogUtil.e("progress==" + progress + "***********interpolatedTime==" + interpolatedTime);
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

}