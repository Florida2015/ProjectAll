package com.hxxc.user.app.ui.product.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.hxxc.user.app.Event.BindBankEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.BankInfo;
import com.hxxc.user.app.ui.adapter.BankcardListForAddAdapter;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.widget.verticalpager.CustRecycleView;
import com.hxxc.user.app.widget.verticalpager.DragDialogLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/11/1.
 */

public class BankCardListDialogForAdd extends Dialog implements View.OnClickListener {
    private BankcardListForAddAdapter adapter;
    private final List<BankInfo> bankInfos = new ArrayList<>();
    private FrameLayout empty_view;

    public BankCardListDialogForAdd(Context context) {
        super(context, R.style.AddressDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_sure:
                if(adapter.mList != null && adapter.mList.size() > 0){
                    EventBus.getDefault().post(new BindBankEvent(adapter.mList.get(adapter.getItem())));
                }
                dismiss();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_bankcard);
        findViewById(R.id.iv_close).setOnClickListener(this);
        findViewById(R.id.tv_sure).setOnClickListener(this);
        empty_view = (FrameLayout) findViewById(R.id.empty_view);
        DragDialogLayout content = (DragDialogLayout) findViewById(R.id.dl_content);
        content.setNextPageListener(new DragDialogLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                empty_view.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }
        });

        CustRecycleView recyclerview = (CustRecycleView) findViewById(R.id.recyclerview);
        recyclerview.setType(3);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BankcardListForAddAdapter(getContext(),bankInfos , recyclerview);
        adapter.rows = 1000;
        adapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {

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
        initPosition();
    }

    private void initPosition() {
        // 获取到窗体
        Window window = getWindow();
        // 获取到窗体的属性
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        // 让对话框展示到屏幕的下边
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    public void setDatas(List<BankInfo> datas) {
        if(null != adapter){
            adapter.notifyDatasChanged(datas,false);
            if(adapter.mList == null || adapter.mList.size() ==0){
                empty_view.setVisibility(View.VISIBLE);
            }else{
                empty_view.setVisibility(View.GONE);
            }
        }else {

            if(null != datas){
                bankInfos.clear();
                bankInfos.addAll(datas);
            }
        }
    }
}
