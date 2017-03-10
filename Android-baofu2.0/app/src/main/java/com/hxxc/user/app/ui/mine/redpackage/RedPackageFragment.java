package com.hxxc.user.app.ui.mine.redpackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxxc.user.app.data.bean.RedPackageItemBean;
import com.hxxc.user.app.ui.vh.RedPackageVH;
import com.hxxc.user.app.widget.trecyclerview.TRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houwen.lai on 2016/10/27.
 * listview
 */

public class RedPackageFragment extends Fragment {


    private TRecyclerView mXRecyclerView;

    public static RedPackageFragment newInstance(String orderStatus) {
        Bundle arguments = new Bundle();
        arguments.putString("status",orderStatus);
        RedPackageFragment fragment = new RedPackageFragment();
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
        mXRecyclerView.setView(RedPackageVH.class);
        return mXRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mXRecyclerView != null) mXRecyclerView.fetch();

        if (mXRecyclerView.getAdapter()==null)return;

    }

    List<RedPackageItemBean> lists = new ArrayList<RedPackageItemBean>();

}
