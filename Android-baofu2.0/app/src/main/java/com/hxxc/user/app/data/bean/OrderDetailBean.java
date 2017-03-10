package com.hxxc.user.app.data.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by houwen.lai on 2016/10/26.
 *
 */

public class OrderDetailBean implements Serializable {

    private String productName;
    private String titles;
    private int pid;
    private int status;

    private List<OrderDetailStatusBean>  orderStatusMessages;

    private int isDebt;
    private String statusName;
    private int debtStatus;
    private String orderStatusText;
    private String debtStatusText;

    private long profitTime;//开始生息时间
    private long expireTime;//到期时间

    private int continuedInvestmentStatus;//是否续投 0
    private long signTime;
    private int orderStatus;
    private String activityLabel;

    private OrderDetailLoanDetailsBean orderDefaultInfo;

    private String productBidName;
    private String currPeriod;

    private OrderConInvestBean orderConInvestApp;

    private String contractUrl;//合同

    public OrderDetailBean() {
        super();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDebt() {
        return isDebt;
    }

    public void setIsDebt(int isDebt) {
        this.isDebt = isDebt;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(int debtStatus) {
        this.debtStatus = debtStatus;
    }

    public String getOrderStatusText() {
        return orderStatusText;
    }

    public void setOrderStatusText(String orderStatusText) {
        this.orderStatusText = orderStatusText;
    }

    public String getDebtStatusText() {
        return debtStatusText;
    }

    public void setDebtStatusText(String debtStatusText) {
        this.debtStatusText = debtStatusText;
    }

    public long getProfitTime() {
        return profitTime;
    }

    public void setProfitTime(long profitTime) {
        this.profitTime = profitTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public List<OrderDetailStatusBean> getOrderStatusMessages() {
        return orderStatusMessages;
    }

    public void setOrderStatusMessages(List<OrderDetailStatusBean> orderStatusMessages) {
        this.orderStatusMessages = orderStatusMessages;
    }

    public int getContinuedInvestmentStatus() {
        return continuedInvestmentStatus;
    }

    public void setContinuedInvestmentStatus(int continuedInvestmentStatus) {
        this.continuedInvestmentStatus = continuedInvestmentStatus;
    }

    public long getSignTime() {
        return signTime;
    }

    public void setSignTime(long signTime) {
        this.signTime = signTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderDetailLoanDetailsBean getOrderDefaultInfo() {
        return orderDefaultInfo;
    }

    public void setOrderDefaultInfo(OrderDetailLoanDetailsBean orderDefaultInfo) {
        this.orderDefaultInfo = orderDefaultInfo;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public String getProductBidName() {
        return productBidName;
    }

    public void setProductBidName(String productBidName) {
        this.productBidName = productBidName;
    }

    public String getCurrPeriod() {
        return currPeriod;
    }

    public void setCurrPeriod(String currPeriod) {
        this.currPeriod = currPeriod;
    }

    public OrderConInvestBean getOrderConInvestApp() {
        return orderConInvestApp;
    }

    public void setOrderConInvestApp(OrderConInvestBean orderConInvestApp) {
        this.orderConInvestApp = orderConInvestApp;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }
}
