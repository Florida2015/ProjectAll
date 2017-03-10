package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.NoticeBean;

import java.util.List;

/**
 * Created by chenqun on 2016/11/17.
 */

public interface NoticesV {
    void setDatas(List<NoticeBean.UserMessageVoBean> strings, boolean isLoadingmore);
}
