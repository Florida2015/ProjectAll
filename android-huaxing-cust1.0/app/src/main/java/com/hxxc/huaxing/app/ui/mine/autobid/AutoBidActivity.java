package com.hxxc.huaxing.app.ui.mine.autobid;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.AutoInvestBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.InvestQueryBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.base.RxBasePresenter;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.ToastUtil;
import com.switchbutton.SwitchButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/23.
 * 自动投标页 填写信息
 */
public class AutoBidActivity extends BaseActivity {

    public final int CODE_choise_data =0x01;
    public final int CODE_choise_yeild_min =0x02;
    public final int CODE_choise_yeild_max =0x03;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.btn_switch_auto)
    SwitchButton btn_switch_auto;

    @BindView(R.id.btn_limit_yield)
    SwitchButton btn_limit_yield;

    @BindView(R.id.btn_use_cash)
    SwitchButton btn_use_cash;
    @BindView(R.id.btn_use_increase_interest)
    SwitchButton btn_use_increase_interest;
    @BindView(R.id.btn_switch_auto_limit)
    SwitchButton btn_switch_auto_limit;

    @BindView(R.id.relative_choise_data)
    RelativeLayout relative_choise_data;

    @BindView(R.id.tv_borrow_data)
    TextView tv_borrow_data;

    @BindView(R.id.relative_low_invest)
    RelativeLayout relative_low_invest;
    @BindView(R.id.relative_hight_invest)
    RelativeLayout relative_hight_invest;
    @BindView(R.id.tv_invest_low)
    TextView tv_invest_low;
    @BindView(R.id.tv_invest_high)
    TextView tv_invest_high;

    @BindView(R.id.ed_money_low)
    EditText ed_money_low;
    @BindView(R.id.ed_money_high)
    EditText ed_money_high;

    @BindView(R.id.btn_auto_submit)
    FancyButton btn_auto_submit;

    @Override
    public int getLayoutId() {
        return R.layout.bid_auto_write;
    }

    @Override
    public void initView() {
        toolbar_title.setText("自动投标");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getAutoInvestData();
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

    @OnClick({R.id.relative_choise_data,R.id.btn_auto_submit,R.id.relative_low_invest,R.id.relative_hight_invest})
    public void onClick(View view){
        //确认 自动投标 信息
        switch (view.getId()){
            case R.id.relative_choise_data:
                startActivityForResult(new Intent(mContext,ChoiseTermDataActivity.class).putExtra("index",index),CODE_choise_data);
                break;
            case R.id.btn_auto_submit://自动投标
                if (BtnUtils.isFastDoubleClick()){
                    RequestAutoBid();
                }
                break;
            case R.id.relative_low_invest://最低年化收益
                if (BtnUtils.isFastDoubleClick()){
                    startActivityForResult(new Intent(mContext,ChoiseInvestActivity.class).putExtra("text",yeild_min),CODE_choise_yeild_min);
                }
                break;
            case R.id.relative_hight_invest://最高年化收益
                if (BtnUtils.isFastDoubleClick()){
                    startActivityForResult(new Intent(mContext,ChoiseInvestActivity.class).putExtra("text",yeild_max),CODE_choise_yeild_max);
                }
                break;
        }
    }

    private int index=0;
    InvestQueryBean investQueryBean;
    String yeild_min = "";
    String yeild_max = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CODE_choise_data&&resultCode==RESULT_OK&&data!=null){
            //选择 期限
            investQueryBean = (InvestQueryBean) data.getSerializableExtra("index");
            LogUtil.d("选择期限="+investQueryBean.toString());
            index = investQueryBean.getId();
            tv_borrow_data.setText(investQueryBean.getRemarks());
        }

        if (requestCode==CODE_choise_yeild_min&&resultCode==RESULT_OK&&data!=null) {
            //选择年化收益

            yeild_min = data.getStringExtra("text");
            tv_invest_low.setText(yeild_min);
        }
        if (requestCode==CODE_choise_yeild_max&&resultCode==RESULT_OK&&data!=null) {
            //选择年化收益
            yeild_max = data.getStringExtra("text");
            tv_invest_high.setText(yeild_max);
        }
    }

    /**
     *
     */
    public void RequestAutoBid(){
        String money_low = ed_money_low.getText().toString().trim();
        String money_high = ed_money_high.getText().toString().trim();

        if (TextUtils.isEmpty(money_low)){
            ToastUtil.ToastShort(mContext,"请输入最小单笔投资金额");
            return;
        }
        if (TextUtils.isEmpty(money_high)){
            ToastUtil.ToastShort(mContext,"请输入最大单笔投资金额");
            return;
        }

        if (index<=0){
            ToastUtil.ToastShort(mContext,"请选择借款期限");
            return;
        }

        if (TextUtils.isEmpty(yeild_min)){
            ToastUtil.ToastShort(mContext,"请输入最低年化收益");
            return;
        }
        if (TextUtils.isEmpty(yeild_max)){
            ToastUtil.ToastShort(mContext,"请输入最高年化收益");
            return;
        }

        Api.getClient().getAutoInvest(Api.uid,btn_switch_auto_limit.isChecked()?"1":"0",money_low,money_high,""+index, (btn_limit_yield.isChecked()?"1":"0"),
                yeild_min,yeild_max,(btn_switch_auto.isChecked()?"1":"0")).
                compose(RxApiThread.convert()).subscribe(new BaseSubscriber<String>(mContext) {
            @Override
            public void onSuccess(String s) {
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.ToastShort(mContext,"自动投标成功");
                    AutoBidActivity.this.finish();
                }
            }

            @Override
            public void onFail(String fail) {
                if (!TextUtils.isEmpty(fail)) ToastUtil.ToastShort(mContext,"自动投标失败");
            }
        });
    }

    public void getAutoInvestData(){
        Api.getClient().getSelectAutoInvest(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<AutoInvestBean>(mContext) {
                    @Override
                    public void onSuccess(AutoInvestBean autoInvestBean) {
                        if (autoInvestBean==null)return;
                        initData(autoInvestBean);
                    }

                    @Override
                    public void onFail(String fail) {

                    }
                });
    }

    public void initData(AutoInvestBean bean){
        if (bean.getIsAutoInvestStatus()==1)btn_switch_auto.setChecked(true);
        else btn_switch_auto.setChecked(false);

        if (bean.getIsLimitInvestAmount()==1)btn_switch_auto_limit.setChecked(true);
        else btn_switch_auto_limit.setChecked(false);

        ed_money_low.setText(""+bean.getMinInvestAmount().doubleValue());
        ed_money_high.setText(""+bean.getMaxInvestAmount().doubleValue());

        index = bean.getLoanPeriod();

        tv_borrow_data.setText(TextUtils.isEmpty(bean.getRemarks())?"不限":bean.getRemarks());

        if (bean.getIsLimitYearRate()==1)btn_limit_yield.setChecked(true);
        else btn_limit_yield.setChecked(false);

        tv_invest_low.setText(DateUtil.getInstance().getOne(100*bean.getMinYearRate())+"%");
        tv_invest_high.setText(DateUtil.getInstance().getOne(100*bean.getMaxYearRate())+"%");

        yeild_min = ""+bean.getMinYearRate();
        yeild_max = ""+bean.getMaxYearRate();
    }

}
