package com.hxxc.user.app.ui.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.hxxc.user.app.Event.ExitLoginEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AccountInfo;
import com.hxxc.user.app.data.bean.MemberCenterDetailBean;
import com.hxxc.user.app.data.bean.MineListBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.mine.SettingActivity;
import com.hxxc.user.app.ui.mine.UserInfoActivity;
import com.hxxc.user.app.ui.mine.membercenter.MemberCenterActivity;
import com.hxxc.user.app.ui.mine.noticelist.MessageActivity;
import com.hxxc.user.app.ui.pager.mine.MineViewPager;
import com.hxxc.user.app.ui.vh.UserDynamicVH;
import com.hxxc.user.app.ui.vh.UserHeaderDynamicVH;
import com.hxxc.user.app.utils.DisplayUtil;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.widget.trecyclerview.TRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/10/25.
 * 我的
 */

public class MineNewPager extends BasePager {

    private final FragmentManager mFragmentManager;
    @BindView(R.id.toolbar_mine)
    ImageButton toolbar_mine;
    //    @BindView(R.id.toolbar_tv_weath)
//    TextView toolbar_tv_weath;
    @BindView(R.id.toolbar_wearing)
    RelativeLayout toolbar_wearing;
    @BindView(R.id.tv_unread2)
    TextView tv_unread2;

    @BindView(R.id.toolbar_setting)
    ImageButton toolbar_setting;

    @BindView(R.id.linear_wealth)
    LinearLayout linear_wealth;
    @BindView(R.id.tv_member_level_mine)
    TextView tv_member_level_mine;
    @BindView(R.id.tv_member_level_name_mine)
    TextView tv_member_level_name_mine;

    //    @BindView(R.id.tv_income_money)
//    TextView tv_income_money;
//    @BindView(R.id.tv_total_assets)
//    TextView tv_total_assets;
//    @BindView(R.id.tv_remainder_money)
//    TextView tv_remainder_money;

    @BindView(R.id.recyclerview_mine)
    TRecyclerView recyclerview_mine;

    private MineViewPager mineViewPager;
    //    private MineAdapter mineAdapter;
    private List<MineListBean> list;

//    private TRecyclerView recyclerview_mine;

    private View viewButtom;

    private boolean flag;
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"我的");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"我的");
    }
    @OnClick({R.id.toolbar_setting, R.id.toolbar_mine, R.id.linear_wealth, R.id.toolbar_wearing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_setting:
                mContext.startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.toolbar_mine:
                mContext.startActivity(new Intent(mContext, UserInfoActivity.class));
                break;
            case R.id.linear_wealth://会员中心
                mContext.startActivity(new Intent(mContext, MemberCenterActivity.class));
                break;
            case R.id.toolbar_wearing://消息中心
                mContext.startActivity(new Intent(mContext, MessageActivity.class));
                break;
        }
    }

//    public MineNewPager(Context context) {
//        super(context);
//    }

    public MineNewPager(Context context, FragmentManager manager) {
        super(context);
        mFragmentManager = manager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.activity_mine_new_1, null);
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(mContext);
            view.setPadding(0, statusBarHeight, 0, 0);
        }
        recyclerview_mine.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        return view;
    }

    @Override
    public void initData() {

        mineViewPager = new MineViewPager(mContext, mFragmentManager);
        viewButtom = View.inflate(mContext, R.layout.view_textview, null);

        getWealth();
        getAccountMessage();

        recyclerview_mine.setHeadView(UserHeaderDynamicVH.class);
        recyclerview_mine.setView(UserDynamicVH.class);
        recyclerview_mine.setBackgroundColor(mContext.getResources().getColor(R.color.grey_f1f1));
        recyclerview_mine.fetch();

        recyclerview_mine.getSwipeRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.d("mine fragment onRefresh");
                getWealth();
                getAccountMessage();
                recyclerview_mine.reFetch();
            }
        });
        recyclerview_mine.setWaningText(mContext.getResources().getString(R.string.text_accout_underwriting),R.mipmap.icon_pacific_ocean);
    }

    /**
     * 获取财气值
     */
    public void getWealth() {
        Api.getClient().getGetMemberDetail(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<MemberCenterDetailBean>(mContext) {
                              @Override
                              public void onSuccess(MemberCenterDetailBean bean) {
                                  recyclerview_mine.setRefreshing(false);
                                  tv_member_level_mine.setText(bean.getLevel() + "");
                                  tv_member_level_name_mine.setText("财气值 | " + bean.getPointValue());
                              }

                              @Override
                              public void onFail(String fail) {
                                  recyclerview_mine.setRefreshing(false);
                              }
                          }
                );
    }

    /**
     * 获取账户的信息
     */
    public void getAccountMessage() {
        Api.getClient().getMyAccountInfo(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<AccountInfo>(mContext) {
                              @Override
                              public void onSuccess(AccountInfo accountInfo) {
                                  recyclerview_mine.setRefreshing(false);
                                  mineViewPager.setTopData(MoneyUtil.formatMoney(accountInfo.getCumulativeProfit(), 2, ROUND_FLOOR),
                                          MoneyUtil.formatMoney(accountInfo.getTatalAmount(), 2, ROUND_FLOOR),
                                          MoneyUtil.formatMoney(accountInfo.getYesterdayProfit(), 2, ROUND_FLOOR),
                                          MoneyUtil.formatMoney(accountInfo.getRemainAmount(), 2, ROUND_FLOOR));//设置金额

                                  recyclerview_mine.getAdapter().setHeadViewData(accountInfo);
                                  recyclerview_mine.getAdapter().notifyDataSetChanged();
                              }

                              @Override
                              public void onFail(String fail) {
                                  recyclerview_mine.setRefreshing(false);
                              }
                          }
                );
    }

    //TODO 刷新数据
    public void onMineEvent() {
        getWealth();
        getAccountMessage();
        recyclerview_mine.reFetch();
    }

    @Override
    public void onExitLoginEvent(ExitLoginEvent event) {
        super.onExitLoginEvent(event);

        tv_member_level_mine.setText("0");
        tv_member_level_name_mine.setText("财气值 | 0");
        if (mineViewPager!=null)
        mineViewPager.setTopData("", "", "", "");
    }

    @Override
    public void onUnreadMessageEvent(int count) {
        if (null == tv_unread2) return;
        if (count > 0) tv_unread2.setVisibility(View.VISIBLE);
        else tv_unread2.setVisibility(View.INVISIBLE);
    }
}

