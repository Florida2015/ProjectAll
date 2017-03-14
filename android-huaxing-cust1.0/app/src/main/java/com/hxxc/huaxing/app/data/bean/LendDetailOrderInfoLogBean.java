package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/12.
 * 出借详情
 */

public class LendDetailOrderInfoLogBean implements Serializable {

    private int id;
    private String orderNo;
    private String titles;
    private String bizType;
    private String bitValue;
    private String createTime;
    private String contents;

    public LendDetailOrderInfoLogBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBitValue() {
        return bitValue;
    }

    public void setBitValue(String bitValue) {
        this.bitValue = bitValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "LendDetailOrderInfoLogBean{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", titles='" + titles + '\'' +
                ", bizType=" + bizType +
                ", bitValue='" + bitValue + '\'' +
                ", createTime=" + createTime +
                ", contents='" + contents + '\'' +
                '}';
    }
}
