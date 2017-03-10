package com.hxxc.user.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.OverScroller;

import com.hxxc.user.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqun on 16/6/7.
 */
public class SelectView extends View {
    private boolean isMoving = false;

    private Paint mPaint;//背景刻度画笔
    private Paint mPaintRed;//中间刻度画笔

    private Paint mPaintText;//文字画笔
    private float mTextSize = 0;
    private float mPointX = 0f;
    private float mPointXoff = 0f;
    private int mPadding = dip2px(1);
    private OverScroller scroller;//控制滑动
    private VelocityTracker velocityTracker;

    private float mUnit = 50;
    private int mMaxValue = 200;
    private int mMinValue = 150;
    private int mMiddleValue = (mMaxValue + mMinValue) / 2;
    private int mUnitLongLine = 5;
    private boolean isCanvasLine = true;

    private int bgColor = Color.rgb(228, 228, 228);
    private int textColor = Color.rgb(151, 151, 151);
    private int textSelectColor = Color.rgb(151, 151, 151);

    private Paint mLinePaint;
    private int minvelocity;
    private int mWidth;
    private int mHeight;
    private float mCurrentValue;

    public SelectView(Context context) {
        this(context, null);
    }

    public SelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new OverScroller(context);
        minvelocity = ViewConfiguration.get(getContext())
                .getScaledMinimumFlingVelocity();
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SelectView);
        if (typedArray != null) {
            isCanvasLine = typedArray.getBoolean(R.styleable.SelectView_isCanvasLine, true);
            mTextSize = dip2px(13);
            bgColor = typedArray.getColor(R.styleable.SelectView_bgColor, Color.rgb(228, 228, 228));
            textColor = Color.parseColor("#aaaaaa");
            mUnit = typedArray.getDimension(R.styleable.SelectView_unitSize, 50.f);
            mUnitLongLine = typedArray.getInteger(R.styleable.SelectView_unitLongLine, 5);
            textSelectColor = typedArray.getColor(R.styleable.SelectView_textSelectColor, Color.rgb(151, 151, 151));

            typedArray.recycle();
        }
        initPaint();
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#cccccc"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dip2px(0.7f));

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(textColor);
        mPaintText.setTextSize(mTextSize);
        mPaintText.setStyle(Paint.Style.FILL);

        mPaintRed = new Paint();
        mPaintRed.setAntiAlias(true);
        mPaintRed.setColor(Color.parseColor("#fbb02b"));
        mPaintRed.setStrokeWidth(dip2px(1) * 3 / 2);
        mPaintRed.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.parseColor("#cccccc"));
        mLinePaint.setStrokeWidth(dip2px(0.7f));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvasBg(canvas);
        canvasLineAndText(canvas);
        canvasRedLine(canvas);
    }


    private void canvasBg(Canvas canvas) {//画圆角矩形
//        RectF rectF = new RectF();
//        rectF.top = mPadding;
//        rectF.left = mPadding;
//        rectF.bottom = getMeasuredHeight() - mPadding;
//        rectF.right = getMeasuredWidth() - mPadding;
//        canvas.drawRoundRect(rectF, dip2px(2), dip2px(2), mPaint);
        canvas.drawLine(0, mHeight - mPadding, mWidth, mHeight - mPadding, mLinePaint);
    }

    private void canvasRedLine(Canvas canvas) {//中间红色刻度
        Path pathRed = new Path();
        pathRed.moveTo(getMeasuredWidth() / 2, getMeasuredHeight() - mPadding);
        pathRed.lineTo(getMeasuredWidth() / 2, 0);
        canvas.drawPath(pathRed, mPaintRed);
    }

    private void canvasLineAndText(Canvas canvas) {
        int current = (int) (Math.rint(mPointX / mUnit));
        for (int i = mMinValue; i <= mMaxValue; i += 1) {
            float space = mCurrentValue - i;
            float x = getMeasuredWidth() / 2 - space * mUnit + mPointX;
            if (x > mPadding && x < getMeasuredWidth() - mPadding) {//判断x轴在视图范围内
                float y = getMeasuredHeight() / 2;
//                if (i % mUnitLongLine == 0) {//画长刻度线 默认每5格一个
//
//                    mPaintText.setColor(textColor);
//                    if (Math.abs(mCurrentValue - current - i) < (mUnitLongLine / 2 + 1)) {//计算绝对值在某一区间内文字显示高亮
//                        mPaintText.setColor(textSelectColor);
//                    }
                String text;
                if (0 == i) {
                    text = "0";
                } else {
                    text = listValue.get(i) + "0000";
                }

                canvas.drawText(text,
                        x - getFontlength(mPaintText, text) / 2,
                        y,
                        mPaintText);
                y = getMeasuredHeight() * 2 / 3;

//                } else {
//                    y = y + y * 2 / 3;
//                }
                if (isCanvasLine) {//画长刻度线
                    canvas.drawLine(x, getMeasuredHeight() - mPadding, x, y, mPaint);

                    //绘制中间的短刻度
                    if (i < mMaxValue) {
                        for (int a = 1; a < 3; a++) {
                            float v = mUnit / 5.0f;
                            canvas.drawLine(x + v * ((float) a), getMeasuredHeight() - mPadding, x + v * ((float) a), getMeasuredHeight() * 5 / 6, mPaint);
                        }
                    }
                    if (i > mMinValue) {
                        for (int a = -2; a < 0; a++) {
                            float v = mUnit / 5.0f;
                            canvas.drawLine(x + v * ((float) a), getMeasuredHeight() - mPadding, x + v * ((float) a), getMeasuredHeight() * 5 / 6, mPaint);
                        }
                    }
                }
            }
        }
    }


    private boolean isActionUp = false;
    private float mLastX;
//    private boolean startAnim = true;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float xPosition = event.getX();

        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentValue = (int) Math.rint(mCurrentValue);
                isMoving = true;
                if (mScrolleAnim != null)
                    clearAnimation();
                isActionUp = false;
                scroller.forceFinished(true);

                break;
            case MotionEvent.ACTION_MOVE:
                isActionUp = false;
                mPointXoff = xPosition - mLastX;//计算滑动距离
                mPointX = mPointX + mPointXoff;
                postInvalidate();

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isActionUp = true;
                countVelocityTracker(event);//控制快速滑动
                startAnim();
//                startAnim = true;
                return false;
            default:
                break;
        }

        mLastX = xPosition;
        return true;
    }


    @Override
    public void computeScroll() {

        if (scroller.computeScrollOffset()) {
            float mPointXoff = (scroller.getFinalX() - scroller.getCurrX());
            mPointX = mPointX + mPointXoff * functionSpeed();
            float absmPointX = Math.abs(mPointX);
            float mmPointX = (mMaxValue - mMinValue) * mUnit / 2;
//            if (absmPointX < mmPointX) {//在视图范围内
            startAnim();
//            }
        }
        super.computeScroll();
    }

    /**
     * 控制滑动速度
     *
     * @return
     */
    private float functionSpeed() {
        return 0.2f;
    }

    private void countVelocityTracker(MotionEvent event) {
        velocityTracker.computeCurrentVelocity(1000, 2000);//一秒（1000毫秒）时间单位内运动了2000个像素
        float xVelocity = velocityTracker.getXVelocity();

        float absmPointX = Math.abs(mPointX);
        float mmPointX;
        if (mPointX > 0) {
            mmPointX = Math.abs(mCurrentValue - mMinValue) * mUnit;
        } else {
            mmPointX = Math.abs(mCurrentValue - mMaxValue) * mUnit;
        }
        if (absmPointX > mmPointX) {
            if (Math.abs(xVelocity) > minvelocity) {
                scroller.fling(0, 0, (int) xVelocity / 100, 0, Integer.MIN_VALUE,
                        Integer.MAX_VALUE, 0, 0);
            }
        } else {
            if (Math.abs(xVelocity) > minvelocity) {
                Log.e("chenqun", "xVelocity" + xVelocity + "");
//                if(xVelocity<0){
//                    float v = mPointX - event.getX();
//                    if(Math.abs(xVelocity) > Math.abs(v)){
//                        xVelocity = v-100;
//                    }
//                }
                scroller.fling(0, 0, (int) xVelocity, 0, Integer.MIN_VALUE,
                        Integer.MAX_VALUE, 0, 0);
            }
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    private ScrolleAnim mScrolleAnim;


    private class ScrolleAnim extends Animation {

        float fromX = 0f;
        float desX = 0f;

        public ScrolleAnim(float d, float f) {
            fromX = f;
            desX = d;
        }


        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            mPointX = fromX + (desX - fromX) * interpolatedTime;//计算动画每贞滑动的距离

            invalidate();

            if (1 == interpolatedTime) {
                isMoving = false;
            }
        }
    }


    private void startAnim() {
        float absmPointX = Math.abs(mPointX);
        float mmPointX = 0;
        if (mPointX > 0) {
            mmPointX = Math.abs(mCurrentValue - mMinValue) * mUnit;
        } else {
            mmPointX = Math.abs(mCurrentValue - mMaxValue) * mUnit;
        }
//         mmPointX = (mMaxValue - mMinValue) * mUnit / 2;
        if (absmPointX > mmPointX) {//超出视图范围
            if (mPointX > 0) {//最左
                moveToX(mCurrentValue - mMinValue, 200);
            } else {//最右
                moveToX(mCurrentValue - mMaxValue, 200);
            }

        } else {

//            float more = mCurrentValue - ((int) mCurrentValue);
//            mPointX += more <= 0.5 ? more * mUnit : -(1 - more) * mUnit;
            int space = (int) (Math.rint(mPointX / mUnit));//四舍五入计算出往左还是往右滑动
            moveToX(space, 200);
        }
    }


    private void moveToX(float distance, int time) {
        if (mScrolleAnim != null)
            clearAnimation();
        mScrolleAnim = new ScrolleAnim((distance * mUnit), mPointX);
        mScrolleAnim.setDuration(time);
        startAnimation(mScrolleAnim);
        if (mOnSelect != null)
            if (mCurrentValue - distance <= 0) {
                mOnSelect.onSelectItem(listValue.get(0));
            } else {
                mOnSelect.onSelectItem(listValue.get((int) (mCurrentValue - distance)) + "0000");
            }
    }

    private List<String> listValue = new ArrayList<>();
    private onSelect mOnSelect = null;

    public void showValue(List<String> list, onSelect monSelect) {
        mOnSelect = monSelect;
        listValue.clear();
        listValue.addAll(list);
        mMaxValue = listValue.size() - 1;
        mMinValue = 0;
        mMiddleValue = (mMaxValue + mMinValue) / 2;
    }

    public void setCurrentValue(float currentValue) {
        if (isMoving) {
            return;
        }
        if (currentValue < mMinValue) currentValue = mMinValue;
        if (currentValue > mMaxValue) currentValue = mMaxValue;
        mCurrentValue = currentValue;
        mPointX = 0;

        postInvalidate();
    }

    public interface onSelect {
        void onSelectItem(String value);
    }

    public void setListener(onSelect monSelect) {
        mOnSelect = monSelect;
    }

}
