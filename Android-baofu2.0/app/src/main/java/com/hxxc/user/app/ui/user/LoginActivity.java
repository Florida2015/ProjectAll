package com.hxxc.user.app.ui.user;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.Event.AdsEvent;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.Event.MineEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.contract.i.ILogin;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.MainActivity2;
import com.hxxc.user.app.ui.discovery.search.ClearEditText;
import com.hxxc.user.app.ui.mine.gesture.CreateGesturePasswordActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.NumberUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.LoadingView;
import com.hxxc.user.app.widget.MyPasswordView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.hxxc.user.app.R.id.ed_user_phone;

public class LoginActivity extends AppCompatActivity implements ILogin {

    public static final int NoRestartMain = 0x00000010;

    @BindView(ed_user_phone)
    ClearEditText userNameView;
    @BindView(R.id.ed_user_pass)
    MyPasswordView passwordView;
    @BindView(R.id.btn_longin)
    FancyButton loginButton;
    @BindView(R.id.tv_forget_pass)
    TextView find_password_text;

    @BindView(R.id.btn_register)
    FancyButton btn_register;

    private int mStartType;
    private Dialog dialog2;
    private String mUserName;
    private String mPass;
    private int from;
    private String to;
    private String todo;

    public static LoginActivity loginActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        loginActivity = this;
//        EventBus.getDefault().register(this);
        mStartType = getIntent().getIntExtra("startType", 0);//是否需要重新打开MainActivity

        from = getIntent().getIntExtra("from", -1);//来自于哪个activity
        if (from == AuthenticationActivity.FROM_Action38)
            mStartType = NoRestartMain;

        to = getIntent().getStringExtra("to");//要去哪个activity
        todo = getIntent().getStringExtra("todo");
        initView();
    }

    @Override
    protected void onRestart() {
        passwordView.password_edit.setText("");
        userNameView.setFocusableInTouchMode(true);
        userNameView.setFocusable(true);
        userNameView.requestFocus();
        initView();
        userNameView.setSelection(userNameView.getText().toString().length());
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        if (null != dialog2) {
            if (dialog2.isShowing()) {
                dialog2.dismiss();
            }
        }
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initView();
    }

    private void initView() {
        if (TextUtils.isEmpty(userNameView.getText())) {
            String userName = SPUtils.geTinstance().getUserName();
            userNameView.setText(NumberUtils.Phone2xing(userName));
            userNameView.setSelection(userName.length());
        }
    }

    @OnClick({R.id.img_btn_back, R.id.tv_forget_pass, R.id.btn_longin, R.id.btn_register})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_btn_back:
                onBackPressed();
                break;
            case R.id.btn_longin:
                login();
                break;
            case R.id.tv_forget_pass://忘记密码
                intent = new Intent(this, ForgetpasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register://注册
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

//                Intent e = new Intent(this,BindingFinancialActivity.class);
//                startActivity(e);
                break;

        }
    }

    private void login() {
        if (TextUtils.isEmpty(userNameView.getText().toString().trim())) {
            ToastUtil.showSafeToast(LoginActivity.this, "请输入用户名");
            return;
        }
        String userNames = SPUtils.geTinstance().getUserName();
        if (userNameView.getText().toString().trim().equals(NumberUtils.Phone2xing(userNames))) {
            mUserName = SPUtils.geTinstance().getUserName();
        } else mUserName = userNameView.getText().toString().trim();

        if (!CommonUtil.isMobileNoAll(mUserName)) {
            ToastUtil.ToastShort(this, "请输入正确的手机号码");
            return;
        }
        mPass = passwordView.password_edit.getText().toString().trim();
        if (TextUtils.isEmpty(mPass)) {
            ToastUtil.showSafeToast(LoginActivity.this, "请输入密码");
            return;
        }
        CommonUtil.hideKeyBoard(this);
        showMyDialog();
        loginButton.setClickable(false);
        SPUtils.geTinstance().setUserName(mUserName);
        Midhandler.login(mUserName, mPass, this);
    }

    @Override
    public void onLoginSuccess(UserInfo userBean) {

        SPUtils.geTinstance().clearLoginCache();

        loginButton.setClickable(true);
        Api.uid = userBean.getUid() + "";
        Api.token = userBean.getToken();
        SPUtils.geTinstance().setLoginCache(userBean.getUid() + "", userBean.getToken(), mUserName, mPass);
        // 获取用户信息
        Midhandler.getUserInfo();
        // 获取理财师信息
        Midhandler.getFinanceInfo();
        EventBus.getDefault().post(new MineEvent());//刷新我的界面

        SPUtils.geTinstance().setUserInfo(userBean);

        //在MainActivity中进行初始化
        if (from == MainActivity2.FROMMINEACTIVITY) {
            MainEvent event = new MainEvent(3);
            event.to = to;
            EventBus.getDefault().post(event);
        } else {
            EventBus.getDefault().post(new MainEvent(-1));
        }
        closeDialog();

        if (mStartType != NoRestartMain) {
            toMainActivity();
            finish();
        } else {
            //如果是AdsActivity活动界面进来，并且需要自动打开分享的

            if ("share".equals(todo)) {
                EventBus.getDefault().post(new AdsEvent(AdsEvent.TODO_SHARE));
                finish();
            } else if ("gesture".equals(todo)) {
                ApiManager.getInstance().closeGesturePwd(new SimpleCallback<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        HXXCApplication.getInstance().getLockPatternUtils().clearLock();
                        Midhandler.getUserInfo();
                        EventBus.getDefault().post(new MainEvent(3).setLoginType(MainEvent.FROMFINDPASSWORD));

                        Intent pwdIn = new Intent(LoginActivity.this, CreateGesturePasswordActivity.class);
                        pwdIn.putExtra("from", "login");
                        startActivity(pwdIn);
                        finish();
                    }

                    @Override
                    public void onError() {
                    }
                });

            } else if ("webview".equals(todo)) {
                EventBus.getDefault().post(new AdsEvent(AdsEvent.TODO_JSCallback));
                finish();
            } else if (from == AuthenticationActivity.FROM_Action38) {
                EventBus.getDefault().post(new AdsEvent(AdsEvent.TODO_Reflush));
                ApiManager.getInstance().getUserInfoByUid(SPUtils.geTinstance().getUid(), new SimpleCallback<UserInfo>() {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        if (null == userInfo) {
                            LoginActivity.this.finish();
                            return;
                        }
                        if (userInfo.getRnaStatus() == 0) {
                            startActivity(new Intent(LoginActivity.this, AuthenticationActivity.class).putExtra("from", from));
                        }
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onError() {
                        LoginActivity.this.finish();
                    }
                });
            } else {
                LoginActivity.this.finish();
            }
        }

    }

    public void toMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity2.class));
    }

    @Override
    public void onLoginFailure() {
        closeDialog();
        loginButton.setClickable(true);

//        startActivity(new Intent(LoginActivity.this, MainActivity2.class));
    }


    private void showMyDialog() {
        dialog2 = new Dialog(this, R.style.loadingDialogTheme);
        dialog2.setContentView(new LoadingView(this));
        dialog2.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        dialog2.show();
    }

    private void closeDialog() {
        if (null != dialog2) {
            if (dialog2.isShowing()) {
                dialog2.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if ("gesture".equals(todo) || "changeAccount".equals(todo)) {
            EventBus.getDefault().post(new MainEvent(0).setLoginType(MainEvent.BACK));
        }
        super.onBackPressed();
    }
}
