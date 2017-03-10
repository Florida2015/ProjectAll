package com.huaxia.finance.mangemoneydm;

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
import com.framwork.Utils.MyLog;
import com.framwork.Utils.PhoneUtils;
import com.framwork.Utils.RequestUtils;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.DMConstant;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

/**
 * 发送支付验证码
 * Created by houwen.lai on 2016/1/26.
 */
public class PaySmsActivity extends Activity implements View.OnClickListener{
    private final String mPageName = PaySmsActivity.class.getSimpleName();

    private ImageButton img_btn_close;
    private TextView tv_pay_money;
    private TextView tv_sms_number;
    private EditText ed_phone_code;
    private Button btn_phone_code;
    private Button btn_pay_phone_code;

    private String phone;

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
                        btn_phone_code.setTextColor(getResources().getColor(R.color.orange_ff92));
                        btn_phone_code.setBackgroundResource(R.drawable.shape_white_w);
                        btn_phone_code.setEnabled(true);
                        btn_phone_code.setText(getResources().getString(R.string.forget_code));
                    }else{
                        btn_phone_code.setTextColor(getResources().getColor(R.color.white));
                        btn_phone_code.setBackgroundResource(R.drawable.shape_gray_cc);
                        btn_phone_code.setEnabled(false);
                        btn_phone_code.setText(countTime+"s后重发");
                        handler.sendEmptyMessageDelayed(msg_code,1000);
                        countTime--;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private int PayMoney=0;

    private String pkId;//用户所绑定银行卡id
    private String bankCode;//银行卡缩写 例如ICBC

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
        setContentView(R.layout.pay_send_sms);
        mContext = this;
        PayMoney = getIntent().getIntExtra("money", 0);
        phone = SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_userPhone);
        pkId = getIntent().getStringExtra("pId");
        bankCode = getIntent().getStringExtra("bCode");

        findAllviews();

}

    public void findAllviews(){
        img_btn_close = (ImageButton) findViewById(R.id.img_btn_close);
        tv_pay_money = (TextView) findViewById(R.id.tv_pay_money);
        tv_sms_number = (TextView) findViewById(R.id.tv_sms_number);
        ed_phone_code = (EditText) findViewById(R.id.ed_phone_code);
        btn_phone_code = (Button) findViewById(R.id.btn_phone_code);
        btn_pay_phone_code = (Button) findViewById(R.id.btn_pay_phone_code);

        img_btn_close.setOnClickListener(this);
        btn_phone_code.setOnClickListener(this);
        btn_pay_phone_code.setOnClickListener(this);

        tv_pay_money.setText(PayMoney+"元");

        String phone_y = PhoneUtils.getInstance().Phone2xing(phone);
        tv_sms_number.setText(phone_y);

        getSMSCodeQuest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_close:
                    finish();
//                overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_in);
                break;
            case R.id.btn_phone_code://发送验证码

                getSMSCodeQuest();
                break;
            case R.id.btn_pay_phone_code://验证码
                String code = ed_phone_code.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    ToastUtils.showShort("请输入验证码");
                }else{
                    setResult(RESULT_OK,new Intent().putExtra("code",code));
                    finish();
                }

                break;

        }
    }

    /**
     * 获取验证码
     */
    public void getSMSCodeQuest(){
        if (!PhoneUtils.getInstance().isPhone(this,phone))return;
        DMConstant.flagCode=true;
        btn_phone_code.setEnabled(false);
        handler.sendEmptyMessage(msg_code);
        final BaseRequestParams params = new BaseRequestParams();
        params.put("pkId", pkId);
        params.put("amount", ""+PayMoney);
        params.put("bankCode", bankCode);

        String url = UrlConstants.urlBase+UrlConstants.urlPaymentSendSms;
        MyLog.d("api_url=" + url + "_pkid=" + pkId+"_amount="+PayMoney+"_bankCode="+bankCode);
        DMApplication.getInstance().getHttpClient(this).post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    String resultDate = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nresult=" + resultDate);
                    if (statusCode == 200 && !RequestUtils.isHtml5ToString(resultDate)) {
                        BaseModel model = DMApplication.getInstance().getGson().fromJson(resultDate, new TypeToken<BaseModel<String>>() {
                        }.getType());
                        if (model.getStatus().equals(StateConstant.Status_success)) {
//                            ToastUtils.showShort(model.getMsg());

                        } else {
//                            ToastUtils.showShort("验证码发送失败");
                            ToastUtils.showShort(model.getMsg());
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

    @Override
    protected void onStop() {
        super.onStop();
        boolean safe = AntiHijackingUtil.checkActivity(this);
        if (!safe) ToastUtils.showLong("防盗号或欺骗，请不要输入密码");
    }

}
