package com.hxxc.user.app.bean;

/**
 * Created by chenqun on 2016/11/9.
 */

public class HelpCenterItemBean {


    /**
     * id : 1
     * questions : 产品预期收益如何计算？
     * status : 1
     * sort : 1
     * type : 2
     * mobileAnswers : 产品预期收益=本金×预期年化收益率×实际出借天数÷360<br />
     * pcAnswers : 产品预期收益=本金×预期年化收益率×实际出借天数÷360
     * mobileImgId : 12
     * pcImgId : 12
     * mobbileDes :
     * realMobileImgUrl : https://lcsitn.huaxiafinance.com/picture/news/2016/11/28/17/1480325364315.png
     * realPcImgUrl : https://lcsitn.huaxiafinance.com/picture/news/2016/11/28/17/1480325364315.png
     * mobileImgVo : {"fileId":12,"locate":"/news/2016/11/28/17/","fileName":"QQ截图20161128172757.png","fileExtName":"png","fileType":"news","saveName":"1480325364315.png","refObject":"","operator":null,"operateDate":1480414671000,"segment":"","remarks":"","enabled":1,"bytes":null}
     * pcImgVo : {"fileId":12,"locate":"/news/2016/11/28/17/","fileName":"QQ截图20161128172757.png","fileExtName":"png","fileType":"news","saveName":"1480325364315.png","refObject":"","operator":null,"operateDate":1480414671000,"segment":"","remarks":"","enabled":1,"bytes":null}
     */

    private int id;
    private String questions;
    private int status;
    private int sort;
    private int type;
    private String mobileAnswers;
    private String pcAnswers;
    private int mobileImgId;
    private int pcImgId;
    private String mobbileDes;
    private String realMobileImgUrl;
    private String realPcImgUrl;
    private MobileImgVoBean mobileImgVo;
    private PcImgVoBean pcImgVo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMobileAnswers() {
        return mobileAnswers;
    }

    public void setMobileAnswers(String mobileAnswers) {
        this.mobileAnswers = mobileAnswers;
    }

    public String getPcAnswers() {
        return pcAnswers;
    }

    public void setPcAnswers(String pcAnswers) {
        this.pcAnswers = pcAnswers;
    }

    public int getMobileImgId() {
        return mobileImgId;
    }

    public void setMobileImgId(int mobileImgId) {
        this.mobileImgId = mobileImgId;
    }

    public int getPcImgId() {
        return pcImgId;
    }

    public void setPcImgId(int pcImgId) {
        this.pcImgId = pcImgId;
    }

    public String getMobbileDes() {
        return mobbileDes;
    }

    public void setMobbileDes(String mobbileDes) {
        this.mobbileDes = mobbileDes;
    }

    public String getRealMobileImgUrl() {
        return realMobileImgUrl;
    }

    public void setRealMobileImgUrl(String realMobileImgUrl) {
        this.realMobileImgUrl = realMobileImgUrl;
    }

    public String getRealPcImgUrl() {
        return realPcImgUrl;
    }

    public void setRealPcImgUrl(String realPcImgUrl) {
        this.realPcImgUrl = realPcImgUrl;
    }

    public MobileImgVoBean getMobileImgVo() {
        return mobileImgVo;
    }

    public void setMobileImgVo(MobileImgVoBean mobileImgVo) {
        this.mobileImgVo = mobileImgVo;
    }

    public PcImgVoBean getPcImgVo() {
        return pcImgVo;
    }

    public void setPcImgVo(PcImgVoBean pcImgVo) {
        this.pcImgVo = pcImgVo;
    }

    public static class MobileImgVoBean {
        /**
         * fileId : 12
         * locate : /news/2016/11/28/17/
         * fileName : QQ截图20161128172757.png
         * fileExtName : png
         * fileType : news
         * saveName : 1480325364315.png
         * refObject :
         * operator : null
         * operateDate : 1480414671000
         * segment :
         * remarks :
         * enabled : 1
         * bytes : null
         */

        private int fileId;
        private String locate;
        private String fileName;
        private String fileExtName;
        private String fileType;
        private String saveName;
        private String refObject;
        private Object operator;
        private long operateDate;
        private String segment;
        private String remarks;
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

        public String getRefObject() {
            return refObject;
        }

        public void setRefObject(String refObject) {
            this.refObject = refObject;
        }

        public Object getOperator() {
            return operator;
        }

        public void setOperator(Object operator) {
            this.operator = operator;
        }

        public long getOperateDate() {
            return operateDate;
        }

        public void setOperateDate(long operateDate) {
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

        public Object getBytes() {
            return bytes;
        }

        public void setBytes(Object bytes) {
            this.bytes = bytes;
        }
    }

    public static class PcImgVoBean {
        /**
         * fileId : 12
         * locate : /news/2016/11/28/17/
         * fileName : QQ截图20161128172757.png
         * fileExtName : png
         * fileType : news
         * saveName : 1480325364315.png
         * refObject :
         * operator : null
         * operateDate : 1480414671000
         * segment :
         * remarks :
         * enabled : 1
         * bytes : null
         */

        private int fileId;
        private String locate;
        private String fileName;
        private String fileExtName;
        private String fileType;
        private String saveName;
        private String refObject;
        private Object operator;
        private long operateDate;
        private String segment;
        private String remarks;
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

        public String getRefObject() {
            return refObject;
        }

        public void setRefObject(String refObject) {
            this.refObject = refObject;
        }

        public Object getOperator() {
            return operator;
        }

        public void setOperator(Object operator) {
            this.operator = operator;
        }

        public long getOperateDate() {
            return operateDate;
        }

        public void setOperateDate(long operateDate) {
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

        public Object getBytes() {
            return bytes;
        }

        public void setBytes(Object bytes) {
            this.bytes = bytes;
        }
    }
}
