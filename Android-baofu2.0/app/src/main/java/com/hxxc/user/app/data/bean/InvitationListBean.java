package com.hxxc.user.app.data.bean;

import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.widget.trecyclerview.BaseEntity;
import com.hxxc.user.app.widget.trecyclerview.C;

import rx.Observable;

/**
 * Created by houwen.lai on 2016/11/21.
 * 邀友记录
 */

public class InvitationListBean extends BaseEntity.ListBean{

    @Override
    public Observable getPageAt(int page) {
        return Api.getClient().getGetUserInvitedList(Api.uid,page, C.PAGE_COUNT).compose(RxApiThread.convert());
    }

    private int id;
    private String invitedCode;
    private String uid;
    private long createTime;

    private String createTimeStr;

//    private InvitedUserBean invitedUser;
    private InvitedUserBean  userVo;


    public InvitationListBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvitedCode() {
        return invitedCode;
    }

    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public InvitedUserBean getUserVo() {
        return userVo;
    }

    public void setUserVo(InvitedUserBean userVo) {
        this.userVo = userVo;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
