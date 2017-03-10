package com.hxxc.user.app.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.hxxc.user.app.Event.KICKOUTEvent;
import com.hxxc.user.app.bean.FinancialPlanner;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.listener.MyConversationBehaviorListener;
import com.hxxc.user.app.listener.MyReceiveMessageListener;
import com.hxxc.user.app.listener.MyReceiveUnreadCountChangedListener;
import com.hxxc.user.app.ui.im.AMAPLocationActivity;
import com.hxxc.user.app.ui.im.MyTextMessageItemProvider;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by chenqun on 2016/7/5.
 */
public class ImUtils implements RongIM.LocationProvider {
    private static volatile ImUtils utils;

    public static ImUtils getInstance() {
        ImUtils util = utils;
        if (null == util) {
            synchronized (ImUtils.class) {
                util = utils;
                if (null == util) {
                    util = new ImUtils();
                    utils = util;
                }
            }
        }
        return util;
    }

    public void startPrivateChat(Context context) {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPrivateChat(context, SPUtils.geTinstance().getFinancer().getFinancialno(), "");
        }
    }

    //设置单点登录
    public void setImConnectionListener() {
        resetInputProvider();

        /**
         * 设置连接状态变化的监听器.
         */
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {

            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus) {
                    case CONNECTED://连接成功。
                        break;
                    case DISCONNECTED://断开连接。
                        break;
                    case CONNECTING://连接中。
                        break;
                    case NETWORK_UNAVAILABLE://网络不可用。
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                        EventBusUtils.getInstance().kickOff(KICKOUTEvent.FROM_IM);
                        LogUtils.e("From--IM被踢下线,请重新登录");
                        break;
                }
            }
        });
        setOnReceiveMessageListener();
    }

    public void setOnReceiveMessageListener() {
        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
    }

    public void setUserInfoProvider() {
        setUserInfoProvider2();

        RongIM.setLocationProvider(this);//设置地理位置提供者,
        RongIM.setConversationBehaviorListener(new MyConversationBehaviorListener());//设置会话界面点击事件
    }

    public void setUserInfoProvider2() {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public io.rong.imlib.model.UserInfo getUserInfo(final String userId) {
                UserInfo bean = SPUtils.geTinstance().getUserInfo();
                FinancialPlanner financialPlanner = SPUtils.geTinstance().getFinancer();
                LogUtils.e("retrunNo==" + userId + "///UserNo==" + bean.getUserNo() + "/f==" + financialPlanner.getFinancialno());
                if (userId.equals(bean.getUserNo())) {
                    String name = bean.getLegalName();
                    if (TextUtils.isEmpty(name)) {
                        name = bean.getMobile();
                    }
                    return new io.rong.imlib.model.UserInfo(userId, name, Uri.parse(bean.getRealIcon() + ""));//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
                } else {
                    String name = financialPlanner.getFname();
                    if (TextUtils.isEmpty(name)) {
                        name = financialPlanner.getUsername();
                    }
                    LogUtils.e("FinancerNo==" + financialPlanner.getFinancialno() + "///FinancerName==" + name + "///url==" + financialPlanner.getRealIcon());
                    return new io.rong.imlib.model.UserInfo(financialPlanner.getFinancialno(), name, Uri.parse(financialPlanner.getRealIcon() + ""));
                }
            }
        }, true);
    }

    public void logoutIm() {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().logout();
        }
    }

    public void onImDisconnection() {
        if (RongIM.getInstance() != null) {
            LogUtils.e("onImDisconnection--------------------");
            RongIM.getInstance().disconnect();
        }

    }

    public void resetInputProvider() {
        //扩展功能自定义
        InputProvider.ExtendProvider[] provider = {
                new ImageInputProvider(RongContext.getInstance()),//图片
                new CameraInputProvider(RongContext.getInstance())//相机
                , new LocationInputProvider(RongContext.getInstance())//地理位置
        };
        RongIM.resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
    }

    public void refreshUserInfoCache(final UserInfo user) {
        /**
         * 刷新用户缓存数据。
         *
         * @param userInfo 需要更新的用户缓存数据。
         */
        LogUtils.e("*********************ImUserInfo-->" + user.getUserNo() + "/" + user.getLegalName() + "头像" + user.getRealIcon());
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().refreshUserInfoCache(new io.rong.imlib.model.UserInfo(user.getUserNo(), user.getLegalName(), Uri.parse(user.getRealIcon() + "")));
        }
    }

    public void refreshUserInfoCache(final FinancialPlanner user) {
        /**
         * 刷新用户缓存数据。
         *
         * @param userInfo 需要更新的用户缓存数据。
         */
        String name = user.getFname();
        if (TextUtils.isEmpty(name)) {
            name = user.getUsername();
        }
        LogUtils.e("*********************FinancialPlanner-->" + user.getFinancialno() + "/" + name + "头像" + user.getRealIcon());
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().refreshUserInfoCache(new io.rong.imlib.model.UserInfo(user.getFinancialno(), name, Uri.parse(user.getRealIcon() + "")));
        }
    }


    public void setOnReceiveUnreadCountChangedListener() {
        /**
         * 设置接收未读消息的监听器。
         *
         * @param listener          接收未读消息消息的监听器。
         * @param conversationTypes 接收指定会话类型的未读消息数。
         */
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new MyReceiveUnreadCountChangedListener(), Conversation.ConversationType.PRIVATE);
        }
    }

    public void removeOnReceiveUnreadCountChangedListener() {
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().setOnReceiveUnreadCountChangedListener(null);
        }
    }

    //以下为定位地图相关方法
    @Override
    public void onStartLocation(Context context, LocationCallback locationCallback) {
        setLastLocationCallback(locationCallback);
        Intent intent = new Intent(context, AMAPLocationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private RongIM.LocationProvider.LocationCallback mLastLocationCallback;

    public RongIM.LocationProvider.LocationCallback getLastLocationCallback() {
        return mLastLocationCallback;
    }

    public void setLastLocationCallback(RongIM.LocationProvider.LocationCallback lastLocationCallback) {
        this.mLastLocationCallback = lastLocationCallback;
    }

    public void changePrivateUI() {
        RongIM.registerMessageTemplate(new MyTextMessageItemProvider());//修改回话界面ui
    }
}
