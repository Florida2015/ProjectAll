package com.hxxc.user.app.bean;

/**
 * Created by chenqun on 2016/7/14.
 */
public class ChatTokenBean {

    /**
     * chatToken : WvzOycR1OHXhdJx6rSg8pPTIyXCQ6jTXjiWTuW+5cMp/lv56V95igJL7s94rxH9P1Vze2jBR5ueqY/KSVr6iHN1xt5N4hzr1gYwPeZBtJVQ=
     * icon : https://lc.huaxiafinance.com/images/user300x300.jpg
     * mobile : 15250464603
     * ufid : 700
     * userNo : HX7000714105325
     */

    private String chatToken;
    private String icon;
    private String mobile;
    private int ufid;
    private String userNo;

    public String getChatToken() {
        return chatToken;
    }

    public void setChatToken(String chatToken) {
        this.chatToken = chatToken;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUfid() {
        return ufid;
    }

    public void setUfid(int ufid) {
        this.ufid = ufid;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "ChatTokenBean{" +
                "chatToken='" + chatToken + '\'' +
                ", icon='" + icon + '\'' +
                ", mobile='" + mobile + '\'' +
                ", ufid=" + ufid +
                ", userNo='" + userNo + '\'' +
                '}';
    }
}
