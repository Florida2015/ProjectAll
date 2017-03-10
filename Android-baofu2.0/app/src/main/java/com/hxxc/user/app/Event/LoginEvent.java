package com.hxxc.user.app.Event;

/**
 * Created by chenqun on 2016/7/11.
 */
public class LoginEvent {
    public static int USERINFO_TYPE = 0;
    public static int FINANCE_TYPE = 1;

    public int type = USERINFO_TYPE;

    public LoginEvent(int type) {
        this.type = type;
    }
}
