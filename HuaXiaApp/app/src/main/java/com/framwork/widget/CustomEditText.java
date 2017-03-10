package com.framwork.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by  on 2016/3/2.
 */
public class CustomEditText extends EditText {

    private Paint mPaint;
    private int color;
    public static final int STATUS_FOCUSED = 1;
    public static final int STATUS_UNFOCUSED = 2;
    public static final int STATUS_ERROR = 3;
    private int status = 2;
    private Drawable del_btn;
    private Drawable del_btn_down;
    Drawable left = null;
    private Context mContext;
    /**
     * 是否获取焦点，默认没有焦点
     */
    private boolean hasFocus = false;
    /**
     * 手指抬起时的X坐标
     */
    private int xUp = 0;

    public CustomEditText(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStryle) {
        super(context, attrs, defStryle);
        mContext = context;
        init();
    }

    /**
     * 2014/7/31
     *
     * @author Aimee.ZHANG
     */
    private void init() {
        mPaint = new Paint();
         mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(0.05f);
        color = Color.parseColor("#E5E5E5");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(color);
        canvas.drawLine(0, this.getHeight() - 0.5f, this.getWidth()-1,
                this.getHeight() - 0.5f, mPaint);
    }


}
