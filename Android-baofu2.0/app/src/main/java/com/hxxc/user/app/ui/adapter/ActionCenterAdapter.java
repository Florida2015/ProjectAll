package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ActionCenterBean;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.DisplayUtil;
import com.hxxc.user.app.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chenqun on 2016/11/3.
 */

public class ActionCenterAdapter extends BaseAdapter2<ActionCenterBean> {
    public ActionCenterAdapter(Context mContext, List<ActionCenterBean> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_center, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ActionCenterBean bean = mList.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.tv_title.setText(bean.getActivityName());
        mHolder.tv_time.setText(CommonUtil.formatDate1(bean.getBeginTime()) + "到" + CommonUtil.formatDate1(bean.getEndTime()));
        mHolder.tv_des.setText(bean.getDescribes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.setOnItemClick(holder.itemView, position);
                }
            }
        });
        if ("是".equals(bean.getStatus())) {
            mHolder.tv_tag.setText("HOT");
            mHolder.tv_tag.setTextColor(mContext.getResources().getColor(R.color.orange_text));
            mHolder.tv_tag.setBackgroundResource(R.drawable.stroke_orange);
        } else {
            mHolder.tv_tag.setText("已结束");
            mHolder.tv_tag.setTextColor(mContext.getResources().getColor(R.color.grey_text));
            mHolder.tv_tag.setBackgroundResource(R.drawable.stroke_grey);
        }


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mHolder.iv_image.getLayoutParams();
        params.width = ImageUtils.getInstance().getmWindowWidth() - DisplayUtil.dip2px(mContext, 15) * 2;
        params.height = (int) (params.width * Constants.RATE_ActionCenter);
        mHolder.iv_image.setLayoutParams(params);

        if (!TextUtils.isEmpty(bean.getMobileListUrlText()))
            Picasso.with(mContext)
                    .load(bean.getMobileListUrlText())
                    .resize(params.width, params.height)
                    .centerCrop()
                    .config(Bitmap.Config.RGB_565)
                    .tag(Constants.ActionCenter)
                    .error(R.drawable.banner)
                    .placeholder(R.drawable.banner)
                    .into(mHolder.iv_image);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_title;
        private final TextView tv_time;
        private final ImageView iv_image;
        private final TextView tv_des;
        private final TextView tv_tag;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_des = (TextView) itemView.findViewById(R.id.tv_des);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
        }
    }
}
