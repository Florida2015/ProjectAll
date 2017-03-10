package com.hxxc.user.app.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6191227590748979488L;

	/**
	 * iid : 47
	 * bank : 中国工商银行
	 * branch :
	 * bankCard : 622****5653
	 * isDefaultCard : 1
	 * amount : 0
	 * pcLogoUrl : www.pclogo.com
	 * mobileLogoUrl : www.applogo.com
	 * singleLimit : 5000
	 * dailyLimit : 100000
	 * monthLimit : 300000
	 * bankColour : www.pccolour.com
	 * uid :
	 * mobile : 152****4603
	 * singleWLimit : 0.5
	 * dailyWLimit : 10
	 * monthWLimit : 30
	 */

	private int iid;
	private String bank;
	private String branch;
	private String bankCard;
	private int isDefaultCard;
	private BigDecimal amount;
	private String pcLogoUrl;
	private String mobileLogoUrl;
	private BigDecimal singleLimit;
	private BigDecimal dailyLimit;
	private BigDecimal monthLimit;
	private String bankColour;
	private String uid;
	private String mobile;
	private BigDecimal singleWLimit;
	private BigDecimal dailyWLimit;
	private BigDecimal monthWLimit;

	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public int getIsDefaultCard() {
		return isDefaultCard;
	}

	public void setIsDefaultCard(int isDefaultCard) {
		this.isDefaultCard = isDefaultCard;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public String getBankColour() {
		return bankColour;
	}

	public void setBankColour(String bankColour) {
		this.bankColour = bankColour;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
}
