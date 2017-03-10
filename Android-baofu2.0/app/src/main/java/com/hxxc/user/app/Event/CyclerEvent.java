package com.hxxc.user.app.Event;

/**
 * Created by Administrator on 2017/2/24.
 */

public class CyclerEvent {

    private int index;

    public CyclerEvent() {
        super();
    }

    public CyclerEvent(int text) {
        this.index = text;
    }

    public int getFirstVisiablePosition() {
        return index;
    }

    public void setFirstVisiablePosition(int firstVisiablePosition) {
        this.index = firstVisiablePosition;
    }

}
