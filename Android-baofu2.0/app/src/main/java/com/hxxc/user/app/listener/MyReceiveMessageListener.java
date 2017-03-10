package com.hxxc.user.app.listener;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.UnreadMessageContentEvent;
import com.hxxc.user.app.utils.SPUtils;

import de.greenrobot.event.EventBus;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by chenqun on 2016/11/16.
 */

public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {
    /**
     * 收到消息的处理。
     *
     * @param message 收到的消息实体。
     * @param left    剩余未拉取消息数目。
     * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
     */
    @Override
    public boolean onReceived(Message message, int left) {
        //开发者根据自己需求自行处理
        String str;
        MessageContent content = message.getContent();
        if(content instanceof LocationMessage){
            str = ((LocationMessage) message.getContent()).getPoi();
        }else if(content instanceof ImageMessage){
            str = "图片";
        }else if(content instanceof VoiceMessage){
            str = "语音";
        }else if(content instanceof TextMessage){
            str = ((TextMessage) message.getContent()).getContent();
        }else{
            str = "...";
        }
        SPUtils.geTinstance().put(Constants.LASTEST_MESSAGE,str);
        EventBus.getDefault().postSticky(new UnreadMessageContentEvent(str, message.getReceivedTime()));
        return false;
    }
}
