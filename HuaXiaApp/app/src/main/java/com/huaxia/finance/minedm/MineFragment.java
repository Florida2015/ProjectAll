package com.huaxia.finance.minedm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.mangemoneydm.OrderListActvity;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.UserInfoModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

/**
 * 我的模块
 * Created by houwen.lai on 2016/1/19.
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    private String mPageName;
    int mNum;

    private TextView tv_user_phone;
    private TextView tv_user_income;
    private TextView tv_invest_money;
    private TextView tv_sum_income;
    private ImageView img_logos;

    private RelativeLayout relatice_my_order;
    private Button btn_income;
    private Button btn_expire;
    private Button btn_refund;
    private Button btn_refund_over;

    private RelativeLayout relative_bank;
    private RelativeLayout relative_trading_cashvour;
    private RelativeLayout relative_account;

    private UserInfoModel userInfoModel;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageEnd(mPageName);

        tv_user_phone = (TextView) getActivity().findViewById(R.id.tv_user_phone);
        tv_user_income = (TextView) getActivity().findViewById(R.id.tv_user_income);
        tv_invest_money = (TextView) getActivity().findViewById(R.id.tv_invest_money);
        tv_sum_income = (TextView) getActivity().findViewById(R.id.tv_sum_income);
        img_logos= (ImageView) getActivity().findViewById(R.id.img_logos);

        relatice_my_order = (RelativeLayout) getActivity().findViewById(R.id.relatice_my_order);
        btn_income = (Button) getActivity().findViewById(R.id.btn_income);
        btn_expire = (Button) getActivity().findViewById(R.id.btn_expire);
        btn_refund = (Button) getActivity().findViewById(R.id.btn_refund);
        btn_refund_over = (Button) getActivity().findViewById(R.id.btn_refund_over);

        relative_bank = (RelativeLayout) getActivity().findViewById(R.id.relative_bank);
        relative_trading_cashvour = (RelativeLayout) getActivity().findViewById(R.id.relative_trading_cashvour);
        relative_account = (RelativeLayout) getActivity().findViewById(R.id.relative_account);

        img_logos.setOnClickListener(this);
        tv_user_phone.setOnClickListener(this);
        relatice_my_order.setOnClickListener(this);

        btn_income.setOnClickListener(this);
        btn_expire.setOnClickListener(this);
        btn_refund.setOnClickListener(this);
        btn_refund_over.setOnClickListener(this);

        relative_bank.setOnClickListener(this);
        relative_trading_cashvour.setOnClickListener(this);
        relative_account.setOnClickListener(this);

        setUserHttpRequest();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mPageName = String.format("fragment %d", mNum);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            // Currently in a layout without a container, so no
            // reason to create our view.
            return null;
        }
        LayoutInflater myInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = myInflater.inflate(R.layout.fragment_mine, container,
                false);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_logos:
            case R.id.tv_user_phone:
            case R.id.relative_account://
                startActivity(new Intent(getActivity(), AccountSetActivity.class));
                break;
            case R.id.relatice_my_order://订单
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(getActivity(),UserConstant.key_token))){
                    startActivity(new Intent(getActivity(), OrderListActvity.class));
                }else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
            case R.id.btn_income://收益中
            case R.id.btn_expire://即将到期
            case R.id.btn_refund://还款中
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(getActivity(),UserConstant.key_token))){
                    startActivity(new Intent(getActivity(), OrderListActvity.class).putExtra("type",0));
                }else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
            case R.id.btn_refund_over://已还款
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(getActivity(),UserConstant.key_token))){
                    startActivity(new Intent(getActivity(), OrderListActvity.class).putExtra("type",1));
                }else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
            case R.id.relative_bank://银行卡
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(getActivity(),UserConstant.key_token))){
                    startActivity(new Intent(getActivity(),UserBankListActivity.class));
                }else {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
            case R.id.relative_trading_cashvour://优惠券
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(getActivity(),UserConstant.key_token))){
                    getActivity().startActivity(new Intent(getActivity(), CashVoucherActivity.class));
                }else {
                    getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
                }
                break;
        }
    }

    public void initDate(UserInfoModel model){
        if (model!=null){
            if (!TextUtils.isEmpty(model.getAuthnameStatus())&&model.getAuthnameStatus().equals("0"))tv_user_phone.setText(model.getUserName());
            else tv_user_phone.setText(model.getPhone());

            tv_user_income.setText(model.getTotalProfitStr());

            tv_invest_money.setText(""+model.getOrderMoney());
            tv_sum_income.setText(model.getAccumulatedIncome());

//            btn_income.setText("收益中("+model.getProfiting()+")");
//            btn_expire.setText("即将到期("+model.getWillend()+")");
//            btn_refund.setText("还款中(" + model.getRepayment() + ")");
//            btn_refund_over.setText("已还款(" + model.getRepaymented()+")");

            SpannableString msp = new SpannableString("收益中("+model.getProfiting()+")");
            // 设置字体颜色
            msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.black_9999)), 0, 4,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
            msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange_ff92)), 4, msp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
            btn_income.setText(msp);
            btn_income.setMovementMethod(LinkMovementMethod.getInstance());

            msp = new SpannableString("即将到期("+model.getWillend()+")");
            // 设置字体颜色
            msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.black_9999)), 0, 5,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
            msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange_ff92)), 5, msp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
            btn_expire.setText(msp);
            btn_expire.setMovementMethod(LinkMovementMethod.getInstance());

            msp = new SpannableString("还款中(" + model.getRepayment() + ")");
            // 设置字体颜色
            msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.black_9999)), 0, 4,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
            msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange_ff92)), 4, msp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
            btn_refund.setText(msp);
            btn_refund.setMovementMethod(LinkMovementMethod.getInstance());

            msp = new SpannableString("已还款(" + model.getRepaymented()+")");
            // 设置字体颜色
            msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.black_9999)), 0, 4,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
            msp.setSpan(new ForegroundColorSpan(getActivity().getResources().getColor(R.color.orange_ff92)), 4, msp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为
            btn_refund_over.setText(msp);
            btn_refund_over.setMovementMethod(LinkMovementMethod.getInstance());

        }
    }

    public void setUserHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        String url = UrlConstants.urlBase+UrlConstants.urlAccountUserInfo;
        MyLog.d("api_=" + url);
        DMApplication.getInstance().getHttpClient(getActivity()).post(getActivity(), url, params, new AsyncHttpResponseHandler() {
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
                            SharedPreferencesUtils.getInstanse().putKeyValue(getActivity(), UserConstant.key_username,userInfoModel.getUserName());
                            SharedPreferencesUtils.getInstanse().putKeyValue(getActivity(), UserConstant.key_userauthnamestatus, userInfoModel.getAuthnameStatus());
                            initDate(baseModel.getData());
                            if (MenuTwoActivity.getInstance().mPushAgent != null) {
                                MyLog.d("push_=_id--=" + MenuTwoActivity.getInstance().mPushAgent.getRegistrationId());
//                                MenuTwoActivity.getInstance().mPushAgent.addExclusiveAlias(baseModel.getData().getAccountId(), UserConstant.AliasHuaxia);
                                MenuTwoActivity.getInstance().mPushAgent.setExclusiveAlias(baseModel.getData().getAccountId(), UserConstant.AliasHuaxia);
                            }
                        }else if(baseModel.getStatus().equals(StateConstant.Status_login)){
                            DMApplication.isLoginFlag = false;
                            DMApplication.token = "";
                            SharedPreferencesUtils.getInstanse().putKeyValue(getActivity(), UserConstant.key_token, "");
                            MenuTwoActivity.getInstance().setTab(0);

//                            startActivity(new Intent(getActivity(),LoginActivity.class));
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
