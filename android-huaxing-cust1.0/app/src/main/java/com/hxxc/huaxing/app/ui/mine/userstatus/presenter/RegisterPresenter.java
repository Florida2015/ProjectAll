package com.hxxc.huaxing.app.ui.mine.userstatus.presenter;

import android.content.Context;

import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.mine.userstatus.contract.RegisterContract;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/27.
 * 注册
 *
 */

public class RegisterPresenter extends RegisterContract.Presenter{

    @Override
    public void registernext(Context mContext, String userphone, String code, String pass, String repass,String invitecode) {
        Api.getClient().getRegisterNext(userphone,code,pass,repass,invitecode).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<String>(mContext) {
                    @Override
                    public void onSuccess(String s) {
                        mView.toRegisterNext();
                    }

                    @Override
                    public void onFail(String fail) {
                        mView.showErrorMessage(fail);
                    }
                });

    }

    @Override
    public void SendCode(Context mContext, String userphone,String type) {
        Api.getClient().getSendCode(userphone,type).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<String>(mContext) {
                    @Override
                    public void onSuccess(String s) {
                            mView.toSendCode();
                    }

                    @Override
                    public void onFail(String fail) {
                        mView.showErrorMessage(fail);
                    }
                }
    );
    }

}
