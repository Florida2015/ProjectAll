package com.hxxc.user.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.bean.FinancialPlanner;
import com.hxxc.user.app.bean.UserInfo;

import java.util.Map;
import java.util.Set;

public class SPUtils {

    private static volatile SPUtils sharedPreferencesUtils;

    private SharedPreferences share;
    private UserInfo mUser;
    private FinancialPlanner mFinancer;

    private String mImId;//用户聊天id
    private String mUid;//用户uid
    private String mToken;//用户token
    private String mImToken;//用户聊天token

    public SPUtils(Context context) {
        init(context);
    }

    public static SPUtils geTinstance(Context context) {
        SPUtils utils = sharedPreferencesUtils;
        if (utils == null) {
            synchronized (SPUtils.class) {
                utils = sharedPreferencesUtils;
                if (null == utils) {
                    utils = new SPUtils(context);
                    sharedPreferencesUtils = utils;
                }
            }
        }
        return utils;
    }

    public static SPUtils geTinstance() {
        SPUtils utils = sharedPreferencesUtils;
        if (utils == null) {
            synchronized (SPUtils.class) {
                utils = sharedPreferencesUtils;
                if (null == utils) {
                    utils = new SPUtils(HXXCApplication.getContext());
                    sharedPreferencesUtils = utils;
                }
            }
        }
        return utils;
    }


    private void init(Context context) {
        if (null != context)
            share = context.getSharedPreferences("config", 0);
    }

    public void put(String key, String value) {
        share.edit().putString(key, value).apply();
    }

    public void put(String key, boolean value) {
        share.edit().putBoolean(key, value).apply();
    }

    public void put(String key, float value) {
        share.edit().putFloat(key, value).apply();
    }

    public void put(String key, int value) {
        share.edit().putInt(key, value).apply();
    }

    public void put(String key, long value) {
        share.edit().putLong(key, value).apply();
    }

    @SuppressLint("NewApi")
    public void put(String key, Set<String> value) {
        share.edit().putStringSet(key, value).apply();
    }


    public Map getAll() {
        return share.getAll();
    }

    public String get(String key, String defValue) {
        return share.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        return share.getInt(key, defValue);
    }

    public float get(String key, float defValue) {
        return share.getFloat(key, defValue);
    }

    public long get(String key, long defValue) {
        return share.getLong(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return share.getBoolean(key, defValue);
    }

    @SuppressLint("NewApi")
    public Set<String> get(String key, Set<String> defValues) {
        return share.getStringSet(key, defValues);
    }

    public String getUserName() {
        return share.getString(Constants.USER_NAME, "");
    }

    public void setUserName(String value) {
        share.edit().putString(Constants.USER_NAME, value).apply();
    }

    public void setPassword(String value) {
        share.edit().putString(Constants.PASSWORD, CommonUtil.md5(value)).apply();
    }

    public boolean checkPassword(String pwd) {
        String string = share.getString(Constants.PASSWORD, "");
        if (!TextUtils.isEmpty(string) && string.equals(CommonUtil.md5(pwd))) {
            return true;
        }
        return false;
    }

    public void setUserInfo(UserInfo value) {
        mUser = value;
        if (null != value) {
            share.edit().putString(Constants.USER_INFO, new Gson().toJson(value)).apply();
        } else {
            share.edit().putString(Constants.USER_INFO, "").apply();
        }
    }

    public UserInfo getUserInfo() {
        if (null == mUser) {
            String json = share.getString(Constants.USER_INFO, "");
            if (!TextUtils.isEmpty(json)) {
                return new Gson().fromJson(json, UserInfo.class);
            }
            return null;
        } else {
            return mUser;
        }
    }

    public void setFinancer(FinancialPlanner value) {
        mFinancer = value;
        if (null == value) {
            share.edit().putString(Constants.FINANCER, "").apply();
        } else {
            share.edit().putString(Constants.FINANCER, new Gson().toJson(value)).apply();
        }
    }

    public FinancialPlanner getFinancer() {
        if (null != mFinancer) {
            return mFinancer;
        }
        String json = share.getString(Constants.FINANCER, "");
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, FinancialPlanner.class);
        }
        return null;
    }

    public void setLoginCache(String uid, String token, String userName, String password) {
        setUid(uid);
        setToken(token);
        setUserName(userName);
        setPassword(password);
    }

    public void clearLoginCache() {
        mUid = null;
        mToken = null;
        mFinancer = null;
        mUser = null;
        mImId = null;
        mImToken = null;
        setUid("");
        setToken("");
//        setUserName("");
        setPassword("");
        setImToken("");
        setUserInfo(null);
        setFinancer(null);
    }

    public String getImToken() {
        if (TextUtils.isEmpty(mImToken)) {
            String uid = getUid();
            if (!TextUtils.isEmpty(uid)) {
                return get(Constants.CHATTOKEN + uid, "");
            }
            return "";
        } else {
            return mImToken;
        }
    }

    public void setImToken(String token) {
        mImToken = token;
        String uid = getUid();
        if (!TextUtils.isEmpty(uid)) {
            put(Constants.CHATTOKEN + uid, token);
        }
    }

    public String getUid() {
        if (TextUtils.isEmpty(mUid)) {
            return get(Constants.UID, "");
        } else {
            return mUid;
        }

    }

    public void setUid(String uid) {
        mUid = uid;
        put(Constants.UID, uid);
    }

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            return get(Constants.TOKEN, "");
        } else {
            return mToken;
        }
    }

    public void setToken(String token) {
        mToken = token;
        put(Constants.TOKEN, token);
    }

    public void setImId(String id) {
        mImId = id;
        String uid = getUid();
        if (!TextUtils.isEmpty(uid)) {
            put(Constants.CHATUSERID + uid, id);
        }
    }

    public String getImId() {
        if (!TextUtils.isEmpty(mImId)) {
            return mImId;
        }
        String uid = getUid();
        if (!TextUtils.isEmpty(uid)) {
            return get(Constants.CHATUSERID + uid, "");
        }
        return "";
    }
}
