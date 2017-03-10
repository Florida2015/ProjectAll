package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/11/4.
 * 会员信息
 */

public class MemberCenterDetailBean implements Serializable {
//    "memberId": null,
//            "pointValue": 200468,
//            "experienceValue": 200468,
//            "level": 1,
//            "identityId": null,
//            "identityName": "普通会员",
//            "memberName": "张三",
//            "token": null,
//            "nextMinExperienceValue": null,
//            "leftExperienceValue": null,
//            "nextLevel": null,
//            "nextIdentityName": null,
//            "phoneOrMemberName": null

    private String memberId;//
    private int pointValue;//积分
    private int experienceValue;//还差多少积分值
    private int level;//既有等级
    private String identityId;//
    private String identityName;//普通会员
    private String memberName;//张三
    private int nextMinExperienceValue;//100/2000 中的2000
    private int leftExperienceValue;//100/2000 中100
    private String nextLevel;//下一个等级
    private String nextIdentityName;//下一个会员
    private String phoneOrMemberName;//姓名取值
    private String privilegeCount;
    private String realIcon;

    public MemberCenterDetailBean() {
        super();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public int getExperienceValue() {
        return experienceValue;
    }

    public void setExperienceValue(int experienceValue) {
        this.experienceValue = experienceValue;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getNextMinExperienceValue() {
        return nextMinExperienceValue;
    }

    public void setNextMinExperienceValue(int nextMinExperienceValue) {
        this.nextMinExperienceValue = nextMinExperienceValue;
    }

    public int getLeftExperienceValue() {
        return leftExperienceValue;
    }

    public void setLeftExperienceValue(int leftExperienceValue) {
        this.leftExperienceValue = leftExperienceValue;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getNextIdentityName() {
        return nextIdentityName;
    }

    public void setNextIdentityName(String nextIdentityName) {
        this.nextIdentityName = nextIdentityName;
    }

    public String getPhoneOrMemberName() {
        return phoneOrMemberName;
    }

    public void setPhoneOrMemberName(String phoneOrMemberName) {
        this.phoneOrMemberName = phoneOrMemberName;
    }

    public String getPrivilegeCount() {
        return privilegeCount;
    }

    public void setPrivilegeCount(String privilegeCount) {
        this.privilegeCount = privilegeCount;
    }

    public String getRealIcon() {
        return realIcon;
    }

    public void setRealIcon(String realIcon) {
        this.realIcon = realIcon;
    }

    @Override
    public String toString() {
        return "MemberCenterDetailBean{" +
                "memberId='" + memberId + '\'' +
                ", pointValue=" + pointValue +
                ", experienceValue=" + experienceValue +
                ", level=" + level +
                ", identityId='" + identityId + '\'' +
                ", identityName='" + identityName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", nextMinExperienceValue='" + nextMinExperienceValue + '\'' +
                ", leftExperienceValue='" + leftExperienceValue + '\'' +
                ", nextLevel='" + nextLevel + '\'' +
                ", nextIdentityName='" + nextIdentityName + '\'' +
                ", phoneOrMemberName='" + phoneOrMemberName + '\'' +
                '}';
    }
}
