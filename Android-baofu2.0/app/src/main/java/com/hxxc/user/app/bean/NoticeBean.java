package com.hxxc.user.app.bean;

/**
 * Created by chenqun on 2016/11/17.
 */

public class NoticeBean {


    /**
     * userMessageVo : {"id":25,"title":"222","createTime":1479284274000,"deleteFlag":0,"readedFlag":1,"uid":50,"msgStatus":1,"backUserId":1,"bid":"1479284843387","bizType":null,"showChannel":null,"content":"222","bizValue":null,"createTimeStr1":"2016年11月16日 16:17","createTimeStr2":"11-16 16:17"}
     * unMsgCount : 0
     */

    private UserMessageVoBean userMessageVo;
    private int unMsgCount;

    public UserMessageVoBean getUserMessageVo() {
        return userMessageVo;
    }

    public void setUserMessageVo(UserMessageVoBean userMessageVo) {
        this.userMessageVo = userMessageVo;
    }

    public int getUnMsgCount() {
        return unMsgCount;
    }

    public void setUnMsgCount(int unMsgCount) {
        this.unMsgCount = unMsgCount;
    }

    public static class UserMessageVoBean {
        /**
         * id : 25
         * title : 222
         * createTime : 1479284274000
         * deleteFlag : 0
         * readedFlag : 1
         * uid : 50
         * msgStatus : 1
         * backUserId : 1
         * bid : 1479284843387
         * bizType : null
         * showChannel : null
         * content : 222
         * bizValue : null
         * createTimeStr1 : 2016年11月16日 16:17
         * createTimeStr2 : 11-16 16:17
         */

        private int id;
        private String title;
        private long createTime;
        private int deleteFlag;
        private int readedFlag;
        private int uid;
        private int msgStatus;
        private int backUserId;
        private String bid;
        private int bizType;
        private Object showChannel;
        private String content;
        private String bizValue;
        private String createTimeStr1;
        private String createTimeStr2;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public int getReadedFlag() {
            return readedFlag;
        }

        public void setReadedFlag(int readedFlag) {
            this.readedFlag = readedFlag;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getMsgStatus() {
            return msgStatus;
        }

        public void setMsgStatus(int msgStatus) {
            this.msgStatus = msgStatus;
        }

        public int getBackUserId() {
            return backUserId;
        }

        public void setBackUserId(int backUserId) {
            this.backUserId = backUserId;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public int getBizType() {
            return bizType;
        }

        public void setBizType(int bizType) {
            this.bizType = bizType;
        }

        public Object getShowChannel() {
            return showChannel;
        }

        public void setShowChannel(Object showChannel) {
            this.showChannel = showChannel;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getBizValue() {
            return bizValue;
        }

        public void setBizValue(String bizValue) {
            this.bizValue = bizValue;
        }

        public String getCreateTimeStr1() {
            return createTimeStr1;
        }

        public void setCreateTimeStr1(String createTimeStr1) {
            this.createTimeStr1 = createTimeStr1;
        }

        public String getCreateTimeStr2() {
            return createTimeStr2;
        }

        public void setCreateTimeStr2(String createTimeStr2) {
            this.createTimeStr2 = createTimeStr2;
        }
    }
}
