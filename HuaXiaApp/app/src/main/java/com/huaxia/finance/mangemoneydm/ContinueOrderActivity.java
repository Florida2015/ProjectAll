package com.huaxia.finance.mangemoneydm;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.DateUtils;
import com.framwork.Utils.MyLog;
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
import com.huaxia.finance.widgetutils.dialog.ContinueRulesDialogFragment;

import org.apache.http.Header;

/**
 * 续投
 * Created by houwen.lai on 2016/4/8.
 */
public class ContinueOrderActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_continu_award;//[花宝30天]第195期
    private TextView tv_contine_money;//投资金额（元）
    private TextView tv_continu_income;//累计预期收益（元）
    private TextView tv_close_date;//封闭期结束日期
    private TextView tv_remain_day;//剩余0天
    private TextView tv_look_rules;//查看规则?
    private TextView tv_award_text;//奖励 描述
    private Button btn_continue;//续投
    private CheckBox cb_agrement;//
    private TextView tv_contine_agrement;

    private OrderDetailModel orderDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.continue_order_activity);
        orderDetailModel = (OrderDetailModel) getIntent().getSerializableExtra("model");
        initDate();
    }

    public void initDate(){
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
        tv_title_bar.setText("续投");
        img_btn_title_right.setVisibility(View.GONE);

        tv_continu_award = (TextView)findViewById(R.id.tv_continu_award);
        tv_contine_money = (TextView)findViewById(R.id.tv_contine_money);
        tv_continu_income = (TextView)findViewById(R.id.tv_continu_income);
        tv_close_date = (TextView)findViewById(R.id.tv_close_date);
        tv_remain_day = (TextView)findViewById(R.id.tv_remain_day);
        tv_look_rules = (TextView)findViewById(R.id.tv_look_rules);
        tv_award_text = (TextView)findViewById(R.id.tv_award_text);
        btn_continue = (Button)findViewById(R.id.btn_continue);
        cb_agrement = (CheckBox)findViewById(R.id.cb_agrement);
        tv_contine_agrement = (TextView)findViewById(R.id.tv_contine_agrement);
        tv_look_rules.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        tv_contine_agrement.setOnClickListener(this);

        tv_look_rules.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tv_look_rules.setText("查看规则?");

        tv_continu_award.setText(new StringBuffer("[ ").append(orderDetailModel.getProductName()).append(" ] 第").append(orderDetailModel.getProductNum()).append("期").toString());
        tv_contine_money.setText(""+orderDetailModel.getOrderMoney());
        tv_continu_income.setText(DateUtils.getInstanse().getTwo(orderDetailModel.getTotalIncome() + orderDetailModel.getAmount()));
        tv_close_date.setText("封闭期结束日期:"+DateUtils.getInstanse().getmstodated(orderDetailModel.getCreateTime(), DateUtils.YYYYMMDD_D));
        tv_remain_day.setText("剩余"+orderDetailModel.getRemainDate()+"天");
        tv_award_text.setText("奖励按0.2%逐期递增，最高加息2.4%，最高收益率9.4%");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_title_back:
                finish();
                break;
            case R.id.tv_contine_agrement://协议
            //花虾服务协议详情
                if (orderDetailModel != null && !TextUtils.isEmpty(orderDetailModel.getOrderId())) {
                    startActivity(new Intent(ContinueOrderActivity.this, AgreetBondWebActivity.class).putExtra("url", UrlConstants.urlBase_web + UrlConstants.urlAgreementProduct + orderDetailModel.getProductId()).putExtra("title", "花虾金融服务协议").putExtra("orderid", ""));
                }

                break;
            case R.id.tv_look_rules://查看规则?
                ContinueRulesDialogFragment continueRulesDialogFragment = new ContinueRulesDialogFragment().newInstance("30天产品续投加息，按0.2%逐期递增，最高至续投一年不再递增，最高续投加息2.4%，最高收益率9.4%");
                continueRulesDialogFragment.show(getSupportFragmentManager(), "updateAppDialogFragment");

                break;
            case R.id.btn_continue://续投
                if (BtnUtils.isFastDoubleClick()&&orderDetailModel!=null){
                    if (cb_agrement.isChecked())RequestContinueDate(orderDetailModel.getOrderId());
                    else ToastUtils.showShort("请选择同意协议");
                }
                break;
        }
    }

    /**
     * 002: 用户下没有此订单
     040: 没有此用户
     003: 所选产品无法续投
     000: 保存成功
     050: 操作失败
     * 开启或关闭订单续投
     */
    public void RequestContinueDate(String orderId){
        BaseRequestParams params = new BaseRequestParams();
        params.put("orderId", ""+orderId);
        params.put("reBuyStyle", "1");//0到期退出；1续投花宝30天
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlOrderSaveOrderRebuy);
        MyLog.d("api=" + url + "_orderId=" + orderId);
        DMApplication.getInstance().getHttpClient(this).get(this, url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);
                    if (statusCode == 200) {
                        BaseModel<String> baseModel = DMApplication.getInstance().getGson().fromJson(resultDate,new TypeToken<BaseModel<String>>(){}.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)){
                            finish();
                        }else ToastUtils.showShort(baseModel.getMsg());
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
