package com.hxxc.huaxing.app.ui.wealth;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.wealth.productdetail.ProductDetailVertical2V;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;

import static com.hxxc.huaxing.app.R.id.swipe_refresh_layout;

/**
 * Created by chenqun on 2016/9/22.
 */

public abstract  class BaseFragment2 extends RxFragment {
    protected Context mContext;
    public  boolean isLoading = false;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            return inflater.inflate(getContentViewID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(swipe_refresh_layout);
        trySetupSwipeRefresh();
        initViewsAndEvents(view);
    }
    void trySetupSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                    R.color.colorPrimaryDark, R.color.colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override public void onRefresh() {
                    BaseFragment2.this.onRefresh();
                }
            });
        }
    }
    public void setRefresh(boolean requestDataRefresh) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            // 防止刷新消失太快，让子弹飞一会儿.
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override public void run() {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 600);
        } else {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            },100);
        }
    }

    //子类需要重写
    protected void onRefresh() {

    }

    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getContentViewID();


    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViewsAndEvents(View rootView);

    /**
     * toast
     */
    public void toast(@NonNull CharSequence text) {
        Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast
     */
    public void toast(@StringRes int stringRes) {
        Toast.makeText(getActivity().getApplicationContext(), stringRes, Toast.LENGTH_SHORT).show();
    }

    public  void initDatas(){
    }
    protected ProductDetailVertical2V v;
    public void setV(ProductDetailVertical2V view){
        this.v = view;
    }

    @Override
    public void onDestroy() {
        v = null;
        super.onDestroy();
    }
}
