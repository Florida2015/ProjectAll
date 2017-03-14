package com.hxxc.huaxing.app.ui.wealth;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.utils.DisplayUtil;

/**
 * Created by Administrator on 2016/9/21.
 * 财富
 */
public class WealthFragment extends BaseFragment2 {
//    @BindView(R.id.toolbar_title)
//    TextView title;


    private ViewPager mViewPager;

    public static WealthFragment newInstance(String users) {
        Bundle arguments = new Bundle();
        arguments.putString("user",users);
        WealthFragment fragment = new WealthFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getContentViewID() {
        return R.layout.home_wealth_fragment;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
//        title.setText("财富列表");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(mContext);
            rootView.setPadding(0,statusBarHeight,0,0);
        }

        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        TabLayout mTabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new WealthAdapter(getActivity().getSupportFragmentManager()));
    }
}
