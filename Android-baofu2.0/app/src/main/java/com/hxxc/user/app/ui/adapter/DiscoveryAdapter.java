package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.utils.DisplayUtil;
import com.hxxc.user.app.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chenqun on 2016/10/25.
 */

public class DiscoveryAdapter extends BaseAdapter2<ContentBean> {
    public DiscoveryAdapter(Context mContext, List<ContentBean> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_discovery, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentBean bean = mList.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.tv_title.setText(bean.getTitle());
        mHolder.tv_time.setText(bean.getCreateTimeStr());

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mHolder.iv_image.getLayoutParams();
        params.width = ImageUtils.getInstance().getmWindowWidth() - DisplayUtil.dip2px(mContext,15)*2;
        params.height = (int) (params.width* Constants.RATE_Discovery);
        mHolder.iv_image.setLayoutParams(params);

        Picasso.with(mContext)
                .load(bean.getRealMbContentUrl())
                .resize(params.width,params.height)
                .centerCrop()
                .config(Bitmap.Config.RGB_565)
                .tag(Constants.Discovery)
                .placeholder(R.drawable.faxian)
                .error(R.drawable.faxian)
                .into(mHolder.iv_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener){
                    mListener.setOnItemClick(holder.itemView,position);
                }
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_title;
        private final TextView tv_time;
        private final ImageView iv_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
