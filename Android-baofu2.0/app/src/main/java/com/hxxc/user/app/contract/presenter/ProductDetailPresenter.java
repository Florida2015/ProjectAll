package com.hxxc.user.app.contract.presenter;


import com.hxxc.user.app.bean.ProductInfo;
import com.hxxc.user.app.contract.ProductDetailContract;
import com.hxxc.user.app.rest.rx.SimpleCallback;

/**
 * Created by chenqun on 2016/10/9.
 */

public class ProductDetailPresenter extends RxBasePresenter {
    private  ProductDetailContract.View view;

    public ProductDetailPresenter(ProductDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {

    }
    @Override
    public void unsubscribe() {
        view = null;
        super.unsubscribe();
    }
    @Override
    public void doReflush() {

    }

    public void getDatas(String mPid) {
        mApiManager.getProductDetail(mPid, new SimpleCallback<ProductInfo>() {
            @Override
            public void onNext(ProductInfo products) {
                view.onGetDatas(products);
            }

            @Override
            public void onError() {
                view.onGetDatas(null);
            }
        });

    }
}
