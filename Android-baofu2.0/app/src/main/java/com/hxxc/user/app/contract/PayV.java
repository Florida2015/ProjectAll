package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.Account;
import com.hxxc.user.app.bean.RedPackagePayBean;

import java.util.List;

/**
 * Created by chenqun on 2016/11/7.
 */

public interface PayV {
    void onPaySuccess();
    void onPayFailure();

    void onGetAccountList(List<Account> datas);

    void onReflushAccountList(List<Account> datas);

    void onGetRedPackageList(List<RedPackagePayBean> redPackageItemBeen);

    void startCountTime(long aLong);

    void gameOver();
}
