package com.huaxia.finance.mangemoneydm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.DateUtils;
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
import com.huaxia.finance.constant.UmengConstants;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.minedm.LoginActivity;
import com.huaxia.finance.model.BankItemModel;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.CashVoucherModel;
import com.huaxia.finance.model.OrderDetailModel;
import com.huaxia.finance.model.ProductDetailModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.List;

/**
 * 支付页面
 * Created by houwen.lai on 2016/1/25.
 */
public class PayOrderActivity extends BaseActivity implements View.OnClickListener{
    private final String mPageName = PayOrderActivity.class.getSimpleName();

    private Context mContext;

    private TextView tv_prodect_title;//产品描述
    private TextView tv_prodect_rate;//
    private TextView tv_prodect_count_time;//

    private RelativeLayout relative_add_bank;
    private TextView tv_bank_name;
    private TextView tv_bank_detail;
    private TextView tv_bank_next;

    private TextView tv_invest_money;
    private TextView tv_income_rate;
    private TextView tv_extra_rate;

    private EditText ed_input_money;

    private RelativeLayout relative_add_coupon;
    private TextView tv_coupon_name;
    private TextView tv_add_rate;

    private CheckBox check_box;
    private TextView tv_order_agreement;

    private Button btn_pay_order;

    private final int RequestCode_bank = 0x01;
    private final int RequestCode_coupon = 0x02;
    private final int RequestCode_SMS = 0x03;
    private final int RequestCode_Aprove = 0x04;

    private BankItemModel bankItemModel;

    private double money = 0;//投资金额
    private double income_money=0;//预计收益
    private double activities_money=0;//活动收益
    private long PayMoney=0;
    private double yield=0;
    private double awardRate=0;
    private int period=1;

    private ProductDetailModel productDetailModel;

    private CashVoucherModel cashVoucherModel;//优惠券
    private boolean flagClick=false;

    DialogDoubleCommon dialogDoubleCommon;

    private boolean flagMoneyClick=false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RequestCode_bank&&resultCode==RESULT_OK&&data!=null){
            MyLog.d("api_pay_back="+data.getSerializableExtra("bank"));
            bankItemModel = (BankItemModel) data.getSerializableExtra("bank");
            setBankInit();
        }
        if (requestCode==RequestCode_coupon&&resultCode==RESULT_OK&&data!=null){
            cashVoucherModel = (CashVoucherModel) data.getSerializableExtra("coupon");
            MyLog.d("api_coup_back=");
            if (cashVoucherModel!=null){
                flagClick=true;
                if (cashVoucherModel.getIsUseAble()==0){
                    tv_coupon_name.setTextColor(getResources().getColor(R.color.black_3333));
                    tv_add_rate.setTextColor(getResources().getColor(R.color.orange_ff92));
                }else{
                    tv_coupon_name.setTextColor(getResources().getColor(R.color.black_9999));
                    tv_add_rate.setTextColor(getResources().getColor(R.color.black_9999));
                }

                tv_add_rate.setText(cashVoucherModel.getName());

            }else {
                flagClick=true;
                tv_coupon_name.setTextColor(getResources().getColor(R.color.black_3333));
                tv_add_rate.setTextColor(getResources().getColor(R.color.black_6666));
//                tv_add_rate.setText("点击添加");
            }
        }

        if (requestCode==RequestCode_SMS&&resultCode==RESULT_OK&&data!=null){//短信
            //下单
            String code = data.getStringExtra("code");
            MyLog.d("api_sms_code="+code);
            if (cashVoucherModel!=null)
            setPayHttpRequest(bankItemModel.getPkId(),code,productDetailModel.getProductId(),cashVoucherModel.getPkid());
            else setPayHttpRequest(bankItemModel.getPkId(),code,productDetailModel.getProductId(),"null");
        }

        if (requestCode==RequestCode_Aprove&&resultCode==RESULT_OK) {//第一次绑卡认证
            startActivity(new Intent(PayOrderActivity.this,OrderListActvity.class));
            finish();

        }
    }

    public void setBankInit(){
        if (bankItemModel!=null){
            tv_bank_detail.setVisibility(View.VISIBLE);
            String bankNo = bankItemModel.getPkId();
            if (TextUtils.isEmpty(bankItemModel.getPkId())){
                tv_bank_name.setText(bankItemModel.getBankName());
                tv_bank_next.setText("换卡");
            }else {
                tv_bank_next.setText("换卡");
                tv_bank_name.setText(bankItemModel.getBankName()+" 尾号"+bankItemModel.getCardNo());
            }

            if (TextUtils.isEmpty(bankItemModel.getDailyLimit())){
                tv_bank_detail.setText(new StringBuffer("单笔限额").append(bankItemModel.getSingleLimit()).append("万,单日不限").toString());
            }else{
                tv_bank_detail.setText(new StringBuffer("单笔限额").append(bankItemModel.getSingleLimit()).append("万,单日").append(bankItemModel.getDailyLimit()).append("万").toString());
            }
        }else{
            tv_bank_name.setText("添加银行卡");
            tv_bank_detail.setVisibility(View.GONE);
            tv_bank_next.setText("点击添加");
        }
    }

    private static PayOrderActivity instance=null;
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
        setBaseContentView(R.layout.pay_order);
        mContext = this;
        instance = this;
        PayMoney = getIntent().getLongExtra("money",0);
        productDetailModel = (ProductDetailModel) getIntent().getSerializableExtra("product");
        if (productDetailModel!=null)MyLog.d("api_pay_order_product_="+productDetailModel.toString());
        findAllViews();

    }

    public void findAllViews(){
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("支付订单");
        img_btn_title_back.setOnClickListener(this);

        tv_prodect_title = (TextView) findViewById(R.id.tv_prodect_title);
        tv_prodect_rate = (TextView) findViewById(R.id.tv_prodect_rate);
        tv_prodect_count_time = (TextView) findViewById(R.id.tv_prodect_count_time);

        relative_add_bank = (RelativeLayout) findViewById(R.id.relative_add_bank);
        tv_bank_name = (TextView) findViewById(R.id.tv_bank_name);
        tv_bank_detail = (TextView) findViewById(R.id.tv_bank_detail);
        tv_bank_detail.setVisibility(View.GONE);
        tv_bank_next = (TextView) findViewById(R.id.tv_bank_next);

        ed_input_money = (EditText) findViewById(R.id.ed_input_money);
        ed_input_money.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                ed_input_money.setSelection(ed_input_money.getText().length());

            }
        });
        ed_input_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ed_input_money.setSelection(s.toString().length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ed_input_money.setSelection(s.toString().length());
                try {
                    if (s.length() == 0) {
                        income_money = 0.00;
                    } else {
                        PayMoney = Long.parseLong(s.toString().trim());
                        income_money = PayMoney * (yield / 100) / 365 * period;
                    }
                    tv_income_rate.setText(DateUtils.getInstanse().getTwo(income_money) + "元");

//                    activities_money = PayMoney*(awardRate/100);
//                    tv_extra_rate.setText(DateUtils.getInstanse().getDouble(activities_money) +"元");

                    flagClick=true;
                    tv_coupon_name.setTextColor(getResources().getColor(R.color.black_3333));
                    tv_add_rate.setTextColor(getResources().getColor(R.color.black_6666));
//                    tv_add_rate.setText("点击添加");
                    if (baseCoupModel!=null)
                        CoupDateInit(baseCoupModel.getData());


                    if (!flagMoneyClick){
                        flagMoneyClick=true;
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("productId", productDetailModel != null ?productDetailModel.getProductId():"null");
                        map.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
                        map.put("payMoneyChangeClick", "payMoneyChangeClick");
                        MobclickAgent.onEvent(PayOrderActivity.this, UmengConstants.huaxia_068, map);
                    }

                } catch (Exception e) {

                }
            }
        });

        tv_invest_money = (TextView) findViewById(R.id.tv_invest_money);
        tv_income_rate = (TextView) findViewById(R.id.tv_income_rate);
        tv_extra_rate = (TextView) findViewById(R.id.tv_extra_rate);

        relative_add_coupon = (RelativeLayout) findViewById(R.id.relative_add_coupon);
        tv_coupon_name = (TextView) findViewById(R.id.tv_coupon_name);
        tv_add_rate = (TextView) findViewById(R.id.tv_add_rate);

        check_box = (CheckBox) findViewById(R.id.check_box);
        tv_order_agreement = (TextView) findViewById(R.id.tv_order_agreement);

        btn_pay_order = (Button) findViewById(R.id.btn_pay_order);

        relative_add_bank.setOnClickListener(this);
        relative_add_coupon.setOnClickListener(this);
        btn_pay_order.setOnClickListener(this);
        tv_order_agreement.setOnClickListener(this);

        setUserHttpRequest();
        setCoupHttpRequest();
        InitDate();
    }

    public void InitDate(){
        if (productDetailModel==null)return;
        yield = productDetailModel.getYield();
        awardRate = productDetailModel.getAwardRate();
        period = productDetailModel.getPeriod();

        tv_prodect_title.setText(new StringBuffer("【").append(productDetailModel.getProductName()).append("】 第").append(productDetailModel.getProductNum()).append("期").toString());
        tv_prodect_rate.setText(DateUtils.getInstanse().getTwo(productDetailModel.getYield()));
        tv_prodect_count_time.setText(""+productDetailModel.getPeriod());

        ed_input_money.setText(DateUtils.getInstanse().getInt(PayMoney));
        ed_input_money.setSelection(DateUtils.getInstanse().getInt(PayMoney).length());

        income_money = PayMoney * (yield/100)/365*period;
        tv_income_rate.setText(DateUtils.getInstanse().getTwo(income_money) + "元");

//        activities_money = PayMoney*(yield/100)*(awardRate/100);
//        tv_extra_rate.setText(DateUtils.getInstanse().getDouble(activities_money) +"元");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_title_back:
                finish();
                break;
            case R.id.relative_add_bank://添加银行卡
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_token))){

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("productId", productDetailModel != null ?productDetailModel.getProductId():"null");
                    map.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
                    map.put("payChoiceBankClick", "payChoiceBankClick");
                    MobclickAgent.onEvent(PayOrderActivity.this, UmengConstants.huaxia_063, map);

                    startActivityForResult(new Intent(PayOrderActivity.this,ChoiceBackDialog.class),RequestCode_bank);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else{
                    startActivity(new Intent(PayOrderActivity.this, LoginActivity.class).putExtra("flagLogin","payorder"));
                }
                break;
            case R.id.relative_add_coupon://添加优惠券
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_token))){

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("productId", productDetailModel != null ?productDetailModel.getProductId():"null");
                    map.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
                    map.put("payCouponClick", "payCouponClick");
                    MobclickAgent.onEvent(PayOrderActivity.this, UmengConstants.huaxia_078, map);

                    startActivityForResult(new Intent(PayOrderActivity.this, CouponActivity.class).putExtra("baseMode",baseCoupModel).putExtra("productId",productDetailModel.getProductId()).putExtra("money", (int)PayMoney),RequestCode_coupon);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }else{
                    startActivity(new Intent(PayOrderActivity.this, LoginActivity.class).putExtra("flagLogin","payorder"));
                }
                break;
            case R.id.btn_pay_order://发短信验证码  下单
                if (BtnUtils.isFastDoubleClick()) MakeOrder();

                break;
            case R.id.tv_order_agreement://协议
                if (productDetailModel!=null&&!TextUtils.isEmpty(productDetailModel.getProductId()))
                startActivity(new Intent(PayOrderActivity.this, AgreetBondWebActivity.class).putExtra("title","出借咨询与服务协议").putExtra("url",UrlConstants.urlBase_web+UrlConstants.urlArgeementProduct+productDetailModel.getProductId()));
//                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                break;
        }
    }

    public void MakeOrder(){
        if (TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_token))){
            startActivity(new Intent(PayOrderActivity.this, LoginActivity.class).putExtra("flagLogin", "payorder"));
            return;
        }
        if (bankItemModel==null){
            ToastUtils.showShort("请选择银行卡");
            return;
        }

        if (PayMoney<productDetailModel.getMinPrice()){
            ToastUtils.showLong("投资金额小于起投金额");
            return;
        }
        if (productDetailModel.getMaxPrice()>0&&PayMoney>productDetailModel.getMaxPrice()){
            ToastUtils.showLong("投资金额大于最大金额");
            return;
        }
        if (PayMoney>productDetailModel.getRemainingNum()){
            ToastUtils.showLong("投资金额大于可投金额");
            return;
        }

        if (bankItemModel!=null&&PayMoney>10000*bankItemModel.getSingleLimit()){
            ToastUtils.showLong("投资金额大于单笔金额");
            return;
        }

        //步进
        if((double)(PayMoney-productDetailModel.getMinPrice())%productDetailModel.getStepPrice()>0){
            ToastUtils.showLong("投资金额必须是"+productDetailModel.getStepPrice()+"的整数倍");
            return;
        }
        if (!check_box.isChecked()){
            ToastUtils.showShort("请选中出借咨询与服务协议");
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("productId", productDetailModel != null ?productDetailModel.getProductId():"null");
        map.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
        map.put("payNextClick", "payNextClick");
        MobclickAgent.onEvent(PayOrderActivity.this, UmengConstants.huaxia_201, map);

        if (!flagClick){//提示选择优惠券
            dialogDoubleCommon = new DialogDoubleCommon(this);
            dialogDoubleCommon.setTitleText("提示");
            dialogDoubleCommon.setContxtText("此订单有未使用的优惠券，是否使用？");
            dialogDoubleCommon.setLeftBtnText("不使用");
            dialogDoubleCommon.setLeftBtnTextColor(getResources().getColor(R.color.blue_2299));
            dialogDoubleCommon.setRightBtnText("使用");
            dialogDoubleCommon.setRightBtnTextColor(getResources().getColor(R.color.blue_2299));
            dialogDoubleCommon.setLeftBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDoubleCommon.onDimess();
                    if (TextUtils.isEmpty(bankItemModel.getPkId())) {//去认证
                        HashMap<String, String> map_a = new HashMap<String, String>();
                        map_a.put("productId", productDetailModel != null ? productDetailModel.getProductId() : "null");
                        map_a.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
                        map_a.put("payOrderApproveClick", "payOrderApproveClick");
                        map_a.put("orderSrc", "3");
                        MobclickAgent.onEvent(PayOrderActivity.this, UmengConstants.huaxia_203, map_a);
                        startActivityForResult(new Intent(PayOrderActivity.this, ApproveActivity.class).putExtra("money", (int)PayMoney).putExtra("bank", bankItemModel).putExtra("product",productDetailModel).putExtra("vard", cashVoucherModel).putExtra("title", "确认支付"),RequestCode_Aprove);
                    } else {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("productId", productDetailModel != null ? productDetailModel.getProductId() : "null");
                        map.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
                        map.put("payOrderSmsNextClick", "payOrderSmsNextClick");
                        map.put("orderSrc", "3");
                        MobclickAgent.onEvent(PayOrderActivity.this, UmengConstants.huaxia_202, map);
                        startActivityForResult(new Intent(PayOrderActivity.this, PaySmsActivity.class).putExtra("money", (int)PayMoney).putExtra("pId", bankItemModel.getPkId()).putExtra("bCode", bankItemModel.getBankCode()), RequestCode_SMS);
                    }
                }
            });
            dialogDoubleCommon.setRightBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogDoubleCommon.onDimess();
                    startActivityForResult(new Intent(PayOrderActivity.this, CouponActivity.class).putExtra("baseMode",baseCoupModel).putExtra("productId",productDetailModel.getProductId()).putExtra("money", (int)PayMoney),RequestCode_coupon);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
            });
            dialogDoubleCommon.onShow();
        }else{
            if (TextUtils.isEmpty(bankItemModel.getPkId())) {//去认证
                HashMap<String, String> map_a = new HashMap<String, String>();
                map_a.put("productId", productDetailModel != null ? productDetailModel.getProductId() : "null");
                map_a.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
                map_a.put("payOrderApproveClick", "payOrderApproveClick");
                map_a.put("orderSrc", "3");
                MobclickAgent.onEvent(PayOrderActivity.this, UmengConstants.huaxia_203, map_a);
                startActivityForResult(new Intent(PayOrderActivity.this, ApproveActivity.class).putExtra("money",(int) PayMoney).putExtra("bank", bankItemModel).putExtra("product",productDetailModel).putExtra("vard", cashVoucherModel).putExtra("title", "确认支付"),RequestCode_Aprove);
            } else {
                HashMap<String, String> mapp = new HashMap<String, String>();
                mapp.put("productId", productDetailModel != null ? productDetailModel.getProductId() : "null");
                mapp.put("productName", productDetailModel != null ? productDetailModel.getProductName() + "" : "null");
                mapp.put("payOrderSmsNextClick", "payOrderSmsNextClick");
                mapp.put("orderSrc", "3");
                MobclickAgent.onEvent(PayOrderActivity.this, UmengConstants.huaxia_202, mapp);
                startActivityForResult(new Intent(PayOrderActivity.this, PaySmsActivity.class).putExtra("money", (int)PayMoney).putExtra("pId", bankItemModel.getPkId()).putExtra("bCode", bankItemModel.getBankCode()), RequestCode_SMS);
            }
        }
    }

    /**
     * 下单
     */
    public void setPayHttpRequest(String pkId,String smsCode,String productId,String varId){
        relative_no_work.setVisibility(View.VISIBLE);
        img_empty.setVisibility(View.GONE);
        tv_reloading.setVisibility(View.GONE);
        ballview.setVisibility(View.VISIBLE);

        BaseRequestParams params = new BaseRequestParams();
        params.put("pkId",pkId);//卡号
        params.put("smsCode",smsCode);//
        params.put("productId",productId);//要购买的产品id
        params.put("amount",""+PayMoney);//支付金额
        params.put("discountVoucherPkId",varId);//优惠券discountVoucherPkId
        params.put("orderSrc", "3");//来源 1.WAP 2.IOS 3.安卓 4.网站
        String url = UrlConstants.urlBase+UrlConstants.urlPaymentPay;
        MyLog.d("api_pay_url="+url+"_pkid="+pkId+"_smsCode="+smsCode+"_productId="+productId+"_amount="+PayMoney+"_discountVoucherPkId="+varId);
        DMApplication.getInstance().getHttpClient(this).post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                relative_no_work.setVisibility(View.GONE);
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<OrderDetailModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<OrderDetailModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            ToastUtils.showShort("下单成功");
//                            startActivity(new Intent(PayOrderActivity.this,OrderListActvity.class).putExtra("orderId",""));
                            ProductExplainActivity.finshLoginActivity();
                            MenuTwoActivity.getInstance().setTab(3);
                            startActivity(new Intent(PayOrderActivity.this, OrderListActvity.class).putExtra("type",0).putExtra("orderId",baseModel.getData().getOrderId()));
                            finish();
//                            setResult(RESULT_OK);
                        }else if(baseModel.getStatus().equals(StateConstant.Status_Order_040)){//处理中
//                            ToastUtils.showShort("订单处理中");
                            ProductExplainActivity.finshLoginActivity();
                            MenuTwoActivity.getInstance().setTab(3);
                            startActivity(new Intent(PayOrderActivity.this, OrderListActvity.class).putExtra("type",3).putExtra("orderId",baseModel.getData().getOrderId()));
                            finish();
                        }else if(baseModel.getStatus().equals(StateConstant.Status_Order_022)){
//                            ToastUtils.showShort("购买失败");
                            ProductExplainActivity.finshLoginActivity();
                            MenuTwoActivity.getInstance().setTab(3);
                            startActivity(new Intent(PayOrderActivity.this, OrderListActvity.class).putExtra("type",2).putExtra("orderId",baseModel.getData().getOrderId()));
                            finish();
                        }else if(baseModel.getStatus().equals(StateConstant.Status_Order_030)){//订单处理失败短信验证码输入错误
                            ToastUtils.showShort(baseModel.getMsg());
                        }else  ToastUtils.showShort(baseModel.getMsg());
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");
                relative_no_work.setVisibility(View.GONE);

            }
        });
    }
    /**
     * 用户银行卡列表
     */
    public void setUserHttpRequest() {
        BaseRequestParams params = new BaseRequestParams();
        params.put("payStatus","20");
        String url = UrlConstants.urlBase + UrlConstants.urlUserBankList;

        DMApplication.getInstance().getHttpClient(this).get(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<List<BankItemModel>> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<List<BankItemModel>>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            if (baseModel.getData() != null && baseModel.getData().size() > 0) {
                                bankItemModel = baseModel.getData().get(0);
                                setBankInit();
                            }
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
    private BaseModel<List<CashVoucherModel>> baseCoupModel;
    /**
     * 根据用户id和产品分类获取优惠券
     */
    public void setCoupHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("productId",productDetailModel.getProductId());//产品类型
        String url = UrlConstants.urlBase+UrlConstants.urlFindCashVoucher;
        MyLog.d("api_url="+url+"_productId="+productDetailModel.getProductId());
        DMApplication.getInstance().getHttpClient(this).get(mContext, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        baseCoupModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<List<CashVoucherModel>>>() {
                        }.getType());
                        if (baseCoupModel.getStatus().equals(StateConstant.Status_success)){
                            CoupDateInit(baseCoupModel.getData());
                        }else {
                            flagClick=true;
                            tv_add_rate.setText("无可用优惠券");
                        }
                    }else {
                        flagClick=true;
                        tv_add_rate.setText("无可用优惠券");
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

    public void CoupDateInit(List<CashVoucherModel> list){
        if (list==null||list.size()==0){
            flagClick=true;
            tv_add_rate.setText("无可用优惠券");
            return;
        }
        flagClick=true;
        int useCount = list.size();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getIsUseAble()==0){
                flagClick=false;
                break;
            }
        }
        if (useCount==0){
            flagClick=true;
            tv_add_rate.setText("无可用优惠券");
        } else tv_add_rate.setText("有"+useCount+"张可用优惠券");
    }
}
