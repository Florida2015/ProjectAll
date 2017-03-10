package com.hxxc.user.app.ui.product;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.HelpCenterBean;
import com.hxxc.user.app.bean.ProductInfo;
import com.hxxc.user.app.contract.ProductDetailVertical2V;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.adapter.ProductDetailVertical2Adapter;
import com.hxxc.user.app.ui.base.BaseFragment2;

import java.util.List;

import static com.hxxc.user.app.contract.presenter.RxBasePresenter.mApiManager;


/**
 * Created by chenqun on 2016/9/23.
 * 产品详情 页
 */

public class VerticalFragment2 extends BaseFragment2 implements ProductDetailVertical2V {

    private ViewPager mViewPager;
    private ProductDetailVertical2Adapter mAdapter;
    private ProductInfo mDatas;
    private String mPid;
    private String mProblemType;

    public static VerticalFragment2 newInstance() {
        Bundle arguments = new Bundle();
        VerticalFragment2 fragment = new VerticalFragment2();
        fragment.setArguments(arguments);
        return fragment;
    }
    @Override
    protected int getContentViewID() {
        return R.layout.fragment_vertical2;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        TabLayout mTabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        mAdapter = new ProductDetailVertical2Adapter(getActivity().getSupportFragmentManager(),this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mAdapter.getCount()-1);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mAdapter.onPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getProblemTypes();
    }

    public void initData(ProductInfo datas, String pid) {
        //TODO 获取数据并设置数据
        mDatas = datas;
        mPid = pid;
        mAdapter.reset();
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.onPagerSelected(mViewPager.getCurrentItem());
            }
        });
    }

    private void getProblemTypes() {
        mApiManager.getDictList(Constants.Type_ChuJie, new SimpleCallback<List<HelpCenterBean>>() {


            @TargetApi(Build.VERSION_CODES.DONUT)
            @Override
            public void onNext(List<HelpCenterBean> helpCenterBeen) {
                if(null != helpCenterBeen && helpCenterBeen.size()>0){
                    mProblemType = helpCenterBeen.get(0).getValue();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public ProductInfo getProductInfo() {
        return mDatas;
    }

    @Override
    public String getDefaultPid() {
        return mPid;
    }

    @Override
    public String getProblemType() {
        return mProblemType;
    }

    @Override
    public void onDestroy() {
        mAdapter.clear();
        super.onDestroy();
    }
}
