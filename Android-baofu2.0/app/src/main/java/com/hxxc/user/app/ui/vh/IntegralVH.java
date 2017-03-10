package com.hxxc.user.app.ui.vh;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.IntegralRecordListBean;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

/**
 * Created by houwen.lai on 2016/11/2.
 * 会员中心 积分记录
 */

public class IntegralVH extends BaseViewHolder<IntegralRecordListBean> {

    RelativeLayout relative_integral_month;
    TextView tv_integral_month;
    TextView tv_integral_getting;
    TextView tv_integral_useing;

    RelativeLayout relative_integral;
    TextView tv_integral_title;
    TextView tv_integral_date;
    TextView tv_integral_count;

    public IntegralVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.integral_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, IntegralRecordListBean obj) {

        if (position%2==0){
            relative_integral.setBackgroundColor(mContext.getResources().getColor(R.color.white_fafa));
        }else {
            relative_integral.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        relative_integral_month.setVisibility(View.GONE);

        tv_integral_title.setText(obj.getTaskName());
        tv_integral_date.setText(obj.getCreateTimeStr());
        tv_integral_count.setText(obj.getPoint()>0?"+"+obj.getPoint():"-"+obj.getPoint());

    }


}
