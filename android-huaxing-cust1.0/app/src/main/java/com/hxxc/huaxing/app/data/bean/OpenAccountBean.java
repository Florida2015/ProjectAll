package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/6.
 */

public class OpenAccountBean implements Serializable {

    private String baseUrl;
    private String htmlStr;
    private String bindcardStatus;//绑卡状态【1：已绑定；0：未绑定】

    public OpenAccountBean() {
        super();
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }

    public String getBindcardStatus() {
        return bindcardStatus;
    }

    public void setBindcardStatus(String bindcardStatus) {
        this.bindcardStatus = bindcardStatus;
    }
}
