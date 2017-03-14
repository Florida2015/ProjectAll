package com.hxxc.huaxing.app.data.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/21.
 * 基类
 */
public class BaseBean<T> implements Serializable {

    /**
     * statusCode : 0000
     * errMsg : 成功
     * total : null
     * model : T
     * success : true
     * successful : true
     */
    private boolean success;
    private boolean successful;
    private String statusCode;
    private String errMsg;
    private T model;
    private String rows;
    private int page;
    private int total;

    public BaseBean() {
        super();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "success=" + success +
                ", successful=" + successful +
                ", statusCode='" + statusCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", model=" + model +
                ", rows='" + rows + '\'' +
                ", page=" + page +
                ", total=" + total +
                '}';
    }

    //    public BaseBean<T> prase(String text){
//        return  HXXCApplication.getInstance().getGson().fromJson(text,new TypeToken<BaseBean<T>>(){}.getType());
//    }
}
