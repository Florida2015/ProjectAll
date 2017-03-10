package com.hxxc.user.app.Event;

/**
 * Created by chenqun on 2016/11/24.
 */

public class UnreadMessageContentEvent {
    public String message;
    public long time;

    public UnreadMessageContentEvent(String message, long time) {
        this.message = message;
        this.time = time;
    }
}
