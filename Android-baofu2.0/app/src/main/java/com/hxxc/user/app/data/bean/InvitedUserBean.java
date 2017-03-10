package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/11/21.
 *
 *
 */

public class InvitedUserBean implements Serializable {

    private String uid;
    private String userNo;
    private String userName;
    private String mobile;
    private String nationality;
    private String marriageStatus;
    private String occupation;
    private String address;
    private String documentType;
    private String identityCard;

    private String fid;
    private String maskUserName;
    private String mobileMask;

    public InvitedUserBean() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getMaskUserName() {
        return maskUserName;
    }

    public void setMaskUserName(String maskUserName) {
        this.maskUserName = maskUserName;
    }

    public String getMobileMask() {
        return mobileMask;
    }

    public void setMobileMask(String mobileMask) {
        this.mobileMask = mobileMask;
    }
}
