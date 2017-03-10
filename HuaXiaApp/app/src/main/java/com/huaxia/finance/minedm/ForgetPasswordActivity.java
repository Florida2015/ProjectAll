package com.huaxia.finance.minedm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.huaxia.finance.R;
import com.huaxia.finance.constant.DMConstant;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.BaseModel;

import org.apache.http.Header;


/**
 * 功能：忘记密码
 * Created by houwen.lai on 2015/11/24.
 */
public class ForgetPasswordActivity extends Activity implements View.OnClickListener{

    public ImageButton img_btn_title_back;
    public TextView tv_title_bar;
    public ImageButton img_btn_title_right;

    private EditText ed_phone_number;
    private EditText ed_forget_code;
    private Button btn_forget_code;
    private Button btn_forgetpassword_code;

    private String phone;
    private String code;

    private Context mContext;

    private final int msg_code=0x001;
    private int countTime = 60;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x001://btn_forget_code
                    if (countTime<=0){
                        countTime=60;
                        btn_forget_code.setTextColor(getResources().getColor(R.color.orange_ff92));
                        btn_forget_code.setEnabled(true);
                        btn_forget_code.setText(getResources().getString(R.string.forget_code));
                    }else{
                        btn_forget_code.setTextColor(getResources().getColor(R.color.black_9999));
                        btn_forget_code.setEnabled(false);
                        btn_forget_code.setText(countTime+"s后重新发送");
                        handler.sendEmptyMessageDelayed(msg_code,1000);
                        countTime--;
                    }
                break;
            }
            super.handleMessage(msg);
        }
    };

    private static ForgetPasswordActivity instance=null;
    public static void finshLoginActivity(){
        if (instance!=null){
            instance.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frogetpassword_activity);
        mContext = this;
        instance = this;
        DMConstant.flagCode=false;
        findViews();

    }

    private void findViews(){
//        relative_title_bar = (RelativeLayout) findViewById(R.id.relative_title_bar);
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_back.setOnClickListener(this);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("忘记密码");

        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number);
        ed_forget_code= (EditText) findViewById(R.id.ed_forget_code);
        btn_forget_code= (Button) findViewById(R.id.btn_forget_code);
        btn_forgetpassword_code= (Button) findViewById(R.id.btn_forgetpassword_code);

        btn_forget_code.setOnClickListener(this);
        btn_forgetpassword_code.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_title_back://返回
                finish();
                break;
            case R.id.btn_forget_code://获取验证码
                if (BtnUtils.isFastDoubleClick()){
                    getForgetCodeQuest(false);
                }
                break;
            case R.id.btn_forgetpassword_code://提交
                if (BtnUtils.isFastDoubleClick()){
                    putForgetPasswordQuest();
                }
                break;
        }
    }

    /**
     * 获取验证码
     */
    public void getForgetCodeQuest(final boolean flag){
        phone = ed_phone_number.getText().toString().trim();
        if (!PhoneUtils.getInstance().isPhone(this,phone))return;
        DMConstant.flagCode=true;
        final RequestParams params = new RequestParams();
        params.put("phone", phone);
//        String url = UrlConstants.urlBase+UrlConstants.urlSendSms;
//        MyLog.d("api_url=" + url + "_mobibleNo=" + phone);
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        if(flag){
            url.append(UrlConstants.urlSendSms);
        }else url.append(UrlConstants.urlValidatePhone);
        MyLog.d("api_url=" + url.toString() + "_phone=" + phone);
        DMApplication.getInstance().getHttpClient(this).post(this, url.toString(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nresult=" + resultDate);
                    if (statusCode == 200 && !RequestUtils.isHtml5ToString(resultDate)) {
                        BaseModel model = DMApplication.getInstance().getGson().fromJson(resultDate, new TypeToken<BaseModel<String>>() { }.getType());
                        if (model.getStatus().equals(StateConstant.Status_success)) {
                            if (flag) handler.sendEmptyMessage(msg_code);
                            else getForgetCodeQuest(true);
                        } else {
                            if (flag) ToastUtils.showShort("验证码发送失败");
                            else ToastUtils.showShort("该手机号码未注册");
                        }
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nreult=" + StringsUtils.getBytetoString(responseBody));

            }
        });
    }

    public void putForgetPasswordQuest(){
        phone = ed_phone_number.getText().toString().trim();
        if (!PhoneUtils.getInstance().isPhone(this,phone))return;
        code = ed_forget_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)||code.length()==0){
            ToastUtils.showShort("请输入验证码");
            return;
        }
        startActivity(new Intent(ForgetPasswordActivity.this,SetPasswordActiviity.class).putExtra("phone",phone).putExtra("code",code).putExtra("flag",true));

        //请求
//        setHttpRequest(phone, password, password_again, code);
    }

    /**
     *
     */
    public void setHttpRequest(final String phone,String password, final String passwordagain,String smscodde){
        final RequestParams params = new RequestParams();
//        final AsyncHttpClient client  = new AsyncHttpClient();
        params.put("mobileNo", phone);
        params.put("accountVO.password", password);
        params.put("accountVO.confirm", passwordagain);
        params.put("accountVO.smsCode", smscodde);
        String url = UrlConstants.urlBase+UrlConstants.urlForget;
        MyLog.d("api_url=" + url + "_mobibleNo=" + phone + "_password=" + password+"_passagain="+passwordagain+"_sms="+smscodde);
        DMApplication.getInstance().getHttpClient(this).post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nresult=" + result);
                    if (statusCode == 200 && !RequestUtils.isHtml5ToString(result)) {
                        BaseModel model = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<String>>() {
                        }.getType());
                        if (model.isSuccess()) {
                            ToastUtils.showShort("找回密码成功");
                            SharedPreferencesUtils.getInstanse().putKeyValue(mContext, UserConstant.key_userPhone, phone);
//                            SharedPreferencesUtils.getInstanse().putKeyValue(mContext, UserConstant.key_userpassword, passwordagain);
                            setResult(RESULT_OK, new Intent().putExtra("phone", phone).putExtra("pass", passwordagain));
                            finish();
                        } else {
//                            tv_forget_warming.setVisibility(View.GONE);
                            ToastUtils.showShort(model.getMes());
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
