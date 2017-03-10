package com.hxxc.user.app.ui.im;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hxxc.user.app.Event.UnreadMessageContentEvent;
import com.hxxc.user.app.Event.UnreadMessageEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.FinancialPlanner;
import com.hxxc.user.app.listener.IFinanceCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.ImUtils;
import com.hxxc.user.app.utils.SPUtils;

import java.util.Locale;

import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static com.hxxc.user.app.R.id.conversation;

/**
 * Created by chenqun on 2016/7/4.
 */
public class ChatActivity extends ToolbarActivity {

    private String mTargetId;
    private Conversation.ConversationType mConversationType;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void setTitle() {
        FinancialPlanner bean = SPUtils.geTinstance().getFinancer();
        if (null != bean) {
            if (!TextUtils.isEmpty(bean.getFname())) {
                mTitle.setText(bean.getFname());
            } else if (!TextUtils.isEmpty(bean.getUsername())) {
                mTitle.setText(bean.getUsername());
            } else {
                mTitle.setText(bean.getMobile());
            }
        } else {
            mTitle.setText("聊天");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        EventBus.getDefault().removeStickyEvent(UnreadMessageEvent.class);
        EventBus.getDefault().removeStickyEvent(UnreadMessageContentEvent.class);
        //设置用户信息提供者
        ImUtils.getInstance().setUserInfoProvider2();
        getIntentDate(intent);
        isReconnect(intent);

        Midhandler.refreshFinanceInfo( new IFinanceCallback() {
            @Override
            public void refreshFinance(FinancialPlanner financialPlanner) {
                ImUtils.getInstance().refreshUserInfoCache(financialPlanner);
            }
        });
    }

    private void getIntentDate(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
        enterFragment(mConversationType, mTargetId);
    }

    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }

    private void isReconnect(Intent intent) {
        String token = SPUtils.geTinstance().getImToken();
        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals("true")) {

                reconnect(token);
            } else {
                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
                    reconnect(token);
                } else {
                    enterFragment(mConversationType, mTargetId);
                }
            }
        }
    }

    private void reconnect(String token) {

        if (getApplicationInfo().packageName.equals(CommonUtil.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String s) {

                    enterFragment(mConversationType, mTargetId);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
