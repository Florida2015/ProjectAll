package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by houwen.lai on 2016/12/2.
 * 自动投标详情
 */

public class AutoInvestBean implements Serializable {

    private int uid;
    private int isLimitInvestAmount;
    private BigDecimal minInvestAmount;
    private BigDecimal maxInvestAmount;
    private int loanPeriod;
    private int isLimitYearRate;
    private double minYearRate;
    private double maxYearRate;
    private String isUseRateCoupon;
    private String isUseCashCoupon;
    private int isAutoInvestStatus;
    private long operateTime;
    private String autoInvestConfigCode;
    private String remarks;

    public AutoInvestBean() {
        super();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIsLimitInvestAmount() {
        return isLimitInvestAmount;
    }

    public void setIsLimitInvestAmount(int isLimitInvestAmount) {
        this.isLimitInvestAmount = isLimitInvestAmount;
    }

    public BigDecimal getMinInvestAmount() {
        return minInvestAmount;
    }

    public void setMinInvestAmount(BigDecimal minInvestAmount) {
        this.minInvestAmount = minInvestAmount;
    }

    public BigDecimal getMaxInvestAmount() {
        return maxInvestAmount;
    }

    public void setMaxInvestAmount(BigDecimal maxInvestAmount) {
        this.maxInvestAmount = maxInvestAmount;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public int getIsLimitYearRate() {
        return isLimitYearRate;
    }

    public void setIsLimitYearRate(int isLimitYearRate) {
        this.isLimitYearRate = isLimitYearRate;
    }

    public double getMinYearRate() {
        return minYearRate;
    }

    public void setMinYearRate(double minYearRate) {
        this.minYearRate = minYearRate;
    }

    public double getMaxYearRate() {
        return maxYearRate;
    }

    public void setMaxYearRate(double maxYearRate) {
        this.maxYearRate = maxYearRate;
    }

    public String getIsUseRateCoupon() {
        return isUseRateCoupon;
    }

    public void setIsUseRateCoupon(String isUseRateCoupon) {
        this.isUseRateCoupon = isUseRateCoupon;
    }

    public String getIsUseCashCoupon() {
        return isUseCashCoupon;
    }

    public void setIsUseCashCoupon(String isUseCashCoupon) {
        this.isUseCashCoupon = isUseCashCoupon;
    }

    public int getIsAutoInvestStatus() {
        return isAutoInvestStatus;
    }

    public void setIsAutoInvestStatus(int isAutoInvestStatus) {
        this.isAutoInvestStatus = isAutoInvestStatus;
    }

    public long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(long operateTime) {
        this.operateTime = operateTime;
    }

    public String getAutoInvestConfigCode() {
        return autoInvestConfigCode;
    }

    public void setAutoInvestConfigCode(String autoInvestConfigCode) {
        this.autoInvestConfigCode = autoInvestConfigCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
