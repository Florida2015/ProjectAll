package com.hxxc.user.app.ui.mine.tradelist;

import android.view.View;

import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;

/**
 * Created by Administrator on 2017/3/2.
 */

public abstract class TradeViewHolder<T> extends BaseViewHolder<T> {

    private String request;

    public TradeViewHolder(View v) {
        super(v);
    }
    public void onBindViewHolder(T obj){}
}
