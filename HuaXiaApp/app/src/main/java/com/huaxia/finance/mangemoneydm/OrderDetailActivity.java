package com.huaxia.finance.mangemoneydm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.OrderDetailModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.switchbutton.SwitchButton;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

/**
 * 订单详情 页面
 * Created by houwen.lai on 2016/2/3.
 */
public class OrderDetailActivity extends BaseActivity {
    private final String mPageName = OrderDetailActivity.class.getSimpleName();

    private LinearLayout linearLayout_order_detail;

    private TextView tv_product_name_time;//花虾30天 第90期
    private TextView tv_order_time;//2015-09-09 13：22
    private TextView tv_order_success;
    private TextView tv_activities_alins;

    private TextView tv_order_id;//订单编号
    private TextView tv_order_date;//创建时间
    private TextView tv_order_money;//投资金额
    private TextView tv_yeild;//年化收益率
    private TextView tv_income;//预计收益
    private TextView tv_peroid;//封闭期
    private TextView tv_back_money;//到期还款
    private TextView tv_end_date;//到期日期
    private TextView tv_back_money_date;//到期还款日
    private TextView tv_back_money_bank;//还款至

    private TextView tv_add_mange_out;//费用
    private TextView tv_out_money;//提前退出费用
    private TextView tv_safe_way;//保障方式
    private TextView tv_agreement;//合同协议
    private TextView tv_group;//债权组合

    private String orderId;
    private OrderDetailModel orderDetailModel;

    private RelativeLayout relative_contuie;//奖励
    private TextView tv_continu_award;
    private TextView tv_order_award_next;
    private SwitchButton sb_android;


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
        RequestDate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.web_order_detail);
        orderId = getIntent().getStringExtra("orderId");

        initViews();

    }

    public void initViews(){
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right= (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title_bar.setText("订单详情");
        img_btn_title_right.setVisibility(View.GONE);

        linearLayout_order_detail = (LinearLayout) findViewById(R.id.linearLayout_order_detail);
        linearLayout_order_detail.setVisibility(View.INVISIBLE);

        tv_product_name_time = (TextView) findViewById(R.id.tv_product_name_time);
        tv_order_time = (TextView) findViewById(R.id.tv_order_time);
        tv_order_success= (TextView) findViewById(R.id.tv_order_success);
        tv_activities_alins= (TextView) findViewById(R.id.tv_activities_alins);

        tv_order_id= (TextView) findViewById(R.id.tv_order_id);
        tv_order_date= (TextView) findViewById(R.id.tv_order_date);
        tv_order_money= (TextView) findViewById(R.id.tv_order_money);
        tv_yeild= (TextView) findViewById(R.id.tv_yeild);

        tv_income= (TextView) findViewById(R.id.tv_income);
        tv_peroid= (TextView) findViewById(R.id.tv_peroid);
        tv_back_money= (TextView) findViewById(R.id.tv_back_money);
        tv_end_date= (TextView) findViewById(R.id.tv_end_date);
        tv_back_money_date= (TextView) findViewById(R.id.tv_back_money_date);
        tv_back_money_bank= (TextView) findViewById(R.id.tv_back_money_bank);

        relative_contuie= (RelativeLayout) findViewById(R.id.relative_contuie);
        tv_continu_award= (TextView) findViewById(R.id.tv_continu_award);
        tv_order_award_next= (TextView) findViewById(R.id.tv_order_award_next);
        sb_android= (SwitchButton) findViewById(R.id.sb_android);
        tv_activities_alins.setVisibility(View.GONE);
        sb_android.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            tv_continu_award.setTextColor(getResources().getColor(R.color.black));
            tv_continu_award.setText("本息续投");
            tv_order_award_next.setTextColor(getResources().getColor(R.color.black_9999));
            if (isChecked) {
                tv_order_award_next.setText("已开启");
                RequestContinueDate("1");//0到期退出；1续投花宝30天
            } else {
                tv_order_award_next.setText("已关启");
                RequestContinueDate("0");
            }

            }
        });
        relative_contuie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //续投奖励
                if (NetWorkUtils.isNetworkConnected(OrderDetailActivity.this)){
                    if(orderDetailModel.getIsConInvest()==-1){
                        startActivity(new Intent(OrderDetailActivity.this, ContinueOrderActivity.class).putExtra("model", orderDetailModel));
                    }
                } else ToastUtils.showShort("网络不给力");

            }
        });

        tv_add_mange_out= (TextView) findViewById(R.id.tv_add_mange_out);
        tv_out_money= (TextView) findViewById(R.id.tv_out_money);
        tv_safe_way= (TextView) findViewById(R.id.tv_safe_way);
        tv_agreement= (TextView) findViewById(R.id.tv_agreement);
        tv_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //花虾服务协议详情
                if (orderDetailModel != null && !TextUtils.isEmpty(orderDetailModel.getOrderId())) {
                    startActivity(new Intent(OrderDetailActivity.this, AgreetBondWebActivity.class).putExtra("url", UrlConstants.urlBase_web + UrlConstants.urlAgreementOrder + orderDetailModel.getOrderId()).putExtra("title", "花虾金融服务协议").putExtra("orderid", orderDetailModel.getOrderId()));
                }
            }
        });
        tv_group= (TextView) findViewById(R.id.tv_group);
        tv_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //债权组合详情
                if (orderDetailModel != null && !TextUtils.isEmpty(orderDetailModel.getOrderId())) {
                    startActivity(new Intent(OrderDetailActivity.this, AgreetBondWebActivity.class).putExtra("url", UrlConstants.urlBase_web + UrlConstants.urlAccountDebtPortfolio + orderDetailModel.getOrderId()).putExtra("title", "债权组合详情"));
                }
            }
        });

        RequestDate();
    }

    public void initDate(){
        if (orderDetailModel==null)return;

        tv_product_name_time.setText(new StringBuffer("[ ").append(orderDetailModel.getProductName()).append(" ] 第").append(orderDetailModel.getProductNum()).append("期").toString());

       if (orderDetailModel.getUpdateTime()>1000){
           tv_order_time.setText(DateUtils.getInstanse().getmstodated(orderDetailModel.getCreateTime(), DateUtils.YYYYMMDDHHMM_C));
       }else{
           tv_order_time.setText(DateUtils.getInstanse().getmstodated(orderDetailModel.getCreateTime(), DateUtils.YYYYMMDDHHMM_C));
       }
        tv_order_success.setText(orderDetailModel.getComments());

        if (orderDetailModel.getOrderStatus().equals("10")){
            tv_order_success.setText("订单处理中");
        }else if(orderDetailModel.getOrderStatus().equals("20")){
            tv_order_success.setText("支付成功");
        }else if(orderDetailModel.getOrderStatus().equals("21")){
            tv_order_success.setText("支付失败");
        }else if(orderDetailModel.getOrderStatus().equals("60")){
            tv_order_success.setText("已还款");
        }else if(orderDetailModel.getOrderStatus().equals("30")){
            tv_order_success.setText("计息中");
        }

        //奖励
        //花宝
        if((orderDetailModel.getOrderStatus().equals("20")||orderDetailModel.getOrderStatus().equals("30"))&&orderDetailModel.getProductStyle()==1){
            relative_contuie.setVisibility(View.VISIBLE);
            tv_continu_award.setVisibility(View.VISIBLE);
            tv_order_award_next.setVisibility(View.VISIBLE);
            sb_android.setVisibility(View.GONE);
            if (orderDetailModel.getIsConInvest()==-1){
                relative_contuie.setBackgroundColor(getResources().getColor(R.color.orange_fdf3));
                tv_continu_award.setTextColor(getResources().getColor(R.color.orange_ff92));
                tv_continu_award.setText("续投奖励0.2%逐月递增，最高奖励2.4%");
                tv_order_award_next.setTextColor(getResources().getColor(R.color.blue_3B96));
                tv_order_award_next.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
                tv_order_award_next.setText("点击查看");
                sb_android.setVisibility(View.GONE);
            }else if(orderDetailModel.getIsConInvest()==0){
                relative_contuie.setBackgroundColor(getResources().getColor(R.color.black_ecec));
                tv_continu_award.setTextColor(getResources().getColor(R.color.black));
                tv_continu_award.setText("本息续投");
                tv_order_award_next.setTextColor(getResources().getColor(R.color.black_9999));
                tv_order_award_next.setText("已关闭");
                tv_back_money_date.setText("T+2日");
                sb_android.setChecked(false);
                sb_android.setVisibility(View.VISIBLE);
            }else if(orderDetailModel.getIsConInvest()==1){
                relative_contuie.setBackgroundColor(getResources().getColor(R.color.black_ecec));
                tv_continu_award.setTextColor(getResources().getColor(R.color.black));
                tv_continu_award.setText("本息续投");
                tv_order_award_next.setTextColor(getResources().getColor(R.color.black_9999));
                tv_order_award_next.setText("已开启");
                tv_back_money_date.setText("T");
                sb_android.setChecked(true);
                sb_android.setVisibility(View.VISIBLE);
            }
        }else relative_contuie.setVisibility(View.GONE);

        relative_contuie.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(orderDetailModel.getActivityAlias())) {
            tv_activities_alins.setVisibility(View.VISIBLE);
            tv_activities_alins.setText(orderDetailModel.getActivityAlias());
        }else tv_activities_alins.setVisibility(View.INVISIBLE);

        tv_order_id.setText(orderDetailModel.getOrderId());
        tv_order_date.setText(DateUtils.getInstanse().getmstodated(orderDetailModel.getCreateTime(), DateUtils.YYYYMMDDHHMM_C));
        tv_order_money.setText("" + orderDetailModel.getOrderMoney() + "元");
        if(orderDetailModel.getAwardRate()>0){
            tv_yeild.setText(new StringBuffer(DateUtils.getInstanse().getTwo(orderDetailModel.getYield())).append("%+").append(DateUtils.getInstanse().getTwo(orderDetailModel.getAwardRate())).append("%"));
        }else tv_yeild.setText(new StringBuffer(DateUtils.getInstanse().getTwo(orderDetailModel.getYield())).append("%"));

        if (orderDetailModel.getAmount()>0){
            tv_income.setText(orderDetailModel.getTotalIncome()+"元+"+orderDetailModel.getAmount()+"元");
            tv_back_money.setText(DateUtils.getInstanse().getTwo(orderDetailModel.getOrderMoney() + orderDetailModel.getTotalIncome() + orderDetailModel.getAmount()) + "元");
        }else{
            tv_income.setText(orderDetailModel.getTotalIncome()+"元");
            tv_back_money.setText(DateUtils.getInstanse().getTwo(orderDetailModel.getOrderMoney() + orderDetailModel.getTotalIncome()) + "元");
        }

        tv_peroid.setText(orderDetailModel.getPeriod()+"天");

        tv_end_date.setText(DateUtils.getInstanse().getmstodated(orderDetailModel.getExpiryDate(), DateUtils.YYYYMMDD_C));
        tv_back_money_date.setText("T+2日");
        tv_back_money_bank.setText(orderDetailModel.getCardName()+" 尾号"+orderDetailModel.getCardNoLast());

        tv_add_mange_out.setText("加入费用：" + orderDetailModel.getEntryFee() + "%\n管理费用：" + orderDetailModel.getManagementFee() + "%\n退出费用：" + orderDetailModel.getReturnFee() + "%");
        tv_out_money.setText("提前退出费用" + orderDetailModel.getPreReturnFee() + "%");
        tv_safe_way.setText("风险准备金随时待命");

        SpannableStringBuilder msp = new SpannableStringBuilder("点击查看《花虾金融服务协议》");
        msp.setSpan(new ForegroundColorSpan(Color.argb(255,102,102,102)), 0, 4,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(Color.argb(255, 34, 153, 236)), 4, msp.toString().trim().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
        // dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        tv_agreement.setText(msp);
        tv_agreement.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableStringBuilder mspp = new SpannableStringBuilder("点击查看《债权组合详情》");
        mspp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_6666)), 0, 4,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mspp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue_2299)), 4, mspp.toString().trim().length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 第二个参数boolean
        // dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。
        tv_group.setText(mspp);
        tv_group.setMovementMethod(LinkMovementMethod.getInstance());
        linearLayout_order_detail.setVisibility(View.VISIBLE);
    }

    /**
     *
     */
    public void RequestDate(){
        BaseRequestParams params = new BaseRequestParams();
        params.put("orderId", ""+orderId);
        params.put("isFindResult", "false");
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlOrderInfo);
        MyLog.d("api="+url+"_orderId="+orderId);
        DMApplication.getInstance().getHttpClient(this).get(this, url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);
                if (statusCode == 200) {
                    BaseModel<OrderDetailModel> baseModel = DMApplication.getInstance().getGson().fromJson(resultDate,new TypeToken<BaseModel<OrderDetailModel>>(){}.getType());
                    if (baseModel.getStatus().equals(StateConstant.Status_success)){
                        MyLog.d("api_basemodel=" + baseModel.getData().toString());
                        orderDetailModel = baseModel.getData();
                        initDate();
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

    /**
     * 002: 用户下没有此订单
     040: 没有此用户
     003: 所选产品无法续投
     000: 保存成功
     050: 操作失败
     * 开启或关闭订单续投
     */
    public void RequestContinueDate(String style){
        BaseRequestParams params = new BaseRequestParams();
        params.put("orderId", ""+orderId);
        params.put("reBuyStyle", style);//0到期退出；1续投花宝30天
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlOrderSaveOrderRebuy);
        MyLog.d("api="+url+"_orderId="+orderId);
        DMApplication.getInstance().getHttpClient(this).get(this, url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);
                    if (statusCode == 200) {
                        BaseModel<String> baseModel = DMApplication.getInstance().getGson().fromJson(resultDate,new TypeToken<BaseModel<String>>(){}.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)){

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
