package com.hxxc.user.app.ui.mine.assetsrecord;

import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AccountInfo;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.widget.piechart_2.IPieElement;
import com.hxxc.user.app.widget.piechart_2.PieChart;
import com.hxxc.user.app.widget.piechart_2.TestEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/11/7.
 * 资产分析
 */

public class AssetsRecordActivity extends BaseRxActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_account_balance)
    TextView tv_account_balance;
//    @BindView(R.id.pieChart)
//    PieChart pieChart;
    @BindView(R.id.pieChart)
    PieChart pieChart;


    @BindView(R.id.tv_collect_principal_proportion)
    TextView tv_collect_principal_proportion;
    @BindView(R.id.tv_collect_principal)
    TextView tv_collect_principal;

    @BindView(R.id.tv_frozen_principal_proportion)
    TextView tv_frozen_principal_proportion;
    @BindView(R.id.tv_frozen_principal)
    TextView tv_frozen_principal;

    @BindView(R.id.tv_collect_income_proportion)
    TextView tv_collect_income_proportion;
    @BindView(R.id.tv_collect_income_principal)
    TextView tv_collect_income_principal;

    @BindView(R.id.tv_account_balance_proportion)
    TextView tv_account_balance_proportion;
    @BindView(R.id.tv_account_balance_principal)
    TextView tv_account_balance_principal;

//    private ArrayList<PieData> mPieDatas = new ArrayList<PieData>();
    // 颜色表
    private int[] mColors = {0xFF29cdb5,0xFFF1F1F1,0xFFE32636, 0xFFfbb02b, 0xFFffffff, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    AccountInfo accountInfo;

    @Override
    public int getLayoutId() {
        return R.layout.assets_record_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("资产分析");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        accountInfo = (AccountInfo) getIntent().getSerializableExtra("info");

        setDateInit(accountInfo);


        getAccountMessage();

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


    public void setDateInit( AccountInfo accountInfo){

        this.accountInfo = accountInfo;
        String sum_award = "0.00";

        if (accountInfo==null)sum_award="0.00";
        else sum_award = MoneyUtil.addComma(accountInfo.getTatalAmount(),2,ROUND_FLOOR);

        SpannableStringBuilder sp = new SpannableStringBuilder(""+sum_award+"\n账户总资产(元)");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, sum_award.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white_d2e5)), sum_award.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(22, true), 0, sum_award.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        sp.setSpan(new AbsoluteSizeSpan(12, true),sum_award.length(), sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_account_balance.setText(sp);

        if (accountInfo==null)initData(0.1f,0.1f);
        else initData(accountInfo.getFrozenAmount()==null?0.1f:accountInfo.getFrozenAmount().floatValue(),
                accountInfo.getPrincipalAmount()==null?0.1f:accountInfo.getPrincipalAmount().floatValue());

        if (accountInfo!=null&&accountInfo.getPrincipalAmount()!=null&&accountInfo.getPrincipalAmount().doubleValue()>0){
            double count = accountInfo.getFrozenAmount().doubleValue()+accountInfo.getPrincipalAmount().doubleValue();
            count = count<=0?1:count;
            tv_collect_principal_proportion.setVisibility(View.VISIBLE);
            tv_collect_principal_proportion.setText(DateUtil.getInstance().getInt(100*accountInfo.getPrincipalAmount().doubleValue()/count)+"%");
        }else {
            tv_collect_principal_proportion.setText("0%");
            tv_collect_principal_proportion.setVisibility(View.VISIBLE);
        }

        tv_collect_principal.setText(MoneyUtil.addComma(accountInfo==null? BigDecimal.valueOf(0.00):accountInfo.getPrincipalAmount(),2,ROUND_FLOOR)+"元");

        if (accountInfo!=null&&accountInfo.getFrozenAmount()!=null&&accountInfo.getFrozenAmount().doubleValue()>0){
            double count = accountInfo.getFrozenAmount().doubleValue()+accountInfo.getPrincipalAmount().doubleValue();
            count = count<=0?1:count;
            tv_frozen_principal_proportion.setVisibility(View.VISIBLE);
            tv_frozen_principal_proportion.setText(DateUtil.getInstance().getInt(100*accountInfo.getFrozenAmount().doubleValue()/count)+"%");
        }else {
            tv_frozen_principal_proportion.setText("0%");
            tv_frozen_principal_proportion.setVisibility(View.VISIBLE);
        }

        tv_frozen_principal.setText(MoneyUtil.addComma(accountInfo==null? BigDecimal.valueOf(0.00):accountInfo.getFrozenAmount(),2,ROUND_FLOOR)+"元");


    }

    private void initData(float value_1,float value_2){
//        if(mPieDatas==null)mPieDatas = new ArrayList<PieData>();
//
//        if(mPieDatas!=null&&mPieDatas.size()>0)
//        mPieDatas.clear();
//
//        PieData pieData = new PieData();
//        pieData.setName("区域");//accountInfo.getPrincipalAmount().floatValue()
//        pieData.setValue(value_1<=0?(float) 1:value_1);//(float)i+1
//        pieData.setColor(mColors[0]);
//        mPieDatas.add(pieData);
//
//        PieData pieData_1 = new PieData();
//        pieData_1.setName("区域");//accountInfo.getFrozenAmount().floatValue()
//        if (value_2>0) pieData_1.setValue(value_2);
//        else pieData_1.setValue(1f);
//        pieData_1.setColor(mColors[4]);
//        mPieDatas.add(pieData_1);
//
//        pieChart.setPieData(mPieDatas);

    TestEntity entity=new TestEntity(value_1<0.1f?0.1f:value_1,"#FF29cdb5");
    TestEntity entity1=new TestEntity(value_2<0.1f?0.1f:value_2,"#FFffffff");

    List<IPieElement> list=new ArrayList<>();
    list.add(entity);
    list.add(entity1);
    pieChart.setData(list);

    }

    /**
     * 获取账户的信息
     */
    public void getAccountMessage() {
        Api.getClient().getMyAccountInfo(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<AccountInfo>(mContext) {
                              @Override
                              public void onSuccess(AccountInfo accountInfo) {
                                  setDateInit(accountInfo);
                              }

                              @Override
                              public void onFail(String fail) {

                                  setDateInit(null);
                              }

                          }
                );
    }

}
