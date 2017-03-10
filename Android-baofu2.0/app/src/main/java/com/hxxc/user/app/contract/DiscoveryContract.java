package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.ContentBean;

import java.util.List;

/**
 * Created by chenqun on 2016/11/2.
 */

public interface DiscoveryContract {
    interface View  {
        void  setDatas(List<ContentBean> list, boolean isLoadingmore);

        void toMail(String s);
    }

    interface Presenter {
        void getDatas(boolean isLoadingmore);
        void onLoadingmore(boolean isLoadingmore);
    }
}
