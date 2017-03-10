package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.Product;

import java.util.List;

/**
 * Created by chenqun on 2016/6/22.
 */
public interface ProductContract {

    interface View  {
        void  setProductList(List<Product> list, boolean isLoadingmore);
    }

    interface Presenter {
        void getProductList(boolean isLoadingmore);
    }
}
