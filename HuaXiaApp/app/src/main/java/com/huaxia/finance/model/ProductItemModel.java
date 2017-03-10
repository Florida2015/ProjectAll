package com.huaxia.finance.model;

import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/1/20.
 * 产品列表item模型
 */
public class ProductItemModel implements Serializable {

    private String activityName;//活动名称
    private double awardRate;//奖励利率
    private String activityType;//1. 新手标,2. 转发类活动,3. 增益类活动,4. 送优惠券活动,5. 实物类活动,6. 其他
    private int awardType;//奖励类型
    private String activityAlias;//活动别名

    private String productId;
    private String productName;
    private String productAlias;//产品别名
    private int productType;//产品类型 '1:固定利率, 2:按月复投, 3:每月付息，4：体验金',
    private int productStyle;
    private String productCode;//产品代码



    private int status;//1.待售，2.在售，3.售磬，4.封闭，5.到期，6.还款中，7.已还款
    private float amount;//融资金额元
    private double minPrice;//最小购买金额 起投金额
    private double maxPrice;//最大购买金额'
    private int stepPrice;//步进金额
    private double yield;//收益率
    private String extraYield;
    private String productNum;//期数
    private String period;//有效期
    private int periodUnit;
    private String expiryDate;
    private String comments;
    private long productStart;//起售时间
    private long productEnd;//结束时间
    private String isHome;
    private long serverTime;//服务器当前时间
    private float remainingNum;//剩余金额
    private int minuSecond;//倒计时


    public ProductItemModel() {
        super();
    }

    public ProductItemModel(String activityName, double awardRate, String activityType, int awardType, String activityAlias, String productId, String productName, String productAlias, int productType, int productStyle, String productCode, int status, long amount, double minPrice, double maxPrice, int stepPrice, double yield, String extraYield, String productNum, String period, int periodUnit, String expiryDate, String comments, long productStart, long productEnd, String isHome, long serverTime, long remainingNum) {
        this.activityName = activityName;
        this.awardRate = awardRate;
        this.activityType = activityType;
        this.awardType = awardType;
        this.activityAlias = activityAlias;
        this.productId = productId;
        this.productName = productName;
        this.productAlias = productAlias;
        this.productType = productType;
        this.productStyle = productStyle;
        this.productCode = productCode;
        this.status = status;
        this.amount = amount;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.stepPrice = stepPrice;
        this.yield = yield;
        this.extraYield = extraYield;
        this.productNum = productNum;
        this.period = period;
        this.periodUnit = periodUnit;
        this.expiryDate = expiryDate;
        this.comments = comments;
        this.productStart = productStart;
        this.productEnd = productEnd;
        this.isHome = isHome;
        this.serverTime = serverTime;
        this.remainingNum = remainingNum;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public double getAwardRate() {
        return awardRate;
    }

    public void setAwardRate(double awardRate) {
        this.awardRate = awardRate;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    public String getActivityAlias() {
        return activityAlias;
    }

    public void setActivityAlias(String activityAlias) {
        this.activityAlias = activityAlias;
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

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(int productStyle) {
        this.productStyle = productStyle;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
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

    public String getExtraYield() {
        return extraYield;
    }

    public void setExtraYield(String extraYield) {
        this.extraYield = extraYield;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(int periodUnit) {
        this.periodUnit = periodUnit;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getIsHome() {
        return isHome;
    }

    public void setIsHome(String isHome) {
        this.isHome = isHome;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public float getRemainingNum() {
        return remainingNum;
    }

    public void setRemainingNum(float remainingNum) {
        this.remainingNum = remainingNum;
    }

    public int getMinuSecond() {
        return minuSecond;
    }

    public void setMinuSecond(int minuSecond) {
        this.minuSecond = minuSecond;
    }

    @Override
    public String toString() {
        return "ProductItemModel{" +
                "activityName='" + activityName + '\'' +
                ", awardRate=" + awardRate +
                ", activityType='" + activityType + '\'' +
                ", awardType=" + awardType +
                ", activityAlias='" + activityAlias + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productAlias='" + productAlias + '\'' +
                ", productType=" + productType +
                ", productStyle=" + productStyle +
                ", productCode='" + productCode + '\'' +
                ", status=" + status +
                ", amount=" + amount +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", stepPrice=" + stepPrice +
                ", yield=" + yield +
                ", extraYield='" + extraYield + '\'' +
                ", productNum='" + productNum + '\'' +
                ", period='" + period + '\'' +
                ", periodUnit=" + periodUnit +
                ", expiryDate='" + expiryDate + '\'' +
                ", comments='" + comments + '\'' +
                ", productStart=" + productStart +
                ", productEnd=" + productEnd +
                ", isHome='" + isHome + '\'' +
                ", serverTime=" + serverTime +
                ", remainingNum=" + remainingNum +
                ", minuSecond=" + minuSecond +
                '}';
    }

    public static ProductItemModel prase(String text){
        return  DMApplication.getInstance().getGson().fromJson(text,new TypeToken<ProductItemModel>(){}.getType());
    }
}
