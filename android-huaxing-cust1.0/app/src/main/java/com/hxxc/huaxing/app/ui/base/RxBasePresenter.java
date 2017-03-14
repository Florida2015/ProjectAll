package com.hxxc.huaxing.app.ui.base;

import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.ApiService;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenqun on 2016/10/8.
 */

public abstract class RxBasePresenter {
    public static final ApiService mApi = Api.getClient();

    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    public void unsubscribe() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public abstract void doReflush();

    public void onLoadingmore() {
    }
}
