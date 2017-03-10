package com.hxxc.user.app.contract.presenter;

import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.bean.NoticeBean;
import com.hxxc.user.app.contract.MessageV;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * Created by chenqun on 2016/11/16.
 */

public class MessagePresenter extends RxBasePresenter {

    private  MessageV mView;

    public MessagePresenter(MessageV view) {
        mView = view;
    }
    @Override
    public void doReflush() {
        getNews();
        getNotices();
    }
    @Override
    public void subscribe() {
        getNews();
        getNotices();
    }

    @Override
    public void unsubscribe() {
        mView = null;
        super.unsubscribe();
    }

    private void getNews() {
        Subscription subscription = mApiManager.selectNewsList(DiscoveryPresenter.TYPE_1 + "", "1", "1", new SimpleCallback<List<ContentBean>>() {
            @Override
            public void onNext(List<ContentBean> products) {
                if (null != products && products.size() > 0) {
                    mView.onGetNes(products.get(0));
                }
            }

            @Override
            public void onError() {

            }
        });
        addSubscription(subscription);
    }


    private void getNotices() {
        Map<String,String> params = new HashMap<>();
        params.put("uid", SPUtils.geTinstance().getUid());

        Subscription notifiAlert = mApiManager.getNotifiAlert(params, new SimpleCallback<NoticeBean>() {
            @Override
            public void onNext(NoticeBean noticeBean) {
                mView.onGetNotice(noticeBean);
            }

            @Override
            public void onError() {
                mView.onGetNotice(null);
            }
        });
        addSubscription(notifiAlert);
    }

}
