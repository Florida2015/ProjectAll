package com.framwork.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.huaxia.finance.R;

/**
 * Created by Administrator on 2016/4/8.
 */
public class DotRoundView extends View {

    private Paint p;
    private int width;
    private int height;
    private int dash;

    public DotRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DotRoundView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStyle(Paint.Style.FILL);
        p.setColor(context.getResources().getColor(R.color.orange_c772));
        p.setAntiAlias(true);
        final float scale = context.getResources().getDisplayMetrics().density;
        dash=(int) (7 * scale + 0.5f);//
//        dash=(int) (3 * scale + 0.5f);//
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int circleRadius      = 6;
        if(width>10){
            for(int i=circleRadius;i<width;i+=dash){
//                canvas.drawLine(i, 0, i+=dash, 0, p);
                canvas.drawCircle(i, circleRadius, circleRadius, p);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
//        p.setStrokeWidth(height);
        this.postInvalidate();
    }

}
