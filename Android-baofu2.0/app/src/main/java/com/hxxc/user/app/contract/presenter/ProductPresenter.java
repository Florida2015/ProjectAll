package com.hxxc.user.app.contract.presenter;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.bean.Product;
import com.hxxc.user.app.contract.ProductContract;
import com.hxxc.user.app.rest.rx.SimpleCallback;

import java.util.List;

import rx.Subscription;

/**
 * Created by chenqun on 2016/6/22.
 */
public class ProductPresenter extends RxBasePresenter implements ProductContract.Presenter {

    public static final int TYPE_DINGXING = 1;
    public static final int TYPE_HUAXING = 2;
    public static final int TYPE_XINSHOU = 3;
    public static final int TYPE_JIJIN = 4;

    private  ProductContract.View mView;
    private int page = 1;
    private int type = TYPE_DINGXING;

    public ProductPresenter(ProductContract.View view,int type) {
        this.mView = view;
        this.type = type;
    }

    @Override
    public void subscribe() {
        getProductList(false);
    }
    @Override
    public void unsubscribe() {
        mView = null;
        super.unsubscribe();
    }
    @Override
    public void doReflush() {
        page = 1;
        getProductList(false);
    }

    @Override
    public void getProductList(boolean isLoadingmore) {
        Subscription s = mApiManager.getProductList(type+"",page+"", Constants.ROWS+"", new SimpleCallback<List<Product>>() {
            @Override
            public void onNext(List<Product> products) {
                mView.setProductList(products,isLoadingmore);
            }

            @Override
            public void onError() {
                mView.setProductList(null,isLoadingmore);
            }
        });
        addSubscription(s);
    }

    public void onLoadingmore() {
        page ++;
        getProductList(true);
    }
}
