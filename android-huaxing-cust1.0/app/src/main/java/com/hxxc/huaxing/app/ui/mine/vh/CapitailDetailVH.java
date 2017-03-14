package com.hxxc.huaxing.app.ui.mine.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.CapitalDetailBean;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseViewHolder;

/**
 * Created by Administrator on 2016/10/13.
 * 资金明细
 */

public class CapitailDetailVH extends BaseViewHolder<CapitalDetailBean> {

    RelativeLayout relative_capital;

    TextView tv_mine_capital_type;
    TextView tv_mine_capital_time;
    TextView tv_mine_capital_cont;

    public CapitailDetailVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.mine_capital_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, CapitalDetailBean obj) {
        if (position%2==0)relative_capital.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        else relative_capital.setBackgroundColor(mContext.getResources().getColor(R.color.white_fafa));
        //类型(出借pay,收益profit,本息赎回principal,充值recharge,提现cash,refund 退款)
        String temp="+";
        if (!TextUtils.isEmpty(obj.getType())&&(obj.getType().contains("pay")||obj.getType().contains("cash"))){
            temp="-";
        }else {//
            temp="+";
        }

        tv_mine_capital_type.setText(TextUtils.isEmpty(obj.getTypeStr())?"":obj.getTypeStr());//
        tv_mine_capital_time.setText(obj.getCreateTimeText());
        tv_mine_capital_cont.setText(temp+DateUtil.getInstance().getDouble(obj.getMoney()));

    }

}
