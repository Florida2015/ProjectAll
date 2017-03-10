package com.huaxia.finance.model;

import java.io.Serializable;
import java.util.List;

/**
 * 通知列表
 * Created by houwen.lai on 2016/2/19.
 */
public class NoticeListModel implements Serializable {

    private int PageSize;
    private int ItemSize;
    private List<NoticeModel> list;

    public NoticeListModel() {
        super();
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getItemSize() {
        return ItemSize;
    }

    public void setItemSize(int itemSize) {
        ItemSize = itemSize;
    }

    public List<NoticeModel> getList() {
        return list;
    }

    public void setList(List<NoticeModel> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NoticeListModel{" +
                "PageSize=" + PageSize +
                ", ItemSize=" + ItemSize +
                ", list=" + list +
                '}';
    }
}
