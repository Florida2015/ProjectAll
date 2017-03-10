package com.hxxc.user.app.bean;

/**
 * Created by chenqun on 2016/11/9.
 */

public class HelpCenterBean {

    /**
     * id : 613
     * label : 订单
     * value : 4
     * type : faq_help
     * description : 用户帮助中心常见的问题
     * sort : 4
     * remark :
     * delFlag :
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
}
