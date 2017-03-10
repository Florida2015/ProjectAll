package com.hxxc.user.app.contract.presenter;

import com.hxxc.user.app.BasePresenter;
import com.hxxc.user.app.rest.ApiManager;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by chenqun on 2016/6/22.
 */
public abstract class RxBasePresenter implements BasePresenter {
    public static final ApiManager mApiManager = ApiManager.getInstance();

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }


    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    public void unsubscribe() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

}
