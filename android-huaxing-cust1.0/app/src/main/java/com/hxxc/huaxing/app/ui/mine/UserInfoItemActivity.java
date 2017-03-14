package com.hxxc.huaxing.app.ui.mine;

import android.content.Intent;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.CommonUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by chenqun on 2016/6/27.
 */
public class UserInfoItemActivity extends BaseActivity {
    public static final int Result_code = 22;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.back)
    ImageButton back;

    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.do_step)
    FancyButton do_step;
    private String content;
    private String from;
    private String type;

    @Override
    public int getLayoutId() {
        return R.layout.dialog2;
    }
    @Override
    public void initView() {
        back.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        from = intent.getStringExtra("from");
        type = intent.getStringExtra("type");
        toolbar_title.setText(from);
        et_content.setText(content);

        et_content.requestFocus();
        int index = et_content.getText().length();
        et_content.setSelection(index);
        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter() {

            Pattern emoji = Pattern. compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]" ,Pattern . UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji . matcher ( source ) ;
                if ( emojiMatcher . find ( ) ) {
                    return "" ;
                }
                return null ;
            }
        };

        if (!TextUtils.isEmpty(from) && from.contains("地址")) {
            et_content.setHint("请输入"+ from +"(限50个字符)");
            filter[1] = new InputFilter.LengthFilter(50);
        }else {
            et_content.setHint("请输入"+ from +"(限20个字符)");
            filter[1] = new InputFilter.LengthFilter(20);
        }
        et_content.setFilters(filter);
        et_content.postDelayed(new Runnable() {

            @Override
            public void run() {
                CommonUtil.showKeyBoard(UserInfoItemActivity.this);
            }
        }, 200);
    }

    @Override
    public void initPresenter() {
    }

    @OnClick({R.id.back,R.id.do_step})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.do_step:
                CommonUtil.hideKeyBoard(this);
                String string = et_content.getText().toString().trim();
                if (TextUtils.isEmpty(string)) {
                    toast("请输入"+from);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("content", string);
                intent.putExtra("from",from);
                intent.putExtra("type",type);
                setResult(Result_code, intent);
                UserInfoItemActivity.this.finish();
                break;
        }
    }
}
