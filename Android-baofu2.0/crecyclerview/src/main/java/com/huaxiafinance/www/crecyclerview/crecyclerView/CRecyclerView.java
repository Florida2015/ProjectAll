package com.huaxiafinance.www.crecyclerview.crecyclerView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huaxiafinance.www.crecyclerview.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by chenqun on 2017/2/17.
 */

public class CRecyclerView<T> extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private int row = 8;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<T, BaseViewHolder> mAdapter;
    private BaseCEntity<T> model;
    private View notDataView;
    private View errorView;
    private View loadingView;

    private Map<String, String> mParams;

    public CRecyclerView(Context context) {
        this(context, null);
    }

    public CRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        View layout = LayoutInflater.from(getContext()).inflate(
                R.layout.crecyclerview_base, this);
//        layout.setLayoutParams(new LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        addView(layout);

        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swiperefresh);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        loadingView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_loading, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_empty, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.findViewById(R.id.tv_empty).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        errorView = LayoutInflater.from(context).inflate(R.layout.crecyclerview_error, (ViewGroup) mRecyclerView.getParent(), false);
        errorView.findViewById(R.id.tv_error).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    public void setAdapter(BaseQuickAdapter<T, BaseViewHolder> adapter) {
        this.mAdapter = adapter;
        this.mAdapter.setOnLoadMoreListener(this);
//        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                model.onClick(getContext(), mAdapter.getItem(position));
            }
        });
        mAdapter.setEmptyView(loadingView);
        mSwipeRefreshLayout.setEnabled(false);
    }

    public CRecyclerView setView(Class<? extends BaseCEntity> cla) {
        try {
            model = (BaseCEntity<T>) cla.getConstructor().newInstance();
            if (null == mAdapter) setAdapter(new PullToRefreshAdapter());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public CRecyclerView setParams(String key, String value) {
        if (null == mParams) mParams = new HashMap<>();
        mParams.put(key, value);
        return this;
    }

    public CRecyclerView setRow(int row) {
        this.row = row;
        return this;
    }

    private boolean isStarted;

    public CRecyclerView start() {
        if (!isStarted) {
            onRefresh();
            isStarted = true;
        }
        return this;
    }

    private int index = 1;

    @Override
    public void onLoadMoreRequested() {
        if (model == null) {
            Log.e("model", "null");
            return;
        }
        model.setParam(mParams);
        if (model.getPageAt(index, row) == null) return;

        index++;

        mSwipeRefreshLayout.setEnabled(false);
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                model.getPageAt(index, row)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<BaseResult<List<T>>>() {
                                       @Override
                                       public void call(BaseResult<List<T>> subjects) {
                                           if (!subjects.success) {
                                               index--;
                                               mAdapter.loadMoreFail();

                                           } else {
                                               mAdapter.addData(subjects.getData());
                                               if (subjects.getData().size() < row)
                                                   mAdapter.loadMoreEnd(false);
                                               else mAdapter.loadMoreComplete();
                                           }
                                           mSwipeRefreshLayout.setEnabled(true);
                                       }
                                   }, new Action1<Throwable>() {
                                       @Override
                                       public void call(Throwable e) {
                                           index--;
                                           mAdapter.loadMoreFail();
                                           mSwipeRefreshLayout.setEnabled(true);
                                           e.printStackTrace();
                                       }
                                   }
                        );
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        if (model == null) {
            Log.e("model", "null");
            return;
        }
        model.setParam(mParams);
        if (model.getPageAt(index, row) == null) return;

        index = 1;

        if (mAdapter.getData() == null || mAdapter.getData().size() <= 0) {
            mAdapter.setEmptyView(loadingView);
            mSwipeRefreshLayout.setEnabled(false);
        }

        mAdapter.setEnableLoadMore(false);

        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("CRecyclerView", "onRefresh");
                model.getPageAt(index, row)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<BaseResult<List<T>>>() {
                                       @Override
                                       public void call(BaseResult<List<T>> subjects) {
                                           if (!subjects.success) {
                                               mAdapter.setNewData(null);
                                               mAdapter.setEmptyView(errorView);
                                           } else {
                                               mAdapter.setNewData(subjects.getData());
                                               if (subjects.getData().size() == 0)
                                                   mAdapter.setEmptyView(notDataView);
                                               else {
                                                   if (subjects.getData().size() < row) {
                                                       mAdapter.loadMoreEnd(true);
                                                   }
                                                   mSwipeRefreshLayout.setEnabled(true);
                                               }
                                           }
                                           mAdapter.setEnableLoadMore(true);
                                           mSwipeRefreshLayout.setRefreshing(false);
                                       }
                                   }, new Action1<Throwable>() {
                                       @Override
                                       public void call(Throwable e) {
                                           mAdapter.setNewData(null);
                                           mAdapter.setEmptyView(errorView);
                                           mAdapter.setEnableLoadMore(true);
                                           e.printStackTrace();
                                           mSwipeRefreshLayout.setRefreshing(false);
                                       }
                                   }
                        );
            }
        }, 1000);

    }

    class PullToRefreshAdapter extends BaseQuickAdapter<T, BaseViewHolder> {
        PullToRefreshAdapter() {
            super(model == null ? 0 : model.getItemLayou(), null);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            if (model == null) {
                Log.e("model", "null");
                return;
            }
            model.convert(helper, item);
        }
    }
}
