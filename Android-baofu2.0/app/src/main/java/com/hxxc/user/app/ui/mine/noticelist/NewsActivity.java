package com.hxxc.user.app.ui.mine.noticelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.contract.NewsV;
import com.hxxc.user.app.contract.presenter.NewsPresenter;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.adapter.NewsAdapter;
import com.hxxc.user.app.ui.base.SwipeRefreshBaseActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqun on 2016/11/17.
 * 新闻公告
 */

public class NewsActivity extends SwipeRefreshBaseActivity implements NewsV {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;
    private ArrayList<ContentBean> mList;
    private NewsAdapter mAdapter;
    private NewsPresenter presenter;
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
        mTitle.setText("新闻公告");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new NewsPresenter(this);
        setPresenter(presenter);
        initView();
        setRefresh(true);
    }

    private void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        mAdapter = new NewsAdapter(this, mList, recyclerview);

        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                Intent intent = new Intent(NewsActivity.this, AdsActivity.class);
                intent.putExtra("title", "公告详情");
                String url = HttpRequest.httpActionCenter + "?id=" + mAdapter.mList.get(tag).getId();
                intent.putExtra("url", url);
                startActivity(intent);
            }

            @Override
            public void setOnItemLongClick(View view, int tag) {

            }
        });
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoadingmore && mAdapter.hasDatas() && mAdapter.isLoadmore) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1) {
                        mIsLoadingmore = true;
                        presenter.onLoadingmore(true);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);
                final Picasso picasso = Picasso.with(NewsActivity.this);
                if (scrollState == RecyclerView.SCROLL_STATE_IDLE || scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    picasso.resumeTag(Constants.News);
                } else {
                    picasso.pauseTag(Constants.News);
                }
            }
        });
    }

    @Override
    public void setDatas(List<ContentBean> list, boolean isLoadingmore) {
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
