package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.utils.ImageUtils;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by chenqun on 2016/6/28.
 */
public class DepartmentPicsAdapter extends BaseAdapter {
    private ImageLoadingListener animateFirstListener = ImageUtils.AnimateFirstDisplayListener.getInstance();
    private List<String> list ;
    private Context context;

    public DepartmentPicsAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_department_pics_list,null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String url = list.get(position);
        ImageUtils.getInstance().displayImage(url, viewHolder.imageView);
        return convertView;
    }

    static class ViewHolder{
        ImageView imageView;
    }
}
