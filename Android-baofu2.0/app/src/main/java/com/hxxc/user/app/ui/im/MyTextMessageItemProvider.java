package com.hxxc.user.app.ui.im;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.message.TextMessage;

/**
 * Created by chenqun on 2016/7/12.
 */
@ProviderTag( messageContent = TextMessage.class , showPortrait = true , showProgress = false,showSummaryWithName = true )
public class MyTextMessageItemProvider extends TextMessageItemProvider {
}
