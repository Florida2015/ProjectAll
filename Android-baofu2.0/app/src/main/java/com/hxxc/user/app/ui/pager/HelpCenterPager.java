package com.hxxc.user.app.ui.pager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.HelpCenterItemBean;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.adapter.HelpCenterAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by chenqun on 2016/11/9.
 */

public class HelpCenterPager extends BasePager {
    private final String mIndex;
    private FrameLayout empty_view;
    private RecyclerView recyclerview;
    private HelpCenterAdapter adapter;

    public HelpCenterPager(Context context, String index) {
        super(context);
        this.mIndex = index;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.view_reflush_recyclerview, null);
        empty_view = (FrameLayout) view.findViewById(R.id.empty_view);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setHasFixedSize(true);

        adapter = new HelpCenterAdapter(mContext, new ArrayList<HelpCenterItemBean>(), recyclerview);
        adapter.rows = 1000;
        recyclerview.setAdapter(adapter);
        return view;
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        getData();
    }

    @Override
    public void initData() {
        isLoading = true;
        setRefresh(true);
        recyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 500);
    }

    private void getData() {
        Subscription s = mApiManager.httpGetFaqList(mIndex, new SimpleCallback<List<HelpCenterItemBean>>() {
            @Override
            public void onNext(List<HelpCenterItemBean> helpCenterItemBeen) {
                adapter.notifyDatasChanged(helpCenterItemBeen, false);
                if (null == helpCenterItemBeen || helpCenterItemBeen.size() == 0)
                    empty_view.setVisibility(View.VISIBLE);
                else empty_view.setVisibility(View.GONE);
                setRefresh(false);
            }

            @Override
            public void onError() {
                empty_view.setVisibility(View.VISIBLE);
            }
        });
        addSubscription(s);
    }
}
