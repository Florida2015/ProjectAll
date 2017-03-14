package com.hxxc.huaxing.app.ui.HuaXingFragment;

import com.hxxc.huaxing.app.data.bean.AdsBean;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.ui.base.BaseV;

import java.util.List;

/**
 * Created by chenqun on 2016/10/9.
 */

public interface HomeV extends BaseV<List<ProductBean>> {
    void onGetAds(List<AdsBean> model);
}
