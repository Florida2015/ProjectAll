package com.hxxc.user.app.ui.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.mobstat.StatService;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.contract.DiscoveryContract;
import com.hxxc.user.app.contract.presenter.DiscoveryPresenter;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.adapter.DiscoveryAdapter;
import com.hxxc.user.app.ui.discovery.ActionCenterActivity;
import com.hxxc.user.app.ui.discovery.DepartmentActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by chenqun on 2016/8/18.
 */
public class DiscoveryPager extends BaseTitlePager implements DiscoveryContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;

    private Unbinder unbinder;
    private ArrayList<ContentBean> mList;
    private DiscoveryAdapter mAdapter;
    private boolean mIsLoadingmore;
    private DiscoveryPresenter presenter;
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(mContext,"发现");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(mContext,"发现");
    }

    public DiscoveryPager(Context context) {
        super(context);
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
        mTitle.setText("发现");
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.activity_discovery, null);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        presenter = new DiscoveryPresenter(this, DiscoveryPresenter.TYPE_4);
        return view;
    }


    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mList = new ArrayList<>();
        mAdapter = new DiscoveryAdapter(mContext, mList, recyclerview);

        mAdapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                ContentBean bean = mAdapter.mList.get(tag);
                if (null == bean) return;

                Intent intent = new Intent(mContext, AdsActivity.class);
                intent.putExtra("title", "文章详情");

                intent.putExtra("isShare", true);
                intent.putExtra("shareTitle", bean.getTitle());
                intent.putExtra("shareDes", bean.getIntroduction());
                intent.putExtra("shareImg", bean.getRealShareIcon());

                String url = HttpRequest.httpActionCenter + "?id=" + bean.getId();
                intent.putExtra("shareUrl", url+"&share=share");
                intent.putExtra("url", url);
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
                        presenter.onLoadingmore(true);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                super.onScrollStateChanged(recyclerView, scrollState);
                final Picasso picasso = Picasso.with(mContext);
                if (scrollState == RecyclerView.SCROLL_STATE_IDLE || scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    picasso.resumeTag(Constants.Discovery);
                } else {
                    picasso.pauseTag(Constants.Discovery);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void initData() {
        setRefresh(true);
        presenter.subscribe();
    }

    @Override
    protected void onRefresh() {
        presenter.doReflush();
    }

    @OnClick({R.id.textview1, R.id.textview2, R.id.textview3})
    public void onClick(View view) {
        if (BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.textview1://积分商城
//                    presenter.getMailUrl();
                    toMail(HttpRequest.UrlMail);
                    break;
                case R.id.textview2://财富门店
                    mContext.startActivity(new Intent(mContext, DepartmentActivity.class));
                    break;
                case R.id.textview3://活动中心
                    mContext.startActivity(new Intent(mContext, ActionCenterActivity.class));
                    break;
            }
        }
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

    //    打开积分商城页面
    @Override
    public void toMail(String s) {
        Intent intent = new Intent(mContext, AdsActivity.class);
        intent.putExtra("url", s);
        intent.putExtra("title", Constants.TEXT_INTEGRAL);
        mContext.startActivity(intent);
    }
}
