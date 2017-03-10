package com.hxxc.user.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by houwen.lai on 2016/10/26.
 */

public class OrderDetailLoanDetailsBean  implements Serializable {

    private String orderNo;
    private double yearRate;
    private String yearRateText;
    private int periods;//
    private BigDecimal amount;
    private BigDecimal expectProfit;
    private int interestType;
    private int fid;
    private String fname;
    private String financialNo;
    private long signTime;
    private String interestTypeStr;//收益方式

    private String productName;
    private int days;
    private BigDecimal money;
    private long profitTime;
    private long expireTime;
    private BigDecimal payMoney;
    private String name;
    private int orderStatus;
    private String credit;
    private String contract;
    private String userRewardType;
    private BigDecimal userRewardMoney;

    public OrderDetailLoanDetailsBean() {
        super();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getYearRate() {
        return yearRate;
    }

    public String getYearRateText() {
        return yearRateText;
    }

    public void setYearRateText(String yearRateText) {
        this.yearRateText = yearRateText;
    }

    public void setYearRate(double yearRate) {
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

    public int getInterestType() {
        return interestType;
    }

    public void setInterestType(int interestType) {
        this.interestType = interestType;
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

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public long getProfitTime() {
        return profitTime;
    }

    public void setProfitTime(long profitTime) {
        this.profitTime = profitTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getUserRewardType() {
        return userRewardType;
    }

    public void setUserRewardType(String userRewardType) {
        this.userRewardType = userRewardType;
    }

    public BigDecimal getUserRewardMoney() {
        return userRewardMoney;
    }

    public void setUserRewardMoney(BigDecimal userRewardMoney) {
        this.userRewardMoney = userRewardMoney;
    }


}
