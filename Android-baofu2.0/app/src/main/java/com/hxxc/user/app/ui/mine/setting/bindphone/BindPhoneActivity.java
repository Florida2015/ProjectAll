package com.hxxc.user.app.ui.mine.setting.bindphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hxxc.user.app.Event.BindPhoneEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.StringUtil;
import com.hxxc.user.app.widget.LeftAndRightTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/12/7.
 */

public class BindPhoneActivity extends ToolbarActivity {
    @BindView(R.id.tv_1)
    LeftAndRightTextView tv_1;
    @BindView(R.id.tv_2)
    LeftAndRightTextView tv_2;
    @BindView(R.id.tv_bindStatus)
    TextView tv_bindStatus;
    private UserInfo userInfo;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bindphone;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("绑定手机");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        userInfo = SPUtils.geTinstance().getUserInfo();
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        tv_bindStatus.setText(userInfo.getAccountStatusStr());
        tv_1.setRightText(StringUtil.getRatStr2(userInfo.getMobile()));
    }

    @OnClick({R.id.tv_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_2:
                startActivity(new Intent(this, BindPhone2Activity.class));
                break;
        }
    }

    public void onEventMainThread(BindPhoneEvent event) {
        userInfo = SPUtils.geTinstance().getUserInfo();
        initView();
    }
}
