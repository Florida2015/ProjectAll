package com.hxxc.huaxing.app.ui.mine.lend;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.adapter.FragmentAdapter;
import com.hxxc.huaxing.app.data.entity.TabEntity;
import com.hxxc.huaxing.app.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/22.
 * 我的出借
 * 还款中, 投标中, 已流标,已结清
 */
public class MineLendActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.cTabLayout_lend)
    CommonTabLayout cTabLayout;
    @BindView(R.id.viewpager_lend)
    ViewPager viewpager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"还款中", "投标中", "已流标", "已结清"};
    private String[] mStatus = {"2", "1", "3", "4"};//1募集中（投标中） 2 计息(还款中) 3流标 4结清
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    int postion =0;

    @Override
    public int getLayoutId() {
        return R.layout.mine_lend_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("我的出借");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        tabLayoutInit();

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

    private void tabLayoutInit() {
        mFragments.clear();
        mTabEntities.clear();
        for (String status : mStatus) {
            mFragments.add(LendFragment.newInstance(status));//  状态
        }
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
}
