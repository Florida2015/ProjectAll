package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/10/26.
 * 出借详情 订单状态
 */

public class OrderDetailStatusBean implements Serializable {

    private int id;
    private String orderNo;
    private String title;
    private int bizType;
    private String bitValue;
    private long createTime;
    private String contents;

    private String time;
    private String message;
    private int status;
    private String keyType;
    private String action;

    private String keyword;

    private int currStatus;

    public OrderDetailStatusBean() {
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
        return title;
    }

    public void setTitles(String titles) {
        this.title = titles;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public String getBitValue() {
        return bitValue;
    }

    public void setBitValue(String bitValue) {
        this.bitValue = bitValue;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(int currStatus) {
        this.currStatus = currStatus;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
