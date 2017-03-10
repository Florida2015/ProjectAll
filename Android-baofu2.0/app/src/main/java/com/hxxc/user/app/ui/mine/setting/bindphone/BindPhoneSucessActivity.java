package com.hxxc.user.app.ui.mine.setting.bindphone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.ThreadManager;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenqun on 2016/12/12.
 */

public class BindPhoneSucessActivity extends ToolbarActivity {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;

    @BindView(R.id.tv_count)
    TextView tv_count;

    public boolean a = true;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_success_find_password;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("修改成功");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        image.setImageResource(R.drawable.alter);
        tv_1.setText("手机号修改成功");
        tv_2.setText("后自动跳转到绑定手机界面");

        ThreadManager.getShortPool().execute(new Runnable(){
            @Override
            public void run() {
                for (int i = 3; i >= -1&& a; i--) {
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
                finish();
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
