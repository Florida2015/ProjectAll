package com.hxxc.user.app.widget.trecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hxxc.user.app.Event.CyclerEvent;

import java.util.Map;

/**
 * T is model or bean
 * @param <T>
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public Context mContext;

    public BaseViewHolder(View v) {
        super(v);
        mContext = v.getContext();
        ViewUtil.autoFind(this, v);//id与name一致
//        ButterKnife.bind(this,v);
    }

    /**
     * ViewHolder的Type，同时也是它的LayoutId
     *
     * @return
     */
    public abstract int getType();

    /**
     * 绑定ViewHolder
     *
     * @return
     */
    public abstract void onBindViewHolder(View view,int position, T obj);


    public void onBindViewHolder(View view,int position, T obj,int positionF, T objF,Map<String, String> param){}

    public void onBindViewHolder(View view,int position, T obj,Map<String, String> param,boolean isHasMore,int headerCount){}

}
