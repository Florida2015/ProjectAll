package com.hxxc.huaxing.app.ui.base;

/**
 * Created by chenqun on 2016/10/9.
 */

public interface BaseV <T>{
    void onSuccess(T datas,boolean isLoadingmore);
    void onError();


}
