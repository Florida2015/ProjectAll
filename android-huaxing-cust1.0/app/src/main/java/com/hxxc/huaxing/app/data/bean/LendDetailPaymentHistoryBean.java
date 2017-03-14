package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/10/12.
 *"periods": null,
 "currPeriod": 1,
 "capital": 10000,
 "interest": 91.6667,
 "updateTime": 1474274593000,
 "payStatus": 100,
 "isPay": 1,
 "payStatusValue": null,
 "isPayValue": "付息中"
 */

public class LendDetailPaymentHistoryBean implements Serializable {

    private String periods;//期次
    private int currPeriod;
    private BigDecimal capital;//本金
    private BigDecimal interest;//利息
    private long updateTime;
    private String payStatus;
    private int isPay;
    private String payStatusValue;
    private String isPayValue;
    private String payCapitalStr;
    private String payInterestStr;

    public LendDetailPaymentHistoryBean() {
        super();
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public int getCurrPeriod() {
        return currPeriod;
    }

    public void setCurrPeriod(int currPeriod) {
        this.currPeriod = currPeriod;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getPayStatusValue() {
        return payStatusValue;
    }

    public void setPayStatusValue(String payStatusValue) {
        this.payStatusValue = payStatusValue;
    }

    public String getIsPayValue() {
        return isPayValue;
    }

    public void setIsPayValue(String isPayValue) {
        this.isPayValue = isPayValue;
    }

    public String getPayCapitalStr() {
        return payCapitalStr;
    }

    public void setPayCapitalStr(String payCapitalStr) {
        this.payCapitalStr = payCapitalStr;
    }

    public String getPayInterestStr() {
        return payInterestStr;
    }

    public void setPayInterestStr(String payInterestStr) {
        this.payInterestStr = payInterestStr;
    }

    @Override
    public String toString() {
        return "LendDetailPaymentHistoryBean{" +
                "periods='" + periods + '\'' +
                ", currPeriod='" + currPeriod + '\'' +
                ", capital='" + capital + '\'' +
                ", interest='" + interest + '\'' +
                ", updateTime=" + updateTime +
                ", payStatus='" + payStatus + '\'' +
                ", isPay=" + isPay +
                ", payStatusValue='" + payStatusValue + '\'' +
                ", isPayValue='" + isPayValue + '\'' +
                '}';
    }
}
