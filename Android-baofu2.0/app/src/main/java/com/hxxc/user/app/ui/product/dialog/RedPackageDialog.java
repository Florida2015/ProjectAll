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

import com.hxxc.user.app.Event.PayEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.RedPackagePayBean;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.adapter.RedPackageForPayAdapter;
import com.hxxc.user.app.widget.verticalpager.CustRecycleView;
import com.hxxc.user.app.widget.verticalpager.DragDialogLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/10/27.
 */

public class RedPackageDialog extends Dialog implements View.OnClickListener {
    private RedPackageForPayAdapter adapter;
    private List<RedPackagePayBean> mDatas = new ArrayList<>();
    private FrameLayout empty_view;

    public RedPackageDialog(Context context) {
        super(context, R.style.AddressDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        setContentView(R.layout.dialog_redpackage);
        findViewById(R.id.tv_not_use).setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);
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
