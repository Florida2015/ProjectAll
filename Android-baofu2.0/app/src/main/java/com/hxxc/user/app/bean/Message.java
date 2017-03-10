package com.hxxc.user.app.bean;

import java.io.Serializable;

/**
 * Created by chenqun on 2016/7/6.
 */
public class Message implements Serializable{

    /**
     * bid : 0
     * content : 亲,恭喜您获得了1次抽奖机会!了解详情请登录<a href='https://lc.huaxiafinance.com/level/activity.do?page=duanwu'>https://lc.huaxiafinance.com/level/activity.do?page=duanwu</a>
     * createTime : 1467255972000
     * id : 3131
     * msgStatus : 1
     * readedFlag : 0
     * title : 浓情端午 惊喜不断
     */

    private String bid;
    private String content;
    private Long createTime;
    private int id;
    private int msgStatus;
    private int readedFlag;
    private String title;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public int getReadedFlag() {
        return readedFlag;
    }

    public void setReadedFlag(int readedFlag) {
        this.readedFlag = readedFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
