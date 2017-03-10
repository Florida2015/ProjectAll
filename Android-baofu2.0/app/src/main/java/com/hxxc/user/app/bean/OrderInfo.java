package com.hxxc.user.app.bean;

import java.io.Serializable;

public class OrderInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2806483639650942417L;

	/**
	 * oid :
	 * createTime : 1478490607365
	 * pid : 12
	 * targets :
	 * uid : 22
	 * status :
	 * remarks :
	 * contractId :
	 * signTime :
	 * money : 50000
	 * orderNo : HXXC34838107355
	 * profitTime :
	 * expireTime :
	 * quitTime :
	 * rewardId :
	 * source : 2
	 * orderStatus : 0
	 * tatalProfit :
	 * isConInvest : 0
	 * conInvestType :
	 * parentOrderids :
	 * interestRate :
	 * payMoney :
	 * accountMoney :
	 * iid :
	 */

	private String oid;
	private long createTime;
	private int pid;
	private String targets;
	private int uid;
	private String status;
	private String remarks;
	private String contractId;
	private String signTime;
	private int money;
	private String orderNo;
	private String profitTime;
	private String expireTime;
	private String quitTime;
	private String rewardId;
	private String source;
	private int orderStatus;
	private String tatalProfit;
	private int isConInvest;
	private String conInvestType;
	private String parentOrderids;
	private String interestRate;
	private String payMoney;
	private String accountMoney;
	private String iid;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProfitTime() {
		return profitTime;
	}

	public void setProfitTime(String profitTime) {
		this.profitTime = profitTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(String quitTime) {
		this.quitTime = quitTime;
	}

	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTatalProfit() {
		return tatalProfit;
	}

	public void setTatalProfit(String tatalProfit) {
		this.tatalProfit = tatalProfit;
	}

	public int getIsConInvest() {
		return isConInvest;
	}

	public void setIsConInvest(int isConInvest) {
		this.isConInvest = isConInvest;
	}

	public String getConInvestType() {
		return conInvestType;
	}

	public void setConInvestType(String conInvestType) {
		this.conInvestType = conInvestType;
	}

	public String getParentOrderids() {
		return parentOrderids;
	}

	public void setParentOrderids(String parentOrderids) {
		this.parentOrderids = parentOrderids;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	public String getAccountMoney() {
		return accountMoney;
	}

	public void setAccountMoney(String accountMoney) {
		this.accountMoney = accountMoney;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}
}