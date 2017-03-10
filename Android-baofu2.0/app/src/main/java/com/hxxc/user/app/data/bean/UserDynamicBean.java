package com.hxxc.user.app.data.bean;

import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;
import com.hxxc.user.app.widget.trecyclerview.C;

import rx.Observable;

/**
 * Created by houwen.lai on 2016/11/18.
 *
 * 我的 获取动态消息列表
 */

public class UserDynamicBean extends BaseEntity.ListBean {

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getGetUserdynamicList(Api.uid,page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private double bizType;//bizType，目前 1:认证，应该跳转去认证的页面，2是红包，应该跳转产品列表页面让用户去购买产品，3是订单，如果是订单，会有个bizVlue，这值是订单编号，你们通过它跳转到订单详细页面
    private String bizTypeStr;
    private String bizValue;
    private String contents;
    private long createTime;
    private int deadline;
    private int id;
    private int uid;
    private String ignoreDate;
    private int isIgnore;
    private int isRead;
    private String mobileIconUrl;
    private String realMobileIconUrl;
    private String statusName;
    private String title;

    public UserDynamicBean() {
        super();
    }

    public double getBizType() {
        return bizType;
    }

    public void setBizType(double bizType) {
        this.bizType = bizType;
    }

    public String getBizTypeStr() {
        return bizTypeStr;
    }

    public void setBizTypeStr(String bizTypeStr) {
        this.bizTypeStr = bizTypeStr;
    }

    public String getBizValue() {
        return bizValue;
    }

    public void setBizValue(String bizValue) {
        this.bizValue = bizValue;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getIgnoreDate() {
        return ignoreDate;
    }

    public void setIgnoreDate(String ignoreDate) {
        this.ignoreDate = ignoreDate;
    }

    public int getIsIgnore() {
        return isIgnore;
    }

    public void setIsIgnore(int isIgnore) {
        this.isIgnore = isIgnore;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getMobileIconUrl() {
        return mobileIconUrl;
    }

    public void setMobileIconUrl(String mobileIconUrl) {
        this.mobileIconUrl = mobileIconUrl;
    }

    public String getRealMobileIconUrl() {
        return realMobileIconUrl;
    }

    public void setRealMobileIconUrl(String realMobileIconUrl) {
        this.realMobileIconUrl = realMobileIconUrl;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "UserDynamicBean{" +
                "bizType=" + bizType +
                ", bizValue=" + bizValue +
                ", contents='" + contents + '\'' +
                ", createTime=" + createTime +
                ", deadline=" + deadline +
                ", id=" + id +
                ", uid=" + uid +
                ", ignoreDate='" + ignoreDate + '\'' +
                ", isIgnore=" + isIgnore +
                ", isRead=" + isRead +
                ", mobileIconUrl='" + mobileIconUrl + '\'' +
                ", realMobileIconUrl='" + realMobileIconUrl + '\'' +
                ", statusName='" + statusName + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
