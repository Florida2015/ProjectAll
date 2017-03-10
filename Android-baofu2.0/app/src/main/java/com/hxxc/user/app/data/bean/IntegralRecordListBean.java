package com.hxxc.user.app.data.bean;

import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;
import com.hxxc.user.app.widget.trecyclerview.C;

import rx.Observable;

/**
 * Created by houwen.lai on 2016/11/10.
 * 会员中心 积分
 */

public class IntegralRecordListBean extends BaseEntity.ListBean {

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getQueryMemberRecordList(Api.uid,param.get("type"),page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private int taskId;
    private long createTime;
    private int point;
    private String createTimeStr;
    private int pointId;
    private String remarks;
    private int memberId;
    private int type;
    private String taskName;
    private String exchangeId;

    public IntegralRecordListBean() {
        super();
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    @Override
    public String toString() {
        return "TradeRecordListBean{" +
                "taskId=" + taskId +
                ", createTime=" + createTime +
                ", point=" + point +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", pointId=" + pointId +
                ", remarks='" + remarks + '\'' +
                ", memberId=" + memberId +
                ", type=" + type +
                ", taskName='" + taskName + '\'' +
                ", exchangeId='" + exchangeId + '\'' +
                '}';
    }
}
