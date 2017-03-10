package com.hxxc.user.app.contract;

import com.hxxc.user.app.BaseView;
import com.hxxc.user.app.bean.AppUpdateBean;

/**
 * Created by chenqun on 2016/6/22.
 */
public interface MainContract {

    interface View extends BaseView {
        void showUpdateDialog(AppUpdateBean updateBean);
        void connectIm(String token);
    }

    interface Presenter{
        void getUpdateInfo();
        void updateDevice(String deviceToken);
    }
}
