package com.hxxc.user.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14.
 * 理财师详情
 */

public class FinancialDetailBean implements Serializable{

    private int fid;
    private String financialno;
    private String username;
    private String fname;
    private String icon;
    private String nickname;
    private String mobile;
    private String email;
    private int servicecount;
    private double investmentamout;
    private int department;
    private int status;
    private String financialKey;
    private String qrCodeUrl;
    private String createTime;
    private String roleId;
    private String pid;
    private String remarks;
    private String level;
    private String isOnline;
    private String bdata;
    private SysDepartmentVoBean sysDepartmentVo;

    private String countryPhone;

    public FinancialDetailBean() {
        super();
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFinancialno() {
        return financialno;
    }

    public void setFinancialno(String financialno) {
        this.financialno = financialno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getServicecount() {
        return servicecount;
    }

    public void setServicecount(int servicecount) {
        this.servicecount = servicecount;
    }

    public double getInvestmentamout() {
        return investmentamout;
    }

    public void setInvestmentamout(double investmentamout) {
        this.investmentamout = investmentamout;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFinancialKey() {
        return financialKey;
    }

    public void setFinancialKey(String financialKey) {
        this.financialKey = financialKey;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getBdata() {
        return bdata;
    }

    public void setBdata(String bdata) {
        this.bdata = bdata;
    }

    public SysDepartmentVoBean getSysDepartmentVo() {
        return sysDepartmentVo;
    }

    public void setSysDepartmentVo(SysDepartmentVoBean sysDepartmentVo) {
        this.sysDepartmentVo = sysDepartmentVo;
    }

    public String getCountryPhone() {
        return countryPhone;
    }

    public void setCountryPhone(String countryPhone) {
        this.countryPhone = countryPhone;
    }

    @Override
    public String toString() {
        return "FinancialDetailBean{" +
                "fid=" + fid +
                ", financialno='" + financialno + '\'' +
                ", username='" + username + '\'' +
                ", fname='" + fname + '\'' +
                ", icon='" + icon + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", servicecount=" + servicecount +
                ", investmentamout='" + investmentamout + '\'' +
                ", department=" + department +
                ", status=" + status +
                ", financialKey='" + financialKey + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", createTime='" + createTime + '\'' +
                ", roleId='" + roleId + '\'' +
                ", pid='" + pid + '\'' +
                ", remarks='" + remarks + '\'' +
                ", level='" + level + '\'' +
                ", isOnline='" + isOnline + '\'' +
                ", bdata='" + bdata + '\'' +
                ", sysDepartmentVo=" + sysDepartmentVo +
                '}';
    }
}
