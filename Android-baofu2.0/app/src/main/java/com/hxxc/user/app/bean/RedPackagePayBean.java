package com.hxxc.user.app.bean;

/**
 * Created by chenqun on 2016/12/2.
 */

public class RedPackagePayBean {

    /**
     * id : 340
     * money : 20
     * startTime : 1480642111000
     * endTime : 1481506111000
     * status : 1
     * useMoney : 10000
     * sources : 用户实名
     * isUseRule : 0
     * useContents :
     * startTimeText : 2016-12-02 09:28:31
     * endTimeText : 2016-12-12 09:28:31
     */

    private int id;
    private int money;
    private long startTime;
    private long endTime;
    private int status;
    private int useMoney;
    private String sources;
    private int isUseRule;
    private String useContents;
    private String startTimeText;
    private String endTimeText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(int useMoney) {
        this.useMoney = useMoney;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public int getIsUseRule() {
        return isUseRule;
    }

    public void setIsUseRule(int isUseRule) {
        this.isUseRule = isUseRule;
    }

    public String getUseContents() {
        return useContents;
    }

    public void setUseContents(String useContents) {
        this.useContents = useContents;
    }

    public String getStartTimeText() {
        return startTimeText;
    }

    public void setStartTimeText(String startTimeText) {
        this.startTimeText = startTimeText;
    }

    public String getEndTimeText() {
        return endTimeText;
    }

    public void setEndTimeText(String endTimeText) {
        this.endTimeText = endTimeText;
    }
}
