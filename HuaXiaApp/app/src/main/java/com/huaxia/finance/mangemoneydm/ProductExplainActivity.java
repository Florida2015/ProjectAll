package com.huaxia.finance.mangemoneydm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UmengConstants;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.minedm.LoginActivity;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.ProductDetailModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.HashMap;

/**
 * 产品说明页
 * Created by houwen.lai on 2016/1/21.
 */
public class ProductExplainActivity extends BaseActivity implements View.OnClickListener{
    private final String mPageName = ProductExplainActivity.class.getSimpleName();

    private TextView tv_product_rate;//
    private TextView tv_product_sum_time;//封闭期
    private TextView tv_remainingNum;//剩余金额
    private TextView tv_bbin;//活动描述

    private LinearLayout relative_award;

    private TextView tv_product_detail_bg;//产品详情页
    private TextView tv_product_safe_bg;//安全保障页

    private TextView tv_income;//收益
    private TextView tv_income_extra;//额外收益

    private ImageButton img_btn_question_product;//问题

    private EditText ed_input_money;//输入金额
    private Button btn_pay_begin;//开始投资

    private double income_money=0;//预计收益
    private double activities_money=0;//活动收益
    private double money = 0;//投资金额

    private String produnctId="8996779735607201712097";

    private ProductDetailModel productDetailModel;
    private double yield=0;
    private double awardRate=0;
    private int period=1;

    private boolean flagInput=false;

    private long countTime=0;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==0x011){
                countTime=countTime-1000;
                if (countTime>1000){
                    String[] mt = DateUtils.getHourMinSec((int)(countTime/1000));
                    btn_pay_begin.setText(mt[0]+"小时"+mt[1]+"分"+mt[2]+"秒"+"后开售");
                    MyLog.d("api_product=" + countTime);
                    handler.sendEmptyMessageDelayed(0x011, 1000);
                }else{
                    btn_pay_begin.setEnabled(true);
                    btn_pay_begin.setText("开始投资");
                    btn_pay_begin.setTextColor(getResources().getColor(R.color.white));
                    btn_pay_begin.setBackgroundResource(R.drawable.btn_login_shape);
                }
            }
            super.handleMessage(msg);
        }
    };

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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0x011);
    }

    private static ProductExplainActivity instance=null;
    public static void finshLoginActivity(){
        if (instance!=null){
            instance.finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.product_explain);
        instance = this;
        produnctId = getIntent().getStringExtra("produnctId");
        money = getIntent().getIntExtra("money",0);
        if (TextUtils.isEmpty(produnctId)) MyLog.d("api_productId="+produnctId);

        findAllViews();

    }

    public void findAllViews(){
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("");
        img_btn_title_back.setOnClickListener(this);

        tv_product_rate = (TextView) findViewById(R.id.tv_product_rate);

        tv_product_sum_time = (TextView) findViewById(R.id.tv_product_sum_time);
        tv_remainingNum = (TextView) findViewById(R.id.tv_remainingNum);
        tv_bbin = (TextView) findViewById(R.id.tv_text_activiy);

        relative_award = (LinearLayout) findViewById(R.id.relative_award);

        tv_product_rate.setText("0.0");
        tv_product_sum_time.setText("");
        tv_remainingNum.setText("");
        tv_bbin.setText("");
//        String rate = "12.12%%";
//        SpannableString msp = new SpannableString(rate);
//        msp.setSpan(new AbsoluteSizeSpan(22,true), 0,rate.length()-2,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        msp.setSpan(new AbsoluteSizeSpan(12,true), rate.length()-2,rate.length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
//        // dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
//        tv_product_rate.setText(msp.toString());
//        tv_product_rate.setMovementMethod(LinkMovementMethod.getInstance());

        tv_product_detail_bg = (TextView) findViewById(R.id.tv_product_detail_bg);
        tv_product_safe_bg = (TextView) findViewById(R.id.tv_product_safe_bg);
        tv_product_detail_bg.setOnClickListener(this);
        tv_product_safe_bg.setOnClickListener(this);

        tv_income = (TextView) findViewById(R.id.tv_income);
        income_money = 0.00;
        tv_income.setText(DateUtils.getInstanse().getTwo(income_money) + "元");
        tv_income_extra = (TextView) findViewById(R.id.tv_income_extra);
        img_btn_question_product = (ImageButton) findViewById(R.id.img_btn_question_product);
        img_btn_question_product.setOnClickListener(this);
        ed_input_money= (EditText) findViewById(R.id.ed_input_money);
        ed_input_money.setText(money==0?"":""+DateUtils.getInstanse().getInt(money));
        ed_input_money.setSelection(ed_input_money.getText().toString().trim().length());

        btn_pay_begin= (Button) findViewById(R.id.btn_pay_begin);
        btn_pay_begin.setOnClickListener(this);

        ed_input_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if (s.length() == 0) {
                        income_money = 0.00;
                    } else {
                        money = Double.parseDouble(s.toString().trim());
                        income_money = money * (yield / 100) / 365 * period;
                    }
                    MyLog.d("api_incom_money=" + income_money);
                    MyLog.d("api_incom_money_double=" + DateUtils.getInstanse().getTwo(income_money));
                    tv_income.setText(DateUtils.getInstanse().getTwo(income_money) + "元");
//                    CountIncome();
                    if(!flagInput){
                        flagInput = true;
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("productId", produnctId + "");
                        map.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
                        map.put("productInputMoneyClick", "productInputMoneyClick");
                        MobclickAgent.onEvent(ProductExplainActivity.this, UmengConstants.huaxia_045, map);
                    }
                } catch (Exception e) {

                }
            }
        });
        flagInput=false;
        RequestDate();

    }

    public void CountIncome(){
        if (productDetailModel==null)return;
        if (productDetailModel.getAwardType()==1){//加本金

            activities_money = awardRate;

        }else if (productDetailModel.getAwardType()==2){//加利率
            activities_money = money*awardRate/365*productDetailModel.getPeriod();
        }else if (productDetailModel.getAwardType()==3){//实物
            activities_money=0;
        }else if (productDetailModel.getAwardType()==4){//乘利率
            activities_money = money*productDetailModel.getYield()*productDetailModel.getAwardRate()/365*productDetailModel.getPeriod();
        }
        income_money = money * (yield/100)/365*period;
        tv_income.setText(DateUtils.getInstanse().getTwo(income_money) + "元");

        tv_income_extra.setText(DateUtils.getInstanse().getTwo(activities_money) + "元");


    }


    public void InitDate(){
        if (productDetailModel==null)return;
        tv_title_bar.setText(productDetailModel.getProductName());
        yield = productDetailModel.getYield();
        awardRate = productDetailModel.getAwardRate();
        period = productDetailModel.getPeriod();

        tv_product_rate.setText(DateUtils.getInstanse().getTwo(productDetailModel.getYield()));

        StringBuffer product_time = new StringBuffer("封闭期\n").append(productDetailModel.getPeriod()).append("天");
        SpannableString msp = new SpannableString(StringsUtils.ToDBC(product_time.toString()));
        msp.setSpan(new AbsoluteSizeSpan(22,true), 0,product_time.toString().trim().length()-2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), product_time.toString().trim().length() - 2, product_time.toString().trim().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
        // dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        tv_product_sum_time.setText(productDetailModel.getPeriod()+"");
//        tv_product_sum_time.setMovementMethod(LinkMovementMethod.getInstance());

        StringBuffer remainingNum = new StringBuffer("剩余金额\n").append(StringsUtils.getMoneyGap(""+productDetailModel.getRemainingNum())).append("元");
        SpannableString mspp = new SpannableString(remainingNum);
        mspp.setSpan(new AbsoluteSizeSpan(22, true), 0, remainingNum.toString().trim().length() - 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mspp.setSpan(new AbsoluteSizeSpan(16, true), remainingNum.toString().trim().length() - 2, remainingNum.toString().trim().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
        // dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        tv_remainingNum.setText(StringsUtils.getMoneyGap("" + productDetailModel.getRemainingNum()));
//        tv_remainingNum.setMovementMethod(LinkMovementMethod.getInstance());

        if (!TextUtils.isEmpty(productDetailModel.getActivityAlias())){
            tv_bbin.setVisibility(View.VISIBLE);
            relative_award.setVisibility(View.VISIBLE);
            tv_bbin.setText(productDetailModel.getActivityAlias());
        }else {
            tv_bbin.setVisibility(View.GONE);
            relative_award.setVisibility(View.GONE);
        }

        ed_input_money.setHint("起投" + productDetailModel.getMinPrice());

        if (money==0){
            income_money = 0.00;
        }else {
            income_money = money * (yield/100)/365*period;
        }
        tv_income.setText(DateUtils.getInstanse().getTwo(income_money) + "元");

        StatusDate();
    }

    public void StatusDate(){
        if (productDetailModel==null)return;
        if (productDetailModel.getStatus()==2||productDetailModel.getStatus()==1){//在售
            if(productDetailModel.getProductStart()-productDetailModel.getServerTime() <0){
                btn_pay_begin.setEnabled(true);
                btn_pay_begin.setBackgroundResource(R.drawable.btn_login_shape);
            }else{
                countTime = productDetailModel.getProductStart()-productDetailModel.getServerTime();
                btn_pay_begin.setEnabled(false);
                btn_pay_begin.setBackgroundResource(R.drawable.shape_gray_c1c1);
                handler.sendEmptyMessage(0x011);
            }
        }else if(productDetailModel.getStatus()==3){//售磬
            btn_pay_begin.setEnabled(false);
            btn_pay_begin.setBackgroundResource(R.drawable.shape_gray_c1c1);
            btn_pay_begin.setText("售磬");
        }else {
            btn_pay_begin.setEnabled(false);
            btn_pay_begin.setBackgroundResource(R.drawable.shape_gray_c1c1);
            btn_pay_begin.setText("售磬");
        }
    }

    @Override
    public void onClick(View v) {
        HashMap<String,String> map = new HashMap<String,String>();
        switch (v.getId()){
            case R.id.img_btn_title_back://返回
                finish();
                break;//
            case R.id.img_btn_question_product://问题

                break;
            case R.id.tv_product_detail_bg://详情
                if (NetWorkUtils.isNetworkConnected(this)) {
                    map.put("productId",produnctId+"");
                    map.put("productName",productDetailModel!=null?productDetailModel.getProductName()+"":"null");
                    map.put("productDetailClick","productDetailClick");
                    MobclickAgent.onEvent(this, UmengConstants.huaxia_043, map);
                    startActivity(new Intent(ProductExplainActivity.this,ProductDetailActivity.class).putExtra("productId",produnctId));
    //                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else ToastUtils.showShort("网络不给力");
                break;
            case R.id.tv_product_safe_bg://安全
                if (NetWorkUtils.isNetworkConnected(this)) {
                    map.clear();
                    map.put("productId",produnctId+"");
                    map.put("productName",productDetailModel!=null?productDetailModel.getProductName()+"":"null");
                    map.put("productSafeClick","productSafeClick");
                    MobclickAgent.onEvent(this, UmengConstants.huaxia_044, map);
                    startActivity(new Intent(ProductExplainActivity.this,AgreetBondWebActivity.class).putExtra("url",UrlConstants.urlBase_web+UrlConstants.urlProductInsurance+produnctId).putExtra("title","安全保障页"));
    //                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);urlProductInsurance
                }else ToastUtils.showShort("网络不给力");
                break;
            case R.id.btn_pay_begin://开始投资
                if (NetWorkUtils.isNetworkConnected(this)) {
                    if (TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_token))) {
                        startActivity(new Intent(ProductExplainActivity.this, LoginActivity.class).putExtra("flagLogin", "payorder"));
                    } else {
                        DealPayDate();
                    }
                }else ToastUtils.showShort("网络不给力");
                break;
        }
    }

    public void DealPayDate(){
        try{
            if (TextUtils.isEmpty(ed_input_money.getText().toString().trim())){
                ToastUtils.showLong("请输入投资金额");
                return;
            }
            long paymoney = Long.parseLong(ed_input_money.getText().toString().trim());
            if (productDetailModel==null)return;
            if (paymoney<productDetailModel.getMinPrice()){
                ToastUtils.showLong("投资金额小于起投金额");
                return;
            }
            if (productDetailModel.getMaxPrice()>0&&paymoney>productDetailModel.getMaxPrice()){
                ToastUtils.showLong("投资金额大于最大金额");
                return;
            }
            if (paymoney>productDetailModel.getRemainingNum()){
                ToastUtils.showLong("投资金额大于可投金额");
                return;
            }

//            MyLog.d("api_step="+(double)(paymoney-productDetailModel.getMinPrice())%productDetailModel.getStepPrice());
            //步进
            if((double)(paymoney-productDetailModel.getMinPrice())%productDetailModel.getStepPrice()>0){
                ToastUtils.showLong("投资金额必须是"+productDetailModel.getStepPrice()+"的整数倍");
                return;
            }

            HashMap<String,String> map = new HashMap<String,String>();
            map.put("productId",produnctId+"");
            map.put("productName",productDetailModel!=null?productDetailModel.getProductName()+"":"null");
            map.put("productAmount",""+paymoney);
            map.put("productPayClick","productPayClick");
            MobclickAgent.onEvent(this, UmengConstants.huaxia_046, map);

            startActivity(new Intent(ProductExplainActivity.this, PayOrderActivity.class).putExtra("money",paymoney).putExtra("product",productDetailModel));
//                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }catch (Exception e){

        }
    }

    /**
     * 控件初始化
     */
    public void RequestDate(){
        BaseRequestParams params = new BaseRequestParams();
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlProduct).append(produnctId);
        MyLog.d("api_url="+url.toString());
        DMApplication.getInstance().getHttpClient(this).get(this, url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);
                if (statusCode == 200) {
                        BaseModel<ProductDetailModel> baseModel = DMApplication.getInstance().getGson().fromJson(resultDate,new TypeToken<BaseModel<ProductDetailModel>>(){}.getType());
//
                        if (baseModel.getStatus().equals(StateConstant.Status_success)){
                            productDetailModel = baseModel.getData();
                            InitDate();
                        }
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


}
