package com.hxxc.huaxing.app.ui.wealth;

import android.content.Context;

import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.RxBasePresenter;
import com.hxxc.huaxing.app.utils.LogUtil;

import java.util.List;

import rx.Subscriber;

/**
 * Created by chenqun on 2016/10/8.
 */

public class WealthPresenter extends RxBasePresenter{
    private final int mType;
    private int pager = 1;
    private  WealthV mView;

    private Context mContext;

    public WealthPresenter(WealthV view,int type,Context context) {
       mView = view;
        this.mType = type;
        this.mContext = context;
    }

    @Override
    public void doReflush() {
        pager = 1;
        getDatas(false);
    }

    private void getDatas(boolean isLoadingmore){
        mApi.getProductList(mType+"",pager==1?"":"1",pager+"", UserInfoConfig.ROWS+"").compose(RxApiThread.convert()).subscribe(new BaseSubscriber<List<ProductBean>>(mContext) {
            @Override
            public void onSuccess(List<ProductBean> productBeen) {
                mView.onSuccess(productBeen,isLoadingmore);
                LogUtil.d(productBeen.toString());
            }

            @Override
            public void onFail(String fail) {
                mView.onError();
                LogUtil.d("业绩报告==" + fail);
            }
        });
    }

    public void onLoadingMore(){
        pager++;
        getDatas(true);
    }
}
