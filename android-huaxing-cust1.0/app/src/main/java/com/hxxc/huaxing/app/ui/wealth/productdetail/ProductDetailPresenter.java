package com.hxxc.huaxing.app.ui.wealth.productdetail;

import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.ProductInfo;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.RxBasePresenter;

import rx.Subscriber;

/**
 * Created by chenqun on 2016/10/9.
 */

public class ProductDetailPresenter extends RxBasePresenter {
    private final ProductdetailV mView;

    public ProductDetailPresenter(ProductdetailV view) {
        this.mView = view;
    }

    @Override
    public void doReflush() {

    }

    public void getDatas(String pid){

        mApi.getProductDefaultById(pid+"").compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<ProductInfo>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mView.onError();
            }

            @Override
            public void onNext(BaseBean<ProductInfo> productInfoBaseBean) {
                mView.onSuccess(productInfoBaseBean.getModel(),false);
            }
        });
    }
}
