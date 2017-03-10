package com.hxxc.user.app.ui.mine.equitynote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxxc.user.app.data.bean.RedPackageItemBean;
import com.hxxc.user.app.ui.vh.EquityNoteVH;
import com.hxxc.user.app.widget.trecyclerview.TRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/11/3.
 * 权益说明
 *
 */

public class EquityNoteFragment extends Fragment {

    private TRecyclerView mXRecyclerView;

    public static EquityNoteFragment newInstance(String orderStatus) {
        Bundle arguments = new Bundle();
        arguments.putString("status",orderStatus);
        EquityNoteFragment fragment = new EquityNoteFragment();
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
        mXRecyclerView.setView(EquityNoteVH.class);
        return mXRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (mXRecyclerView != null) mXRecyclerView.reFetch();

//        if (mXRecyclerView.getAdapter()==null)return;

//        InitData();

    }
    List<RedPackageItemBean> lists = new ArrayList<RedPackageItemBean>();

//    public void InitData(){
//        if (lists==null)lists = new ArrayList<RedPackageItemBean>();
//        lists.clear();
//        for (int i=0;i<10;i++){
//            RedPackageItemBean bean = new RedPackageItemBean();
//            bean.setType(getArguments().getString("status"));
//            bean.setCreateTimeStr("2016-10-12");
//            bean.setDescription("最低投资金额1万元\n使用产品：华信财\n赠送来源：新手注册\n有效期2016-7-8到2016-8-12");
////            bean.setLendDiscount("现金券50元");
////            bean.setLendType("还款中");
//            bean.setMoney(23.01);
//            lists.add(bean);
//        }
//        mXRecyclerView.getAdapter().setBeans(lists,1);
//        mXRecyclerView.getAdapter().notifyDataSetChanged();
//
//    }

}
