package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 * 出借详情 出借详情
 *
 */

public class LendDetailItemBean implements Serializable {

    private String productName;
    private String titles;
    private int pid;
    private int status;
    private List<LendDetailOrderInfoLogBean> orderInfoLogs;
    private List<LendDetailPaymentHistoryBean> paymentHistory;
    private LendDetailLoanDetailsBean loanDetails;
    private int isDebt;
    private int isRevoke;
    private String statusName;
    private int debtStatus;
    private String orderStatusText;
    private String debtStatusText;

    private long profitTime;//开始生息时间
    private String expireTime;//到期时间

    public LendDetailItemBean() {
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

    public List<LendDetailOrderInfoLogBean> getOrderInfoLogs() {
        return orderInfoLogs;
    }

    public void setOrderInfoLogs(List<LendDetailOrderInfoLogBean> orderInfoLogs) {
        this.orderInfoLogs = orderInfoLogs;
    }

    public List<LendDetailPaymentHistoryBean> getPaymentHistory() {
        return paymentHistory;
    }

    public void setPaymentHistory(List<LendDetailPaymentHistoryBean> paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    public LendDetailLoanDetailsBean getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LendDetailLoanDetailsBean loanDetails) {
        this.loanDetails = loanDetails;
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

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "LendDetailItemBean{" +
                "productName='" + productName + '\'' +
                ", titles='" + titles + '\'' +
                ", pid=" + pid +
                ", status=" + status +
                ", orderInfoLogs=" + orderInfoLogs +
                ", paymentHistory=" + paymentHistory +
                ", loanDetails=" + loanDetails +
                ", isDebt=" + isDebt +
                ", statusName='" + statusName + '\'' +
                ", debtStatus=" + debtStatus +
                ", orderStatusText='" + orderStatusText + '\'' +
                ", debtStatusText='" + debtStatusText + '\'' +
                ", profitTime=" + profitTime +
                ", expireTime=" + expireTime +
                '}';
    }
}
