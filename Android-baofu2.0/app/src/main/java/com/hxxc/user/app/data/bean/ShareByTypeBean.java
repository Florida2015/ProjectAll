package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/12/28.
 * 获取分享列表
 *
 */

public class ShareByTypeBean implements Serializable {

    private int id;
    private String type;
    private String shareIcon;
    private String shareTitle;
    private String shareContents;
    private String ordinaryUrl;
    private String activityUrl;//分享链接
    private String activityId;//分享链接
    private String status;
    private String remark;
    private long createTime;
    private String createter;
    private String realShareIcon;//缩略图

    private SysAttachmentFileVoBean sysAttachmentFileVo;

    public ShareByTypeBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContents() {
        return shareContents;
    }

    public void setShareContents(String shareContents) {
        this.shareContents = shareContents;
    }

    public String getOrdinaryUrl() {
        return ordinaryUrl;
    }

    public void setOrdinaryUrl(String ordinaryUrl) {
        this.ordinaryUrl = ordinaryUrl;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateter() {
        return createter;
    }

    public void setCreateter(String createter) {
        this.createter = createter;
    }

    public String getRealShareIcon() {
        return realShareIcon;
    }

    public void setRealShareIcon(String realShareIcon) {
        this.realShareIcon = realShareIcon;
    }

    public SysAttachmentFileVoBean getSysAttachmentFileVo() {
        return sysAttachmentFileVo;
    }

    public void setSysAttachmentFileVo(SysAttachmentFileVoBean sysAttachmentFileVo) {
        this.sysAttachmentFileVo = sysAttachmentFileVo;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }
}
