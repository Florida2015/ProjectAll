package com.hxxc.huaxing.app.ui.wealth.productlist;

import com.hxxc.huaxing.app.data.bean.ProductBean;

import java.util.List;

/**
 * Created by chenqun on 2016/10/9.
 */

public interface ProductListV {

    void onError();
    void onSuccess(List<ProductBean> model, boolean isLoadingmore);
}
