package com.hxxc.user.app.ui.vh;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.InvitaionRewardBean;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/11/4.
 *  奖励记录
 */

public class InvitaitonRecordVH extends BaseViewHolder<InvitaionRewardBean> {

    RelativeLayout relative_invitation_record_list;

    TextView tv_reward_name;
    TextView tv_reward_date;
    TextView tv_reward_money;
    TextView tv_reward_source;

    public InvitaitonRecordVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.invitation_reward_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, InvitaionRewardBean obj) {

        if (position==0){
            relative_invitation_record_list.setVisibility(View.VISIBLE);
        }else {
            relative_invitation_record_list.setVisibility(View.GONE);
        }

        tv_reward_name.setText(TextUtils.isEmpty(obj.getUserName())?"":obj.getUserName());
        tv_reward_date.setText(obj.getCreateTimeText());//DateUtil.getmstodate(obj.getCreateTime(),DateUtil.YYYYMMDDHHMMSS)

        tv_reward_money.setText("+"+ MoneyUtil.addComma(obj.getMoney(),2,ROUND_FLOOR));
        tv_reward_source.setText("来源："+obj.getSources());

    }

}
