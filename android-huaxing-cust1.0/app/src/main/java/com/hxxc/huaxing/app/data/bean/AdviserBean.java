package com.hxxc.huaxing.app.data.bean;

import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.wedgit.trecyclerview.BaseEntity;
import com.hxxc.huaxing.app.wedgit.trecyclerview.C;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import rx.Observable;

/**
 * Created by Administrator on 2016/10/8.
 * 理财师顾问列表
 */

public class AdviserBean extends BaseEntity.ListBean {

    @Override
    public Observable getPageAt(int page) {
//        try {
//            return Api.getClient().getFpList(param.get("showType"),  URLEncoder.encode(param.get("cityName"),"UTF-8").toString() ,param.get("searchKey"),page, C.PAGE_COUNT).compose(RxApiThread.convert());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
            return Api.getClient().getFpList(param.get("showType"),  param.get("cityName") ,param.get("searchKey"),page, C.PAGE_COUNT).compose(RxApiThread.convert());
//        }
    }

    private long fid;
    private String financialno;
    private String username;
    private String password;
    private String fname;
    private String icon;
    private String nickname;
    private String mobile;
    private String email;
    private int servicecount;
    private BigDecimal investmentamout;
    private int department;
    private int status;
    private String token;
    private String devicetoken;
    private String financialKey;
    private String qrCodeUrl;
    private long createTime;
    private int roleId;
    private int pid;
    private String remarks;
    private String level;
    private int isOnline;
    private String bdata;
    private SysDepartmentVoBean sysDepartmentVo;
//            "homepagestatus": 1,
//            "homepagesort": 0,
//            "productpagestatus": 1,
//            "productpagesort": 0,
//            "citypagestatus": 1,
//            "citypagesort": 0,
//            "registerpagestatus": 1,
//            "registerpagesort": 0,
//            "roleId": 1,
//            "pid": 0,
//            "remarks": "1",
//            "level": null,
//            "isOnline": 1,
//            "bdata": "烦烦烦方法方法反反复复反复反复反复顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶灌灌灌灌灌灌灌灌灌灌",
//            "serviceadvantage": "",
//            "successfulcase": "",
//            "sysDepartmentVo": null


    public AdviserBean() {
        super();
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public BigDecimal getInvestmentamout() {
        return investmentamout;
    }

    public void setInvestmentamout(BigDecimal investmentamout) {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevicetoken() {
        return devicetoken;
    }

    public void setDevicetoken(String devicetoken) {
        this.devicetoken = devicetoken;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
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

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getBdata() {
        return bdata;
    }

    public void setBdata(String bdata) {
        this.bdata = bdata;
    }

    @Override
    public String toString() {
        return "AdviserBean{" +
                "fid=" + fid +
                ", financialno='" + financialno + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fname='" + fname + '\'' +
                ", icon='" + icon + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", servicecount=" + servicecount +
                ", investmentamout=" + investmentamout +
                ", department=" + department +
                ", status=" + status +
                ", token='" + token + '\'' +
                ", devicetoken='" + devicetoken + '\'' +
                ", financialKey='" + financialKey + '\'' +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", createTime=" + createTime +
                ", roleId=" + roleId +
                ", pid=" + pid +
                ", remarks='" + remarks + '\'' +
                ", level='" + level + '\'' +
                ", isOnline=" + isOnline +
                ", bdata='" + bdata + '\'' +
                '}';
    }


}
