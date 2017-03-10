package com.hxxc.user.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 通用的 adapter
 * Created by xxx on 2015/8/28.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mList;//数据
    protected int mLayoutId;//item布局

    public CommonAdapter(Context context, List<T> list, int layoutId){
        this.mContext = context;
        this.mList = list;
        this.mLayoutId = layoutId;
    }

    /**
     * 给列表添加数据
     * @param listData
     * @param position -1:列表末尾添加数据
     */
    public void setAdapterListData(int position,List<T> listData){
        if (listData == null) return;
        if (position == -1) this.mList.addAll(listData);
        else this.mList.addAll(position,listData);
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clearAdapterListData(){
        if (this.mList!=null){
            this.mList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 移除列表中的某个数据
     * @param position 移除position item的数据
     */
    public void removeAdapterListData(int position){
        if (this.mList == null)return;
        if (this.mList.size()>position){
            this.mList.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 返回列表的所有数据
     * @return
     */
    public List<T> getAdapterListData(){
        return this.mList;
    }

    @Override
    public int getCount() {
        if(null == mList){
            return 0;
        }
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent,mLayoutId,position);
        convert(viewHolder, getItem(position));
        convert(viewHolder, getItem(position),position);
        return viewHolder.getConvertView();
    }

    /**
     * 推控件进行赋值
     * @param helper
     * @param item
     */
    public void convert(ViewHolder helper, T item){};
    public void convert(ViewHolder helper, T item,int position){};
    /**
     * 布局
     */
    public static class ViewHolder {

        private View mConvertView;

        public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            mConvertView.setTag(this);
        }

        public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
            if (convertView == null) {
                return new ViewHolder(context, parent, layoutId, position);
            }
            return (ViewHolder) convertView.getTag();
        }


        public View getConvertView() {
            return mConvertView;
        }

        public <T extends View> T getView(int viewId) {
            return (T) mConvertView.findViewById(viewId);
        }

        /**
         * TextView 赋值
         * @param viewId
         * @param text
         * @return
         */
        public ViewHolder setText(int viewId,String text){
            TextView view = getView(viewId);
            view.setText(text);
            return this;
        }

        /**
         * ImageView 图片
         * @param viewId
         * @param drawableId
         * @return
         */
        public ViewHolder setImageResource(int viewId, int drawableId) {
            ImageView view = getView(viewId);
            view.setImageResource(drawableId);
            return this;
        }
    }

}
