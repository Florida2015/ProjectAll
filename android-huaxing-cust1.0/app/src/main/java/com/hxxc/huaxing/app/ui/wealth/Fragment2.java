//package com.hxxc.huaxing.app.ui.wealth;
//
//
//import android.content.Intent;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.FrameLayout;
//
//import com.hxxc.huaxing.app.R;
//import com.hxxc.huaxing.app.data.bean.ProductBean;
//import com.hxxc.huaxing.app.ui.base.BaseAdapter2;
//import com.hxxc.huaxing.app.ui.wealth.productdetail.ProductDetailActivity;
//import com.hxxc.huaxing.app.ui.wealth.productlist.ProductListActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by chenqun on 2016/9/22.
// */
//
//public class Fragment2 extends BaseFragment2 implements WealthV {
//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.empty_view)
//    FrameLayout empty_view;
//    private ArrayList<Object> mList;
//    private Fragment1Adapter mAdapter;
//    private boolean mIsLoadingmore = false;
//    private WealthPresenter mPresenter;
//
//    @Override
//    protected int getContentViewID() {
//        return R.layout.fragment_wealth_1;
//    }
//
//    @Override
//    protected void initViewsAndEvents(View rootView) {
//        ButterKnife.bind(this,rootView);
////        setRefresh(true);
//        initRecyclerView();
//        mPresenter = new WealthPresenter(Fragment2.this,Fragment1.Type_Zhaiquan,getActivity());
//
//        rootView.post(new Runnable() {
//            @Override
//            public void run() {
//                setRefresh(true);
//                mPresenter.doReflush();
//            }
//        });
//    }
//    private void initRecyclerView() {
//        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
//        mList = new ArrayList<>();
//        mAdapter = new Fragment1Adapter(mContext, mList, recyclerview,2);
//        //设置条目头部点击事件
//        mAdapter.setOnHeadItemClick(new BaseAdapter2.OnHeadItemClick() {
//            @Override
//            public void setOnHeadItemClick(View view, int tag, int status,String statustext) {
//                toast(status+"");
//                Intent intent = new Intent(getActivity(), ProductListActivity.class);
//                intent.putExtra("statustext",statustext);
//                intent.putExtra("status",status);
//                getActivity().startActivity(intent);
//            }
//        });
//        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
//            @Override
//            public void setOnItemClick(View view, int tag) {
//                toast(tag+"");
//                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
//                intent.putExtra("pid",mAdapter.mList.get(tag).getId());
//                startActivity(intent);
//            }
//
//            @Override
//            public void setOnItemLongClick(View view, int tag) {
//
//            }
//        });
//        recyclerview.setAdapter(mAdapter);
//        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (!mIsLoadingmore && mAdapter.hasDatas() && mAdapter.isLoadmore) {
//                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
//                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1) {
////                        mIsLoadingmore = true;
////                        presenter.onLoadingmore();
//                    }
//                }
//            }
//        });
//    }
//    @Override
//    protected void onRefresh() {
//        super.onRefresh();
//        mPresenter.doReflush();
//    }
//
//    @Override
//    public void onSuccess(List<ProductBean> datas, boolean isLoadingmore) {
//        mAdapter.notifyDatasChanged(datas, false);
//        if (null != datas && datas.size() > 0) {
//            empty_view.setVisibility(View.GONE);
//        } else {
//            empty_view.setVisibility(View.VISIBLE);
//        }
//        setRefresh(false);
//    }
//
//    @Override
//    public void onError() {
//        setRefresh(false);
//        empty_view.setVisibility(View.VISIBLE);
//    }
//}
