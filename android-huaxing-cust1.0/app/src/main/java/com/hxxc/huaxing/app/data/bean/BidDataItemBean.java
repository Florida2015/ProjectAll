package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/23.
 * 自动投标 选择期限
 */
public class BidDataItemBean implements Serializable {

    private String name;
    private boolean flag;

    public BidDataItemBean() {
       super();
    }

    public BidDataItemBean(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BidDataItemBean{" +
                "name='" + name + '\'' +
                ", flag=" + flag +
                '}';
    }
}
