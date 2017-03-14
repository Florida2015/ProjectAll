package com.hxxc.huaxing.app.ui.wealth.productlist;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.ui.HuaXingFragment.HomeAdapter;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.base.BaseAdapter2;
import com.hxxc.huaxing.app.ui.wealth.productdetail.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenqun on 2016/10/9.
 */

public class ProductListActivity extends BaseActivity implements ProductListV {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.back)
    ImageButton back;
    private ArrayList<ProductBean> mList;
    private HomeAdapter mAdapter;
    private ProductListPresenter mPresenter;
    private int type;
    private int status;

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_list;
    }

    @Override
    public void initView() {
        back.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        status = intent.getIntExtra("status", 1);
        type = intent.getIntExtra("type", 1);
        toolbar_title.setText(intent.getStringExtra("statustext"));

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        mList = new ArrayList<>();
        mAdapter = new HomeAdapter(this, mList, recyclerview);
        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                if (mAdapter.mList.size() > 0) {
                    Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                    intent.putExtra("pid",mAdapter.mList.get(tag).getId()+"");
                    intent.putExtra("type",type);
                    startActivity(intent);
                }
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
                        mPresenter.onLoadingmore();
                    }
                }
            }
        });
    }

    private boolean mIsLoadingmore = false;

    @Override
    public void initPresenter() {
        setRefresh(true);
        mPresenter = new ProductListPresenter(this,type,status);
        mPresenter.doReflush();
    }

    @Override
    protected void onRefresh() {
        mPresenter.doReflush();
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(List<ProductBean> model, boolean isLoadingmore) {
        mIsLoadingmore = false;
        mAdapter.notifyDatasChanged(model, isLoadingmore);
        if (null != model && model.size() > 0) {
            empty_view.setVisibility(View.GONE);
        } else {
            if (!isLoadingmore) {
                empty_view.setVisibility(View.VISIBLE);
            }
        }
        setRefresh(false);
    }

    @Override
    public void onError() {
        setRefresh(false);
        empty_view.setVisibility(View.VISIBLE);
    }
}
