package com.hxxc.user.app.ui.mine.noticelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.UnreadMessageContentEvent;
import com.hxxc.user.app.Event.UnreadMessageEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.bean.NoticeBean;
import com.hxxc.user.app.contract.MessageV;
import com.hxxc.user.app.contract.presenter.MessagePresenter;
import com.hxxc.user.app.ui.base.SwipeRefreshBaseActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.ImUtils;
import com.hxxc.user.app.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/11/16.
 * 消息列表
 */

public class MessageActivity extends SwipeRefreshBaseActivity implements MessageV {

    @BindView(R.id.tv_financer)
    TextView tv_financer;
    @BindView(R.id.tv_content_1)
    TextView tv_content_1;
    @BindView(R.id.tv_time_1)
    TextView tv_time_1;
    @BindView(R.id.unread_chat)
    TextView unread_chat;

    @BindView(R.id.tv_news)
    TextView tv_news;
    @BindView(R.id.tv_content_2)
    TextView tv_content_2;
    @BindView(R.id.tv_time_2)
    TextView tv_time_2;

    @BindView(R.id.tv_notice)
    TextView tv_notice;
    @BindView(R.id.tv_content_3)
    TextView tv_content_3;
    @BindView(R.id.tv_time_3)
    TextView tv_time_3;
    @BindView(R.id.unread)
    TextView unread;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("消息列表");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().registerSticky(this);
        MessagePresenter presenter = new MessagePresenter(this);
        setPresenter(presenter);
        initView();

    }

    @Override
    protected void onResume() {
        mPresenter.doReflush();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
    }

    @OnClick({R.id.rl_chat, R.id.rl_news, R.id.rl_notices})
    public void onClick(View view) {
        if(BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.rl_chat:
                    ImUtils.getInstance().startPrivateChat(this);
                    unread_chat.setVisibility(View.INVISIBLE);
                    EventBus.getDefault().removeStickyEvent(UnreadMessageEvent.class);
                    EventBus.getDefault().removeStickyEvent(UnreadMessageContentEvent.class);
                    break;
                case R.id.rl_news:
                    startActivity(new Intent(this, NewsActivity.class));
                    break;
                case R.id.rl_notices:
                    startActivity(new Intent(this, NoticesActivity.class));
                    break;
            }
        }
    }

    public void onEventMainThread(UnreadMessageEvent event) {
        if (event.unread > 0) {
            unread_chat.setVisibility(View.VISIBLE);
            unread_chat.setText(event.unread + "");

            if ("暂无消息".equals(tv_content_1.getText().toString())) {
                tv_content_1.setText(SPUtils.geTinstance().get(Constants.LASTEST_MESSAGE, "暂无消息"));
            }
        } else {
            unread_chat.setVisibility(View.INVISIBLE);
        }
    }

    public void onEventMainThread(UnreadMessageContentEvent event) {
        if (!TextUtils.isEmpty(event.message)) {
            tv_content_1.setText(event.message);
            tv_time_1.setText(DateUtil.getTime2(event.time));
        }
    }

    @Override
    public void onGetNes(ContentBean contentBean) {
        tv_content_2.setText(contentBean.getTitle());
        tv_time_2.setText(contentBean.getCreateTimeStr2());
    }

    @Override
    public void onGetNotice(NoticeBean noticeBean) {
        if (null != noticeBean) {
            tv_content_3.setText(noticeBean.getUserMessageVo().getContent());
            tv_time_3.setText(noticeBean.getUserMessageVo().getCreateTimeStr2());
            if (noticeBean.getUnMsgCount() > 0) {
                unread.setVisibility(View.VISIBLE);
                unread.setText(noticeBean.getUnMsgCount() + "");
            } else {
                unread.setVisibility(View.INVISIBLE);
            }
        } else {
            unread.setVisibility(View.INVISIBLE);
        }
        setRefresh(false);
    }

}
