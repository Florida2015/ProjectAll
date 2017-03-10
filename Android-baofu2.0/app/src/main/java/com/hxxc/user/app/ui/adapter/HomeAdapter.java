package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Product;
import com.hxxc.user.app.ui.adapter.viewholder.HomeViewHolder;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.DisplayUtil;

import java.util.List;

/**
 * Created by chenqun on 2016/9/22.
 */

public class HomeAdapter extends BaseAdapter2<Product> {

    public HomeAdapter(Context mContext, List<Product> mList, RecyclerView recyclerView) {
        super(mContext, mList, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home, parent, false);
        HomeViewHolder holder = new HomeViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindNomalViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        HomeViewHolder mHolder = (HomeViewHolder) holder;
        Product bean = mList.get(position);
        mHolder.tv_title.setText(bean.getProductName());
        mHolder.tv_rate.setText(bean.getYearRate() * 100f + "");
        mHolder.tv_deadline.setText("期限" + bean.getPeriod() + "个月");
        mHolder.tv_investable_money.setText("可投金额" + CommonUtil.checkMoney(bean.getSurplus()) + "元");
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

//        List<Product.ActivityLabelBean> list = bean.getActivityLabel();
//        mHolder.ll_mark.removeAllViews();
//        if (null != list && list.size() > 0) {
//            TextView textView;
//            for (int a = 0; a < (list.size() > 2 ? 2 : list.size()); a++) {
//                Product.ActivityLabelBean o = list.get(a);
//                textView = new TextView(mContext);
//                textView.setText(o.getActivityName());
//                textView.setTextSize(12);
//                textView.setBackgroundResource(R.drawable.blue_stroke);
//                textView.setPadding(DisplayUtil.dip2px(mContext, 3), DisplayUtil.dip2px(mContext, 1), DisplayUtil.dip2px(mContext, 3), DisplayUtil.dip2px(mContext, 1));
//                textView.setTextColor(Color.parseColor("#ff1f80d1"));
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
//                layoutParams.leftMargin = DisplayUtil.dip2px(mContext, 10);
//                layoutParams.gravity = Gravity.CENTER_VERTICAL;
//                textView.setLayoutParams(layoutParams);
//                textView.setGravity(Gravity.CENTER);
//                textView.setSingleLine();
//                textView.setEllipsize(TextUtils.TruncateAt.END);
//                mHolder.ll_mark.addView(textView);
//            }
//        }

        mHolder.ll_mark.removeAllViews();
        String  strList = bean.getStampContents();
        if(!TextUtils.isEmpty(strList)){
            List<Product.ActivityLabelBean2> list = new Gson().fromJson(strList, new TypeToken<List<Product.ActivityLabelBean2>>() {
            }.getType());

            if (null != list && list.size() > 0) {
                TextView textView;
                for (int a = 0; a < (list.size() > 2 ? 2 : list.size()); a++) {
                    Product.ActivityLabelBean2 o = list.get(a);
                    textView = new TextView(mContext);
                    textView.setText(o.getActivitylabel());
                    textView.setTextSize(12);
                    textView.setBackgroundResource(R.drawable.blue_stroke);
                    textView.setPadding(DisplayUtil.dip2px(mContext, 3), DisplayUtil.dip2px(mContext, 1), DisplayUtil.dip2px(mContext, 3), DisplayUtil.dip2px(mContext, 1));
                    textView.setTextColor(Color.parseColor("#ff1f80d1"));
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



        //TODO
        if (mList != null && mList.size() > 0) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) mHolder.ll_content.getLayoutParams();
            if (position == mList.size() - 1) {
                params.setMargins(0, 0, 0, DisplayUtil.dip2px(mContext, 40));
            } else {
                params.setMargins(0, 0, 0, 0);
            }
            mHolder.ll_content.setLayoutParams(params);
        }
    }
}
