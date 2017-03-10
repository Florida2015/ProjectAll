package com.hxxc.user.app.Event;

import com.hxxc.user.app.bean.Account;
import com.hxxc.user.app.bean.RedPackagePayBean;

/**
 * Created by chenqun on 2016/11/7.
 */

public class PayEvent {
    public static  final  int TYPE_REFLUSH_ACCOUNT = 32;
    public static  final  int TYPE_RED = 33;
    public static  final  int TYPE_Account = 34;
    public int type = TYPE_RED;

    public RedPackagePayBean redPackageItemBean  = null;
    public Account account = null;
    public PayEvent(int type) {
        this.type = type;
    }
}
