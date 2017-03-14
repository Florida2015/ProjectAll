package com.hxxc.huaxing.app.ui.wealth.productlist;

import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.RxBasePresenter;
import com.hxxc.huaxing.app.utils.LogUtil;

import java.util.List;

import rx.Subscriber;

/**
 * Created by chenqun on 2016/10/9.
 */

public class ProductListPresenter extends RxBasePresenter {
    private final ProductListV mView;
    private int pager = 1;
    private int type;
    private int status;

    public ProductListPresenter(ProductListV view,int type,int status) {
        this.mView = view;
        this.type = type;
        this.status = status;
    }

    @Override
    public void doReflush() {
        pager = 1;
        getDatas(false);
    }

    private void getDatas(boolean isLoadingmore){
        mApi.getProductList(type+"",status+"",pager+"", UserInfoConfig.ROWS+"").compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<List<ProductBean>>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mView.onError();
                LogUtil.d("业绩报告==" + e.getMessage());
            }

            @Override
            public void onNext(BaseBean<List<ProductBean>> s) {
                mView.onSuccess(s.getModel(),isLoadingmore);
                LogUtil.d(s.toString());
            }
        });
    }

    @Override
    public void onLoadingmore() {
        pager++;
        getDatas(true);
    }
}
