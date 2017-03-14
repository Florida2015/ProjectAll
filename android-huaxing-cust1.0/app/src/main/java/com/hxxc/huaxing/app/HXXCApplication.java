package com.hxxc.huaxing.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.BuildConfig;

import com.google.gson.Gson;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.umeng.analytics.MobclickAgent;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * Created by Administrator on 2016/9/21.
 *
 * 配置 debug模式
 * 环境配置
 *
 */
public class HXXCApplication extends Application {

    private static HXXCApplication _instance;

    private static Context mContext;

    public static HXXCApplication getInstance() {
        return _instance;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CustomActivityOnCrash.install(this);

        //umeng 5.0兼容以下系统
        MultiDex.install(this);//

        //友盟统计
        MobclickAgent.setCatchUncaughtExceptions(!BuildConfig.DEBUG);//umeng 统计错误

        mContext = getApplicationContext();
        _instance = this;

        Api.token= SharedPreUtils.getInstanse().getToken();
//        LogUtil.d("token="+token);
        Api.uid=SharedPreUtils.getInstanse().getUid();
//        LogUtil.d("uid="+uid);
    }

    /**
     * FinancialPlanner financialPlanner = HXXCApplication.getInstance().getGson().fromJson(t, new TypeToken<FinancialPlanner>() {}.getType());
     *
     * 对象 转化 字符串
     * String string = HXXCApplication.getInstance().getGson().toJson(historyList)
     */
    private Gson gson;
    public Gson getGson(){
        if (gson == null) return new Gson();
        else return gson;
    }

}
