package com.hxxc.user.app.utils;

import android.content.ClipData;
import android.content.Context;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by houwen.lai on 2016/11/29.
 * 复制文字
 *
 */

public class ShearPlateUtils {

    public interface OnClickFinish{
        void onResult(boolean f);
    }


    public static void ShearPlateCope(Context context,String text,OnClickFinish onClickFinish){
        if(getSDKVersionNumber() >= 11){
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(text);
        }else{
            // 得到剪贴板管理器
            android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
        }
        Toast.makeText(context,"已复制到粘贴板",Toast.LENGTH_SHORT).show();
        onClickFinish.onResult(true);
    }

    /**
     * 获取手机操作系统版本
     * @return
     * @author SHANHY
     * @date   2015年12月4日
     */
    public static int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }


}
