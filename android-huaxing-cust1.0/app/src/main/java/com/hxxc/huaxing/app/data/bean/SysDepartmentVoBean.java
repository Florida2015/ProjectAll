package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/9.
 */

public class SysDepartmentVoBean implements Serializable {

    private int did;
    private String name;
    private String address;
    private String picurl;
    private int city;
    private String telephone;
    private int province;
    private int departmentNum;
    private int status;
    private String pics;
    private City cityVo;

    public SysDepartmentVoBean() {
        super();
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getDepartmentNum() {
        return departmentNum;
    }

    public void setDepartmentNum(int departmentNum) {
        this.departmentNum = departmentNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public City getCityVo() {
        return cityVo;
    }

    public void setCityVo(City cityVo) {
        this.cityVo = cityVo;
    }

    @Override
    public String toString() {
        return "SysDepartmentVoBean{" +
                "did=" + did +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", picurl='" + picurl + '\'' +
                ", city=" + city +
                ", telephone='" + telephone + '\'' +
                ", province=" + province +
                ", departmentNum=" + departmentNum +
                ", status=" + status +
                ", pics='" + pics + '\'' +
                ", cityVo=" + cityVo +
                '}';
    }
}
