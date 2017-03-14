package com.hxxc.huaxing.app.wedgit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.utils.DisplayUtil;
import com.hxxc.huaxing.app.utils.LogUtil;

/**
 * Created by chenqun on 2016/9/27.
 */

public class LeftAndRightTextView extends FrameLayout {
    private int left_Color = 0xff333333;
    private int right_Color = 0xffaaaaaa;
    private TextView mRight;
    private TextView mLeft;

    public LeftAndRightTextView(Context context) {
        this(context, null);
    }

    public LeftAndRightTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeftAndRightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = View.inflate(context, R.layout.item_left_right_textview, this);
        LinearLayout content = (LinearLayout) view.findViewById(R.id.ll_content);
        mLeft = (TextView) view.findViewById(R.id.tv_left);
        mRight = (TextView) view.findViewById(R.id.tv_right);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LeftRightTextView);
        String left = a.getString(R.styleable.LeftRightTextView_left);
        String right = a.getString(R.styleable.LeftRightTextView_right);
        String right_hint = a.getString(R.styleable.LeftRightTextView_right_hint);
        boolean hasArr = a.getBoolean(R.styleable.LeftRightTextView_arr, false);
        int i = a.getInt(R.styleable.LeftRightTextView_background_color, 0);
        Drawable drawable_left = a.getDrawable(R.styleable.LeftRightTextView_left_drawable);

        left_Color = a.getColor(R.styleable.LeftRightTextView_left_color, left_Color);
        right_Color = a.getColor(R.styleable.LeftRightTextView_right_color, right_Color);
        a.recycle();

        LogUtil.e("left==" + left + "****************right==" + right + "*************background==" + i);
        switch (i) {
            case 0:
                content.setBackgroundColor(Color.parseColor("#ffffffff"));
                break;
            case 1:
                content.setBackgroundColor(Color.parseColor("#fffafafa"));
                break;
        }

        mLeft.setText(left);
        mLeft.setTextColor(left_Color);
        mRight.setText(right);
        if (!TextUtils.isEmpty(right_hint)) mRight.setHint(right_hint);
        mRight.setTextColor(right_Color);
        if (hasArr) {
            Drawable drawable = context.getResources().getDrawable(R.mipmap.aliwx_arrow);
            mRight.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            mRight.setCompoundDrawablePadding(DisplayUtil.dip2px(context, 15));
        }

        if (null != drawable_left) {
            mLeft.setCompoundDrawablesWithIntrinsicBounds(drawable_left, null, null, null);
            mLeft.setCompoundDrawablePadding(DisplayUtil.dip2px(context, 15));
        }
    }

    public void setRightText(String text) {
        mRight.setText(text);
    }

    public String getRightText() {
        return mRight.getText().toString();
    }

    public void setLeftDrawable(Drawable drawable) {
        mLeft.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public void setRightTextSingleLine(boolean b) {
        mRight.setSingleLine(b);
    }

    public void hasArr(boolean b) {
        setEnabled(b);
        if (b) {
            Drawable drawable = getContext().getResources().getDrawable(R.mipmap.aliwx_arrow);
            mRight.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            mRight.setCompoundDrawablePadding(DisplayUtil.dip2px(getContext(), 15));
        } else
            mRight.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }
}
