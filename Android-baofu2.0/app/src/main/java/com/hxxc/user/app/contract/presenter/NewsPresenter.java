package com.hxxc.user.app.contract.presenter;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.contract.NewsV;
import com.hxxc.user.app.rest.rx.SimpleCallback;

import java.util.List;

/**
 * Created by chenqun on 2016/11/17.
 */

public class NewsPresenter extends RxBasePresenter {

    private  NewsV mView;
    private int page = 1;

    public NewsPresenter(NewsV view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        getDatas(false);
    }
    @Override
    public void unsubscribe() {
        mView = null;
        super.unsubscribe();
    }
    @Override
    public void doReflush() {
        page = 1;
        getDatas(false);
    }

    public void getDatas(boolean isLoadingmore) {
        mApiManager.selectNewsList(DiscoveryPresenter.TYPE_1+"",page+"", Constants.ROWS+"", new SimpleCallback<List<ContentBean>>() {
            @Override
            public void onNext(List<ContentBean> products) {
                mView.setDatas(products,isLoadingmore);
            }

            @Override
            public void onError() {
                mView.setDatas(null,isLoadingmore);
            }
        });
    }

    public void onLoadingmore(boolean isLoadingmore) {
        page ++;
        getDatas(true);
    }
}
