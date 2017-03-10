package com.hxxc.user.app.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.ThreadManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Created by chenqun on 2016/11/4.
 */

public class FindPasswordSuccessActivity extends ToolbarActivity {
    @BindView(R.id.tv_count)
    TextView tv_count;

    public boolean a = true;
    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_success_find_password;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("重置成功");
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ThreadManager.getShortPool().execute(new Runnable(){
            @Override
            public void run() {
                for (int i = 3; i >= -1 && a; i--) {
                    handelr.sendEmptyMessage(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private Handler handelr = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what<0){
//                EventBus.getDefault().post(new MainEvent(3).setLoginType(MainEvent.FROMFINDPASSWORD));
                AppManager.getAppManager().finishActivity(ForgetpasswordActivity.class);
                finish();
//                startActivity(new Intent(FindPasswordSuccessActivity.this, LoginActivity.class).putExtra("startType",0));//Extra("startType",0)
            }else{
                tv_count.setText(msg.what+"秒");
            }
        }
    };

    @Override
    protected void onDestroy() {
        a = false;
        super.onDestroy();
    }
}
