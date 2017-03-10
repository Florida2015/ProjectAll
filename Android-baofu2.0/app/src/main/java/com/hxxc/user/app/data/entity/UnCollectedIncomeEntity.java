package com.hxxc.user.app.data.entity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.PaymentBean;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.mine.assetsrecord.ClanderActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;

import rx.Observable;

/**
 * Created by chenqun on 2017/2/22.
 * param的值:    0:待回款本金       1:待回款收益        2:获取指定月份的回款收益列表
 */

public class UnCollectedIncomeEntity extends BaseCEntity<PaymentBean> {

    @Override
    public Observable getPageAt(int page, int row) {
        String status = mParam.get("status");
        if (!TextUtils.isEmpty(status) && "1".equals(status))
            return HttpRequest.getInstance().getApiService().getReturnPaymentList(SPUtils.geTinstance().getUid(), page + "", row + "");
        else if (!TextUtils.isEmpty(status) && "2".equals(status))
            return HttpRequest.getInstance().getApiService().getBackPaymentList(SPUtils.geTinstance().getUid(), page + "", row + "");
        else
            return HttpRequest.getInstance().getApiService().getPrincipalPaymentList(SPUtils.geTinstance().getUid(), page + "", row + "");
    }

    @Override
    public void onClick(Context context, PaymentBean item) {

        Intent intent = new Intent(context, ClanderActivity.class);
        intent.putExtra("end_time", item.getEndTimeStr());
        intent.putExtra("endTimeMonthStr", item.getEndTimeMonthStr());
        context.startActivity(intent);
    }

    @Override
    public int getItemLayou() {
        return R.layout.item_pager_backassets;
    }

    @Override
    public void convert(BaseViewHolder helper, PaymentBean item) {
        if (helper.getLayoutPosition() % 2 == 0)
            helper.setBackgroundRes(R.id.ll_item_content, R.color.white);
        else
            helper.setBackgroundRes(R.id.ll_item_content, R.color.white_fafa);
        helper.setText(R.id.tv_1, item.getEndTimeStr());

        String status = mParam.get("status");
        if (!TextUtils.isEmpty(status) && "0".equals(status))
            helper.setText(R.id.tv_2, "+ " + CommonUtil.moneyType(item.getPayCapitalCount() == null ? 0 : item.getPayCapitalCount().doubleValue()));
        else
            helper.setText(R.id.tv_2, "+ " + CommonUtil.moneyType(item.getPayInterestCount() == null ? 0 : item.getPayInterestCount().doubleValue()));
    }
}
