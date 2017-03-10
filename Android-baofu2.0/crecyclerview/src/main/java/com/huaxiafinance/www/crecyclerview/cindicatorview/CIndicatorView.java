package com.huaxiafinance.www.crecyclerview.cindicatorview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.huaxiafinance.www.crecyclerview.R;
import com.huaxiafinance.www.crecyclerview.cindicatorview.indicator.TitleIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.huaxiafinance.www.crecyclerview.R.id.viewpager;

/**
 * Created by chenqun on 2017/2/20.
 */

public class CIndicatorView extends FrameLayout {
    private CViewPager mViewpager;
    private TitleIndicator mIndicator;
    private static final int pageLimits = 3;//viewpage单边最多缓存页面的数量

    public CIndicatorView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View layout = LayoutInflater.from(getContext()).inflate(
                R.layout.crecyclerview_indicator, this);
        mIndicator = (TitleIndicator) layout.findViewById(R.id.indicator);
        mViewpager = (CViewPager) layout.findViewById(viewpager);

        mIndicator.setTextSize(15);
        mIndicator.setItemUnderHeight(4);
        mIndicator.setIsAllInScreen(true);
//        mIndicator.setItemBackgroundResId(R.drawable.selector_tabbackground);
        mIndicator.setItemUnderColor(Color.parseColor("#ff1f80d1"));
        mIndicator.setTabUnderColor(Color.WHITE);
        mIndicator.setTabUnderHeight(1);
        mIndicator.setTextColorResId(R.color.tab_text_selector);
    }

    public CIndicatorView initDatas(List<Fragment> fragments, String[] titles, FragmentManager manager) {
        mViewpager.setAdapter(new FragmentAdapter(manager, fragments, Arrays.asList(titles)));
        mViewpager.setOffscreenPageLimit(Math.min(fragments.size() - 1, pageLimits));
        mIndicator.setViewPager(mViewpager);
        return this;
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;
        private List<String> mTitles;

        FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            this.mFragments = fragments;
            this.mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
