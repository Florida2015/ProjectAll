package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * app升级
 * Created by houwen.lai on 2016/2/24.
 */
public class UpdateModel implements Serializable{

    private int versionCode;
    private String versionId;
    private int isForceUpdate;//0 强制升级 1不用
    private String versionName;
    private String contents;//升级描述
    private long createDate;
    private String url;//下载链接
    private int type;


    public UpdateModel() {
        super();
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public int getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(int isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UpdateModel{" +
                "versionCode=" + versionCode +
                ", versionId='" + versionId + '\'' +
                ", isForceUpdate=" + isForceUpdate +
                ", versionName='" + versionName + '\'' +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                ", url='" + url + '\'' +
                ", type=" + type +
                '}';
    }
}
