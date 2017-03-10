package com.hxxc.user.app.listener;

import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.Event.UnreadMessageEvent;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;

/**
 * 接收未读消息的监听器。
 */
public class MyReceiveUnreadCountChangedListener implements RongIM.OnReceiveUnreadCountChangedListener {

    /**
     * @param count           未读消息数。
     */
    @Override
    public void onMessageIncreased(int count) {
        EventBus.getDefault().postSticky(new UnreadMessageEvent(count));
    }
}
