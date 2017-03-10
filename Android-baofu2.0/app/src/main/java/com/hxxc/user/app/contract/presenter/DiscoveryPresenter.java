package com.hxxc.user.app.contract.presenter;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.contract.DiscoveryContract;
import com.hxxc.user.app.rest.rx.SimpleCallback;

import java.util.List;

/**
 * Created by chenqun on 2016/11/2.
 */

public class DiscoveryPresenter extends RxBasePresenter implements DiscoveryContract.Presenter {
    public static final String TYPE_1 = "1";//平台公告
    //    public static final String TYPE_2 = "2";//媒体报道
//    public static final String TYPE_3 = "3";//公司动态
    public static final String TYPE_4 = "4";//精选文章
    public static final String TYPE_Other = "2,3,4";//媒体报道,公司动态,精选文章

    private DiscoveryContract.View mView;
    private int page = 1;
    private String type;

    public DiscoveryPresenter(DiscoveryContract.View view, String type) {
        this.mView = view;
        this.type = type;
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

    @Override
    public void getDatas(boolean isLoadingmore) {

        mApiManager.selectNewsList(type, page + "", Constants.ROWS + "", new SimpleCallback<List<ContentBean>>() {
            @Override
            public void onNext(List<ContentBean> products) {
                mView.setDatas(products, isLoadingmore);
            }

            @Override
            public void onError() {
                mView.setDatas(null, isLoadingmore);
            }
        });
    }

    @Override
    public void onLoadingmore(boolean isLoadingmore) {
        page++;
        getDatas(true);
    }

    public void getMailUrl() {
        mApiManager.getMailUrl("", new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                mView.toMail(s);
            }

            @Override
            public void onError() {

            }
        });
    }
}
