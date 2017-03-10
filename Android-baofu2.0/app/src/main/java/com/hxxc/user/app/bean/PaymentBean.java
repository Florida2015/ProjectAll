package com.hxxc.user.app.bean;

import java.math.BigDecimal;

/**
 * Created by chenqun on 2017/2/22.
 */

public class PaymentBean {

    /**
     * interestId : 191601
     * contractId : HXXC32996949188
     * productId : 50
     * team : 1
     * startTime : 1482940800000
     * endTime : 1485532800000
     * capital : 50000
     * interest : 467.1233
     * payCapital : 0
     * payInterest : 467.13
     * payMonery : 467.13
     * remarks : null
     * isPay : 0
     * isValid : 0
     * type : 2
     * lendDate : null
     * handleStatus : null
     * isContininve : null
     * redeemStatus :
     * totalTermInterests : 0
     * originalInterest : 0
     * dayInterset : 15.0685
     * updateTime : null
     * contractUpdTime : null
     * remark :
     * createTime : 1482419756000
     * version : 0
     * canPayDate : null
     * contrEndDate : null
     * contrValueDate : null
     * endTimeStr : 2017-01
     * payCapitalCount : null
     * payInterestCount : 1358.93
     */

    private String endTimeMonthStr;
    private int interestId;
    private String contractId;
    private int productId;
    private int team;
    private long startTime;
    private long endTime;
    private float capital;
    private double interest;
    private float payCapital;
    private double payInterest;
    private double payMonery;
    private Object remarks;
    private int isPay;
    private String isValid;
    private String type;
    private Object lendDate;
    private Object handleStatus;
    private Object isContininve;
    private String redeemStatus;
    private double totalTermInterests;
    private double originalInterest;
    private double dayInterset;
    private Object updateTime;
    private Object contractUpdTime;
    private String remark;
    private long createTime;
    private int version;
    private Object canPayDate;
    private Object contrEndDate;
    private Object contrValueDate;
    private String endTimeStr;
    private BigDecimal payCapitalCount;
    private BigDecimal payInterestCount;
    private String showContext;//: "待回款本金",
    private BigDecimal showValue;// 934.26

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public float getCapital() {
        return capital;
    }

    public void setCapital(float capital) {
        this.capital = capital;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public float getPayCapital() {
        return payCapital;
    }

    public void setPayCapital(float payCapital) {
        this.payCapital = payCapital;
    }

    public double getPayInterest() {
        return payInterest;
    }

    public void setPayInterest(double payInterest) {
        this.payInterest = payInterest;
    }

    public double getPayMonery() {
        return payMonery;
    }

    public void setPayMonery(double payMonery) {
        this.payMonery = payMonery;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getLendDate() {
        return lendDate;
    }

    public void setLendDate(Object lendDate) {
        this.lendDate = lendDate;
    }

    public Object getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Object handleStatus) {
        this.handleStatus = handleStatus;
    }

    public Object getIsContininve() {
        return isContininve;
    }

    public void setIsContininve(Object isContininve) {
        this.isContininve = isContininve;
    }

    public String getRedeemStatus() {
        return redeemStatus;
    }

    public void setRedeemStatus(String redeemStatus) {
        this.redeemStatus = redeemStatus;
    }

    public double getTotalTermInterests() {
        return totalTermInterests;
    }

    public void setTotalTermInterests(double totalTermInterests) {
        this.totalTermInterests = totalTermInterests;
    }

    public double getOriginalInterest() {
        return originalInterest;
    }

    public void setOriginalInterest(double originalInterest) {
        this.originalInterest = originalInterest;
    }

    public double getDayInterset() {
        return dayInterset;
    }

    public void setDayInterset(double dayInterset) {
        this.dayInterset = dayInterset;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public Object getContractUpdTime() {
        return contractUpdTime;
    }

    public void setContractUpdTime(Object contractUpdTime) {
        this.contractUpdTime = contractUpdTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Object getCanPayDate() {
        return canPayDate;
    }

    public void setCanPayDate(Object canPayDate) {
        this.canPayDate = canPayDate;
    }

    public Object getContrEndDate() {
        return contrEndDate;
    }

    public void setContrEndDate(Object contrEndDate) {
        this.contrEndDate = contrEndDate;
    }

    public Object getContrValueDate() {
        return contrValueDate;
    }

    public void setContrValueDate(Object contrValueDate) {
        this.contrValueDate = contrValueDate;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public BigDecimal getPayCapitalCount() {
        return payCapitalCount;
    }

    public void setPayCapitalCount(BigDecimal payCapitalCount) {
        this.payCapitalCount = payCapitalCount;
    }

    public BigDecimal getPayInterestCount() {
        return payInterestCount;
    }

    public void setPayInterestCount(BigDecimal payInterestCount) {
        this.payInterestCount = payInterestCount;
    }

    public String getEndTimeMonthStr() {
        return endTimeMonthStr;
    }

    public void setEndTimeMonthStr(String endTimeMonthStr) {
        this.endTimeMonthStr = endTimeMonthStr;
    }

    public String getShowContext() {
        return showContext;
    }

    public void setShowContext(String showContext) {
        this.showContext = showContext;
    }

    public BigDecimal getShowValue() {
        return showValue;
    }

    public void setShowValue(BigDecimal showValue) {
        this.showValue = showValue;
    }
}
