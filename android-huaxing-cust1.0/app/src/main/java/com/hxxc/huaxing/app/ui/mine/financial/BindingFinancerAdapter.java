package com.hxxc.huaxing.app.ui.mine.financial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.base.BaseAdapter2;
import com.hxxc.huaxing.app.wedgit.CircleImageView;

import java.util.List;

/**
 * Created by chenqun on 2016/9/30.
 */

public class BindingFinancerAdapter extends BaseAdapter2<String> {
    public BindingFinancerAdapter(Context mContext, List mList, RecyclerView recyclerView) {
        super(mContext, mList, recyclerView);
    }

    public int item = -1;

    public void setCheckItem(int p) {
        item = p;
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_finance_item, parent, false));
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        if (position == mList.size() - 1) {
            viewHolder.line1.setVisibility(View.GONE);
            viewHolder.line2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.line1.setVisibility(View.VISIBLE);
            viewHolder.line2.setVisibility(View.GONE);
        }

//        ImageUtils.getInstance().displayImage(HttpRequest.baseUrlPre + finance.getIcon(), viewHolder.circleImageView, ImageUtils.mOptionsNoCache);
//
//        viewHolder.name_text.setText(finance.getFname());
//        viewHolder.service_count_text.setText(finance.getTservicecount() + "位");
//        viewHolder.finance_no_text.setText(finance.getFinancialno());
//        if (finance.getTinvestmentamout() == 0) {
//            viewHolder.amout_text.setText("0万元");
//        } else {
//            viewHolder.amout_text.setText((int) (finance.getTinvestmentamout() / 10000) + "万元");
//        }
//
        if (position == item) {
            viewHolder.is_check_img.setVisibility(View.VISIBLE);
        } else {
            viewHolder.is_check_img.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item == position) {
                    setCheckItem(-1);
                    return;
                }
                setCheckItem(position);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView circleImageView;
        public TextView name_text;
        public TextView service_count_text;
        public TextView finance_no_text;
        public TextView amout_text;
        public ImageView is_check_img;
        public View line1;
        public View line2;

        public MyViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            name_text = (TextView) itemView.findViewById(R.id.name_text);
            service_count_text = (TextView) itemView.findViewById(R.id.service_count_text);
            finance_no_text = (TextView) itemView.findViewById(R.id.finance_no_text);
            amout_text = (TextView) itemView.findViewById(R.id.amout_text);
            is_check_img = (ImageView) itemView.findViewById(R.id.is_check_img);
            line1 = itemView.findViewById(R.id.line1);
            line2 = itemView.findViewById(R.id.line2);
        }
    }
}
