package com.hxxc.user.app.ui.vh;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.NoticeItemBean;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

/**
 * Created by Administrator on 2016/11/10.
 * 通知列表
 *
 */

public class NoticeVH extends BaseViewHolder<NoticeItemBean> {

    TextView tv_notice_data;
    TextView tv_notice_title;
    TextView tv_notice_time;
    TextView tv_notice_des;

    RelativeLayout relative_notice;

    public NoticeVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.notice_item;
    }

    @Override
    public void onBindViewHolder(View view, int position, NoticeItemBean obj) {

        tv_notice_data.setText("2016-10-25 12:23");
        tv_notice_title.setText("title");
        tv_notice_time.setText("2016-12-03");
        tv_notice_des.setText("xxxxxxx");

        relative_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
