package com.hxxc.user.app.contract.presenter;

import android.text.TextUtils;

import com.hxxc.user.app.bean.Account;
import com.hxxc.user.app.bean.RedPackagePayBean;
import com.hxxc.user.app.contract.PayV;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hxxc.user.app.contract.presenter.RxBasePresenter.mApiManager;

/**
 * Created by chenqun on 2016/11/7.
 */

public class PayPresenter {
    private final PayV mView;

    public PayPresenter(PayV view) {
        this.mView = view;
    }

    public void queryRedPackageList(String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SPUtils.geTinstance().getUid());
        map.put("orderNo", orderNo);
        map.put("status", "1");
        mApiManager.findCanPayUserReward(map, new SimpleCallback<List<RedPackagePayBean>>() {
            @Override
            public void onNext(List<RedPackagePayBean> redPackageItemBeen) {
                mView.onGetRedPackageList(redPackageItemBeen);
            }

            @Override
            public void onError() {
            }
        });
    }

    public void querySubAccountList() {
        mApiManager.querySubAccountList(SPUtils.geTinstance().getUid(), new SimpleCallback<List<Account>>() {
            @Override
            public void onNext(List<Account> datas) {
                mView.onGetAccountList(datas);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void reflushSubAccountList() {
        mApiManager.querySubAccountList(SPUtils.geTinstance().getUid(), new SimpleCallback<List<Account>>() {
            @Override
            public void onNext(List<Account> datas) {
                mView.onReflushAccountList(datas);
            }

            @Override
            public void onError() {

            }
        });
    }

//    public void doBalancePay(String orderNo,String rId,String code){
//        mApiManager.doBalancePay(orderNo,rId,code, new SimpleCallback<String>() {
//            @Override
//            public void onNext(String s) {
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
//    }

    public void doPay(String transId, String code) {
        mApiManager.doPay(transId, code, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                mView.onPaySuccess();
            }

            @Override
            public void onError() {
                mView.onPayFailure();
            }
        });
    }

    public void getOrderSuplusTime(String orderNo) {
        Map<String, String> map = new HashMap<>();
        map.put("orderNo", orderNo);
        mApiManager.getOrderSurplusTime(map, new SimpleCallback<String>() {
            @Override
            public void onNext(String agreements) {
                if (!TextUtils.isEmpty(agreements)) {
                    Long aLong = Long.valueOf(agreements);
                    if (aLong >= 1) mView.startCountTime(aLong);
                    else mView.gameOver();
                }
            }

            @Override
            public void onError() {
            }
        });
    }
}
