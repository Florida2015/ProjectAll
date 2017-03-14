package com.hxxc.huaxing.app.data.bean;

import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseEntity;
import com.hxxc.huaxing.app.wedgit.trecyclerview.C;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/18.
 * 通知列表
 */

public class NotifyBean extends BaseEntity.ListBean {

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getSelectUserMsgList(Api.uid,page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private int id;
    private String title;
    private long createTime;
    private int deleteFlag;
    private int readedFlag;
    private int uid;
    private int msgStatus;
    private String backUserId;
    private String bid;
    private String messageType;
    private String showChannel;
    private String content;
    private String createTimeStr;

    public NotifyBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getReadedFlag() {
        return readedFlag;
    }

    public void setReadedFlag(int readedFlag) {
        this.readedFlag = readedFlag;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(int msgStatus) {
        this.msgStatus = msgStatus;
    }

    public String getBackUserId() {
        return backUserId;
    }

    public void setBackUserId(String backUserId) {
        this.backUserId = backUserId;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getShowChannel() {
        return showChannel;
    }

    public void setShowChannel(String showChannel) {
        this.showChannel = showChannel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    @Override
    public String toString() {
        return "NotifyBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", deleteFlag=" + deleteFlag +
                ", readedFlag=" + readedFlag +
                ", uid=" + uid +
                ", msgStatus=" + msgStatus +
                ", backUserId='" + backUserId + '\'' +
                ", bid='" + bid + '\'' +
                ", messageType='" + messageType + '\'' +
                ", showChannel='" + showChannel + '\'' +
                ", content='" + content + '\'' +
                ", createTimeStr='" + createTimeStr + '\'' +
                '}';
    }
}
