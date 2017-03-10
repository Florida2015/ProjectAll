package com.huaxia.finance.minedm;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.widget.DialogDoubleCommon;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.UserInfoModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

/**
 * 账号设置
 * Created by houwen.lai on 2016/2/18.
 */
public class AccountSetActivity extends BaseActivity implements View.OnClickListener {
    private final String mPageName = AccountSetActivity.class.getSimpleName();

    private RelativeLayout relative_approve_account;
    private ImageView img_approve;
    private TextView tv_approve_next;
    private TextView tv_approve_name;
    private TextView tv_approve_accout;
    private TextView tv_approve_phone;
    private RelativeLayout relative_update_pass;

    private Button btn_out_longin ;

    private Context mContext;
    private UserInfoModel userInfoModel;

    DialogDoubleCommon dialogDoubleCommon;

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
        setBaseContentView(R.layout.account_set);
        mContext = this;
        findAllViews();

    }

    public void findAllViews(){
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("账户设置");
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        relative_approve_account= (RelativeLayout) findViewById(R.id.relative_approve_account);
        tv_approve_next= (TextView) findViewById(R.id.tv_approve_next);
        tv_approve_name= (TextView) findViewById(R.id.tv_approve_name);
        img_approve= (ImageView) findViewById(R.id.img_approve);

        tv_approve_phone= (TextView) findViewById(R.id.tv_approve_phone);

        tv_approve_accout = (TextView) findViewById(R.id.tv_approve_accout);
        relative_update_pass= (RelativeLayout) findViewById(R.id.relative_update_pass);
        btn_out_longin = (Button) findViewById(R.id.btn_out_longin);

        relative_approve_account.setOnClickListener(this);
        relative_update_pass.setOnClickListener(this);
        btn_out_longin.setOnClickListener(this);

        setUserHttpRequest();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_update_pass://修改密码
                startActivity(new Intent(AccountSetActivity.this,SetPasswordActiviity.class).putExtra("flag",false));
                break;
            case R.id.btn_out_longin://安全退出
                LogoutDialog();

                break;
        }
    }

    public void LogoutDialog(){
        if (dialogDoubleCommon==null)
        dialogDoubleCommon = new DialogDoubleCommon(this);
        dialogDoubleCommon.setTitleText("您真的要退出登录吗？");
//        dialogDoubleCommon.setContxtText("您真的要退出登录吗？");
        dialogDoubleCommon.setLeftBtnText("取消");
        dialogDoubleCommon.setLeftBtnTextColor(getResources().getColor(R.color.blue_2299));
        dialogDoubleCommon.setRightBtnText("确认退出");
        dialogDoubleCommon.setRightBtnTextColor(getResources().getColor(R.color.blue_2299));
        dialogDoubleCommon.setLeftBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogDoubleCommon.onDimess();

            }
        });
        dialogDoubleCommon.setRightBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDoubleCommon.onDimess();
                DMApplication.isLoginFlag = false;
                DMApplication.token = "";
                SharedPreferencesUtils.getInstanse().putKeyValue(AccountSetActivity.this, UserConstant.key_token, "");
//                SharedPreferencesUtils.getInstanse().putKeyValue(LoginActivity.this, UserConstant.key_userPhone, phone);
                MenuTwoActivity.getInstance().setTab(0);
                AccountSetActivity.this.finish();
            }
        });
        dialogDoubleCommon.onShow();
    }

    public void initDate(){
        if (userInfoModel==null)return;
        if (userInfoModel.getAuthnameStatus().equals("0")){
            tv_approve_next.setText("已认证");
            tv_approve_next.setTextColor(getResources().getColor(R.color.orange_ff92));
            img_approve.setImageResource(R.drawable.icon_aprove_y);
//            Drawable drawable= getResources().getDrawable(R.drawable.icon_approve);
//            // 这一步必须要做,否则不会显示.
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            tv_approve_next.setCompoundDrawables(drawable,null, null, null);

//            tv_approve_name.setText("姓名\n"+userInfoModel.getUserName());
//            tv_approve_name.setTextColor(getResources().getColor(R.color.orange_ff92));
            SpannableString msp = new SpannableString("姓名\n"+userInfoModel.getUserName());
            // 设置字体前景色
            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_6666)), 0, 3,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色
            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_9999)), 3, msp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色
            tv_approve_name.setText(msp);
            tv_approve_name.setMovementMethod(LinkMovementMethod.getInstance());

            Drawable drawable_name= getResources().getDrawable(R.drawable.icon_head_y);
            /// 这一步必须要做,否则不会显示.
            drawable_name.setBounds(0, 0, drawable_name.getMinimumWidth(), drawable_name.getMinimumHeight());
            tv_approve_name.setCompoundDrawables(null, drawable_name, null, null);

//            tv_approve_accout.setText("身份证号\n" + PhoneUtils.getInstance().Cert2xing(userInfoModel.getCertNo()));
//            tv_approve_accout.setTextColor(getResources().getColor(R.color.orange_ff92));
            //PhoneUtils.getInstance().Cert2xing(userInfoModel.getCertNo())
            msp = new SpannableString("身份证号\n" + userInfoModel.getMaskCertNo());
            // 设置字体前景色
            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_6666)), 0, 5,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色
            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_9999)), 5, msp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色
            tv_approve_accout.setText(msp);
            tv_approve_accout.setMovementMethod(LinkMovementMethod.getInstance());

            Drawable drawable_cert= getResources().getDrawable(R.drawable.icon_card_y);
            /// 这一步必须要做,否则不会显示.
            drawable_cert.setBounds(0, 0, drawable_cert.getMinimumWidth(), drawable_cert.getMinimumHeight());
            tv_approve_accout.setCompoundDrawables(null, drawable_cert, null, null);

//            tv_approve_phone.setText("绑定手机\n" + userInfoModel.getPhone());
//            tv_approve_phone.setTextColor(getResources().getColor(R.color.orange_ff92));

            msp = new SpannableString("绑定手机\n" + userInfoModel.getPhone());
            // 设置字体前景色
            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_6666)), 0, 5,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色
            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_9999)), 5, msp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为洋红色
            tv_approve_phone.setText(msp);
            tv_approve_phone.setMovementMethod(LinkMovementMethod.getInstance());

            Drawable drawable_phone= getResources().getDrawable(R.drawable.icon_phone_n);
            /// 这一步必须要做,否则不会显示.
            drawable_phone.setBounds(0, 0, drawable_phone.getMinimumWidth(), drawable_phone.getMinimumHeight());
            tv_approve_phone.setCompoundDrawables(null, drawable_phone, null, null);

        } else{
            tv_approve_next.setText("未认证");
            tv_approve_next.setTextColor(getResources().getColor(R.color.black_9999));
            img_approve.setImageResource(R.drawable.icon_approve);
//            Drawable drawable= getResources().getDrawable(R.drawable.icon_approve_next_y);
            /// 这一步必须要做,否则不会显示.
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//            tv_approve_next.setCompoundDrawables(null, null, drawable, null);

            tv_approve_name.setText("姓名\n未认证");
            tv_approve_name.setTextColor(getResources().getColor(R.color.black_9999));
            Drawable drawable_name= getResources().getDrawable(R.drawable.icon_head_n);
            /// 这一步必须要做,否则不会显示.
            drawable_name.setBounds(0, 0, drawable_name.getMinimumWidth(), drawable_name.getMinimumHeight());
            tv_approve_name.setCompoundDrawables(null, drawable_name, null, null);

            tv_approve_accout.setText("身份证号\n未认证");
            tv_approve_accout.setTextColor(getResources().getColor(R.color.black_9999));
            Drawable drawable_cert= getResources().getDrawable(R.drawable.icon_card_n);
            /// 这一步必须要做,否则不会显示.
            drawable_cert.setBounds(0, 0, drawable_cert.getMinimumWidth(), drawable_cert.getMinimumHeight());
            tv_approve_accout.setCompoundDrawables(null, drawable_cert, null, null);

            SpannableString msp = new SpannableString("绑定手机\n未认证");
            tv_approve_phone.setTextColor(getResources().getColor(R.color.black_9999));
            tv_approve_phone.setText(msp);
            Drawable drawable_phone= getResources().getDrawable(R.drawable.icon_phone_y);
            /// 这一步必须要做,否则不会显示.
            drawable_phone.setBounds(0, 0, drawable_phone.getMinimumWidth(), drawable_phone.getMinimumHeight());
            tv_approve_phone.setCompoundDrawables(null, drawable_phone, null, null);
        }

    }

    public void setUserHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        String url = UrlConstants.urlBase+UrlConstants.urlAccountUserInfo;
        MyLog.d("api_=" + url);
        DMApplication.getInstance().getHttpClient(this).post(mContext,url,params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<UserInfoModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<UserInfoModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            userInfoModel = baseModel.getData();
                            SharedPreferencesUtils.getInstanse().putKeyValue(mContext, UserConstant.key_username,userInfoModel.getUserName());
                            SharedPreferencesUtils.getInstanse().putKeyValue(mContext,UserConstant.key_usercertNo,userInfoModel.getCertNo());
                            SharedPreferencesUtils.getInstanse().putKeyValue(mContext,UserConstant.key_userauthnamestatus,userInfoModel.getAuthnameStatus());
                            initDate();

                         }else  ToastUtils.showShort(baseModel.getMsg());
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
}
