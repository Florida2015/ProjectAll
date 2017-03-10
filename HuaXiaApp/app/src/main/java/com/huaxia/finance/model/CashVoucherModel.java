package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * 优惠券 model
 * Created by houwen.lao on 2016/2/1.
 */
public class CashVoucherModel implements Serializable {

    private String pkid;
    private String accountId;
    private String discountVoucherId;// 优惠券ID
    private String activityId;
    private int gainType;//增益类型1.收益率，2.本金券，3.抵扣券，4.加金券5.现金券
    private String gainValue;//增加值
    private String amount;//奖励金额
    private int isUse;//是否使用 0未使用1已使用
    private long createTime;
    private long useDate;
    private long endDate2;//截止日期
    private String orderId;
    private String name;//优惠券名称
    private String comments;//说明
    private int threshold;//金额门槛
    private int isUseAble;//优惠券是否可用 0可用1不可用

    private int indexx;//0 1 2

//    "pkid": "2",
//            "accountId": "3237714902887146294461",
//            "discountVoucherId": "2666063851145167941650",
//            "activityId": "2",
//            "gainType": 2,
//            "gainValue": 0.2,
//            "amount": 200,
//            "isUse": 0,
//            "createTime": 1453370054000,
//            "useDate": 1454086399000,
//            "endDate2": 1455962058000,
//            "orderId": "",
//            "name": "测试",
//            "comments": "1",
//            "threshold": 600,
//            "isUseAble": 0

    public CashVoucherModel() {
        super();
    }


    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getDiscountVoucherId() {
        return discountVoucherId;
    }

    public void setDiscountVoucherId(String discountVoucherId) {
        this.discountVoucherId = discountVoucherId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getGainType() {
        return gainType;
    }

    public void setGainType(int gainType) {
        this.gainType = gainType;
    }

    public String getGainValue() {
        return gainValue;
    }

    public void setGainValue(String gainValue) {
        this.gainValue = gainValue;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUseDate() {
        return useDate;
    }

    public void setUseDate(long useDate) {
        this.useDate = useDate;
    }

    public long getEndDate2() {
        return endDate2;
    }

    public void setEndDate2(long endDate2) {
        this.endDate2 = endDate2;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getIsUseAble() {
        return isUseAble;
    }

    public void setIsUseAble(int isUseAble) {
        this.isUseAble = isUseAble;
    }

    public int getIndexx() {
        return indexx;
    }

    public void setIndexx(int indexx) {
        this.indexx = indexx;
    }

    @Override
    public String toString() {
        return "CashVoucherModel{" +
                "pkid='" + pkid + '\'' +
                ", accountId='" + accountId + '\'' +
                ", discountVoucherId='" + discountVoucherId + '\'' +
                ", activityId='" + activityId + '\'' +
                ", gainType=" + gainType +
                ", gainValue='" + gainValue + '\'' +
                ", amount='" + amount + '\'' +
                ", isUse=" + isUse +
                ", createTime=" + createTime +
                ", useDate=" + useDate +
                ", endDate2=" + endDate2 +
                ", orderId='" + orderId + '\'' +
                ", name='" + name + '\'' +
                ", comments='" + comments + '\'' +
                ", threshold=" + threshold +
                ", isUseAble=" + isUseAble +
                ", indexx=" + indexx +
                '}';
    }
}
