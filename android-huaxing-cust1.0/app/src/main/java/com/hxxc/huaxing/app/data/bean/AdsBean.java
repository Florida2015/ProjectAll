package com.hxxc.huaxing.app.data.bean;

/**
 * Created by chenqun on 2016/11/30.
 */

public class AdsBean {

    /**
     * aid : 2
     * name : 222111
     * pictureSourceUrl : a-guozixing.jpg
     * connectUrl : 22222
     * status : 222
     * sort : 0
     * typeId : 0
     * platform : 0
     */

    private int aid;
    private String name;
    private String pictureSourceUrl;
    private String connectUrl;
    private int status;
    private int sort;
    private int typeId;
    private int platform;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureSourceUrl() {
        return pictureSourceUrl;
    }

    public void setPictureSourceUrl(String pictureSourceUrl) {
        this.pictureSourceUrl = pictureSourceUrl;
    }

    public String getConnectUrl() {
        return connectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}
