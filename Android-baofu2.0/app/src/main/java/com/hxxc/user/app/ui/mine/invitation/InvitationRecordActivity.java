package com.hxxc.user.app.ui.mine.invitation;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.FragmentAdapter;
import com.hxxc.user.app.data.bean.InvitationRecordBean;
import com.hxxc.user.app.data.entity.TabEntity;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.MoneyUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/11/3.
 * 邀请记录
 */

public class InvitationRecordActivity extends BaseRxActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.cTabLayout_invitation_record_list)
    CommonTabLayout cTabLayout;
    @BindView(R.id.viewpager_invitation_record_list)
    ViewPager viewpager;

    @BindView(R.id.tv_total_award_amount)
    TextView tv_total_award_amount;
    @BindView(R.id.tv_invitation_people_count)
    TextView tv_invitation_people_count;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"奖励记录", "邀请记录"};
    private String[] mStatus = {"1", "2"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    int postion =0;

    InvitationRecordBean invitationRecordBean;

    @Override
    public int getLayoutId() {
        return R.layout.invitation_record_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("邀请记录");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        postion = getIntent().getIntExtra("index",0);
        invitationRecordBean = (InvitationRecordBean) getIntent().getSerializableExtra("bean");

        setInitData();

        tabLayoutInit();

    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //点击了返回箭头
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void tabLayoutInit() {
        mFragments.clear();
        mTabEntities.clear();
//        for (String status : mStatus) {
//            mFragments.add(InvitationRecordFragment.newInstance(status));//  状态
//        }
        mFragments.add(InvitationRecordFragment.newInstance(""));//  状态
        mFragments.add(InvitationListFragment.newInstance(""));//  状态

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

    public void setInitData(){
        String sum_award="0.00";
        if (invitationRecordBean==null){
            sum_award="0.00";
        }else  sum_award = MoneyUtil.addComma(invitationRecordBean.getInvitation(),2,ROUND_FLOOR);//

        SpannableStringBuilder sp = new SpannableStringBuilder(""+sum_award+"\n奖励总额(元)");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, sum_award.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white_d2e5)), sum_award.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(22, true), 0, sum_award.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        sp.setSpan(new AbsoluteSizeSpan(12, true),sum_award.length(), sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_total_award_amount.setText(sp);


        String sum_people = "0";

        if (invitationRecordBean==null){
            sum_people="0";
        }else  sum_people=invitationRecordBean.getInvitationTotalNumber()+"";

        sp = new SpannableStringBuilder(""+sum_people+"\n邀请人数(人)");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, sum_people.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white_d2e5)), sum_people.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(22, true), 0, sum_people.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        sp.setSpan(new AbsoluteSizeSpan(12, true),sum_people.length(), sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_invitation_people_count.setText(sp);

    }

}
