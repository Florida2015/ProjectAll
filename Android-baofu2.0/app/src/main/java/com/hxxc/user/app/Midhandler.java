package com.hxxc.user.app;

import android.content.Context;
import android.content.Intent;

import com.hxxc.user.app.Event.ExitLoginEvent;
import com.hxxc.user.app.Event.LoginEvent;
import com.hxxc.user.app.Event.SettingEvent;
import com.hxxc.user.app.bean.FinancerEvent;
import com.hxxc.user.app.bean.FinancialPlanner;
import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.contract.i.ILogin;
import com.hxxc.user.app.listener.IFinanceCallback;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.MainActivity2;
import com.hxxc.user.app.ui.user.LoginActivity;
import com.hxxc.user.app.utils.ImUtils;
import com.hxxc.user.app.utils.ImageUtils;
import com.hxxc.user.app.utils.SPUtils;

import java.util.List;

import de.greenrobot.event.EventBus;
import rx.subscriptions.CompositeSubscription;

public class Midhandler {

    private static boolean mIsToast = true;
    private static CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public static void isToast(boolean b) {
        mIsToast = b;
    }

    public static void exitLogin(Context context) {
        ImageUtils.getInstance().clearCache();
        SPUtils.geTinstance().clearLoginCache();
        EventBus.getDefault().postSticky(new ExitLoginEvent());
        Api.uid = "";
        Api.token = "";
        Intent intent1 = new Intent(context, MainActivity2.class);
        context.startActivity(intent1);
        ImUtils.getInstance().logoutIm();
    }

    public static void exitLogin(Context context, int startType) {
        ImageUtils.getInstance().clearCache();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("startType", startType);
        context.startActivity(intent);
        EventBus.getDefault().postSticky(new ExitLoginEvent());
        SPUtils.geTinstance().clearLoginCache();
        ImUtils.getInstance().logoutIm();
    }

    public static void login(String mobile, String pwd, final ILogin login) {

        ApiManager.getInstance().login(mobile, pwd, new SimpleCallback<UserInfo>() {
            @Override
            public void onNext(UserInfo userBean) {
                login.onLoginSuccess(userBean);
            }

            @Override
            public void onError() {
                login.onLoginFailure();
            }
        });
    }


    /**
     * 登录后获取用户理财师
     */
    public static void getFinanceInfo() {
        refreshFinanceInfo(true, null);
    }

    /**
     * 刷新用户理财师
     */
    public static void refreshFinanceInfo(final IFinanceCallback callback) {
        refreshFinanceInfo(false, callback);
    }

    /**
     * 获取用户理财师
     */
    private static void refreshFinanceInfo(final boolean isLogin, final IFinanceCallback callback) {
        ApiManager.getInstance()
                .getFinancialPlannerByFid(SPUtils.geTinstance().getUid() + "", new SimpleCallback<FinancialPlanner>() {
                    @Override
                    public void onNext(FinancialPlanner financialPlanner) {
                        if (null == financialPlanner) return;

                        SPUtils.geTinstance().setFinancer(financialPlanner);
                        if (null != callback) {
                            callback.refreshFinance(financialPlanner);
                        }
                        if (isLogin) {
                            EventBus.getDefault().postSticky(new LoginEvent(LoginEvent.FINANCE_TYPE));
                        } else {
                            EventBus.getDefault().post(new FinancerEvent());
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    /**
     * 登录注册用 获取用户信息
     */
    public static void getUserInfo() {
        ApiManager.getInstance().getUserInfoByUid(SPUtils.geTinstance().getUid(), new SimpleCallback<UserInfo>() {
            @Override
            public void onNext(UserInfo userInfo) {
                if (null == userInfo) return;
                //保存手势密码到本地
                if (userInfo.getIsGesturePwd() == 1) {
                    HXXCApplication.getInstance().getLockPatternUtils().saveStringLockPattern(userInfo.getGesturePwd());
                }

                SPUtils.geTinstance().setUserInfo(userInfo);//new Gson().toJson(userInfo)
                EventBus.getDefault().postSticky(new LoginEvent(LoginEvent.USERINFO_TYPE));
            }

            @Override
            public void onError() {

            }
        });
    }

    /**
     * 刷新用 获取用户信息
     */
    public static void getUserInfo(OnGetUserInfo listener) {
        ApiManager.getInstance().getUserInfoByUid(SPUtils.geTinstance().getUid(), new SimpleCallback<UserInfo>() {
            @Override
            public void onNext(UserInfo userInfo) {
                if (null != userInfo) {
                    ImUtils.getInstance().refreshUserInfoCache(userInfo);
                    SPUtils.geTinstance().setUserInfo(userInfo);
                    EventBus.getDefault().post(new SettingEvent());
                }
                if (null != listener) {
                    listener.onNext(userInfo);
                }
            }

            @Override
            public void onError() {
                if (null != listener) {
                    listener.onNext(null);
                }
            }
        });
    }

    public static void getAds(int type, OnGetAds listener) {
        ApiManager.getInstance().getIndexAdsList(type + "", "2", "1", "5", new SimpleCallback<List<IndexAds>>() {
            @Override
            public void onNext(final List<IndexAds> indexAdses) {
                if (null != listener) {
                    listener.onNext(indexAdses);
                }
            }

            @Override
            public void onError() {
                if (null != listener) {
                    listener.onNext(null);
                }
            }
        });
    }

    public static void unSubscripe() {
        mCompositeSubscription.unsubscribe();
    }

    public static interface OnGetUserInfo {
        void onNext(UserInfo userInfo);
    }

    public static interface OnGetAds {
        void onNext(List<IndexAds> indexAdses);
    }
}
