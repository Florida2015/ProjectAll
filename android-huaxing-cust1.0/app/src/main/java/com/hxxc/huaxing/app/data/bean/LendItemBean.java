package com.hxxc.huaxing.app.data.bean;

import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseEntity;
import com.hxxc.huaxing.app.wedgit.trecyclerview.C;

import java.math.BigDecimal;

import rx.Observable;

/**
 * Created by Administrator on 2016/9/29.
 * 我的出借 models
 * status
 * 1募集中（投标中） 2 计息(还款中) 3流标 4结清
 */

public class LendItemBean extends BaseEntity.ListBean {

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getOrderInfoListByUid(Api.uid,param.get("status"),page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private String orderNo;
    private int pid;
    private String productName;//
    private double yearRate;
    private int periods;
    private int awordType;//1.现金 2.加息
    private String awardValue;
    private BigDecimal money;
    private int orderStatus;
    private int debtStatus;// 0取orderStatusText  1取debtStatusText
    private String orderStatusText;
    private String debtStatusText;

    public LendItemBean() {
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

    public int getAwordType() {
        return awordType;
    }

    public void setAwordType(int awordType) {
        this.awordType = awordType;
    }

    public String getAwardValue() {
        return awardValue;
    }

    public void setAwardValue(String awardValue) {
        this.awardValue = awardValue;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(int debtStatus) {
        this.debtStatus = debtStatus;
    }

    public String getOrderStatusText() {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }

    public String getDebtStatusText() {
        return debtStatusText;
    }

    public void setDebtStatusText(String debtStatusText) {
        this.debtStatusText = debtStatusText;
    }

    @Override
    public String toString() {
        return "LendItemBean{" +
                "orderNo='" + orderNo + '\'' +
                ", pid=" + pid +
                ", productName='" + productName + '\'' +
                ", yearRate=" + yearRate +
                ", periods=" + periods +
                ", awordType=" + awordType +
                ", awardValue='" + awardValue + '\'' +
                ", money=" + money +
                ", orderStatus=" + orderStatus +
                ", debtStatus=" + debtStatus +
                ", orderStatusText='" + orderStatusText + '\'' +
                ", debtStatusText='" + debtStatusText + '\'' +
                '}';
    }

}
