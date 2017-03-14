package com.hxxc.huaxing.app.ui.mine.userstatus;

import com.hxxc.huaxing.app.data.bean.UserInfoBean;

/**
 * Created by chenqun on 2016/10/12.
 */

public interface UserInfoV{
    void onReflushUserInfo(UserInfoBean model);

    void onUploadIcon(String fileName);
}
