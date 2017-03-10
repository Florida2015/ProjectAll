package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Product;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.widget.CircleView;

import java.util.List;

/**
 * Created by chenqun on 2016/10/25.
 */

public class ProductAdapter extends BaseAdapter2<Product> {

    public ProductAdapter(Context mContext, List<Product> mList, RecyclerView recyclerView) {
        super(mContext, mList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_product, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        Product bean = mList.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.tv_title.setText(bean.getProductName());
        mHolder.tv_title2.setText(bean.getCurrPeriod() + "期");
        mHolder.tv_title3.setText("可投金额" + CommonUtil.checkMoney(bean.getSurplus()));
        mHolder.tv_rate.setText(bean.getYearRate() * 100f + "");
        mHolder.tv_deadline.setText("期限" + bean.getPeriod() + "个月");
        mHolder.tv_investable_money.setText(CommonUtil.checkMoney(bean.getMinAmount()) + "起投");
        if (null != mListener) {
            mHolder.ll_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setOnItemClick(holder.itemView, position);
                }
            });
        }
        if (1 == bean.getStatus())
            mHolder.circle_progress.setText(bean.getStatusText()).setTextColor(bean.getStatus()).setProgress((float) bean.getProportion() * 100f);//bean.getProportion()
        else //已满额，等待加入
            mHolder.circle_progress.setText(bean.getStatusText()).setTextColor(bean.getStatus()).setProgress(0);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout ll_content;
        private CircleView circle_progress;
        private final TextView tv_title;
        private final TextView tv_title2;
        private final TextView tv_title3;
        private final TextView tv_rate;
        private final TextView tv_deadline;
        private final TextView tv_investable_money;

        public MyViewHolder(View view) {
            super(view);
            ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
            circle_progress = (CircleView) view.findViewById(R.id.circle_progress);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_title2 = (TextView) view.findViewById(R.id.tv_title2);
            tv_title3 = (TextView) view.findViewById(R.id.tv_title3);
            tv_rate = (TextView) view.findViewById(R.id.tv_rate);
            tv_deadline = (TextView) view.findViewById(R.id.tv_deadline);
            tv_investable_money = (TextView) view.findViewById(R.id.tv_investable_money);
        }
    }

}
