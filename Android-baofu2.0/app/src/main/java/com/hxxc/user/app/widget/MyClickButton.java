package com.hxxc.user.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.DisplayUtil;

import java.util.Date;

public class MyClickButton extends FrameLayout {

    private TextView button;
    private ProgressBar progressbar;
    private final int WHITE = -1;
    private final int BLUE = 0;
    private final int YELLOW = 1;// 中间
    private final int RED = 2;
    private final int BLUE_1 = 3;
    private final int BLUE_2 = 6;
    private final int YELLOW_1 = 4;
    private final int BLUE_INDEX = 5;
    private final int BLUE_3 = 7;
    private String text;
    private MyClickListener mListener;

    public MyClickButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyClickButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyClickButton(Context context) {
        super(context);
        init(context);
    }

    private int dimension = 14;

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.item_myclickbutton, this);
        button = (TextView) findViewById(R.id.button);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyClickButton);
        int textColor;
        String color = ta.getString(R.styleable.MyClickButton_textColor);
        if (!TextUtils.isEmpty(color)) {
            textColor = Color.parseColor(color);
        } else {
            textColor = ta.getColor(R.styleable.MyClickButton_textColor, Color.parseColor("#ffffffff"));
        }
        text = ta.getString(R.styleable.MyClickButton_text);
        int itbackground = ta.getInt(R.styleable.MyClickButton_itbackground, 0);
        if (ta.hasValue(R.styleable.MyClickButton_textSize)) {
            dimension = (int) ta.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, dimension);
            dimension = DisplayUtil.px2sp(context, dimension);
        }
        ta.recycle();

        button.setTextColor(textColor);
        button.setText(text);
        button.setTextSize(dimension);
        switch (itbackground) {
            case WHITE:
                button.setBackgroundColor(Color.WHITE);
                break;
            case BLUE:
                button.setBackgroundResource(R.drawable.btn_background_left);
                break;
            case YELLOW:
                button.setBackgroundResource(R.drawable.btn_background_right);
                break;
            case RED:
                button.setBackgroundResource(R.drawable.btn_background_exitlogin);
                break;
            case BLUE_1:
                button.setBackgroundResource(R.drawable.btn_background_left);
                break;
            case BLUE_2:
                button.setBackgroundResource(R.drawable.bth_background_down);
                break;
            case YELLOW_1:
                button.setBackgroundResource(R.drawable.btn_background_right);
                break;
            case BLUE_INDEX:
                button.setBackgroundResource(R.drawable.icon_buy);
                break;
            case BLUE_3:
                button.setBackgroundColor(Color.parseColor("#ff1f80d1"));
                break;
        }
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (BtnUtils.isFastDoubleClick()) {
                    if (null != mListener) {
                        mListener.onMyClickListener();
                    }
                }
            }
        });
    }

    public static final int type_yicanjia = 0;
    public static final int type_yishouwan = 2;
    public static final int type_lijigoumai = 3;
    public static final int type_over = 4;

    public void setIndexBuyType(int buyType) {
        switch (buyType) {
            case type_yicanjia:
                button.setText("您已参加该活动");
                button.setEnabled(false);
                button.setBackgroundResource(R.drawable.icon_over);
                break;
            case type_yishouwan:
                button.setText("已满额");
                button.setEnabled(false);
                button.setBackgroundResource(R.drawable.icon_over);
                break;
            case type_over:
                button.setText("已满额");
                button.setEnabled(false);
                button.setBackgroundResource(R.drawable.icon_over);
                break;
            case type_lijigoumai:
                button.setText("立即购买");
                button.setEnabled(true);
                button.setBackgroundResource(R.drawable.icon_buy);
                break;
        }
    }

    public void setIndexBuyType10dianKaiQiang(Date date) {
        String stringDate = CommonUtil.getStringDate(date);
        String substring;
        if (stringDate.charAt(0) == '0') {
            substring = stringDate.substring(1, 2);
        } else {
            substring = stringDate.substring(0, 2);
        }
        button.setText(substring + "点开抢");
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.icon_over);
    }

    public void begin() {
        button.setText("");
        button.setEnabled(false);
        this.setEnabled(false);
        progressbar.setVisibility(View.VISIBLE);
    }

    public void finish() {
        button.setText(text);
        button.setEnabled(true);
        this.setEnabled(true);
        progressbar.setVisibility(View.INVISIBLE);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.item_myclickbutton, this);
        button = (TextView) findViewById(R.id.button);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
    }

    public void setOnMyClickListener(MyClickListener listener) {
        this.mListener = listener;
    }

    public interface MyClickListener {
        void onMyClickListener();
    }

    public void setText(String str) {
        button.setText(str);
    }
}
