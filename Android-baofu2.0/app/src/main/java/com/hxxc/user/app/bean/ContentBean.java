package com.hxxc.user.app.bean;

import java.io.Serializable;

/**
 * Created by chenqun on 2016/11/2.
 */

public class ContentBean implements Serializable{


    /**
     * id : 2
     * picUrl : 17
     * introduction : 22222
     * type : 1
     * author : 22222
     * createTime : 1478672247000
     * source : 新浪
     * clickCount : 22222
     * status : 1
     * goods : 7
     * noGoods : 6
     * title : 华夏信财APP2.0版本更新公告
     * wapContent : 22222
     * pcContent : 22222
     * mbContentUrl :
     * createTimeStr : 2016年11月09日 14:17
     * createTimeStr2 : 11-09 14:17
     * sysAttachmentFileVo : {"fileId":17,"locate":"/usericon/2016/11/9/9/","fileName":"1477019905727.jpg","fileExtName":"jpg","fileType":"usericon","saveName":"1478654496007.jpg","refObject":null,"operator":null,"operateDate":null,"segment":null,"remarks":null,"enabled":1,"bytes":null}
     * realPicUrl : http://192.168.11.48:8089/picture/usericon/2016/11/9/9/1478654496007.jpg
     */

    private int id;
    private String picUrl;
    private String introduction;
    private String type;
    private String author;
    private long createTime;
    private String source;
    private int clickCount;
    private int status;
    private int goods;
    private int noGoods;
    private String title;
    private String wapContent;
    private String pcContent;

    private String realMbContentUrl;
    private String mbContentUrl;
    private String createTimeStr;
    private String createTimeStr2;
    private SysAttachmentFileVoBean sysAttachmentFileVo;
    private String realPicUrl;
    private String realShareIcon;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getGoods() {
        return goods;
    }

    public void setGoods(int goods) {
        this.goods = goods;
    }

    public int getNoGoods() {
        return noGoods;
    }

    public void setNoGoods(int noGoods) {
        this.noGoods = noGoods;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWapContent() {
        return wapContent;
    }

    public void setWapContent(String wapContent) {
        this.wapContent = wapContent;
    }

    public String getPcContent() {
        return pcContent;
    }

    public void setPcContent(String pcContent) {
        this.pcContent = pcContent;
    }

    public String getMbContentUrl() {
        return mbContentUrl;
    }

    public void setMbContentUrl(String mbContentUrl) {
        this.mbContentUrl = mbContentUrl;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCreateTimeStr2() {
        return createTimeStr2;
    }

    public void setCreateTimeStr2(String createTimeStr2) {
        this.createTimeStr2 = createTimeStr2;
    }

    public SysAttachmentFileVoBean getSysAttachmentFileVo() {
        return sysAttachmentFileVo;
    }

    public void setSysAttachmentFileVo(SysAttachmentFileVoBean sysAttachmentFileVo) {
        this.sysAttachmentFileVo = sysAttachmentFileVo;
    }

    public String getRealPicUrl() {
        return realPicUrl;
    }

    public void setRealPicUrl(String realPicUrl) {
        this.realPicUrl = realPicUrl;
    }

    public String getRealShareIcon() {
        return realShareIcon;
    }

    public void setRealShareIcon(String realShareIcon) {
        this.realShareIcon = realShareIcon;
    }

    public String getRealMbContentUrl() {
        return realMbContentUrl;
    }

    public void setRealMbContentUrl(String realMbContentUrl) {
        this.realMbContentUrl = realMbContentUrl;
    }

    public static class SysAttachmentFileVoBean implements Serializable{
        /**
         * fileId : 17
         * locate : /usericon/2016/11/9/9/
         * fileName : 1477019905727.jpg
         * fileExtName : jpg
         * fileType : usericon
         * saveName : 1478654496007.jpg
         * refObject : null
         * operator : null
         * operateDate : null
         * segment : null
         * remarks : null
         * enabled : 1
         * bytes : null
         */

        private int fileId;
        private String locate;
        private String fileName;
        private String fileExtName;
        private String fileType;
        private String saveName;
        private Object refObject;
        private Object operator;
        private Object operateDate;
        private Object segment;
        private Object remarks;
        private int enabled;
        private Object bytes;

        public int getFileId() {
            return fileId;
        }

        public void setFileId(int fileId) {
            this.fileId = fileId;
        }

        public String getLocate() {
            return locate;
        }

        public void setLocate(String locate) {
            this.locate = locate;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileExtName() {
            return fileExtName;
        }

        public void setFileExtName(String fileExtName) {
            this.fileExtName = fileExtName;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getSaveName() {
            return saveName;
        }

        public void setSaveName(String saveName) {
            this.saveName = saveName;
        }

        public Object getRefObject() {
            return refObject;
        }

        public void setRefObject(Object refObject) {
            this.refObject = refObject;
        }

        public Object getOperator() {
            return operator;
        }

        public void setOperator(Object operator) {
            this.operator = operator;
        }

        public Object getOperateDate() {
            return operateDate;
        }

        public void setOperateDate(Object operateDate) {
            this.operateDate = operateDate;
        }

        public Object getSegment() {
            return segment;
        }

        public void setSegment(Object segment) {
            this.segment = segment;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public Object getBytes() {
            return bytes;
        }

        public void setBytes(Object bytes) {
            this.bytes = bytes;
        }
    }
}
