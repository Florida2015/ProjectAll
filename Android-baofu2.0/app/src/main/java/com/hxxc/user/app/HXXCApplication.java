package com.hxxc.user.app;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.hxxc.user.app.Event.PushEvent;
import com.hxxc.user.app.ui.MainActivity2;
import com.hxxc.user.app.ui.SplashActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.ImUtils;
import com.hxxc.user.app.utils.ImageUtils;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.widget.lockpatternview.LockPatternUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONObject;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;

/**
 * aa8a531c36f398b3cd0606039e8ec3b4132
 * Created by chenqun on 2016/6/21.
 */
public class HXXCApplication extends Application {

    private static HXXCApplication mInstance;
    private LockPatternUtils mLockPatternUtils;

    public static HXXCApplication getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private volatile boolean mBackground = true;

    public boolean getIsInBackground() {
        return mBackground;
    }

    public void setIsInBackground(boolean b) {
        mBackground = b;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            mBackground = true;
        }
        if (level >= TRIM_MEMORY_UI_HIDDEN) {
            ImageUtils.getInstance().clearMemoryCache();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);


        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(CommonUtil.getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(CommonUtil.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
//            RongPushClient.registerMiPush(this, "", "");
            RongIM.init(this);
            ImUtils.getInstance().changePrivateUI();//修改回话界面ui
        }

        mInstance = this;

        init();
//        if (getApplicationInfo().packageName.equals(CommonUtil.getCurProcessName(getApplicationContext())))
//            EventBus.getDefault().register(this);
    }

    private void init() {

        CustomActivityOnCrash.install(this);

        UMShareAPI.get(this);
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = false;
        //友盟分享配置
        initUmengSocial();

        pushInit();
        initImageloader(getApplicationContext());
        initBugly();
        listenForScreenTurningOff();

//        EventBus.getDefault().post(new SplashEvent());
    }

    /**
     * 友盟分享配置
     */
    public void initUmengSocial() {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx8d2bdc995e638769", "af024a50df0216731c4c1fc59b683af6");
        //新浪微博
//        PlatformConfig.setSinaWeibo("888007131", "e1b408630292c08b9cd7c72d1e9ff4c2");
//        PlatformConfig.setQQZone("1105151719", "UnQXEUB4MgdA5hrV");
    }

    private void listenForScreenTurningOff() {
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mBackground = true;
                ImageUtils.getInstance().clearMemoryCache();
            }
        }, screenStateFilter);
    }

    private void initBugly() {
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        //...在这里设置strategy的属性，在bugly初始化时传入
        CrashReport.initCrashReport(getApplicationContext(), "900028024", false);
    }

    private void initImageloader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)

                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(100) //缓存的文件数量
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 推送初始化
     */
    private void pushInit() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDisplayNotificationNumber(5);//設置通知欄消息最多顯示3條
        //TODO
        mPushAgent.setDebugMode(false);
        UmengMessageHandler messageHandler = new UmengMessageHandler() {

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
                        if (isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                    }
                });
            }

            /**
             * 参考集成文档的1.6.4
             * http://dev.umeng.com/push/android/integration#1_6_4
             * */
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                msg.title = "华夏信财";
                msg.ticker = msg.text;
                switch (msg.builder_id) {
                    case 0:
                        Notification.Builder builder = new Notification.Builder(context);
                        RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
//			        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
                        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//			        myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
//                        myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
                        builder.setContent(myNotificationView)
//			        	   .setLargeIcon(getLargeIcon(context, msg))
                                .setSmallIcon(getSmallIconId(context, msg))
                                .setTicker(msg.ticker)
                                .setAutoCancel(true);

                        return builder.getNotification();
//                    case -2://被踢下线
//                        EventBusUtils.getInstance().kickOff();
//                        return null;
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
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Activity mainActivity = ActivityList.getMainActivity(MainActivity2.class.toString());
                if (null == mainActivity) {
                    Intent intent = new Intent(context, SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, MainActivity2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

//                launchApp(context, msg);//启动app
                LogUtils.e("pushMsg==" + msg.toString() + "////mainActivity==" + mainActivity);
                String json = msg.custom;
                try {
                    JSONObject parse = new JSONObject(json);
                    String page = parse.getString("page");
                    String data = parse.getString("data");
                    EventBus.getDefault().postSticky(new PushEvent(page, data));
                } catch (Exception e) {
                    LogUtils.e("pushBug==" + e.getMessage());
                }
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                LogUtils.e("deviceToken==" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.e("PushAgentRegisterFailure==" + s);
            }
        });
    }

    public void initLockPatternUtils() {
        mLockPatternUtils = new LockPatternUtils(this);
    }

    public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }


    /**
     * FinancialPlanner financialPlanner = HXXCApplication.getInstance().getGson().fromJson(t, new TypeToken<FinancialPlanner>() {}.getType());
     * <p>
     * 对象 转化 字符串
     * String string = HXXCApplication.getInstance().getGson().toJson(historyList)
     */
    private Gson gson;

    public Gson getGson() {
        if (gson == null) return new Gson();
        else return gson;
    }

//    public void onEvent(AppEvent event) {
//        init();
//    }
}
