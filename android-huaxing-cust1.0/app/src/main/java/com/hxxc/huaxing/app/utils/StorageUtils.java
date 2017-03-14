package com.hxxc.huaxing.app.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.hxxc.huaxing.app.HXXCApplication;

import java.io.File;

/**
 * Created by chenqun on 2016/7/15.
 */
public class StorageUtils {
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    private static final String SDCARDPATH = "/huaxing/pic/";
    private static final String APKPATH = "/huaxing/apk/bsdiff/";

    public static String getCachePath(Context context){
        String filePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {//手机有sd卡
            File externalCacheDir = context.getExternalCacheDir();
            if(null != externalCacheDir){
                filePath = externalCacheDir.getAbsolutePath();
            }else{
                filePath = context.getCacheDir().getAbsolutePath();
            }

        }else{
            filePath = context.getCacheDir().getAbsolutePath();
        }
        return filePath ;
    }

    private static String getStoragePath(Context context){
        String filePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)){//手机有sd卡
            File externalDir = Environment.getExternalStorageDirectory();
            if(null != externalDir){
                filePath = externalDir.getAbsolutePath();
            }else{
                filePath = context.getFilesDir().getAbsolutePath();
            }

        }else{
            filePath = context.getFilesDir().getAbsolutePath();
        }
        return filePath ;
    }

    public static String getSdcardpath(Context context){
        return getCachePath(context)+SDCARDPATH;
    }
    public static String getApkPatchPath(){
        return getStoragePath(HXXCApplication.getInstance().getApplicationContext())+APKPATH + "update.patch";
    }

    public static String getApkpath(){
        return getStoragePath(HXXCApplication.getInstance().getApplicationContext())+APKPATH + "new.apk";
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
