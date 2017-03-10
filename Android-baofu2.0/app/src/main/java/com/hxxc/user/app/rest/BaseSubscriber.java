package com.hxxc.user.app.rest;

import android.content.Context;
import android.text.TextUtils;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.KICKOUTEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.R;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.EventBusUtils;
import com.hxxc.user.app.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import de.greenrobot.event.EventBus;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/11/23.
 */

public abstract class BaseSubscriber<T> extends Subscriber<BaseResult<T>> implements BaseObserver<T> {

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
            if (!CommonUtil.isNetworkAvailable2(HXXCApplication.getContext())) {
                ToastUtil.getInstance().ToastShortFromNet("网络连接不可用,请检查网络设置");
            } else {
                ToastUtil.getInstance().ToastShortFromNet(e.getMessage());
            }
        }
        onFail(e.getMessage());
    }

    @Override
    public void onNext(BaseResult<T> baseBean) {
        if (baseBean.getSuccess()) {
            onSuccess(baseBean.getData());
        } else {
            if (!TextUtils.isEmpty(baseBean.getErrorCode()) && baseBean.getErrorCode().contains(Constants.ErrorCode_login_out)) {//异地登录
                ToastUtil.ToastShort(HXXCApplication.getContext(), R.string.text_login_out);
                //清除本地数据
                EventBusUtils.getInstance().kickOff(KICKOUTEvent.From_BACK);
            } else {
                if (!TextUtils.isEmpty(baseBean.getErrorMsg()))
                    ToastUtil.getInstance().ToastShortFromNet(baseBean.getErrorMsg());
            }
            onFail(baseBean.getErrorMsg());
        }
    }

    @Override
    public void onFail(String fail) {
    }
}
