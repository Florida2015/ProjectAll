package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by houwen.lai on 2016/12/14.
 * 我的 资产 信息
 */

public class AssetsBean implements Serializable {

    private String bankId;
    private String acNo;
    private String acName;
    private double actual;//
    private BigDecimal availablebal;//可用金额
    private BigDecimal frozbl;//冻结金额
    private String bindcardStatus;////
    private BigDecimal acctbal;//余额
    private BigDecimal accountTotalAssets;//总金额
    private BigDecimal collectCorpus;//待收本金
    private BigDecimal collectInterest;//待收利息
    private BigDecimal earnedIncome;//已赚收益
    private BigDecimal expectedReturn;//预期收益

    public AssetsBean() {
        super();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public BigDecimal getAvailablebal() {
        return availablebal;
    }

    public void setAvailablebal(BigDecimal availablebal) {
        this.availablebal = availablebal;
    }

    public BigDecimal getFrozbl() {
        return frozbl;
    }

    public void setFrozbl(BigDecimal frozbl) {
        this.frozbl = frozbl;
    }

    public String getBindcardStatus() {
        return bindcardStatus;
    }

    public void setBindcardStatus(String bindcardStatus) {
        this.bindcardStatus = bindcardStatus;
    }

    public BigDecimal getAcctbal() {
        return acctbal;
    }

    public void setAcctbal(BigDecimal acctbal) {
        this.acctbal = acctbal;
    }

    public BigDecimal getAccountTotalAssets() {
        return accountTotalAssets;
    }

    public void setAccountTotalAssets(BigDecimal accountTotalAssets) {
        this.accountTotalAssets = accountTotalAssets;
    }

    public BigDecimal getCollectCorpus() {
        return collectCorpus;
    }

    public void setCollectCorpus(BigDecimal collectCorpus) {
        this.collectCorpus = collectCorpus;
    }

    public BigDecimal getCollectInterest() {
        return collectInterest;
    }

    public void setCollectInterest(BigDecimal collectInterest) {
        this.collectInterest = collectInterest;
    }

    public BigDecimal getEarnedIncome() {
        return earnedIncome;
    }

    public void setEarnedIncome(BigDecimal earnedIncome) {
        this.earnedIncome = earnedIncome;
    }

    public BigDecimal getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(BigDecimal expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    @Override
    public String toString() {
        return "AssetsBean{" +
                "bankId='" + bankId + '\'' +
                ", acNo='" + acNo + '\'' +
                ", acName='" + acName + '\'' +
                ", actual=" + actual +
                ", availablebal=" + availablebal +
                ", frozbl=" + frozbl +
                ", bindcardStatus='" + bindcardStatus + '\'' +
                ", acctbal=" + acctbal +
                ", accountTotalAssets=" + accountTotalAssets +
                ", collectCorpus='" + collectCorpus + '\'' +
                ", collectInterest='" + collectInterest + '\'' +
                ", earnedIncome='" + earnedIncome + '\'' +
                ", expectedReturn='" + expectedReturn + '\'' +
                '}';
    }
}
