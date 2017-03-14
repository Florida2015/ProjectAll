package com.hxxc.huaxing.app.ui.HuaXingFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.wedgit.CircleView;

/**
 * Created by chenqun on 2016/9/28.
 */

public class HomeViewHolder extends RecyclerView.ViewHolder {
    public final LinearLayout ll_mark;
    public final LinearLayout ll_top;
    public final View view_line;
    public final TextView tv_top;
    public final TextView tv_more;
    public final LinearLayout ll_content;

    public final TextView tv_right;
    public CircleView circle_progress;
    public final TextView tv_title;
    public final TextView tv_rate;
    public final TextView tv_deadline;
    public final TextView tv_investable_money;

    public HomeViewHolder(View view) {
        super(view);
        ll_mark = (LinearLayout) view.findViewById(R.id.ll_mark);
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        ll_top = (LinearLayout) view.findViewById(R.id.ll_top);
        view_line = (View) view.findViewById(R.id.view_line);
        tv_top = (TextView) view.findViewById(R.id.tv_top);
        tv_more = (TextView) view.findViewById(R.id.tv_more);

        tv_right = (TextView) view.findViewById(R.id.tv_right);
        circle_progress = (CircleView) view.findViewById(R.id.circle_progress);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_rate = (TextView) view.findViewById(R.id.tv_rate);
        tv_deadline = (TextView) view.findViewById(R.id.tv_deadline);
        tv_investable_money = (TextView) view.findViewById(R.id.tv_investable_money);
    }
}
