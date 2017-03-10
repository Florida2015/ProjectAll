package com.hxxc.user.app.bean;

import com.hxxc.user.app.data.bean.ShareByTypeBean;

import java.io.Serializable;

public class IndexAds implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6741417932809854355L;


    /**
     * aid : 2
     * name : 12
     * pictureSourceUrl : 17
     * connectUrl :
     * status : 1
     * sort : 1
     * typeId : 0
     * platform : 2
     * sysAttachmentFileVo : {"fileId":17,"locate":"/usericon/2016/11/9/9/","fileName":"1477019905727.jpg","fileExtName":"jpg","fileType":"usericon","saveName":"1478654496007.jpg","refObject":"","operator":"","operateDate":"","segment":"","remarks":"","enabled":1,"bytes":""}
     * realPictureSourceUrl : http://192.168.11.48:8089/picture/usericon/2016/11/9/9/1478654496007.jpg
     */

    private int aid;
    private String name;
    private String pictureSourceUrl;
    private String connectUrl;
    private int status;
    private int sort;
    private int typeId;
    private int platform;
    /**
     * fileId : 17
     * locate : /usericon/2016/11/9/9/
     * fileName : 1477019905727.jpg
     * fileExtName : jpg
     * fileType : usericon
     * saveName : 1478654496007.jpg
     * refObject :
     * operator :
     * operateDate :
     * segment :
     * remarks :
     * enabled : 1
     * bytes :
     */


    private SysAttachmentFileVoBean sysAttachmentFileVo;
    private String realPictureSourceUrl;

    private ShareByTypeBean shareVo;//分享

    public ShareByTypeBean getShareVo() {
        return shareVo;
    }

    public void setShareVo(ShareByTypeBean shareVo) {
        this.shareVo = shareVo;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureSourceUrl() {
        return pictureSourceUrl;
    }

    public void setPictureSourceUrl(String pictureSourceUrl) {
        this.pictureSourceUrl = pictureSourceUrl;
    }

    public String getConnectUrl() {
        return connectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public SysAttachmentFileVoBean getSysAttachmentFileVo() {
        return sysAttachmentFileVo;
    }

    public void setSysAttachmentFileVo(SysAttachmentFileVoBean sysAttachmentFileVo) {
        this.sysAttachmentFileVo = sysAttachmentFileVo;
    }

    public String getRealPictureSourceUrl() {
        return realPictureSourceUrl;
    }

    public void setRealPictureSourceUrl(String realPictureSourceUrl) {
        this.realPictureSourceUrl = realPictureSourceUrl;
    }

    public static class SysAttachmentFileVoBean {
        private int fileId;
        private String locate;
        private String fileName;
        private String fileExtName;
        private String fileType;
        private String saveName;
        private String refObject;
        private String operator;
        private String operateDate;
        private String segment;
        private String remarks;
        private int enabled;
        private String bytes;

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

        public String getRefObject() {
            return refObject;
        }

        public void setRefObject(String refObject) {
            this.refObject = refObject;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getOperateDate() {
            return operateDate;
        }

        public void setOperateDate(String operateDate) {
            this.operateDate = operateDate;
        }

        public String getSegment() {
            return segment;
        }

        public void setSegment(String segment) {
            this.segment = segment;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getEnabled() {
            return enabled;
        }

        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }

        public String getBytes() {
            return bytes;
        }

        public void setBytes(String bytes) {
            this.bytes = bytes;
        }
    }
}