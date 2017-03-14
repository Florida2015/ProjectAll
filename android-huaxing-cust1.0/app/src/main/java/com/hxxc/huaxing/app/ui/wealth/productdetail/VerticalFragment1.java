package com.hxxc.huaxing.app.ui.wealth.productdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.ProductInfo;
import com.hxxc.huaxing.app.ui.wealth.BaseFragment2;
import com.hxxc.huaxing.app.ui.wealth.Fragment1;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.wedgit.MyLinerProgressbar;
import com.hxxc.huaxing.app.wedgit.verticalpager.CustScrollView;

import java.math.BigDecimal;

import butterknife.BindView;

/**
 * Created by chenqun on 2016/9/23.
 * 产品详情 页
 */

public class VerticalFragment1 extends BaseFragment2 {
    @BindView(R.id.myprogressbar)//进度条
            MyLinerProgressbar mMyProgressbar;
    @BindView(R.id.view_line)
    View view_line;

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
    @BindView(R.id.tv_num_3_2)//借款期限
            TextView tv_num_3_2;

    @BindView(R.id.tv_text_1)//预期年收益
            TextView tv_text_1;
    @BindView(R.id.tv_text_2)//预期年收益
            TextView tv_text_2;
    @BindView(R.id.tv_text_3)//预期年收益
            TextView tv_text_3;

    @BindView(R.id.tv_num_person)//-人已投
            TextView tv_num_person;
    @BindView(R.id.tv_surplus_money)//剩余金额
            TextView tv_surplus_money;
    @BindView(R.id.tv_surplus_money_text)//剩余金额text
            TextView tv_surplus_money_text;

    @BindView(R.id.tv_des_1)//还款方式
            TextView tv_des_1;
    @BindView(R.id.tv_des_2)//起息标准
            TextView tv_des_2;
    @BindView(R.id.tv_des_3)//起投金额
            TextView tv_des_3;
    @BindView(R.id.tv_des_4)//保障方式
            TextView tv_des_4;

    @BindView(R.id.tv_1)//身份认证
            TextView tv_1;
    @BindView(R.id.tv_2)//工作认证
            TextView tv_2;
    @BindView(R.id.tv_3)//收入认证
            TextView tv_3;
    @BindView(R.id.tv_4)//信用报告
            TextView tv_4;
    private int mType;

    public static VerticalFragment1 newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type", type);
        VerticalFragment1 fragment = new VerticalFragment1();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_vertical1;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        custScrollView.setType(2);
        mType = getArguments().getInt("type");
        view_line.setVisibility(View.GONE);
        if (mType == Fragment1.Type_Zhaiquan) {
            tv_text_2.setText("转让价格");
            tv_text_3.setText("剩余期限");
            tv_surplus_money_text.setText("债权金额");
        } else {
            tv_text_2.setText("募集金额");
            tv_text_3.setText("借款期限");
            tv_surplus_money_text.setText("剩余金额");
        }
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

        switch (info.getStatus()) {
            case 1://募集中
                tv_name_mark.setBackgroundResource(R.drawable.orange2_stroke);
                tv_name_mark.setTextColor(mContext.getResources().getColor(R.color.orange2_stroke));
                break;
            case 2://已流标
                tv_name_mark.setBackgroundResource(R.drawable.orange4_stroke);
                tv_name_mark.setTextColor(mContext.getResources().getColor(R.color.orange4_stroke));
                break;
            case 3://已撤标
                tv_name_mark.setBackgroundResource(R.drawable.black_stroke);
                tv_name_mark.setTextColor(mContext.getResources().getColor(R.color.black_stroke));
                break;
            case 4://已结清
                tv_name_mark.setBackgroundResource(R.drawable.grey_stroke);
                tv_name_mark.setTextColor(mContext.getResources().getColor(R.color.grey_stroke));
                break;
            case 0://未发标
            default://还款中
                tv_name_mark.setBackgroundResource(R.drawable.orange3_stroke);
                tv_name_mark.setTextColor(mContext.getResources().getColor(R.color.orange3_stroke));
                break;
        }
        tv_name.setText(info.getProductName());
        tv_name_mark.setText(info.getStatusText());

        tv_num_1.setText(info.getYearRate().multiply(new BigDecimal(100)).doubleValue() + "");
        tv_num_2.setText(CommonUtil.toMoneyType(info.getAmount() == null ? 0 : info.getAmount().doubleValue()));

        tv_des_1.setText("还款方式：" + info.getInterestType());
        tv_des_2.setText("起息标准：" + info.getInterestStandard());
        tv_des_3.setText("起投金额：" + CommonUtil.toMoneyType(info.getMinAmount().doubleValue()) + "元");
        tv_des_4.setText("" + info.getSecurityStyle());

        mMyProgressbar.setProgress((int) (Math.round(info.getProportion() * 100f)));//TODO info.getProportion()
        if (mType == Fragment1.Type_Zhaiquan) {
            tv_surplus_money.setText(CommonUtil.toMoneyType(info.getAmount() == null ? 0 : info.getAmount().doubleValue()) + "元");
            tv_num_person.setText("转让人：" + info.getTransferor());
            tv_num_3.setText(info.getPeriodMonthStr() + "");
            tv_num_3_2.setVisibility(View.GONE);
        } else {
            tv_num_person.setText(info.getNumber() + "人已投");//TODO
            tv_surplus_money.setText(CommonUtil.toMoneyType(info.getSurplus() == null ? 0 : info.getSurplus().doubleValue()) + "元");
            tv_num_3.setText(info.getPeriodMonth() + "");
            tv_num_3_2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        ((ProductDetailActivity) getActivity()).doReflush();
    }

    public void onRefreshEnd() {
        setRefresh(false);
    }
}
