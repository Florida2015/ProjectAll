package com.hxxc.user.app.ui.vh;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.RedPackageItemBean;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

import java.math.BigDecimal;

import de.greenrobot.event.EventBus;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/10/27.
 * 我的 红包列表 item modes
 */

public class RedPackageVH extends BaseViewHolder<RedPackageItemBean> {

    LinearLayout linear_red_count;
    RelativeLayout relative_red_content;

    TextView tv_red_count;
    TextView tv_red_title;

    ImageView img_red_tip;

    public RedPackageVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.red_package_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, RedPackageItemBean obj) {
        LogUtil.d("RedItemVH  RedItemBean="+obj.toString());
        if (obj.getStatus()==1){//未使用 可用
            if (System.currentTimeMillis()-obj.getEndTime()>0){//过期
                linear_red_count.setBackgroundResource(R.drawable.greay_red_g);
                relative_red_content.setBackgroundResource(R.drawable.greay_red_s);
                img_red_tip.setBackgroundResource(R.mipmap.red_everdue);
                tv_red_title.setTextColor(mContext.getResources().getColor(R.color.black_aaaa));
            }else {
                linear_red_count.setBackgroundResource(R.drawable.red_package_n);
                relative_red_content.setBackgroundResource(R.drawable.red_package_s);
                img_red_tip.setBackgroundResource(R.mipmap.red_unsed);
                tv_red_title.setTextColor(mContext.getResources().getColor(R.color.red_ff60));
            }
        }else if(obj.getStatus()==0){//已使用 不可用
            linear_red_count.setBackgroundResource(R.drawable.greay_red_g);
            relative_red_content.setBackgroundResource(R.drawable.greay_red_s);
            img_red_tip.setBackgroundResource(R.mipmap.red_used);
            tv_red_title.setTextColor(mContext.getResources().getColor(R.color.black_aaaa));
        }else {//已过期
            linear_red_count.setBackgroundResource(R.drawable.greay_red_g);
            relative_red_content.setBackgroundResource(R.drawable.greay_red_s);
            img_red_tip.setBackgroundResource(R.mipmap.red_everdue);
            tv_red_title.setTextColor(mContext.getResources().getColor(R.color.black_aaaa));
        }

        tv_red_count.setText(MoneyUtil.addComma(obj.getMoney(),0,ROUND_FLOOR,MoneyUtil.point)+"元");

        StringBuffer text = new StringBuffer("最低投资金额");
        BigDecimal mm ;
        if (obj.getUseMoney()==null) mm =new BigDecimal("0");
        else mm = BigDecimal.valueOf(obj.getUseMoney().doubleValue()/10000);

        text.append(MoneyUtil.addComma(mm,0,ROUND_FLOOR,MoneyUtil.point)).append("万元").
                append("\n赠送来源：").append(obj.getSources()).append("\n" +
                "有效期").append(DateUtil.getmstodatesed(obj.getStartTime(),DateUtil.YYYYMMDD)).
                append("至").append(DateUtil.getmstodatesed(obj.getEndTime(),DateUtil.YYYYMMDD));

        tv_red_title.setText(text.toString());

        //未使用
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (BtnUtils.isFastDoubleClick()){
                if (obj.getStatus()==1&&System.currentTimeMillis()-obj.getEndTime()<0){//可用

                    EventBus.getDefault().post(new MainEvent(1).setLoginType(MainEvent.FROMFINDPASSWORD));

                    ((Activity)mContext).finish();

                }
            }
            }
        });

    }


}
