package com.hxxc.user.app.ui.mine.setting;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.HelpCenterBean;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.pager.BasePager;
import com.hxxc.user.app.ui.pager.HelpCenterPager;
import com.hxxc.user.app.widget.indicator.TitleIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqun on 2016/11/9.
 * 帮助中心
 */

public class HelpCenterActivity extends ToolbarActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.activity_title_indicator)
    TitleIndicator mIndicator;
    @BindView(R.id.activity_title_viewpager)
    ViewPager mViewpager;

    //缓存pager
    private  Map<Integer, BasePager> mPagerMaps = new HashMap<Integer, BasePager>();
    //从缓存中获取pager
    public  BasePager getPager(int index){
        BasePager pager = mPagerMaps.get(index);
        if(null == pager){
            pager = new HelpCenterPager(this,mList.get(index).getValue());
            mPagerMaps.put(index,pager);
        }
        return pager;
    }

    public List<HelpCenterBean> mList = new ArrayList<>();
    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_help_center;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("帮助中心");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mIndicator.setTextSize(15);

        mIndicator.setItemUnderHeight(4);
        mIndicator.setIsAllInScreen(true);
//        mIndicator.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicator.setItemUnderColor(Color.parseColor("#ff1f80d1"));
        mIndicator.setTabUnderColor(Color.WHITE);
        mIndicator.setTabUnderHeight(1);
        mIndicator.setTextColorResId(R.color.tab_text_selector);

        initDatas();
    }

    private void initDatas() {
        mApiManager.getDictList(Constants.Type_Help, new SimpleCallback<List<HelpCenterBean>>() {


            @TargetApi(Build.VERSION_CODES.DONUT)
            @Override
            public void onNext(List<HelpCenterBean> helpCenterBeen) {
                mList =helpCenterBeen;
                mViewpager.setAdapter(new HelpCenterPagerAdapter());
                mIndicator.setViewPager(mViewpager);
                mIndicator.setOnPageChangeListener(HelpCenterActivity.this);
                getPager(0).initData();
                getPager(0).isLoading = true;
            }

            @Override
            public void onError() {

            }
        });
    }

    public class HelpCenterPagerAdapter extends PagerAdapter {

        public HelpCenterPagerAdapter(){

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(getPager(position).getRootView());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = getPager(position);
            container.addView(pager.getRootView());
            return pager.getRootView();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position).getLabel();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        final BasePager pager = getPager(position);
        if (!pager.isLoading) {
            pager.initData();
            pager.isLoading = true;
        }
    }
}
