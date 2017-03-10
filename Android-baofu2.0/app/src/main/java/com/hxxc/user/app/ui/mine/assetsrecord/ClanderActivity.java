package com.hxxc.user.app.ui.mine.assetsrecord;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.bean.PaymentBean;
import com.hxxc.user.app.data.bean.ClanderBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.MoneyUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2017/2/15.
 * 回款日历
 */

public class ClanderActivity extends BaseRxActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_cancel)
    TextView toolbar_cancel;

    @BindView(R.id.tv_sign_month)
    TextView tv_sign_month;

    @BindView(R.id.grid_view_sign)
    GridView grid_view_sign;


    @BindView(R.id.tv_month)
    TextView tv_month;

    @BindView(R.id.tv_return_principal_type)
    TextView tv_return_principal_type;//待回款本金
    @BindView(R.id.tv_return_principal)
    TextView tv_return_principal;//

    @BindView(R.id.tv_payment_interest)
    TextView tv_payment_interest;//待回款利息
    @BindView(R.id.tv_outstand_principal)
    TextView tv_outstand_principal;//已回款本金

    @BindView(R.id.tv_payment_interest_type)
    TextView  tv_payment_interest_type;//待回款利息
    @BindView(R.id.tv_interest_payment)
    TextView tv_interest_payment;//已回款利息

    SignAdapter signAdapter;
    List<ClanderBean> lists;

    @BindView(R.id.relative_principal)
    RelativeLayout relative_principal;
    @BindView(R.id.relative_interest)
    RelativeLayout relative_interest;

    private boolean flag;//是否首页 点击调转 日历

    @Override
    public int getLayoutId() {
        return R.layout.calendar_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("回款日历");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar_cancel.setVisibility(View.VISIBLE);
        toolbar_cancel.setTextSize(14);
        toolbar_cancel.setTextColor(getResources().getColor(R.color.blue_text));
        toolbar_cancel.setText("全部回款");

        if (getIntent().hasExtra("flagIndex"))
            flag = getIntent().getBooleanExtra("flagIndex",false);
        else flag = false;

        String end_time = getIntent().getStringExtra("end_time");
        LogUtil.d("clander_time="+end_time);

        tv_sign_month.setText(end_time);

        String endTimeMonthStr = getIntent().getStringExtra("endTimeMonthStr");
        LogUtil.d("endTimeMonthStr="+endTimeMonthStr);
        tv_month.setText(endTimeMonthStr+"月");

        getClanderDate(end_time);

        requestData(end_time);
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

    @OnClick(R.id.toolbar_cancel)
    public void onClick(View view){
        if (view.getId() == R.id.toolbar_cancel){
            if (flag){
                startActivity(new Intent(mContext, BackAssetsRecordActivity2.class));
                finish();
            }else {
                finish();
            }
        }
    }


    public void inintData(List<ClanderBean> mapList){
        if (lists==null)lists = new ArrayList<>();
        if (mapList==null)return;
        lists.clear();
//        for (int i=0;i<mapList.size();i++){
//            lists.add(mapList.get(i).get("0"));
//            lists.add(mapList.get(i).get("1"));
//            lists.add(mapList.get(i).get("2"));
//            lists.add(mapList.get(i).get("3"));
//            lists.add(mapList.get(i).get("4"));
//            lists.add(mapList.get(i).get("5"));
//            lists.add(mapList.get(i).get("6"));
//        }

        LogUtil.d("签到 -lists="+lists.toString());
        signAdapter = new SignAdapter(mContext,mapList,R.layout.mine_sign_item);
        grid_view_sign.setAdapter(signAdapter);

    }

    /**
     * 日历适配器
     */
    public class SignAdapter extends CommonAdapter<ClanderBean> {


        public SignAdapter(Context context, List<ClanderBean> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, ClanderBean item, int position) {
            super.convert(helper, item, position);
            TextView tv_sign_month = (TextView) helper.getView(R.id.tv_sign_month);
            ImageView img_sign = (ImageView) helper.getView(R.id.img_sign);
            if (item==null){
                tv_sign_month.setText("");
                tv_sign_month.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                img_sign.setVisibility(View.GONE);
                return;
            }
            tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.black_3333));
            tv_sign_month.setText(""+item.getTo_day_str());

            if (item.isToDay()){//是否今天
                tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_sign_month.setBackgroundResource(R.mipmap.tip_sign_now);
                img_sign.setVisibility(View.GONE);
            }
            else if (item.getProdInterest()!=null){
                tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_sign_month.setBackgroundResource(R.mipmap.tip_sign_reward);
                tv_sign_month.setText("回款");
                img_sign.setVisibility(View.GONE);
                img_sign.setImageResource(R.mipmap.tip_sign_correctly);
            }
// else  if (item.getIsExcessRewards()==1){
//                tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.white));
//                tv_sign_month.setText("奖励");
//                tv_sign_month.setBackgroundResource(R.mipmap.tip_sign_reward);
//                img_sign.setVisibility(View.GONE);
//            }
            else{
                tv_sign_month.setTextColor(mContext.getResources().getColor(R.color.black_3333));
                tv_sign_month.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                img_sign.setVisibility(View.GONE);
            }

        }
    }

    /**
     *
     *  请求日历数据
     */
    public void getClanderDate(String endTime){
        Api.getClient().getAccountCalenderList(Api.uid,endTime).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<ClanderBean>>(mContext) {
                    @Override
                    public void onSuccess(List<ClanderBean> list) {

                        inintData(list);

                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }
    /**
     * 获取某月份的本金 利息等
     */
    public void requestData(String time){

        Api.getClient().getAccountBackPaymentList(Api.uid,time).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<PaymentBean>(mContext) {
                    @Override
                    public void onSuccess(PaymentBean paymentBeen) {

                        setPayData(paymentBeen);

                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        setPayData(null);
                    }
                });
    }


    /**
     *
     */
    public void setPayData(PaymentBean paymentBeen){
        relative_principal.setVisibility(View.VISIBLE);
        relative_interest.setVisibility(View.VISIBLE);

        tv_return_principal_type.setText("待回款本金");//待回款本金

        tv_payment_interest_type.setText("待回款利息");//已回款本金

        if (paymentBeen==null){
            tv_return_principal.setText("0.00");//待回款利息
            tv_payment_interest.setText("0.00");//已回款利息
        }else {
            tv_return_principal.setText(MoneyUtil.addComma(paymentBeen.getPayCapitalCount(),2,ROUND_FLOOR));//待回款利息
            tv_payment_interest.setText(MoneyUtil.addComma(paymentBeen.getPayInterestCount(),2,ROUND_FLOOR));//已回款利息
        }

    }
}
