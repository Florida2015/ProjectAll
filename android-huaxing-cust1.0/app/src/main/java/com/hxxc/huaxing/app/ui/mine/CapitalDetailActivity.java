package com.hxxc.huaxing.app.ui.mine;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.mine.vh.CapitailDetailVH;
import com.hxxc.huaxing.app.wedgit.trecyclerview.TRecyclerView;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/13.
 * 资金明细 页
 */

public class CapitalDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.trecycler_listview_capital)
    TRecyclerView trecycler_listview;

    @Override
    public int getLayoutId() {
        return R.layout.mine_capital_detail;
    }

    @Override
    public void initView() {
        toolbar_title.setText("资金明细");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        trecycler_listview.setView(CapitailDetailVH.class);
        if (trecycler_listview!=null)trecycler_listview.fetch();


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
