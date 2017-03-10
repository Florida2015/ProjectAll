package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.BankInfo;
import com.hxxc.user.app.utils.CommonUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chenqun on 2016/11/1.
 */

public class BankcardListForAddAdapter extends BaseAdapter2<BankInfo> {
    private int item = -1;
    public int getItem(){
        return item;
    }

    public BankcardListForAddAdapter(Context mContext, List<BankInfo> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_bankcard_add,parent,false));
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        BankInfo info = mList.get(position);
        MyViewHolder myHolder = (MyViewHolder)holder;
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = position;
                notifyDataSetChanged();
                if (mListener != null){
                    mListener.setOnItemClick(myHolder.itemView,position);
                }
            }
        });
        if (myHolder.getLayoutPosition() == item){
            myHolder.tl_content.setBackgroundColor(Color.parseColor("#fff1f1f1"));
        }else {
            myHolder.tl_content.setBackgroundColor(Color.WHITE);
        }
        if(position == mList.size()-1){
            myHolder.line.setVisibility(View.INVISIBLE);
        }else {
            myHolder.line.setVisibility(View.VISIBLE);
        }

        Picasso.with(mContext).load(info.getRealMobileLogoUrl()).into(myHolder.tv_ico);
        myHolder.tv_bank.setText(info.getBankName());
        myHolder.tv_des.setText("单笔限额"+ CommonUtil.checkMoney2(info.getSingleWLimit().floatValue())+"万，单日限额"+CommonUtil.checkMoney2(info.getDailyWLimit().floatValue())+"万");
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView tv_ico;
        private final TextView tv_bank;
        private final TextView tv_des;
        private final View line;
        private final RelativeLayout tl_content;

        private MyViewHolder(View itemView) {
            super(itemView);
            tv_ico = (ImageView) itemView.findViewById(R.id.tv_ico);
            tv_bank = (TextView) itemView.findViewById(R.id.tv_bank);
            tv_des = (TextView) itemView.findViewById(R.id.tv_des);
            line = (View) itemView.findViewById(R.id.line);
            tl_content = (RelativeLayout) itemView.findViewById(R.id.tl_content);
        }
    }
}
