package com.hxxc.user.app.ui.order.contract;

import android.content.Context;

import com.hxxc.user.app.data.bean.OrderContundBean;
import com.hxxc.user.app.ui.base.BaseRxModel;
import com.hxxc.user.app.ui.base.BaseRxPresenter;
import com.hxxc.user.app.ui.base.BaseRxView;

import java.util.List;

/**
 * Created by houwen.lai on 2016/11/14.
 * 订单详情 续投
 */

public interface ContinuedContract {



    interface View extends BaseRxView {
        void toContinuedInvestment(String type,List<OrderContundBean> listContinued); //
        void showMsg(String msg);
    }

    abstract class Presenter extends BaseRxPresenter<BaseRxModel,View>{

        public abstract void toContinuedInvestment(Context context, String type);

        @Override
        public void onStart() {

        }
    }

}

