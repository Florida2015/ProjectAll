package com.hxxc.user.app.ui.product.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.hxxc.user.app.Event.PayEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.RedPackagePayBean;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.adapter.RedPackageForPayAdapter;
import com.hxxc.user.app.widget.materialdesignview.MyBottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2017/1/5.
 */

public class RedPackageDialogMD extends MyBottomSheetDialog implements View.OnClickListener {
    private RedPackageForPayAdapter adapter;
    private List<RedPackagePayBean> mDatas = new ArrayList<>();
    private FrameLayout empty_view;

    public RedPackageDialogMD(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_not_use:
                EventBus.getDefault().post(new PayEvent(PayEvent.TYPE_RED));
                adapter.setItem(-1);
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.dialog_bottom_redpackage, null);
        setContentView(view);
        findViewById(R.id.tv_not_use).setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);
        empty_view = (FrameLayout) findViewById(R.id.empty_view);
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        assert recyclerview != null;
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RedPackageForPayAdapter(getContext(), mDatas, recyclerview);
        adapter.rows = 1000;
        adapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                PayEvent event = new PayEvent(PayEvent.TYPE_RED);
                event.redPackageItemBean = adapter.mList.get(tag);
                EventBus.getDefault().post(event);
                dismiss();
            }

            @Override
            public void setOnItemLongClick(View view, int tag) {

            }
        });
        recyclerview.setAdapter(adapter);
        if(adapter.mList == null || adapter.mList.size() ==0){
            empty_view.setVisibility(View.VISIBLE);
        }else{
            empty_view.setVisibility(View.GONE);
        }
    }

    public void setDatas(List<RedPackagePayBean> datas) {
        if(null != adapter){
            adapter.notifyDatasChanged(datas,false);

            if(adapter.mList == null || adapter.mList.size() ==0){
                empty_view.setVisibility(View.VISIBLE);
            }else{
                empty_view.setVisibility(View.GONE);
            }
        }else{
            if(null != datas){
                mDatas.clear();
                mDatas.addAll(datas);
            }
        }
    }
}
