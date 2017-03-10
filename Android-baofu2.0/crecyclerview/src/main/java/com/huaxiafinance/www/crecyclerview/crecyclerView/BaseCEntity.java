package com.huaxiafinance.www.crecyclerview.crecyclerView;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;

import java.io.Serializable;
import java.util.Map;

import rx.Observable;

/**
 * Created by chenqun on 2017/2/17.
 */

public abstract class BaseCEntity<T> implements Serializable {

    protected Map<String, String> mParam;

    //    获取接口的Observable
    public abstract Observable getPageAt(int page,int row);

    //每个条目的点击事件
    public abstract void onClick(Context context, T item);


    //    获取RecyclerView的item的layout
    public int getItemLayou() {
        return 0;
    }

    //    给每个条目设置数据，item是数据
    public void convert(BaseViewHolder helper, T item) {
    }

    void setParam(Map<String, String> param) {
        this.mParam = param;
    }
}
