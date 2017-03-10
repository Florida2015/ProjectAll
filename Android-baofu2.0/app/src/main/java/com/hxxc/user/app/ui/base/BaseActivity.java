package com.hxxc.user.app.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.hxxc.user.app.R;
import com.hxxc.user.app.contract.presenter.RxBasePresenter;
import com.hxxc.user.app.rest.ApiManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenqun on 2016/6/20.
 */
public class BaseActivity extends AppCompatActivity {
    public final ApiManager mApiManager = ApiManager.getInstance();

    public RxBasePresenter mPresenter;

    public void setPresenter(RxBasePresenter presenter) {
        this.mPresenter = presenter;
    }

    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //友盟推送应用统计
        PushAgent.getInstance(this).onAppStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != mPresenter) {
                    mPresenter.subscribe();
                }
            }
        }, 100);
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    //用于友盟,百度统计
    public boolean isMobclick = true;

    @Override
    protected void onResume() {
        super.onResume();
        if (isMobclick) {
            MobclickAgent.onResume(this);
            TextView title = (TextView) findViewById(R.id.toolbar_title);
            if (null != title && !TextUtils.isEmpty(title.getText().toString()) && !"华夏信财".equals(title.getText().toString()))
                StatService.onPageStart(this, title.getText().toString());
            else
                StatService.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isMobclick) {
            MobclickAgent.onPause(this);
            TextView title = (TextView) findViewById(R.id.toolbar_title);
            if (null != title && !TextUtils.isEmpty(title.getText().toString()) && !"华夏信财".equals(title.getText().toString()))
                StatService.onPageEnd(this, title.getText().toString());
            else
                StatService.onPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (null != mPresenter) {
            mPresenter.unsubscribe();
            mPresenter = null;
        }
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}
