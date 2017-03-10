package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * 用户的个人信息
 * Created by houwen.lai on 2016/2/3.
 */
public class UserInfoModel implements Serializable {

    private String accountId;
    private String certType;
    private String certNo;
    private String mobileNo;
    private String userName;
    private String accStatus;
    private String authnameStatus;//"0"已经绑卡 “1”
    private String cardNo;
    private int profiting;//收益中订单数量
    private int willend;//计量到期订单数联
    private int repayment;//还款中订单数量
    private int repaymented;//已还款订单数量

    private double yield;
    private int period;
    private String orderMoney;//在投金额
    private String totalProfitStr;//每日收益
    private String totalInvestmentAmountStr;//
    private double totalIncome;
    private double amount;
    private String phone;
    private String accumulatedIncome;//累计收益

    private String maskCertNo;//身份证

//    "accountId": "3237714902887146294461",
//            "certType": "1",
//            "certNo": "310101199303120022",
//            "mobileNo": "17051026720",
//            "userName": "张三",
//            "accStatus": null,
//            "authnameStatus": "0",
//            "cardNo": null,
//            "profiting": 48,
//            "willend": 3,
//            "repayment": 0,
//            "repaymented": 0,
//            "yield": 0,
//            "period": 0,
//            "orderMoney": 0,
//            "totalProfitStr": "6.45",
//            "totalInvestmentAmountStr": "18838.45",
//            "totalIncome": 0,
//            "amount": 0,
//            "phone": "170****6720"
//    "repaymented": 0,已还款订单数量
//    "repayment": null,还款中订单数量
//    "willend": null,计量到期订单数联
//    "profiting": null,收益中订单数量


    public UserInfoModel() {
        super();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public String getAuthnameStatus() {
        return authnameStatus;
    }

    public void setAuthnameStatus(String authnameStatus) {
        this.authnameStatus = authnameStatus;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getProfiting() {
        return profiting;
    }

    public void setProfiting(int profiting) {
        this.profiting = profiting;
    }

    public int getWillend() {
        return willend;
    }

    public void setWillend(int willend) {
        this.willend = willend;
    }

    public int getRepaymented() {
        return repaymented;
    }

    public void setRepaymented(int repaymented) {
        this.repaymented = repaymented;
    }

    public int getRepayment() {
        return repayment;
    }

    public void setRepayment(int repayment) {
        this.repayment = repayment;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String  getTotalProfitStr() {
        return totalProfitStr;
    }

    public void setTotalProfitStr(String totalProfitStr) {
        this.totalProfitStr = totalProfitStr;
    }

    public String getTotalInvestmentAmountStr() {
        return totalInvestmentAmountStr;
    }

    public void setTotalInvestmentAmountStr(String totalInvestmentAmountStr) {
        this.totalInvestmentAmountStr = totalInvestmentAmountStr;
    }

    public String getAccumulatedIncome() {
        return accumulatedIncome;
    }

    public void setAccumulatedIncome(String accumulatedIncome) {
        this.accumulatedIncome = accumulatedIncome;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMaskCertNo() {
        return maskCertNo;
    }

    public void setMaskCertNo(String maskCertNo) {
        this.maskCertNo = maskCertNo;
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "accountId='" + accountId + '\'' +
                ", certType='" + certType + '\'' +
                ", certNo='" + certNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", userName='" + userName + '\'' +
                ", accStatus='" + accStatus + '\'' +
                ", authnameStatus='" + authnameStatus + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", profiting='" + profiting + '\'' +
                ", willend='" + willend + '\'' +
                ", repayment='" + repayment + '\'' +
                ", repaymented='" + repaymented + '\'' +
                ", yield=" + yield +
                ", period=" + period +
                ", orderMoney='" + orderMoney + '\'' +
                ", totalProfitStr='" + totalProfitStr + '\'' +
                ", totalInvestmentAmountStr='" + totalInvestmentAmountStr + '\'' +
                ", totalIncome=" + totalIncome +
                ", amount=" + amount +
                ", phone='" + phone + '\'' +
                ", accumulatedIncome='" + accumulatedIncome + '\'' +
                ", maskCertNo='" + maskCertNo + '\'' +
                '}';
    }
}
