package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/28.
 * 银行item
 */

public class BankItemBean implements Serializable{

    private String icon;
    private String name;
    private String bankName;
    private String bankCode;

    private String bankId;
    private String acNo;
    private String acName;
    private double actual;
    private double availablebal;
    private double frozbl;
    private String bindcardStatus;//

    public BankItemBean() {
        super();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public BankItemBean(double frozbl, String icon, String name, String bankName, String bankCode, String bankId, String acNo, String acName, double actual, double availablebal) {
        this.frozbl = frozbl;
        this.icon = icon;
        this.name = name;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.bankId = bankId;
        this.acNo = acNo;
        this.acName = acName;
        this.actual = actual;
        this.availablebal = availablebal;
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

    public double getAvailablebal() {
        return availablebal;
    }

    public void setAvailablebal(double availablebal) {
        this.availablebal = availablebal;
    }

    public double getFrozbl() {
        return frozbl;
    }

    public void setFrozbl(double frozbl) {
        this.frozbl = frozbl;
    }

    public String getBindcardStatus() {
        return bindcardStatus;
    }

    public void setBindcardStatus(String bindcardStatus) {
        this.bindcardStatus = bindcardStatus;
    }

    @Override
    public String toString() {
        return "BankItemBean{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankId='" + bankId + '\'' +
                ", acNo='" + acNo + '\'' +
                ", acName='" + acName + '\'' +
                ", actual=" + actual +
                ", availablebal=" + availablebal +
                ", frozbl=" + frozbl +
                '}';
    }

}
