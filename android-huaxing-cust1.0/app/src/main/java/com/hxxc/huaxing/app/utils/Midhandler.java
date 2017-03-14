package com.hxxc.huaxing.app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Window;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.retrofit.Api;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Midhandler {

	
	public static void exitLogin(Context context){
//		SharedPreUtils.getInstanse().putKeyValue(context, UserInfoConfig.FINANCIAL,"");
		SharedPreUtils.getInstanse().putKeyValue(context, UserInfoConfig.spToken,"");
		SharedPreUtils.getInstanse().putKeyValue(context, UserInfoConfig.spUid,"");

		Api.token="";
//		HXXCApplication.getInstance().mPushAgent.disable();
//		Intent intent = new Intent(context,LoginActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//		context.startActivity(intent);
//		AppManager.getAppManager().finishAllActivity();

	}
	
//	/**
//     * 只支持MIUI V6
//     * @param context
//     * @param type 0--只需要状态栏透明 1-状态栏透明且黑色字体 2-清除黑色字体
//     */
//    public static void setStatusBarTextColor(Activity context,int type){
//        Window window = context.getWindow();
//        Class clazz = window.getClass();
//        try {
//            int tranceFlag = 0;
//            int darkModeFlag = 0;
//            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
//            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
//            tranceFlag = field.getInt(layoutParams);
//            field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
//            darkModeFlag = field.getInt(layoutParams);
//            java.lang.reflect.Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
//            if (type == 0){
//                extraFlagField.invoke(window, tranceFlag, tranceFlag);//只需要状态栏透明
//            }else if(type == 1){
////                extraFlagField.invoke(window, tranceFlag | darkModeFlag, tranceFlag | darkModeFlag);//状态栏透明且黑色字体
//            	  extraFlagField.invoke(window, darkModeFlag,  darkModeFlag);//状态栏透明且黑色字体
//            }else {
//                extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
//            }
//        }catch (Exception e){
//
//        }
//    }
	
    public static void setStatusBarBackBg(Activity context){
   	 try {
   		 Window window = context.getWindow();
			 Class clazz = window.getClass();
			 Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
			 Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
			 int tranceFlag = field.getInt(layoutParams);
			 field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			 int  darkModeFlag = field.getInt(layoutParams);
			 java.lang.reflect.Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
			 extraFlagField.invoke(window, darkModeFlag,  darkModeFlag);//状态栏透明且黑色字体
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
		}
   }
    
	
    public static void setStatusBarClearBg(Activity context){
   	 try {
   		 Window window = context.getWindow();
			 Class clazz = window.getClass();
			 Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
			 Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
			 int tranceFlag = field.getInt(layoutParams);
			 field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			 int  darkModeFlag = field.getInt(layoutParams);
			 java.lang.reflect.Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
			 extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
		}
   }
}
