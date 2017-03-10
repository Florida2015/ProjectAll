package com.hxxc.user.app.ui.pager.product;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Product;
import com.hxxc.user.app.contract.ProductContract;
import com.hxxc.user.app.contract.presenter.ProductPresenter;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.adapter.ProductAdapter;
import com.hxxc.user.app.ui.pager.BasePager;
import com.hxxc.user.app.ui.product.ProductDetailActivity;
import com.hxxc.user.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenqun on 2016/11/18.
 */

public class ProductPager1 extends BasePager implements ProductContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;
    private Unbinder unbinder;
    private ProductPresenter presenter;
    private ProductAdapter mAdapter;
    private boolean mIsLoadingmore = false;

    public ProductPager1(Context context, int type) {
        super(context);
        presenter = new ProductPresenter(this, type);
    }

    @Override
    public void onDestroy() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.view_reflush_recyclerview, null);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }


    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ProductAdapter(mContext, new ArrayList<Product>(), recyclerview);

        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("pid", mAdapter.mList.get(tag).getId() + "");
                mContext.startActivity(intent);
            }

            @Override
            public void setOnItemLongClick(View view, int tag) {

            }
        });
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LogUtil.e("recyclerViewTop == "+recyclerView.getChildAt(0).getTop());
                if (null != mSwipeRefreshLayout) {
                    int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                    mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
                }

                if (!mIsLoadingmore && mAdapter.hasDatas() && mAdapter.isLoadmore) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1) {
                        mIsLoadingmore = true;
                        presenter.onLoadingmore();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void initData() {
        recyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.subscribe();
            }
        }, 500);
        setRefresh(true);
    }

    @Override
    public void setProductList(List<Product> datas, boolean isLoadingmore) {
        mIsLoadingmore = false;
        mAdapter.notifyDatasChanged(datas, isLoadingmore);
        if (null != datas && datas.size() > 0) {
            empty_view.setVisibility(View.GONE);
        } else {
            if (!isLoadingmore) {
                empty_view.setVisibility(View.VISIBLE);
            }
        }
        setRefresh(false);
    }

    @Override
    protected void onRefresh() {
        presenter.doReflush();
    }
}
