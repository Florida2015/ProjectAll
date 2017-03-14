package com.hxxc.huaxing.app.data.event;

import com.hxxc.huaxing.app.data.bean.UserInfoBean;

/**
 * Created by chenqun on 2016/10/12.
 */

public class ReflushUserInfoEvent {

    public final UserInfoBean userInfoBean;

    public ReflushUserInfoEvent(UserInfoBean model) {
        userInfoBean = model;
    }
}
