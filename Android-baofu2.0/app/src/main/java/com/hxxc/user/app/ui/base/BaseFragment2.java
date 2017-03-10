package com.hxxc.user.app.ui.base;

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

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.contract.ProductDetailVertical2V;
import com.hxxc.user.app.widget.MultiSwipeRefreshLayout;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;

import static com.hxxc.user.app.R.id.swipe_refresh_layout;

/**
 * Created by chenqun on 2016/9/22.
 */

public abstract  class BaseFragment2 extends RxFragment {
    protected Context mContext;

    protected MultiSwipeRefreshLayout mSwipeRefreshLayout;
    protected ProductDetailVertical2V v;

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
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) view.findViewById(swipe_refresh_layout);
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
//            mSwipeRefreshLayout.setAnimateToRefreshDuration(1000);
//            mSwipeRefreshLayout.setAnimateToStartDuration(1000);
//            mSwipeRefreshLayout.setDragDistanceConverter(new IDragDistanceConverter() {
//                @Override
//                public float convert(float scrollDistance, float refreshDistance) {
//                    return scrollDistance * 0.5f;
//                }
//            });
//            mSwipeRefreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    BaseFragment2.this.onRefresh();
//                }
//            });
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
                    postReflush(false);
                }

            }, Constants.Delay_Reflush_false);
//            postReflush(false);
        } else {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    postReflush(true);
                }
            },Constants.Delay_Reflush_true);
//            postReflush(false);
        }
    }
    private void postReflush(boolean b) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(b);
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

    public  boolean isLoading = false;
    public  void initDatas(){
    }

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

    // 设置SwipeRefreshLayout可以滚动的孩子
    protected void setScrollUpChild(){

    }

    public void setV(ProductDetailVertical2V view){
        this.v = view;
    }

    @Override
    public void onDestroy() {
        v = null;
        super.onDestroy();
    }
}
