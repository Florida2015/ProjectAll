package com.hxxc.huaxing.app.ui.mine.userstatus.presenter;

import android.content.Context;

import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.mine.userstatus.contract.SendCodeContract;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/10/8.
 *  * 发送短信验证码
 *  * 请输入验证码类型，取值范围【01：手动下单成功，02：自动下单成功，03：募集完成，开始起息，
 * 04：资金撤标/银行主动撤标，05：未募集完/银行主动流标，06：还款付息，07：提前还款，
 * 08：债权转让，09：转让失败，10：注册，11：找回密码，12：修改密码，13：修改邮箱】
 */

public class SendCodePresenter extends SendCodeContract.Presenter {

    @Override
    public void SendCode(Context mContext, String userphone,String type) {
        Api.getClient().getSendCode(userphone,type).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    ToastUtil.getInstance().ToastShortFromNet("网络中断，请检查您的网络状态");
                } else if (e instanceof ConnectException) {
                    ToastUtil.getInstance().ToastShortFromNet("网络中断，请检查您的网络状态");
                } else {
                    if (!CommonUtil.isNetworkAvailable2(HXXCApplication.getContext())) {
                        ToastUtil.getInstance().ToastShortFromNet("网络连接不可用,请检查网络设置");
                    }
                }
                mView.showErrorMessage("");
            }

            @Override
            public void onNext(BaseBean<String> stringBaseBean) {
                if (stringBaseBean.isSuccess()){
                    mView.toSendCode();
                }else {
                    mView.showErrorMessage(stringBaseBean.getErrMsg());
                }
            }
        });
    }

}
