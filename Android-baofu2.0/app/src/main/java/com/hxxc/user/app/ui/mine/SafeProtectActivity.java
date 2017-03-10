package com.hxxc.user.app.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.user.app.Event.AccountSafeEvent;
import com.hxxc.user.app.Event.LoginEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.mine.gesture.GestureManagerActivity;
import com.hxxc.user.app.ui.mine.setting.bindphone.BindPhoneActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.ui.user.SetingPasswordActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.StringUtil;
import com.hxxc.user.app.widget.ArcProgress;
import com.hxxc.user.app.widget.LeftAndRightTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by houwen.lai on 2016/10/28.
 * 安全保障页
 */

public class SafeProtectActivity extends ToolbarActivity {

    @BindView(R.id.arcprogress)
    ArcProgress arcprogress;

    @BindView(R.id.tv_des)
    TextView tv_des;

    @BindView(R.id.tv_1)
    LeftAndRightTextView tv_1;
    @BindView(R.id.tv_2)
    LeftAndRightTextView tv_2;
    @BindView(R.id.tv_3)
    LeftAndRightTextView tv_3;
    @BindView(R.id.tv_4)
    LeftAndRightTextView tv_4;
    @BindView(R.id.tv_5)
    LeftAndRightTextView tv_5;
    //    private Dialog dialog;
    private EditText et_pwd;
    private UserInfo user;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_safe_protect;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("账户安全");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        user = SPUtils.geTinstance().getUserInfo();
    }

    private void initView() {
        if (null != user) {
            initRealName();
            initPhone();
            initTradePwd();
            initGesturePwd();
            initPwd();
            initImageView();
        }
    }

    @OnClick({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5})
    public void onClick(View view) {
        if (BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_1://实名认证
                    if (user.getRnaStatus() != 1) {
                        Intent intent = new Intent(this, AuthenticationActivity.class);
                        intent.putExtra("from", AuthenticationActivity.FROM_AccountSafe);
                        startActivity(intent);
                    }
                    break;
                case R.id.tv_2://绑定手机
                    startActivity(new Intent(this, BindPhoneActivity.class));
                    break;
                case R.id.tv_3://绑定邮箱
                    startActivity(new Intent(this, BindEmailActivity.class));
                    break;
                case R.id.tv_4://手势密码
                    startActivity(new Intent(this, GestureManagerActivity.class));
//                    if (HXXCApplication.getInstance().getLockPatternUtils()
//                            .isPatternSaved(getApplicationContext())) {
//                        showConfimDialog();
//                    } else {
//                        Intent pwdIn = new Intent(this, CreateGesturePasswordActivity.class);
//                        startActivity(pwdIn);
//                    }
                    break;
                case R.id.tv_5://修改密码
                    Intent intent = new Intent(this, SetingPasswordActivity.class);
                    intent.putExtra("type", SetingPasswordActivity.TYPE_UPDATE_PASSWORD);
                    startActivity(intent);
                    break;
            }
        }
    }
//
//    private void showConfimDialog() {
//        if (dialog == null) {
//            dialog = new Dialog(this, R.style.loadingDialogTheme);
//            View inflate = View.inflate(this, R.layout.dialog_safe_protect, null);
//            TextView tv_phone = (TextView) inflate.findViewById(R.id.tv_phone);
//            et_pwd = (EditText) inflate.findViewById(R.id.et_pwd);
//
//            inflate.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != dialog) {
//                        dialog.dismiss();
//                    }
//                }
//            });
//
//            inflate.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String pwd = et_pwd.getText().toString().trim();
//                    if (TextUtils.isEmpty(pwd)) {
//                        ToastUtil.showSafeToast(SafeProtectActivity.this,
//                                "请输入登录密码");
//                        return;
//                    }
//                    if (SPUtils.geTinstance().checkPassword(pwd)) {
//                        if (null != dialog) {
//                            dialog.dismiss();
//                        }
//                        Intent pwdIn = new Intent(SafeProtectActivity.this,
//                                CreateGesturePasswordActivity.class);
//                        // 打开新的Activity
//                        startActivity(pwdIn);
//                    } else {
//                        ToastUtil.showSafeToast(SafeProtectActivity.this,
//                                "密码不正确");
//                    }
//                }
//            });
//
//            tv_phone.setText(StringUtil.getRatStr2(SPUtils.geTinstance().getUserName()));
//            dialog.setContentView(inflate);
//        }
//        dialog.show();
//    }


    // 判斷是否实名认证
    private void initRealName() {
        if (user.getRnaStatus() == 1) {
            tv_1.setRightText(user.getMaskUserName() + "（" + user.getMaskIdentityCard() + "）");
            tv_1.setClickable(false);
            tv_1.setLeftDrawable(getResources().getDrawable(R.drawable.ico_real_name_s));
        } else {
            tv_1.setRightText("未认证");
            tv_1.setClickable(true);
            tv_1.setLeftDrawable(getResources().getDrawable(R.drawable.ico_real_name_n));
        }
    }

    // 手机认证
    private void initPhone() {
        if (!TextUtils.isEmpty(user.getMobile())) {
            tv_2.setRightText(StringUtil.getRatStr2(user.getMobile()));
            tv_2.setClickable(true);
            tv_2.setLeftDrawable(getResources().getDrawable(R.drawable.phone_s));
        } else {
            tv_2.setRightText("未认证");
            tv_2.setClickable(true);
            tv_2.setLeftDrawable(getResources().getDrawable(R.drawable.phone_n));
        }
    }

    // 交易密码，邮箱验证
    private void initTradePwd() {

        if (!TextUtils.isEmpty(user.getEmail())) {
            tv_3.setRightText(user.getMaskEmail());
            tv_3.setLeftDrawable(getResources().getDrawable(R.drawable.email_s));
        } else {
            tv_3.setRightText("未验证");
            tv_3.setLeftDrawable(getResources().getDrawable(R.drawable.email_n));
        }
    }

    // 手势密码
    private void initGesturePwd() {
        if (HXXCApplication.getInstance().getLockPatternUtils()
                .isPatternSaved(this) && user.getIsGesturePwd() == 1) {
            tv_4.setRightText("已开启");
            tv_4.setLeftDrawable(getResources().getDrawable(R.drawable.gestures_s));
        } else {
            if (HXXCApplication.getInstance().getLockPatternUtils().isPatternSaved(this)) {
                tv_4.setRightText("已关闭");
            } else {
                tv_4.setRightText("未设置");
            }
            tv_4.setLeftDrawable(getResources().getDrawable(R.drawable.gestures_n));
        }
    }

    // 密码修改
    private void initPwd() {
//        mProgress += num;
        tv_5.setRightText("修改");
        tv_5.setLeftDrawable(getResources().getDrawable(R.drawable.lock_s));
    }

    // 初始化头部图片
    private void initImageView() {
        arcprogress.postDelayed(new Runnable() {
            @Override
            public void run() {
                arcprogress.setBottomText(user.getSecurityLevel());
                arcprogress.setSesameValues(user.getSafetyRatio());//(int) (Math.random()*100)
            }
        }, 500);
    }

    public void onEventMainThread(AccountSafeEvent event) {
        user = SPUtils.geTinstance().getUserInfo();
    }

    public void onEventMainThread(LoginEvent event) {
        if (event.type == LoginEvent.USERINFO_TYPE) {
            user = SPUtils.geTinstance().getUserInfo();
            initView();
        }
    }
}
