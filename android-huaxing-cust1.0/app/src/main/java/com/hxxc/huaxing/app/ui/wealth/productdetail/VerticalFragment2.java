package com.hxxc.huaxing.app.ui.wealth.productdetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.ProductInfo;
import com.hxxc.huaxing.app.ui.wealth.BaseFragment2;


/**
 * Created by chenqun on 2016/9/23.
 * 产品详情 页
 */

public class VerticalFragment2 extends BaseFragment2 implements ProductDetailVertical2V{

    private int mType;
    private String[] mTitles = {"标的信息", "相关图片", "投标记录"};
    private ViewPager mViewPager;
    private ProductDetailAdapter mAdapter;

    private ProductInfo mDatas;
    private String mPid;

    public static VerticalFragment2 newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type", type);
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
        mType = getArguments().getInt("type");
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        TabLayout mTabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        mAdapter = new ProductDetailAdapter(getActivity().getSupportFragmentManager(),mType,this);
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

    @Override
    public ProductInfo getProductInfo() {
        return mDatas;
    }

    @Override
    public String getDefaultPid() {
        return mPid;
    }
}
