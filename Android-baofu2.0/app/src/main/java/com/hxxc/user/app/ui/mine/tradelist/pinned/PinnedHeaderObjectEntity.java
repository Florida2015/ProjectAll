package com.hxxc.user.app.ui.mine.tradelist.pinned;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Administrator on 2017/2/28.
 *
 */

public class PinnedHeaderObjectEntity<T> implements MultiItemEntity {

    private final int itemType;

    private T data;

    private String pinnedHeaderName;
    private String pinnedHeaderName1;
    private String pinnedHeaderName2;

    public PinnedHeaderObjectEntity(T data, int itemType, String pinnedHeaderName, String pinnedHeaderName1, String pinnedHeaderName2) {
        this.data = data;
        this.itemType = itemType;
        this.pinnedHeaderName = pinnedHeaderName;
        this.pinnedHeaderName1 = pinnedHeaderName1;
        this.pinnedHeaderName2 = pinnedHeaderName2;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getPinnedHeaderName() {
        return pinnedHeaderName;
    }

    public void setPinnedHeaderName(String pinnedHeaderName) {
        this.pinnedHeaderName = pinnedHeaderName;
    }

    public String getPinnedHeaderName1() {
        return pinnedHeaderName1;
    }

    public void setPinnedHeaderName1(String pinnedHeaderName1) {
        this.pinnedHeaderName1 = pinnedHeaderName1;
    }

    public String getPinnedHeaderName2() {
        return pinnedHeaderName2;
    }

    public void setPinnedHeaderName2(String pinnedHeaderName2) {
        this.pinnedHeaderName2 = pinnedHeaderName2;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

}
