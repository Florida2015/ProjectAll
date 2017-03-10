package com.hxxc.user.app.contract.i;

import com.hxxc.user.app.bean.UserInfo;

/**
 * Created by chenqun on 2016/7/11.
 */
public interface ILogin {
    void onLoginSuccess(UserInfo bean);
    void onLoginFailure();
}
