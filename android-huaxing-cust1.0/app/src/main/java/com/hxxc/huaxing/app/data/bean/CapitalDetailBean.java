package com.hxxc.huaxing.app.data.bean;

import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseEntity;
import com.hxxc.huaxing.app.wedgit.trecyclerview.C;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/13.
 * 资金明细model
 */

public class CapitalDetailBean extends BaseEntity.ListBean {

    @Override
    public Observable getPageAt(int page) {//Api.uid 51
        return Api.getClient().getFinancialDetails(Api.uid,page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private String createTimeText;
    private double money;
    private String descriptions;
    private String type;//类型(出借pay,收益profit,本息赎回principal,充值recharge,提现cash)
    private String typeStr;

    private long createTime;

    public CapitalDetailBean() {
        super();
    }

    public String getCreateTimeText() {
        return createTimeText;
    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    @Override
    public String toString() {
        return "CapitalDetailBean{" +
                "createTimeText='" + createTimeText + '\'' +
                ", money=" + money +
                ", descriptions='" + descriptions + '\'' +
                ", type='" + type + '\'' +
                ", createTime=" + createTime +
                '}';
    }


}
