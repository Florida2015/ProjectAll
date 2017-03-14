package com.hxxc.huaxing.app.data.bean;

/**
 * Created by chenqun on 2016/10/13.
 */

public class InvestHistoryBean {

    /**
     * mobile :
     * createTime : 1474525572000
     * money : 40000.0000
     */

    private String mobile;
    private long createTime;
    private String money;

    public InvestHistoryBean() {
    }

    public InvestHistoryBean(String mobile, long createTime, String money) {
        this.mobile = mobile;
        this.createTime = createTime;
        this.money = money;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
