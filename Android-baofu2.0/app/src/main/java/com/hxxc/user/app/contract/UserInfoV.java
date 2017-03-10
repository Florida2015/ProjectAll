package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.UserInfo;

/**
 * Created by chenqun on 2016/10/27.
 */

public interface UserInfoV {
    void onReflushUserInfo(UserInfo model);

    void onUploadIcon(UserInfo userInfo);
}
