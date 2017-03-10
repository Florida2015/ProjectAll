package com.huaxia.finance.minedm;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.framwork.Utils.AntiHijackingUtil;
import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.FunctionUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.PhoneUtils;
import com.framwork.Utils.RequestUtils;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.asychttpclient.RequestParams;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.DMConstant;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.TokenModel;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

/**
 * 功能：注册页面
 * Created by Administrator on 2015/11/19.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private String mPageName = RegisterActivity.class.getSimpleName();

    private LinearLayout linear_argements;

    private EditText ed_user_phone;
    private EditText ed_user_password;
    private EditText ed_register_code;
    private CheckBox checkbox_register;
    private Button btn_register_code;
    private TextView tv_btn_argment;
    private ImageButton img_btn_visiable;
    private Button btn_register;

    public boolean flagTemp=true;

    private ScrollView scrollview_register;

    private final int msg_code=0x001;
    private int countTime = 60;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x001://btn_forget_code
                    if (countTime<=0){
                        countTime=60;
                        btn_register_code.setEnabled(true);
                        btn_register_code.setText(getResources().getString(R.string.forget_code));
                    }else{
                        btn_register_code.setEnabled(false);
                        btn_register_code.setText("重发("+countTime+"s)");
                        handler.sendEmptyMessageDelayed(msg_code,1000);
                        countTime--;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    int high = 168;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.register_activity);
        DMConstant.flagCode=false;
        flagTemp = getIntent().getBooleanExtra("flag",true);
        findViews();

    }

    private void findViews(){
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(flagTemp){
            tv_title_bar.setText("注册");
            mPageName = RegisterActivity.class.getSimpleName();
        } else {
            tv_title_bar.setText("忘记密码");
            mPageName = ForgetPasswordActivity.class.getSimpleName();
        }

        scrollview_register = (ScrollView) findViewById(R.id.scrollview_register);

        ed_user_phone = (EditText) findViewById(R.id.ed_user_phone);
        ed_user_password= (EditText) findViewById(R.id.ed_user_password);
        ed_register_code= (EditText) findViewById(R.id.ed_register_code);
        ed_user_password.setTypeface(Typeface.DEFAULT);
        linear_argements = (LinearLayout) findViewById(R.id.linear_argements);
        checkbox_register = (CheckBox) findViewById(R.id.checkbox_register);
        btn_register_code= (Button) findViewById(R.id.btn_register_code);
        tv_btn_argment= (TextView) findViewById(R.id.tv_btn_argment);
        img_btn_visiable= (ImageButton) findViewById(R.id.img_btn_visiable);
        btn_register= (Button) findViewById(R.id.btn_register);

        btn_register_code.setOnClickListener(this);
        tv_btn_argment.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        img_btn_visiable.setOnClickListener(this);

//        high = ed_user_phone.getTop();

        if (flagTemp){
            linear_argements.setVisibility(View.VISIBLE);
            btn_register.setText("注册");
        } else {
            linear_argements.setVisibility(View.INVISIBLE);
            btn_register.setText("确定");
        }

        ed_user_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    scrollview_register.smoothScrollTo(0,high);
                }
            }
        });
        ed_user_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    scrollview_register.smoothScrollTo(0,high);
                }
            }
        });
        ed_register_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    scrollview_register.smoothScrollTo(0,high);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_btn_argment://协议
                startActivity(new Intent(RegisterActivity.this,AgreementActivity.class));
                break;
            case R.id.img_btn_visiable://密码可见
                if (ed_user_password.getInputType()== InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    img_btn_visiable.setImageResource(R.drawable.icon_open_eye);
                    ed_user_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//可见
                    ed_user_password.setTypeface(Typeface.DEFAULT);
                }else {
                    ed_user_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//不可见
                    ed_user_password.setTypeface(Typeface.DEFAULT);
                    img_btn_visiable.setImageResource(R.drawable.icon_eges);
                }
                ed_user_password.setSelection(ed_user_password.getText().length());
                break;
            case R.id.btn_register_code://获取验证码
                if (BtnUtils.isFastDoubleClick())
                    getRegisterCode();
                break;
            case R.id.btn_register://注册
                if (BtnUtils.getInstance().isFastDoubleClick())
                    putRegisterQuest();
                break;
        }
    }

    public void getRegisterCode(){
        String mphone = ed_user_phone.getText().toString().trim();
        if (TextUtils.isEmpty(mphone)){
            ToastUtils.showShort("请输入您的手机号码");
            return;
        }
        DMConstant.flagCode=true;
        getRegisterCodeQuest(false, mphone);
    }

    /**
     * 获取验证码
     */
    public void getRegisterCodeQuest(final boolean flag, final String phone){
        MyLog.d("api_register_phone=" + phone);
        if (!PhoneUtils.getInstance().isPhone(this,phone))return;
        final RequestParams params = new RequestParams();
        params.put("phone", phone);
//        String url = UrlConstants.urlBase+UrlConstants.urlSendSms;
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        if(flag){
            url.append(UrlConstants.urlSendSms);
        }else url.append(UrlConstants.urlValidatePhone);
        MyLog.d("api_url=" + url.toString() + "_mobibleNo=" + phone);
        btn_register_code.setEnabled(false);
        DMApplication.getInstance().getHttpClient(this).post(this, url.toString(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                btn_register_code.setEnabled(true);
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nresult=" + resultDate);
                    if (statusCode == 200 && !RequestUtils.isHtml5ToString(resultDate)) {
                        BaseModel<String> baseModel = DMApplication.getInstance().getGson().fromJson(resultDate, new TypeToken<BaseModel<String>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            if (flag) handler.sendEmptyMessage(msg_code);
                            else{
                                if (flagTemp) ToastUtils.showShort(baseModel.getMsg());
                                else getRegisterCodeQuest(true, phone);
                            }
                        } else {
                            if (flag) ToastUtils.showShort(baseModel.getMsg());
                            else {
                                if (flagTemp) getRegisterCodeQuest(true, phone);
                                else ToastUtils.showShort(baseModel.getMsg());
                            }
                        }
                    }
                } catch (Exception e) {
                    ToastUtils.showShort("数据异常!");
                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nreult=" + StringsUtils.getBytetoString(responseBody));
                ToastUtils.showShort("网络异常!");
                btn_register_code.setEnabled(true);
            }
        });
    }


    public void putRegisterQuest(){
        String phone = ed_user_phone.getText().toString().trim();
        String password = ed_user_password.getText().toString().trim();
        String register_code = ed_register_code.getText().toString().trim();
        boolean flag = checkbox_register.isChecked();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showShort("请输入手机号码");
            return;
        }
        if (TextUtils.isEmpty(password)){
            ToastUtils.showShort("请输入登录密码");
            return;
        }
        if (password.length()<6||password.length()>20){
            ToastUtils.showShort("密码请输入6-20位数字字母组合");
            return;
        }
        if (!FunctionUtils.isNumerLetter(password)){
            ToastUtils.showShort("密码请输入6-20位数字字母组合");
            return;
        }
        if (!DMConstant.flagCode){
            ToastUtils.showShort("验证码未发送");
            return;
        }
        if (TextUtils.isEmpty(register_code)){
            ToastUtils.showShort("验证码不能为空");
            return;
        }
        if (!flag&&flagTemp){
            ToastUtils.showShort("请选择服务协议");
            return;
        }
        //网络请求
        if (flagTemp)
        setHttpRequest(phone, password, register_code);
        else setForHttpRequest(phone, password, register_code);
    }


    /**
     *
     */
    public void setHttpRequest(final String phone,String password,String code){
        final RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("passwd", password);
        params.put("smscode", code);
        String url = UrlConstants.urlBase+UrlConstants.urlRegister;
        MyLog.d("api_url=" + url + "_phone=" + phone + "_password=" + password + "_code=" + code);

        DMApplication.getInstance().getHttpClient(this).post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String result = StringsUtils.getBytetoString(responseBody);
                            MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nresult=" + result);
                            if (statusCode == 200) {
                                if (!RequestUtils.isHtml5ToString(result)) {
                                    BaseModel<TokenModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<TokenModel>>() {
                                    }.getType());
                                    if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                                        ToastUtils.showShort(baseModel.getMsg());
                                        String token = baseModel.getData().getToken();
                                        MyLog.d("api_login_token=" + token);
                                        if (!TextUtils.isEmpty(token)) {
                                            DMApplication.isLoginFlag = true;
                                            DMApplication.token = token;
                                            SharedPreferencesUtils.getInstanse().putKeyValue(RegisterActivity.this, UserConstant.key_token, token);
                                            SharedPreferencesUtils.getInstanse().putKeyValue(RegisterActivity.this, UserConstant.key_userPhone, phone);

                                            LoginActivity.finshLoginActivity();
                                            MenuTwoActivity.getInstance().setTab(0);
//                                            setResult(RESULT_OK);
                                            RegisterActivity.this.finish();
                                        }
                                    }else{
                                        ToastUtils.showShort(baseModel.getMsg());
                                    }
                                }
                            }
                        } catch (Exception e) {
                            ToastUtils.showShort("数据异常!");
                        }
                    }

                    @Override
                    public void onFailure(String reqUrl, int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {
                        MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                        ToastUtils.showShort("网络异常!");
                    }
                }
            );
        }

    /**
     * 忘记密码
     */
    public void setForHttpRequest(final String phone, final String password,String smscode){
        final RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("smscode", smscode);
        params.put("passwd", password);
        String url = UrlConstants.urlBase+UrlConstants.urlForget;
        MyLog.d("api_url=" + url + "_phone=" + phone + "_password=" + password + "_sms=" + smscode);
        DMApplication.getInstance().getHttpClient(this).post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nresult=" + result);
                    if (statusCode == 200 && !RequestUtils.isHtml5ToString(result)) {
                        BaseModel<TokenModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<TokenModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            ToastUtils.showShort("密码找回成功");
                            String token = baseModel.getData().getToken();
                            MyLog.d("api_register_token=" + token);
                            if (!TextUtils.isEmpty(token)) {
                                DMApplication.isLoginFlag = true;
                                DMApplication.token = token;
                                SharedPreferencesUtils.getInstanse().putKeyValue(RegisterActivity.this, UserConstant.key_token, token);
                                SharedPreferencesUtils.getInstanse().putKeyValue(RegisterActivity.this, UserConstant.key_userPhone, phone);
                                setResult(RESULT_OK, new Intent().putExtra("phone", phone).putExtra("pass", password));

                                LoginActivity.finshLoginActivity();
                                MenuTwoActivity.getInstance().setTab(0);
                                finish();
                            }
                        } else {
                            ToastUtils.showShort(baseModel.getMsg());
                        }
                    }
                } catch (Exception e) {
                    ToastUtils.showShort("数据异常!");
                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                ToastUtils.showShort("网络异常!");
            }
        });
    }

        @Override
    protected void onStop() {
        super.onStop();
        boolean safe = AntiHijackingUtil.checkActivity(this);
        if (!safe) ToastUtils.showLong("防盗号或欺骗，请不要输入密码");
    }
}
