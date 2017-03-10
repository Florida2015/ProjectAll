package com.hxxc.user.app.ui.mine;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.user.app.Event.AccountSafeEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyClickButton;
import com.hxxc.user.app.widget.SendCodeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/11/1.
 */

public class BindEmailActivity extends ToolbarActivity implements SendCodeView.ICode {
    @BindView(R.id.tv_email)
    EditText tv_email;

    @BindView(R.id.code_edit)
    EditText code_edit;

    @BindView(R.id.send_code_text)
    SendCodeView send_code_text;

    @BindView(R.id.step_btn)
    MyClickButton step_btn;

    public Dialog systemDialog;
    private String mCode;
    private String mEmail;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bind_email;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("绑定邮箱");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        send_code_text.onDestory();
        super.onDestroy();
    }

    private void initViews() {
        step_btn.setOnMyClickListener(new MyClickButton.MyClickListener() {
            @Override
            public void onMyClickListener() {
                final String email = tv_email.getText().toString();
                if ("".equals(email)) {
                    ToastUtil.ToastShort(BindEmailActivity.this, "请输入邮箱地址");
                    return;
                }
                if (!CommonUtil.isEmailNo(email)) {
                    ToastUtil.ToastShort(BindEmailActivity.this, "电子邮件地址不正确");
                    return;
                }

                if (TextUtils.isEmpty(mCode)) {
                    ToastUtil.ToastShort(BindEmailActivity.this, "请获取验证码");
                    return;
                }

                String code = code_edit.getText().toString();
                if ("".equals(code)) {
                    ToastUtil.ToastShort(BindEmailActivity.this, "请输入验证码");
                    return;
                }
                if (!email.equals(mEmail)) {
                    ToastUtil.showSafeToast(BindEmailActivity.this, "请重新获取验证码");
                    return;
                }
                step_btn.begin();

                mApiManager.bindEmail(SPUtils.geTinstance().getUid(), code, email, new SimpleCallback<String>() {
                    @Override
                    public void onNext(String s) {
                        toast("邮箱绑定成功");
                        step_btn.finish();
                        Midhandler.getUserInfo(new Midhandler.OnGetUserInfo() {
                            @Override
                            public void onNext(UserInfo userInfo) {
                                EventBus.getDefault().post(new AccountSafeEvent());
                                finish();
                            }
                        });
                    }

                    @Override
                    public void onError() {
                        step_btn.finish();
                    }
                });

            }
        });

        send_code_text.setNetType(SendCodeView.EMAIL);
        Map<String, String> params = new HashMap<>();
        params.put("type", "14");
        send_code_text.initDatas(this, 1, params, new SendCodeView.MyOnClickListener() {

            @Override
            public void onSuccess(String t) {
                mCode = "t";
                mEmail = tv_email.getText().toString();

                if (null == systemDialog) {
                    systemDialog = new Dialog(BindEmailActivity.this, R.style.loadingDialogTheme);
                    View inflate = View.inflate(BindEmailActivity.this, R.layout.dialog_feedback, null);
                    TextView tv_1 = (TextView) inflate.findViewById(R.id.tv_1);
                    TextView tv_2 = (TextView) inflate.findViewById(R.id.tv_2);
                    tv_1.setVisibility(View.GONE);
                    tv_2.setText("验证码已发送至您的邮箱，请在24小时内完成验证！");

                    MyClickButton sure = (MyClickButton) inflate.findViewById(R.id.sure);
                    sure.setText("我知道了");
                    sure.setOnMyClickListener(new MyClickButton.MyClickListener() {
                        @Override
                        public void onMyClickListener() {
                            systemDialog.dismiss();
                        }
                    });
                    systemDialog.setContentView(inflate);
                }
                systemDialog.show();
            }

            @Override
            public void onFailure(String t) {
            }

            @Override
            public boolean onPre() {
                return false;
            }
        });
    }

    @Override
    public String getMobile() {
        return tv_email.getText().toString();
    }
}
