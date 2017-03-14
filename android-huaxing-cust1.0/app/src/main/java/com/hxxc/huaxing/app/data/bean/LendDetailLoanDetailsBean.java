package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/10/12.
 */

public class LendDetailLoanDetailsBean implements Serializable{

    private String orderNo;
    private BigDecimal yearRate;
    private int periods;
    private BigDecimal amount;
    private BigDecimal expectProfit;
    private String interestType;
    private int fid;
    private String fname;
    private String financialNo;
    private long signTime;
    private String interestTypeStr;

    public LendDetailLoanDetailsBean() {
        super();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getYearRate() {
        return yearRate;
    }

    public void setYearRate(BigDecimal yearRate) {
        this.yearRate = yearRate;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getExpectProfit() {
        return expectProfit;
    }

    public void setExpectProfit(BigDecimal expectProfit) {
        this.expectProfit = expectProfit;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFinancialNo() {
        return financialNo;
    }

    public void setFinancialNo(String financialNo) {
        this.financialNo = financialNo;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
    }

    public String getInterestTypeStr() {
        return interestTypeStr;
    }

    public void setInterestTypeStr(String interestTypeStr) {
        this.interestTypeStr = interestTypeStr;
    }

    @Override
    public String toString() {
        return "LendDetailLoanDetailsBean{" +
                "orderNo='" + orderNo + '\'' +
                ", yearRate=" + yearRate +
                ", periods=" + periods +
                ", amount=" + amount +
                ", expectProfit='" + expectProfit + '\'' +
                ", interestType=" + interestType +
                ", fid='" + fid + '\'' +
                ", fname='" + fname + '\'' +
                ", financialNo='" + financialNo + '\'' +
                ", signTime=" + signTime +
                ", interestTypeStr='" + interestTypeStr + '\'' +
                '}';
    }
}
