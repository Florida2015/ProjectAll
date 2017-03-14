package com.hxxc.huaxing.app.ui.mine.appupdata;


import android.content.Context;

import com.hxxc.huaxing.app.data.bean.UpdateBean;
import com.hxxc.huaxing.app.ui.base.BaseModel;
import com.hxxc.huaxing.app.ui.base.BasePresenter;
import com.hxxc.huaxing.app.ui.base.BaseView;

/**
 * Created by Administrator on 2016/7/11.
 * app升级
 */
public interface AppUpdataContract {

    interface Model extends BaseModel {

    }

    interface View extends BaseView {
        void toAppUpdata(String type, UpdateBean updateBean); // 升级处理
        void showMsg(String msg);
    }

    abstract class Presenter extends BasePresenter<Model,View> {

        public abstract void toAppUpdata(Context context,String type,int versionCode,String versionName);

        @Override
        public void onStart() {

        }
    }
}
