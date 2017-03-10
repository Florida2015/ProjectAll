package com.hxxc.user.app.rest.rx;

import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by chenqun on 2016/7/7.
 */
public class ExceptionSubscriber<T> extends Subscriber<T> {
    public static final int type_constract = 10;
    private int showType = 0;
    private boolean showToash = true;

    private SimpleCallback<T> simpleCallback;

    public ExceptionSubscriber(SimpleCallback simpleCallback) {
        this.simpleCallback = simpleCallback;
    }

    public ExceptionSubscriber(SimpleCallback simpleCallback, boolean showToast) {
        this.simpleCallback = simpleCallback;
        this.showToash = showToast;
    }

    public ExceptionSubscriber(SimpleCallback simpleCallback, int type) {
        this.simpleCallback = simpleCallback;
        this.showType = type;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            ToastUtil.getInstance().ToastShortFromNet("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            ToastUtil.getInstance().ToastShortFromNet("网络中断，请检查您的网络状态");
        } else {
            if(!CommonUtil.isNetworkAvailable2(HXXCApplication.getContext())){
                ToastUtil.getInstance().ToastShortFromNet("网络连接不可用,请检查网络设置");
            }else{
                if (showToash) {
                    if(showType == type_constract){
                        ToastUtil.getInstance().ToastShortFromNet("合同暂未生成");
                    }else{
                        ToastUtil.getInstance().ToastShortFromNet(e.getMessage());
//                        ToastUtil.getInstance().ToastShortFromNet("服务器连接异常");
                    }

                }

            }
        }
        if (simpleCallback != null)
            simpleCallback.onError();
    }

    @Override
    public void onNext(T t) {
        if (simpleCallback != null)
            simpleCallback.onNext(t);
    }
}
