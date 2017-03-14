package com.hxxc.huaxing.app.ui.wealth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenqun on 2016/9/22.
 */

public class WealthAdapter extends FragmentPagerAdapter {
    private String[] mTitles = {"财富项目","债权转让"};
    private static final int PAGE_COUNT = 2;
    private Map<Integer , Fragment> mFragments = new HashMap<>();

    public WealthAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
        if(null == fragment){
            switch (position){
                case 0:
                    fragment = Fragment1.newInstance(Fragment1.Type_Wealth);
                    break;
                case 1:
                    fragment = Fragment1.newInstance(Fragment1.Type_Zhaiquan);
                    break;
            }
            mFragments.put(position,fragment);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  mTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
