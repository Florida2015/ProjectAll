package com.hxxc.user.app.listener;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.hxxc.user.app.ui.financial.FinancialDetailActivity;
import com.hxxc.user.app.ui.im.AMAPLocationActivity;
import com.hxxc.user.app.ui.mine.UserInfoActivity;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.utils.SPUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.LocationMessage;

/**
 * Created by chenqun on 2016/7/8.
 */
public class MyConversationBehaviorListener implements RongIM.ConversationBehaviorListener {
    @Override
    public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        if (null != userInfo) {
            LogUtils.d(userInfo.toString());
            String fid = SPUtils.geTinstance().getFinancer().getFinancialno();
            String userno = SPUtils.geTinstance().getImId();
            LogUtil.e(userInfo.getUserId());
            //TODO 打开
            if (!TextUtils.isEmpty(fid) && fid.equals(userInfo.getUserId())) {
                Intent in = new Intent();
                in.setClass(context, FinancialDetailActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
                return true;
            }else if(!TextUtils.isEmpty(userno) && userno.equals(userInfo.getUserId())){
                Intent intent = new Intent();
                intent.setClass(context, UserInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean onMessageClick(Context context, View view, Message message) {
        LogUtils.d("点击地图消息"+message.toString());
        if (message.getContent() instanceof LocationMessage) {//如果是地图，打开地图activity
            Intent intent = new Intent(context, AMAPLocationActivity.class);
            intent.putExtra("location", message.getContent());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMessageLinkClick(Context context, String s) {
        return false;
    }

    @Override
    public boolean onMessageLongClick(Context context, View view, Message message) {
        return false;
    }
}
