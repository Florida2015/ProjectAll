package com.huaxia.finance.model;

import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;

import java.io.Serializable;

/**
 * 功能：model 基类
 * Created by houwen.lai on 2015/11/26.
 */
public class BaseModel<T> implements Serializable{

    private boolean success;
    private String token;
    private String msg;//返回描述
    private String mes;
    private String status;//请求返回状态
    private T data;//返回数据集

    public BaseModel(){
        super();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "success=" + success +
                ", token='" + token + '\'' +
                ", msg='" + msg + '\'' +
                ", mes='" + mes + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }

    public BaseModel prase(String text){
        return  DMApplication.getInstance().getGson().fromJson(text,new TypeToken<BaseModel<T>>(){}.getType());
    }
}
