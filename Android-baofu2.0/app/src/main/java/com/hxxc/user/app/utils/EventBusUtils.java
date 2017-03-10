package com.hxxc.user.app.utils;

import com.hxxc.user.app.Event.KICKOUTEvent;

import java.util.Date;

import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/7/13.
 */
public class EventBusUtils {
    private static volatile EventBusUtils utils ;

    public static EventBusUtils getInstance(){
        EventBusUtils util = utils;
        if(null == util){
            synchronized (EventBusUtils.class){
                util = utils;
                if(null == util){
                    util = new EventBusUtils();
                    utils = util;
                }
            }
        }
        return util;
    }
    private long time = 0;
    private final long intervalTime = 1000*2;//2秒的周期
    public void kickOff(int a){
        long newTime = new Date().getTime();
        if (0 == time) {
            time = newTime;
            EventBus.getDefault().postSticky(new KICKOUTEvent(a));
        }else {
            long l = newTime - time;
            LogUtils.e("TIME**********************************=="+l);
            if (l >= intervalTime) {
                EventBus.getDefault().postSticky(new KICKOUTEvent(a));
                time = newTime;
            }
        }
    }
}
