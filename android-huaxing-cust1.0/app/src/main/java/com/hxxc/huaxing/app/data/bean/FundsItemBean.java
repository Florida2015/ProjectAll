package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/29.
 * 资金明细 我的资产
 *
 */

public class FundsItemBean implements Serializable{

    private String fundsName;
    private String fundsMoney;

    public FundsItemBean() {
        super();
    }

    public String getFundsName() {
        return fundsName;
    }

    public void setFundsName(String fundsName) {
        this.fundsName = fundsName;
    }

    public String getFundsMoney() {
        return fundsMoney;
    }

    public void setFundsMoney(String fundsMoney) {
        this.fundsMoney = fundsMoney;
    }
}
