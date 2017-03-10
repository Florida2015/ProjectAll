package com.hxxc.user.app.ui.pager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
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
import com.hxxc.user.app.ui.product.ProductDetailActivity;
import com.hxxc.user.app.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenqun on 2016/8/18.
 */
public class ProductPager extends BaseTitlePager implements ProductContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;

    private Unbinder unbinder;
    private ProductPresenter presenter;
    private ProductAdapter mAdapter;
    private boolean mIsLoadingmore = false;

    public ProductPager(Activity activity) {
        super(activity);
        initRootView();
    }

    private void initRootView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(mContext);
            getRootView().setPadding(0, statusBarHeight, 0, 0);
        }
    }

    @Override
    protected void setTitle() {
        mTitle.setText("财富列表");
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.activity_product, null);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        presenter = new ProductPresenter(this,1);
        return view;
    }

    @Override
    public void onDestroy() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ProductAdapter(mContext, new ArrayList<Product>(), recyclerview);

        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("pid",mAdapter.mList.get(tag).getId()+"");
                mContext.startActivity(intent);
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
                        presenter.onLoadingmore();
                    }
                }
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
        },500);
        setRefresh(true);
    }

    @Override
    public void setProductList(List<Product> datas, boolean isLoadingmore) {
        mIsLoadingmore = false;
        mAdapter.notifyDatasChanged(datas, isLoadingmore);
        if (null != datas && datas.size() > 0) {
            empty_view.setVisibility(View.GONE);
        } else {
            if(!isLoadingmore){
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
