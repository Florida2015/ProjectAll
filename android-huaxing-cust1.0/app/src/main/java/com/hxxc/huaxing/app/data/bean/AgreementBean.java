package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/27.
 */

public class AgreementBean implements Serializable {

    private int id;
    private String agreementName;
    private int agreementType;
    private int createUid;
    private long createTime;
    private String describes;
    private String pcViewUrl;
    private String mobileViewUrl;
    private String downloadUrl;

    public AgreementBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public int getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(int agreementType) {
        this.agreementType = agreementType;
    }

    public int getCreateUid() {
        return createUid;
    }

    public void setCreateUid(int createUid) {
        this.createUid = createUid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getPcViewUrl() {
        return pcViewUrl;
    }

    public void setPcViewUrl(String pcViewUrl) {
        this.pcViewUrl = pcViewUrl;
    }

    public String getMobileViewUrl() {
        return mobileViewUrl;
    }

    public void setMobileViewUrl(String mobileViewUrl) {
        this.mobileViewUrl = mobileViewUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
