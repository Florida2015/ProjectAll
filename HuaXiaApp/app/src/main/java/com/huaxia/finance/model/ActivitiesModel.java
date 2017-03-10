package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * 活动 item
 * Created by houwen.lai on 2016/2/17.
 */
public class ActivitiesModel implements Serializable{

    private String activityId;
    private String activityName;
    private int activityType;
    private String activityDetail;
    private long beginTime;
    private long endTime;
    private double awardRate;
    private int awardType;

    private String comments;
    private String homeUrl;
    private int showIndex;
    private String detailUrl;
    private int isReturnButton;
    private String buttonName;
    private String buttonUrl;
    private int isLogin;

    private String iconUrl;
    private String activityAlias;


//    "activityId": "2436966854220400318551",
//            "activityName": "狂送50%",
//            "activityType": 3,
//            "activityDetail": "",
//            "beginTime": 1446307200000,
//            "endTime": 1480435200000,
//            "awardRate": null,
//            "amount": null,
//            "awardType": 2,
//            "comments": "",
//            "homeUrl": "4444",
//            "showIndex": null,
//            "detailUrl": null,
//            "isReturnButton": null,
//            "buttonName": null,
//            "buttonUrl": null,
//            "isLogin": null,
//            "iconUrl": null,
//            "activityAlias": "+50%"


    public ActivitiesModel() {
        super();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public double getAwardRate() {
        return awardRate;
    }

    public void setAwardRate(double awardRate) {
        this.awardRate = awardRate;
    }

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getActivityAlias() {
        return activityAlias;
    }

    public void setActivityAlias(String activityAlias) {
        this.activityAlias = activityAlias;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public int getIsReturnButton() {
        return isReturnButton;
    }

    public void setIsReturnButton(int isReturnButton) {
        this.isReturnButton = isReturnButton;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonUrl() {
        return buttonUrl;
    }

    public void setButtonUrl(String buttonUrl) {
        this.buttonUrl = buttonUrl;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    @Override
    public String toString() {
        return "ActivitiesModel{" +
                "activityId='" + activityId + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityType=" + activityType +
                ", activityDetail='" + activityDetail + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", awardRate=" + awardRate +
                ", awardType=" + awardType +
                ", comments='" + comments + '\'' +
                ", homeUrl='" + homeUrl + '\'' +
                ", showIndex=" + showIndex +
                ", detailUrl='" + detailUrl + '\'' +
                ", isReturnButton=" + isReturnButton +
                ", buttonName='" + buttonName + '\'' +
                ", buttonUrl='" + buttonUrl + '\'' +
                ", isLogin=" + isLogin +
                ", iconUrl='" + iconUrl + '\'' +
                ", activityAlias='" + activityAlias + '\'' +
                '}';
    }
}
