package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Department;
import com.hxxc.user.app.utils.ImageUtils;

import java.util.List;

/**
 * Created by chenqun on 2016/10/25.
 */

public class DepartmentAdapter extends BaseAdapter2<Department> {
    public DepartmentAdapter(Context mContext, List<Department> list, RecyclerView recyclerView) {
        super(mContext, list, recyclerView);
    }

    @Override
    protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department,parent,false));
    }

    @Override
    protected void onBindNomalViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holdler = (MyViewHolder) viewHolder;
        Department department = mList.get(position);
        ImageUtils.getInstance().displayImage(department.getRealPicUrl(), holdler.department_pic_img);

        if(null != mListener){
            holdler.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = viewHolder.getLayoutPosition();
                    if(i <= mList.size() - 1){
                        mListener.setOnItemClick(viewHolder.itemView, i);
                    }
                }
            });
        }
        holdler.name_text.setText(department.getName());
        holdler.address_text.setText("地址：" + department.getAddress());
        holdler.phone_text.setText("电话：" + department.getTelephone());
    }
    private static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView department_pic_img;
        private final TextView name_text;
        private final TextView address_text;
        private final TextView distance_text;
        private final TextView phone_text;

        private MyViewHolder(View itemView) {
            super(itemView);
            department_pic_img = (ImageView) itemView.findViewById(R.id.department_pic_img);
            name_text = (TextView) itemView.findViewById(R.id.name_text);
            address_text = (TextView) itemView.findViewById(R.id.address_text);
            distance_text = (TextView) itemView.findViewById(R.id.distance_text);
            phone_text = (TextView) itemView.findViewById(R.id.phone_text);
        }
    }
}
