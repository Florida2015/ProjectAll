package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.ProductInfo;

import java.io.Serializable;

/**
 * Created by chenqun on 2016/11/9.
 */

public interface ProductDetailVertical2V extends Serializable{
    ProductInfo getProductInfo();
    String getDefaultPid();
    String getProblemType();
}
