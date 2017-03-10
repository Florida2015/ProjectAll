package com.hxxc.user.app.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hxxc.user.app.ActivityList;
import com.hxxc.user.app.Event.IMPushEvent;
import com.hxxc.user.app.ui.MainActivity2;
import com.hxxc.user.app.ui.SplashActivity;

import de.greenrobot.event.EventBus;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by chenqun on 2016/7/12.
 */
public class MyNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
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
        EventBus.getDefault().postSticky(new IMPushEvent());
        return true;
    }

}
