package com.hxxc.user.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.hxxc.user.app.R;
import com.hxxc.user.app.utils.DisplayUtil;
import com.hxxc.user.app.utils.LogUtils;

/**
 * Created by bruce on 11/6/14.
 */
public class ArcProgressIndex extends View {
    private final Context mContext;
    private int CENTER_X;//中心点x
    private int CENTER_Y;//中心点x

    private ViewAnim mViewAnim = new ViewAnim();
    private float mAnimDuration = 2600;

    private Paint paint;
    protected Paint textPaint;

    private RectF rectF = new RectF();

    private float strokeWidth;
    private float suffixTextSize;
    private float bottomTextSize;
    private String bottomText;
    private float textSize;
    private int textColor;
    private float progress = 0;
    private int max;
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private float arcAngle;
    private String suffixText = "%";
    private float suffixTextPadding;

    private float arcBottomHeight;

    private final int default_finished_color = Color.WHITE;
    private final int default_unfinished_color = Color.rgb(72, 106, 176);
    private final int default_text_color = Color.rgb(66, 145, 241);
    private final float default_suffix_text_size;
    private final float default_suffix_padding;
    private final float default_bottom_text_size;
    private final float default_stroke_width;
    private final String default_suffix_text;
    private final int default_max = 100;
    private final float default_arc_angle = 360 * 0.8f;
    private float default_text_size;
    private final int min_size;

    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_STROKE_WIDTH = "stroke_width";
    private static final String INSTANCE_SUFFIX_TEXT_SIZE = "suffix_text_size";
    private static final String INSTANCE_SUFFIX_TEXT_PADDING = "suffix_text_padding";
    private static final String INSTANCE_BOTTOM_TEXT_SIZE = "bottom_text_size";
    private static final String INSTANCE_BOTTOM_TEXT = "bottom_text";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private static final String INSTANCE_ARC_ANGLE = "arc_angle";
    private static final String INSTANCE_SUFFIX = "suffix";
    private float extraProgress = 0;
    private float oriProgress = 0;

    public ArcProgressIndex(Context context) {
        this(context, null);
    }

    public ArcProgressIndex(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgressIndex(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        default_text_size = DisplayUtil.sp2px(context, 18);
        min_size = (int) DisplayUtil.dp2px(context, 100);
        default_text_size = DisplayUtil.sp2px(context, 18);
        default_suffix_text_size = DisplayUtil.sp2px(context, 15);
        default_suffix_padding = DisplayUtil.dp2px(context, 4);
        default_suffix_text = "%";
        default_bottom_text_size = DisplayUtil.sp2px(context, 10);
        default_stroke_width = DisplayUtil.dp2px(context, 4);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initByAttributes(TypedArray attributes) {
        finishedStrokeColor = attributes.getColor(R.styleable.ArcProgress_arc_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.ArcProgress_arc_unfinished_color, default_unfinished_color);
        textColor = attributes.getColor(R.styleable.ArcProgress_arc_text_color, default_text_color);
        textSize = attributes.getDimension(R.styleable.ArcProgress_arc_text_size, default_text_size);
        arcAngle = attributes.getFloat(R.styleable.ArcProgress_arc_angle, default_arc_angle);
        setMax(attributes.getInt(R.styleable.ArcProgress_arc_max, default_max));
        strokeWidth = attributes.getDimension(R.styleable.ArcProgress_arc_stroke_width, default_stroke_width);
        suffixTextSize = attributes.getDimension(R.styleable.ArcProgress_arc_suffix_text_size, default_suffix_text_size);
        suffixText = TextUtils.isEmpty(attributes.getString(R.styleable.ArcProgress_arc_suffix_text)) ? default_suffix_text : attributes.getString(R.styleable.ArcProgress_arc_suffix_text);
        suffixTextPadding = attributes.getDimension(R.styleable.ArcProgress_arc_suffix_text_padding, default_suffix_padding);
        bottomTextSize = attributes.getDimension(R.styleable.ArcProgress_arc_bottom_text_size, default_bottom_text_size);
        bottomText = attributes.getString(R.styleable.ArcProgress_arc_bottom_text);
    }

    protected void initPainters() {
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(default_unfinished_color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }


    public float getSuffixTextSize() {
        return suffixTextSize;
    }

    public String getBottomText() {
        return bottomText;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {

        Log.e("setProgress", "progress=="+progress);
        if (progress > getMax()) {
            progress = getMax();
        }
        if (progress < 0) {
            progress = 0;
        }
        this.oriProgress = this.progress;
        extraProgress = progress - this.progress;

        float newDuration = Math.abs(extraProgress * mAnimDuration / (float) getMax());
        if (null != mViewAnim ) clearAnimation();
        assert mViewAnim != null;
        mViewAnim.setDuration((long) newDuration);
        startAnimation(mViewAnim);
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public float getBottomTextSize() {
        return bottomTextSize;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.invalidate();
    }

    public int getFinishedStrokeColor() {
        return finishedStrokeColor;
    }


    public int getUnfinishedStrokeColor() {
        return unfinishedStrokeColor;
    }


    public float getArcAngle() {
        return arcAngle;
    }


    public String getSuffixText() {
        return suffixText;
    }


    public float getSuffixTextPadding() {
        return suffixTextPadding;
    }


    @Override
    protected int getSuggestedMinimumHeight() {
        return min_size;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return min_size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        CENTER_X = CENTER_Y = width / 2;
        float suffixHeight = Math.abs(textPaint.descent() + textPaint.ascent()) * 2;
        rectF.set(strokeWidth / 2f + suffixHeight, strokeWidth / 2f + suffixHeight, width - strokeWidth / 2f - suffixHeight, MeasureSpec.getSize(heightMeasureSpec) - strokeWidth / 2f - suffixHeight);
        float radius = width / 2f;
        float angle = (360 - arcAngle) / 2f;
        arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = 270 - arcAngle / 2f;

        float finishedSweepAngle = progress == 0? 0 : progress * arcAngle / (float) getMax();
        LogUtils.e("onDraw" + "///progress==>" + progress + "///arcAngle==>" + arcAngle + "///startAngle==>" + startAngle + "///angle==>" + finishedSweepAngle);
        paint.setColor(unfinishedStrokeColor);
        canvas.drawArc(rectF, startAngle, arcAngle, false, paint);
        if(0 != finishedSweepAngle){
            paint.setColor(finishedStrokeColor);
            canvas.drawArc(rectF, startAngle, finishedSweepAngle, false, paint);
        }

        String text = String.valueOf(getProgress());
        float textHeight = 0;
//        if (!TextUtils.isEmpty(text)) {
//            textPaint.setColor(textColor);
//            textPaint.setTextSize(DisplayUtil.dip2px(mContext, 12));
//            textHeight = textPaint.descent() + textPaint.ascent();
//
//            //绘制百分比数字
//            float angle = (360 - arcAngle) / 2;
//            canvas.save();
//            canvas.rotate(-(180 - angle) + progress * arcAngle / 100, CENTER_X, CENTER_X);
//            canvas.drawText(text + "%", (getWidth() - textPaint.measureText(text)) / 2.0f, Math.abs(textHeight) * 1.5f, textPaint);
//            canvas.restore();
//        }


        textPaint.setColor(getFinishedStrokeColor());
        textPaint.setTextSize(DisplayUtil.dip2px(mContext, 17));
        canvas.drawText(getText1(), (getWidth() - textPaint.measureText(getText1())) / 2.0f, strokeWidth * 5.5f, textPaint);

        textPaint.setColor(mContext.getResources().getColor(R.color.gry));
        textPaint.setTextSize(DisplayUtil.dip2px(mContext, 14));
        canvas.drawText(getText2(), (getWidth() - textPaint.measureText(getText2())) / 2.0f, strokeWidth * 7.5f, textPaint);

        textPaint.setColor(getFinishedStrokeColor());
        textPaint.setTextSize(DisplayUtil.dip2px(mContext, 55));
        textHeight = textPaint.descent() + textPaint.ascent();
        canvas.drawText(getText3(), (getWidth() - textPaint.measureText(getText3())) / 2.0f, (getHeight() - textHeight) / 2 + DisplayUtil.dip2px(mContext, 5), textPaint);

        textPaint.setTextSize(DisplayUtil.dip2px(mContext, 12));
        textPaint.setColor(mContext.getResources().getColor(R.color.gry));
        canvas.drawText(getText4(), (getWidth() - textPaint.measureText(getText4())) / 2.0f, getHeight() - strokeWidth * 6.0f, textPaint);

    }

    private String text1 = "新手专享";
    private String text2 = "预期年化收益";
    private String text3 = "0%";
    private String text4 = "剩余金额:";
    private String textNum = "0";

    private String getText1() {
        return text1;
    }

    private String getText2() {
        return text2;
    }

    private String getText3() {
        return text3;
    }

    private String getText4() {
        return text4 + textNum;
    }

    public void setText1(String text) {
        this.text1 = text;
    }

    public void setText2(String text) {
        this.text2 = text;
    }

    public void setText3(String text) {
        this.text3 = text;
    }

    public void setText4(String text) {
        this.textNum = text;
    }


    private class ViewAnim extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float v = extraProgress * interpolatedTime;
            progress = oriProgress + v;
            invalidate();
        }
    }


//    @Override
//    protected Parcelable onSaveInstanceState() {
//        final Bundle bundle = new Bundle();
//        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
//        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());
//        bundle.putFloat(INSTANCE_SUFFIX_TEXT_SIZE, getSuffixTextSize());
//        bundle.putFloat(INSTANCE_SUFFIX_TEXT_PADDING, getSuffixTextPadding());
//        bundle.putFloat(INSTANCE_BOTTOM_TEXT_SIZE, getBottomTextSize());
//        bundle.putString(INSTANCE_BOTTOM_TEXT, getBottomText());
//        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
//        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
//        bundle.putFloat(INSTANCE_PROGRESS, getProgress());
//        bundle.putInt(INSTANCE_MAX, getMax());
//        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor());
//        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor());
//        bundle.putFloat(INSTANCE_ARC_ANGLE, getArcAngle());
//        bundle.putString(INSTANCE_SUFFIX, getSuffixText());
//        return bundle;
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        if (state instanceof Bundle) {
//            final Bundle bundle = (Bundle) state;
//            strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);
//            suffixTextSize = bundle.getFloat(INSTANCE_SUFFIX_TEXT_SIZE);
//            suffixTextPadding = bundle.getFloat(INSTANCE_SUFFIX_TEXT_PADDING);
//            bottomTextSize = bundle.getFloat(INSTANCE_BOTTOM_TEXT_SIZE);
//            bottomText = bundle.getString(INSTANCE_BOTTOM_TEXT);
//            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
//            textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
//            setMax(bundle.getInt(INSTANCE_MAX));
//            setProgress(bundle.getFloat(INSTANCE_PROGRESS));
//            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
//            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR);
//            suffixText = bundle.getString(INSTANCE_SUFFIX);
//            initPainters();
//            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
//            return;
//        }
//        super.onRestoreInstanceState(state);
//    }
}
