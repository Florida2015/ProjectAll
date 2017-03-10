package com.hxxc.user.app.data.bean;

import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;
import com.hxxc.user.app.widget.trecyclerview.C;

import java.math.BigDecimal;

import rx.Observable;

/**
 * Created by houwen.lai on 2016/11/21.
 * 邀请好友  将来红包
 */

public class InvitaionRewardBean extends BaseEntity.ListBean {

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getFindInvitedCountMoney(Api.uid,page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private String userName;
    private long createTime;
    private BigDecimal money;
    private String sources;
    private String createTimeText;

    public InvitaionRewardBean() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getCreateTimeText() {
        return createTimeText;
    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }
}
