package com.hxxc.user.app.ui.mine.noticelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.NoticeBean;
import com.hxxc.user.app.contract.NoticesV;
import com.hxxc.user.app.contract.presenter.NoticesPresenter;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.adapter.NoticesAdapter;
import com.hxxc.user.app.ui.base.SwipeRefreshBaseActivity;
import com.hxxc.user.app.ui.mine.integral.IntegralListActivity;
import com.hxxc.user.app.ui.mine.redpackage.RedPackageActivity;
import com.hxxc.user.app.ui.order.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqun on 2016/11/17.
 * 通知提醒
 */

public class NoticesActivity extends SwipeRefreshBaseActivity implements NoticesV {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;
    private NoticesPresenter presenter;
    private NoticesAdapter mAdapter;
    private boolean mIsLoadingmore;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.modle_recycleview;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("通知提醒");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new NoticesPresenter(this);
        setPresenter(presenter);
        initView();
    }

    private void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NoticesAdapter(this, new ArrayList<>(), recyclerview);
        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                //TODO 跳转到订单详情页,红包列表页
                //1.系统：2.活动(红包等),3.积分,4.订单
                NoticeBean.UserMessageVoBean bean = mAdapter.mList.get(tag);
                int type = bean.getBizType();
                switch (type) {
//                    case 1:
//                        break;
                    case 2:
                        startActivity(new Intent(NoticesActivity.this, RedPackageActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(NoticesActivity.this, IntegralListActivity.class));
                        break;
                    case 4:
                        Intent intent = new Intent(NoticesActivity.this, OrderDetailActivity.class);
                        intent.putExtra("orderNo", bean.getBizValue());
                        startActivity(intent);
                        break;
                }
//                presenter.readNotice(bean.getId() + "");
            }

            @Override
            public void setOnItemLongClick(View view, int tag) {

            }
        });
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (null != mSwipeRefreshLayout) {
//                    int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
//                    mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
//                }
                if (!mIsLoadingmore && mAdapter.hasDatas() && mAdapter.isLoadmore) {
//                    if (null != mSwipeRefreshLayout && mSwipeRefreshLayout.isRefreshing()) return;
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1) {
                        mIsLoadingmore = true;
                        presenter.onLoadingmore(true);
                    }
                }
//                super.onScrolled(recyclerView, dx, dy);
            }
        });
        recyclerview.post(new Runnable() {
            @Override
            public void run() {
                setRefresh(true);
            }
        });
    }

    @Override
    public void setDatas(List<NoticeBean.UserMessageVoBean> list, boolean isLoadingmore) {
        mAdapter.notifyDatasChanged(list, isLoadingmore);
        if (null != list && list.size() > 0) {
            empty_view.setVisibility(View.GONE);
        } else {
            if (!isLoadingmore) {
                empty_view.setVisibility(View.VISIBLE);
            }
        }
        setRefresh(false);
        mIsLoadingmore = false;
    }
}
