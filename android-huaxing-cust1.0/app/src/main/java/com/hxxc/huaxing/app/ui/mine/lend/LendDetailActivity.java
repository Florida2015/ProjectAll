package com.hxxc.huaxing.app.ui.mine.lend;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.adapter.FragmentAdapter;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.LendDetailItemBean;
import com.hxxc.huaxing.app.data.bean.LendItemBean;
import com.hxxc.huaxing.app.data.bean.OpenAccountBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.mine.account.WebOpenEaccountActivity;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/26.
 * 出借详情 页
 */

public class LendDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_lendName)
    TextView tv_lendName;
    @BindView(R.id.tv_lendData)
    TextView tv_lendData;
    @BindView(R.id.tv_lendType)
    TextView tv_lendType;

    @BindView(R.id.tab_layout_lend_detail)
    TabLayout tab_layout;

    @BindView(R.id.viewpager_lend_detail)
    ViewPager viewpager;

    @BindView(R.id.btn_credicard_person)
    FancyButton btn_credicard_person;

    private LendItemBean lendItemBean;

    @Override
    public int getLayoutId() {
        return R.layout.lend_detail_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("出借详情");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        lendItemBean = (LendItemBean) getIntent().getSerializableExtra("lenddetail");
        if(lendDetailItemBean!=null)   LogUtil.d("lend detail ="+lendDetailItemBean.toString());

//        IniteData();

        RequestLendDetail(lendItemBean.getOrderNo());

//        InitFragment();

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

    @OnClick(R.id.btn_credicard_person)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_credicard_person://申请债权转让
                if (BtnUtils.isFastDoubleClick()&&!TextUtils.isEmpty(lendItemBean.getOrderNo()))RequestData(lendItemBean.getOrderNo());
                break;
        }
    }

    /**
     *
     */
    public void IniteData(){
        if(lendDetailItemBean==null) return;

        LogUtil.d("lend detail ="+lendDetailItemBean.toString());

        if (lendDetailItemBean.getIsDebt()==0)btn_credicard_person.setVisibility(View.GONE);
        else btn_credicard_person.setVisibility(View.VISIBLE);

        tv_lendName.setText(TextUtils.isEmpty(lendDetailItemBean.getProductName())?"":lendDetailItemBean.getProductName());

        tv_lendData.setText("起息时间："+ DateUtil.getmstodate(lendDetailItemBean.getProfitTime(),DateUtil.YYYYMMDDHHMMSS));


        if (lendItemBean.getOrderStatus()==1){//投标
            tv_lendType.setTextColor(mContext.getResources().getColor(R.color.orange_BE7F));
            tv_lendType.setText(lendItemBean.getOrderStatusText());
        }else if (lendItemBean.getOrderStatus()==2){//还款中
            if (lendItemBean.getDebtStatus()==0){//还款中
                tv_lendType.setText(lendItemBean.getOrderStatusText());
                tv_lendType.setTextColor(mContext.getResources().getColor(R.color.orange_E6C6));
            }else{
                tv_lendType.setText(lendItemBean.getDebtStatusText());
                tv_lendType.setTextColor(mContext.getResources().getColor(R.color.red_FC6B));
            }
        }else if (lendItemBean.getOrderStatus()==3){//流标
            tv_lendType.setText(lendItemBean.getOrderStatusText());
            tv_lendType.setTextColor(mContext.getResources().getColor(R.color.orange_D7B3));
        }else if (lendItemBean.getOrderStatus()==4){//已结清
            if (lendItemBean.getDebtStatus()==0){//结清
                tv_lendType.setText(lendItemBean.getOrderStatusText());
                tv_lendType.setTextColor(mContext.getResources().getColor(R.color.black_aaaa));
            }else{
                tv_lendType.setText(lendItemBean.getDebtStatusText());
                tv_lendType.setTextColor(mContext.getResources().getColor(R.color.black_3333));
            }
        }

    }

    /**
     * tablayout适配
     */
    private String[] mTabs = new String[]{"出借状态","出借详情","回款记录"};
    List<Fragment> fragments = new ArrayList<Fragment>();

    public void InitFragment(){
        fragments.clear();
        fragments.add(LendStatusFragment.newInstance(lendDetailItemBean));//出借状态
        fragments.add(LendDetailFragment.newInstance(lendDetailItemBean.getLoanDetails()));//出借详情
        fragments.add(LendPaymentHistoryFragment.newInstance(lendDetailItemBean));//回款记录

        viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments, Arrays.asList(mTabs)));
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabsFromPagerAdapter(viewpager.getAdapter());

        tab_layout.setOnTabSelectedListener(this);
        viewpager.setOnPageChangeListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    LendDetailItemBean lendDetailItemBean;
    /**
     * 出借详情
     */
    public void RequestLendDetail(String orderNo){
        Api.getClient().getOrderInfoByOrderNoDetail(orderNo).compose(RxApiThread.convert()).
                subscribe(new Subscriber<BaseBean<LendDetailItemBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                toast("数据异常");
            }

            @Override
            public void onNext(BaseBean<LendDetailItemBean> stringBaseBean) {
                if (stringBaseBean.isSuccess()){
                    lendDetailItemBean = stringBaseBean.getModel();
                    IniteData();
                    InitFragment();
                }else{

                }
            }
        });

    }


    /**
     *申请债权转让
     */
    public void RequestData(String orderNo){
        if(TextUtils.isEmpty(orderNo))return;

        Api.getClient().getAppleCreditAssigment(Api.uid,orderNo).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<OpenAccountBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                toast("转让异常");
            }

            @Override
            public void onNext(BaseBean<OpenAccountBean> baseBean) {
                if (baseBean.isSuccess()){

                    if (!TextUtils.isEmpty(baseBean.getModel().getBaseUrl()))
                        startActivity(new Intent(LendDetailActivity.this,WebOpenEaccountActivity.class).
                                putExtra("url",baseBean.getModel().getBaseUrl()).putExtra("title","申请债权转让").
                                putExtra("data",baseBean.getModel().getHtmlStr()).putExtra("flag",false));

                }else{
                    if (!TextUtils.isEmpty(baseBean.getErrMsg())) toast(baseBean.getErrMsg());
                }
            }
        });


    }

}
