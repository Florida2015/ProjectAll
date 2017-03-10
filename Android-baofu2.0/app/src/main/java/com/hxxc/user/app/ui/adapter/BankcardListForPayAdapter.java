package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Account;
import com.hxxc.user.app.utils.CommonUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chenqun on 2016/10/28.
 */

public class BankcardListForPayAdapter extends BaseAdapter2<Account> {
    private int item = -1;

    public void setItem(int a) {
        this.item = a;
    }

    public BankcardListForPayAdapter(Context mContext, List<Account> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item_bankcard, parent, false));
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        Account account = mList.get(position);
        MyViewHolder myHolder = (MyViewHolder) holder;
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = position;
                notifyDataSetChanged();
                if (mListener != null) {
                    mListener.setOnItemClick(myHolder.itemView, position);
                }
            }
        });
        if (-1 == item) {
            if (account.getIsDefaultCard() == 1) {
                myHolder.tv_selected.setVisibility(View.VISIBLE);
            } else {
                myHolder.tv_selected.setVisibility(View.INVISIBLE);
            }
        } else {
            if (myHolder.getLayoutPosition() == item) {
                myHolder.tv_selected.setVisibility(View.VISIBLE);
            } else {
                myHolder.tv_selected.setVisibility(View.INVISIBLE);
            }
        }

//        if (mList.size() > 1 && position == mList.size() - 1) {
//            myHolder.view_line.setVisibility(View.INVISIBLE);
//        } else {
//            myHolder.view_line.setVisibility(View.VISIBLE);
//        }
        Picasso.with(mContext).load(account.getMobileLogoUrl()).error(R.drawable.abc).placeholder(R.drawable.abc).into(myHolder.iv_bank);
        myHolder.tv_bank_name.setText(account.getBank() + "（尾号" + account.getBankCard().substring(account.getBankCard().length() - 4, account.getBankCard().length()) + ")");
        myHolder.tv_bank_describe.setText("单笔限额" + (CommonUtil.checkMoney2(account.getSingleWLimit().floatValue())) + "万，单日限额" + CommonUtil.checkMoney2(account.getDailyWLimit().floatValue()) + "万");
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_bank;
        private final TextView tv_bank_name;
        private final TextView tv_bank_describe;
        private final ImageView tv_selected;
        private final View view_line;

        private MyViewHolder(View itemView) {
            super(itemView);
            iv_bank = (ImageView) itemView.findViewById(R.id.iv_bank);
            tv_bank_name = (TextView) itemView.findViewById(R.id.tv_bank_name);
            tv_bank_describe = (TextView) itemView.findViewById(R.id.tv_bank_describe);
            tv_selected = (ImageView) itemView.findViewById(R.id.tv_selected);
            view_line = (View) itemView.findViewById(R.id.view_line);
        }
    }
}
