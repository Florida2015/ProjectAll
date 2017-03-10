package com.hxxc.user.app.contract;

import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.bean.Product;

import java.util.List;

/**
 * Created by chenqun on 2016/6/22.
 */
public interface IndexContract {
    interface View  {
        void initViewPagerData(List<IndexAds> list);
        void onGetDatas(List<Product> list ,boolean isLoadingmore);
    }

    interface Presenter{
        void getTopbarData();
        void getDatas();

        void onLoadingmore();
    }
}
