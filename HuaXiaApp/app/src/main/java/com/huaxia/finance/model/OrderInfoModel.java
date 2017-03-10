package com.huaxia.finance.model;

import java.io.Serializable;

/**
 *
 * Created by houwen.lai on 2016/3/1.
 */
public class OrderInfoModel implements Serializable {

    private String amount;
    private String merchantId;
    private String message;
    private String orderId;
    private String resultCode;
    private String sign;
    private boolean success;
    private String thirdOrderId;
    private String tradeTime;
    private String transCode;
    private String verifyTime;


    public OrderInfoModel() {
        super();
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    @Override
    public String toString() {
        return "OrderInfoModel{" +
                "amount='" + amount + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", message='" + message + '\'' +
                ", orderId='" + orderId + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", sign='" + sign + '\'' +
                ", success=" + success +
                ", thirdOrderId='" + thirdOrderId + '\'' +
                ", tradeTime='" + tradeTime + '\'' +
                ", transCode='" + transCode + '\'' +
                ", verifyTime='" + verifyTime + '\'' +
                '}';
    }
}
