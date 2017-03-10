package com.hxxc.user.app.bean;

import java.math.BigDecimal;

/**
 * Created by chenqun on 2016/11/11.
 */

public class BankInfo {


    /**
     * bankNo : 1
     * bankCode : ICBC
     * bankName : 中国工商银行
     * pcLogoUrl : /ban_icon/pc/icon/gsyh.png
     * mobileLogoUrl : /ban_icon/app/40-40/gsyh.png
     * bankDesc : 中国工商银行
     * singleLimit : 50000
     * dailyLimit : 500000
     * monthLimit : 5000000
     * status : 1
     * payChannel : 1
     * showIndex : 1
     * createBy : admin
     * createTime : 1479778740000
     * updateBy : admin
     * updateTime : 1479792681000
     * payMethod : api
     * crmBankNo : B005
     * bankColour : /ban_icon/bg/blue.png
     * singleWLimit : 5
     * dailyWLimit : 50
     * monthWLimit : 500
     * realPcLogoUrl : http://192.168.11.48:8089/picture/ban_icon/pc/icon/gsyh.png
     * realMobileLogoUrl : http://192.168.11.48:8089/picture/ban_icon/app/40-40/gsyh.png
     * realBankColour : http://192.168.11.48:8089/picture/ban_icon/bg/blue.png
     */

    private String bankNo;
    private String bankCode;
    private String bankName;
    private String pcLogoUrl;
    private String mobileLogoUrl;
    private String bankDesc;
    private BigDecimal singleLimit;
    private BigDecimal dailyLimit;
    private BigDecimal monthLimit;
    private String status;
    private String payChannel;
    private int showIndex;
    private String createBy;
    private long createTime;
    private String updateBy;
    private long updateTime;
    private String payMethod;
    private String crmBankNo;
    private String bankColour;
    private BigDecimal singleWLimit;
    private BigDecimal dailyWLimit;
    private BigDecimal monthWLimit;
    private String realPcLogoUrl;
    private String realMobileLogoUrl;
    private String realBankColour;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getPcLogoUrl() {
        return pcLogoUrl;
    }

    public void setPcLogoUrl(String pcLogoUrl) {
        this.pcLogoUrl = pcLogoUrl;
    }

    public String getMobileLogoUrl() {
        return mobileLogoUrl;
    }

    public void setMobileLogoUrl(String mobileLogoUrl) {
        this.mobileLogoUrl = mobileLogoUrl;
    }

    public String getBankDesc() {
        return bankDesc;
    }

    public void setBankDesc(String bankDesc) {
        this.bankDesc = bankDesc;
    }

    public BigDecimal getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(BigDecimal singleLimit) {
        this.singleLimit = singleLimit;
    }

    public BigDecimal getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(BigDecimal dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public BigDecimal getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(BigDecimal monthLimit) {
        this.monthLimit = monthLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getCrmBankNo() {
        return crmBankNo;
    }

    public void setCrmBankNo(String crmBankNo) {
        this.crmBankNo = crmBankNo;
    }

    public String getBankColour() {
        return bankColour;
    }

    public void setBankColour(String bankColour) {
        this.bankColour = bankColour;
    }

    public BigDecimal getSingleWLimit() {
        return singleWLimit;
    }

    public void setSingleWLimit(BigDecimal singleWLimit) {
        this.singleWLimit = singleWLimit;
    }

    public BigDecimal getDailyWLimit() {
        return dailyWLimit;
    }

    public void setDailyWLimit(BigDecimal dailyWLimit) {
        this.dailyWLimit = dailyWLimit;
    }

    public BigDecimal getMonthWLimit() {
        return monthWLimit;
    }

    public void setMonthWLimit(BigDecimal monthWLimit) {
        this.monthWLimit = monthWLimit;
    }

    public String getRealPcLogoUrl() {
        return realPcLogoUrl;
    }

    public void setRealPcLogoUrl(String realPcLogoUrl) {
        this.realPcLogoUrl = realPcLogoUrl;
    }

    public String getRealMobileLogoUrl() {
        return realMobileLogoUrl;
    }

    public void setRealMobileLogoUrl(String realMobileLogoUrl) {
        this.realMobileLogoUrl = realMobileLogoUrl;
    }

    public String getRealBankColour() {
        return realBankColour;
    }

    public void setRealBankColour(String realBankColour) {
        this.realBankColour = realBankColour;
    }
}
