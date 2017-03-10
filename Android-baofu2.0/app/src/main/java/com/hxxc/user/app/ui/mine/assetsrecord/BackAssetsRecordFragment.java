package com.hxxc.user.app.ui.mine.assetsrecord;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huaxiafinance.www.crecyclerview.cindicatorview.LazyFragment;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;
import com.hxxc.user.app.R;
import com.hxxc.user.app.data.entity.UnCollectedIncomeEntity;

/**
 * Created by chenqun on 2017/2/22.
 */

public class BackAssetsRecordFragment extends LazyFragment {
    private CRecyclerView view;
    private View inflate;

    public static BackAssetsRecordFragment newInstance(int orderStatus) {
        Bundle arguments = new Bundle();
        arguments.putInt("status", orderStatus);
        BackAssetsRecordFragment fragment = new BackAssetsRecordFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == inflate) {
            inflate = inflater.inflate(R.layout.pager_backassets, null);
            LinearLayout ll_title_view = (LinearLayout) inflate.findViewById(R.id.ll_title_view);
            view = new CRecyclerView(getContext())
                    .setRow(10)
                    .setParams("status",getArguments().getInt("status")+"")
                    .setView(UnCollectedIncomeEntity.class);
            ll_title_view.addView(view);
        }
        return inflate;
    }

    @Override
    protected void getDatas() {
        if (null != view)
            view.start();
    }
}
