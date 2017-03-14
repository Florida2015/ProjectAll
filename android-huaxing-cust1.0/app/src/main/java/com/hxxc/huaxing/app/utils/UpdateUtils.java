package com.hxxc.huaxing.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.hxxc.huaxing.app.HXXCApplication;
import com.skywds.android.bsdiffpatch.JniApi;

import java.io.File;

/**
 * 增量更新工具
 * Created by chenqun on 2016/10/24.
 */

public class UpdateUtils {
    public static void update(Activity activity) {
        String sourceDir = HXXCApplication.getInstance().getApplicationInfo().sourceDir;
        String packageName = HXXCApplication.getInstance().getPackageName();
        String localApkPath = "/data/app/" + packageName + "-1.apk";
        String localApkPath2 = "/data/app/" + packageName + "-2.apk";
        String localApkPath3 = "/data/app/" + packageName + "-3.apk";

        File file = new File(sourceDir);
        if (!file.exists()) {
            file = new File(localApkPath);
        }
        if (!file.exists()) {
            file = new File(localApkPath2);
        }
        if (!file.exists()) {
            file = new File(localApkPath3);
        }

        if (file.exists() && file.canRead()) {
            Toast.makeText(HXXCApplication.getInstance().getApplicationContext(), "文件可以读取", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HXXCApplication.getInstance().getApplicationContext(), "文件不存在或 文件无法读取", Toast.LENGTH_SHORT).show();
            return;
        }
        //旧版apk存放文件
        String oldFile = file.getAbsolutePath();

        //新版本apk存放文件
        String newFile = StorageUtils.getApkpath();
        //创建新版本apk所在文件夹
        new File(newFile).getParentFile().mkdirs();
        //将增量更新patch文件放在   /mnt/sdcard/bsdiff/update.patch 这里
        String updateFile = StorageUtils.getApkPatchPath();

        File patchFile = new File(updateFile);
        if (!patchFile.exists()) {
            toast("增量更新文件不存在");
            return;
        }

        //增量更新生成新版apk安装包
        int bspatch = JniApi.bspatch(oldFile, newFile, updateFile);

        toast("增量更新文件处理完成:" + bspatch);

        if (bspatch == 0) {
            toast("补丁包处理完毕, 开始安装新版本");
        } else {
            toast("补丁包处理失败");
        }

        //安装新版apk文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(newFile)),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private static void toast(String string) {
        Toast.makeText(HXXCApplication.getInstance().getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}
