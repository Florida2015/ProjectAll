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
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.utils.DisplayUtil;
import com.hxxc.user.app.utils.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chenqun on 2016/11/17.
 */

public class NewsAdapter extends BaseAdapter2<ContentBean> {
    public NewsAdapter(Context mContext, List<ContentBean> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notices, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentBean bean = mList.get(position);
        MyViewHolder mHolder = (MyViewHolder) holder;
        mHolder.tv_1.setText(bean.getTitle());
//        mHolder.tv_1.setTextColor(Color.parseColor("#aaaaaa"));
        mHolder.tv_3.setText(bean.getIntroduction());
        mHolder.tv_time.setText(bean.getCreateTimeStr());

        if (TextUtils.isEmpty(bean.getRealPicUrl()))
            mHolder.iv_image.setVisibility(View.GONE);
        else {
            mHolder.iv_image.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mHolder.iv_image.getLayoutParams();
            params.width = ImageUtils.getInstance().getmWindowWidth() - DisplayUtil.dip2px(mContext, 15) * 2;
            params.height = (int) (params.width * Constants.RATE_News);
            mHolder.iv_image.setLayoutParams(params);

            Picasso.with(mContext)
                    .load(bean.getRealPicUrl())
                    .resize(params.width, params.height)
                    .centerCrop()
                    .config(Bitmap.Config.RGB_565)
                    .tag(Constants.News)
                    .placeholder(R.drawable.banner)
                    .error(R.drawable.banner)
                    .into(mHolder.iv_image);
        }


        mHolder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.setOnItemClick(mHolder.ll_content, position);
                }
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout ll_content;
        private final TextView tv_1;
        private final TextView tv_3;
        private final TextView tv_time;
        private final ImageView iv_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            ll_content = (LinearLayout) itemView.findViewById(R.id.ll_content);
            tv_1 = (TextView) itemView.findViewById(R.id.tv_1);
            tv_3 = (TextView) itemView.findViewById(R.id.tv_3);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
