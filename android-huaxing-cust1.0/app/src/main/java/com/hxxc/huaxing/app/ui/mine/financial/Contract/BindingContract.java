package com.hxxc.huaxing.app.ui.mine.financial.Contract;

import android.content.Context;

import com.hxxc.huaxing.app.ui.base.BaseModel;
import com.hxxc.huaxing.app.ui.base.BasePresenter;
import com.hxxc.huaxing.app.ui.base.BaseView;

/**
 * Created by Administrator on 2016/9/27.
 * 推荐顾问
 */

public interface BindingContract {

    interface Model extends BaseModel {
    }
    interface View extends BaseView {
        void toRecommedResult();//
        void showErrorMessage(String erro);
    }
    abstract class Presenter extends BasePresenter<BindingContract.Model,BindingContract.View> {

        public abstract void getRecommedFinancial(Context mContext,String areaId,String searchKey,int page,int rows);

        @Override
        public void onStart() {
        }
    }

}
