package com.hxxc.huaxing.app.ui.mine.lend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxxc.huaxing.app.data.bean.LendItemBean;
import com.hxxc.huaxing.app.ui.mine.lend.vh.LendItemVH;
import com.hxxc.huaxing.app.wedgit.trecyclerview.TRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/22.
 * 还款中
 */
public class LendFragment extends Fragment {

    private TRecyclerView mXRecyclerView;

    public static LendFragment newInstance(String orderStatus) {
        Bundle arguments = new Bundle();
        arguments.putString("status", orderStatus);
        LendFragment fragment = new LendFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mXRecyclerView = new TRecyclerView(getActivity());
        mXRecyclerView.setParam("status", getArguments().getString("status"));
        mXRecyclerView.setView(LendItemVH.class);
        return mXRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mXRecyclerView != null) mXRecyclerView.fetch();

        if (mXRecyclerView.getAdapter() == null) return;

//        InitData();

    }

    List<LendItemBean> lists = new ArrayList<LendItemBean>();

    public void InitData() {
        if (lists == null) lists = new ArrayList<LendItemBean>();
        lists.clear();
        for (int i = 0; i < 10; i++) {
            LendItemBean bean = new LendItemBean();
            bean.setProductName("宝马X3抵押借款");
            bean.setYearRate(12.5);
            bean.setPeriods(12);
//            bean.setLendDiscount("现金券50元");
//            bean.setLendType("还款中");
            bean.setMoney(BigDecimal.valueOf(120123.01));
            lists.add(bean);
        }
        mXRecyclerView.getAdapter().setBeans(lists, 1);
        mXRecyclerView.getAdapter().notifyDataSetChanged();

    }


}
