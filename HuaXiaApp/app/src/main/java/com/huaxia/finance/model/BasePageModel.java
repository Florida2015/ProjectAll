package com.huaxia.finance.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 * Created by houwen.lai on 2016/2/22.
 */
public class BasePageModel<T> implements Serializable {

    private int PageSize;
    private int ItemSize;
    private List<T> list;

    public BasePageModel() {
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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BasePageModel{" +
                "PageSize=" + PageSize +
                ", ItemSize=" + ItemSize +
                ", list=" + list +
                '}';
    }
}
