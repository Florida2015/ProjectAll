package com.hxxc.huaxing.app.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.adapter.CommonAdapter;
import com.hxxc.huaxing.app.data.bean.AssetsBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.FundsItemBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.home.HomeActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.LoginActivity;
import com.hxxc.huaxing.app.utils.MoneyUtil;
import com.hxxc.huaxing.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

import static java.math.BigDecimal.ROUND_DOWN;

/**
 * Created by Administrator on 2016/9/22.
 * 我的资产
 */
public class MineFundsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_mine_sum_money)
    TextView tv_mine_sum_money;

    @BindView(R.id.listview_mine_funds)
    ListView listview;

    private FundsAdapter fundsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.mine_funds_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("我的资产");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        IntintData(null);
        RequestData();
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

    private String[] nameArray = new String[]{"可用余额","待收本金","待收利息","冻结资金","已赚收益"};
    private List<FundsItemBean> lists;

    public void IntintData(AssetsBean assetsBean){
        if (lists==null)lists = new ArrayList<FundsItemBean>();

        if (assetsBean==null){
            lists.clear();
            tv_mine_sum_money.setText("0.00");
            for (int i=0;i<nameArray.length;i++){
                FundsItemBean bean = new FundsItemBean();
                bean.setFundsName(nameArray[i]);
                bean.setFundsMoney("0.00");
                lists.add(bean);
            }
        }else{
            lists.clear();
            tv_mine_sum_money.setText(MoneyUtil.addComma(assetsBean.getExpectedReturn(),2,ROUND_DOWN));
            for (int i=0;i<nameArray.length;i++){
                FundsItemBean bean = new FundsItemBean();
                bean.setFundsName(nameArray[i]);
                if (i==0)  bean.setFundsMoney(MoneyUtil.addComma(assetsBean.getAvailablebal(),2,ROUND_DOWN));
                else if(i==1) bean.setFundsMoney(MoneyUtil.addComma(assetsBean.getCollectCorpus(),2,ROUND_DOWN));
                else if(i==2) bean.setFundsMoney(MoneyUtil.addComma(assetsBean.getCollectInterest(),2,ROUND_DOWN));
                else if(i==3) bean.setFundsMoney(MoneyUtil.addComma(assetsBean.getFrozbl(),2,ROUND_DOWN));
                else if(i==4) bean.setFundsMoney(MoneyUtil.addComma(assetsBean.getEarnedIncome(),2,ROUND_DOWN));
                lists.add(bean);
            }
        }

        fundsAdapter = new FundsAdapter(mContext,lists,R.layout.mine_funds_item);
        listview.setAdapter(fundsAdapter);
    }

    /**
     * 适配
     *
     */
    public class FundsAdapter extends CommonAdapter<FundsItemBean> {

        public FundsAdapter(Context context, List<FundsItemBean> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, FundsItemBean item, int position) {
            super.convert(helper, item, position);

            helper.setText(R.id.tv_funds_name,item.getFundsName());
            helper.setText(R.id.tv_funds_values,item.getFundsMoney());

        }
    }

    public void RequestData(){
        Api.getClient().getUserAssets(Api.uid).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<AssetsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                toast("数据异常");
            }

            @Override
            public void onNext(BaseBean<AssetsBean> stringBaseBean) {
                if (stringBaseBean.isSuccess()){

                    IntintData(stringBaseBean.getModel());

                }else {
                    toast(stringBaseBean.getErrMsg());
                    IntintData(null);
                    if (stringBaseBean.getStatusCode().equals(UserInfoConfig.http_error_out)){
                        ToastUtil.ToastShort(mContext,getResources().getString(R.string.text_login_out));
                        AppManager.getAppManager().ExitLogin();
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                }
            }

            });
    }


}
