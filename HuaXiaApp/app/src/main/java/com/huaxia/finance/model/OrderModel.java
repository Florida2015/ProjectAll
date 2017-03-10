package com.huaxia.finance.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by houwen.lai on 2016/2/3.
 */
public class OrderModel implements Serializable {

    private int itemSize;
    private int pageSize;
    private List<OrderDetailModel> orders;

    public OrderModel() {
        super();
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<OrderDetailModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetailModel> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "itemSize=" + itemSize +
                ", pageSize=" + pageSize +
                ", orders=" + orders +
                '}';
    }
}
