package com.hxxc.user.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by houwen.lai on 2017/2/24.
 * 日历
 */


public class ProdInterestBean implements Serializable {

    private int interestId;
    private String contractId;
    private int productId;
    private int team;
    private long startTime;
    private long endTime;
    private BigDecimal capital;
    private BigDecimal interest;
    private BigDecimal payCapital;
    private BigDecimal payInterest;
    private BigDecimal payMonery;
    private String remarks;
    private int isPay;
    private String isValid;
    private String type;
    private String lendDate;
    private String handleStatus;
    private String isContininve;
    private String redeemStatus;
    private BigDecimal totalTermInterests;
    private BigDecimal originalInterest;
    private BigDecimal dayInterset;
    private long updateTime;
    private long contractUpdTime;
    private String remark;
    private long createTime;
    private String version;
    private String canPayDate;
    private String contrEndDate;
    private String contrValueDate;
    private String endTimeStr;
    private String payCapitalCount;
    private String payInterestCount;
    private BigDecimal payCount;
    private String endTimeMonthStr;//02月

    public ProdInterestBean() {
        super();
    }

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

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getPayCapital() {
        return payCapital;
    }

    public void setPayCapital(BigDecimal payCapital) {
        this.payCapital = payCapital;
    }

    public BigDecimal getPayInterest() {
        return payInterest;
    }

    public void setPayInterest(BigDecimal payInterest) {
        this.payInterest = payInterest;
    }

    public BigDecimal getPayMonery() {
        return payMonery;
    }

    public void setPayMonery(BigDecimal payMonery) {
        this.payMonery = payMonery;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
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

    public String getLendDate() {
        return lendDate;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getIsContininve() {
        return isContininve;
    }

    public void setIsContininve(String isContininve) {
        this.isContininve = isContininve;
    }

    public String getRedeemStatus() {
        return redeemStatus;
    }

    public void setRedeemStatus(String redeemStatus) {
        this.redeemStatus = redeemStatus;
    }

    public BigDecimal getTotalTermInterests() {
        return totalTermInterests;
    }

    public void setTotalTermInterests(BigDecimal totalTermInterests) {
        this.totalTermInterests = totalTermInterests;
    }

    public BigDecimal getOriginalInterest() {
        return originalInterest;
    }

    public void setOriginalInterest(BigDecimal originalInterest) {
        this.originalInterest = originalInterest;
    }

    public BigDecimal getDayInterset() {
        return dayInterset;
    }

    public void setDayInterset(BigDecimal dayInterset) {
        this.dayInterset = dayInterset;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getContractUpdTime() {
        return contractUpdTime;
    }

    public void setContractUpdTime(long contractUpdTime) {
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCanPayDate() {
        return canPayDate;
    }

    public void setCanPayDate(String canPayDate) {
        this.canPayDate = canPayDate;
    }

    public String getContrEndDate() {
        return contrEndDate;
    }

    public void setContrEndDate(String contrEndDate) {
        this.contrEndDate = contrEndDate;
    }

    public String getContrValueDate() {
        return contrValueDate;
    }

    public void setContrValueDate(String contrValueDate) {
        this.contrValueDate = contrValueDate;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getPayCapitalCount() {
        return payCapitalCount;
    }

    public void setPayCapitalCount(String payCapitalCount) {
        this.payCapitalCount = payCapitalCount;
    }

    public String getPayInterestCount() {
        return payInterestCount;
    }

    public void setPayInterestCount(String payInterestCount) {
        this.payInterestCount = payInterestCount;
    }

    public BigDecimal getPayCount() {
        return payCount;
    }

    public void setPayCount(BigDecimal payCount) {
        this.payCount = payCount;
    }

    public String getEndTimeMonthStr() {
        return endTimeMonthStr;
    }

    public void setEndTimeMonthStr(String endTimeMonthStr) {
        this.endTimeMonthStr = endTimeMonthStr;
    }

    @Override
    public String toString() {
        return "ProdInterestBean{" +
                "interestId=" + interestId +
                ", contractId='" + contractId + '\'' +
                ", productId=" + productId +
                ", team=" + team +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", capital=" + capital +
                ", interest=" + interest +
                ", payCapital=" + payCapital +
                ", payInterest=" + payInterest +
                ", payMonery=" + payMonery +
                ", remarks='" + remarks + '\'' +
                ", isPay=" + isPay +
                ", isValid='" + isValid + '\'' +
                ", type='" + type + '\'' +
                ", lendDate='" + lendDate + '\'' +
                ", handleStatus='" + handleStatus + '\'' +
                ", isContininve='" + isContininve + '\'' +
                ", redeemStatus='" + redeemStatus + '\'' +
                ", totalTermInterests=" + totalTermInterests +
                ", originalInterest=" + originalInterest +
                ", dayInterset=" + dayInterset +
                ", updateTime=" + updateTime +
                ", contractUpdTime=" + contractUpdTime +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", version='" + version + '\'' +
                ", canPayDate='" + canPayDate + '\'' +
                ", contrEndDate='" + contrEndDate + '\'' +
                ", contrValueDate='" + contrValueDate + '\'' +
                ", endTimeStr='" + endTimeStr + '\'' +
                ", payCapitalCount='" + payCapitalCount + '\'' +
                ", payInterestCount='" + payInterestCount + '\'' +
                ", payCount=" + payCount +
                ", endTimeMonthStr=" +  endTimeMonthStr+
                '}';
    }
}
