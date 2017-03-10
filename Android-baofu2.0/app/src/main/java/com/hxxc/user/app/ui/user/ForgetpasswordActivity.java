package com.hxxc.user.app.ui.user;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.user.app.ActivityList;
import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.SharedPreUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.SendCodeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26.
 * 忘记密码 页
 */

public class ForgetpasswordActivity extends BaseRxActivity implements SendCodeView.ICode {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_forget_phone)
    EditText ed_forget_phone;
    @BindView(R.id.ed_forget_code)
    EditText ed_forget_code;
//    @BindView(R.id.tv_forget_send_code)
//    TextView tv_forget_send_code;


    @BindView(R.id.send_code_text)
    SendCodeView send_code_text;

    @Override
    public int getLayoutId() {
        return R.layout.user_forgetpassword;
    }

    @Override
    public void initView() {
        toolbar_title.setText("验证手机");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        AppManager.getAppManager().addActivity(this);
        initViews();
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

    private String mCode = "";
    private String mMobile = "";
    public void initViews() {

        Map<String,String> params = new HashMap<>();
        params.put("type", SendCodeView.TYPE_FIND_PASSWORD+"");
        send_code_text.initDatas(this,0, params, new SendCodeView.MyOnClickListener(){
            @Override
            public boolean onPre() {
                return false;
            }

            @Override
            public void onSuccess(String t) {
                mCode = "t";
                ToastUtil.showSafeToast(ForgetpasswordActivity.this,"验证码发送成功");
                mMobile = ed_forget_phone.getText().toString();
            }

            @Override
            public void onFailure(String t) {

            }
        });
    }

    @OnClick({R.id.btn_forget_next})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_forget_next://下一步
                if (BtnUtils.isFastDoubleClick())
                RequestData();
                break;
        }
    }

    public void RequestData(){
        String mobile = ed_forget_phone.getText().toString();
        if(TextUtils.isEmpty(mobile)){
            ToastUtil.ToastShort(this, "请输入手机号码");
            return;
        }
        if (!CommonUtil.isMobileNoAll(mobile)) {
            ToastUtil.ToastShort(this, "请输入正确的手机号码");
            return;
        }
        if(TextUtils.isEmpty(mCode)){
            ToastUtil.ToastShort(this, "请获取验证码");
            return;
        }
        String code = ed_forget_code.getText().toString();
        if(TextUtils.isEmpty(code)){
            ToastUtil.ToastShort(this, "请输入验证码");
            return;
        }

        SharedPreUtils.getInstanse().putKeyValue(mContext,UserInfoConfig.LoginPhone,mobile);

        ApiManager.getInstance().getFindPassNext(mobile, code, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                startActivity(new Intent(mContext,SetingPasswordActivity.class).putExtra("mobile",mobile).putExtra("code",code));
//                finish();
            }

            @Override
            public void onError() {
                ed_forget_phone.setEnabled(true);
            }
        });
    }

    @Override
    public String getMobile() {
        if (!TextUtils.isEmpty(ed_forget_phone.getText().toString()))
        SPUtils.geTinstance().setUserName(ed_forget_phone.getText().toString());
        return ed_forget_phone.getText().toString();
    }

    @Override
    protected void onDestroy() {
        send_code_text.onDestory();
        super.onDestroy();
    }
}
