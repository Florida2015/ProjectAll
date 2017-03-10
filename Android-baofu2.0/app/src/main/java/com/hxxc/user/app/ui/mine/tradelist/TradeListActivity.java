package com.hxxc.user.app.ui.mine.tradelist;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.adapter.FragmentAdapter;
import com.hxxc.user.app.data.entity.TabEntity;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.mine.tradelist.pinned.TradeTFragment;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by houwen.lai on 2016/11/8.
 * 交易记录
 */

public class TradeListActivity extends BaseRxActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.cTabLayout_trade_list)
    CommonTabLayout cTabLayout;
    @BindView(R.id.viewpager_trade_list)
    ViewPager viewpager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"全部", "收入", "支出"};
    private String[] mStatus = {"0", "1", "2"};//
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    int postion =0;

    @Override
    public int getLayoutId() {
        return R.layout.trade_list;
    }

    @Override
    public void initView() {
        toolbar_title.setText("交易记录");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        if (getIntent().hasExtra("index")){
            postion = getIntent().getIntExtra("index",0);
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
//        mFragments.add(TradeFragment.newInstance(mStatus[0]));//  所有
//        mFragments.add(TradeFragment.newInstance(mStatus[1]));//  收入
//        mFragments.add(TradeFragment.newInstance(mStatus[2]));//  支出
        mFragments.add(TradeTFragment.newInstance(mStatus[0]));//  所有
        mFragments.add(TradeTFragment.newInstance(mStatus[1]));//  收入
        mFragments.add(TradeTFragment.newInstance(mStatus[2]));//  支出
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
