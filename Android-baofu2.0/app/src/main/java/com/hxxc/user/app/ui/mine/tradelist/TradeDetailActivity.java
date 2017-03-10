package com.hxxc.user.app.ui.mine.tradelist;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.TradeRecordBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.MoneyUtil;

import butterknife.BindView;
import rx.Subscriber;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/11/15.
 * 我的 交易记录详情
 *
 */

public class TradeDetailActivity extends BaseRxActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_out_money)
    TextView tv_out_money;
    @BindView(R.id.tv_trade_type)
    TextView tv_trade_type;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_trade_number)
    TextView tv_trade_number;
    @BindView(R.id.tv_product_context)
    TextView tv_product_context;
    @BindView(R.id.tv_pay_mode)
    TextView tv_pay_mode;

    @BindView(R.id.tv_pay_mode_t)
    TextView tv_pay_mode_t;
    @BindView(R.id.tv_deductible_money_t)
    TextView tv_deductible_money_t;

    @BindView(R.id.tv_deductible_money)
    TextView tv_deductible_money;
    @BindView(R.id.tv_trade_money)
    TextView tv_trade_money;
    @BindView(R.id.tv_trade_tag)
    TextView tv_trade_tag;

    @BindView(R.id.tv_trade_order)
    TextView tv_trade_order;

    @BindView(R.id.relative_trade_namber)
    RelativeLayout relative_trade_namber;
    @BindView(R.id.relative_trade_order)
    RelativeLayout relative_trade_order;
    @BindView(R.id.relative_trade_product_detail)
    RelativeLayout relative_trade_product_detail;
    @BindView(R.id.relative_trade_account)
    RelativeLayout relative_trade_account;
    @BindView(R.id.relative_red_deductible)
    RelativeLayout relative_red_deductible;
    @BindView(R.id.relative_trade_money)
    RelativeLayout relative_trade_money;
    @BindView(R.id.relative_trade_remarks)
    RelativeLayout relative_trade_remarks;


    @Override
    public int getLayoutId() {
        return R.layout.trade_detail_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("交易详情");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getTradeDetail(getIntent().getStringExtra("id"));
//        initData();
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initData(){

     tv_out_money.setText("");//
     tv_trade_type.setText("");//
     tv_create_time.setText("");//
     tv_trade_number.setText("");//
     tv_product_context.setText("");//
     tv_pay_mode.setText("");//
     tv_deductible_money.setText("");//
     tv_trade_money.setText("");//
     tv_trade_tag.setText("");//

    }

    private void getTradeDetail(String id){
        Api.getClient().getGetTransactionFlowing(Api.uid,id).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseResult<TradeRecordBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                toast("数据异常");
            }

            @Override
            public void onNext(BaseResult<TradeRecordBean> baseBean) {
                if (baseBean.getSuccess()){
                    tv_out_money.setText(MoneyUtil.addComma(baseBean.getData().getPayMoney(),2,ROUND_FLOOR)+"元");//
                    if (baseBean.getData().getTradeType()==1){//收入
//                        tv_trade_type.setText("收入");//
                        tv_deductible_money_t.setText("退出费用");//
                        setRelativeBackground(false);
                    }else {//2支出
//                        tv_trade_type.setText("支出");//
                        tv_deductible_money_t.setText("红包抵扣");//
                        setRelativeBackground(true);
                    }
                    tv_trade_type.setText(baseBean.getData().getTradeTypeText());//
//                    if (baseBean.getData().getType().contains("pay")){//支出
//                        tv_trade_tag.setText("出借");//
//                    }else if (baseBean.getData().getType().contains("profit")){
//                        tv_trade_tag.setText("利息收入");//
//                    }else if (baseBean.getData().getType().contains("principal")){
//                        tv_trade_tag.setText("本息赎回");//
//                    }
                    tv_trade_tag.setText(baseBean.getData().getTypeText());//

                    tv_create_time.setText(DateUtil.getmstodate(baseBean.getData().getCreateTime(),DateUtil.YYYYMMDDHHMMSS));//
                    tv_trade_number.setText(baseBean.getData().getFlowing());//
                    tv_trade_order.setText(baseBean.getData().getOrderNo());
                    tv_product_context.setText(baseBean.getData().getDescriptions());//

                    tv_pay_mode.setText(baseBean.getData().getBankAndCard());//

                    tv_deductible_money.setText(MoneyUtil.addComma(baseBean.getData().getAdditionalMoney(),2,ROUND_FLOOR)+"元");//

                    tv_trade_money.setText(MoneyUtil.addComma(baseBean.getData().getMoney(),2,ROUND_FLOOR)+"元");//


                }else {
                    toast(baseBean.getErrorMsg());
            }
            }
        });
    }

    public void setRelativeBackground(boolean flag){
        if (flag){
            relative_trade_namber.setVisibility(View.VISIBLE);
            relative_trade_namber.setBackgroundColor(getResources().getColor(R.color.white));
            relative_trade_order.setBackgroundColor(getResources().getColor(R.color.white_fafa));
            relative_trade_product_detail.setBackgroundColor(getResources().getColor(R.color.white));
            relative_trade_account.setBackgroundColor(getResources().getColor(R.color.white_fafa));
            relative_red_deductible.setBackgroundColor(getResources().getColor(R.color.white));
            relative_trade_money.setBackgroundColor(getResources().getColor(R.color.white_fafa));
            relative_trade_remarks.setBackgroundColor(getResources().getColor(R.color.white));
        }else{
            relative_trade_namber.setVisibility(View.GONE);
            relative_trade_namber.setBackgroundColor(getResources().getColor(R.color.white));
            relative_trade_order.setBackgroundColor(getResources().getColor(R.color.white));
            relative_trade_product_detail.setBackgroundColor(getResources().getColor(R.color.white_fafa));
            relative_trade_account.setBackgroundColor(getResources().getColor(R.color.white));
            relative_red_deductible.setBackgroundColor(getResources().getColor(R.color.white_fafa));
            relative_trade_money.setBackgroundColor(getResources().getColor(R.color.white));
            relative_trade_remarks.setBackgroundColor(getResources().getColor(R.color.white_fafa));
        }
    }
}
