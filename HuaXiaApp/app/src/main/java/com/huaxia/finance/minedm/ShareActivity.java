package com.huaxia.finance.minedm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.ToastUtils;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.ShareConstants;
import com.huaxia.finance.share.Util;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 功能：微信好友 微信朋友圈 新浪微博 分享
 * Created by houwen.lai on 2015/11/24.
 */
public class ShareActivity extends Activity implements View.OnClickListener,IWeiboHandler.Response {

    private Button btn_weixin_circle;
    private Button btn_weixin_friends;
    private Button btn_weibo;

    /** 微博微博分享接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;
    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;//客户端
    public static final int SHARE_ALL_IN_ONE = 2;//网页
    private int mShareType = SHARE_CLIENT;

    private String title;
    private String desContents;
    private String url_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity);
        btn_weixin_circle = (Button) findViewById(R.id.btn_weixin_circle);
        btn_weixin_friends = (Button) findViewById(R.id.btn_weixin_friends);
        btn_weibo = (Button) findViewById(R.id.btn_weibo);

        btn_weixin_circle.setOnClickListener(this);
        btn_weixin_friends.setOnClickListener(this);
        btn_weibo.setOnClickListener(this);

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, ShareConstants.APP_KEY);
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();	// 将应用注册到微博客户端

    // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }

//
        api = WXAPIFactory.createWXAPI(this, ShareConstants.APP_ID);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_weixin_circle://朋友圈
                if (BtnUtils.isFastDoubleClick())
                    sendWeixinUrl(true,title,desContents,url_share);
                break;
            case R.id.btn_weixin_friends://好友
                if (BtnUtils.isFastDoubleClick())
                    sendWeixinUrl(false,title,desContents,url_share);
                break;
            case R.id.btn_weibo://微博
                if (BtnUtils.isFastDoubleClick())
                sendMessage(title,desContents,url_share,R.mipmap.logo);
                break;
        }

    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(this,
                        getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * @see {@link #sendMultiMessage} 或者 {@link #sendSingleMessage}
     */
    private void sendMessage(String title, String desc,String url,int resouce) {
        if (mShareType == SHARE_CLIENT) {
            if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
                int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
                if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                    sendMultiMessage(title,desc,url,resouce);
                } else {
                    sendSingleMessage(title,desc,url,resouce);
                }
            } else {
                sendMultiMessage(title,desc,url,resouce);
            }
        }
        else if (mShareType == SHARE_ALL_IN_ONE) {
            sendMultiMessage(title,desc,url,resouce);
        }
    }
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     * @param url 分享的内容是否有网页
     */
    private void sendMultiMessage(String title, String desc,String url,int resouce) {
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）

        weiboMessage.mediaObject = getWebpageObj(title,desc,url,resouce);

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        if (mShareType == SHARE_CLIENT) {
            mWeiboShareAPI.sendRequest(ShareActivity.this, request);
        }
        else if (mShareType == SHARE_ALL_IN_ONE) {
            AuthInfo authInfo = new AuthInfo(this, ShareConstants.APP_KEY, ShareConstants.REDIRECT_URL, ShareConstants.SCOPE);
//            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());

            String token = SharedPreferencesUtils.getInstanse().getKeyValue(getApplicationContext(),ShareConstants.APP_Token);
            if (TextUtils.isEmpty(token)) {
//                token =
//                return;
            }
            mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

                @Override
                public void onWeiboException( WeiboException arg0 ) {
                }

                @Override
                public void onComplete( Bundle bundle ) {
                    // TODO Auto-generated method stub
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    newToken.getToken();
//                    AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
                    SharedPreferencesUtils.getInstanse().putKeyValue(getApplicationContext(),ShareConstants.APP_Token,newToken.getToken());
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     *
     * @param url 分享的内容是否有网页
     */
    private void sendSingleMessage(String title, String desc,String url,int resouce) {
        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();

        weiboMessage.mediaObject = getWebpageObj(title,desc,url,resouce);

        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(ShareActivity.this, request);
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(int resouce) {
        ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
        //        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resouce);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(String title,String desc,String url,int resouce) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = desc;

        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(),resouce );
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;
        mediaObject.defaultText = "";//默认文案
        return mediaObject;
    }


    private IWXAPI api;
    /**
     * 微信分享
     */
    private void sendWeixinUrl(boolean flag,String title,String desc,String url){
        if (api==null||!api.openWXApp()){
            ToastUtils.showShort("请先按照微信客户端再分享");
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        msg.thumbData = Util.bmpToByteArray(thumb, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = flag ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
