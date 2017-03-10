package com.hxxc.user.app.widget.trecyclerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.Event.CyclerEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.data.bean.IntegralBean;
import com.hxxc.user.app.ui.base.RxManage;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * @author Administrator
 */
public class TRecyclerView<T extends BaseEntity.ListBean> extends LinearLayout {
    @BindView(R.id.swiperefresh_order)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.recyclerview_order)
    RecyclerView recyclerview;
    @BindView(R.id.ll_emptyview)
    LinearLayout ll_emptyview;
    @BindView(R.id.tv_warn_text)
    TextView tv_warn_text;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private CoreAdapter<T> mCommAdapter = new CoreAdapter<>();
    private int begin = 0;
    private boolean isRefreshable = true, isHasHeadView = false, isEmpty = false;
    private T model;
    public RxManage mRxManage = new RxManage();
    private Map<String, String> param = new HashMap<>();
    private boolean flagFirstPositon=false;//可见第一位置

    public TRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public TRecyclerView(Context context, AttributeSet att) {
        super(context, att);
        init(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mRxManage.clear();
    }

    public void init(Context context) {
        this.context = context;
        View layout = LayoutInflater.from(context).inflate(
                R.layout.layout_list_recyclerview, null);
        layout.setLayoutParams(new LayoutParams(
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
        recyclerview.setAdapter(mCommAdapter);
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

                    mRxManage.post(CyclerEvent.class.getSimpleName(),new CyclerEvent(firstItemPosition));

                    System.out.println(lastItemPosition + "   " + firstItemPosition);
                }

                if (recyclerview.getAdapter() != null
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerview.getAdapter()
                        .getItemCount() && mCommAdapter.isHasMore)
                    fetch();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int arg0, int arg1) {
                super.onScrolled(recyclerView, arg0, arg1);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        mRxManage.on(C.EVENT_DEL_ITEM, (arg0) -> mCommAdapter.removeItem((Integer) arg0));
        mRxManage.on(C.EVENT_UPDATE_ITEM, (arg0) -> mCommAdapter.upDateItem(((UpDateData) arg0).i, ((UpDateData) arg0).oj));
        ll_emptyview.setOnClickListener((view -> reFetch()));
    }

    public CoreAdapter getAdapter() {
        return mCommAdapter;
    }

    /**
     * 底部风险提示
     */
    public void setWaningText(String text){
        tv_warn_text.setText(text);
    }
    public void setWaningText(String text,int resource){
        //在左侧添加图片
        Drawable drawable= getResources().getDrawable(resource);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        tv_warn_text.setCompoundDrawables(drawable, null, null, null);
        tv_warn_text.setTextColor(getResources().getColor(R.color.grey_text));
        tv_warn_text.setCompoundDrawablePadding(4);//设置图片和text之间的间距
        tv_warn_text.setText(text);
    }

    public void setRefreshing(boolean i) {
        swiperefresh.setRefreshing(i);
    }

    public TRecyclerView setIsRefreshable(boolean i) {
        isRefreshable = i;
        swiperefresh.setEnabled(i);
        return this;
    }

    /**
     *
     * @param flagFirstPositon
     * @return
     */
    public TRecyclerView setReturnFirstPosition(boolean flagFirstPositon) {
        this.flagFirstPositon = flagFirstPositon;
        return this;
    }

    public RecyclerView getRecyclerview() {
        return recyclerview;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swiperefresh;
    }

    public TRecyclerView setHeadView(Class<? extends BaseViewHolder> cla) {
        if (cla == null) {
            isHasHeadView = false;
            this.mCommAdapter.setHeadViewType(0, cla, null);
        } else
            try {
                Object obj = ((Activity) context).getIntent().getSerializableExtra(C.HEAD_DATA);
                int mHeadViewType = ((BaseViewHolder) (cla.getConstructor(View.class)
                        .newInstance(new View(context)))).getType();
                this.mCommAdapter.setHeadViewType(mHeadViewType, cla, obj);
                isHasHeadView = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return this;
    }

    public TRecyclerView setFooterView(Class<? extends BaseViewHolder> cla) {
        this.begin = 0;
        try {
            int mFooterViewType = ((BaseViewHolder) (cla.getConstructor(View.class)
                    .newInstance(new View(context)))).getType();
            this.mCommAdapter.setFooterViewType(mFooterViewType, cla);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public void setEmpty() {
        if (!isHasHeadView &&!isEmpty) {//!isHasHeadView &&!isEmpty
            isEmpty = true;
            ll_emptyview.setVisibility(View.VISIBLE);
            swiperefresh.setVisibility(View.GONE);
        }else{
            ll_emptyview.setVisibility(View.GONE);
            swiperefresh.setVisibility(View.VISIBLE);
            if (isHasHeadView){
                mCommAdapter.setHeadViewData(null);
            }
        }
    }

    public TRecyclerView setView(Class<? extends BaseViewHolder<T>> cla) {
        try {
            BaseViewHolder mIVH = ((BaseViewHolder) (cla.getConstructor(View.class)
                    .newInstance(new View(context))));
            int mType = mIVH.getType();
            this.model = ((Class<T>) ((ParameterizedType) (cla
                    .getGenericSuperclass())).getActualTypeArguments()[0])
                    .newInstance();// 根据类的泛型类型获得model的实例
            this.mCommAdapter.setViewType(mType, cla);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public TRecyclerView setParam(String key, String value) {
        this.param.put(key, value);
        return this;
    }

    public TRecyclerView setData(List<T> datas) {
        if (isEmpty) {
            ll_emptyview.setVisibility(View.GONE);
//            swiperefresh.setVisibility(View.VISIBLE);
        }
        mCommAdapter.setBeans(datas, 1);
        return this;
    }

    public void reFetch() {
        LogUtil.d("trecyclerView=reFetch");
        this.begin = 0;
        if (param.containsKey("type_status")&&param.get("type_status").equals("integral"))
            fetchIntegral();
        else  fetch();
    }

    public void fetch() {
        if (param.containsKey("type_status")&&param.get("type_status").equals("integral")){
            fetchIntegral();
            return;
        }

        begin++;
        if (isEmpty) {
            ll_emptyview.setVisibility(View.GONE);
            swiperefresh.setVisibility(View.VISIBLE);
        }
        if (model == null) {
            Log.e("model", "null");
            return;
        }
        LogUtil.d("trecyclerView=model");
        model.setParam(param);

        mCommAdapter.setParam(model.param);

        if (model.getPageAt(begin)==null)return;
//        swiperefresh.setRefreshing(true);
        LogUtil.d("trecyclerView=getPageAt");

//        mRxManage.add(model.getPageAt(begin)
//                .subscribe(new Action1<BaseResult<List<T>>>() {//BaseBean<List<T>>
//                            @Override
//                            public void call(BaseResult<List<T>> subjects) {//Data<T>
////                                LogUtil.d("success=",subjects.toString());
//                                swiperefresh.setRefreshing(false);
//                                if (subjects.getData()!=null&&subjects.getData().size()>0)
//                                mCommAdapter.setBeans(subjects.getData(), begin);
//                                else {
//                                    mCommAdapter.setBeans(subjects.getData(), begin);
//                                    begin--;
//                                    if (begin<=1&&mCommAdapter!=null&&mCommAdapter.getItemCount()<=1){
//                                        isEmpty=false;
//                                        setEmpty();
//                                    }
//                                }
//                            }
//                        }, new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable e) {
//                                swiperefresh.setRefreshing(false);
//                                LogUtil.d("Throwable=",e.getMessage());
//                                begin--;
//                                setEmpty();
//                                e.printStackTrace();
//                            }
//                        }
//                ));
        model.getPageAt(begin)
                .subscribe(new Action1<BaseResult<List<T>>>() {//BaseBean<List<T>>
                               @Override
                               public void call(BaseResult<List<T>> subjects) {//Data<T>
//                                LogUtil.d("success=",subjects.toString());
                                   swiperefresh.setRefreshing(false);
                                   if (subjects.getData()!=null&&subjects.getData().size()>0) {

                                       mCommAdapter.setBeans(subjects.getData(), begin);
                                   }else {
                                       mCommAdapter.setBeans(subjects.getData(), begin);
                                       begin--;
                                       if (begin<=1&&mCommAdapter!=null){
//                                           isEmpty=false;
//                                           setEmpty();
                                       }
                                   }
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable e) {
                                   swiperefresh.setRefreshing(false);
                                   LogUtil.d("Throwable=",e.getMessage());
                                   begin--;
                                   isEmpty=false;
                                   setEmpty();
                                   e.printStackTrace();
                               }
                           }
                );
    }

    public void fetchIntegral() {
        begin++;
        if (isEmpty) {
            ll_emptyview.setVisibility(View.GONE);
//            swiperefresh.setVisibility(View.VISIBLE);
        }
        if (model == null) {
            Log.e("model", "null");
            return;
        }
        model.setParam(param);
        if (model.getPageAt(begin)==null)return;
        swiperefresh.setRefreshing(true);
        mRxManage.add(model.getPageAt(begin)
                .subscribe(new Action1<BaseResult<IntegralBean<List<T>>>>() {//BaseBean<List<T>>
                               @Override
                               public void call(BaseResult<IntegralBean<List<T>>> subjects) {//Data<T>
//                                LogUtil.d("success=",subjects.toString());
                                   swiperefresh.setRefreshing(false);
                                   if (subjects.getData().getRecordList().size()>0)
                                       mCommAdapter.setBeans(subjects.getData().getRecordList(), begin);
                                   else {
                                       mCommAdapter.setBeans(subjects.getData().getRecordList(), begin);
                                       begin--;
                                       if (begin<=1&&mCommAdapter!=null){
                                           isEmpty=false;
                                           setEmpty();
                                       }
                                   }
                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable e) {
                                   swiperefresh.setRefreshing(false);
                                   LogUtil.d("Throwable=",e.getMessage());
                                   begin--;
//                                setEmpty();
                                   e.printStackTrace();
                               }
                           }
                ));
    }

    public class UpDateData {
        public int i;
        public T oj;

        public UpDateData(int i, T oj) {
            this.i = i;
            this.oj = oj;
        }
    }


    public class CoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        protected List<T> mItemList = new ArrayList<>();
        public boolean isHasMore = true;
        public int viewtype, isHasFooter = 1, isHasHader = 0, mHeadViewType;
        public Object mHeadData;
        public Class<? extends BaseViewHolder> mItemViewClass, mHeadViewClass, mFooterViewClass = CommFooterVH.class;
        public int mFooterViewType = CommFooterVH.LAYOUT_TYPE;

        public Map<String, String> mParam;

        public void setParam(Map<String, String> param){
            mParam = param;
        }

        public Map<String, String> getParam(){
            return mParam;
        }

        public void setViewType(int i, Class<? extends BaseViewHolder> cla) {
            this.isHasMore = true;
            this.viewtype = i;
            this.mItemList = new ArrayList<>();
            this.mItemViewClass = cla;
            notifyDataSetChanged();
        }

        public void setHeadViewType(int i, Class<? extends BaseViewHolder> cla, Object data) {
            if (cla == null) {
                this.isHasHader = 0;
            } else {
                this.isHasHader = 1;
                this.mHeadViewType = i;
                this.mHeadViewClass = cla;
                this.mHeadData = data;
                notifyDataSetChanged();
            }
        }

        public void setHeadViewData(Object data) {
            this.mHeadData = data;
        }

        public void setFooterViewType(int i, Class<? extends BaseViewHolder> cla) {
            this.mFooterViewType = i;
            this.mFooterViewClass = cla;
            this.mItemList = new ArrayList<>();
        }

        @Override
        public int getItemViewType(int position) {
            LogUtil.d("getItemViewType="+position);
            return isHasHader == 1 ? (position == 0 ? mHeadViewType
                    : (position + 1 == getItemCount() ? mFooterViewType : viewtype))
                    : (position + 1 == getItemCount() ? mFooterViewType : viewtype);
        }

        @Override
        public int getItemCount() {
            LogUtil.d("getItemCount="+mItemList.size() + isHasFooter + isHasHader);
            return mItemList.size() + isHasFooter + isHasHader;
        }

        public void setBeans(List<T> datas, int begin) {
            if (datas == null) datas = new ArrayList<>();
            this.isHasMore = datas.size() >= C.PAGE_COUNT;
            LogUtil.d("trcyclerView isHasMore ="+isHasMore);
            if (begin > 1) {
                this.mItemList.addAll(datas);
            } else {
                this.mItemList.clear();
                this.mItemList.addAll(datas);
            }
            notifyDataSetChanged();
        }

        /**
         * 获取某条数据
         * @param firstPosition
         * @return
         */
        public T getPositionData(int firstPosition){
            return mItemList.get(firstPosition);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LogUtil.d("onCreateViewHolder viewType="+viewType + "  mFooterViewType=" + mFooterViewType);
            try {
                boolean isFoot = viewType == mFooterViewType;
                return (RecyclerView.ViewHolder) (viewType == mHeadViewType ? mHeadViewClass.getConstructor(View.class).newInstance(LayoutInflater.from(parent.getContext()).inflate(mHeadViewType, parent, false))
                        : (RecyclerView.ViewHolder) (isFoot ? mFooterViewClass : mItemViewClass).getConstructor(View.class).newInstance( LayoutInflater.from(parent.getContext()).inflate(isFoot ? mFooterViewType: viewtype, parent,false)));
            } catch (Exception e) {
                LogUtil.d("ViewHolderException", "onCreateViewHolder十有八九是xml写错了,哈哈");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            LogUtil.d("onBindViewHolder", "onBindViewHolder  position="+position+"_isHasMore="+isHasMore+" _getItemCount="+getItemCount());
            ((BaseViewHolder) holder).onBindViewHolder(holder.itemView,position,
                    position + 1 == getItemCount() ?
                            (isHasMore ? new Object(): null) :
                            isHasHader == 1 && position == 0 ? mHeadData: mItemList.get(position - isHasHader));

//            if (position==0&&flagFirstPositon){//是否显示
//                mRxManage.post(CyclerEvent.class.getSimpleName(),new CyclerEvent(0));
//
//            }
            ((BaseViewHolder) holder).onBindViewHolder(holder.itemView,position,
                    position + 1 == getItemCount() ?
                            (isHasMore ? new Object(): null) :
                            isHasHader == 1 && position == 0 ? mHeadData: mItemList.get(position - isHasHader),param,isHasMore,isHasHader);

            //是否显示
            if (position==0) {
                ((BaseViewHolder) holder).onBindViewHolder(holder.itemView,position,
                        position + 1 == getItemCount() ?
                                (isHasMore ? new Object(): null) :
                                isHasHader == 1 && position == 0 ? mHeadData: mItemList.get(position - isHasHader),
                        0,null,param);
            }

            else if(position==1){

                    ((BaseViewHolder) holder).onBindViewHolder(holder.itemView,position,
                            position + 1 == getItemCount() ?
                                    (isHasMore ? new Object(): null) :
                                    isHasHader == 1 && position == 0 ? mHeadData: mItemList.get(position - isHasHader),
                            position-1,
                            position + 1 == getItemCount() ?
                                    (isHasMore ? new Object(): null) :
                                    isHasHader == 1 && position == 0 ? mHeadData: mItemList.get(position-1) ,param);


            }else{
                ((BaseViewHolder) holder).onBindViewHolder(holder.itemView,position,
                        position + 1 == getItemCount() ?
                                (isHasMore ? new Object(): null) :
                                isHasHader == 1 && position == 0 ? mHeadData: mItemList.get(position - isHasHader),
                        position-1,
                        position + 1 == getItemCount() ?
                                (isHasMore ? new Object(): null) :
                                isHasHader == 1 && position == 0 ? mHeadData: mItemList.get(position-1 - isHasHader),param
                );
            }
        }

        public void removeItem(int position) {
            mItemList.remove(position);
            notifyItemRemoved(position);
        }

        public void upDateItem(int position, T item) {
            mItemList.remove(position);
            mItemList.add(position, item);
            notifyItemChanged(position);
        }
    }
}