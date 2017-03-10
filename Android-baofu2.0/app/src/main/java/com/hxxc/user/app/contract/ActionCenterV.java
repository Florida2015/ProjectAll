package com.hxxc.user.app.contract;


import com.hxxc.user.app.bean.ActionCenterBean;

import java.util.List;

/**
 * Created by chenqun on 2016/11/10.
 */

public interface ActionCenterV {
    void setDatas(List<ActionCenterBean> actionCenterBean, boolean isLoadingmore);
}
