package com.hxxc.user.app.Event;

/**
 * Created by chenqun on 2016/12/12.
 */

public class GestureManagerEvent {
    public static final  int From_CreateGesture = 22;
    public static final  int From_UnlockGesture = 222;
    public String patterns ;
    public int from = From_UnlockGesture ;

    public GestureManagerEvent(int from) {
        this.from = from;
    }

    public GestureManagerEvent setPatterns(String p){
        this.patterns = p;
        return this;
    }
}
