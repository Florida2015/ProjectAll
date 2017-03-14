package com.hxxc.financial.app.data.entity;

import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseEntity;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class CommentInfo extends BaseEntity.ListBean {

    public String content;

//    @Override
//    public Observable<String> getPageAt(int page) {
//        return Api.getClient().getBespeakList(Api.getTokenF(),page,10).compose(RxSchedulers.io_main());
////        return Api.getInstance().movieService
////                .getCommentList(ApiUtil.getInclude(param), ApiUtil.getWhere(param), C.PAGE_COUNT * (page - 1), C.PAGE_COUNT)
////                .compose(RxSchedulers.io_main());
//    }

    @Override
    public Observable getPageAt( int page) {
//        Api.getClient().getBespeakList(Api.token,Api.fid,Api.role,"").compose(RxSchedulers.io_main());
        return null;
    }
}
