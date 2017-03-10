
package com.hxxc.user.app.ui.user;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Agreement;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.financial.BindingFinancialActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.SendCodeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.hxxc.user.app.contract.presenter.RxBasePresenter.mApiManager;

/**
 * Created by Administrator on 2016/9/26.
 * 注册 页
 */

public class RegisterActivity extends BaseRxActivity implements SendCodeView.ICode {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_register_phone)
    EditText ed_register_phone;
    //    @BindView(R.id.ed_register_code_pic)
//    EditText ed_register_code_pic;
    @BindView(R.id.ed_register_code)
    EditText ed_register_code;
//    @BindView(R.id.img_register_pic)
//    ImageView img_register_pic;

    @BindView(R.id.code_text)
    SendCodeView code_text;

    @BindView(R.id.ed_set_pass)
    EditText ed_set_pass;
    @BindView(R.id.ed_set_pass_again)
    EditText ed_set_pass_again;

    @BindView(R.id.ed_set_code)
    EditText ed_set_code;

//    @BindView(R.id.tv_send_code)
//    TextView tv_send_code;

    @BindView(R.id.ch_box_register)
    CheckBox ch_box_register;

    @BindView(R.id.btn_register_r)
    FancyButton btn_register_r;

    @BindView(R.id.tv_tologin)
    TextView tv_tologin;

//    SendCodePresenter sendCodePresenter;//
//    SendCodeContract sendCodeContract;

    String mobile;
    String pass;
    String pass_again;
    String pCode;

    private String code;
    private String mMobile;

    public static RegisterActivity registerActivity = null;
    private int from;

    @Override
    protected void onDestroy() {
        code_text.onDestory();
        super.onDestroy();
    }

    @Override
    public String getMobile() {
        return ed_register_phone.getText().toString();
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_register;
    }

    @Override
    public void initView() {
        toolbar_title.setText("注册");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        registerActivity = this;
        AppManager.getAppManager().addActivity(this);
        from = getIntent().getIntExtra("from", -1);
        initViews();
    }

    private void initViews() {
//        mButtonRight.setText("登录");
//        mButtonRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        Map<String, String> params = new HashMap<>();
        params.put("type", "10");
        code_text.initDatas(this, 0, params, new SendCodeView.MyOnClickListener() {
            @Override
            public boolean onPre() {
                return false;
            }

            @Override
            public void onSuccess(String t) {
                ToastUtil.showSafeToast(RegisterActivity.this, "验证码发送成功");
                code = "t";
                mMobile = ed_register_phone.getText().toString();
                ed_register_code.setText("");
            }

            @Override
            public void onFailure(String t) {
            }
        });

        String str = "已有账号？马上登录";
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#aaaaaa")), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue_text)), 5, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//getResources().getColor(R.color.text_blue)
        tv_tologin.setText(style);
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

    @OnClick({R.id.img_register_pic, R.id.btn_register_r, R.id.tv_register_agreement, R.id.tv_tologin})
    public void onClick(View view) {
        if (BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_tologin://跳转登录界面
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("from", from);
                    startActivity(intent);
                    if (from == AuthenticationActivity.FROM_Action38)
                        finish();
                    break;
                case R.id.img_register_pic://图形验证码
                    break;
                case R.id.btn_register_r://注册
                    doStep();
                    break;
                case R.id.tv_register_agreement://服务协议
                    HashMap<String, String> map = new HashMap<>();
                    map.put("greementType", Constants.Agreement_Regist);
                    mApiManager.getAgreementTemplateList(map, new SimpleCallback<List<Agreement>>() {
                        @Override
                        public void onNext(List<Agreement> agreements) {
                            if (null != agreements && agreements.size() > 0) {
                                Agreement agreement = agreements.get(0);
                                if (TextUtils.isEmpty(agreement.getMobileViewUrl())) {
                                    return;
                                }
                                Intent intent = new Intent(RegisterActivity.this, AdsActivity.class);
                                intent.putExtra("title", TextUtils.isEmpty(agreement.getAgreementName()) ? "注册服务协议" : agreement.getAgreementName());//"网络借贷风险揭示函"
                                intent.putExtra("url", agreement.getMobileViewUrl());
                                intent.putExtra("type", -1);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                    break;
            }
        }
    }

    //服务器返回的验证码
    private void doStep() {
        mobile = ed_register_phone.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.ToastShort(this, R.string.text_register_phone);
            return;
        }
        if (!CommonUtil.isMobileNoAll(mobile)) {
            ToastUtil.ToastShort(this, "请输入正确的手机号码");
            return;
        }
        String mCode = ed_register_code.getText().toString();
        if (TextUtils.isEmpty(mCode)) {
            ToastUtil.ToastShort(this, R.string.text_register_code);
            return;
        }

        pass = ed_set_pass.getText().toString().trim();
        if (TextUtils.isEmpty(pass) || !CommonUtil.isPWD2(pass)) {
            ToastUtil.ToastShort(this, R.string.text_register_pass);
            return;
        }
        pass_again = ed_set_pass_again.getText().toString().trim();
        if (TextUtils.isEmpty(pass_again) || !CommonUtil.isPWD2(pass_again)) {
            ToastUtil.ToastShort(this, R.string.text_register_pass_again);
            return;
        }

        if (!pass.equals(pass_again)) {
            ToastUtil.ToastShort(this, "两次密码输入不一致");
            return;
        }

        pCode = ed_set_code.getText().toString().trim();

        if (!ch_box_register.isChecked()) {
            ToastUtil.ToastShort(this, "请先阅读《华夏信财服务协议条款》");
            return;
        }

//        startActivity(new Intent(RegisterActivity.this,BindingFinancialActivity.class));

        btn_register_r.setEnabled(false);
        //请求注册
        Api.getClient().getRegisterNext(mobile, mCode, pass, pass_again, pCode).
                compose(RxApiThread.convert()).subscribe(new BaseSubscriber<String>(this) {
            @Override
            public void onSuccess(String s) {
                btn_register_r.setEnabled(true);
                Intent intent = new Intent(RegisterActivity.this, BindingFinancialActivity.class);
                intent.putExtra("mobile", mobile);
                intent.putExtra("code", mCode);
                intent.putExtra("pass", pass);
                intent.putExtra("pass_again", pass_again);
                intent.putExtra("pCode", pCode);
                intent.putExtra("from", from);
                startActivity(intent);
            }

            @Override
            public void onFail(String fail) {
                btn_register_r.setEnabled(true);
            }
        });

    }
}
