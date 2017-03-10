package com.huaxia.finance.minedm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.framwork.Utils.AntiHijackingUtil;
import com.framwork.Utils.BtnUtils;
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
import com.huaxia.finance.constant.DMConstant;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.TokenModel;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

/**
 * 功能：登录界面
 * Created by houwen.lai on 2015/11/19.
 *
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private final String mPageName = LoginActivity.class.getSimpleName();

    private final int CodeForget=0x0001;
    private final int CodeRegister=0x0002;

    private ImageButton img_btn_back;

    private EditText ed_username;
    private EditText ed_userpassword;
    private TextView tv_login_forget_pass;

    private ImageButton img_btn_visiable;
    private Button btn_login;
    private TextView tv_register;

    private String phone;

    private String flagTemp="";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK&&requestCode==CodeForget&&data!=null){
            phone=data.getStringExtra("phone");
            ed_username.setText(phone);
        }
        if (requestCode==RESULT_OK&&requestCode==CodeRegister){
            MenuTwoActivity.getInstance().setTab(2);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static LoginActivity instance=null;
    public static void finshLoginActivity(){
        if (instance!=null){
            instance.finish();
        }
    }

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
        setContentView(R.layout.login_activity);
        instance=this;
        phone = SharedPreferencesUtils.getInstanse().getKeyValue(this,UserConstant.key_userPhone);
        flagTemp = getIntent().getStringExtra("flagLogin");
        findViews();

    }

    private void findViews(){
        img_btn_back = (ImageButton) findViewById(R.id.img_btn_back);
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_userpassword= (EditText) findViewById(R.id.ed_userpassword);
        tv_login_forget_pass = (TextView) findViewById(R.id.tv_login_forget_pass);
        img_btn_visiable = (ImageButton) findViewById(R.id.img_btn_visiable);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_register= (TextView) findViewById(R.id.tv_register);
        img_btn_back.setOnClickListener(this);
        tv_login_forget_pass.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        img_btn_visiable.setOnClickListener(this);

        if (!TextUtils.isEmpty(phone)){
            ed_username.setText(phone);
            ed_username.setSelection(phone.length());
        }

        ed_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed_userpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_back:
                finish();
                break;
            case R.id.tv_login_forget_pass://忘记密码
//                startActivityForResult(new Intent(LoginActivity.this, ForgetPasswordActivity.class).putExtra("phone", phone), CodeForget);
                startActivityForResult(new Intent(LoginActivity.this,RegisterActivity.class).putExtra("flag",false),CodeRegister);
                break;
            case R.id.img_btn_visiable://密码可见
                if (ed_userpassword.getInputType()== InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    img_btn_visiable.setImageResource(R.drawable.icon_open_eye);
                    ed_userpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);//可见
                }else {
                    ed_userpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//不可见
                    img_btn_visiable.setImageResource(R.drawable.icon_eges);
                }
                ed_userpassword.setSelection(ed_userpassword.getText().length());
                break;
            case R.id.btn_login://登录
                if (BtnUtils.getInstance().isFastDoubleClick()){
                    setLoginQuest();
                }
                break;
            case R.id.tv_register://注册
                if (BtnUtils.isFastDoubleClick())
                startActivityForResult(new Intent(LoginActivity.this,RegisterActivity.class).putExtra("flag",true),CodeRegister);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            DMConstant.flagCode=false;
            setResult(RESULT_OK);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setLoginQuest(){
        String name = ed_username.getText().toString().trim();
        String password = ed_userpassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            ToastUtils.showShort("手机号不能为空");
            return;
        }
        if (!PhoneUtils.getInstance().isPhone(this,name))return;

        if (TextUtils.isEmpty(password)){
            ToastUtils.showShort("密码不能为空");
            return;
        }
        if (password.length()<6||password.length()>20){
            ToastUtils.showShort("密码6-20位字母数字组合");
            return;
        }

        //请求
        setHttpRequest(name,password);

//        setRequest(name,password);

    }

    /**
     *
     */
    public void setHttpRequest(final String phone,String password){
        final RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("passwd", password);
        String url = UrlConstants.urlBase+UrlConstants.urlLogin;
        MyLog.d("api_url=" + url+"_phone="+phone+"_password="+password);
        DMApplication.getInstance().getHttpClient(this).post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    String result = StringsUtils.getBytetoString(responseBody);
                    if (statusCode == 200) {
                        if (!RequestUtils.isHtml5ToString(result)){
                            BaseModel<TokenModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<TokenModel>>() {
                            }.getType());
                            if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                                String token = baseModel.getData().getToken();
                                MyLog.d("api_login_token="+token);
                                if (!TextUtils.isEmpty(token)) {
                                    DMApplication.isLoginFlag = true;
                                    DMApplication.token = token;
                                    SharedPreferencesUtils.getInstanse().putKeyValue(LoginActivity.this, UserConstant.key_token, token);
                                    SharedPreferencesUtils.getInstanse().putKeyValue(LoginActivity.this, UserConstant.key_userPhone, phone);
                                    if (flagTemp.equals("menu")) {
                                        MenuTwoActivity.getInstance().setTab(3);
                                        setResult(RESULT_OK);
                                        LoginActivity.this.finish();
                                    } else if (flagTemp.equals("payorder")) {
                                        LoginActivity.this.finish();
                                    }
                                }
                            }else {
                                ToastUtils.showShort(baseModel.getMsg());
                            }
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                ToastUtils.showShort("网络异常!");

            }
        });
    }
    /**
     * 判断号码是否存在
     */
    public void setHttpRequestPhone(final String phone){
        final RequestParams params = new RequestParams();
        params.put("accountVO.mobileNo", phone);
        String url = UrlConstants.urlBase+UrlConstants.urlValidatePhone;
        MyLog.d("api_url=" + url+"_mobibleNo="+phone);
        DMApplication.getInstance().getHttpClient(this).post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    String result = StringsUtils.getBytetoString(responseBody);
                    if (statusCode == 200 && !RequestUtils.isHtml5ToString(result)) {
//                        BaseModel model = BaseModel.prase(result);
//                        if (model.isSuccess()) {
//                            tv_login_warming.setVisibility(View.VISIBLE);
//                            tv_login_warming.setText("该号码尚未注册");
//                        } else
//                            startActivityForResult(new Intent(LoginActivity.this, ForgetPasswordActivity.class).putExtra("phone", phone), CodeForget);
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");


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
