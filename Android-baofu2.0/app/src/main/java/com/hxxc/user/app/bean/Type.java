package com.hxxc.user.app.bean;

import com.huaxiafinance.lc.pickerview.model.IPickerViewData;

/**
 * Created by chenqun on 2016/10/12.
 */

public class Type implements IPickerViewData {

    /**
     * code : WH
     * value : 0
     * desc : 未婚
     */

    private String code;
    private int value;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getPickerViewText() {
        return desc;
    }
}
