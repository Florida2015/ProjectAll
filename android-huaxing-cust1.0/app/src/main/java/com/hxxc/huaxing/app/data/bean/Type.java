package com.hxxc.huaxing.app.data.bean;

import com.huaxiafinance.lc.pickerview.model.IPickerViewData;

/**
 * Created by chenqun on 2016/10/12.
 */

public class Type implements IPickerViewData {

    /**
     * id : 220
     * label : 女
     * value : 1
     * type : sex
     * description : 性别
     * sort : 2
     * remark :
     * delFlag : 1
     */

    private int id;
    private String label;
    private String value;
    private String type;
    private String description;
    private int sort;
    private String remark;
    private String delFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getPickerViewText() {
        return label;
    }
}
