package com.hxxc.user.app.ui.product.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hxxc.user.app.Event.PayEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Account;
import com.hxxc.user.app.ui.adapter.BankcardListForPayAdapter;
import com.hxxc.user.app.ui.adapter.BaseAdapter2;
import com.hxxc.user.app.ui.product.BaofuBingCardActivity;
import com.hxxc.user.app.widget.verticalpager.CustRecycleView;
import com.hxxc.user.app.widget.verticalpager.DragDialogLayout;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/10/28.
 */

public class BankCardListDialog extends Dialog implements View.OnClickListener {
    private BankcardListForPayAdapter adapter;
    private ArrayList<Account> mDatas = new ArrayList<>();

    public BankCardListDialog(Context context) {
        super(context, R.style.AddressDialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_more:
                Intent intent = new Intent(getContext(), BaofuBingCardActivity.class);
                intent.putExtra("type", BaofuBingCardActivity.TYPE_FROM_PAY);
                getContext().startActivity(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_bankcard);
        findViewById(R.id.iv_close).setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("选择支付方式");

        DragDialogLayout content = (DragDialogLayout) findViewById(R.id.dl_content);
        content.setNextPageListener(new DragDialogLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                content.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }
        });
        CustRecycleView recyclerview = (CustRecycleView) findViewById(R.id.recyclerview);
        recyclerview.setType(2);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        View footerView = View.inflate(getContext(), R.layout.item_dialog_bankcard, null);
        footerView.findViewById(R.id.tv_more).setOnClickListener(this);

        adapter = new BankcardListForPayAdapter(getContext(), mDatas, recyclerview);
        adapter.addFooterView(footerView);
        adapter.rows = 1000;
        adapter.setOnItemClick(new BaseAdapter2.OnItemClick() {
            @Override
            public void setOnItemClick(View view, int tag) {
                PayEvent event = new PayEvent(PayEvent.TYPE_Account);
                event.account = adapter.mList.get(tag);
                EventBus.getDefault().post(event);
                dismiss();
            }

            @Override
            public void setOnItemLongClick(View view, int tag) {

            }
        });
        recyclerview.setAdapter(adapter);
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

    public void setDatas(List<Account> datas) {
        if (null != adapter) {
            adapter.notifyDatasChanged(datas, false);
        } else {
            if (null != datas) {
                mDatas.clear();
                mDatas.addAll(datas);
            }
        }
    }

    public void setDatas(List<Account> datas, int a) {
        if (null != adapter) {
            adapter.setItem(a);
            adapter.notifyDatasChanged(datas, false);
        }
    }
}
