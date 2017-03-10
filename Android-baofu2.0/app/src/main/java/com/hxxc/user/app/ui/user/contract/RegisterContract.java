package com.hxxc.user.app.ui.user.contract;

import android.content.Context;

import com.hxxc.user.app.ui.base.BaseRxPresenter;

/**
 * Created by Administrator on 2016/9/27.
 */

public interface RegisterContract {

    interface Model extends SendCodeContract.Model {
    }
    interface View extends SendCodeContract.View {
        void toRegisterNext();//
        void showErrorMessage(String erro);
    }
    abstract class Presenter extends BaseRxPresenter<Model,View> {

        public abstract void SendCode(Context mContext, String userphone,String type);

        public abstract void registernext(Context mContext, String userphone, String code,String pass, String repass, String invitecode);

        @Override
        public void onStart() {
        }
    }

}
