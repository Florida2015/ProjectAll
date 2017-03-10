package com.huaxia.finance.minedm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.framwork.Utils.AntiHijackingUtil;
import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.FunctionUtils;
import com.framwork.Utils.MyLog;
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
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.TokenModel;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

/**
 * 设置密码
 * Created by houwen.lai on 2016/1/22.
 */
public class SetPasswordActiviity extends BaseActivity implements View.OnClickListener{
    private final String mPageName = SetPasswordActiviity.class.getSimpleName();

    private EditText ed_news_password;
    private EditText ed_news_password_again;
    private ImageButton img_btn_visiable;
    private Button btn_news_password;
    private CheckBox check_set;

    private Context mContext;

    private boolean typeFlag=true;//true设置密码 false修改密码
    private String phone;
    private String code;

    private String title;

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
        setBaseContentView(R.layout.set_password);
        mContext = this;
        typeFlag =getIntent().getBooleanExtra("flag",true);
        if (typeFlag){
            phone =getIntent().getStringExtra("phone");
            code =getIntent().getStringExtra("code");
        }
        findAllViews();

    }

    public void findAllViews(){
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);

        img_btn_visiable = (ImageButton) findViewById(R.id.img_btn_visiable);
        img_btn_visiable.setVisibility(View.GONE);

        img_btn_title_back.setOnClickListener(this);
        img_btn_visiable.setOnClickListener(this);
        img_btn_title_right.setVisibility(View.GONE);

        btn_news_password = (Button) findViewById(R.id.btn_news_password);

        check_set = (CheckBox) findViewById(R.id.check_set);

        ed_news_password = (EditText) findViewById(R.id.ed_news_password);
        ed_news_password_again = (EditText) findViewById(R.id.ed_news_password_again);
//        ed_news_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD );
//        ed_news_password.setTypeface(Typeface.DEFAULT);
//        ed_news_password_again.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        ed_news_password_again.setTypeface(Typeface.DEFAULT);

        ed_news_password.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        ed_news_password_again.setTransformationMethod(PasswordTransformationMethod
                .getInstance());


        if (!typeFlag){
            tv_title_bar.setText("修改密码");
            img_btn_visiable.setVisibility(View.GONE);
            check_set.setVisibility(View.VISIBLE);
            btn_news_password.setText("确认修改");
            ed_news_password.setHint("请输入当前密码");
            ed_news_password_again.setHint("请输入新密码");
        }else{
            tv_title_bar.setText("设置密码");
            img_btn_visiable.setVisibility(View.GONE);
            check_set.setVisibility(View.GONE);
        }

        check_set.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ed_news_password_again.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ed_news_password_again.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                ed_news_password_again.setSelection(ed_news_password_again.getText().length());
            }
        });

        btn_news_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_title_back:
                    finish();
                break;
            case R.id.img_btn_visiable://密码可见
//                if (ed_news_password_again.getInputType()== InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
//                    img_btn_visiable.setImageResource(R.drawable.icon_open_eye);// | InputType.TYPE_CLASS_TEXT
//                    ed_news_password_again.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);//可见
//                }else {
//                    ed_news_password_again.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);//不可见
//                    img_btn_visiable.setImageResource(R.drawable.icon_eges);
//                }



                ed_news_password_again.setSelection(ed_news_password_again.getText().length());
                break;
            case  R.id.btn_news_password:
                if (BtnUtils.isFastDoubleClick()){
                    if (typeFlag)
                    putForgetPasswordQuest();
                    else putUpdatePassQuest();
                }
                break;
        }
    }

    public void putForgetPasswordQuest(){
        String password = ed_news_password.getText().toString().trim();
        String password_again = ed_news_password_again.getText().toString().trim();
        if (TextUtils.isEmpty(password)){
            ToastUtils.showShort("密码不能为空");
            return;
        }
        if (password.length()<6||password.length()>20||password_again.length()<6||password_again.length()>20){
            ToastUtils.showShort("密码请输入6-20位数字字母组合");
            return;
        }
        if (!FunctionUtils.isNumerLetter(password)){
            ToastUtils.showShort("密码请输入6-20位数字字母组合");
            return;
        }
        if (!FunctionUtils.isNumerLetter(password_again)){
            ToastUtils.showShort("密码请输入6-20位数字字母组合");
            return;
        }
        if (!password.equals(password_again)){
            ToastUtils.showShort("两次密码不一致");
            return;
        }
        //请求
        setHttpRequest(phone, password, code);
    }

    /**
     *
     */
    public void setHttpRequest(final String phone, final String password,String smscode){
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
                                SharedPreferencesUtils.getInstanse().putKeyValue(SetPasswordActiviity.this, UserConstant.key_token, token);
                                SharedPreferencesUtils.getInstanse().putKeyValue(SetPasswordActiviity.this, UserConstant.key_userPhone, phone);
                                setResult(RESULT_OK, new Intent().putExtra("phone", phone).putExtra("pass", password));

                                ForgetPasswordActivity.finshLoginActivity();
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

    public void putUpdatePassQuest(){
        String password = ed_news_password.getText().toString().trim();
        String password_again = ed_news_password_again.getText().toString().trim();
        if (TextUtils.isEmpty(password)){
            ToastUtils.showShort("密码不能为空");
            return;
        }
        if (password.length()<6||password.length()>20||password_again.length()<6||password_again.length()>20){
            ToastUtils.showShort("密码6-20位字母数字组合");
            return;
        }
        if (!FunctionUtils.isNumerLetter(password_again)){
            ToastUtils.showShort("新密码6-20位字母数字组合");
            return;
        }
        //请求
        setUpdateHttpRequest(password, password_again);
    }

    /**
     *
     */
    public void setUpdateHttpRequest(String password, final String newpassword){
        final RequestParams params = new RequestParams();
        params.put("passwd", password);
        params.put("newPasswd", newpassword);
        String url = UrlConstants.urlBase+UrlConstants.urlResetPass;
        MyLog.d("api_url=" + url + "_passwd=" + password + "_newPasswd=" + newpassword);
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
                            ToastUtils.showShort("密码修改成功");
                            String token = baseModel.getData().getToken();
                            MyLog.d("api_register_token=" + token);
                            if (!TextUtils.isEmpty(token)) {
                                DMApplication.isLoginFlag = true;
                                DMApplication.token = token;
                                SharedPreferencesUtils.getInstanse().putKeyValue(SetPasswordActiviity.this, UserConstant.key_token, token);
//                                setResult(RESULT_OK, new Intent().putExtra("phone", phone).putExtra("pass", password));
//
                                finish();
                            }
                        } else {
                            ToastUtils.showShort(baseModel.getMsg());
                        }
                    }
                } catch (Exception e) {

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
