package com.hxxc.huaxing.app.ui.wealth;

import com.hxxc.huaxing.app.data.bean.ProductBean;

import java.util.List;

/**
 * Created by chenqun on 2016/10/8.
 */

public interface WealthV {
    void onSuccess(List<ProductBean> datas,boolean isLoadingmore);
    void onError();
}
