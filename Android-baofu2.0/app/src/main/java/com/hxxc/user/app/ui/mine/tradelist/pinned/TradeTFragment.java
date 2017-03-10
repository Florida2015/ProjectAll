package com.hxxc.user.app.ui.mine.tradelist.pinned;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxxc.user.app.Event.CyclerEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.mine.tradelist.TradeFragment;
import com.hxxc.user.app.ui.vh.TradeVH;
import com.hxxc.user.app.widget.trecyclerview.TRecyclerView;

/**
 * Created by Administrator on 2017/3/8.
 */

public class TradeTFragment extends Fragment {

    PinnedSelectCyc1View recyclerView;

    public static TradeTFragment newInstance(String orderStatus) {
        Bundle arguments = new Bundle();
        arguments.putString("status",orderStatus);
        TradeTFragment fragment = new TradeTFragment();
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
        recyclerView = new PinnedSelectCyc1View(getActivity());
        recyclerView.setParam("trade",getArguments().getString("status"));
        recyclerView.setParamKey(getArguments().getString("status"));
        return recyclerView;

//        if (container == null) {
//            // Currently in a layout without a container, so no
//            // reason to create our view.
//            return null;
//        }
//        LayoutInflater myInflater = (LayoutInflater) getActivity()
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = myInflater.inflate(R.layout.trade_fragment, container,
//                false);
//        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (recyclerView!=null)
        recyclerView.fetch();
    }

}
