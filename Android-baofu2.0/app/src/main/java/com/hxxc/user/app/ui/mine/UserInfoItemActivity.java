package com.hxxc.user.app.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyClickButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenqun on 2016/10/27.
 */

public class UserInfoItemActivity extends ToolbarActivity {
    public static final int Result_code = 22;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.do_step)
    MyClickButton do_step;
    private String content;
    private String from;
    private String type;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.dialog2;
    }

    @Override
    protected void setTitle() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        from = intent.getStringExtra("from");
        type = intent.getStringExtra("type");
        initView();
    }

    private void initView() {
        mTitle.setText(from);
        et_content.setText(content);
        et_content.requestFocus();
        int index = et_content.getText().length();
        et_content.setSelection(index);
        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter() {

            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };

        if (!TextUtils.isEmpty(from) && (from.contains("地址") || from.contains("职业"))) {
            et_content.setHint("请输入" + from + "(限50个字符)");
            filter[1] = new InputFilter.LengthFilter(50);
        } else {
            et_content.setHint("请输入" + from + "(限20个字符)");
            filter[1] = new InputFilter.LengthFilter(20);
        }
        et_content.setFilters(filter);
        et_content.postDelayed(new Runnable() {

            @Override
            public void run() {
                CommonUtil.showKeyBoard(UserInfoItemActivity.this);
            }
        }, 200);

        do_step.setOnMyClickListener(new MyClickButton.MyClickListener() {
            @Override
            public void onMyClickListener() {
                CommonUtil.hideKeyBoard(UserInfoItemActivity.this);
                String string = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(string)) {
                    ToastUtil.ToastShort(UserInfoItemActivity.this, "请输入" + from);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("content", string);
                intent.putExtra("from", from);
                intent.putExtra("type", type);
                setResult(Result_code, intent);
                UserInfoItemActivity.this.finish();
            }
        });
    }

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
