package com.hxxc.huaxing.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.utils.ApplicationUtils;
import com.hxxc.huaxing.app.utils.FileUtils;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.lzy.okhttpserver.download.DownloadInfo;
import com.lzy.okhttpserver.download.DownloadManager;
import com.lzy.okhttpserver.download.DownloadService;
import com.lzy.okhttpserver.listener.DownloadListener;


/**
 * Created by houwen.lai on 2016/7/13.
 *
 */
public class DownAppService extends Service {

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    private String title = "财划算";
    private String name;
    protected static final String TAG = "api_DownloadService";

    private DownloadManager downloadManager;
    private DownloadInfo downloadInfo;

    @Override
    public void onDestroy() {
        super.onDestroy();
//记得移除，否者会回调多次
        downloadManager.removeAllTask();
    }

    //通过binder实现调用者client与Service之间的通信
    private ServiceBinder binder = new ServiceBinder();
    //此方法是为了可以在Acitity中获得服务的实例
    public class ServiceBinder extends Binder {
        public DownAppService getService() {
            return DownAppService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogUtil.d("api_down_url__onStartCommand");
        if (intent!=null){
            downloadManager = DownloadService.getDownloadManager(this);
            downloadManager.setTargetFolder(UserInfoConfig.getDownloadFilePath_d());

            UserInfoConfig.UPDATE_URL  = intent.getStringExtra("url");
            name = intent.getStringExtra("versionName");
            LogUtil.d("api_down_url=" + UserInfoConfig.UPDATE_URL +"_name="+name);
            name = "huaxiafinance_"+name+".apk";
            int index = UserInfoConfig.UPDATE_URL.lastIndexOf("/");

            if (index==0)return super.onStartCommand(intent, flags, startId);

            name = UserInfoConfig.UPDATE_URL.substring(index);

            downloadInfo = downloadManager.getTaskByUrl(UserInfoConfig.UPDATE_URL);
            if (downloadInfo != null) {
                //如果任务存在，把任务的监听换成当前页面需要的监听
                downloadInfo.setListener(downloadListener);
            }

            initView();//FileUtils.getUrlFileName(DMConstant.UPDATE_URL)

           if (FileUtils.isFileNameExist(UserInfoConfig.getDownloadFilePath_d(),name)) {
                LogUtil.d("apk 已下载 path="+UserInfoConfig.getDownloadFilePath_d()+name);
               FileUtils.getInstance().del(UserInfoConfig.getDownloadFilePath_d());
               mBuilder.setContentText(title+"下载完成后");
               mNotificationManager.notify(1,mBuilder.build());
               mNotificationManager.cancel(1);
               installApk();
               if (downloadInfo!=null) downloadManager.removeTask(downloadInfo.getUrl());
               downloadManager.addTask(UserInfoConfig.UPDATE_URL, downloadListener);
            }else  {
                LogUtil.d("apk 下载中....");
                downloadManager.addTask(UserInfoConfig.UPDATE_URL, downloadListener);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    DownloadListener downloadListener = new DownloadListener() {
        @Override
        public void onProgress(DownloadInfo downloadInfo) {
            mBuilder.setProgress((int)downloadInfo.getTotalLength(), (int)downloadInfo.getDownloadLength(), false);
            mNotificationManager.notify(1,mBuilder.build());
        }

        @Override
        public void onFinish(DownloadInfo downloadInfo) {
            mBuilder.setContentText(title+"下载完成后");
            mNotificationManager.notify(1,mBuilder.build());
            mNotificationManager.cancel(1);
            installApk();
        }

        @Override
        public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
            LogUtil.d("apk down error ="+errorMsg +"_state="+downloadInfo.getState());
            if (downloadInfo!=null&&downloadInfo.getState()==DownloadManager.FINISH){
                mNotificationManager.cancel(1);
                installApk();
            }else if(downloadInfo.getState()==5||downloadInfo.getState()==4){
                if (FileUtils.isFileNameExist(UserInfoConfig.getDownloadFilePath_d(),name)) {
                    LogUtil.d("apk 已下载 path="+UserInfoConfig.getDownloadFilePath_d()+name);
                    FileUtils.getInstance().del(UserInfoConfig.getDownloadFilePath_d());
//                    if (downloadInfo!=null) downloadManager.removeTask(downloadInfo.getUrl());
//                    downloadManager.addTask(UserInfoConfig.UPDATE_URL, downloadListener);
                }else  {
                    LogUtil.d("apk 下载中....");
//                    if (downloadInfo!=null) downloadManager.removeTask(downloadInfo.getUrl());
//                    downloadManager.addTask(UserInfoConfig.UPDATE_URL, downloadListener);
                }
            }else showNotification();
        }
    };

    /**
     * The notification is the icon and associated expanded entry in the status
     * bar.
     */
    protected void showNotification() {
        CharSequence from = "财划算更新失败";
        CharSequence message = "下载超时，请稍后重试！";
        mBuilder.setContentTitle(from);
        mBuilder.setContentText(message);
        mNotificationManager.notify(1,mBuilder.build());
    }

    protected void initView() {
        CharSequence noticeText = "财划算下载更新中...";
        mBuilder = new NotificationCompat.Builder(this);
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);// 设置通知栏点击意图
        mBuilder.setContentTitle(title);// 设置通知栏标题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//悬挂式Notification，5.0后显示
            mBuilder.setContentText(noticeText).setFullScreenIntent(pendingIntent, true);
            mBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
            mBuilder.setSmallIcon(R.mipmap.icon_logo);// 设置通知小ICON（5.0必须采用白色透明图片）
        }else{
            mBuilder.setSmallIcon(R.mipmap.icon_logo);// 设置通知小ICON
            mBuilder.setContentText(noticeText);
        }
//        mBuilder.setProgress(100,16,false);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_logo));// 设置通知大ICON
        mBuilder.setTicker(noticeText); // 通知首次出现在通知栏，带上升动画效果的
        mBuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX); // 设置该通知优先级
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);//在任何情况下都显示，不受锁屏影响。
        mBuilder.setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消
//		mBuilder.setDefaults(NotificationCompat.FLAG_AUTO_CANCEL);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();//API 16
        mNotificationManager.notify(1, notification);

    }

    public void installApk() {
        ApplicationUtils.installApk(this, UserInfoConfig.getDownloadFilePath_d()+name);
        onDestroy();
    }
}
