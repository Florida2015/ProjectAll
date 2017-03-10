package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.NoticeBean;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.widget.LeftAndRightTextView;

import java.util.List;

/**
 * Created by chenqun on 2016/11/17.
 */

public class NoticesAdapter extends BaseAdapter2<NoticeBean.UserMessageVoBean> {
    public NoticesAdapter(Context mContext, List<NoticeBean.UserMessageVoBean> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notices, parent,false));
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder mHolder = (MyViewHolder) holder;
        NoticeBean.UserMessageVoBean bean = mList.get(position);
        mHolder.tv_time.setText(CommonUtil.formatDate(bean.getCreateTime()));
        mHolder.tv_1.setText(bean.getTitle());
        mHolder.tv_3.setText(bean.getContent());
        mHolder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener){
                    mListener.setOnItemClick(mHolder.ll_content,position);
                }
            }
        });

        if(bean.getBizType() != 2 && bean.getBizType() != 3 && bean.getBizType() != 4){
            mHolder.view_line.setVisibility(View.GONE);
            mHolder.lrtv_button.setVisibility(View.GONE);
        }
    }

    private static  class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_content;
        TextView tv_time;
        TextView tv_1;
        TextView tv_3;

        View view_line;
        LeftAndRightTextView lrtv_button;

        MyViewHolder(View itemView) {
            super(itemView);
            ll_content = (LinearLayout) itemView.findViewById(R.id.ll_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_3);
            view_line =  itemView.findViewById(R.id.view_line);

            lrtv_button = (LeftAndRightTextView) itemView.findViewById(R.id.lrtv_button);
        }
    }
}
