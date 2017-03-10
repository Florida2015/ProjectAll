package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.ContentBean;

import java.util.List;

/**
 * Created by chenqun on 2016/11/17.
 */

public interface NewsV {
    void setDatas(List<ContentBean> products, boolean isLoadingmore);
}
