package com.hxxc.user.app.contract.presenter;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.bean.ActionCenterBean;
import com.hxxc.user.app.contract.ActionCenterV;
import com.hxxc.user.app.rest.rx.SimpleCallback;

import java.util.List;

/**
 * Created by chenqun on 2016/11/10.
 */

public class ActionCenterPresenter extends RxBasePresenter {
    private  ActionCenterV mView;
    private int page = 1;

    public ActionCenterPresenter(ActionCenterV view) {
        this.mView = view;
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

    private void getDatas(boolean isLoadingmore) {
        mApiManager.getActionCenter(page+"", Constants.ROWS+"", new SimpleCallback<List<ActionCenterBean>>() {
            @Override
            public void onNext(List<ActionCenterBean> actionCenterBean) {
                mView.setDatas(actionCenterBean,isLoadingmore);
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
