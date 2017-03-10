package com.huaxia.finance.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.framwork.Utils.AntiHijackingUtil;
import com.framwork.Utils.BtnUtils;
import com.framwork.Utils.DateUtils;
import com.framwork.Utils.FileUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.PhoneUtils;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.pullrefresh.ui.PullToRefreshBase;
import com.framwork.pullrefresh.ui.PullToRefreshWebView;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.DMConstant;
import com.huaxia.finance.constant.ShareConstants;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.minedm.LoginActivity;
import com.huaxia.finance.share.BaseUIListener;
import com.huaxia.finance.share.Util;
import com.huaxia.finance.sina.AccessTokenKeeper;
import com.huaxia.finance.widgetutils.SharePopupWindow;
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

/**
 * 功能：web子页面
 *
 * Created by houwen.lai on 2015/11/20.
 */
public class WebActivity extends BaseActivity implements View.OnClickListener,IWeiboHandler.Response,OnShareListener{

    private WebView mWebView;
    private PullToRefreshWebView mPullWebView;

    private String activityUrl;

    private SharePopupWindow sharePopupWindow;

    private RelativeLayout relative_title_bar;
    private ProgressBar progressbar;//进度条

    private boolean flagMe=false;//我的更多进入 产品页

    private final int Request=0x0011;

    private String shareDes;

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
        }
    }

    /**
     * 网络
     */
    private LinearLayout linear_no_network;//
    private Button btn_reloading;
    private ProgressBar probar_no_work;

    private View.OnClickListener itemOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (sharePopupWindow!=null)
            sharePopupWindow.dismiss();
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
            }
        }
    };
    private String title = "花虾金融";
    private String desContents = "活动";
    private String url_share ;
    /** 微博微博分享接口实例 */
    private IWeiboShareAPI mWeiboShareAPI;
    public static final String KEY_SHARE_TYPE = "key_share_type";
    public static final int SHARE_CLIENT = 1;//客户端
    public static final int SHARE_ALL_IN_ONE = 2;//网页
    private int mShareType = SHARE_CLIENT;


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x11://回首页
                    if (!flagMe){
//                        MenuActivity.getInstance().setTab(0);
                        WebActivity.this.finish();
                    }else {
//                        ApplicationUtils.removeUrlCookies(mWebView, activityUrl);
//                        MenuActivity.getInstance().removeWebViewCookie();
//                        CookieManager cookieManager = CookieManager.getInstance();
//                        cookieManager.removeAllCookie();
//                        cookieManager.removeSessionCookie();

                        FileUtils.getInstance().del(DMApplication.getInstance().getDir("cache", Context.MODE_PRIVATE).getPath());

                        DMApplication.isLoginFlag = false;
                        DMConstant.flagMine=true;
                        DMApplication.token = "";
                        SharedPreferencesUtils.getInstanse().putKeyValue(WebActivity.this, UserConstant.key_token, "");
//                        MenuActivity.getInstance().setTab(0);
//                        MenuActivity.getInstance().removeFragment(2);
                        WebActivity.this.finish();
                    }
                    break;
                case 0x12://拨打电话
                    if (msg.getData()!=null){
                        String tell=msg.getData().getString("tel:");
                        PhoneUtils.showTel(WebActivity.this,tell);
                    }
                    break;
                case 0x13://产品的购买页面
                    if (msg.getData()!=null){
                        img_btn_title_right.setVisibility(View.GONE);
                        String pUrl=msg.getData().getString("url");
                        MyLog.d("api_send_url="+pUrl);
                        if (pUrl.contains("productId")){
                            img_btn_title_right.setVisibility(View.GONE);
                        }else img_btn_title_right.setVisibility(View.VISIBLE);
                        activityUrl=pUrl+DMConstant.UrlHuaxiaDisplay;

                        if (!TextUtils.isEmpty(DMApplication.token)){
                            mWebView.loadUrl(pUrl+DMConstant.UrlHuaxiaDisplay+"&token="+DMApplication.token);
                        }else{
                            mWebView.loadUrl(pUrl+DMConstant.UrlHuaxiaDisplay);
                        }
                        mPullWebView.setPullRefreshEnabled(false);
                    }
                    break;
                case 0x14://登录界面
                    DMApplication.isLoginFlag=false;
                    SharedPreferencesUtils.getInstanse().putKeyValue(WebActivity.this, UserConstant.key_token,"");
                    startActivityForResult(new Intent(WebActivity.this, LoginActivity.class), Request);
//                    WebActivity.this.finish();
                    break;
                case 0x15://调用浏览器
                    String murl = msg.getData().getString("url");
                    Uri uri = Uri.parse(murl);
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
//                    WebRequest.getInstance(WebActivity.this).setHttpRequest(murl,DMConstant.UrlWork);
                    break;
                case 0x16://理财页面
//                    MenuActivity.getInstance().setTab(1);
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==Request&&resultCode==RESULT_OK){
            mPullWebView.doPullRefreshing(true,200);
        }
//        BaseUIListener myListener = new BaseUIListener(this);
//        Tencent.onActivityResultData(requestCode,resultCode,data,myListener);
        if (mTencent!=null)  mTencent.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_refresh_webview);
        activityUrl = getIntent().getStringExtra("url");
        flagMe = getIntent().getBooleanExtra("flag",false);
        MyLog.d("api_web_url=>" + activityUrl);

        // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
// 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(ShareConstants.APP_ID_qq, WebActivity.this);
// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        baseUIListener = new BaseUIListener(this);

        initViews();

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

        if (!TextUtils.isEmpty(activityUrl)){
            url_share = activityUrl;
        }
    }

    @SuppressLint("JavascriptInterface")
    public void initViews(){
        relative_title_bar = (RelativeLayout) findViewById(R.id.relative_title_bar);
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        img_btn_title_right= (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setOnClickListener(this);
        img_btn_title_right.setOnClickListener(this);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        if (activityUrl.contains("weixin")){
            tv_title_bar.setText("活动");
            img_btn_title_right.setVisibility(View.VISIBLE);
        }else{
            img_btn_title_right.setVisibility(View.GONE);
        }

        mPullWebView = (PullToRefreshWebView) findViewById(R.id.pull_webview);//new PullToRefreshWebView(this);
        mPullWebView.setPullLoadEnabled(false);

        linear_no_network = (LinearLayout) findViewById(R.id.linear_no_network);
        btn_reloading = (Button) findViewById(R.id.btn_reloading);
        probar_no_work = (ProgressBar) findViewById(R.id.probar_no_work);

        getNetworkState();

        linear_no_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetWorkUtils.isNetworkConnected(WebActivity.this)){
                    mPullWebView.setVisibility(View.GONE);
                    linear_no_network.setVisibility(View.VISIBLE);
                    btn_reloading.setVisibility(View.VISIBLE);
                    probar_no_work.setVisibility(View.GONE);
                }else {
                    btn_reloading.setVisibility(View.GONE);
                    probar_no_work.setVisibility(View.VISIBLE);
                    mWebView.loadUrl(activityUrl);
                }
            }
        });
        mWebView = mPullWebView.getRefreshableView();
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.setScrollbarFadingEnabled(false);
//        mWebView.setNestedScrollingEnabled(true);
//        mWebView.setHorizontalScrollbarOverlay();

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
//        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        mPullWebView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {
                if (NetWorkUtils.isNetworkConnected(WebActivity.this)){
                    if (!TextUtils.isEmpty(DMApplication.token)){
                        mWebView.loadUrl(activityUrl+"&token="+DMApplication.token);
                    }else{
                        mWebView.loadUrl(activityUrl);
//                        mWebView.loadDataWithBaseURL();
                    }
                }else getNetworkState();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {


            }
        });


        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.d("api_url=>" + url);
                if (url.contains("activityId")){
                    mWebView.loadUrl("javascript:removeEnter()");
                }
//                mWebView.loadUrl("javascript:hide_footer()");
                if (!TextUtils.isEmpty(url)&&url.equals(UrlConstants.urlBase)) {//进入拦截首页
                    handler.sendEmptyMessageDelayed(0x11,200);
                    return true;
                }else if(!TextUtils.isEmpty(url)&&url.contains("tel:")){//
                    Message message =new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("tel:",url);
                    message.what=0x12;
                    message.setData(bundle);
                    handler.sendMessageDelayed(message, 100);
                    return true;
                }else if(!TextUtils.isEmpty(url)&&(url.contains("activityId") || url.contains("productId"))){//产品的购买页面
                    Message message =new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("url",url);
                    message.what=0x13;
                    message.setData(bundle);
                    handler.sendMessageDelayed(message, 200);
                    return true;
                }else if(!TextUtils.isEmpty(url)&&url.equals(DMConstant.Urllogin)){//登录界面
                    handler.sendEmptyMessageDelayed(0x14,200);
                    return true;
                }else if(url.contains("pdf")){
                    Message message =new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("url",url);
                    message.what=0x15;
                    message.setData(bundle);
                    handler.sendMessageDelayed(message, 200);
                    return true;
                }else if(url.contains(DMConstant.UrlMangemoney)){
                    handler.sendEmptyMessageDelayed(0x16, 200);
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.contains("activityId")){
                    mWebView.loadUrl("javascript:removeEnter()");
                }
//                mWebView.loadUrl("javascript:hide_footer()");

                progressbar.setVisibility(View.VISIBLE);

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                MyLog.d("api_url_f=>" + url);
//                MyLog.d("api_title_finish="+view.getTitle());
                if (url.contains("weixin")){
                    if (view.getTitle().contains("关于花虾")){
                    }else tv_title_bar.setText("活动");
                    img_btn_title_right.setVisibility(View.VISIBLE);
                }else{
                    img_btn_title_right.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(view.getTitle())&&view.getTitle().contains("more/paysucess?respDesc=")){
                        tv_title_bar.setText("");
                    }else tv_title_bar.setText(!TextUtils.isEmpty(view.getTitle())?view.getTitle():"");
                }
                activityUrl = url;
                mPullWebView.onPullDownRefreshComplete();
                setLastUpdateTime();
                CookieManager cookieManager = CookieManager.getInstance();
//                if (DMApplication.isLoginFlag){
//                    cookieManager.removeAllCookie();
//                    cookieManager.removeSessionCookie();
//                }
                String CookieStr = cookieManager.getCookie(url);
                MyLog.d("api_webview_Cookies = " + CookieStr);
                super.onPageFinished(view, url);
//                if (NetWorkUtils.isNetworkConnected(WebActivity.this)){
//                    ToastUtils.showShortView(MenuActivity.getInstance(),"数据已刷新",R.layout.toast_view,relative_title_bar.getHeight());
//                    mPullWebView.setVisibility(View.VISIBLE);
//                    linear_no_network.setVisibility(View.GONE);
//                }
                if (url.contains("activityId")){
                    mWebView.loadUrl("javascript:removeEnter()");
                }
//                mWebView.loadUrl("javascript:hide_footer()");
                progressbar.setVisibility(View.GONE);

                //
                if (url.contains("weixin")){
                    mWebView.loadUrl(String.format("javascript:getAndroid()"));
//                    mWebView.addJavascriptInterface(new JsInterface(WebActivity.this), "JsInterface");//
                }else title="花虾金融";
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                MyLog.d("api_url_failing="+failingUrl);
                mPullWebView.setVisibility(View.GONE);
                linear_no_network.setVisibility(View.VISIBLE);
                btn_reloading.setVisibility(View.VISIBLE);
                probar_no_work.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {

                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            //api21
//            @SuppressLint("NewApi")
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                if (request != null && request.getUrl() != null) {
//                    String scheme = request.getUrl().getScheme().trim();
//                    MyLog.d("api_should_new=>" + scheme);
////                    if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
//
//                    if (scheme.equals("http://m.huaxiafinance.com/")) {//进入拦截首页
//
//
//                    }else{
//
//                    try {
//                        URL url = new URL(injectIsParams(request.getUrl().toString()));
//                        URLConnection connection = url.openConnection();
//                        return new WebResourceResponse(connection.getContentType(), connection.getHeaderField("encoding"), connection.getInputStream());
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                }
//                return null;
////                return super.shouldInterceptRequest(view, request);
//            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                MyLog.d("api_url_viewbbbbbb=>" + url);

                return super.shouldInterceptRequest(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress>70){
                    MyLog.d("api_WebChromeClient_OriginalUrl="+view.getOriginalUrl());
                    MyLog.d("api_WebChromeClient_url="+view.getUrl());
                    if (!TextUtils.isEmpty(view.getUrl())&&view.getUrl().contains("activityId")){
                        mWebView.loadUrl("javascript:removeEnter()");
                    }
                    mWebView.loadUrl("javascript:hide_footer()");
                }
                progressbar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                MyLog.d("api_onReceivedTitle="+title);
                shareDes=title;
                tv_title_bar.setText(title);
                super.onReceivedTitle(view, title);
            }
        });

//        ApplicationUtils.setUrlCookies(this,activityUrl ,DMApplication.cookieslogin);
        if (!TextUtils.isEmpty(DMApplication.token)){
            mWebView.loadUrl(activityUrl+"&token="+DMApplication.token);
        }else{
            mWebView.loadUrl(activityUrl);
        }

        mWebView.addJavascriptInterface(new JsInterface(WebActivity.this), "JsInterface");//

        setLastUpdateTime();

    }

    public void enterMenuActiviyt(String murl){
        if (!TextUtils.isEmpty(murl)){
            if (murl.equals("http://m.huaxiafinance.com/")){//进入拦截首页

            }
        }
    }

    public static String injectIsParams(String url) {
        if (url != null ) {//&& !url.contains("")
            if (url.contains("?")) {
                return url + "&display=1";
            } else {
                return url + "?display=1";
            }
        } else {
            return url;
        }
    }

    private void setLastUpdateTime() {
        String text = DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS);
        mPullWebView.setLastUpdatedLabel(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_title_back://
                if (mWebView.canGoBack()){
                    mPullWebView.setPullRefreshEnabled(true);
                    mWebView.goBack();
                }else  finish();
                break;
            case R.id.img_btn_title_right://分享
                if (!TextUtils.isEmpty(url_share)&&NetWorkUtils.isNetworkConnected(this)){
                    MyLog.d("api_share="+url_share);
//                    int bottomHight=WebActivity.this.findViewById(R.id.webview_linear).getHeight();
//                    int hhight = UserConstant.mhight-UserConstant.bhight-bottomHight;
//                    MyLog.d("api_mhigt="+UserConstant.mhight+"__bhight="+UserConstant.bhight+"__view.hith="+bottomHight+"_hhh.hitht="+hhight);
//
//                    sharePopupWindow = new SharePopupWindow(WebActivity.this,itemOnclick);
//                    sharePopupWindow.showAtLocation(WebActivity.this.findViewById(R.id.pull_webview),Gravity.BOTTOM,0,0);

                    ShareDialogFragment shareDialogFragment = new ShareDialogFragment();
                    shareDialogFragment.show(getFragmentManager(),"shareactivity");

                }

//                Bundle bundle = new Bundle();
//                bundle.putString("title","");
//                bundle.putString("desc","");
//                bundle.putString("utl","");
//                startActivity(new Intent(WebActivity.this, ShareActivity.class).putExtras(bundle));

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否可以返回操作
        if (mWebView.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            mPullWebView.setPullRefreshEnabled(true);
            mWebView.goBack();
            //过滤是否为重定向后的链接
//            if(loadHistoryUrls.size()>0&&loadUrls.get(loadHistoryUrls.size()-1).contains("index.html"))
//
//                //移除加载栈中的最后两个链接
//                loadHistoryUrls.remove(loadHistoryUrls.get(loadHistoryUrls.size()-1));
//
//            loadHistoryUrls.remove(loadHistoryUrls.get(loadHistoryUrls.size()-1));
//
//            //加载重定向之前的页
//            webview.load(loadUrls.get(loadHistoryUrls.size()-1));

            return true;
        }else finish();

    return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
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
            title = text;
            MyLog.d("api_jstoandroid_title="+text);
            //这里是java端调用webview的JS
            //java2js
//            mWebView.loadUrl(String.format("javascript:getShareTitle()"));//test是参数
            //这里是java端调用webview的JS
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
            mWeiboShareAPI.sendRequest(WebActivity.this, request);
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
        mWeiboShareAPI.sendRequest(WebActivity.this, request);
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
        Bitmap  bitmap = BitmapFactory.decodeResource(WebActivity.this.getResources(),R.drawable.ic_logo);
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
            ToastUtils.showShort("请先按照微信客户端再分享");
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

    //网络判断
    public void getNetworkState(){
        if (!NetWorkUtils.isNetworkConnected(this)){
            mPullWebView.setVisibility(View.GONE);
            linear_no_network.setVisibility(View.VISIBLE);
            btn_reloading.setVisibility(View.VISIBLE);
            probar_no_work.setVisibility(View.GONE);
        }else {
            mPullWebView.setVisibility(View.VISIBLE);
            linear_no_network.setVisibility(View.GONE);
        }
    }

    public Tencent mTencent;
    BaseUIListener baseUIListener;
    /**
     * 分享到qq好友
     */
    private void shareToQqFriends(String tit,String cont,String url,boolean flag) {
        baseUIListener = new BaseUIListener(WebActivity.this);
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
        mTencent.shareToQQ(WebActivity.this, params,baseUIListener);
    }

    /**
     * 分享到qq空间
     */
    private void shareToQzone (String tit,String cont,String url) {
        MyLog.d("api_share--------");
        baseUIListener = new BaseUIListener(WebActivity.this);
        mTencent = Tencent.createInstance(ShareConstants.APP_ID_qq,WebActivity.this);
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
        mTencent.shareToQzone(WebActivity.this, params, new BaseUIListener(WebActivity.this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        boolean safe = AntiHijackingUtil.checkActivity(this);
        if (!safe) ToastUtils.showLong("防盗号或欺骗，请不要输入密码");
    }
}
