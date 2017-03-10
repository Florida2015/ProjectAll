package com.hxxc.user.app.data.bean;

import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;
import com.hxxc.user.app.widget.trecyclerview.C;

import java.io.Serializable;
import java.math.BigDecimal;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/27.
 * 我的 红包列表 红包 models
 */

public class RedPackageItemBean extends BaseEntity.ListBean implements Serializable{

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getRedPackageList(Api.uid,param.get("status"),page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    public String activityType;
    public String activityTypeStr;
    public Long createTime;
    public String createTimeStr;
    public String description;
    public long endTime;
    public String endTimeStr;
    public int id;
    public int invitedRelationId;
    public BigDecimal money;
    public int pAId;
    public long startTime;
    public String startTimeStr;
    public int status;
    public String type;
    public int uid;
    public BigDecimal useMoney;
    public String bizType;//
    public String useContents;

    public String sources;

    public RedPackageItemBean() {
        super();
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityTypeStr() {
        return activityTypeStr;
    }

    public void setActivityTypeStr(String activityTypeStr) {
        this.activityTypeStr = activityTypeStr;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvitedRelationId() {
        return invitedRelationId;
    }

    public void setInvitedRelationId(int invitedRelationId) {
        this.invitedRelationId = invitedRelationId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getpAId() {
        return pAId;
    }

    public void setpAId(int pAId) {
        this.pAId = pAId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public BigDecimal getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(BigDecimal useMoney) {
        this.useMoney = useMoney;
    }

    public String getUseContents() {
        return useContents;
    }

    public void setUseContents(String useContents) {
        this.useContents = useContents;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }
}
