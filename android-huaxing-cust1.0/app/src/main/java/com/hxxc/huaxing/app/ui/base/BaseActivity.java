package com.hxxc.huaxing.app.ui.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

import static com.hxxc.huaxing.app.R.id.swipe_refresh_layout;


/**
 * Created by Administrator on 2016/4/5.
 * 基类
 */
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends RxAppCompatActivity {

    public T mPresenter;
    public E mModel;
    public Context mContext;
    public SwipeRefreshLayout mSwipeRefreshLayout;

    public boolean isTranslucentBar(){
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        beforeOnCreate();
        if(isTranslucentBar()){
            ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                parentView.setFitsSystemWindows(true);
            }
        }
        setRefreshView();
        ButterKnife.bind(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);

        this.initView();
        this.initPresenter();

        AppManager.getAppManager().addActivity(this);
    }

    private void setRefreshView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(swipe_refresh_layout);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                    R.color.colorPrimaryDark, R.color.colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override public void onRefresh() {
                    BaseActivity.this.onRefresh();
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

    protected void onRefresh() {

    }

    public void beforeOnCreate() {

    }

    public abstract int getLayoutId();

    public abstract void initView();

    /**
     * 简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
     * mPresenter.setVM(this, mModel);
     */
    public abstract void initPresenter();


    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("---------onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("---------onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("---------onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i("---------onStop ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("---------onDestroy ");
        if (mPresenter != null)
            mPresenter.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * toast
     */
    public void toast(@NonNull CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast
     */
    public void toast(@StringRes int stringRes) {
        Toast.makeText(this, stringRes, Toast.LENGTH_SHORT).show();
    }

}
