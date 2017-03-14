package com.hxxc.huaxing.app.ui.mine.appupdata;

import android.content.Context;
import android.text.TextUtils;

import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.UpdateBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.utils.LogUtil;

import rx.Subscriber;

/**
 * Created by houwen.lai on 2016/7/11.
 *
 */
public class AppUpdataPresenter  extends AppUpdataContract.Presenter {

    @Override
    public void toAppUpdata(Context context,String type, int versionCode,String versionName) {
        Api.getClient().getAppUpdate(""+versionCode,versionName).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<UpdateBean>(context) {
                    @Override
                    public void onSuccess(UpdateBean updateBean) {
                        if(updateBean!=null&&updateBean.getIsUpdate().equals("1")){
                            updateBean.setUrl(UserInfoConfig.UPDATE_URL);
//                            updateBean.setUrl(updateBean.getUrl());
                            mView.toAppUpdata(type,updateBean);
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        if (!TextUtils.isEmpty(type))
                        mView.showMsg("已是最新版本");
                    }
                });
    }
}
