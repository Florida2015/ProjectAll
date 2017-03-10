package com.hxxc.user.app.ui.mine.assetsrecord;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AccountInfo;
import com.hxxc.user.app.listener.AssetsFragmentListener;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.mine.setting.HelpCenterActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.widget.FixedScroller;
import com.hxxc.user.app.widget.customviewpager.CustomViewpager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * Created by houwen.lai on 2017/2/14.
 * 资产分析
 *  账户总资产 总收益
 *
 */

public class AssetsRecordNewActivity extends BaseRxActivity implements AssetsFragmentListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_share)
    ImageButton toolbar_share;

    @BindView(R.id.viewpager)
    CustomViewpager viewpager;

    AssetsRecordFragment assetsFragment;//总资产
    AssetsRecordFragment assetsFragmentIncome;//总收益
    final List<Fragment> fragments = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.assets_record_new_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("资产分析");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        toolbar_share.setVisibility(View.VISIBLE);//问题

//        viewpager.setAdapter();

        getAccountMessage();



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
    @OnClick(R.id.toolbar_share)
    public void onClick(View view){
        if (!BtnUtils.isFastDoubleClick())return;
        if (view.getId()==R.id.toolbar_share){//问题
            startActivity(new Intent(mContext,HelpCenterActivity.class));
        }
    }


    public void setViewpagerData(AccountInfo accountInfo){

        assetsFragment = AssetsRecordFragment.newInstance(true,accountInfo);

        assetsFragmentIncome = AssetsRecordFragment.newInstance(false,accountInfo);
        fragments.add(assetsFragment);
        fragments.add(assetsFragmentIncome);

        try {
            Field mScroller;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Interpolator sInterpolator = new AccelerateDecelerateInterpolator();
            FixedScroller scroller = new FixedScroller(viewpager.getContext(), sInterpolator);
            mScroller.set(viewpager, scroller);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }


        viewpager.setScanScroll(false);//禁止滑动
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

    }

    /**
     * fragment 切换
     * @param flag
     */
    @Override
    public void onClickId(boolean flag) {
        if (flag){
            viewpager.setCurrentItem(0,true);
        }else {
            viewpager.setCurrentItem(1,true);
        }
    }

    /**
     * 获取账户的信息
     */
    public void getAccountMessage() {
        Api.getClient().getMyAccountInfo(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<AccountInfo>(mContext) {
                              @Override
                              public void onSuccess(AccountInfo accountInfo) {

//                                          MoneyUtil.formatMoney(accountInfo.getCumulativeProfit(), 2, ROUND_FLOOR),
//                                          MoneyUtil.formatMoney(accountInfo.getTatalAmount(), 2, ROUND_FLOOR),
//                                          MoneyUtil.formatMoney(accountInfo.getYesterdayProfit(), 2, ROUND_FLOOR),
//                                          MoneyUtil.formatMoney(accountInfo.getRemainAmount(), 2, ROUND_FLOOR));//设置金额
                                  setViewpagerData(accountInfo);
//                                  if(assetsFragment!=null){//账号总资产
//                                      assetsFragment.setAssetsData(accountInfo);
//                                  }
//                                  if(assetsFragmentIncome!=null){//总收益
//                                      assetsFragmentIncome.setAssetsIncomeData(accountInfo);
//                                  }
                              }

                              @Override
                              public void onFail(String fail) {
                                  setViewpagerData(null);
                              }
                          }
                );
    }

}
