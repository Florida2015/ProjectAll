package com.hxxc.user.app.bean;

/**
 * Created by chenqun on 2016/11/4.
 */

public class InvestHistoryBean {

    /**
     * mobile : 138****0017
     * createTime :
     * money : 10000
     */

    private String mobile;
    private long createTime;
    private String createTimeText;
    private int money;

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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getCreateTimeText() {
        return createTimeText;
    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }
}
