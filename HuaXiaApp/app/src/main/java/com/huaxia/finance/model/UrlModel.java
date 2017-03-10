package com.huaxia.finance.model;

import java.io.Serializable;

/**
 * 易联支付
 * Created by houwen.lai on 2016/2/4.
 */
public class UrlModel implements Serializable {

    private String url;
    private OrderInfoModel orderInfo;

    public UrlModel() {
        super();
    }

    public UrlModel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OrderInfoModel getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoModel orderInfo) {
        this.orderInfo = orderInfo;
    }

    @Override
    public String toString() {
        return "UrlModel{" +
                "url='" + url + '\'' +
                ", orderInfo=" + orderInfo +
                '}';
    }
}
