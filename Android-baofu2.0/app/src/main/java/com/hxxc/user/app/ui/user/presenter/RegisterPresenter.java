package com.hxxc.user.app.ui.user.presenter;

import android.content.Context;

import com.hxxc.user.app.ui.user.contract.RegisterContract;
/**
 * Created by Administrator on 2016/9/27.
 * 注册
 *
 */

public class RegisterPresenter extends RegisterContract.Presenter{

    @Override
    public void registernext(Context mContext, String userphone, String code, String pass, String repass,String invitecode) {
//        Api.getClient().getRegisterNext(userphone,code,pass,repass,invitecode).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<String>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.showErrorMessage("");
//            }
//
//            @Override
//            public void onNext(BaseBean<String> stringBaseBean) {
//                if (stringBaseBean.isSuccess())
//                    mView.toRegisterNext();
//                else mView.showErrorMessage(stringBaseBean.getErrMsg());
//                mView.toRegisterNext();
//            }
//        });

    }

    @Override
    public void SendCode(Context mContext, String userphone,String type) {
//        Api.getClient().getSendCode(userphone,type).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<String>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                mView.showErrorMessage("");
//            }
//
//            @Override
//            public void onNext(BaseBean<String> stringBaseBean) {
//                if (stringBaseBean.isSuccess())
//                mView.toSendCode();
//                else mView.showErrorMessage(stringBaseBean.getErrMsg());
//            }
//        });
    }

}
