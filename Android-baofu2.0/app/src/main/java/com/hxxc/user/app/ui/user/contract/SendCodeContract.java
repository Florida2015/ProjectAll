package com.hxxc.user.app.ui.user.contract;

import android.content.Context;

import com.hxxc.user.app.ui.base.BaseRxModel;
import com.hxxc.user.app.ui.base.BaseRxPresenter;
import com.hxxc.user.app.ui.base.BaseRxView;

/**
 * Created by Administrator on 2016/10/8.
 * 发送短信验证码
 *  * 请输入验证码类型，取值范围【01：手动下单成功，02：自动下单成功，03：募集完成，开始起息，
 * 04：资金撤标/银行主动撤标，05：未募集完/银行主动流标，06：还款付息，07：提前还款，
 * 08：债权转让，09：转让失败，10：注册，11：找回密码，12：修改密码，13：修改邮箱】
 */

public interface SendCodeContract {
    interface Model extends BaseRxModel {
    }
    interface View extends BaseRxView {
        void toSendCode();//
        void showErrorMessage(String erro);
    }
    abstract class Presenter extends BaseRxPresenter<Model,View> {

        public abstract void SendCode(Context mContext, String userphone,String type);

        @Override
        public void onStart() {
        }
    }
}
