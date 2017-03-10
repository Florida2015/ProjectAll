package com.hxxc.user.app.Event;

public class MainEvent {

    public static final int FROM_MINE_PAGER = 4444;
    public static final int FROMFINDPASSWORD = 1111;
    public static final int BACK = 2222;
    public static final int STATUS_BAR = 3333;
    public static final int UNREAD_MESSAGE = 5555;
    public static final int IM_CONNECT = 6666;

    public int item;

    public int loginType = 0;

    public String to = "";

    public MainEvent(int item) {
        super();
        this.item = item;
    }

    public MainEvent setLoginType(int a) {
        loginType = a;
        return this;
    }

    public float alpha;
    public MainEvent(float alpha) {
        super();
        this.alpha = alpha;
    }
}
