package com.hxxc.user.app.ui.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ActionCenterBean;
import com.hxxc.user.app.contract.ActionCenterV;
import com.hxxc.user.app.contract.presenter.ActionCenterPresenter;
import com.hxxc.user.app.data.bean.ShareByTypeBean;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.adapter.ActionCenterAdapter;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.base.SwipeRefreshBaseActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqun on 2016/11/3.
 */

public class ActionCenterActivity extends SwipeRefreshBaseActivity implements ActionCenterV {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;
    private ActionCenterAdapter mAdapter;
    private boolean mIsLoadingmore;
    private ActionCenterPresenter presenter;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_action_center;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("活动中心");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new ActionCenterPresenter(this);
        setPresenter(presenter);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ActionCenterAdapter(this, new ArrayList<>(), recyclerview);

        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                ActionCenterBean bean = mAdapter.mList.get(tag);
                if (null == bean) return;
                Intent intent = new Intent(ActionCenterActivity.this, AdsActivity.class);
                intent.putExtra("title", "活动详情");

                String url = "";
                if (bean.getMobileShowMethod() == 1) {
                    url = bean.getMobileDetailUrl();
                } else if (bean.getMobileShowMethod() == 2) {
                    url = HttpRequest.httpActionCenter + "?id=" + bean.getActivityId() + "&type=activityCentre";
                }

                String finalUrl = url;

                if (url.contains("?")) url = url + "&reqFrom=android";
                else url = url + "?reqFrom=android";

                intent.putExtra("url", url);

                if (bean.getMobileShowMethod() == 2) {
                    finalUrl = finalUrl + "&share=share";
                }

//                final String fUrl = finalUrl;

                String activityType = bean.getActivityType();
                if (TextUtils.isEmpty(activityType)) activityType = "share";

                mApiManager.getSelectShareByType(activityType, new SimpleCallback<List<ShareByTypeBean>>() {
                    @Override
                    public void onNext(List<ShareByTypeBean> shareByTypeBeen) {
                        if (null != shareByTypeBeen && shareByTypeBeen.size() != 0) {
                            ShareByTypeBean shareByTypeBean = shareByTypeBeen.get(0);
                            if (null != shareByTypeBean) {
                                intent.putExtra("isShare", true);
                                intent.putExtra("shareTitle", shareByTypeBean.getShareTitle());
                                intent.putExtra("shareDes", shareByTypeBean.getShareContents());
                                intent.putExtra("shareUrl", shareByTypeBean.getActivityUrl());
                                intent.putExtra("shareImg", shareByTypeBean.getRealShareIcon());
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onError() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        });
                    }
                });
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
                final Picasso picasso = Picasso.with(ActionCenterActivity.this);
                if (scrollState == RecyclerView.SCROLL_STATE_IDLE || scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    picasso.resumeTag(Constants.ActionCenter);
                } else {
                    picasso.pauseTag(Constants.ActionCenter);
                }
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
    public void setDatas(List<ActionCenterBean> list, boolean isLoadingmore) {
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
