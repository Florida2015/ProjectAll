package com.huaxia.finance.recommenddm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.ShareConstants;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.mangemoneydm.ProductExplainActivity;
import com.huaxia.finance.model.ActivitiesModel;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.huaxia.finance.share.BaseUIListener;
import com.huaxia.finance.share.Util;
import com.huaxia.finance.sina.AccessTokenKeeper;
import com.huaxia.finance.widgetutils.dialog.ShareDialogFragment;
import com.huaxia.finance.widgetutils.dialog.minterface.OnShareListener;
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
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import org.apache.http.Header;

/**
 * 活动详情页
 * Created by houwen.lai on 2016/1/25.
 */
public class ActivtiesWebActivity extends BaseActivity implements View.OnClickListener,IWeiboHandler.Response,OnShareListener {

    private WebView pull_webview;
    private ProgressBar progressbar;

    private String mUrl;
    private String activityId;

    private ActivitiesModel model;

    private boolean flagFinish = false;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x13://产品列表
                    MenuTwoActivity.getInstance().setTab(1);
                    ActivtiesWebActivity.this.finish();
                    break;
                case 0x14://产品详情
                    if (msg.getData()!=null&&!TextUtils.isEmpty(msg.getData().getString("productId"))){
                        startActivity(new Intent(ActivtiesWebActivity.this, ProductExplainActivity.class).putExtra("produnctId", msg.getData().getString("productId")));
                    }
//                    ActivtiesWebActivity.this.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public static ActivtiesWebActivity instance;

    public static ActivtiesWebActivity getInstance(){
        if(instance!=null)return instance;
        else return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTencent.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.pull_refresh_webview);
        instance=this;
        activityId = getIntent().getStringExtra("activityId");
//        model = (ActivitiesModel) getIntent().getSerializableExtra("mode");
//        mUrl = model.getDetailUrl();
        if (!TextUtils.isEmpty(activityId))MyLog.d("api_activityId="+activityId);

        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
// 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(ShareConstants.APP_ID_qq, ActivtiesWebActivity.this);
// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        baseUIListener = new BaseUIListener(this);

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

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, ShareConstants.APP_ID, false);
        // 将该app注册到微信
        api.registerApp(ShareConstants.APP_ID);
        flagFinish = false;
        findAllViews();
    }

    @SuppressLint("JavascriptInterface")
    public void findAllViews(){
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.VISIBLE);
        img_btn_title_right.setOnClickListener(this);
        tv_title_bar.setText("");
        img_btn_title_back.setOnClickListener(this);

        pull_webview = (WebView) findViewById(R.id.pull_webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        pull_webview.getSettings().setJavaScriptEnabled(true);
        pull_webview.getSettings().setDomStorageEnabled(true);
        pull_webview.getSettings().setUseWideViewPort(true);
        pull_webview.getSettings().setLoadWithOverviewMode(true);
        pull_webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        pull_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.d("api_shouldOverrideUrlLoading=>" + url);
                return false;
//                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                MyLog.d("api_on_keyEvent");
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                MyLog.d("api_shouldInterceptRequest=>" + url);
                if(url.contains("/#")&&(url.contains("product/list")||url.contains("product/detail")
                        ||url.contains("account/coupon")||url.contains("account/myOrder")
                        ||url.contains("account/setting")||url.contains("account/bankList")
                        ||url.contains("/login")||url.contains("/register")||url.contains("forget/password"))
                        ||url.equals(UrlConstants.urlBase_web)){
                    handler.sendEmptyMessageDelayed(0x13,200);
                }
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                MyLog.d("api_onPageStarted_url=>" + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                MyLog.d("api_onPageFinished_url=>" + url);
                if (!url.equals(mUrl)){
                    handler.sendEmptyMessageDelayed(0x13,200);
                }
            }
        });

        pull_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbar.setProgress(newProgress);
                if (newProgress==100){
                    flagFinish = true;
                    MyLog.d("api_onProgressChanged=" + flagFinish);
                }
                if (newProgress > 95) {
                    progressbar.setVisibility(View.GONE);
                    pull_webview.loadUrl("javascript:removeEnter(android)");
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                MyLog.d("api_onReceivedTitle=" + title);
//                tv_title_bar.setText(title);
                super.onReceivedTitle(view, title);
            }
        });

//        pull_webview.loadUrl(mUrl);
//        url_share = model.getDetailUrl();
//        title = model.getActivityName();
//        desContents = model.getComments();
//        pull_webview.loadUrl("javascript:removeEnter(android)");
//        pull_webview.addJavascriptInterface(new JsInterface(ActivtiesWebActivity.this), "JsInterface");//

        //请求数据
        if (NetWorkUtils.isNetworkConnected(this)){
            relative_no_work.setVisibility(View.GONE);
            getActivityHttpRequest();//获取活动Id
        }else{
            relative_no_work.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setImageResource(R.drawable.icon_no_wifi);
            tv_reloading.setVisibility(View.VISIBLE);
            tv_reloading.setText("网络出现问题啦!");
        }
    }

    public void initActivityData(){
        if (model==null)return;
        url_share = model.getDetailUrl();
        title = model.getActivityName();
        desContents = model.getComments();

        mUrl = model.getDetailUrl();

        tv_title_bar.setText(model.getActivityName());

        pull_webview.addJavascriptInterface(new JsInterface(ActivtiesWebActivity.this), "JsInterface");
        pull_webview.loadUrl(mUrl);
        pull_webview.loadUrl("javascript:removeEnter(android)");

    }

    //Js调用Java方法
    class JsInterface {
        //interface for javascript to invokes
        private Context context;
        public JsInterface(Context context) {
            this.context = context;
        }
        //js2java
        @JavascriptInterface
        public void clickOnAndroid(String text) {
            MyLog.d("api_js_to_android_title=");
            //这里是java端调用webview的JS
            if (handler!=null){
                handler.sendEmptyMessageDelayed(0x13,200);
            }

            //java2js
//            pull_webview.loadUrl(String.format("javascript:removeEnter(android)"));//test是参数
            //这里是java端调用webview的JS
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_title_back:
                pull_webview.clearCache(true);
                finish();
                break;
            case R.id.img_btn_title_right://分享
                if (!TextUtils.isEmpty(url_share)&&NetWorkUtils.isNetworkConnected(this)){
                    MyLog.d("api_share="+url_share);
                    ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
                    shareDialogFragment.show(getFragmentManager(),"shareactivity");
                }
                break;
        }
    }

    @Override
    public void onShareClick(View v) {
        switch (v.getId()){
            case R.id.btn_weixin_circle://朋友圈
                if (BtnUtils.isFastDoubleClick())
                    sendWeixinUrl(true,title,title,url_share);
                break;
            case R.id.btn_weixin_friends://好友
                if (BtnUtils.isFastDoubleClick())
                    sendWeixinUrl(false,title,title,url_share);
                break;
            case R.id.btn_weibo://微博
                if (BtnUtils.isFastDoubleClick())
                    sendMessage("花虾金融",title,url_share,R.drawable.ic_logo);
                break;
            case R.id.btn_qq_frieneds://qq好友
                if (BtnUtils.isFastDoubleClick())
                    shareToQqFriends(title,title,url_share,false);
                break;
            case R.id.btn_qq_zone://qq空间
                if (BtnUtils.isFastDoubleClick())
                    shareToQqFriends(title, title, url_share, true);
                break;
            default:
                break;
        }
    }

    /**
     * 根据活动ID获取 活动详情
     */
    public void getActivityHttpRequest(){
        if (TextUtils.isEmpty(activityId))return;
        BaseRequestParams params = new BaseRequestParams();
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlActivity).append(activityId);
        MyLog.d("api_url="+url.toString());
        DMApplication.getInstance().getHttpClient(this).get(this, url.toString(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<ActivitiesModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<ActivitiesModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            model = baseModel.getData();
                            initActivityData();

                        }else{

                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");


            }
        });
    }

    /**
     * 确定转发
     */
    public void getWechatHttpRequest(){
        if (model==null)return;
        BaseRequestParams params = new BaseRequestParams();
        params.put("activityId",model.getActivityId());
        params.put("phone", SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_userPhone));
        params.put("comments",model.getComments());
        String url = UrlConstants.urlBase+UrlConstants.urlWechat;
        MyLog.d("api_url="+url);
        DMApplication.getInstance().getHttpClient(this).get(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {


                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");


            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    private String title = "花虾金融";
    private String desContents = "活动";
    private String url_share ;
    /** 微博微博分享接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;
    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;//客户端
    public static final int SHARE_ALL_IN_ONE = 2;//网页
    private int mShareType = SHARE_CLIENT;

    @Override
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                getWechatHttpRequest();
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
                mShareType=SHARE_ALL_IN_ONE;
                sendMultiMessage(title,desc,url,resouce);
            }
        }else if (mShareType == SHARE_ALL_IN_ONE) {
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
        weiboMessage.textObject = getTextObj(desc);
        weiboMessage.mediaObject = getWebpageObj(title,desc,url,resouce);
//        weiboMessage.imageObject = getImageObj(resouce);

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        if (mShareType == SHARE_CLIENT) {
            mWeiboShareAPI.sendRequest(ActivtiesWebActivity.this, request);
        } else if (mShareType == SHARE_ALL_IN_ONE) {
            AuthInfo authInfo = new AuthInfo(this, ShareConstants.APP_KEY, ShareConstants.REDIRECT_URL, ShareConstants.SCOPE);
            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
            String token = "";
            if (accessToken != null) {
                token = accessToken.getToken();
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
                    AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
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
    private void sendSingleMessage(String title, String desc,String url,int resouce) {
        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();
        weiboMessage.mediaObject = getTextObj(desc);
        weiboMessage.mediaObject = getWebpageObj(title,desc,url,resouce);
//        weiboMessage.mediaObject = getImageObj(resouce);
        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(ActivtiesWebActivity.this, request);
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
        Bitmap  bitmap = BitmapFactory.decodeResource(ActivtiesWebActivity.this.getResources(),R.drawable.ic_logo);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = url;//
        mediaObject.defaultText = desc;//默认文案
        return mediaObject;
    }

    private IWXAPI api;
    /**
     * 微信分享
     */
    private void sendWeixinUrl(boolean flag,String title,String desc,String url){
        if (api==null||!api.isWXAppInstalled()){
            ToastUtils.showShort("请先安装微信客户端再分享");
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
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

    public Tencent mTencent;
    BaseUIListener baseUIListener;
    /**
     * 分享到qq好友
     */
    private void shareToQqFriends(String tit,String cont,String url,boolean flag) {
        baseUIListener = new BaseUIListener(ActivtiesWebActivity.this);
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, tit);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  cont);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ShareConstants.qq_image);
        params.putInt(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,R.drawable.ic_logo);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, null);
        if (flag)
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
        mTencent.shareToQQ(ActivtiesWebActivity.this, params,baseUIListener);
    }

    /**
     * 分享到qq空间
     */
    private void shareToQzone (String tit,String cont,String url) {
        MyLog.d("api_share--------");
        baseUIListener = new BaseUIListener(ActivtiesWebActivity.this);
        mTencent = Tencent.createInstance(ShareConstants.APP_ID_qq,ActivtiesWebActivity.this);
        final Bundle params = new Bundle();
        //分享类型
//        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE);
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, tit);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, cont);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
        params.putInt(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, R.drawable.ic_logo);
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, null);
//        params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT,  QzoneShare.SHARE_TO_);
        mTencent.shareToQzone(ActivtiesWebActivity.this, params, new BaseUIListener(ActivtiesWebActivity.this));
    }

}
