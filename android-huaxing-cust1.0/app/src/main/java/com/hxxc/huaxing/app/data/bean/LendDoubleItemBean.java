package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/12.
 */

public class LendDoubleItemBean implements Serializable {

    private String name;
    private String value;

    public LendDoubleItemBean() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LendDoubleItemBean{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
