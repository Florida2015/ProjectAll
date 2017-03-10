package com.hxxc.user.app.ui.product;

import android.view.View;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ProductInfo;
import com.hxxc.user.app.ui.base.BaseFragment2;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.NumberUtils;
import com.hxxc.user.app.widget.LeftAndRightTextView;
import com.hxxc.user.app.widget.verticalpager.CustScrollView;

import butterknife.BindView;

/**
 * Created by chenqun on 2016/9/23.
 */

public class AFragment extends BaseFragment2 {
    @BindView(R.id.scrollview)
    CustScrollView scrollview;

    @BindView(R.id.tv_title)
    TextView tv_title;//华信财简介

    @BindView(R.id.tv_1)
    LeftAndRightTextView tv_1;//预期年化收益
    @BindView(R.id.tv_2)
    LeftAndRightTextView tv_2;//封闭期
    @BindView(R.id.tv_3)
    LeftAndRightTextView tv_3;//起售时间
    @BindView(R.id.tv_4)
    LeftAndRightTextView tv_4;//起息日
    @BindView(R.id.tv_5)
    LeftAndRightTextView tv_5;//退出日
    @BindView(R.id.tv_6)
    LeftAndRightTextView tv_6;//付息方式
    @BindView(R.id.tv_7)
    LeftAndRightTextView tv_7;//保障方式
    @BindView(R.id.tv_8)
    LeftAndRightTextView tv_8;//退出方式
    @BindView(R.id.tv_9)
    LeftAndRightTextView tv_9;//标的介绍
    @BindView(R.id.tv_10)
    LeftAndRightTextView tv_10;//起投金额
    @BindView(R.id.tv_11)
    LeftAndRightTextView tv_11;//递增金额
    @BindView(R.id.tv_12)
    LeftAndRightTextView tv_12;//单笔最高可投

    @BindView(R.id.tv_21)
    LeftAndRightTextView tv_21;//加入费用
    @BindView(R.id.tv_22)
    LeftAndRightTextView tv_22;//管理费用
    @BindView(R.id.tv_23)
    LeftAndRightTextView tv_23;//退出费用
    @BindView(R.id.tv_24)
    LeftAndRightTextView tv_24;//提前退出费用


    @Override
    protected int getContentViewID() {
        return R.layout.fragment_product_a;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        scrollview.setType(3);
        scrollview.setPager(2);
        tv_8.setRightTextSingleLine(false);
        tv_9.setRightTextSingleLine(false);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        if(null != v){
            ProductInfo info = v.getProductInfo();
            if(null == info){
                isLoading = false;
                return;
            }

            if(null == tv_title || null == tv_1) return;

            tv_title.setText(info.getProductName()+"简介");
            tv_1.setRightText(NumberUtils.mul(info.getYearRate(), 100)+"%");
            tv_2.setRightText(info.getPeriod()+"个月");
            tv_3.setRightText(CommonUtil.formatDate(info.getProductStart()));
            tv_4.setRightText(info.getProduct().getInterestStartTime());
            tv_5.setRightText(info.getProduct().getRedeemedDays());
            tv_6.setRightText(info.getProduct().getInterestType());//info.getInterestId()==1?"每月付息":"到期还本付息"
            tv_7.setRightText(info.getProduct().getSecurityStyle());
            tv_8.setRightText(info.getProduct().getExitTypeDes());
            tv_9.setRightText(info.getProduct().getDescription());

            tv_10.setRightText(CommonUtil.checkMoney(info.getMinAmount())+"元");
            tv_11.setRightText(CommonUtil.checkMoney(info.getStepAmount())+"元");
            tv_12.setRightText(CommonUtil.checkMoney(info.getMaxAmount())+"元");

            tv_21.setRightText(info.getProduct().getJoinFee()+"元");
            tv_22.setRightText(info.getProduct().getManagerFee()+"元");
            tv_23.setRightText(info.getProduct().getExitFee()+"元");
            tv_24.setRightText(info.getProduct().getPenalty() + "%退出总资产");
        }
    }
}
