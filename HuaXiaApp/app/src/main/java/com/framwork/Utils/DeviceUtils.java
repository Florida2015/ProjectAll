package com.framwork.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * Created by houwen.lai on 2015/1/13.
 */
public class DeviceUtils {


    public static void getDeviceInfo(Activity mContext) {
        TelephonyManager tmanager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String uuid = tmanager.getDeviceId();// 序列号
        if (TextUtils.isEmpty(uuid)) {
            uuid = "unknow";
        }
        // String uuid=tmanager.getSimSerialNumber();//序列号
        String device = Build.MODEL;// 手机型号
        if (TextUtils.isEmpty(device)) {
            device = "unknow";
        }
        String os = "android";// 操作系统
        String osv = Build.VERSION.RELEASE;// 操作系统版本
        if (TextUtils.isEmpty(osv)) {
            osv = "unknow";
        }
        DisplayMetrics dm = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);
        String dip = dm.widthPixels + "x" + dm.heightPixels;// 分辨率
        String net = NetWorkUtils.getNetWorkType(mContext);// 网络类型
        String params = "uuid=" + uuid + "&device=" + device + "&os=" + os + "&osv=" + osv + "&dpi=" + dip + "&c="
                + net;
        String deviceInfo = uuid + "&device=" + device + "&os=" + os + "&osv=" + osv + "&dpi=" + dip + "&net=" + net;
        //
//        UnderLibraryInit.getUnderLibraryInit().setDevice(params);

    }

}
