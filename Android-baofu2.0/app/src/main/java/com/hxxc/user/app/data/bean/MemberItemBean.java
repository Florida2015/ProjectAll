package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by houwen.lai on 2016/11/1.
 * 会员中心 modes
 */

public class MemberItemBean implements Serializable {

    private String tag;
    private String tag_title;
    private String context_1;
    private String context_2;
    private String context_btn;

    public MemberItemBean() {
        super();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag_title() {
        return tag_title;
    }

    public void setTag_title(String tag_title) {
        this.tag_title = tag_title;
    }

    public String getContext_1() {
        return context_1;
    }

    public void setContext_1(String context_1) {
        this.context_1 = context_1;
    }

    public String getContext_2() {
        return context_2;
    }

    public void setContext_2(String context_2) {
        this.context_2 = context_2;
    }

    public String getContext_btn() {
        return context_btn;
    }

    public void setContext_btn(String context_btn) {
        this.context_btn = context_btn;
    }
}
