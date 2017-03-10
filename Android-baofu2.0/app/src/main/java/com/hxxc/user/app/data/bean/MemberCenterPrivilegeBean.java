package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/11/4.
 * 我的 会员中心 中间 button
 */

public class MemberCenterPrivilegeBean implements Serializable {

//    "privilegeId": 1,
//            "privilegeCode": null,
//            "privilegeName": "专属顾问",
//            "pcIcon": "",
//            "mobileIcon": "",
//            "privilegeType": null,
//            "showIndex": null,
//            "jumpMobileUrl": null,
//            "meetPrivilege": true

    private int privilegeId;
    private String privilegeCode;
    private String privilegeName;
    private String pcIcon;
    private String mobileIcon;
    private String privilegeType;
    private String showIndex;
    private String jumpMobileUrl;
    private boolean  meetPrivilege;

    public MemberCenterPrivilegeBean() {
        super();
    }

    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeCode() {
        return privilegeCode;
    }

    public void setPrivilegeCode(String privilegeCode) {
        this.privilegeCode = privilegeCode;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPcIcon() {
        return pcIcon;
    }

    public void setPcIcon(String pcIcon) {
        this.pcIcon = pcIcon;
    }

    public String getMobileIcon() {
        return mobileIcon;
    }

    public void setMobileIcon(String mobileIcon) {
        this.mobileIcon = mobileIcon;
    }

    public String getPrivilegeType() {
        return privilegeType;
    }

    public void setPrivilegeType(String privilegeType) {
        this.privilegeType = privilegeType;
    }

    public String getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(String showIndex) {
        this.showIndex = showIndex;
    }

    public String getJumpMobileUrl() {
        return jumpMobileUrl;
    }

    public void setJumpMobileUrl(String jumpMobileUrl) {
        this.jumpMobileUrl = jumpMobileUrl;
    }

    public boolean isMeetPrivilege() {
        return meetPrivilege;
    }

    public void setMeetPrivilege(boolean meetPrivilege) {
        this.meetPrivilege = meetPrivilege;
    }

    @Override
    public String toString() {
        return "MemberCenterPrivilegeBean{" +
                "privilegeId=" + privilegeId +
                ", privilegeCode='" + privilegeCode + '\'' +
                ", privilegeName='" + privilegeName + '\'' +
                ", pcIcon='" + pcIcon + '\'' +
                ", mobileIcon='" + mobileIcon + '\'' +
                ", privilegeType='" + privilegeType + '\'' +
                ", showIndex='" + showIndex + '\'' +
                ", jumpMobileUrl='" + jumpMobileUrl + '\'' +
                ", meetPrivilege=" + meetPrivilege +
                '}';
    }
}
