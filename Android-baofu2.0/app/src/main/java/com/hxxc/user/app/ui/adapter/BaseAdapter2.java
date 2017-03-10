package com.hxxc.user.app.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 封装了上拉加载的RecycleVeiw.Adapter
 * Created by chenqun on 2016/7/19.
 */
public abstract class BaseAdapter2<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public int rows = Constants.ROWS;
    private RecyclerView mRecyclerView;

    /**
     * A class that represents a fixed view in a list, for example a header at the top
     * or a footer at the bottom.
     */
    public class FixedViewInfo {
        /**
         * The view to add to the list
         */
        public View view;
        /**
         * The data backing the view. This is returned from {RecyclerView.Adapter#getItemViewType(int)}.
         */
        public int viewType;
    }

    private LinkedList<FixedViewInfo> mHeaderViewInfos = new LinkedList<>();
    private LinkedList<FixedViewInfo> mFooterViewInfos = new LinkedList<>();

    private final static int TYPE_NORMAL = 0;
    private final static int TYPE_HEADER = 100;
    private final static int TYPE_FOOTER = 200;
    private final static int TYPE_LOADINGMORE = 300;//底部--往往是loading_more
    public int page = 1;//-1表示无数据

    public final Context mContext;
    public List<T> mList = new ArrayList<>();
    public boolean isLoadmore;//控制是否显示加载更多条目

    public BaseAdapter2(Context mContext, List<T> list, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.mList.addAll(list);
        this.mRecyclerView = recyclerView;
    }

    public void onNoDatas() {
        page = -1;
        isLoadmore = false;
    }

    public void reSetDatas() {
        page = 1;
        isLoadmore = true;
//        initScreemHeight();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public boolean hasDatas() {
        return page != -1;
    }

    public int getPage() {
        return ++page;
    }

    public void initScreemHeight() {

        int heightScreen = mRecyclerView.getHeight();
        if (heightScreen <= 10) {
            WindowManager wm = (WindowManager) (mContext).getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            heightScreen = outMetrics.heightPixels;
        }

//        View view2 = onNomalViewHolder().itemView;
//        View view2 = View.inflate(mContext, R.layout.item_message, null);
        int height = 0;
        int headerHeight = 0;
        try {
//            view2.measure(0, 0);
//            view2.measure(
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//            height = view2.getMeasuredHeight();
            height = DisplayUtil.dip2px(mContext, 80);

            if (mHeaderViewInfos.size() > 0) {
                for (FixedViewInfo view : mHeaderViewInfos) {
                    view.view.measure(0, 0);
                    headerHeight += view.view.getMeasuredHeight();
                }
            }
        } catch (Exception e) {
            height = DisplayUtil.dip2px(mContext, 80);
            if (mHeaderViewInfos.size() > 0) {
                for (FixedViewInfo view : mHeaderViewInfos) {
                    headerHeight += DisplayUtil.dip2px(mContext, 200);
                }
            }
        } finally {
            isLoadmore = ((mList.size() * height) - headerHeight) > heightScreen;
        }
    }

    private boolean isHeaderPosition(int position) {
        return position < mHeaderViewInfos.size();
    }

    private boolean isFooterPosition(int position) {
        return mFooterViewInfos.size() > 0
                && position >= mHeaderViewInfos.size() + mList.size()
                && position < mHeaderViewInfos.size() + mFooterViewInfos.size() + mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViewInfos.get(position).viewType;
        }
        if (isFooterPosition(position)) {
            return mFooterViewInfos.get(position - mHeaderViewInfos.size() - mList.size()).viewType;
        }
        if (isLoadmore) {
            if (getItemCount() - 1 == position) {
                return TYPE_LOADINGMORE;
            } else {
                return TYPE_NORMAL;
            }
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        int size = mList.size();
        if (0 == size) {
            return mHeaderViewInfos.size() + mFooterViewInfos.size();
        } else {
            if (isLoadmore) {
                return size + 1 + mHeaderViewInfos.size() + mFooterViewInfos.size();
            }
            return size + mHeaderViewInfos.size() + mFooterViewInfos.size();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeader(viewType)) {
            int whichHeader = Math.abs(viewType - TYPE_HEADER);
            View headerView = mHeaderViewInfos.get(whichHeader).view;
            checkAndSetRecyclerViewLayoutParams(headerView);
            return new RecyclerView.ViewHolder(headerView) {
            };
        }
        if (isFooter(viewType)) {
            int whichFooter = Math.abs(viewType - TYPE_FOOTER);
            View footerView = mFooterViewInfos.get(whichFooter).view;
            checkAndSetRecyclerViewLayoutParams(footerView);
            return new RecyclerView.ViewHolder(footerView) {
            };
        }

        if (viewType == TYPE_LOADINGMORE) {
            View view = View.inflate(mContext, R.layout.view_loadmore, null);
            return new LoadingmoreViewHolder(view);
        } else {
            return onNomalViewHolder(parent);
        }
    }

    protected abstract RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            onBindNomalViewHolder(holder, position - mHeaderViewInfos.size());
        }
    }

    protected abstract void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position);

    public void notifyDatasChanged(final List<T> data, boolean isLoadingmore) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoadingmore) {
                    if (null != data) {
                        mList.addAll(data);
                    }
                } else {
                    mList.clear();
                    if (null != data) {
                        mList.addAll(data);
                    }
                    reSetDatas();
                }
                if (data == null || data.size() < rows) {//数据小于rows就说明没有更多数据了
                    onNoDatas();
                }

                if (isLoadingmore) {
                    if (null == data || data.size() == 0) {
                        notifyItemRemoved(getItemCount());
                    } else {
                        notifyDataSetChanged();
                    }
                } else {
                    notifyDataSetChanged();
                }
            }
        }, 500);
    }

    private void checkAndSetRecyclerViewLayoutParams(View child) {
        if (child == null) return;
        ViewGroup.LayoutParams p = child.getLayoutParams();
        RecyclerView.LayoutParams params = null;
        if (p == null) {
            params = new RecyclerView.LayoutParams(new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)));
        } else {
            if (!(p instanceof RecyclerView.LayoutParams)) {
                params = mRecyclerView.getLayoutManager().generateLayoutParams(p);
            }
        }
        child.setLayoutParams(params);
    }

    /**
     * Adds a header view
     *
     * @param view
     */
    public void addHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        }
        final FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.viewType = TYPE_HEADER + mHeaderViewInfos.size();
        mHeaderViewInfos.add(info);
        notifyDataSetChanged();
    }

    public List<View> getHeaderView() {
        List<View> viewList = new ArrayList<View>(mHeaderViewInfos.size());
        for (FixedViewInfo fixedViewInfo : mHeaderViewInfos) {
            viewList.add(fixedViewInfo.view);
        }
        return viewList;
    }

    private boolean isHeader(int viewType) {
        return viewType >= TYPE_HEADER
                && viewType < (TYPE_HEADER + mHeaderViewInfos.size());
    }

    /**
     * Adds a footer view
     *
     * @param view
     */
    public void addFooterView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the view to add must not be null!");
        }
        final FixedViewInfo info = new FixedViewInfo();
        info.view = view;
        info.viewType = TYPE_FOOTER + mFooterViewInfos.size();
        mFooterViewInfos.add(info);
        notifyDataSetChanged();
    }

    public List<View> getFooterView() {
        List<View> viewList = new ArrayList<View>(mFooterViewInfos.size());
        for (FixedViewInfo fixedViewInfo : mFooterViewInfos) {
            viewList.add(fixedViewInfo.view);
        }
        return viewList;
    }

    private boolean isFooter(int viewType) {
        return viewType >= TYPE_FOOTER
                && viewType < (TYPE_FOOTER + mFooterViewInfos.size());
    }

    private class LoadingmoreViewHolder extends RecyclerView.ViewHolder {
        private LoadingmoreViewHolder(View view) {
            super(view);
        }
    }

    public OnItemClick mListener;
    public OnHeadItemClick mHeadListener;

    public void setOnItemClick(OnItemClick listener) {
        this.mListener = listener;
    }

    public OnItemClick getOnItemClickListener() {
        return mListener;
    }

    public interface OnItemClick {
        void setOnItemClick(View view, int tag);

        void setOnItemLongClick(View view, int tag);
    }

    public interface OnHeadItemClick {
        void setOnHeadItemClick(View view, int tag, int status, String statustext);
    }

    public void setOnHeadItemClick(OnHeadItemClick listener) {
        this.mHeadListener = listener;
    }

    public OnHeadItemClick getOnHeadItemClickListener() {
        return mHeadListener;
    }
}