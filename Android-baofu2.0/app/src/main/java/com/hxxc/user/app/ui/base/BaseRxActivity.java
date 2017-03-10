package com.hxxc.user.app.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.hxxc.user.app.R;
import com.hxxc.user.app.utils.LogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;

/**
 * Created by houwen.lai on 2016/10/24.
 */

public abstract class BaseRxActivity<T extends BaseRxPresenter, E extends BaseRxModel> extends RxAppCompatActivity {

    public T mPresenter;
    public E mModel;
    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());

        //友盟推送应用统计
        PushAgent.getInstance(this).onAppStart();
        ButterKnife.bind(this);

        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);

        this.initView();
        this.initPresenter();
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

    //用于友盟，百度统计
    public boolean isMobclick = true;
    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("---------onResume ");
        if(isMobclick) {
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
        LogUtil.i("---------onPause ");
        if(isMobclick) {
            MobclickAgent.onPause(this);
            TextView title = (TextView) findViewById(R.id.toolbar_title);
            if (null != title && !TextUtils.isEmpty(title.getText().toString()) && !"华夏信财".equals(title.getText().toString()))
                StatService.onPageEnd(this, title.getText().toString());
            else
                StatService.onPause(this);
        }
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
