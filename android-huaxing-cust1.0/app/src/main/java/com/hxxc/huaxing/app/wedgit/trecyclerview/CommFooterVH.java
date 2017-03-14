package com.hxxc.huaxing.app.wedgit.trecyclerview;


import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;

public class CommFooterVH extends BaseViewHolder<Object> {

    public ProgressBar progressbar;
    public TextView tv_state;
    public static final int LAYOUT_TYPE = R.layout.list_footer_view;

//    @BindView(R.id.progressbar)
//    ProgressBar progressbar;
//    @BindView(R.id.tv_state)
//    TextView tv_state;

    public CommFooterVH(View view) {
        super(view);
    }

    @Override
    public int getType() {
        return LAYOUT_TYPE;
    }

    @Override
    public void onBindViewHolder(View view,int position,Object o) {
        boolean isHasMore = (o == null ? false : true);
        progressbar.setVisibility(isHasMore ?View.GONE :View.GONE);//
        tv_state.setText(isHasMore ? "正在加载":(position==0?"":"已经到底"));//(position==0?"暂无数据":"已经到底")
    }
}