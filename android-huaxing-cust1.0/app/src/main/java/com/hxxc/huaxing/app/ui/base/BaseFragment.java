package com.hxxc.huaxing.app.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/20.
 *
 */
public abstract class BaseFragment extends RxFragment {

    protected Context mContext;

    /**
     * url passed into fragment
     */
    public static String EXTRA_URL = "url";
    private String mUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        if (getArguments() != null) {
            mUrl = getArguments().getString(EXTRA_URL);
        }

        initDagger();
    }

    protected abstract void initDagger();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            return inflater.inflate(getContentViewID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViewsAndEvents(view);
    }

    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getContentViewID();


    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViewsAndEvents(View rootView);

    /**
     * toast
     */
    public void toast(@NonNull CharSequence text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * toast
     */
    public void toast(@StringRes int stringRes) {
        Toast.makeText(mContext, stringRes, Toast.LENGTH_SHORT).show();
    }

}
