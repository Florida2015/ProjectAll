package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/11/10.
 * 订单详情 续投对话框
 */

public class OrderContundBean implements Serializable {

    private int id;
    private double yearRate;
    private double additional;//额外
    private String productName;
    private int periodDays;
    private int type;//1红包  2加息圈券
    private String yearRateText;
    private String additionalText;

    private int periods;//月数

    private boolean flag = false;

    public OrderContundBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getYearRate() {
        return yearRate;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }

    public double getAdditional() {
        return additional;
    }

    public void setAdditional(double additional) {
        this.additional = additional;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getYearRateText() {
        return yearRateText;
    }

    public void setYearRateText(String yearRateText) {
        this.yearRateText = yearRateText;
    }

    public String getAdditionalText() {
        return additionalText;
    }

    public void setAdditionalText(String additionalText) {
        this.additionalText = additionalText;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    @Override
    public String toString() {
        return "OrderContundBean{" +
                "id=" + id +
                ", yearRate=" + yearRate +
                ", additional=" + additional +
                ", productName='" + productName + '\'' +
                ", periodDays=" + periodDays +
                ", type=" + type +
                ", yearRateText='" + yearRateText + '\'' +
                ", additionalText='" + additionalText + '\'' +
                ", periods=" + periods +
                ", flag=" + flag +
                '}';
    }
}
