package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/12/1.
 */

public class InvestQueryBean implements Serializable {

    private int id;
    private int minPeriod;
    private int maxPeriod;
    private int status;
    private long createTime;
    private String remarks;
    private String showIndex;

    private boolean flag=false;

    public InvestQueryBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinPeriod() {
        return minPeriod;
    }

    public void setMinPeriod(int minPeriod) {
        this.minPeriod = minPeriod;
    }

    public int getMaxPeriod() {
        return maxPeriod;
    }

    public void setMaxPeriod(int maxPeriod) {
        this.maxPeriod = maxPeriod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(String showIndex) {
        this.showIndex = showIndex;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "InvestQueryBean{" +
                "id=" + id +
                ", minPeriod=" + minPeriod +
                ", maxPeriod=" + maxPeriod +
                ", status=" + status +
                ", createTime=" + createTime +
                ", remarks='" + remarks + '\'' +
                ", showIndex='" + showIndex + '\'' +
                '}';
    }
}
