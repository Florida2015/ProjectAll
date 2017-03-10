package com.hxxc.user.app.ui.mine.tradelist.pinned;


import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.TradeRecordBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.mine.tradelist.pinned.BaseHeaderAdapter;
import com.hxxc.user.app.ui.mine.tradelist.pinned.PinnedHeaderObjectEntity;
import com.hxxc.user.app.ui.mine.tradelist.pinned.PinnedSectionAdapter;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.utils.StringUtil;
import com.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;
import com.pinnedsectionitemdecoration.callback.OnHeaderClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by Administrator on 2017/3/7.
 * 分组显示
 *
 *
 */

public class PinnedSelectCyc1View extends FrameLayout {

    @BindView(R.id.swiperefresh_order)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.recyclerview_order)
    RecyclerView recyclerview;
    @BindView(R.id.ll_emptyview)
    LinearLayout ll_emptyview;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private int begin = 0;
    private int PAGE_COUNT = 8;//页数
    private boolean isRefreshable = true, isHasHeadView = false, isEmpty = false;
    private Map<String, String> param = new HashMap<>();
    private boolean flagFirstPositon=false;//可见第一位置
    private String tradeId;//交易

    public PinnedSelectCyc1View(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PinnedSelectCyc1View(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PinnedSelectCyc1View(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        View layout = LayoutInflater.from(context).inflate(
                R.layout.layout_list_recyclerview, null);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(layout);
        ButterKnife.bind(this, layout);
        initView(context);
    }

    private void initView(Context context) {
        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_bright);
        swiperefresh.setEnabled(isRefreshable);
//        swiperefresh.setOnRefreshListener(() -> reFetch());
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.d("Trecyclerview onRefresh===");
                reFetch();
            }
        });
        recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
//        recyclerview.setAdapter(mCommAdapter);

        setDataNotify();

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            protected int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (flagFirstPositon && (layoutManager instanceof LinearLayoutManager)) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();

                    System.out.println(lastItemPosition + "   " + firstItemPosition);
                }
                //加载更多
                if (recyclerview.getAdapter() != null
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerview.getAdapter().getItemCount()
                        &&mAdapter.isLoadMoreEnable()){// && mCommAdapter.isHasMore
                    fetch();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int arg0, int arg1) {
                super.onScrolled(recyclerView, arg0, arg1);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        ll_emptyview.setOnClickListener((view -> reFetch()));
    }

    public PinnedSectionAdapter getAdapter() {
        return mAdapter;
    }

    public void setRefreshing(boolean i) {
        swiperefresh.setRefreshing(i);
    }

    public PinnedSelectCyc1View setIsRefreshable(boolean i) {
        isRefreshable = i;
        swiperefresh.setEnabled(i);
        return this;
    }
    /**
     *
     * @param flagFirstPositon
     * @return
     */
    public PinnedSelectCyc1View setReturnFirstPosition(boolean flagFirstPositon) {
        this.flagFirstPositon = flagFirstPositon;
        return this;
    }

    public RecyclerView getRecyclerview() {
        return recyclerview;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swiperefresh;
    }


    public PinnedSelectCyc1View setFooterView(Class<? extends BaseViewHolder> cla) {
        this.begin = 0;

        return this;
    }

    public void setEmpty() {
        if (!isHasHeadView &&!isEmpty) {//
            isEmpty = true;
            ll_emptyview.setVisibility(View.VISIBLE);
            swiperefresh.setVisibility(View.GONE);
        }else{
            ll_emptyview.setVisibility(View.GONE);
            swiperefresh.setVisibility(View.VISIBLE);
            if (isHasHeadView){

            }
        }
    }

    public PinnedSelectCyc1View setParam(String key, String value) {
        this.param.put(key, value);
        return this;
    }

    public PinnedSelectCyc1View setParamKey(String key) {
        this.tradeId = key;
        return this;
    }

    public void reFetch() {
        LogUtils.d("trecyclerView=reFetch");
        this.begin = 0;
        fetch();
    }

    /**
     * 数据请求
     */
    public void fetch() {
        begin++;
        if (isEmpty) {
            ll_emptyview.setVisibility(View.GONE);
            swiperefresh.setVisibility(View.VISIBLE);
        }
        LogUtils.d("trecyclerView=getPageAt");

        Api.getClient().getGetUserTradeList( Api.uid,tradeId,begin, PAGE_COUNT).
                compose(RxApiThread.convert()).subscribe(new BaseSubscriber<List<TradeRecordBean>>(context) {
            @Override
            public void onSuccess(List<TradeRecordBean> tradeRecordBeen) {
                swiperefresh.setRefreshing(false);

                if (begin==0||begin==1){
                    if (tradeRecordBeen!=null&&tradeRecordBeen.size()>0) {
                        setDataRefresh(true,tradeRecordBeen);
                    }else {
                        isEmpty=false;
                        setEmpty();
                    }
                }else setDataRefresh(false,tradeRecordBeen);

            }

            @Override
            public void onFail(String fail) {
                super.onFail(fail);
                swiperefresh.setRefreshing(false);


            }
        });
    }

    /**
     * 适配器
     */
    List<PinnedHeaderObjectEntity<TradeRecordBean>> data = new ArrayList<>();
    List<TradeRecordBean> mlist = new ArrayList<TradeRecordBean>();
    PinnedSectionAdapter<PinnedHeaderObjectEntity<TradeRecordBean>> mAdapter;
    /**
     * 数据初始化
     * @param lists
     */
    public void setDataRefresh(boolean flagFirst,List<TradeRecordBean> lists){
        if (mlist==null) mlist = new ArrayList<TradeRecordBean>();

        if (flagFirst){
            mlist.clear();
        }

        mlist.addAll(lists);

        data.clear();

        if (mlist.size()>0)
            data.add(new PinnedHeaderObjectEntity<>(mlist.get(0), BaseHeaderAdapter.TYPE_HEADER,
                    mlist.get(0).getMonth()+"月累计",
                    "回款:"+ MoneyUtil.addComma(mlist.get(0).getBackMoney(),2,ROUND_FLOOR),
                    "出借:"+MoneyUtil.addComma(mlist.get(0).getPayMoney(),2,ROUND_FLOOR)));


        for (int i=0;i<mlist.size();i++) {
            if (i>0&&mlist.get(i).getMonth()!=mlist.get(i-1).getMonth()){
                data.add(new PinnedHeaderObjectEntity<>(mlist.get(i), BaseHeaderAdapter.TYPE_HEADER,
                        mlist.get(i).getMonth()+"月累计",
                        "回款:"+ MoneyUtil.addComma(mlist.get(i).getBackMoney(),2,ROUND_FLOOR),
                        "出借:"+MoneyUtil.addComma(mlist.get(i).getPayMoney(),2,ROUND_FLOOR)));
            }

            data.add(new PinnedHeaderObjectEntity<>(mlist.get(i), BaseHeaderAdapter.TYPE_DATA,
                    mlist.get(i).getMonth()+"月累计",
                    "回款:"+ MoneyUtil.addComma(mlist.get(i).getBackMoney(),2,ROUND_FLOOR),
                    "出借:"+ MoneyUtil.addComma(mlist.get(i).getPayMoney(),2,ROUND_FLOOR)));

        }


        if (mAdapter!=null){
            if (lists.size()>=PAGE_COUNT){
                mAdapter.setHasMore(true);
                mAdapter.setEnableLoadMore(true);
                mAdapter.getFooterLayout().findViewById(R.id.progressbar).setVisibility(VISIBLE);
                ((TextView)mAdapter.getFooterLayout().findViewById(R.id.tv_state)).setText("加载更多");
            }
            else {
                mAdapter.setHasMore(false);
                mAdapter.setEnableLoadMore(false);
                mAdapter.getFooterLayout().findViewById(R.id.progressbar).setVisibility(GONE);
                ((TextView)mAdapter.getFooterLayout().findViewById(R.id.tv_state)).setText("已到底了");
            }
            mAdapter.notifyDataSetChanged();
        }else {
            setDataNotify();
        }
    }

    /**
     *  适配器
     */
    private void setDataNotify(){

        mAdapter = new PinnedSectionAdapter<PinnedHeaderObjectEntity<TradeRecordBean>>(data) {

            @Override
            public void setHasMore(boolean hasMore) {
                this.isHasMore = hasMore;
            }

            private SparseIntArray mRandomHeights;

            private boolean isHasMore=true;

            @Override
            protected void addItemTypes() {
                addItemType(BaseHeaderAdapter.TYPE_HEADER, R.layout.intergral_section);//header pinned
                addItemType(BaseHeaderAdapter.TYPE_DATA, R.layout.integral_item);//item
            }

            @Override
            protected void convert(com.chad.library.adapter.base.BaseViewHolder holder, PinnedHeaderObjectEntity<TradeRecordBean> item) {
                switch (holder.getItemViewType()) {
                    case BaseHeaderAdapter.TYPE_HEADER://
                        holder.getView(R.id.tv_integral_month).setVisibility(VISIBLE);
                        holder.setText(R.id.tv_integral_month, item.getPinnedHeaderName());
                        if (!TextUtils.isEmpty(tradeId)&&tradeId.equals("0")){
                            holder.getView(R.id.tv_integral_useing).setVisibility(VISIBLE);
                            holder.getView(R.id.tv_integral_getting).setVisibility(VISIBLE);
                            holder.setText(R.id.tv_integral_useing, item.getPinnedHeaderName1());
                            holder.setText(R.id.tv_integral_getting, item.getPinnedHeaderName2());
                        }else if(!TextUtils.isEmpty(tradeId)&&tradeId.equals("1")){
                            holder.getView(R.id.tv_integral_getting).setVisibility(VISIBLE);
                            holder.getView(R.id.tv_integral_useing).setVisibility(GONE);
                            holder.setText(R.id.tv_integral_getting, item.getPinnedHeaderName1());
                        }else if(!TextUtils.isEmpty(tradeId)&&tradeId.equals("2")){
                            holder.getView(R.id.tv_integral_getting).setVisibility(GONE);
                            holder.getView(R.id.tv_integral_useing).setVisibility(VISIBLE);
                            holder.setText(R.id.tv_integral_useing, item.getPinnedHeaderName2());
                        }
                        break;
                    case BaseHeaderAdapter.TYPE_DATA://item 数据

                        int position = holder.getLayoutPosition();

//                    if (recyclerview.getLayoutManager() instanceof StaggeredGridLayoutManager) {
//                        // 瀑布流布局记录随机高度，就不会导致Item由于高度变化乱跑，导致画分隔线出现问题
//                        // 随机高度, 模拟瀑布效果.
//
//                        if (mRandomHeights == null) {
//                            mRandomHeights = new SparseIntArray(getItemCount());
//                        }
//
//                        if (mRandomHeights.get(position) == 0) {
//                            mRandomHeights.put(position, dip2px(context, (int) (100 + Math.random() * 100)));
//                        }
//
//                        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//                        lp.height = mRandomHeights.get(position);
//                        holder.itemView.setLayoutParams(lp);
//
//                    }

                        /**
                         * 布局 xml
                         */
                        holder.setText(R.id.tv_integral_title, item.getData().getTypeText());
                        holder.setText(R.id.tv_integral_date, item.getData().getCreateTimeText());
                        holder.setText(R.id.tv_integral_count, item.getData().getMoney().intValue()+"");

                        break;
                }
            }

            @Override
            public long getItemId(int position) {
                return data.size()+getHeaderViewsCount()+getFooterViewsCount()+(isHasMore?1:0);
            }
        };

        View mView = View.inflate(context,R.layout.list_footer_view,null);
        mAdapter.addFooterView(mView);//加载更多

//瀑布流 布局
//        recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        //点击事件
        recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                switch (mAdapter.getItemViewType(position)) {
//                    case BaseHeaderAdapter.TYPE_DATA:
//                        PinnedHeaderObjectEntity<TradeRecordBean> entity = mAdapter.getData().get(position);
//                        Toast.makeText(context, entity.getPinnedHeaderName() + ", position=" + position + ", id=" + entity.getData(), Toast.LENGTH_SHORT).show();
//                        break;
//                    case BaseHeaderAdapter.TYPE_HEADER:
//                        entity = mAdapter.getData().get(position);
//                        Toast.makeText(context, "click, tag: " + entity.getPinnedHeaderName(), Toast.LENGTH_SHORT).show();
//                        break;
//                }
            }
        });

        OnHeaderClickListener headerClickListener = new OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, int id, int position) {
//                Toast.makeText(context, "click, tag: " + mAdapter.getData().get(position).getPinnedHeaderName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHeaderLongClick(View view, int id, int position) {
//                Toast.makeText(context, "long click, tag: " + mAdapter.getData().get(position).getPinnedHeaderName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHeaderDoubleClick(View view, int id, int position) {
//                Toast.makeText(context, "double click, tag: " + mAdapter.getData().get(position).getPinnedHeaderName(), Toast.LENGTH_SHORT).show();
            }
        };
        recyclerview.addItemDecoration(new PinnedHeaderItemDecoration.Builder(BaseHeaderAdapter.TYPE_HEADER).setDividerId(R.drawable.divider).enableDivider(true)
                .setHeaderClickListener(headerClickListener).create());

        recyclerview.setAdapter(mAdapter);

    }


}
