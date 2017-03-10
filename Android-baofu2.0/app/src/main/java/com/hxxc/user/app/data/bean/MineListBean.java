package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/10/25.
 * 我的 列表
 */

public class MineListBean implements Serializable {

    private int resId;
    private String title;
    private String time;
    private String context;
    private String textbtn;

    public MineListBean() {
        super();
    }

    public MineListBean(int resId, String title, String time, String context, String textbtn) {
        this.resId = resId;
        this.title = title;
        this.time = time;
        this.context = context;
        this.textbtn = textbtn;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTextbtn() {
        return textbtn;
    }

    public void setTextbtn(String textbtn) {
        this.textbtn = textbtn;
    }
}
