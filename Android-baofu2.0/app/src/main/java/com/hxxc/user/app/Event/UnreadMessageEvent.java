package com.hxxc.user.app.Event;

/**
 * Created by chenqun on 2016/7/8.
 */
public class UnreadMessageEvent {
    public int unread = 0;
    public String message ;

    public UnreadMessageEvent(int unread) {
        this.unread = unread;
    }

    public UnreadMessageEvent setMessage(String message){
        this.message = message;
        return this;
    }
}
