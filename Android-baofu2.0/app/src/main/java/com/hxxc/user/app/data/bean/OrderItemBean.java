package com.hxxc.user.app.data.bean;

import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;
import com.hxxc.user.app.widget.trecyclerview.C;

import java.math.BigDecimal;

import rx.Observable;

/**
 * Created by houwen.lai  on 2016/10/26.
 * 订单详情 状态 item
 */

public class OrderItemBean extends BaseEntity.ListBean{

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getOrderListByUid(Api.uid,param.get("status"),page,C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private String orderNo;
    private int pid;
    private String productName;//
    private double yearRate;
    private int periods;
    private int rewardType;
    private String awardValue;
    private BigDecimal rewardMoney;
    private int orderStatus;
    private String yearRateText;
    private String orderStatusText;

    private String productData;//

    private int days;
    private BigDecimal amount;
    private long signTime;

    private String productBidName;
    private String currPeriod;

    private int isConInvest;

    public OrderItemBean() {
        super();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getYearRate() {
        return yearRate;
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

    public int getRewardType() {
        return rewardType;
    }

    public void setRewardType(int rewardType) {
        this.rewardType = rewardType;
    }

    public String getAwardValue() {
        return awardValue;
    }

    public void setAwardValue(String awardValue) {
        this.awardValue = awardValue;
    }

    public BigDecimal getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(BigDecimal rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getYearRateText() {
        return yearRateText;
    }

    public void setYearRateText(String yearRateText) {
        this.yearRateText = yearRateText;
    }

    public String getOrderStatusText() {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }

    public String getProductData() {
        return productData;
    }

    public void setProductData(String productData) {
        this.productData = productData;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
    }


    public String getProductBidName() {
        return productBidName;
    }

    public void setProductBidName(String productBidName) {
        this.productBidName = productBidName;
    }

    public String getCurrPeriod() {
        return currPeriod;
    }

    public void setCurrPeriod(String currPeriod) {
        this.currPeriod = currPeriod;
    }

    public int getIsConInvest() {
        return isConInvest;
    }

    public void setIsConInvest(int isConInvest) {
        this.isConInvest = isConInvest;
    }
}
