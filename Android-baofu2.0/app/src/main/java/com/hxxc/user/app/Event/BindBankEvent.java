package com.hxxc.user.app.Event;

import com.hxxc.user.app.bean.BankInfo;

/**
 * Created by chenqun on 2016/11/11.
 */

public class BindBankEvent {

    public final BankInfo mBankInfo;

    public BindBankEvent(BankInfo info) {
        this.mBankInfo = info;
    }

    public String provinceName ;
    public String cityName ;
    public String provinceCode ;
    public String cityCode ;
}
