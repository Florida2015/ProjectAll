package com.hxxc.user.app.contract.presenter;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.bean.Product;
import com.hxxc.user.app.contract.IndexContract;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.SPUtils;

import java.util.List;

import rx.Subscription;

/**
 * Created by chenqun on 2016/6/22.
 */
public class IndexPresenter extends RxBasePresenter implements IndexContract.Presenter {


    private  IndexContract.View mView;
    private int page = 1;

    public IndexPresenter(IndexContract.View view) {
        this.mView = view;
    }

    @Override
    public void subscribe() {
        getTopbarData();
        getProductList(false);
    }
    @Override
    public void unsubscribe() {
//        mView = null;
        super.unsubscribe();
    }

    @Override
    public void doReflush() {
        page = 1;
        getTopbarData();
        getProductList(false);
    }
    private void getProductList(boolean isLoadingmore) {
        Subscription s = mApiManager.getIndexProductList(SPUtils.geTinstance().getUid(),page+"", Constants.ROWS+"", new SimpleCallback<List<Product>>() {
            @Override
            public void onNext(List<Product> products) {
                mView.onGetDatas(products, isLoadingmore);
            }

            @Override
            public void onError() {
                mView.onGetDatas(null, isLoadingmore);
            }
        });
        addSubscription(s);
    }


    @Override
    public void getTopbarData() {
        Subscription s = mApiManager.getIndexAdsList(Constants.TYPE_Banner+"","2","1","5", new SimpleCallback<List<IndexAds>>() {
            @Override
            public void onNext(final List<IndexAds> indexAdses) {
                mView.initViewPagerData(indexAdses);
            }

            @Override
            public void onError() {
            }
        });
        addSubscription(s);
//        mApiManager.getIndexAdsList2(TYPE_Banner + "", "2", new SimpleCallback<String>() {
//            @Override
//            public void onNext(String s) {
//                LogUtil.e("测试===="+s);
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
    }

    @Override
    public void getDatas() {

    }

    @Override
    public void onLoadingmore() {
        page ++;
        getProductList(true);
    }
}
