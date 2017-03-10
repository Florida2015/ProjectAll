package com.hxxc.user.app.ui.mine.setting;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;

import com.hxxc.user.app.ActivityList;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyClickButton;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by chenqun on 2016/6/27.
 */
public class FeedbackActivity extends ToolbarActivity {
    @BindView(R.id.context_edit)
    EditText context_edit;
    @BindView(R.id.contact_edit)
    EditText contact_edit;
    @BindView(R.id.submit_btn)
    MyClickButton submit_btn;
    private Dialog systemDialog;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_mine_feedback;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("意见反馈");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        InputFilter[] filter = new InputFilter[1];
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
        context_edit.setFilters(filter);
        contact_edit.setFilters(filter);
        submit_btn.setOnMyClickListener(new MyClickButton.MyClickListener() {

            @Override
            public void onMyClickListener() {
                submit();
            }
        });
    }

    private void submit() {
        String context = context_edit.getText().toString();
        String contact = contact_edit.getText().toString();
        if ("".equals(context)) {
            ToastUtil.ToastShort(this, "请写下您的宝贵意见和建议");
            return;
        }
        if ("".equals(contact)) {
            ToastUtil.ToastShort(this, "请留下您的联系方式");
            return;
        }
        submit_btn.begin();
        Map<String, String> params = new HashMap<>();
        params.put("context", context);
        params.put("contact", contact);
        params.put("type", "2");
        params.put("reqfrom", Constants.TypeChannel);
        params.put("uid", SPUtils.geTinstance().getUid());
        Subscription s = mApiManager
                .feedback(params, new SimpleCallback<String>() {
                    @Override
                    public void onNext(String s) {
                        showFeedbackDialog();
                        submit_btn.finish();
                    }

                    @Override
                    public void onError() {
                        submit_btn.finish();
                    }
                });
        addSubscription(s);
    }

    private void showFeedbackDialog() {
        if(null == systemDialog){
            systemDialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(this, R.layout.dialog_feedback, null);
            MyClickButton sure = (MyClickButton) inflate.findViewById(R.id.sure);
            sure.setOnMyClickListener(new MyClickButton.MyClickListener() {
                @Override
                public void onMyClickListener() {
                    systemDialog.dismiss();
                    finish();
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
    }
}
