package com.hxxc.user.app.ui.financial;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.Event.AdsEvent;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.Event.MineEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.ui.user.LoginActivity;
import com.hxxc.user.app.ui.user.RegisterActivity;
import com.hxxc.user.app.ui.user.RegisterStatusActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.widget.LoadingView;

import de.greenrobot.event.EventBus;

/**
 * Created by houwen.lai on 2016/10/9.
 * 绑定顾问 对话框
 */

public class DialogBindingFinancial extends Activity implements View.OnClickListener {

    private LinearLayout linear_dialog_binding;
    private Button btn_binding_cancal;
    private Button btn_binding_ok;
    private TextView tv_content;

    String mobile;
    String code;
    String pass;
    String pass_again;
    String pCode;
    String fid;

    private Dialog dialog2;
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_binding_financial);

        mobile = getIntent().getStringExtra("mobile");
        code = getIntent().getStringExtra("code");
        pass = getIntent().getStringExtra("pass");
        pass_again = getIntent().getStringExtra("pass_again");
        pCode = getIntent().getStringExtra("pCode");
        fid = getIntent().getStringExtra("fid");
        from = getIntent().getIntExtra("from", -1);

        tv_content = (TextView) findViewById(R.id.tv_content);
        String fname = getIntent().getStringExtra("fname");
        tv_content.setText("确认绑定" + fname + "作为您的理财师?");

        linear_dialog_binding = (LinearLayout) findViewById(R.id.linear_dialog_binding);
        btn_binding_cancal = (Button) findViewById(R.id.btn_binding_cancal);
        btn_binding_ok = (Button) findViewById(R.id.btn_binding_ok);
        btn_binding_cancal.setOnClickListener(this);
        btn_binding_ok.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_binding_cancal:
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case R.id.btn_binding_ok:
                if (BtnUtils.isFastDoubleClick()) RequestData();

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return true;
        }
        return false;
    }

    public void RequestData() {
        btn_binding_ok.setEnabled(false);
        showMyDialog();

        Api.getClient().getRegister(mobile, code, pass, pass_again, fid, pCode).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<UserInfo>(DialogBindingFinancial.this) {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        closeDialog();
                        Api.uid = userInfo.getUid() + "";
                        Api.token = userInfo.getToken();
                        SPUtils.geTinstance().setLoginCache(userInfo.getUid() + "", userInfo.getToken(), mobile, pass);
                        // 获取用户信息
                        Midhandler.getUserInfo();
                        // 获取理财师信息
                        Midhandler.getFinanceInfo();
//                        EventBus.getDefault().post(new MainEvent(3));

                        AppManager.getAppManager().finishActivity(RegisterActivity.class);
                        AppManager.getAppManager().finishActivity(LoginActivity.class);

                        if (from == AuthenticationActivity.FROM_Action38) {
                            finishLoginBingActivity();
                            EventBus.getDefault().post(new AdsEvent(AdsEvent.TODO_Reflush));
                            EventBus.getDefault().post(new MainEvent(-1));
                            EventBus.getDefault().post(new MineEvent());
                            startActivity(new Intent(DialogBindingFinancial.this, AuthenticationActivity.class).
                                    putExtra("from", from));
                        } else
                            startActivity(new Intent(DialogBindingFinancial.this, RegisterStatusActivity.class));

                        DialogBindingFinancial.this.finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }

                    @Override
                    public void onFail(String fail) {
                        btn_binding_ok.setEnabled(true);
                        closeDialog();
                        DialogBindingFinancial.this.finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                });
    }
    public void finishLoginBingActivity() {
        if (LoginActivity.loginActivity != null) {
            LoginActivity.loginActivity.finish();
            LoginActivity.loginActivity = null;
        }
        if (RegisterActivity.registerActivity != null) {
            RegisterActivity.registerActivity.finish();
            RegisterActivity.registerActivity = null;
        }
        if (BindingFinancialActivity.bindingFinancialActivity != null) {
            BindingFinancialActivity.bindingFinancialActivity.finish();
            BindingFinancialActivity.bindingFinancialActivity = null;
        }
        if (SearchFinancialActivity.searchFinancialActivity != null) {
            SearchFinancialActivity.searchFinancialActivity.finish();
            SearchFinancialActivity.searchFinancialActivity = null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDialog();
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
