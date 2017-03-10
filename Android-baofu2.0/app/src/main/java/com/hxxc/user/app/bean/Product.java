package com.hxxc.user.app.bean;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1810627452045160974L;

    /**
     * id : 10
     * pid : 3
     * productName : 新手专享
     * yearRate : 0.05
     * period : 25
     * periodDay : 750
     * surplus : 300000
     * amount : 1000000
     * proportion : 0.17
     * minAmount : 100000
     * maxAmount : 1000000
     * activityLabel : [{"activityName":"新手购买送红包","activityId":1}]
     * status : 1
     * productBidName : 新手专享2016102704期
     * currPeriod : 2016102704
     * statusText : 抢购
     * yearRateText : 5.0%
     */

    private int id;
    private int pid;
    private String productName;
    private double yearRate;
    private int period;
    private int periodDay;
    private long surplus;
    private long amount;
    private double proportion;
    private long minAmount;
    private long maxAmount;
    private int status;
    private String productBidName;
    private String currPeriod;
    private String statusText;
    private String yearRateText;
    private List<ActivityLabelBean> activityLabel;
    private String stampContents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getYearRate() {
        return yearRate;
    }

    public void setYearRate(double yearRate) {
        this.yearRate = yearRate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPeriodDay() {
        return periodDay;
    }

    public void setPeriodDay(int periodDay) {
        this.periodDay = periodDay;
    }

    public long getSurplus() {
        return surplus;
    }

    public void setSurplus(long surplus) {
        this.surplus = surplus;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(long minAmount) {
        this.minAmount = minAmount;
    }

    public long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getYearRateText() {
        return yearRateText;
    }

    public void setYearRateText(String yearRateText) {
        this.yearRateText = yearRateText;
    }

    public List<ActivityLabelBean> getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(List<ActivityLabelBean> activityLabel) {
        this.activityLabel = activityLabel;
    }

    public String getStampContents() {
        return stampContents;
    }

    public void setStampContents(String stampContents) {
        this.stampContents = stampContents;
    }

    public static class ActivityLabelBean2 implements Serializable{
        private String activitylabel;

        public String getActivitylabel() {
            return activitylabel;
        }

        public void setActivitylabel(String activitylabel) {
            this.activitylabel = activitylabel;
        }
    }

    public static class ActivityLabelBean implements Serializable{
        /**
         * activityName : 新手购买送红包
         * activityId : 1
         */

        private String activityName;
        private int activityId;

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }
    }
}