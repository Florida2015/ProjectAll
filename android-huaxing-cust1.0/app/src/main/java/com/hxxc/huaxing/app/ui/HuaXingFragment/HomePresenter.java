package com.hxxc.huaxing.app.ui.HuaXingFragment;

import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.AdsBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.RxBasePresenter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by chenqun on 2016/10/9.
 */

public class HomePresenter extends RxBasePresenter {
    private final HomeV mView;

    public HomePresenter(HomeV view) {
        this.mView = view;
    }

    @Override
    public void doReflush() {
        getDatas(false);
        getAds();
    }

    private void getAds() {
        mApi.getIndexAdsList(0+"", UserInfoConfig.PLATFORM,1+"",UserInfoConfig.ROWS+"").compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<List<AdsBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean<List<AdsBean>> listBaseBean) {
                mView.onGetAds(listBaseBean.getModel());
            }
        });
    }

    @Override
    public void onLoadingmore() {
        super.onLoadingmore();
        getDatas(true);
    }

    private int page = 1;
    public void getDatas(final  boolean isLoadingmore){
        if(isLoadingmore){
            page++;
        }else{
            page = 1;
        }
        mApi.getIndexProductList(page+"",UserInfoConfig.ROWS+"").compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<List<ProductBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if(!isLoadingmore){
                    mView.onError();
                }
            }

            @Override
            public void onNext(BaseBean<List<ProductBean>> listBaseBean) {
                mView.onSuccess(listBaseBean.getModel(),isLoadingmore);
            }
        });
    }
}
