package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * 订单详情
 * Created by houwen.lai on 2016/2/3.
 */
public class OrderDetailModel implements Serializable {

    private String orderId;
    private int orderType;//
    private String productNum;//
    private int agreementAmount;
    private String productId;
    private String accountId;
    private double orderMoney;//
    private long orderTime;
    private long profitDay;
    private long expiryDate;
    private double totalIncome;
    private String totalAmount;
    private String orderStatus;// orderStatus[Integer] 订单状态 10.订单处理中，20.付款成功，21.付款失败，30.计息中，40.产品到期，50.还款准备中，60.已还款,70即将到期
    private String cardNo;
    private long paymentTime;
    private String payinsId;
    private String awardType;
    private double amount;
    private String comments;
    private int debtStatus;
    private String matchAmount;
    private long createTime;
    private long updateTime;
    private String productName;
    private String moblieNo;
    private String userName;
    private String beginDate;
    private String endDate;
    private double yield;
    private double extraYield;
    private int period;
    private String cardName;
    private String cardNoLast;
    private String awardType1;
    private String awardRate1;
    private String amount1;
    private double totalProfit;
    private double preReturnFee;//提前退出费用
    private double awardRate;//奖励利率
    private double returnFee;

    private String activityAlias;

    private double entryFee;//加入费用
    private double managementFee;//管理费用

    private String agreement;//
    private String productStatus;//产品是否售罄 3 售罄

    private int isConInvest;//是否续投
    private int productStyle;//产品类型 花宝
    private int remainDate;//剩余天数

//
//    "orderId": "4898168863709514299050",
//            "orderType": 1,
//            "orderMoney": 500,
//            "agreementAmount": 500,
//            "productId": "9028176609203601866706",
//            "accountId": "3237714902887146294461",
//            "orderTime": 1454402762000,
//            "profitDay": 1454402762000,
//            "expiryDate": 1456994762000,
//            "totalIncome": 3.65,
//            "totalAmount": null,
//            "orderStatus": "20",
//            "cardNo": "6227003324126037902",
//            "paymentTime": 1454402766000,
//            "payinsId": null,
//            "awardType": null,
//            "awardRate": null,
//            "amount": null,
//            "comments": "支付成功",
//            "debtStatus": 3,
//            "matchAmount": null,
//            "createBy": null,
//            "createTime": 1454402762000,
//            "updateBy": null,
//            "updateTime": 1454402766000,
//            "productName": null,
//            "moblieNo": null,
//            "userName": null,
//            "beginDate": null,
//            "endDate": null,
//            "yield": 0,
//            "extraYield": 0,
//            "period": 0,
//            "cardName": null,
//            "cardNoLast": "7902",
//            "awardType1": null,
//            "awardRate1": null,
//            "amount1": null,
//            "totalProfit": null

    public OrderDetailModel() {
        super();
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public int getAgreementAmount() {
        return agreementAmount;
    }

    public void setAgreementAmount(int agreementAmount) {
        this.agreementAmount = agreementAmount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public long getProfitDay() {
        return profitDay;
    }

    public void setProfitDay(long profitDay) {
        this.profitDay = profitDay;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public long getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(long paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPayinsId() {
        return payinsId;
    }

    public void setPayinsId(String payinsId) {
        this.payinsId = payinsId;
    }

    public String getAwardType() {
        return awardType;
    }

    public void setAwardType(String awardType) {
        this.awardType = awardType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAwardRate() {
        return awardRate;
    }

    public void setAwardRate(double awardRate) {
        this.awardRate = awardRate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(int debtStatus) {
        this.debtStatus = debtStatus;
    }

    public String getMatchAmount() {
        return matchAmount;
    }

    public void setMatchAmount(String matchAmount) {
        this.matchAmount = matchAmount;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMoblieNo() {
        return moblieNo;
    }

    public void setMoblieNo(String moblieNo) {
        this.moblieNo = moblieNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public double getExtraYield() {
        return extraYield;
    }

    public void setExtraYield(double extraYield) {
        this.extraYield = extraYield;
    }

    public double getReturnFee() {
        return returnFee;
    }

    public void setReturnFee(double returnFee) {
        this.returnFee = returnFee;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNoLast() {
        return cardNoLast;
    }

    public void setCardNoLast(String cardNoLast) {
        this.cardNoLast = cardNoLast;
    }

    public String getAwardType1() {
        return awardType1;
    }

    public void setAwardType1(String awardType1) {
        this.awardType1 = awardType1;
    }

    public String getAwardRate1() {
        return awardRate1;
    }

    public void setAwardRate1(String awardRate1) {
        this.awardRate1 = awardRate1;
    }

    public String getAmount1() {
        return amount1;
    }

    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getPreReturnFee() {
        return preReturnFee;
    }

    public void setPreReturnFee(double preReturnFee) {
        this.preReturnFee = preReturnFee;
    }

    public String getActivityAlias() {
        return activityAlias;
    }

    public void setActivityAlias(String activityAlias) {
        this.activityAlias = activityAlias;
    }

    public double getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(double managementFee) {
        this.managementFee = managementFee;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(double entryFee) {
        this.entryFee = entryFee;
    }

    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public int getIsConInvest() {
        return isConInvest;
    }

    public void setIsConInvest(int isConInvest) {
        this.isConInvest = isConInvest;
    }

    public int getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(int productStyle) {
        this.productStyle = productStyle;
    }

    public int getRemainDate() {
        return remainDate;
    }

    public void setRemainDate(int remainDate) {
        this.remainDate = remainDate;
    }

    @Override
    public String toString() {
        return "OrderDetailModel{" +
                "orderId='" + orderId + '\'' +
                ", orderType=" + orderType +
                ", productNum=" + productNum +
                ", agreementAmount=" + agreementAmount +
                ", productId='" + productId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", orderMoney=" + orderMoney +
                ", orderTime=" + orderTime +
                ", profitDay=" + profitDay +
                ", expiryDate=" + expiryDate +
                ", totalIncome=" + totalIncome +
                ", totalAmount='" + totalAmount + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", paymentTime=" + paymentTime +
                ", payinsId='" + payinsId + '\'' +
                ", awardType='" + awardType + '\'' +
                ", amount=" + amount +
                ", comments='" + comments + '\'' +
                ", debtStatus=" + debtStatus +
                ", matchAmount='" + matchAmount + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", productName='" + productName + '\'' +
                ", moblieNo='" + moblieNo + '\'' +
                ", userName='" + userName + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", yield=" + yield +
                ", extraYield=" + extraYield +
                ", period=" + period +
                ", cardName='" + cardName + '\'' +
                ", cardNoLast='" + cardNoLast + '\'' +
                ", awardType1='" + awardType1 + '\'' +
                ", awardRate1='" + awardRate1 + '\'' +
                ", amount1='" + amount1 + '\'' +
                ", totalProfit=" + totalProfit +
                ", preReturnFee=" + preReturnFee +
                ", awardRate=" + awardRate +
                ", returnFee=" + returnFee +
                ", activityAlias='" + activityAlias + '\'' +
                ", entryFee=" + entryFee +
                ", managementFee=" + managementFee +
                ", agreement='" + agreement + '\'' +
                ", productStatus='" + productStatus + '\'' +
                ", isConInvest=" + isConInvest +
                ", productStyle=" + productStyle +
                ", remainDate=" + remainDate +
                '}';
    }
}
