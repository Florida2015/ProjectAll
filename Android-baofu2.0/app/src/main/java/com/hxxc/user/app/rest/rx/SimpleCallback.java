package com.hxxc.user.app.rest.rx;

/**
 * Created by chenqun on 2016/7/7.
 */
public interface SimpleCallback<T> {
    void onNext(T t);
    void onError();
}
