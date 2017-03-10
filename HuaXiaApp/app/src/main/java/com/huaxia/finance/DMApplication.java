package com.huaxia.finance;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.framwork.Utils.ApplicationUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.asychttpclient.AsyncHttpClient;
import com.framwork.asychttpclient.PersistentCookieStore;
import com.framwork.lazylist.ImageLoader;
import com.google.gson.Gson;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.umengpush.CustomNotificationHandler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengRegistrar;
import com.umeng.message.entity.UMessage;

import java.util.Map;

/**
 * Created by houwen.lai on 2015/11/17.
 */
public class DMApplication extends Application {

    private static final String TAG = "DMApplication";
    private static DMApplication instance;

    private ImageLoader loader;

    public static boolean isLoginFlag = false;//是否登录

    public static String cookieslogin ="";//登录cookie
    public static String token ="";//登录token

    public boolean isNoticeFlag = false;//是否显示通知

    public static Map<String,String> devicedmessage;//设备信息

    private Gson gson;
    public Gson getGson(){
        if (gson == null) return new Gson();
        else return gson;
    }

    private AsyncHttpClient httpClient;

    public AsyncHttpClient getHttpClient(Activity activity){
        if (httpClient==null)httpClient=new AsyncHttpClient();
        PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        //添加自己的cookie
//        BasicClientCookie newCookie = new BasicClientCookie("cookiesare", "awesome");
//        newCookie.setVersion(1);
//        newCookie.setDomain("mydomain.com");
//        newCookie.setPath("/");
//        cookieStore.addCookie(newCookie);
        httpClient.setCookieStore(cookieStore);
        Map<String ,String> deviceinfo = ApplicationUtils.getDeviceInfo(activity);
        if (deviceinfo!=null) {
            httpClient.addHeader("uuid", TextUtils.isEmpty(deviceinfo.get("uuid")) ? "null" : deviceinfo.get("uuid"));
            httpClient.addHeader("device", TextUtils.isEmpty(deviceinfo.get("device")) ? "null" : deviceinfo.get("device"));
            httpClient.addHeader("os", TextUtils.isEmpty(deviceinfo.get("os")) ? "null" : deviceinfo.get("os"));
            httpClient.addHeader("osv", TextUtils.isEmpty(deviceinfo.get("osv")) ? "null" : deviceinfo.get("osv"));
            httpClient.addHeader("dpi", TextUtils.isEmpty(deviceinfo.get("dpi")) ? "null" : deviceinfo.get("dpi"));
            httpClient.addHeader("net", TextUtils.isEmpty(deviceinfo.get("net")) ? "null" : deviceinfo.get("net"));
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String msg=appInfo.metaData.getString("UMENG_CHANNEL");
        httpClient.addHeader("source", TextUtils.isEmpty(msg)?"null":msg);//
        httpClient.addHeader("sourceType","2");// 1->ios， 2 -> android , 3 -> h5 , 4 -> web
        httpClient.addHeader("token", SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_token));
        MyLog.d("api_request_token=" + SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_token));
        httpClient.addHeader("device_token",device_token);//device_token
        return httpClient;
    }

    public static DMApplication getInstance() {
        return instance;
    }

    public ImageLoader getImageLoader() {
        if (loader == null) {
            loader = new ImageLoader(this);
        }
        return loader;
    }

    private PushAgent mPushAgent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MobclickAgent.setCatchUncaughtExceptions(true);//umeng 统计错误

        mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(true);
        mPushAgent.enable();
        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            /**
             * 参考集成文档的1.6.3
             * http://dev.umeng.com/push/android/integration#1_6_3
             * */
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler().post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        // 对自定义消息的处理方式，点击或者忽略
                        boolean isClickOrDismissed = true;
                        if(isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
//                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }

            /**
             * 参考集成文档的1.6.4
             * http://dev.umeng.com/push/android/integration#1_6_4
             * */
            @Override
            public Notification getNotification(Context context,
                                                UMessage msg) {
                MyLog.d("push_msg_getNotification="+msg.getRaw().toString());
                switch (msg.builder_id) {
                    case 1:
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
                        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView);
                        builder.setContentTitle(msg.title)
                                .setContentText(msg.text)
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);
                        Notification mNotification = builder.build();
                        //由于Android v4包的bug，在2.3及以下系统，Builder创建出来的Notification，并没有设置RemoteView，故需要添加此代码
                        mNotification.contentView = myNotificationView;
                        return mNotification;
                    default:
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
        /**
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * 参考集成文档的1.6.2
         * http://dev.umeng.com/push/android/integration#1_6_2
         * */
//        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){
//            @Override
//            public void dealWithCustomAction(Context context, UMessage msg) {
//
//                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
//            }
//        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知
        //参考http://bbs.umeng.com/thread-11112-1-1.html
        CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        device_token = UmengRegistrar.getRegistrationId(this);
        MyLog.d("push_device_token="+device_token);
//		if (MiPushRegistar.checkDevice(this)) {
//            MiPushRegistar.register(this, "2882303761517400865", "5501740053865");
//		}

    }

    String device_token;

}
