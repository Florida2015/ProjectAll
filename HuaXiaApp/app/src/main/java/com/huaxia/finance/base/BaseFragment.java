package com.huaxia.finance.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.framwork.loadingballs.BallView;

/**
 * Created by houwen.lai on 2016/2/3.
 */
public class BaseFragment extends Fragment {


    private ImageView img_empty;
    private TextView tv_reloading;
    private BallView ballview;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }



}
