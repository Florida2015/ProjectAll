package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.bean.NoticeBean;

/**
 * Created by chenqun on 2016/11/16.
 */

public interface MessageV {
    void onGetNes(ContentBean contentBean);

    void onGetNotice(NoticeBean noticeBean);
}
