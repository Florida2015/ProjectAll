package com.hxxc.user.app.ui.user;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.R;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyClickButton;

import butterknife.BindView;

import static com.hxxc.user.app.contract.presenter.RxBasePresenter.mApiManager;

/**
 * Created by Administrator on 2016/9/27.
 * 设置密码 页
 */

public class SetingPasswordActivity extends BaseRxActivity {

    public static final int TYPE_UPDATE_PASSWORD = 999;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_seting_password)
    EditText ed_seting_password;
    @BindView(R.id.ed_seting_password_again)
    EditText ed_seting_password_again;
    //    @BindView(R.id.btn_seting_pass)
//    FancyButton btn_seting_pass;
    @BindView(R.id.update_password_btn)
    MyClickButton update_password_btn;

    private String code;
    private String mobile;
    private int type;
    private Dialog systemDialog;
    private EditText ed_old_password;


    @Override
    public int getLayoutId() {
        return R.layout.user_seting_pass;
    }

    @Override
    public void initView() {
        toolbar_title.setText("设置密码");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        type = getIntent().getIntExtra("type", 0);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        mobile = intent.getStringExtra("mobile");
        update_password_btn.setOnMyClickListener(new MyClickButton.MyClickListener() {

            @Override
            public void onMyClickListener() {
                doUpdatePasswor();
            }
        });

        if (TYPE_UPDATE_PASSWORD == type) {//修改密码
            toolbar_title.setText("修改密码");
            update_password_btn.setText("确定");
            ed_old_password = (EditText) findViewById(R.id.ed_old_password);
            ed_old_password.setVisibility(View.VISIBLE);
            findViewById(R.id.view_line).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doUpdatePasswor() {
        String newPassowrd = ed_seting_password.getText().toString();
        String reNewPassword = ed_seting_password_again.getText().toString();
        if (TYPE_UPDATE_PASSWORD == type) {//修改密码
            if (TextUtils.isEmpty(ed_old_password.getText().toString())) {
                ToastUtil.ToastShort(SetingPasswordActivity.this, "原始密码不能为空");
                return;
            }
        }

        if ("".equals(newPassowrd)) {
            ToastUtil.ToastShort(SetingPasswordActivity.this, "新密码不能为空");
            return;
        }

        if (!CommonUtil.isPWD2(newPassowrd)) {
            ToastUtil.ToastShort(SetingPasswordActivity.this, "请输入6-36位字符密码，且必须包含数字和字母");
            return;
        }
        if ("".equals(reNewPassword)) {
            ToastUtil.ToastShort(SetingPasswordActivity.this, "确认新密码不能为空");
            return;
        }
        if (!newPassowrd.equals(reNewPassword)) {
            ToastUtil.ToastShort(SetingPasswordActivity.this, "两次密码不一致");
            return;
        }
        update_password_btn.begin();
        if (TYPE_UPDATE_PASSWORD == type) {//修改密码
            mApiManager.updateUserPassword(SPUtils.geTinstance().getUid(), ed_old_password.getText().toString(), newPassowrd, reNewPassword, new SimpleCallback<String>() {
                @Override
                public void onNext(String s) {
                    update_password_btn.finish();
                    showFeedbackDialog();
                }

                @Override
                public void onError() {
                    update_password_btn.finish();
                }
            });
        } else {
            mApiManager.getFindPass(mobile, code, newPassowrd, reNewPassword, new SimpleCallback<String>() {
                @Override
                public void onNext(String s) {
                    update_password_btn.finish();
                    goUpdatePasswordSucess();
                }

                @Override
                public void onError() {
                    update_password_btn.finish();
                }
            });
        }
    }

    private void goUpdatePasswordSucess() {
//        Intent intent = new Intent(this, FindPasswordSuccessActivity.class);
//        startActivity(intent);

        AppManager.getAppManager().finishActivity(ForgetpasswordActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showFeedbackDialog() {
        if (null == systemDialog) {
            systemDialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(this, R.layout.dialog_feedback, null);
            MyClickButton sure = (MyClickButton) inflate.findViewById(R.id.sure);

            TextView tv_1 = (TextView) inflate.findViewById(R.id.tv_1);
            TextView tv_2 = (TextView) inflate.findViewById(R.id.tv_2);
            tv_2.setVisibility(View.GONE);
            tv_1.setText("登录密码修改成功");
            sure.setOnMyClickListener(new MyClickButton.MyClickListener() {
                @Override
                public void onMyClickListener() {
                    systemDialog.dismiss();
                    AppManager.getAppManager().finishActivity(ForgetpasswordActivity.class);
                    finish();
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
    }
}
