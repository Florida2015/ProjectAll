package com.huaxia.finance.model;

import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/1/22.
 *
 */
public class ProductDetailModel implements Serializable {

    private double awardRate;
    private int awardType;
    private String productId;
    private String productName;
    private int productType;
    private int status;
    private long amount;
    private int minPrice;
    private int maxPrice;
    private int stepPrice;
    private double yield;
    private int productNum;
    private int period;
    private int periodUnit;
    private int productStyle;
    private long productStart;
    private long productEnd;
    private int isHome;
    private String activityAlias;//
    private int remainingNum;

    private int minuSecode;
    private double activityAmount;
    private long serverTime;

    public ProductDetailModel() {
        super();
    }

    public ProductDetailModel(double awardRate, int awardType, String productId, String productName, int productType, int status, long amount, int minPrice, int maxPrice, int stepPrice, double yield, int productNum, int period, int periodUnit, int productStyle, long productStart, long productEnd, int isHome, String activityAlias, int remainingNum) {
        this.awardRate = awardRate;
        this.awardType = awardType;
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.status = status;
        this.amount = amount;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.stepPrice = stepPrice;
        this.yield = yield;
        this.productNum = productNum;
        this.period = period;
        this.periodUnit = periodUnit;
        this.productStyle = productStyle;
        this.productStart = productStart;
        this.productEnd = productEnd;
        this.isHome = isHome;
        this.activityAlias = activityAlias;
        this.remainingNum = remainingNum;
    }

    public double getAwardRate() {
        return awardRate;
    }

    public void setAwardRate(double awardRate) {
        this.awardRate = awardRate;
    }

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(int stepPrice) {
        this.stepPrice = stepPrice;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(int periodUnit) {
        this.periodUnit = periodUnit;
    }

    public int getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(int productStyle) {
        this.productStyle = productStyle;
    }

    public long getProductStart() {
        return productStart;
    }

    public void setProductStart(long productStart) {
        this.productStart = productStart;
    }

    public long getProductEnd() {
        return productEnd;
    }

    public void setProductEnd(long productEnd) {
        this.productEnd = productEnd;
    }

    public int getIsHome() {
        return isHome;
    }

    public void setIsHome(int isHome) {
        this.isHome = isHome;
    }

    public String getActivityAlias() {
        return activityAlias;
    }

    public void setActivityAlias(String activityAlias) {
        this.activityAlias = activityAlias;
    }

    public int getRemainingNum() {
        return remainingNum;
    }

    public void setRemainingNum(int remainingNum) {
        this.remainingNum = remainingNum;
    }

    public int getMinuSecode() {
        return minuSecode;
    }

    public void setMinuSecode(int minuSecode) {
        this.minuSecode = minuSecode;
    }

    public double getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(double activityAmount) {
        this.activityAmount = activityAmount;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    @Override
    public String toString() {
        return "ProductDetailModel{" +
                "awardRate=" + awardRate +
                ", awardType=" + awardType +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productType=" + productType +
                ", status=" + status +
                ", amount=" + amount +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", stepPrice=" + stepPrice +
                ", yield=" + yield +
                ", productNum=" + productNum +
                ", period=" + period +
                ", periodUnit=" + periodUnit +
                ", productStyle=" + productStyle +
                ", productStart=" + productStart +
                ", productEnd=" + productEnd +
                ", isHome=" + isHome +
                ", activityAlias='" + activityAlias + '\'' +
                ", remainingNum=" + remainingNum +
                ", minuSecode=" + minuSecode +
                ", activityAmount=" + activityAmount +
                ", serverTime=" + serverTime +
                '}';
    }

    public static ProductDetailModel prase(String text){
        return  DMApplication.getInstance().getGson().fromJson(text,new TypeToken<ProductDetailModel>(){}.getType());
    }
}
