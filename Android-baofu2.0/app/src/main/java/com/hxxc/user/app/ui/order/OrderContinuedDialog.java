package com.hxxc.user.app.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.FragmentAdapter;
import com.hxxc.user.app.data.bean.OrderContundBean;
import com.hxxc.user.app.data.entity.TabEntity;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by houwen.lai on 2016/11/10.
 * 订单详情  续投 对话框
 */

public class OrderContinuedDialog extends FragmentActivity implements UpDateLister{

    @BindView(R.id.btn_no_continued)
    TextView btn_no_continued;

    @BindView(R.id.cTabLayout_continued)
    CommonTabLayout cTabLayout;
    @BindView(R.id.viewpager_continued)
    ViewPager viewpager;

    @BindView(R.id.btn_order_contund_next)
    FancyButton btn_order_contund_next;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"本息续投", "本金续投"};//2  1
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    int postion =0;
    private final String type_zero ="1";
    private final String type_two ="2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_continued_investment);
        ButterKnife.bind(this);

        tabLayoutInit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            return true;
        }
        return false;
    }
    @OnClick({R.id.btn_no_continued,R.id.btn_no_close,R.id.btn_order_contund_next,R.id.view_continued})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_no_continued://不续投
                if (BtnUtils.isFastDoubleClick()){
                    setContinuedOrderRequest(getIntent().getStringExtra("orderNo"),"0","","");
                }
                break;
            case R.id.btn_no_close:
            case R.id.view_continued:
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.btn_order_contund_next://下一步
                if (BtnUtils.isFastDoubleClick()){
                    requestOrderContinued();
                }
                break;
        }
    }

    OrderContunedFragment orderContunedFragment_1;
    OrderContunedFragment orderContunedFragment_2;
    private void tabLayoutInit() {
        mFragments.clear();
        mTabEntities.clear();
//        for (String status : mStatus) {
//            mFragments.add(OrderFragment.newInstance(status));//  状态
//        }
        if (getIntent().hasExtra("type")&&getIntent().getIntExtra("type",0)==1){//1.本金续投
            orderContunedFragment_1 = OrderContunedFragment.newInstance(type_two,"");
            orderContunedFragment_2 = OrderContunedFragment.newInstance(type_zero,getIntent().getStringExtra("pid"));
        }else if (getIntent().hasExtra("type")&&getIntent().getIntExtra("type",0)==2){//2.本息续投
            orderContunedFragment_1 = OrderContunedFragment.newInstance(type_two,getIntent().getStringExtra("pid"));
            orderContunedFragment_2 = OrderContunedFragment.newInstance(type_zero,"");
        }else {
            orderContunedFragment_1 = OrderContunedFragment.newInstance(type_two,"");
            orderContunedFragment_2 = OrderContunedFragment.newInstance(type_zero,"");
        }

        mFragments.add(orderContunedFragment_1);//2 //本息续投
        mFragments.add(orderContunedFragment_2);//1 //本金续投
//        orderContunedFragment_1 = new OrderContunedFragment();
//        Bundle bundle_1 = new Bundle();
//        bundle_1.putString("type","2");
//        bundle_1.putString("pid",getIntent().getStringExtra("pid"));
//        orderContunedFragment_1.setArguments(bundle_1);
//        mFragments.add(orderContunedFragment_1);//本息续投

//        orderContunedFragment_2 = new OrderContunedTFragment();
//        Bundle bundle_2 = new Bundle();
//        bundle_2.putString("type","1");
//        bundle_2.putString("pid",getIntent().getStringExtra("pid"));
//        orderContunedFragment_2.setArguments(bundle_2);
//        mFragments.add(orderContunedFragment_2);//本息续投

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

        getContunedData(this,type_two);
        getContunedData(this,"0");
    }

    @Override
    public void setData(String status, String pid) {
        LogUtil.d("data=status="+status+"__pid="+pid);
        statuss = status;
        ppid = pid;
        if (status.equals(type_zero)){
            orderContunedFragment_1.initData(true,true);
        }else {
            orderContunedFragment_2.initData(true,true);
        }
    }

    String statuss ="";
    String ppid = "";
    public void requestOrderContinued(){
        if (TextUtils.isEmpty(statuss)||TextUtils.isEmpty(ppid)){
//            setContinuedOrderRequest(getIntent().getStringExtra("orderNo"),"0","","");
            ToastUtil.ToastShort(this,"请选择续投产品");
        }else {
            setContinuedOrderRequest(getIntent().getStringExtra("orderNo"),"1",statuss,ppid);
        }
    }

    /**
     * 续投
     * status        1.续投 0.取消续投
     */
    public void setContinuedOrderRequest(String orderNo,String status,String conInvestType,String pid){
        Api.getClient().getOrderContinued(Api.uid,orderNo,status,conInvestType,pid).compose(RxApiThread.convert()).
                subscribe(new Subscriber<BaseResult<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResult<String> baseBean) {
                if (baseBean.getSuccess()){
                    if(status.equals("0")){
                        ToastUtil.show(R.string.text_order_cancal_contine,5000);
                    }else{
                        //提示 续投设置成功
                        ToastUtil.show(R.string.text_order_detail,5000);
                    }
                    setResult(RESULT_OK);
                    finish();
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }else {//需要返回信息
                    if (!TextUtils.isEmpty(baseBean.getErrorMsg()))ToastUtil.ToastLong(OrderContinuedDialog.this,baseBean.getErrorMsg());
                    finish();
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
            }
        });

    }

    public void getContunedData(Context context, String type){
        Api.getClient().getGetXuTouList(type).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<OrderContundBean>>(context) {
                              @Override
                              public void onSuccess(List<OrderContundBean> orderContundBeen) {
                                    if (type.equals(type_two)){
                                        orderContunedFragment_1.setInitData(orderContundBeen);
                                    }else{
                                        orderContunedFragment_2.setInitData(orderContundBeen);
                                    }

                              }
                          }
                );
    }

}
