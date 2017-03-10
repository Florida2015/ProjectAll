package com.hxxc.user.app.bean;

import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;

import java.math.BigDecimal;

import rx.Observable;

/**
 * Created by chenqun on 2016/11/7.
 * 获取用户账户信息
 */

public class AccountInfo extends BaseEntity.ListBean{//implements Serializable

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getMyAccountInfo(Api.uid).compose(RxApiThread.convert());
    }

    /**
     * tatalAmount : 355500
     * remainAmount : 0
     * frozenAmount : 100500
     * principalAmount : 350000
     * yesterdayProfit : 15.0685
     * collectProfit : 1
     * cumulativeProfit : 0
     */

    private BigDecimal tatalAmount;//总资产
    private BigDecimal remainAmount;//剩余金额
    private BigDecimal frozenAmount;//冻结金额
    private BigDecimal principalAmount;//待收本金
    private BigDecimal yesterdayProfit;//昨日收益
    private BigDecimal collectProfit;//待收收益
    private BigDecimal cumulativeProfit;//累计收益
    private BigDecimal uncollectedTotalProfit;//未赚收收益
    private BigDecimal arrivalProfit;//到账收益
    private BigDecimal tatalProfit;//预计总收益

    public BigDecimal getTatalAmount() {
        return tatalAmount;
    }

    public void setTatalAmount(BigDecimal tatalAmount) {
        this.tatalAmount = tatalAmount;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public BigDecimal getYesterdayProfit() {
        return yesterdayProfit;
    }

    public void setYesterdayProfit(BigDecimal yesterdayProfit) {
        this.yesterdayProfit = yesterdayProfit;
    }

    public BigDecimal getCollectProfit() {
        return collectProfit;
    }

    public void setCollectProfit(BigDecimal collectProfit) {
        this.collectProfit = collectProfit;
    }

    public BigDecimal getCumulativeProfit() {
        return cumulativeProfit;
    }

    public void setCumulativeProfit(BigDecimal cumulativeProfit) {
        this.cumulativeProfit = cumulativeProfit;
    }

    public BigDecimal getUncollectedTotalProfit() {
        return uncollectedTotalProfit;
    }

    public void setUncollectedTotalProfit(BigDecimal uncollectedTotalProfit) {
        this.uncollectedTotalProfit = uncollectedTotalProfit;
    }

    public BigDecimal getArrivalProfit() {
        return arrivalProfit;
    }

    public void setArrivalProfit(BigDecimal arrivalProfit) {
        this.arrivalProfit = arrivalProfit;
    }

    public BigDecimal getTatalProfit() {
        return tatalProfit;
    }

    public void setTatalProfit(BigDecimal tatalProfit) {
        this.tatalProfit = tatalProfit;
    }
}
