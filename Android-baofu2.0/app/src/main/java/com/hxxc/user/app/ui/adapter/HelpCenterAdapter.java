package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.HelpCenterItemBean;

import java.util.List;

/**
 * Created by chenqun on 2016/11/9.
 */

public class HelpCenterAdapter extends BaseAdapter2<HelpCenterItemBean> {
    public HelpCenterAdapter(Context mContext, List<HelpCenterItemBean> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate( R.layout.item_help_center, parent,false));
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        HelpCenterItemBean bean = mList.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;
        if(0 == position )mHolder.view_top.setVisibility(View.VISIBLE);
        else mHolder.view_top.setVisibility(View.GONE);

        if(mList.size()-1 == position )mHolder.view_bottom.setVisibility(View.GONE);
        else mHolder.view_bottom.setVisibility(View.VISIBLE);

        mHolder.tv_1.setText(bean.getQuestions());
        mHolder.tv_2.setText(Html.fromHtml(bean.getMobileAnswers()));
    }

    private static  class MyViewHolder extends RecyclerView.ViewHolder {
        View view_top;
        TextView tv_1;
        TextView tv_2;
        View view_bottom;

        MyViewHolder(View itemView) {
            super(itemView);
            view_top = itemView.findViewById(R.id.view_top);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
            tv_2 = (TextView) itemView.findViewById(R.id.tv_2);
            view_bottom = itemView.findViewById(R.id.view_bottom);
        }
    }
}
