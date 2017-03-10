package com.hxxc.user.app.ui.pager.mine;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.ui.base.BaseRxFragment;
import com.hxxc.user.app.ui.financial.FinancialDetailActivity;
import com.hxxc.user.app.ui.mine.SafeProtectActivity;
import com.hxxc.user.app.ui.mine.bankcard.BankCardListActivity;
import com.hxxc.user.app.ui.mine.invitation.InvitationFriendsActivity;
import com.hxxc.user.app.ui.mine.redpackage.RedPackageActivity;
import com.hxxc.user.app.ui.mine.sign.SignActivity;
import com.hxxc.user.app.ui.mine.tradelist.TradeListActivity;
import com.hxxc.user.app.ui.order.OrderListActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by houwen.lai on 2016/10/25.
 */

public class FragmentMineBanner extends BaseRxFragment {

    @BindView(R.id.btn_tab_0)
    Button btn_tab_0;
    @BindView(R.id.btn_tab_1)
    Button btn_tab_1;
    @BindView(R.id.btn_tab_2)
    Button btn_tab_2;
    @BindView(R.id.btn_tab_3)
    Button btn_tab_3;


    private int index = 0;
    private int[] indexTab = new int[]{0, 4};

    public static FragmentMineBanner newInstance(int index) {
        Bundle arguments = new Bundle();
        arguments.putInt("index", index);
        FragmentMineBanner fragment = new FragmentMineBanner();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void initDagger() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.home_mine_banner_item;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {

        index = getArguments().getInt("index");

        initData(indexTab[index]);

    }

    @OnClick({R.id.btn_tab_0, R.id.btn_tab_1, R.id.btn_tab_2, R.id.btn_tab_3})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_tab_0:
//                ToastUtil.ToastShort(mContext,"index_"+index+"_indexTab_0");
                if (index == 0) {//签到
                    intent.setClass(mContext, SignActivity.class);
                    mContext.startActivity(intent);
                } else {//
//                    intent.setClass(mContext, TradeListActivity.class);//账单 交易记录
//                    mContext.startActivity(intent);

                    mContext.startActivity(new Intent(mContext, SafeProtectActivity.class));//账户安全
                }
                break;
            case R.id.btn_tab_1:
//                ToastUtil.ToastShort(mContext,"index_"+index+"_indexTab_1");
                if (index == 0) {//红包
                    intent.setClass(mContext, RedPackageActivity.class);
                    mContext.startActivity(intent);

                } else {//邀友
                    intent.setClass(mContext, InvitationFriendsActivity.class);
                    mContext.startActivity(intent);
                }
                break;
            case R.id.btn_tab_2:
//                ToastUtil.ToastShort(mContext,"index_"+index+"_indexTab_2");
                if (index == 0) {//基金 url
//                    intent.setClass(mContext, WebActivity.class);
//                    intent.putExtra("title","基金");
//                    intent.putExtra("url",UserInfoConfig.URL_fund);
//                    mContext.startActivity(intent);
//                    mContext.startActivity(new Intent(mContext, FundActivity.class));
//                    Intent in = new Intent(mContext, AdsActivity.class);
//                    in.putExtra("title", Constants.TEXT_JiJin);
//                    mContext.startActivity(in);
                    intent.setClass(mContext, TradeListActivity.class);//账单 交易记录
                    mContext.startActivity(intent);
                } else {//银行卡
                    intent.setClass(mContext, BankCardListActivity.class);
                    mContext.startActivity(intent);
                }
                break;
            case R.id.btn_tab_3:
//                ToastUtil.ToastShort(mContext,"index_"+index+"_indexTab_3");
                if (index == 0) {//订单
                    intent.setClass(mContext, OrderListActivity.class);
                    mContext.startActivity(intent);
                } else {//我的顾问
                    intent.setClass(mContext, FinancialDetailActivity.class);
                    mContext.startActivity(intent);

                }
                break;
        }
    }

    public void initData(int index) {

        Drawable drawable = getActivity().getResources().getDrawable(UserInfoConfig.tabs[0 + index]);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        btn_tab_0.setCompoundDrawables(null, drawable, null, null);//画在右边
        btn_tab_0.setText(UserInfoConfig.tabsTitle[0 + index]);

        Drawable drawable_1 = getActivity().getResources().getDrawable(UserInfoConfig.tabs[1 + index]);
        drawable_1.setBounds(0, 0, drawable_1.getMinimumWidth(), drawable_1.getMinimumHeight()); //设置边界
        btn_tab_1.setCompoundDrawables(null, drawable_1, null, null);//画在右边
        btn_tab_1.setText(UserInfoConfig.tabsTitle[1 + index]);

        Drawable drawable_2 = getActivity().getResources().getDrawable(UserInfoConfig.tabs[2 + index]);
        drawable_2.setBounds(0, 0, drawable_2.getMinimumWidth(), drawable_2.getMinimumHeight()); //设置边界
        btn_tab_2.setCompoundDrawables(null, drawable_2, null, null);//画在右边
        btn_tab_2.setText(UserInfoConfig.tabsTitle[2 + index]);

        Drawable drawable_3 = getActivity().getResources().getDrawable(UserInfoConfig.tabs[3 + index]);
        drawable_3.setBounds(0, 0, drawable_3.getMinimumWidth(), drawable_3.getMinimumHeight()); //设置边界
        btn_tab_3.setCompoundDrawables(null, drawable_3, null, null);//画在右边
        btn_tab_3.setText(UserInfoConfig.tabsTitle[3 + index]);

    }

}
