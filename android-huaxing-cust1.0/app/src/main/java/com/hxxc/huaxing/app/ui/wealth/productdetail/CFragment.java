package com.hxxc.huaxing.app.ui.wealth.productdetail;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.InvestHistoryBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseAdapter2;
import com.hxxc.huaxing.app.ui.wealth.BaseFragment2;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.wedgit.verticalpager.CustListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;

/**
 * Created by chenqun on 2016/9/23.
 */

public class CFragment extends BaseFragment2 {
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.recyclerview)
    CustListView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;
    private ArrayList<InvestHistoryBean> mList = new ArrayList<>();
    private CAdapter mAdapter;
    private String mPid;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_product_c;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        recyclerview.setType(3);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CAdapter(getActivity(), mList, recyclerview);
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoadingmore && mAdapter.hasDatas() && mAdapter.isLoadmore) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1) {
                        mIsLoadingmore = true;
                        initDatas(mPid);
                    }
                }
            }
        });
    }

    private boolean mIsLoadingmore = false;

    private int a = 1;

    private void initDatas(String pid) {
        Api.getClient().getInvestmentHistoryByPid(pid, a, 10).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<List<InvestHistoryBean>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
                if (a == 1) {
                    progressbar.setVisibility(View.GONE);
                    empty_view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNext(BaseBean<List<InvestHistoryBean>> listBaseBean) {
                LogUtil.e(listBaseBean.toString());
                mAdapter.notifyDatasChanged(listBaseBean.getModel(), a != 1);
                if (a == 1) {
                    progressbar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.GONE);
                        }
                    }, 1000);
                }


                mIsLoadingmore = false;
                if (null != listBaseBean.getModel() && listBaseBean.getModel().size() > 0) {
                    empty_view.setVisibility(View.INVISIBLE);
                } else {
                    if (a == 1) {
                        empty_view.setVisibility(View.VISIBLE);
                    }
                }
                a++;
            }
        });
    }

    @Override
    public void initDatas() {
        super.initDatas();
        if (null == v) return;
        mPid = v.getDefaultPid();
        if (TextUtils.isEmpty(mPid)) {
            isLoading = false;
            return;
        }
        recyclerview.post(new Runnable() {
            @Override
            public void run() {
                a = 1;
                initDatas(mPid);
            }
        });
    }

    public static class CAdapter extends BaseAdapter2<InvestHistoryBean> {

        public CAdapter(Context mContext, List mList, RecyclerView recyclerView) {
            super(mContext, mList, recyclerView);
        }

        @Override
        protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_2_c, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
            InvestHistoryBean bean = mList.get(position);
            MyViewHolder mHolder = ((MyViewHolder) holder);

            mHolder.tv_money.setText(CommonUtil.getMoneyGap(bean.getMoney()));
            mHolder.tv_phone.setText(bean.getMobile());
            mHolder.tv_time.setText(DateUtil.getPlusTime(new Date(bean.getCreateTime())));

            if (0 == position) {
                mHolder.view_padding.setVisibility(View.VISIBLE);
            } else {
                mHolder.view_padding.setVisibility(View.GONE);
            }

            if (position % 2 != 0) {
                mHolder.ll_content.setBackgroundColor(Color.parseColor("#fffafafa"));
            } else {
                mHolder.ll_content.setBackgroundColor(Color.parseColor("#ffffffff"));
            }
            if (null != mListener) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = holder.getLayoutPosition();
                        mListener.setOnItemClick(holder.itemView, i);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int i = holder.getLayoutPosition();
                        mListener.setOnItemLongClick(holder.itemView, i);
                        return false;
                    }
                });
            }
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public final View view_padding;
        public final TextView tv_phone;
        public final TextView tv_time;
        public final TextView tv_money;
        public final LinearLayout ll_content;

        public MyViewHolder(View view) {
            super(view);
            ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
            tv_phone = (TextView) view.findViewById(R.id.tv_phone);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_money = (TextView) view.findViewById(R.id.tv_money);
            view_padding = (View) view.findViewById(R.id.view_padding);
        }
    }
}
