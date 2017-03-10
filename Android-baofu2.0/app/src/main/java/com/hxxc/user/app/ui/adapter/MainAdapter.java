package com.hxxc.user.app.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.huaxiafinance.lc.bottomindicator.IconPagerAdapter;
import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.pager.BasePager;
import com.hxxc.user.app.ui.pager.PagerFactory;

/**
 * Created by chenqun on 2016/8/17.
 */
public class MainAdapter extends PagerAdapter implements IconPagerAdapter {

    private static String[] strs = {"首页","财富","发现","我的"};
    private static int[] ints = {R.drawable.tab_home_selector, R.drawable.tab_finance_selector, R.drawable.tab_department_selector, R.drawable.tab_mine_selector};
    private final AppCompatActivity context;

    public MainAdapter(AppCompatActivity context) {
        this.context = context;
    }

    @Override
    public int getIconResId(int index) {
        return ints[index];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strs[position];
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(PagerFactory.getPager(context,position).getRootView());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager pager = PagerFactory.getPager(context,position);
        container.addView(pager.getRootView());
        return pager.getRootView();
    }
}
