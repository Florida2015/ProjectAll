package com.hxxc.user.app.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.hxxc.user.app.R;

public class ArcProgress extends View {
    // 最外层圆环渐变色环颜色
    private final int[] mColors = new int[]{
            0xFFa2cced,
            0xFF4c99d8,
            0xFF1e81cf
    };

    //圆环的信用等级文本
    private static final String[] text = {
            "100", "高",
            "50", "低",
            "0"
    };

    //中间进度颜色
    private static final int GREEN_COLOR = 0xFF06C494;

    private static final int GRAY_COLOR = 0xFFF4F4F4;
    private static final int GRAY_COLOR2 = 0xFFaaaaaa;
    private static final int BLUE_COLOR = 0xFFa2cced;
    private static final int BLUE_COLOR2 = 0xff1f80d1;

    // 宽度
    private int width;

    // 高度
    private int height;

    // 半径
    private int radius;

    // 指针图片
    private Bitmap mBitmap;

    // 指针图片宽度
    private int mBitmapWidth;

    // 指针图片高度
    private int mBitmapHeight;

    // 最外层渐变圆环画笔
    private Paint mGradientRingPaint;


    // 小刻度画笔
    private Paint mBigCalibrationPaint;

    // 中间进度圆环画笔
    private Paint mMiddleRingPaint;

    // 外层圆环文本画笔
    private Paint mTextPaint;

    // 指针图片画笔
    private Paint mPointerBitmapPaint;

    //中间文本画笔
    private Paint mCenterTextPaint;

    // 绘制外层圆环的矩形
    private RectF mOuterArc;

    // 绘制内层圆环的矩形
    private RectF mInnerArc;

    // 绘制中间圆环的矩形
    private RectF mMiddleArc;

    // 中间进度圆环的值
    private float oval4;

    // 绘制中间进度圆环的矩形
    private RectF mMiddleProgressArc;

    // 圆环起始角度
    private static final float mStartAngle = 120f;

    // 圆环结束角度
    private static final float mEndAngle = 220f;

    //动画整体时间
    private static final int time = 3000;

    // 指针全部进度
    private float mTotalAngle = 220f;

    // 指针当前进度
    private float mCurrentAngle = 0f;

    // View默认宽高值
    private int defaultSize;

    // 最小数字
    private int mMinNum = 0;

    // 最大数字
    private int mMaxNum = 950;

    //信用等级
    private String sesameLevel = "低";


    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init()
    {
        //设置默认宽高值
        defaultSize = dp2px(250);

        //设置图片线条的抗锯齿
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter
                (0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        //最外层圆环渐变画笔设置
        mGradientRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置圆环渐变色渲染
        mGradientRingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        float position[] = {0.1f, 0.3f, 0.8f};
        Shader mShader = new SweepGradient(width / 2, radius, mColors, position);
        mGradientRingPaint.setShader(mShader);
//        mGradientRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mGradientRingPaint.setStyle(Paint.Style.STROKE);
        mGradientRingPaint.setStrokeWidth(dp2px(15));

        mBigCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBigCalibrationPaint.setColor(Color.WHITE);
        mBigCalibrationPaint.setStyle(Paint.Style.STROKE);
        mBigCalibrationPaint.setStrokeWidth(dp2px2(16f));

        //中间圆环画笔设置
        mMiddleRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddleRingPaint.setStyle(Paint.Style.STROKE);
        mMiddleRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mMiddleRingPaint.setStrokeWidth(dp2px(7));
        mMiddleRingPaint.setColor(GRAY_COLOR);

        //外层圆环文本画笔设置
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(BLUE_COLOR);
        mTextPaint.setTextSize(dp2px(10));

        //中间文字画笔设置
        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setTextAlign(Paint.Align.CENTER);

        //指针图片画笔
        mPointerBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointerBitmapPaint.setColor(GREEN_COLOR);

        //获取指针图片
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        setMeasuredDimension(resolveMeasure(widthMeasureSpec, defaultSize),
                resolveMeasure(heightMeasureSpec, defaultSize));
    }


    /**
     * 根据传入的值进行测量
     *
     * @param measureSpec
     * @param defaultSize
     */
    public int resolveMeasure(int measureSpec, int defaultSize)
    {

        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec))
        {

            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;

            case MeasureSpec.AT_MOST:
                //设置warp_content时设置默认值
                result = Math.min(specSize, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                //设置math_parent 和设置了固定宽高值
                break;

            default:
                result = defaultSize;
        }

        return result;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {

        super.onSizeChanged(w, h, oldw, oldh);

        //确定View宽高
        width = w;
        height = h;

        //圆环半径
        radius = width / 2;

        //外层圆环
        float oval1 = radius - mGradientRingPaint.getStrokeWidth() * 0.5f;
        mMiddleArc = new RectF(-oval1, -oval1, oval1, oval1);

        //中间和内层圆环
        float oval2 = radius * 5 / 8;
        float oval3 = radius * 3 / 4 - mGradientRingPaint.getStrokeWidth()* 0.5f;
        mInnerArc = new RectF(-oval2, -oval2, oval2, oval2);
        mOuterArc = new RectF(-oval3, -oval3, oval3, oval3);

        //中间进度圆环
        oval4 = radius * 6 / 8;
        mMiddleProgressArc = new RectF(-oval4, -oval4, oval4, oval4);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        //设置画布绘图无锯齿
        canvas.setDrawFilter(mPaintFlagsDrawFilter);

        drawArc(canvas);
        drawCalibration(canvas);
        drawArcText(canvas);
        drawCenterText(canvas);
        drawBitmapProgress(canvas);
    }

    /**
     * 绘制中间进度和指针图片
     *
     * @param canvas
     */
    private void drawBitmapProgress(Canvas canvas)
    {

        canvas.save();
        canvas.translate(radius, radius);
        canvas.rotate(270);
        canvas.rotate( 80 +  mCurrentAngle);
        Matrix matrix = new Matrix();
        matrix.preTranslate(-radius , -mBitmapHeight / 2);
        canvas.drawBitmap(mBitmap, matrix, mPointerBitmapPaint);
        canvas.restore();
    }

    /**
     * 绘制中间文本内容
     *
     * @param canvas
     */
    private void drawCenterText(Canvas canvas)
    {

        //绘制Logo
        mCenterTextPaint.setTextSize(dp2px2(40f));
        mCenterTextPaint.setColor(GRAY_COLOR2);
        canvas.drawText("BETA", radius, radius - dp2px2(80f), mCenterTextPaint);

        //绘制信用分数
        mCenterTextPaint.setColor(BLUE_COLOR2);
        mCenterTextPaint.setTextSize(dp2px2(200f));
        mCenterTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(String.valueOf(mMinNum), radius, radius + dp2px2(120f), mCenterTextPaint);

        //绘制信用级别
        mCenterTextPaint.setColor(BLUE_COLOR);
        mCenterTextPaint.setTextSize(dp2px2(40f));
        canvas.drawText(sesameLevel, radius, radius + dp2px2(220f), mCenterTextPaint);
    }

    /**
     * 绘制圆弧上的文本
     *
     * @param canvas
     */
    private void drawArcText(Canvas canvas)
    {

        for (int i = 0; i < text.length; i++)
        {
            canvas.save();
            canvas.rotate(-(-15 + 50 * i - 85), radius, radius);
            canvas.drawText(text[i], radius -dp2px2(20f), radius * 3 / 16, mTextPaint);
            canvas.restore();
        }
    }

    /**
     * 绘制大小刻度线
     *
     * @param canvas
     */
    private void drawCalibration(Canvas canvas)
    {
        float dst = ( radius + radius *3f/4f - mGradientRingPaint.getStrokeWidth());
        for (int i = 0; i <= 45; i++)
        {
            canvas.save();
            canvas.rotate(-(-22.5f + 5 * i), radius, radius);
            canvas.drawLine(dst-2, radius, dst + mGradientRingPaint.getStrokeWidth()+2, radius, mBigCalibrationPaint);
            canvas.restore();
        }
    }

    /**
     * 分别绘制外层 中间 内层圆环
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas)
    {

        canvas.save();
        canvas.translate(width / 2, height / 2);
        //绘制渐变圆环
        canvas.rotate(140);
        canvas.drawArc(mOuterArc, -127f, -206, false, mGradientRingPaint);

        //画最外层的圆环
        canvas.drawArc(mMiddleArc, -mStartAngle, -mEndAngle, false, mMiddleRingPaint);
        canvas.restore();
    }


    /**
     * 设置数据
     *
     * @param num
     */
    public void setSesameValues(int num)
    {
        mMaxNum = num;
        mTotalAngle = num * 200f /100f;

        int n = (int) Math.abs(mTotalAngle - mCurrentAngle);
        int arcTime = n < 70 ? 1000 : (int) (n * time / 200f);

        clearAnimation();
        startRotateAnim(arcTime);
    }


    /**
     * 开始指针旋转动画
     */
    public void startRotateAnim(final int t)
    {

        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(mCurrentAngle, mTotalAngle);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(t);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                mCurrentAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mAngleAnim.start();


        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum, mMaxNum);
        mNumAnim.setDuration(t);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                mMinNum = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mNumAnim.start();
    }


    /**
     * dp2px
     *
     * @param values
     * @return
     */
    public int dp2px(int values)
    {

        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }
    public int dp2px2(float values)
    {
        float density = getResources().getDisplayMetrics().density;
        return (int) ((values/3f) * density + 0.5f);
    }

    public void setBottomText(String securityLevel) {
        sesameLevel = securityLevel;
    }
}
