package com.hxxc.user.app.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.ui.mine.setting.FeedbackActivity;
import com.hxxc.user.app.ui.mine.web.WebActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ShearPlateUtils;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by houwen.lai on 2016/11/8.
 * 分享 微信 qq 短信 邮件
 */

public class ShareActivity extends Activity implements IWeiboHandler.Response {

    private String title;
    private String desContents;
    private String url_share;
    private String url_img = "";

    public Context mContext;

    public Bitmap shareBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        ButterKnife.bind(this);

        mContext = this;
        title = getIntent().getStringExtra("title");
        desContents = getIntent().getStringExtra("des");
        url_share = getIntent().getStringExtra("url");

        LogUtils.d("share activity title=" + title + "__desContents=" + desContents + "___url_share=" + url_share);

        //*******************TODO
        if (!TextUtils.isEmpty(url_share)) {
            int i = url_share.lastIndexOf("?");
            if (i > 0)
                url_share = url_share + "&";
            else
                url_share = url_share + "?";
            UserInfo info = SPUtils.geTinstance().getUserInfo();
            if (null == info)
                url_share = url_share + "channel=" + Constants.TypeChannel;
            else
                url_share = url_share + "uid=" + info.getUid() + "&fid=" + info.getFid() + "&invitedCode=" + info.getInvitedCode() + "&channel=" + Constants.TypeChannel;
        }
        //*******************TODO


        if (getIntent().hasExtra("img"))
            url_img = getIntent().getStringExtra("img");

        //weixin
//        api = WXAPIFactory.createWXAPI(this, UserInfoConfig.APP_ID_weixin,false);
//        api.registerApp(UserInfoConfig.APP_ID_weixin);

        //sina
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, UserInfoConfig.APP_KEY_sina);
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }

        //qq
        if (mTencent == null) {
            mTencent = Tencent.createInstance(UserInfoConfig.APP_ID_qq, this);
        }

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.login_logo);
//        image = new UMImage(this,bitmap);

//        image = new UMImage(this,R.drawable.login_logo);
//        image.setThumb(new UMImage(this, R.drawable.login_logo));

        if (!TextUtils.isEmpty(url_img)) {
            image = new UMImage(this, url_img);//UserInfoConfig.pathIcon_qq
            image.setThumb(new UMImage(this, url_img));//UserInfoConfig.pathIcon_qq

            Picasso.with(mContext).load(url_img).resize(50, 50).centerCrop().into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    shareBitmap = bitmap;
                    LogUtil.d("share url img success");
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }

    }

    @OnClick({R.id.btn_weixin_circle, R.id.btn_weixin_friends, R.id.btn_share_qq, R.id.btn_share_qq_sqace,
            R.id.btn_share_sina, R.id.btn_share_message, R.id.btn_share_email
            , R.id.btn_share_award, R.id.btn_copy_url, R.id.btn_share_refresh, R.id.btn_share_feedback, R.id.view_share_activity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_weixin_circle://微信朋友圈
                if (BtnUtils.isFastDoubleClick())
//                sendWeixinUrl(true,title,desContents,url_share);
                    performShare(SHARE_MEDIA.WEIXIN_CIRCLE, title, desContents, url_share, image);
                break;
            case R.id.btn_weixin_friends://微信好友
                if (BtnUtils.isFastDoubleClick())
//                    sendWeixinUrl(false,title,desContents,url_share);
                    performShare(SHARE_MEDIA.WEIXIN, title, desContents, url_share, image);
                break;
            case R.id.btn_share_qq://QQ
                if (BtnUtils.isFastDoubleClick())
                    doShareQQ(false, title, desContents, url_share, url_img);
                break;
            case R.id.btn_share_qq_sqace://QQ空间
                if (BtnUtils.isFastDoubleClick())
                    doShareQQ(true, title, desContents, url_share, url_img);
                break;
            case R.id.btn_share_sina://新浪微博
                if (BtnUtils.isFastDoubleClick())
                    sendMessage(title, desContents, url_share, shareBitmap);//R.mipmap.icon
                break;
            case R.id.btn_share_message://短信

                if (BtnUtils.isFastDoubleClick())
                    sendEmsn();
                break;
            case R.id.btn_share_email://邮件
                if (BtnUtils.isFastDoubleClick())
                    sendEmail();
                break;
            case R.id.btn_share_award://分享奖励
                if (BtnUtils.isFastDoubleClick())
                    startActivity(new Intent(mContext, WebActivity.class).
                            putExtra("title", "分享奖励").putExtra("url", url_share).
                            putExtra("flag", false));
                break;
            case R.id.btn_copy_url://复制链接
                ShearPlateUtils.ShearPlateCope(this, url_share, new ShearPlateUtils.OnClickFinish() {
                    @Override
                    public void onResult(boolean f) {
                        if (f) {
                            ShareActivity.this.finish();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                });//
                break;
            case R.id.btn_share_refresh://刷新

                break;
            case R.id.btn_share_feedback://意见反馈
                if (BtnUtils.isFastDoubleClick())
                    startActivity(new Intent(mContext, FeedbackActivity.class));
                break;
            case R.id.view_share_activity:
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
        }
    }

//    public IWXAPI api;

    /**
     * 微信分享
     */
//    private void sendWeixinUrl(boolean flag,String title,String desc,String url){
//        if (api==null||!api.openWXApp()){
//            Toast.makeText(this,"请先按照微信客户端再分享",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = url;
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = title;
//        msg.description = desc;
//        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.login_logo);
//        msg.thumbData = Util.bmpToByteArray(thumb, true);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = flag ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
//        api.sendReq(req);
//    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 新浪微博分享
     */
    /**
     * 微博微博分享接口实例
     */
    private IWeiboShareAPI mWeiboShareAPI;
    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;//客户端
    public static final int SHARE_ALL_IN_ONE = 2;//网页
    private int mShareType = SHARE_CLIENT;

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     *
     * @see {@link #sendMultiMessage} 或者 {@link #sendSingleMessage}
     * int resouce
     */
    private void sendMessage(String title, String desc, String url, Bitmap bitmap) {//Bitmap  bitmap
        if (mShareType == SHARE_CLIENT) {
            if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
                int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
                if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                    sendMultiMessage(title, desc, url, bitmap);//resouce
                } else {
                    sendSingleMessage(title, desc, url, bitmap);//resouce
                }
            } else {
                mShareType = SHARE_ALL_IN_ONE;
                sendMultiMessage(title, desc, url, bitmap);//resouce
            }
        } else if (mShareType == SHARE_ALL_IN_ONE) {
            sendMultiMessage(title, desc, url, bitmap);
        }
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     *
     * @param url 分享的内容是否有网页
     */
    private void sendMultiMessage(String title, String desc, String url, Bitmap bitmap) {//int resouce
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        weiboMessage.textObject = getTextObj(desc);
//        weiboMessage.mediaObject = getWebpageObj(title,desc,url,resouce);
//        weiboMessage.imageObject = getImageObj(resouce);

        weiboMessage.mediaObject = getWebpageObj(title, desc, url, bitmap);
        weiboMessage.imageObject = getImageObj(bitmap);

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        if (mShareType == SHARE_CLIENT) {
            mWeiboShareAPI.sendRequest((Activity) mContext, request);
        } else if (mShareType == SHARE_ALL_IN_ONE) {
            AuthInfo authInfo = new AuthInfo(mContext, UserInfoConfig.APP_KEY_sina, UserInfoConfig.REDIRECT_URL, UserInfoConfig.SCOPE);
            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mContext);
            String token = "";
            if (accessToken != null) {
                token = accessToken.getToken();
            }
            mWeiboShareAPI.sendRequest((Activity) mContext, request, authInfo, token, new WeiboAuthListener() {

                @Override
                public void onWeiboException(WeiboException arg0) {
                }

                @Override
                public void onComplete(Bundle bundle) {
                    // TODO Auto-generated method stub
                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                    newToken.getToken();
                    AccessTokenKeeper.writeAccessToken(mContext, newToken);
//                    SharedPreferencesUtils.getInstanse().putKeyValue(getApplicationContext(),ShareConstants.APP_Token,newToken.getToken());
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
    private void sendSingleMessage(String title, String desc, String url, Bitmap bitmap) {//int resouce
        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();
        weiboMessage.mediaObject = getTextObj(desc);
//        weiboMessage.mediaObject = getWebpageObj(title,desc,url,resouce);
//        weiboMessage.mediaObject = getImageObj(resouce);

        weiboMessage.mediaObject = getWebpageObj(title, desc, url, bitmap);
        weiboMessage.mediaObject = getImageObj(bitmap);
        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(this, request);
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

    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
        //        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resouce);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(String title, String desc, String url, int resouce) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = desc;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resouce);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;//
        mediaObject.defaultText = desc;//默认文案
        return mediaObject;
    }

    private WebpageObject getWebpageObj(String title, String desc, String url, Bitmap bitmap) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = title;
        mediaObject.description = desc;
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(),resouce);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;//
        mediaObject.defaultText = desc;//默认文案
        return mediaObject;
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
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
//                Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_SHORT).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
//                Toast.makeText(this,
//                        getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg,
//                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constants.REQUEST_QZONE_SHARE||requestCode == Constants.REQUEST_QQ_SHARE) {
//            Tencent.onActivityResultData(requestCode,resultCode,data,qqShareListener);
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return true;
        }
        return false;
    }

    /**
     * flag true 空间 false qq
     * qq
     * mTencent.shareToQQ(this, params, qqShareListener);
     */
    public Tencent mTencent;

    public void doShareQQ(boolean flag, String title, String content, String url, String imgUrl) {
        if (mTencent == null) mTencent = Tencent.createInstance(UserInfoConfig.APP_ID_qq, this);

//        mTencent.login(this, "huaxia", new IUiListener() {
//            @Override
//            public void onComplete(Object o) {
//                upDataPicture();
//            }
//
//            @Override
//            public void onError(UiError uiError) {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });


        final Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, content);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);

        if (flag) {
            ArrayList<String> imageUrls = new ArrayList<String>();
            imageUrls.add(imgUrl);
            params.putStringArrayList(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            mTencent.shareToQzone(this, params, qqShareListener);
        } else {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            mTencent.shareToQQ(this, params, qqShareListener);
        }
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
//            Toast.makeText(ShareActivity.this,"取消分享",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(ShareActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            LogUtil.d("QQ分享 onError=" + e.errorMessage);
//            Toast.makeText(ShareActivity.this,"分享失败",Toast.LENGTH_SHORT).show();
        }
    };


    public void sendEmail() {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:"));//Uri.parse("mailto:way.ping.li@gmail.com")
        data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
        data.putExtra(Intent.EXTRA_TEXT, "这是内容");
        startActivity(data);
    }

    public void sendEmsn() {
        Uri smsToUri = Uri.parse("smsto:");//Uri.parse("smsto:10000")
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", "测试发送短信");
        startActivity(intent);
    }


    /**
     * 友盟分享
     * SHARE_MEDIA.WEIXIN.toSnsPlatform()
     * SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform()
     */

    public SHARE_MEDIA lastestPlatform;
    UMImage image;


    private void performShare(SHARE_MEDIA platform, String title, String desContents, String url_share, UMImage image) {

//        dismiss();
//        new ShareAction((Activity) mContext).setPlatform(platform)
//                .withText(desContents)
//                .withTitle(title)
//                .withTargetUrl(url_share)
//                .withMedia(image)
//                .setCallback(umShareListener)
//                .share();

        new ShareAction((Activity) mContext).withText(desContents)
                .withMedia(image)
                .withTargetUrl(url_share)
                .withTitle(title)
                .setPlatform(platform)
                .setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtils.d("platform=" + platform);
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText((Activity) mContext, " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText((Activity) mContext, "分享成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Log.d("plat","platform"+platform);
//            Toast.makeText((Activity) mContext,"分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Log.d("plat","platform"+platform);
//            Toast.makeText((Activity) mContext,"分享取消", Toast.LENGTH_SHORT).show();
        }
    };


}
