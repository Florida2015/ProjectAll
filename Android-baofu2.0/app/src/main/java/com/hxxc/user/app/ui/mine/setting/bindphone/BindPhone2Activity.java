package com.hxxc.user.app.ui.mine.setting.bindphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hxxc.user.app.Event.CloseBindPhoneEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.ui.base.ToolbarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/12/7.
 */

public class BindPhone2Activity extends ToolbarActivity {

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bindphone2;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("选择验证方式");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.tv_1, R.id.tv_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                startActivity(new Intent(this, BindPhone3Activity.class));
                break;
            case R.id.tv_2:
                Intent intent = new Intent(this, BindPhone4Activity.class);
                intent.putExtra("type", BindPhone4Activity.TYPE_B);
                startActivity(intent);
                break;
        }
    }

    public void onEventMainThread(CloseBindPhoneEvent event) {
        finish();
    }
}
