package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.ProductInfo;

/**
 * Created by chenqun on 2016/10/25.
 */

public interface ProductDetailContract {
    interface View{
        void onGetDatas(ProductInfo datas);
    }
}
