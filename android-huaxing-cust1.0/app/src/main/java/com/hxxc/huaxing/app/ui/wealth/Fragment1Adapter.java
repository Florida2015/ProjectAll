package com.hxxc.huaxing.app.ui.wealth;

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

import com.google.gson.reflect.TypeToken;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.ui.HuaXingFragment.HomeViewHolder;
import com.hxxc.huaxing.app.ui.base.BaseAdapter2;
import com.hxxc.huaxing.app.utils.DisplayUtil;
import com.hxxc.huaxing.app.utils.LogUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by chenqun on 2016/9/28.
 */

public class Fragment1Adapter extends BaseAdapter2<ProductBean> {

    private final int mType;

    public Fragment1Adapter(Context mContext, List mList, RecyclerView recyclerView, int type) {
        super(mContext, mList, recyclerView);
        mType = type;
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductBean bean = mList.get(position);

        HomeViewHolder mHolder = (HomeViewHolder) holder;
        mHolder.tv_title.setText(bean.getProductName());
        mHolder.tv_rate.setText(bean.getYearRate().multiply(new BigDecimal(100)).floatValue() + "%");

        if (position == 0 || bean.getStatus() != mList.get(position - 1).getStatus()) {
            mHolder.view_line.setVisibility(View.GONE);
            mHolder.ll_top.setVisibility(View.VISIBLE);


            if (bean.getStatus() == 1) {//TODO 募集中（1）或者转让中（1）不需要详情页,没有“更多”，没有点击事件
                mHolder.tv_more.setVisibility(View.GONE);
                mHolder.ll_top.setOnClickListener(null);

                if (mType == Fragment1.Type_Zhaiquan) mHolder.tv_top.setText("转让中");
                else mHolder.tv_top.setText("募集中");
            } else {
                if (mType == Fragment1.Type_Zhaiquan) mHolder.tv_top.setText("已转让");
                else mHolder.tv_top.setText(bean.getStatusText());

                mHolder.tv_more.setVisibility(View.VISIBLE);
                if (null != mHeadListener) {
                    mHolder.ll_top.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mHeadListener.setOnHeadItemClick(mHolder.itemView, position, bean.getStatus(), bean.getStatusText());
                        }
                    });
                }
            }
        } else {
            mHolder.view_line.setVisibility(View.VISIBLE);
            mHolder.ll_top.setVisibility(View.GONE);
            mHolder.ll_top.setOnClickListener(null);
        }

        if (mType == Fragment1.Type_Zhaiquan) {//债权转让
            mHolder.tv_investable_money.setText("可投金额" + bean.getAmount() + "元");
//            mHolder.ll_mark.setVisibility(View.GONE);
            mHolder.circle_progress.setVisibility(View.GONE);
            mHolder.tv_right.setVisibility(View.VISIBLE);
            if (bean.getStatus() == 1) {// 转让中
                if (bean.getSurplus() <= 0) {
                    mHolder.tv_right.setBackgroundResource(R.mipmap.button_gray);
                    mHolder.tv_right.setText("满额");
                    mHolder.tv_right.setTextColor(Color.parseColor("#ffffff"));
                } else {
                    mHolder.tv_right.setBackgroundResource(R.drawable.cycle_yellow);
                    mHolder.tv_right.setText("加入");
                    mHolder.tv_right.setTextColor(Color.parseColor("#be7f24"));
                }

            } else {
                mHolder.tv_right.setBackgroundResource(R.mipmap.button_gray);
                mHolder.tv_right.setText(bean.getStatusText());
                mHolder.tv_right.setTextColor(Color.parseColor("#ffffff"));
            }
            mHolder.tv_deadline.setText("期限" + bean.getPeriodMonthStr());
        } else {//财富项目
            mHolder.tv_investable_money.setText("可投金额" + bean.getSurplus() + "元");
            mHolder.circle_progress.setVisibility(View.VISIBLE);
            mHolder.tv_right.setVisibility(View.GONE);
            if (bean.getStatus() == 1) {//募集中
                if (bean.getSurplus() <= 0)
                    mHolder.circle_progress.setText("满额").setProgress(0);//bean.getProportion()
                else
                    mHolder.circle_progress.setText("加入").setProgress(bean.getProportion() * 100f);//bean.getProportion()
            } else {
                mHolder.circle_progress.setText(bean.getStatusText()).setProgress(0);
            }
            mHolder.tv_deadline.setText("期限" + bean.getPeriodMonth() + "个月");
        }

        mHolder.ll_mark.removeAllViews();
//            List<ProductBean.ActivityLabelBean> list = bean.getActivityLabel();
        LogUtil.d("ssssss=" + bean.getActivityLabel());
        List<ProductBean.ActivityLabelBean> list = HXXCApplication.getInstance().getGson().fromJson(bean.getActivityLabel(), new TypeToken<List<ProductBean.ActivityLabelBean>>() {
        }.getType());

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
        //TODO 设置
        if (null != mListener) {
            mHolder.ll_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.setOnItemClick(holder.itemView, position);
                }
            });
        }

    }
}
