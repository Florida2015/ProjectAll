package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/11/3.
 * 会员中心 item
 */

public class MemberCenterBean implements Serializable{
//    "taskId": 4,
//            "taskCode": "0004",
//            "taskName": "实名认证",
//            "taskType": 0,
//            "activityId": null,
//            "pointsType": 1,
//            "pointsTypeStr": null,
//            "rewardsPoints": 100,
//            "conTaskNumber": null,
//            "excessRewardsPoints": null,
//            "showIndex": null,
//            "remarks": null,
//            "isDealOk": 0,
//            "buttonTxt": "立即设置",
//            "jumpMobileUrl": null,
//            "jumpFlag": false

    private int taskId;
    private String taskCode;
    private String taskName;
    private int taskType;
    private String activityId;
    private int pointsType;
    private String pointsTypeStr;
    private int rewardsPoints;
    private String conTaskNumber;
    private String excessRewardsPoints;
    private String showIndex;
    private String remarks;
    private int isDealOk;
    private String buttonTxt;
    private String jumpMobileUrl;
    private boolean jumpFlag;

    public MemberCenterBean() {
        super();
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getPointsType() {
        return pointsType;
    }

    public void setPointsType(int pointsType) {
        this.pointsType = pointsType;
    }

    public String getPointsTypeStr() {
        return pointsTypeStr;
    }

    public void setPointsTypeStr(String pointsTypeStr) {
        this.pointsTypeStr = pointsTypeStr;
    }

    public int getRewardsPoints() {
        return rewardsPoints;
    }

    public void setRewardsPoints(int rewardsPoints) {
        this.rewardsPoints = rewardsPoints;
    }

    public String getConTaskNumber() {
        return conTaskNumber;
    }

    public void setConTaskNumber(String conTaskNumber) {
        this.conTaskNumber = conTaskNumber;
    }

    public String getExcessRewardsPoints() {
        return excessRewardsPoints;
    }

    public void setExcessRewardsPoints(String excessRewardsPoints) {
        this.excessRewardsPoints = excessRewardsPoints;
    }

    public String getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(String showIndex) {
        this.showIndex = showIndex;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getIsDealOk() {
        return isDealOk;
    }

    public void setIsDealOk(int isDealOk) {
        this.isDealOk = isDealOk;
    }

    public String getButtonTxt() {
        return buttonTxt;
    }

    public void setButtonTxt(String buttonTxt) {
        this.buttonTxt = buttonTxt;
    }

    public String getJumpMobileUrl() {
        return jumpMobileUrl;
    }

    public void setJumpMobileUrl(String jumpMobileUrl) {
        this.jumpMobileUrl = jumpMobileUrl;
    }

    public boolean isJumpFlag() {
        return jumpFlag;
    }

    public void setJumpFlag(boolean jumpFlag) {
        this.jumpFlag = jumpFlag;
    }

    @Override
    public String toString() {
        return "MemberCenterBean{" +
                "taskId=" + taskId +
                ", taskCode='" + taskCode + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskType=" + taskType +
                ", activityId='" + activityId + '\'' +
                ", pointsType=" + pointsType +
                ", pointsTypeStr='" + pointsTypeStr + '\'' +
                ", rewardsPoints=" + rewardsPoints +
                ", conTaskNumber='" + conTaskNumber + '\'' +
                ", excessRewardsPoints='" + excessRewardsPoints + '\'' +
                ", showIndex='" + showIndex + '\'' +
                ", remarks='" + remarks + '\'' +
                ", isDealOk=" + isDealOk +
                ", buttonTxt='" + buttonTxt + '\'' +
                ", jumpMobileUrl='" + jumpMobileUrl + '\'' +
                ", jumpFlag=" + jumpFlag +
                '}';
    }
}
