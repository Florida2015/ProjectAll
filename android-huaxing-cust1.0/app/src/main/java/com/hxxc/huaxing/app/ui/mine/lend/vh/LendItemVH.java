package com.hxxc.huaxing.app.ui.mine.lend.vh;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.LendItemBean;
import com.hxxc.huaxing.app.ui.mine.lend.LendDetailActivity;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.MoneyUtil;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseViewHolder;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by Administrator on 2016/9/29.
 * 我的出借  item
 */

public class LendItemVH extends BaseViewHolder<LendItemBean> {

    TextView tv_lend_out_name;
    TextView tv_lend_rate;
    TextView tv_lend_rate_date;
    TextView tv_lend_lend_money;
    TextView tv_lend_lend_extra;
    TextView tv_lend_lend_type;

    public LendItemVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.mine_lend_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, LendItemBean obj) {
        LogUtil.d("LendItemVH  LendItemBean="+obj.toString());

        tv_lend_out_name.setText(obj.getProductName());
        tv_lend_rate.setText(DateUtil.getInstanse().getOne(obj.getYearRate()*100)+"%");
        tv_lend_rate_date.setText("期限"+obj.getPeriods()+"个月");
        tv_lend_lend_money.setText(MoneyUtil.addComma(obj.getMoney(),2,ROUND_FLOOR)+"元");

        if (obj.getAwordType()==1){//
            tv_lend_lend_extra.setText("(现金券"+obj.getAwardValue()+"元)");
        }else if(obj.getAwordType()==2){//
            tv_lend_lend_extra.setText("(加息券"+obj.getAwardValue()+"%)");
        }else  tv_lend_lend_extra.setText("");


        if (obj.getOrderStatus()==1){//投标
            tv_lend_lend_type.setTextColor(mContext.getResources().getColor(R.color.orange_BE7F));
            tv_lend_lend_type.setText(obj.getOrderStatusText());
        }else if (obj.getOrderStatus()==2){//还款中
            if (obj.getDebtStatus()==0){//还款中
                tv_lend_lend_type.setText(obj.getOrderStatusText());
                tv_lend_lend_type.setTextColor(mContext.getResources().getColor(R.color.orange_E6C6));
            }else{
                tv_lend_lend_type.setText(obj.getDebtStatusText());
                tv_lend_lend_type.setTextColor(mContext.getResources().getColor(R.color.red_FC6B));
            }
        }else if (obj.getOrderStatus()==3){//流标
            tv_lend_lend_type.setText(obj.getOrderStatusText());
            tv_lend_lend_type.setTextColor(mContext.getResources().getColor(R.color.orange_D7B3));
        }else if (obj.getOrderStatus()==4){//已结清
            if (obj.getDebtStatus()==0){//结清
                tv_lend_lend_type.setText(obj.getOrderStatusText());
                tv_lend_lend_type.setTextColor(mContext.getResources().getColor(R.color.black_aaaa));
            }else{
                tv_lend_lend_type.setText(obj.getDebtStatusText());
                tv_lend_lend_type.setTextColor(mContext.getResources().getColor(R.color.black_3333));
            }
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //我的出借 详情
                LogUtil.d("我的出借 详情");
                mContext.startActivity(new Intent(mContext, LendDetailActivity.class).putExtra("lenddetail",obj));

            }
        });

    }

}
