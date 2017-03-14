package com.hxxc.huaxing.app.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.hxxc.huaxing.app.HXXCApplication;

import java.util.Date;

public class ToastUtil {


    public static void ToastLong(Context context, String text) {
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public static void ToastShort(Context context, String text) {
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void ToastShort(Context context, int resId) {
        Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showSafeToast(final Activity activity, final String msg) {
        if (Thread.currentThread().getName().equals("main")) {
            Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(activity.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
/******************************************************************************/

    public static volatile ToastUtil mToastUtils;

    public static ToastUtil getInstance() {
        ToastUtil toast = mToastUtils;
        if(null == toast){
            synchronized (ToastUtil.class){
                toast = mToastUtils;
                if(null == toast){
                    toast = new ToastUtil();
                    mToastUtils = toast;
                }
            }
        }
        return toast;
    }

    private long time = 0;
    private final long intervalTime = 150;//吐司的间隔时间

    public void ToastShortFromNet(String text) {
        long newTime = new Date().getTime();
        if (0 == time) {
            time = newTime;
            Toast.makeText(HXXCApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
        } else {
            long l = newTime - time;
            LogUtil.e("TIME**********************************=="+l);
            if (l >= intervalTime) {
                Toast.makeText(HXXCApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
                time = newTime;
            }
        }
    }
}
