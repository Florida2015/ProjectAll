package com.huaxia.finance.model;

import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;

import java.io.Serializable;
import java.util.List;

/**
 * 产品列表
 * Created by houwen.lai on 2016/1/26.
 */
public class ProductListModel implements Serializable {

    private int PageSize;
    private int ItemSize;
    private List<ProductItemModel> list;

    public ProductListModel() {
        super();
    }

    public ProductListModel(int pageSize, int itemSize, List<ProductItemModel> list) {
        PageSize = pageSize;
        ItemSize = itemSize;
        this.list = list;
    }

    public List<ProductItemModel> getList() {
        return list;
    }

    public void setList(List<ProductItemModel> list) {
        this.list = list;
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


    @Override
    public String toString() {
        return "ProductListModel{" +
                "PageSize='" + PageSize + '\'' +
                ", ItemSize='" + ItemSize + '\'' +
                ", list=" + list +
                '}';
    }
    public static ProductListModel prase(String text){
        return  DMApplication.getInstance().getGson().fromJson(text,new TypeToken<ProductListModel>(){}.getType());
    }

}
