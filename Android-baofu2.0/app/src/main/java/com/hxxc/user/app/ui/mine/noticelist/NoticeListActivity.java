package com.hxxc.user.app.ui.mine.noticelist;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.vh.NoticeVH;
import com.hxxc.user.app.widget.trecyclerview.TRecyclerView;

import butterknife.BindView;

/**
 * Created by houwen.lai on 2016/11/7.
 * 通知列表
 * 消息列表
 */

public class NoticeListActivity extends BaseRxActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recyclerview_notice)
    TRecyclerView mXRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.notice_list_activity;
    }


    @Override
    public void initView() {
        toolbar_title.setText("消息列表");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


        mXRecyclerView.setParam("","");
        mXRecyclerView.setView(NoticeVH.class);
        mXRecyclerView.reFetch();

    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
