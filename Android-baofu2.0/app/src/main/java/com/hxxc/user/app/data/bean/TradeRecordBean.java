package com.hxxc.user.app.data.bean;

import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;
import com.hxxc.user.app.widget.trecyclerview.C;

import java.math.BigDecimal;

import rx.Observable;

/**
 * Created by houwen.lai on 2016/11/10.
 * 我的 交易记录
 */

public class TradeRecordBean extends BaseEntity.ListBean{

    @Override
    public Observable getPageAt(int page) {//Api.uid
        return Api.getClient().getGetUserTradeList(Api.uid,param.get("trade"),page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private int id;
    private String descriptions;
    private long createTime;
    private BigDecimal money;
    private String type;//出借pay，profit利息收入，principal本息赎回
    private int tradeType;//1收入 2支出
    private String createTimeText;

    private String orderNo;
    private String flowing;//交易流水号
    private String transactionType;
    private String transactionCard;
    private int uid;

    private BigDecimal additionalMoney;
    private int additionalType;
    private String bankAndCard;
    private String transactionBank;

    private String typeText;
    private String tradeTypeText;
    private int month;//
    private BigDecimal payMoney;//出借
    private BigDecimal backMoney;//回款

    private String tradeR;//请求trade

    public TradeRecordBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public String getCreateTimeText() {
        return createTimeText;
    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFlowing() {
        return flowing;
    }

    public void setFlowing(String flowing) {
        this.flowing = flowing;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionCard() {
        return transactionCard;
    }

    public void setTransactionCard(String transactionCard) {
        this.transactionCard = transactionCard;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getAdditionalMoney() {
        return additionalMoney;
    }

    public void setAdditionalMoney(BigDecimal additionalMoney) {
        this.additionalMoney = additionalMoney;
    }

    public int getAdditionalType() {
        return additionalType;
    }

    public void setAdditionalType(int additionalType) {
        this.additionalType = additionalType;
    }

    public String getBankAndCard() {
        return bankAndCard;
    }

    public void setBankAndCard(String bankAndCard) {
        this.bankAndCard = bankAndCard;
    }

    public String getTransactionBank() {
        return transactionBank;
    }

    public void setTransactionBank(String transactionBank) {
        this.transactionBank = transactionBank;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getTradeTypeText() {
        return tradeTypeText;
    }

    public void setTradeTypeText(String tradeTypeText) {
        this.tradeTypeText = tradeTypeText;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    public String getTradeR() {
        return tradeR;
    }

}
