package com.hxxc.user.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.UserInfoConfig;

/**
 * Created by Administrator on 2016/9/21.
 * 功能：配置参数保存
 */
public class SharedPreUtils {

    private static String Preferences_TAG = SharedPreUtils.class
            .getSimpleName();
    private static SharedPreUtils preferencesUtils = null;

    public static SharedPreUtils getInstanse() {
        if (preferencesUtils == null) {
            preferencesUtils = new SharedPreUtils();
        }
        return preferencesUtils;
    }
    /**
     *
     * @param context
     *
     */
    public void putKeyValue(Context context, String key, String value) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public void putKeyValue(Context context, String key,int value) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }
    public void putKeyValue(Context context, String key,boolean value) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public String getKeyValue(Context context,String key) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        return sharedPreferences.getString(key,null);
    }

    public String getUid(){
        return getKeyValue(HXXCApplication.getInstance(), UserInfoConfig.spUid);
    }
    public String getToken(){
        return getKeyValue(HXXCApplication.getInstance(),UserInfoConfig.spToken);
    }

//    public UserInfoBean getUserInfo(Context context){
//        String value = getKeyValue(context.getApplicationContext(), UserInfoConfig.UserInfo);
//        LogUtil.i(value);
//        if(TextUtils.isEmpty(value)) return null;
//        BaseBean<UserInfoBean> baseBean = HXXCApplication.getInstance().getGson().fromJson(value,new TypeToken<BaseBean<UserInfoBean>>() {}.getType());
//        return baseBean.getModel();
//    }

    public String getKeyValue(Context context,String key,String def) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        return sharedPreferences.getString(key,def);
    }
    public boolean getKeyValue_b(Context context,String key) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        return sharedPreferences.getBoolean(key,false);
    }
    public int getKeyValue_i(Context context,String key) {
        if (context == null) {
            return -1;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        return sharedPreferences.getInt(key,-1);
    }

    /**
     * 移除key
     * @param context
     * @param key
     */
    public void removeKeyValue(Context context,String key) {
        if (context == null) {
            return ;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
