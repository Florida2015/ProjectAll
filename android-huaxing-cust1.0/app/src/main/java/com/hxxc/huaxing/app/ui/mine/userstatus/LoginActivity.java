package com.hxxc.huaxing.app.ui.mine.userstatus;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.mine.UserInfoPresenter;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.KeyBoradUtils;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.utils.ToastUtil;
import com.hxxc.huaxing.app.wedgit.CustomEdit;
import com.hxxc.huaxing.app.wedgit.LoadingView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.hxxc.huaxing.app.utils.DisplayUtil.getStatusBarHeight;


/**
 * Created by Administrator on 2016/9/26.
 * 登录 页
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ed_user_pass)
    EditText ed_user_pass;

    @BindView(R.id.ed_user_phone_s)
    CustomEdit ed_user_phone_s;

    @BindView(R.id.scroll_login)
    ScrollView scroll_login;

    private Context mContext;

    private Dialog dialog2;

    Handler handler = new Handler();

    public static LoginActivity loginActivity=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        ButterKnife.bind(this);

        mContext = this;
        loginActivity = this;
        AppManager.getAppManager().addActivity(this);

        String longinPhone = SharedPreUtils.getInstanse().getKeyValue(mContext, UserInfoConfig.LoginPhone);
        if (!TextUtils.isEmpty(longinPhone)){
            ed_user_phone_s.setEditText(longinPhone);
        }
        ed_user_phone_s.setEditTextType(InputType.TYPE_CLASS_PHONE);
//        ed_user_phone_s.getEditWedgit().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                scroll_login.smoothScrollTo(0,3* ed_user_phone_s.getEditWedgit().getMeasuredHeight());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        ed_user_phone_s.setHintEditText("请输入手机号");

//        ed_user_pass.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                scroll_login.smoothScrollTo(0,4* ed_user_phone_s.getEditWedgit().getMeasuredHeight());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        scroll_login.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    // 软键盘的显示状态
    private boolean ShowKeyboard = false;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            // 应用可以显示的区域。此处包括应用占用的区域，包括标题栏不包括状态栏
            Rect r = new Rect();
            scroll_login.getWindowVisibleDisplayFrame(r);
            // 键盘最小高度
            int minKeyboardHeight = 150;
            // 获取状态栏高度
            int statusBarHeight = getStatusBarHeight(mContext);
            // 屏幕高度,不含虚拟按键的高度
            int screenHeight = scroll_login.getRootView().getHeight();
            // 在不显示软键盘时，height等于状态栏的高度
            int height = screenHeight - (r.bottom - r.top);

            if (ShowKeyboard) {
                // 如果软键盘是弹出的状态，并且height小于等于状态栏高度，
                // 说明这时软键盘已经收起
                if (height - statusBarHeight < minKeyboardHeight) {
                    ShowKeyboard = false;
                    scroll_login.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll_login.smoothScrollTo(0,0);
                        }
                    });
                }
            } else {
                // 如果软键盘是收起的状态，并且height大于状态栏高度，
                // 说明这时软键盘已经弹出
                if (height - statusBarHeight > minKeyboardHeight) {
                    ShowKeyboard = true;

                    scroll_login.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll_login.smoothScrollTo(0,4* ed_user_phone_s.getEditWedgit().getMeasuredHeight());
                        }
                    });
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        String mobile = SharedPreUtils.getInstanse().getKeyValue(mContext,UserInfoConfig.LoginPhone);
        if (!TextUtils.isEmpty(mobile)){
            ed_user_phone_s.setEditText(mobile);
        }

    }

    @OnClick({R.id.imgbtn_back, R.id.tv_forget_pass, R.id.btn_longin, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgbtn_back:
                if (ed_user_pass != null)KeyBoradUtils.hideSoftInput(mContext,ed_user_pass);
                finish();
                break;
            case R.id.tv_forget_pass://忘记密码
                if (BtnUtils.isFastDoubleClick())
                    startActivity(new Intent(mContext, ForgetpasswordActivity.class));
                break;
            case R.id.btn_longin://登录
                if (BtnUtils.isFastDoubleClick()) InitData();
                break;
            case R.id.btn_register://注册
                if (BtnUtils.isFastDoubleClick())
                    startActivity(new Intent(mContext, RegisterActivity.class));
                break;
        }
    }

    public void InitData(){
        String name = ed_user_phone_s.getEditText();
        String pass = ed_user_pass.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, mContext.getResources().getString(R.string.text_register_phone), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonUtil.isMobileNoAll(name)) {
            ToastUtil.ToastShort(this, mContext.getResources().getString(R.string.text_register_phone_right));
            return;
        }
        if (TextUtils.isEmpty(pass) || !CommonUtil.isNumerLetter(pass)) {
            Toast.makeText(this, mContext.getResources().getString(R.string.text_pass_text), Toast.LENGTH_SHORT).show();
            return;
        }

        //提交数据
        showMyDialog();
        Api.getClient().getUsersLogin(name, pass).compose(RxApiThread.convert()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                closeDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeDialog();
                if (e instanceof SocketTimeoutException) {
                    ToastUtil.getInstance().ToastShortFromNet("网络中断，请检查您的网络状态");
                } else if (e instanceof ConnectException) {
                    ToastUtil.getInstance().ToastShortFromNet("网络中断，请检查您的网络状态");
                } else {
                    if (!CommonUtil.isNetworkAvailable2(HXXCApplication.getContext())) {
                        ToastUtil.getInstance().ToastShortFromNet("网络连接不可用,请检查网络设置");
                    } else {
                        Toast.makeText(mContext, "登录异常", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNext(String s) {
                closeDialog();
                BaseBean<UserInfoBean> baseBean = HXXCApplication.getInstance().getGson().fromJson(s, new TypeToken<BaseBean<UserInfoBean>>() {
                }.getType());
                if (baseBean.isSuccess()) {
                    SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.LoginPhone, name);
                    SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus, "2");
                    SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.FlagLogin, true);
                    if (null != baseBean.getModel()) {
                        SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.spUid, baseBean.getModel().getUid() + "");
                        SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.spToken, baseBean.getModel().getToken() + "");
                        SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.spOpenEaccount, baseBean.getModel().getIsOpenEaccount());
                        UserInfoConfig.getOpenAcount = baseBean.getModel().getIsOpenEaccount();
                    }
                    UserInfoConfig.getRegisterCode = "2";
                    UserInfoPresenter.getUserInfo();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(mContext, baseBean.getErrMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (ed_user_phone_s.getEditWedgit() != null)KeyBoradUtils.hideSoftInput(mContext,ed_user_phone_s.getEditWedgit());
        if (ed_user_pass != null)KeyBoradUtils.hideSoftInput(mContext,ed_user_pass);

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
}
