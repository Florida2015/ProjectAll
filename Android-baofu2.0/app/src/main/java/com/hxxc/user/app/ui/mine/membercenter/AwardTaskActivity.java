package com.hxxc.user.app.ui.mine.membercenter;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.adapter.FragmentAdapter;
import com.hxxc.user.app.data.bean.MemberCenterBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.dialogfragment.LendDialogListener;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.ui.mine.web.WebActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by houwen.lai on 2016/11/7.
 * 有奖任务
 */

public class AwardTaskActivity extends BaseRxActivity implements ViewPager.OnPageChangeListener,TabLayout.OnTabSelectedListener,LendDialogListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_share)
    ImageButton toolbar_share;

    @BindView(R.id.tv_use_inteagel)
    TextView tv_use_inteagel;

    @BindView(R.id.tab_layout_award_task)
    TabLayout tab_layout;
    @BindView(R.id.viewpager_award_task)
    ViewPager viewpager;

    @BindView(R.id.btn_exchange_inteagel)
    FancyButton btn_exchange_inteagel;

    private String urlMall;//商城url

    @Override
    public int getLayoutId() {
        return R.layout.member_award_task;
    }

    @Override
    public void initView() {
        toolbar_title.setText("有奖任务");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar_share.setVisibility(View.VISIBLE);
        toolbar_share.setImageResource(R.mipmap.ico_doubt);

        String inteage = getIntent().getStringExtra("value");

        urlMall = getIntent().getStringExtra("mailUrl");

        //可用积分
        final SpannableStringBuilder sp = new SpannableStringBuilder(inteage+"\n可用积分");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_fbb0)), 0, inteage.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black_aaaa)), inteage.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(18, true), 0, inteage.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        sp.setSpan(new AbsoluteSizeSpan(14, true),inteage.length(), sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_use_inteagel.setText(sp);

//        tabLayoutInit();
        fragments.clear();
        getTaskData(0);

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

    @OnClick({R.id.toolbar_share,R.id.btn_exchange_inteagel})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_share://问题
                if (BtnUtils.isFastDoubleClick()){
                    startActivity(new Intent(AwardTaskActivity.this, WebActivity.class).
                            putExtra("title","积分规则").
                            putExtra("url", HttpRequest.indexBaseUrl+HttpRequest.httpUrlMsgType).
                            putExtra("flag",false));
                }
                break;
            case R.id.btn_exchange_inteagel://
                if (!TextUtils.isEmpty(urlMall)&& BtnUtils.isFastDoubleClick()){
                    Intent mIntent = new Intent();
                    mIntent.setClass(mContext, AdsActivity.class);
                    mIntent.putExtra("url",urlMall);
                    mIntent.putExtra("title","兑换积分");
                    startActivity(mIntent);
                }
                break;
        }
    }


    /**
     * tablayout适配
     */
    private String[] mTabs = new String[]{"成长任务","每日任务"};
    List<Fragment> fragments = new ArrayList<Fragment>();

    private boolean flag = false;

    private void tabLayoutInit() {
//        fragments.clear();
//        fragments.add(AwardTaskFragment.newInstance(""));//订单列表
//        fragments.add(AwardTaskFragment.newInstance( ""));//客户列表

        viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments, Arrays.asList(mTabs)));
        tab_layout.setupWithViewPager(viewpager);
        tab_layout.setTabsFromPagerAdapter(viewpager.getAdapter());

        tab_layout.setOnTabSelectedListener(this);
        viewpager.setOnPageChangeListener(this);

        if (getIntent().hasExtra("index")){
            viewpager.setCurrentItem(getIntent().getIntExtra("index",0));
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 获取任务
     * @param type
     */
    public void getTaskData(int type){
        Api.getClient().getGetMemberSingTask(Api.uid,type).compose(RxApiThread.convert()).
                subscribe(new Subscriber<BaseResult<List<MemberCenterBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        toast("数据异常");
                    }

                    @Override
                    public void onNext(BaseResult<List<MemberCenterBean>> listBaseBean) {
                        LogUtil.d("Tast="+listBaseBean.toString());
                        if (listBaseBean.getSuccess()){

                            if (type==0){
                                fragments.add(AwardTaskFragment.newInstance(listBaseBean));//成长任务
                                getTaskData(1);
                            }else{
                                fragments.add(AwardTaskFragment.newInstance(listBaseBean));//每日任务
                                tabLayoutInit();
                            }
                        }else {
                            toast(listBaseBean.getErrorMsg());
                        }
                    }
        });

    }

    /**
     * 跳转 产品列表
     * @param resId
     */
    @Override
    public void onLendId(int resId) {
        if (resId==R.id.btn_member_lend){
            // 跳转 产品列表
            if(MemberCenterActivity.instance!=null)MemberCenterActivity.instance.finish();
            EventBus.getDefault().post(new MainEvent(1).setLoginType(MainEvent.FROMFINDPASSWORD));
            AwardTaskActivity.this.finish();


        }
    }
}
