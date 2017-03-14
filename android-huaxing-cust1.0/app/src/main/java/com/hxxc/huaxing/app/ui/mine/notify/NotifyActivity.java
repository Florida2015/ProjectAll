package com.hxxc.huaxing.app.ui.mine.notify;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.wedgit.trecyclerview.TRecyclerView;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/18.
 * 通知 页
 */

public class NotifyActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.trecycler_listview_notify)
    TRecyclerView mXRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.notify_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("通知");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mXRecyclerView.setView(NotifyVH.class);

        mXRecyclerView.fetch();

    }

    @Override
    public void initPresenter() {
        if (mXRecyclerView != null) mXRecyclerView.reFetch();
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
