package com.hxxc.huaxing.app.wedgit;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.hxxc.huaxing.app.R;

/**
 * Created by houwen.lai on 2016/12/20.
 * <p>
 * if (hasFocus)scroll_login.smoothScrollTo(0,3*ed_user_phone.getMeasuredHeight());
 * //                handler.post(new Runnable() {
 * //                    @Override
 * //                    public void run() {
 * //                        scroll_login.smoothScrollTo(0,3*ed_user_phone.getMeasuredHeight());
 * ////                        scroll_login.fullScroll(ScrollView.FOCUS_DOWN);
 * //
 * //                    }
 * //                });
 */

public class CustomEdit extends RelativeLayout {

    public Context mContext;

    public View mView;
    public EditText ed_custom;
    public ImageButton imgbtn_close;

    public static final String type_clear = "clear";//账号清空
    public static final String type_display = "display";//密码显示
    public String type = type_clear;//类型

    public CustomEdit(Context context) {
        super(context);
        this.mContext = context;
        inintView();
    }

    public CustomEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        inintView();
    }

    public CustomEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        inintView();
    }

    public void inintView() {
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mView =
        View.inflate(mContext, R.layout.password_custom, this);

        ed_custom = (EditText) findViewById(R.id.ed_custom);
        imgbtn_close = (ImageButton) findViewById(R.id.imgbtn_close);

        //
        this.post(new Runnable() {
            @Override
            public void run() {
                imgbtn_close.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type.equals(type_clear))
                            ed_custom.setText("");
                        else {
                            d(mFlag);
                        }
                    }
                });
            }
        });
    }

    public EditText getEditWedgit() {
        return ed_custom;
    }

    public void setEditTextColor(int color) {
        if (ed_custom != null) ed_custom.setTextColor(color);
    }

    public void setEditHintTextColor(int color) {
        if (ed_custom != null) ed_custom.setHintTextColor(color);
    }

    public void setEditTextSize(float size) {
        if (ed_custom != null) ed_custom.setTextSize(size);
    }

    /**
     * InputType.TYPE_CLASS_PHONE
     *
     * @param type
     */
    public void setEditTextType(int type) {
        if (ed_custom != null) ed_custom.setInputType(type);
    }

    public String getEditText() {
        if (ed_custom != null) return ed_custom.getText().toString().trim();
        else return "";
    }

    public void setEditText(CharSequence text) {
        if (ed_custom != null) {
            ed_custom.setText(text);
            ed_custom.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
        }
    }

    public void setHintEditText(CharSequence text) {
        if (ed_custom != null) ed_custom.setHint(text);
    }

    public void setImgBtnResouse(int resId) {
        if (imgbtn_close != null) imgbtn_close.setImageResource(resId);
    }

    public void setImgBtnBackgrount(int resId) {
        if (imgbtn_close != null) imgbtn_close.setBackgroundResource(resId);
    }

    public void setImgBtnBackgrountColor(int color) {
        if (imgbtn_close != null) imgbtn_close.setBackgroundColor(color);
    }

    public void setImgBtnResouse(int res_d, int res_f) {
        this.resId_d = res_d;
        this.resId_f = res_f;
    }

    /**
     * 对 edittext 操作的方式
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    public int resId_d;
    public int resId_f;
    public boolean mFlag = false;

    public void d(boolean flag) {
        if (flag) {
            imgbtn_close.setImageResource(resId_d);
            mFlag = false;
            ed_custom.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            imgbtn_close.setImageResource(resId_f);
            mFlag = true;
            ed_custom.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        ed_custom.setSelection(ed_custom.getText().toString().trim().length());
    }
}
