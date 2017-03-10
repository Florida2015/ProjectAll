package com.hxxc.user.app.ui.order.presenter;

import android.content.Context;

import com.hxxc.user.app.data.bean.OrderContundBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.order.contract.ContinuedContract;

import java.util.List;

/**
 * Created by houwen.lai on 2016/11/14.
 * 订单详情 续投
 */

public class ContinuedPresenter extends ContinuedContract.Presenter{


    @Override
    public void toContinuedInvestment(Context context,String type) {

        Api.getClient().getGetXuTouList(type).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<OrderContundBean>>(context) {
                              @Override
                              public void onSuccess(List<OrderContundBean> orderContundBeen) {
                                  mView.toContinuedInvestment(type,orderContundBeen);
                              }
                          }
                );

    }


}
