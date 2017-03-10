package com.hxxc.user.app.ui.order;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.adapter.FragmentAdapter;
import com.hxxc.user.app.data.bean.ContractBillNewBean;
import com.hxxc.user.app.data.bean.OrderDetailBean;
import com.hxxc.user.app.data.entity.TabEntity;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.mine.web.WebActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by houwen.lai on 2016/10/26.
 * 订单详情 页
 */

public class OrderDetailActivity extends BaseRxActivity implements ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener{

    public static final int Request = 0x0012;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_orderName)
    TextView tv_orderName;
    @BindView(R.id.tv_orderData)
    TextView tv_orderData;
    @BindView(R.id.tv_orderType)
    TextView tv_orderType;

//    @BindView(R.id.tab_layout_order_detail)
//    TabLayout tab_layout;
    @BindView(R.id.tab_layout_order_detail)
    CommonTabLayout cTabLayout;

    @BindView(R.id.viewpager_order_detail)
    ViewPager viewpager;

    @BindView(R.id.btn_continued)
    FancyButton btn_continued;

    private String orderNo;

    private String cridt_url;//债权

    @Override
    public int getLayoutId() {
        return R.layout.order_detail_activity_1;
    }

    @Override
    public void initView() {
        toolbar_title.setText("订单详情");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        orderNo = (String) getIntent().getStringExtra("orderNo");

        if(!TextUtils.isEmpty(orderNo))
            LogUtil.d("lend detail orderNo="+ orderNo.toString());


        tabLayoutInit();

        getOrderDetailData(orderNo);

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

    @OnClick(R.id.btn_continued)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_continued://立即续投奖50元红包
                if (BtnUtils.isFastDoubleClick()&&orderDetailBean_s!=null){
                    if (orderDetailBean_s.getContinuedInvestmentStatus()==1)
                    startActivityForResult(new Intent(OrderDetailActivity.this,OrderContinuedDialog.class).
                            putExtra("orderNo",orderNo).
                            putExtra("type",orderDetailBean_s.getOrderConInvestApp()==null?"":orderDetailBean_s.getOrderConInvestApp().getConInvestType()).
                            putExtra("pid",orderDetailBean_s.getOrderConInvestApp()==null?"":
                                    orderDetailBean_s.getOrderConInvestApp().getPid()+""),Request);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Request&&resultCode==RESULT_OK){
            if(!TextUtils.isEmpty(orderNo))
            getOrderDetailData(orderNo);

        }
    }

    /**
     * tablayout适配
     */
    OrderStatusFragment orderStatusfragment;
    OrderDetailFragment orderDetailFragment;
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private String[] mTitles = {"出借状态","出借详情"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    int postion =0;
    private void tabLayoutInit() {
//        if(orderDetailBean==null)return;

        orderStatusfragment = OrderStatusFragment.newInstance(null);//orderDetailBean
        orderDetailFragment = OrderDetailFragment.newInstance(null);//orderDetailBean.getOrderDefaultInfo()

        mFragments.clear();
        mTabEntities.clear();
        mFragments.add(orderStatusfragment);//  出借状态
        mFragments.add(orderDetailFragment);//  出借详情
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        cTabLayout.setIconVisible(false);
        cTabLayout.setTabData(mTabEntities);
        cTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewpager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {

                }
            }
        });

        viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), mFragments, Arrays.asList(mTitles)));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                cTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        cTabLayout.setCurrentTab(postion);
        viewpager.setCurrentItem(postion);
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
    OrderDetailBean orderDetailBean_s;

    public void getOrderDetailData(String orderNo){//HXXC54944012906
        if (TextUtils.isEmpty(orderNo))return;
        Api.getClient().getGetOrderDetail(Api.uid,orderNo).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<OrderDetailBean>(mContext) {
                    @Override
                    public void onSuccess(OrderDetailBean orderDetailBean) {
                        getContractBillNew(orderNo);

                        orderDetailBean_s = orderDetailBean;

                        btn_continued.setVisibility(View.VISIBLE);

                        tv_orderName.setText( orderDetailBean.getProductName()+" "+orderDetailBean.getCurrPeriod());


                        if(orderDetailBean.getOrderDefaultInfo()!=null){
                            tv_orderData.setText(DateUtil.getmstodate(orderDetailBean.getOrderDefaultInfo().getSignTime(),DateUtil.YYYYMMDDHHMMSS));
                        }

                        tv_orderType.setText(orderDetailBean.getOrderStatusText());

                        if (orderDetailBean.getContinuedInvestmentStatus()==1){
                            btn_continued.setEnabled(true);
                            btn_continued.setText(orderDetailBean.getActivityLabel());
                        }else {
                            btn_continued.setEnabled(false);
                            btn_continued.setFocusBackgroundColor(getResources().getColor(R.color.grey_aaaaaa));
                            btn_continued.setBackgroundColor(getResources().getColor(R.color.grey_aaaaaa));
                        }

                        btn_continued.setText(orderDetailBean.getActivityLabel());

                        if (orderStatusfragment!=null){
                            orderStatusfragment.setDetailData(orderDetailBean);
                        }
                        if (orderDetailFragment!=null){
                            orderDetailFragment.setDetailData(orderDetailBean);
                        }
                    }
                }
        );
    }

    /**
     * 获取债权 链接
     */
    public void getContractBillNew(String orderNo){
        if (TextUtils.isEmpty(orderNo))return;
        Api.getClient().getContractBillNew(Api.uid,orderNo).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<ContractBillNewBean>(mContext) {
                    @Override
                    public void onSuccess(ContractBillNewBean contractBillNewBean) {
                        //https://lcsitn.huaxiafinance.com/caifu-web-open-platform/contact/getContractFile?reqUrl=bill%2F2016111516295100001%2F2264online.pdf&token=35c19ad9c0f061c092c1f87c898caeb9241&uid=241&channel=android
                        if (contractBillNewBean!=null){
                            StringBuffer stringBuffer = new StringBuffer(HttpRequest.baseUrl);
                            stringBuffer.append(HttpRequest.httpContractFile).append("?reqUrl=").append(contractBillNewBean.getBillPath())
                                    .append("&token=").append(Api.token).append("&uid=").append(Api.uid).append("&channel=android");
                            LogUtil.d("债权url = "+stringBuffer.toString());
                            cridt_url = stringBuffer.toString();
                            if (orderStatusfragment!=null){//contractBillNewBean.getBillPath()
                                orderStatusfragment.setCridet_url(contractBillNewBean.getBillPath());//stringBuffer.toString()
                            }
                            if (orderDetailFragment!=null){//contractBillNewBean.getBillPath()
                                orderDetailFragment.setCridetUrl(contractBillNewBean.getBillPath());//stringBuffer.toString()
                            }
                        }else {
                            cridt_url = "";
                            if (orderStatusfragment!=null){
                                orderStatusfragment.setCridet_url("");
                            }
                            if (orderDetailFragment!=null){
                                orderDetailFragment.setCridetUrl("");
                            }
//                            ToastUtil.showShort("债权处理中！");
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        cridt_url = "";
                        if (orderStatusfragment!=null){
                            orderStatusfragment.setCridet_url("");
                        }
                        if (orderDetailFragment!=null){
                            orderDetailFragment.setCridetUrl("");
                        }
                    }
                });
    }

}
