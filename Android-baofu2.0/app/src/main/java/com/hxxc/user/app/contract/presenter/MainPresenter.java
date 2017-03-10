package com.hxxc.user.app.contract.presenter;

import android.content.pm.PackageManager;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.bean.AppUpdateBean;
import com.hxxc.user.app.contract.MainContract;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.utils.SPUtils;

import java.util.Date;

import static com.hxxc.user.app.utils.SPUtils.geTinstance;

/**
 * Created by chenqun on 2016/6/22.
 */
public class MainPresenter extends RxBasePresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void subscribe() {
        getUpdateInfo();
    }

    @Override
    public void doReflush() {

    }

    @Override
    public void getUpdateInfo() {
        final String versionName;
        try {
            versionName = HXXCApplication.getContext().getPackageManager().getPackageInfo(HXXCApplication.getContext().getPackageName(), 0).versionName;
            mApiManager.updateApp(versionName + "", Constants.UPDATE_ANDROID, new SimpleCallback<AppUpdateBean>() {
                @Override
                public void onNext(AppUpdateBean updateBean) {
                    if (null != updateBean) {
//                        geTinstance().put(Constants.LASTVERSION, updateBean.versionsId);
//                        int ignore_version = geTinstance().get(Constants.IGNORE_VERSIONCODE, 0);
//                            if (ignore_version != updateBean.versionsId) {
                        doUpdateApp(updateBean);
//                            } else {
//                                if (updateBean.isForceUpdate == 0) {//強制更新
//                                    if (versionCode < updateBean.versionsId) {
//                                        doUpdateApp(updateBean);
//                                    }
//                                }
//                            }
                    }
                }

                @Override
                public void onError() {
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDevice(String deviceToken) {
        if (deviceToken == null || "".equals(deviceToken))
            return;
        mApiManager.updateDevice(SPUtils.geTinstance().getUid(), deviceToken, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                LogUtils.e("devicetoken--设备号上传成功");
            }

            @Override
            public void onError() {
                LogUtils.e("devicetoken--设备号上传失败");
            }
        });
    }

    protected void doUpdateApp(final AppUpdateBean updateBean) {
        Date date = new Date();
        date.setTime(updateBean.pushTime);
        int i = new Date().compareTo(date);
        if (i >= 0) {
            mView.showUpdateDialog(updateBean);
        }
    }

    public void getChatToken() {
        mApiManager.getChatUserTokenInfo(SPUtils.geTinstance().getUid(), new SimpleCallback<String>() {
            @Override
            public void onNext(String token) {
                // 保存chatToken到userBean里面去
                LogUtils.e("2**************************请求token成功==" + token);
                geTinstance().setImToken(token);
                mView.connectIm(token);
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void unsubscribe() {
        mView = null;
        super.unsubscribe();
    }
}
