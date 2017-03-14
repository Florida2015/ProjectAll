package com.hxxc.huaxing.app.ui.HuaXingFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.ui.base.BaseAdapter2;
import com.hxxc.huaxing.app.utils.DisplayUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by chenqun on 2016/9/22.
 */

public class HomeAdapter extends BaseAdapter2<ProductBean> {

    public HomeAdapter(Context mContext, List mList, RecyclerView recyclerView) {
        super(mContext, mList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home, parent, false);
        HomeViewHolder holder = new HomeViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductBean bean = mList.get(position);
        HomeViewHolder mHolder = (HomeViewHolder) holder;
        mHolder.tv_title.setText(bean.getProductName());
        mHolder.tv_rate.setText(bean.getYearRate().multiply(new BigDecimal(100)).floatValue() + "%");
        mHolder.tv_deadline.setText("期限" + bean.getPeriodMonth() + "个月");
        mHolder.tv_investable_money.setText("可投金额" + bean.getSurplus() + "元");

        if (null != mListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setOnItemClick(holder.itemView, position);
                }
            });
        }
        if (bean.getStatus() == 1) {
            if (bean.getSurplus() <= 0)
                mHolder.circle_progress.setText("满额").setProgress(0);//bean.getProportion()
            else
                mHolder.circle_progress.setText("加入").setProgress(bean.getProportion() * 100f);//bean.getProportion()
        } else {
            mHolder.circle_progress.setText(bean.getStatusText()).setProgress(0);
        }

        List<ProductBean.ActivityLabelBean> list = HXXCApplication.getInstance().getGson().fromJson(bean.getActivityLabel(), new TypeToken<List<ProductBean.ActivityLabelBean>>() {
        }.getType());

        mHolder.ll_mark.removeAllViews();
        if (null != list && list.size() > 0) {
            TextView textView;
            for (int a = 0; a < (list.size() > 2 ? 2 : list.size()); a++) {
                ProductBean.ActivityLabelBean o = list.get(a);
                textView = new TextView(mContext);
                textView.setText(o.getLabel());
                textView.setTextSize(12);
                textView.setBackgroundResource(R.drawable.blue_stroke);
                textView.setPadding(DisplayUtil.dip2px(mContext, 4), DisplayUtil.dip2px(mContext, 4), DisplayUtil.dip2px(mContext, 4), DisplayUtil.dip2px(mContext, 4));
                textView.setTextColor(mContext.getResources().getColor(R.color.blue_text));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                layoutParams.leftMargin = DisplayUtil.dip2px(mContext, 10);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                textView.setLayoutParams(layoutParams);
                textView.setGravity(Gravity.CENTER);
                textView.setSingleLine();
                textView.setEllipsize(TextUtils.TruncateAt.END);
                mHolder.ll_mark.addView(textView);
            }
        }
    }
}
