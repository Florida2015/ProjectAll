package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.RedPackagePayBean;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.DateUtil;

import java.util.List;

/**
 * Created by chenqun on 2016/10/27.
 */

public class RedPackageForPayAdapter extends BaseAdapter2<RedPackagePayBean> {
    private int item = -1;

    public RedPackageForPayAdapter(Context mContext, List<RedPackagePayBean> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_redpackage_pay, parent, false));
    }

    public void setItem(int a) {
        item = a;
        notifyDataSetChanged();
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        RedPackagePayBean bean = mList.get(position);

        MyViewHolder myHolder = (MyViewHolder) holder;
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = position;
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.setOnItemClick(myHolder.itemView, position);
                }
            }
        });
//        int i = viewHolder.getLayoutPosition();
//        if(i <= mList.size() - 1){
//            mListener.setOnItemClick(viewHolder.itemView, i);
//        }
        if (myHolder.getLayoutPosition() == item) {
            myHolder.ll_left.setBackgroundResource(R.drawable.red_envelope_s1);
            myHolder.ll_right.setBackgroundResource(R.drawable.red_envelope_s2);
            myHolder.tv_des1.setTextColor(Color.parseColor("#ff6159"));
            myHolder.tv_des2.setTextColor(Color.parseColor("#ff6159"));
        } else {
            myHolder.ll_left.setBackgroundResource(R.drawable.red_envelope_n1);
            myHolder.ll_right.setBackgroundResource(R.drawable.red_envelope_n2);
            myHolder.tv_des1.setTextColor(Color.parseColor("#ff938f"));
            myHolder.tv_des2.setTextColor(Color.parseColor("#ff938f"));
        }
        myHolder.tv_money.setText(bean.getMoney() + "元");
        myHolder.tv_des2.setText("有效期" + DateUtil.getDateLong(bean.getStartTime()) + "到" + DateUtil.getDateLong(bean.getEndTime()));
        myHolder.tv_des1.setText("最低投资金额" + CommonUtil.checkMoney(bean.getUseMoney()) + "元");
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout ll_left;
        private final LinearLayout ll_right;
        private final TextView tv_money;
        private final TextView tv_des1;
        private final TextView tv_des2;

        private MyViewHolder(View itemView) {
            super(itemView);
            ll_left = (LinearLayout) itemView.findViewById(R.id.ll_left);
            ll_right = (LinearLayout) itemView.findViewById(R.id.ll_right);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_des1 = (TextView) itemView.findViewById(R.id.tv_des1);
            tv_des2 = (TextView) itemView.findViewById(R.id.tv_des2);
        }
    }
}
