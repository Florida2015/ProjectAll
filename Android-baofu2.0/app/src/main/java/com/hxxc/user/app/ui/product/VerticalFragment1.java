package com.hxxc.user.app.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.Event.ProductDetailEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ProductInfo;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.base.BaseFragment2;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.NumberUtils;
import com.hxxc.user.app.utils.ThreadManager;
import com.hxxc.user.app.widget.MyLinerProgressbar;
import com.hxxc.user.app.widget.verticalpager.CustScrollView;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/9/23.
 * 产品详情 页
 */

public class VerticalFragment1 extends BaseFragment2 {
    @BindView(R.id.myprogressbar)//进度条
            MyLinerProgressbar mMyProgressbar;
    @BindView(R.id.custScrollView)
    CustScrollView custScrollView;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_name_mark)
    TextView tv_name_mark;

    @BindView(R.id.tv_num_1)//预期年收益
            TextView tv_num_1;
    @BindView(R.id.tv_num_2)//募集金额
            TextView tv_num_2;
    @BindView(R.id.tv_num_3)//借款期限
            TextView tv_num_3;
    @BindView(R.id.tv_wan)//借款期限
            TextView tv_wan;

    @BindView(R.id.tv_text_1)//预期年收益
            TextView tv_text_1;
    @BindView(R.id.tv_text_2)//期限
            TextView tv_text_2;
    @BindView(R.id.tv_text_3)//起投金额
            TextView tv_text_3;

    @BindView(R.id.tv_num_person)//-人已投
            TextView tv_num_person;
    @BindView(R.id.tv_surplus_money)//剩余金额
            TextView tv_surplus_money;
    @BindView(R.id.tv_surplus_money_text)//剩余金额text
            TextView tv_surplus_money_text;

    @BindView(R.id.tv_des_1)//付息方式
            TextView tv_des_1;
    @BindView(R.id.tv_des_4)//保障方式
            TextView tv_des_4;
    @BindView(R.id.tv_des_5)//剩余时间
            TextView tv_des_5;


    @BindView(R.id.tv_1)//开始加入
            TextView tv_1;
    @BindView(R.id.tv_2)//进入期限
            TextView tv_2;
    @BindView(R.id.tv_3)//到期退出
            TextView tv_3;
    @BindView(R.id.tv_qixian)//期限1290天
            TextView tv_qixian;

    @BindView(R.id.ll_time)//剩余时间
            LinearLayout ll_time;
    private ThreadManager.ThreadPoolProxy threadPoolProxy;


    public static VerticalFragment1 newInstance() {
        Bundle arguments = new Bundle();
        VerticalFragment1 fragment = new VerticalFragment1();
        fragment.setArguments(arguments);
        return fragment;
    }

    @OnClick(R.id.tv_des_4)
    public void onClick() {
        if (BtnUtils.isFastDoubleClick()) {
            Intent intent = new Intent(getContext(), AdsActivity.class);
            intent.putExtra("url", HttpRequest.riskFund);
            intent.putExtra("title", "风险备用金");
            startActivity(intent);
        }
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_vertical1;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        custScrollView.setType(2);
        setRefresh(true);
        mMyProgressbar.post(new Runnable() {
            @Override
            public void run() {
                ((ProductDetailActivity) getActivity()).doReflush();
            }
        });
    }

    public void initViews(ProductInfo info) {
        setRefresh(false);
        if (info.getStatus() == 1) {
            tv_name_mark.setText("加入中");
            tv_name_mark.setTextColor(getResources().getColor(R.color.blue_text));
            tv_name_mark.setBackgroundResource(R.drawable.blue_stroke);
        } else if (info.getStatus() == 0) {
            tv_name_mark.setText("等待加入");
            tv_name_mark.setTextColor(getResources().getColor(R.color.blue_text_low));
            tv_name_mark.setBackgroundResource(R.drawable.blue_low_stroke);
        } else {
            tv_name_mark.setText("已满额");
            tv_name_mark.setTextColor(getResources().getColor(R.color.grey_text));
            tv_name_mark.setBackgroundResource(R.drawable.stroke_grey);
            ll_time.setVisibility(View.GONE);
        }
//        tv_name_mark.setText(info.getStatusText());

        tv_name.setText(info.getProductBidName());
        tv_num_1.setText(NumberUtils.mul(info.getYearRate(), 100) + "");
        tv_num_2.setText(info.getPeriod() + "");
        long min = info.getMinAmount();
        if (min < 10000) {
            tv_num_3.setText(min + "");
            tv_wan.setText("元");
        } else {
            if (((min / 10000f) - (min / 10000)) > 0) {
                tv_num_3.setText((min / 10000f) + "");
            } else {
                tv_num_3.setText((min / 10000) + "");
            }
            tv_wan.setText("万元");
        }

        mMyProgressbar.setProgress((int) Math.round(info.getProportion() * 100f));//TODO info.getProportion();
        tv_num_person.setText("目标金额" + CommonUtil.checkMoney(info.getAmount()) + "元");//TODO
        tv_surplus_money.setText(CommonUtil.checkMoney(info.getSurplus()) + "元");

        tv_des_1.setText("付息方式：" + info.getProduct().getInterestType());//(info.getInterestId() == 1 ? "每月付息" : "到期还本付息")
//        tv_des_1.setText(info.getInterestType());
        tv_des_4.setText(info.getProduct().getSecurityStyle());
        tv_des_5.setText(DateUtil.getTimeFormat(info.getSurplusTime()));

        tv_1.setText(DateUtil.getTime1(info.getProductStart()));
        tv_2.setText(info.getProduct().getInterestStartTime());
        tv_3.setText(info.getProduct().getRedeemedDays());
        tv_qixian.setText(info.getPeriod() + "个月");

        if (info.getStatus() != 2)
            startTime(info.getSurplusTime());
    }

    private void startTime(long times) {
        if (times < 1) {
            ll_time.setVisibility(View.GONE);
            return;
        }
        threadPoolProxy = ThreadManager.getSinglePool("product");
        threadPoolProxy.execute(new Runnable() {
            @Override
            public void run() {
                long t = times - 1;
                try {
                    while (t >= 0) {
                        LogUtil.e("产品详情-定时器=" + times + "/" + t);
                        Thread.sleep(1000);
                        EventBus.getDefault().post(new ProductDetailEvent(t));
                        t -= 1;
                    }
                    Thread.sleep(1000);
                    EventBus.getDefault().post(new ProductDetailEvent(-1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (null != threadPoolProxy) {
            threadPoolProxy.stop();
            threadPoolProxy.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        ((ProductDetailActivity) getActivity()).doReflush();
    }

    public void onRefreshEnd() {
        setRefresh(false);
    }


    public void setTimeText(long time) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_des_5.setText(DateUtil.getTimeFormat(time));
                if (time < 0) ll_time.setVisibility(View.GONE);
            }
        });
    }
}
