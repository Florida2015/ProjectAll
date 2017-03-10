package com.hxxc.user.app.ui.vh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.OrderItemBean;
import com.hxxc.user.app.ui.order.OrderDetailActivity;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/10/27.
 * 订单列表 item
 */

public class OrderVH extends BaseViewHolder<OrderItemBean> {

    LinearLayout linear_order;

    TextView tv_product_name;
    TextView tv_product_data;
    TextView tv_product_time;

    TextView tv_product_continue;

    TextView tv_product_rate;
    TextView tv_product_count;
    TextView tv_product_money;
    TextView tv_product_extra;
    TextView tv_product_type;

    public OrderVH(View v) {
        super(v);
        linear_order = (LinearLayout) v.findViewById(R.id.linear_order);
    }

    @Override
    public int getType() {
        return R.layout.order_list_item;
    }

    @SuppressLint("InlinedApi")
    @Override
    public void onBindViewHolder(View view, int position, OrderItemBean obj) {
        LogUtil.d("order_item="+obj.toString());

        tv_product_name.setText(""+obj.getProductName());//

        tv_product_data.setText(obj.getCurrPeriod()+"期");

        if (obj.getIsConInvest()==1)tv_product_continue.setVisibility(View.VISIBLE);
        else tv_product_continue.setVisibility(View.GONE);

        tv_product_time.setText(DateUtil.getmstodate(obj.getSignTime(),DateUtil.YYYYMMDDHHMMSS));
        tv_product_rate.setText( obj.getYearRateText() );
        tv_product_count.setText("期限"+obj.getPeriods()+"个月");
        tv_product_money.setText(MoneyUtil.addComma(obj.getAmount(),2,ROUND_FLOOR)+"元");

        if (obj.getRewardType()==2){
            tv_product_extra.setText("(红包抵扣"+MoneyUtil.addComma(obj.getRewardMoney(),2,ROUND_FLOOR)+"元)");
        }else tv_product_extra.setText("");

//        if (obj.getOrderStatus()==1){//1收益中 3赎回中 -1已赎回
//            tv_product_type.setText("收益中");
//        }else if (obj.getOrderStatus()==3){
//            tv_product_type.setText("赎回中");
//        }else if (obj.getOrderStatus()==-1){
//            tv_product_type.setText("已赎回");
//        }

        tv_product_type.setText(obj.getOrderStatusText());

        tv_product_type.setTextColor(mContext.getResources().getColor(R.color.orange_fbb0));
//        if (obj.getRewardType()==0) {//收益中
//            tv_product_extra.setText("(抵扣10元红包)");
//            tv_product_type.setTextColor(mContext.getResources().getColor(R.color.orange_fbb0));
//        }else if(obj.getRewardType()==1) {//出借成功
//            tv_product_type.setTextColor(mContext.getResources().getColor(R.color.green_29cd));
//        } else if(obj.getRewardType()==2)//已赎回
//            tv_product_type.setTextColor(mContext.getResources().getColor(R.color.black_aaaa));

        linear_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToastUtil.showSafeToast((Activity) mContext,"orderdetail="+obj.toString());
                mContext.startActivity(new Intent(mContext, OrderDetailActivity.class).putExtra("orderNo",obj.getOrderNo()));
            }
        });
    }

}
