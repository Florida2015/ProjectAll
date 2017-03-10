package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * 银行列表
 * Created by houwen.lai on 2016/1/23.
 */
public class BankItemModel implements Serializable {

    private String bankNo;//银行id
    private String bankCode;//银行编码
    private String bankName;//银行名称
    private String logoUrl;//
    private String bankDesc;//银行说明
    private Double singleLimit;//单笔支付最大金额
    private String dailyLimit;//单日制度最大金额
    private String status;
    private String payChannel;
    private int showIndex;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;

    //
    private String pkId;
    private String accountId;
    private String cardName;
    private String cardNo;
    private String cardAddress;
    private String branchBank;
    private String sequenceNo;
    private String authMoney;
    private String isAuthmoney;
    private String province;
    private String city;
    private String isLock;
    private String payStatus;//支付状态 10处理中20验证成功
    private String transDesc;
    private String authSuccessTime;
    private String isDefault="1";//'0'默认 '1'可选
    private String mobileNo;

    private boolean flag;

    private String flagText="";
    private boolean flagVisiable=false;

//    "pkId": "3261199979307822292372",
//            "accountId": "3237714902887146294461",
//            "bankNo": null,
//            "cardName": "工商银行",
//            "cardNo": "0103",
//            "cardAddress": null,
//            "branchBank": null,
//            "sequenceNo": "445B819276CD46E69969",
//            "authMoney": null,
//            "isAuthmoney": null,
//            "province": null,
//            "city": null,
//            "isLock": null,
//            "payStatus": "20",
//            "transDesc": "验证成功",
//            "authSuccessTime": 1452765793000,
//            "payTimes": null,
//            "verifyTimes": null,
//            "isDefault": "0",
//            "payPassword": null,
//            "paySalt": null,
//            "payFailedCount": null,
//            "payLastFailedTime": null,
//            "createBy": null,
//            "createTime": 1452765793000,
//            "updateBy": null,
//            "updateTime": 1452766115000,
//            "mobileNo": null,
//            "bankName": "工商银行",
//            "singleLimit": 10000,
//            "dailyLimit": 10,
//            "bankDesc": null,
//            "bankCode": "ICBC",
//            "flag": false

    public BankItemModel() {
        super();
    }

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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBankDesc() {
        return bankDesc;
    }

    public void setBankDesc(String bankDesc) {
        this.bankDesc = bankDesc;
    }

    public Double getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(Double singleLimit) {
        this.singleLimit = singleLimit;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardAddress() {
        return cardAddress;
    }

    public void setCardAddress(String cardAddress) {
        this.cardAddress = cardAddress;
    }

    public String getBranchBank() {
        return branchBank;
    }

    public void setBranchBank(String branchBank) {
        this.branchBank = branchBank;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getAuthMoney() {
        return authMoney;
    }

    public void setAuthMoney(String authMoney) {
        this.authMoney = authMoney;
    }

    public String getIsAuthmoney() {
        return isAuthmoney;
    }

    public void setIsAuthmoney(String isAuthmoney) {
        this.isAuthmoney = isAuthmoney;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsLock() {
        return isLock;
    }

    public void setIsLock(String isLock) {
        this.isLock = isLock;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getTransDesc() {
        return transDesc;
    }

    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    public String getAuthSuccessTime() {
        return authSuccessTime;
    }

    public void setAuthSuccessTime(String authSuccessTime) {
        this.authSuccessTime = authSuccessTime;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlagVisiable() {
        return flagVisiable;
    }

    public void setFlagVisiable(boolean flagVisiable) {
        this.flagVisiable = flagVisiable;
    }

    public String getFlagText() {
        return flagText;
    }

    public void setFlagText(String flagText) {
        this.flagText = flagText;
    }

    @Override
    public String toString() {
        return "BankItemModel{" +
                "bankNo='" + bankNo + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", bankDesc='" + bankDesc + '\'' +
                ", singleLimit=" + singleLimit +
                ", dailyLimit='" + dailyLimit + '\'' +
                ", status='" + status + '\'' +
                ", payChannel='" + payChannel + '\'' +
                ", showIndex=" + showIndex +
                ", createBy='" + createBy + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", pkId='" + pkId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", cardName='" + cardName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", cardAddress='" + cardAddress + '\'' +
                ", branchBank='" + branchBank + '\'' +
                ", sequenceNo='" + sequenceNo + '\'' +
                ", authMoney='" + authMoney + '\'' +
                ", isAuthmoney='" + isAuthmoney + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", isLock='" + isLock + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", transDesc='" + transDesc + '\'' +
                ", authSuccessTime='" + authSuccessTime + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", flag=" + flag +
                '}';
    }
}
