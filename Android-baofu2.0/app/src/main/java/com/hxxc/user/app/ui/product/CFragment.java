package com.hxxc.user.app.ui.product;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.HelpCenterItemBean;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.adapter.HelpCenterAdapter;
import com.hxxc.user.app.ui.base.BaseFragment2;
import com.hxxc.user.app.widget.verticalpager.CustRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.hxxc.user.app.contract.presenter.RxBasePresenter.mApiManager;

/**
 * Created by chenqun on 2016/9/23.
 */

public class CFragment extends BaseFragment2 {
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.recyclerview)
    CustRecycleView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;
    private HelpCenterAdapter mAdapter;
    private boolean mIsLoadingmore = false;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_product_b;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        recyclerview.setType(3);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HelpCenterAdapter(mContext, new ArrayList<>(), recyclerview);
        mAdapter.rows = 1000;
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoadingmore && mAdapter.hasDatas() && mAdapter.isLoadmore) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1) {
                        mIsLoadingmore = true;
                        mAdapter.notifyDatasChanged(null,true);
                    }
                }
            }
        });
    }

    @Override
    public void initDatas() {
        if (null == v) return;
        String type = v.getProblemType();
        if (TextUtils.isEmpty(type)) {
            isLoading = false;
            return;
        }

        mApiManager.httpGetFaqList(type, new SimpleCallback<List<HelpCenterItemBean>>() {
            @Override
            public void onNext(List<HelpCenterItemBean> helpCenterItemBeen) {
                mAdapter.notifyDatasChanged(helpCenterItemBeen, false);
                progressbar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                    }
                }, 1000);
                if (null == helpCenterItemBeen || helpCenterItemBeen.size() == 0)
                    empty_view.setVisibility(View.VISIBLE);
                else empty_view.setVisibility(View.GONE);
                setRefresh(false);
            }

            @Override
            public void onError() {
                progressbar.setVisibility(View.GONE);
                empty_view.setVisibility(View.VISIBLE);
            }
        });
    }
}
