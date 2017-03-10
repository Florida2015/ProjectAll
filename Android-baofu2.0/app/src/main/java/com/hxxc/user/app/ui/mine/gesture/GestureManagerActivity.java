package com.hxxc.user.app.ui.mine.gesture;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.Event.AccountSafeEvent;
import com.hxxc.user.app.Event.GestureManagerEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.StringUtil;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.LeftAndRightTextView;
import com.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/12/12.
 */

public class GestureManagerActivity extends ToolbarActivity {
    @BindView(R.id.lr_tv_1)
    LeftAndRightTextView lr_tv_1;
    @BindView(R.id.btn_switch_auto)
    SwitchButton btn_switch_auto;

    @BindView(R.id.ll_toggle)
    LinearLayout ll_toggle;
    private Dialog dialog;
    private EditText et_pwd;
    private UserInfo mUser;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gesture_manager;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("手势密码");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mUser = SPUtils.geTinstance().getUserInfo();

        initView();
        initToggleView();
    }

    private void initToggleView() {
        btn_switch_auto.setEnabled(false);
    }

    private void initView() {
        if (mUser.getIsGesturePwd() == 1) {
            btn_switch_auto.setCheckedImmediately(true);
            lr_tv_1.setVisibility(View.VISIBLE);
        } else {
            btn_switch_auto.setCheckedImmediately(false);
            lr_tv_1.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.lr_tv_1, R.id.ll_toggle})
    public void onClick(View view) {
        if (BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.lr_tv_1:
                    if (HXXCApplication.getInstance().getLockPatternUtils()
                            .isPatternSaved(getApplicationContext())) {
                        showConfimDialog();
                    } else {
                        startCreateGestureActivity();
                    }
                    break;
                case R.id.ll_toggle:
                    ll_toggle.setEnabled(false);
                    if (btn_switch_auto.isChecked()) {//关闭开关
                        startUnlockGestureActivity();
                    } else {//打开开关
                        if (HXXCApplication.getInstance().getLockPatternUtils()
                                .isPatternSaved(getApplicationContext())) {//已经保存了手势密码
                            startUnlockGestureActivity();
                        } else {//首次设置手势密码
                            showConfimDialog();
                        }
                    }
                    break;
            }
        }
    }


    private void showConfimDialog() {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(this, R.layout.dialog_safe_protect, null);
            TextView tv_phone = (TextView) inflate.findViewById(R.id.tv_phone);
            et_pwd = (EditText) inflate.findViewById(R.id.et_pwd);

            inflate.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != dialog) {
                        dialog.dismiss();
                    }
                }
            });

            inflate.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pwd = et_pwd.getText().toString().trim();
                    if (TextUtils.isEmpty(pwd)) {
                        ToastUtil.showSafeToast(GestureManagerActivity.this,
                                "请输入登录密码");
                        return;
                    }
                    if (SPUtils.geTinstance().checkPassword(pwd)) {
                        if (null != dialog) {
                            dialog.dismiss();
                        }
                        startCreateGestureActivity();
                    } else {
                        ToastUtil.showSafeToast(GestureManagerActivity.this,
                                "密码不正确");
                    }
                }
            });

            tv_phone.setText(StringUtil.getRatStr2(SPUtils.geTinstance().getUserName()));
            dialog.setContentView(inflate);
        }
        et_pwd.setText("");
        dialog.show();
    }

    private void startUnlockGestureActivity() {
        Intent pwdIn = new Intent(this, UnlockGesturePasswordActivity.class);
        pwdIn.putExtra("from", UnlockGesturePasswordActivity.From_GestureManager);
        startActivity(pwdIn);
    }

    private void startCreateGestureActivity() {
        Intent pwdIn = new Intent(GestureManagerActivity.this,
                CreateGesturePasswordActivity.class);
        pwdIn.putExtra("from", "gestureManager");
        startActivity(pwdIn);
    }

    @Override
    protected void onDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEventMainThread(GestureManagerEvent event) {
        if (event.from == GestureManagerEvent.From_CreateGesture) {
            reflush(true);
        } else {
            if (btn_switch_auto.isChecked()) {
                mApiManager.closeGesturePwd(new SimpleCallback<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        reflush(false);
                    }

                    @Override
                    public void onError() {
                        ll_toggle.setEnabled(true);
                    }
                });
            } else {
                mApiManager.saveGesturePwd(event.patterns, new SimpleCallback<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        reflush(true);
                    }

                    @Override
                    public void onError() {
                        ll_toggle.setEnabled(true);
                    }
                });
            }
        }
    }

    private void reflush(Boolean aBoolean) {
        Midhandler.getUserInfo(new Midhandler.OnGetUserInfo() {
            @Override
            public void onNext(UserInfo userInfo) {
                mUser = userInfo;
                EventBus.getDefault().post(new AccountSafeEvent());
                btn_switch_auto.setChecked(aBoolean);
                lr_tv_1.setVisibility(aBoolean ? View.VISIBLE : View.INVISIBLE);
                ll_toggle.setEnabled(true);
            }
        });
    }
}
