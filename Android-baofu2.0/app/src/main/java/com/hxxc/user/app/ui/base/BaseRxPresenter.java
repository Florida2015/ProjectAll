package com.hxxc.user.app.ui.base;

import android.content.Context;

/**
 * Created by Administrator on 2016/10/24.
 */

public abstract class BaseRxPresenter<E, T> {

    public Context context;
    public E mModel;
    public T mView;
    public RxManage mRxManage = new RxManage();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }
    public void setVM(T v) {
        this.mView = v;
        this.onStart();
    }
    public abstract void onStart();

    public void onDestroy() {
        mRxManage.clear();
    }

}
