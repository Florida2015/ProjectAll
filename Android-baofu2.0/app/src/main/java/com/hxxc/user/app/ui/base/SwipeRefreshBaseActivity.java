package com.hxxc.user.app.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.widget.MultiSwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqun on 2016/6/21.
 */
public abstract class SwipeRefreshBaseActivity extends ToolbarActivity {

    @BindView(R.id.swipe_refresh_layout)
    @Nullable public MultiSwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        trySetupSwipeRefresh();
    }
    void trySetupSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                    R.color.colorPrimaryDark, R.color.colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override public void onRefresh() {
                    if (null != mPresenter){
                        mPresenter.doReflush();
                    }else {
                        SwipeRefreshBaseActivity.this.onReflush();
                    }
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
//                    mPresenter.doReflush();
//                }
//            });
        }
    }

    public void onReflush(){

    }

    public void setRefresh(boolean requestDataRefresh) {

        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            // 防止刷新消失太快，让子弹飞一会儿.
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                }
            }, Constants.Delay_Reflush_false);
//            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            },Constants.Delay_Reflush_true);
//            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
    private void postReflush(boolean b) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(b);
        }
    }
}
