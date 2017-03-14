package com.hxxc.huaxing.app.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.Midhandler;
import com.hxxc.huaxing.app.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by houwen.lai on 2016/11/23.
 */

public abstract class BaseSubscriber<T> extends Subscriber<BaseBean<T>> implements BaseObserver<T>{

    private Context mContext;

    protected BaseSubscriber(Context context) {
        super();
        this.mContext = context;
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

            }
        }
        onFail(e.getMessage());
    }

    @Override
    public void onNext(BaseBean<T> baseBean) {
        if (baseBean.isSuccess()){
            onSuccess(baseBean.getModel());
        }else {
                onFail(baseBean.getErrMsg());

            if (!TextUtils.isEmpty(baseBean.getStatusCode())&&
                    (baseBean.getStatusCode().contains(UserInfoConfig.ErrorCode_login_out)||
                            baseBean.getStatusCode().contains(UserInfoConfig.http_error_out))){//异地登录
                ToastUtil.ToastShort(HXXCApplication.getContext(), R.string.text_login_out);
                //清除本地数据
                AppManager.getAppManager().ExitLogin();

            }
        }
    }

}
