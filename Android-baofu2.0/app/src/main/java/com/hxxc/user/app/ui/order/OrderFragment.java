package com.hxxc.user.app.ui.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxxc.user.app.data.bean.OrderItemBean;
import com.hxxc.user.app.ui.vh.OrderVH;
import com.hxxc.user.app.widget.trecyclerview.TRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/10/27.
 * 订单列表
 */

public class OrderFragment extends Fragment {


    private TRecyclerView mXRecyclerView;

    public static OrderFragment newInstance(String orderStatus) {
        Bundle arguments = new Bundle();
        arguments.putString("status",orderStatus);
        OrderFragment fragment = new OrderFragment();
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
        mXRecyclerView.setParam("status",getArguments().getString("status"));
        mXRecyclerView.setView(OrderVH.class);
        return mXRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mXRecyclerView != null) mXRecyclerView.fetch();

        if (mXRecyclerView.getAdapter()==null)return;

//        InitData();

    }

    List<OrderItemBean> lists = new ArrayList<OrderItemBean>();

    public void InitData(){
        if (lists==null)lists = new ArrayList<OrderItemBean>();
        lists.clear();
        for (int i=0;i<10;i++){
            OrderItemBean bean = new OrderItemBean();
//            bean.setAwordType(getArguments().getInt("status"));
            bean.setProductName("华信增A");
            bean.setYearRate(12.50);
            bean.setPeriods(12);
            bean.setProductData("2016-10-12");
//            bean.setDescription("最低投资金额1万元\n使用产品：华信财\n赠送来源：新手注册\n有效期2016-7-8到2016-8-12");
//            bean.setLendDiscount("现金券50元");
//            bean.setLendType("还款中");
            bean.setAmount(BigDecimal.valueOf(12000));
            lists.add(bean);
        }
        mXRecyclerView.getAdapter().setBeans(lists,1);
        mXRecyclerView.getAdapter().notifyDataSetChanged();

    }
}
