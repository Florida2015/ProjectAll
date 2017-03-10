package com.framwork.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 功能：配置参数保存
 * Created by houwen.lai on 2015/11/19.
 *
 */
public class SharedPreferencesUtils {

    private static String Preferences_TAG = SharedPreferencesUtils.class
            .getSimpleName();
    private static SharedPreferencesUtils preferencesUtils = null;

    public static SharedPreferencesUtils getInstanse() {
        if (preferencesUtils == null) {
            preferencesUtils = new SharedPreferencesUtils();
        }
        return preferencesUtils;
    }

    /**
     * 第一次使用该软件
     *
     * @param context
     */
    public void putFirstUser(Context context, boolean aim) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FirstUser", aim);
        editor.commit();
    }

    public boolean getFirstUser(Context context) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        return sharedPreferences.getBoolean("FirstUser", false);
    }

    /**
     *
     * @param context
     *
     */
    public void putKeyValue(Context context, String key,String value) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Preferences_TAG, Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
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

}
