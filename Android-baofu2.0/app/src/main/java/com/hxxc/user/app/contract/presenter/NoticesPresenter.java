package com.hxxc.user.app.contract.presenter;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.bean.NoticeBean;
import com.hxxc.user.app.contract.NoticesV;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenqun on 2016/11/17.
 */

public class NoticesPresenter extends RxBasePresenter {

    private  NoticesV mView;
    private int page = 1;

    public NoticesPresenter(NoticesV view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        getNotices(false);
        readAllNotice();
    }
    @Override
    public void unsubscribe() {
        mView = null;
        super.unsubscribe();
    }
    @Override
    public void doReflush() {
        page = 1;
        getNotices(false);
    }

    private void getNotices(boolean isLoadingmore) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SPUtils.geTinstance().getUid());
        map.put("page",page+"");
        map.put("rows", Constants.ROWS+"");
        mApiManager.getUserMessageList(map, new SimpleCallback<List<NoticeBean.UserMessageVoBean>>() {
            @Override
            public void onNext(List<NoticeBean.UserMessageVoBean> strings) {
                mView.setDatas(strings,isLoadingmore);
            }

            @Override
            public void onError() {
                mView.setDatas(null,isLoadingmore);
            }
        });
    }

    public void onLoadingmore(boolean isLoadingmore) {
        page ++;
        getNotices(true);
    }

    public void readNotice(String msgId){
        Map<String, String> map = new HashMap<>();
        map.put("uid", SPUtils.geTinstance().getUid());
        map.put("msgId",msgId);
        mApiManager.readUserMessage(map, new SimpleCallback<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
            }

            @Override
            public void onError() {
            }
        });
    }

    public void readAllNotice(){
        Map<String, String> map = new HashMap<>();
        map.put("uid", SPUtils.geTinstance().getUid());
        mApiManager.readAllUserMessage(map, new SimpleCallback<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
            }

            @Override
            public void onError() {
            }
        });
    }
}
