package com.framwork.Utils;

import android.util.Log;

import com.huaxia.finance.BuildConfig;


public class MyLog {

    //TODO
	public static boolean isDeBug= BuildConfig.LOG_DEBUG;

	public static String TAG = "MyLog";

	public static final void d(String tag, String msg){
		if (isDeBug) {
			Log.d(tag, msg);
		}
	}

	public static final void d(String msg){
		if (isDeBug) {
			Log.d(TAG, msg);
		}
	}
	
	public static final void d(String tag, String msg,Throwable tr){
		if (isDeBug) {
			MyLog.d(tag, msg,tr);
		}
	}

}
