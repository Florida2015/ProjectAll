package com.hxxc.huaxing.app.data.bean;

import java.math.BigDecimal;

/**
 * Created by chenqun on 2016/10/8.
 */

public class ProductBean {


    /**
     * id : 54
     * productName : 奥迪
     * yearRate : 0.08
     * period : 0
     * periodMonth : 12
     * surplus : 220608
     * amount : 50000
     * status : 5
     * proportion : -3.41216
     * minAmount : 1000
     * maxAmount : 50000
     * activityLabel : [{"label":"送现金"},{"label":"送红包"}]
     * statusText : 还款中
     */

    private int id;
    private String productName;
    private BigDecimal yearRate;
    private int period;
    private int periodMonth;
    private String periodMonthStr;
    private long surplus;
    private BigDecimal amount;
    private int status;
    private float proportion;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String statusText;
//    private List<ActivityLabelBean> activityLabel;
    private String activityLabel;//

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getYearRate() {
        return yearRate;
    }

    public void setYearRate(BigDecimal yearRate) {
        this.yearRate = yearRate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPeriodMonth() {
        return periodMonth;
    }

    public void setPeriodMonth(int periodMonth) {
        this.periodMonth = periodMonth;
    }

    public long getSurplus() {
        return surplus;
    }

    public void setSurplus(long surplus) {
        this.surplus = surplus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

//    public List<ActivityLabelBean> getActivityLabel() {
//        return activityLabel;
//    }
//
//    public void setActivityLabel(List<ActivityLabelBean> activityLabel) {
//        this.activityLabel = activityLabel;
//    }
    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public String getPeriodMonthStr() {
        return periodMonthStr;
    }

    public void setPeriodMonthStr(String periodMonthStr) {
        this.periodMonthStr = periodMonthStr;
    }

    public static class ActivityLabelBean {
        /**
         * label : 送现金
         */

        private String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
