package com.hxxc.user.app.ui.product;

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

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.InvestHistoryBean;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.base.BaseFragment2;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.widget.verticalpager.CustRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenqun on 2016/9/23.
 */

public class BFragment extends BaseFragment2 {
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.recyclerview)
    CustRecycleView recyclerview;
    @BindView(R.id.empty_view)
    FrameLayout empty_view;
    private BAdapter mAdapter;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_product_b;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        recyclerview.setType(3);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new BAdapter(getActivity(), new ArrayList<InvestHistoryBean>(), recyclerview);
        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoadingmore && mAdapter.hasDatas() && mAdapter.isLoadmore) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItemPosition == mAdapter.getItemCount() - 1) {
                        mIsLoadingmore = true;
                        getDatas();
                    }
                }
            }
        });
    }

    private boolean mIsLoadingmore = false;

    private int a = 1;

    public void getDatas() {
        if (null == v) return;
        String pid = v.getDefaultPid();
        if (TextUtils.isEmpty(pid)) {
            isLoading = false;
            mIsLoadingmore = false;
            return;
        }
        ApiManager.getInstance().getInvestmentHistoryByPid(pid, a + "", 10 + "", new SimpleCallback<List<InvestHistoryBean>>() {
            @Override
            public void onNext(List<InvestHistoryBean> investHistoryBeen) {
                mAdapter.notifyDatasChanged(investHistoryBeen, a != 1);
                if (a == 1) {
                    progressbar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.GONE);
                        }
                    }, 1000);
                }
                mIsLoadingmore = false;
                if (null != investHistoryBeen && investHistoryBeen.size() > 0) {
                    empty_view.setVisibility(View.INVISIBLE);
                } else {
                    if (a == 1) {
                        empty_view.setVisibility(View.VISIBLE);
                    }
                }
                a++;
            }

            @Override
            public void onError() {
                mIsLoadingmore = false;
                progressbar.setVisibility(View.GONE);
                empty_view.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void initDatas() {
        super.initDatas();
        if (null != recyclerview) {
            recyclerview.post(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    getDatas();
                }
            });
        }
    }

    public static class BAdapter extends BaseAdapter2<InvestHistoryBean> {

        public BAdapter(Context mContext, List<InvestHistoryBean> mList, RecyclerView recyclerView) {
            super(mContext, mList, recyclerView);
        }

        @Override
        protected RecyclerView.ViewHolder onNomalViewHolder(ViewGroup parent) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_2_b, parent, false));
        }

        @Override
        protected void onBindNomalViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder mHolder = ((MyViewHolder) holder);
            InvestHistoryBean bean = mList.get(position);
            mHolder.tv_money.setText(CommonUtil.moneyType2(bean.getMoney()) + "元");
            mHolder.tv_phone.setText(bean.getMobile());
            mHolder.tv_time.setText(bean.getCreateTimeText());

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
                        mListener.setOnItemClick(holder.itemView, position);
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
