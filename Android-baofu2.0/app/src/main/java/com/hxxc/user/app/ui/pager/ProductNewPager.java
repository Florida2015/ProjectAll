package com.hxxc.user.app.ui.pager;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobstat.StatService;
import com.hxxc.user.app.Event.ExitLoginEvent;
import com.hxxc.user.app.Event.LoginEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.contract.presenter.ProductPresenter;
import com.hxxc.user.app.ui.pager.product.ProductPager1;
import com.hxxc.user.app.ui.pager.product.ProductPager3;
import com.hxxc.user.app.utils.DisplayUtil;
import com.hxxc.user.app.widget.indicator.TitleIndicator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenqun on 2016/11/18.
 */

public class ProductNewPager extends BasePager implements ViewPager.OnPageChangeListener {
    @BindView(R.id.activity_title_indicator)
    TitleIndicator mIndicator;
    @BindView(R.id.activity_title_viewpager)
    ViewPager mViewpager;
    private Unbinder unbinder;

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"财富");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"财富");
    }

    private static final String[] titles = {"华信优选", "定信优选"};//, "基金优选"

    //缓存pager
    private Map<Integer, BasePager> mPagerMaps = new HashMap<Integer, BasePager>();

    //从缓存中获取pager
    public BasePager getPager(int index) {
        BasePager pager = mPagerMaps.get(index);
        if (null == pager) {
            switch (index) {
                case 0:
                    pager = new ProductPager1(mContext, ProductPresenter.TYPE_HUAXING);
                    break;
                case 1:
                    pager = new ProductPager1(mContext, ProductPresenter.TYPE_DINGXING);
                    break;
                case 2:
                    pager = new ProductPager3(mContext);
                    break;
            }
            mPagerMaps.put(index, pager);
        }
        return pager;
    }

    public ProductNewPager(Context context) {
        super(context);
        initRootView();
    }

    private void initRootView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(mContext);
            getRootView().setPadding(0, statusBarHeight, 0, 0);
        }
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.activity_product_new, null);
        unbinder = ButterKnife.bind(this, view);
        inits();
        return view;
    }


    @Override
    public void onDestroy() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        Set<Map.Entry<Integer, BasePager>> entries = mPagerMaps.entrySet();
        for(Map.Entry<Integer, BasePager> map : entries){
            map.getValue().onDestroy();
        }
        mPagerMaps.clear();
        super.onDestroy();
    }

    @Override
    public void initData() {

    }

    private void inits() {
        mIndicator.setTextSize(15);
//        mIndicator.setItemPaddingTop(DisplayUtil.dip2px(mContext,10));
//        mIndicator.setItemPaddingBottom(DisplayUtil.dip2px(mContext,10));
        mIndicator.setItemUnderHeight(4);
        mIndicator.setIsAllInScreen(true);
//        mIndicator.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicator.setItemUnderColor(Color.parseColor("#ff1f80d1"));
        mIndicator.setTabUnderColor(Color.parseColor("#dddddd"));
        mIndicator.setTabUnderHeight(1);
        mIndicator.setTextColorResId(R.color.tab_text_selector);


        mViewpager.setAdapter(new ProductNewPagerAdapter());
//        mViewpager.setOffscreenPageLimit(titles.length);
        mIndicator.setViewPager(mViewpager);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.post(new Runnable() {
            @Override
            public void run() {
                getPager(0).initData();
                getPager(0).isLoading = true;

//                getPager(2);//TODO 使用基金后需打开
            }
        });
    }

    public class ProductNewPagerAdapter extends PagerAdapter {

        public ProductNewPagerAdapter() {

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return titles.length;
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
            return titles[position];
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

    @Override
    public void onLoginEvent(LoginEvent event) {
        super.onLoginEvent(event);
        Set<Map.Entry<Integer, BasePager>> entries = mPagerMaps.entrySet();
        for(Map.Entry<Integer, BasePager> map : entries){
            map.getValue().onLoginEvent(event);
        }
    }

    @Override
    public void onExitLoginEvent(ExitLoginEvent event) {
        super.onExitLoginEvent(event);
        Set<Map.Entry<Integer, BasePager>> entries = mPagerMaps.entrySet();
        for(Map.Entry<Integer, BasePager> map : entries){
            map.getValue().onExitLoginEvent(event);
        }
    }
}
