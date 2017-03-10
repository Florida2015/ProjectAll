package com.hxxc.user.app.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.widget.CircleView;

/**
 * Created by chenqun on 2016/9/28.
 */

public class HomeViewHolder extends RecyclerView.ViewHolder {
    public final LinearLayout ll_content;
    public final LinearLayout ll_mark;
    public CircleView circle_progress;
    public final TextView tv_title;
    public final TextView tv_rate;
    public final TextView tv_deadline;
    public final TextView tv_investable_money;

    public HomeViewHolder(View view) {
        super(view);
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        ll_mark = (LinearLayout) view.findViewById(R.id.ll_mark);
        circle_progress = (CircleView) view.findViewById(R.id.circle_progress);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_rate = (TextView) view.findViewById(R.id.tv_rate);
        tv_deadline = (TextView) view.findViewById(R.id.tv_deadline);
        tv_investable_money = (TextView) view.findViewById(R.id.tv_investable_money);
    }
}
