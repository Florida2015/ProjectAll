package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * app升级
 * Created by houwen.lai on 2016/2/24.
 */
public class UpdateBean implements Serializable{

    private String versionCode;
    private String versionsId;
    private int isForceUpdate;//0 不用  1强制升级
    private String versionName;
    private String contents;//升级描述
    private long createDate;
    private String url;//下载链接
    private int type;
    private String isUpdate; //0  1


    public UpdateBean() {
        super();
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionsId() {
        return versionsId;
    }

    public void setVersionsId(String versionsId) {
        this.versionsId = versionsId;
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

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "versionCode='" + versionCode + '\'' +
                ", versionsId='" + versionsId + '\'' +
                ", isForceUpdate=" + isForceUpdate +
                ", versionName='" + versionName + '\'' +
                ", contents='" + contents + '\'' +
                ", createDate=" + createDate +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", isUpdate=" + isUpdate +
                '}';
    }
}
