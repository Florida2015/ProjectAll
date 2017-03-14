package com.hxxc.huaxing.app.ui.mine.notify;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.NotifyBean;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseViewHolder;

/**
 * Created by Administrator on 2016/10/18.
 * 通知列表
 */

public class NotifyVH extends BaseViewHolder<NotifyBean> {

    View view_top_notify;

    TextView tv_notify_name;
    TextView tv_notify_time;

    public NotifyVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.notify_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, NotifyBean obj) {

        if(position==0){
            view_top_notify.setVisibility(View.VISIBLE);
        }else  view_top_notify.setVisibility(View.GONE);


        tv_notify_name.setText(obj.getTitle());
        tv_notify_time.setText(obj.getCreateTimeStr());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查看通知详情
                mContext.startActivity(new Intent(mContext,NotifyDetailActivity.class).putExtra("msgId",""+obj.getId()));
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //删除信息

                return false;
            }
        });
    }

}
