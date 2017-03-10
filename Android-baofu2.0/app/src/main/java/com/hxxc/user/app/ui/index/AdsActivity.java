package com.hxxc.user.app.ui.index;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.ActionEvent;
import com.hxxc.user.app.Event.AdsEvent;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.share.ShareActivity;
import com.hxxc.user.app.ui.MainActivity2;
import com.hxxc.user.app.ui.base.SwipeRefreshBaseActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.ui.user.LoginActivity;
import com.hxxc.user.app.ui.user.RegisterActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyWebView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/6/22.
 */
public class AdsActivity extends SwipeRefreshBaseActivity implements View.OnClickListener {

    @BindView(R.id.fl_toolbar)
    FrameLayout fl_toolbar;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.ll_thumbs)
    LinearLayout ll_thumbs;
    @BindView(R.id.tv_good)
    TextView tv_good;
    @BindView(R.id.tv_bad)
    TextView tv_bad;

    @BindView(R.id.ll_webcontainer)
    FrameLayout ll_webcontainer;

    private String json;
    private IndexAds ads;
    private String title;
    private int i;
    private String connecturl;
    private MyWebView mWebView;
    private ContentBean content;
    private String shareTitle;
    private String shareDes;
    private String shareUrl;
    private String shareImg;
    private String url;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ads;
    }

    @Override
    protected void setTitle() {
    }

    @Override
    public void onReflush() {
        super.onReflush();
        if (null != content)
            mWebView.loadDataWithBaseURL(null, content.getWapContent(), "text/html", "UTF-8", null);
        else mWebView.reload();
        setRefresh(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        json = SPUtils.geTinstance().getUid();
        if (null == mWebView) {
            mWebView = new MyWebView(HXXCApplication.getContext());
        }

        ll_webcontainer.removeAllViews();
        ll_webcontainer.addView(mWebView, 0);
        assert mSwipeRefreshLayout != null;
        mSwipeRefreshLayout.setScrollUpChild(mWebView);


        Intent intent = getIntent();
        //1.首页广告栏
        ads = (IndexAds) intent.getSerializableExtra("ads");
        //2.发现页面精选文章
        content = (ContentBean) intent.getSerializableExtra("content");
        //3.普通url
        url = intent.getStringExtra("url");
        addUidToUrl();

        //4.标题
        title = intent.getStringExtra("title");
        //5.是否分享
        boolean isShare = intent.getBooleanExtra("isShare", false);
        //6.是否刷新
        boolean reflush = intent.getBooleanExtra("reflush", true);
//        if (!reflush) {
        mSwipeRefreshLayout.setEnabled(false);
//        }

        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        } else {
            mTitle.setText("华夏信财");
        }
        if (isShare) {
            mIvShare.setVisibility(View.VISIBLE);

            if (5 == intent.getIntExtra("shareUrlFrom", 0)) {
                mIvShare.setVisibility(View.INVISIBLE);
                getInviteShareUrl();
            } else {
                shareTitle = intent.getStringExtra("shareTitle");
                shareDes = intent.getStringExtra("shareDes");
                shareUrl = intent.getStringExtra("shareUrl");
                shareImg = intent.getStringExtra("shareImg");
            }
        }
        //-1时不能在页面内跳转
        i = getIntent().getIntExtra("type", 0);
        initWebView();

        if (null != ads) {
            connecturl = ads.getConnectUrl();
            mWebView.loadUrl(connecturl);
        } else if (null != content) {
            mWebView.loadDataWithBaseURL(null, content.getWapContent(), "text/html", "UTF-8", null);
//            initThumbs();
        } else {
//            if (Constants.TEXT_INTEGRAL.equals(title)) {//TODO 积分商城
////                if (TextUtils.isEmpty(url)) {
//                View view = View.inflate(this, R.layout.view_empty, null);
//                progressbar.setVisibility(View.GONE);
//                ll_webcontainer.removeAllViews();
//                ll_webcontainer.addView(view, 0);
//                mSwipeRefreshLayout.setEnabled(false);
//                return;
////                }
//            }
            if (TextUtils.isEmpty(url) && Constants.TEXT_JiJin.equals(title)) {
                getFundUrl();//我的基金界面
                return;
            }

            mWebView.loadUrl(url);
        }
    }

    private void addUidToUrl() {
        if (!TextUtils.isEmpty(url) && !url.contains("uid=") && !TextUtils.isEmpty(SPUtils.geTinstance().getUid())) {
            if (url.contains("?"))
                url = url + "&uid=" + SPUtils.geTinstance().getUid();
            else{
                url = url + "?uid=" + SPUtils.geTinstance().getUid();
            }
        }
    }

    private int thumbs = 0;

    private void initThumbs() {
        ll_thumbs.setVisibility(View.VISIBLE);
        tv_good.setText(content.getGoods() + "");
        tv_bad.setText(content.getNoGoods() + "");
        tv_good.setOnClickListener(this);
        tv_bad.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        if (null != ll_webcontainer)
            ll_webcontainer.removeAllViews();
        if (null != mWebView)
            mWebView.destroy();
//        if (null != content) {
//            doThumbs();
//        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
//        settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);// 支持js功能
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);//H5使用了在浏览器本地存储功能就必须加这句
        mWebView.setWebViewClient(new WebViewClient() {

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                progressbar.setVisibility(View.GONE);
                View v = View.inflate(view.getContext(), R.layout.view_empty, null);
                ll_webcontainer.removeAllViews();
                ll_webcontainer.addView(v, 0);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            // 所有链接跳转会走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (i != -1) {
                    view.loadUrl(url);// 在跳转链接时强制在当前webview中加载
                }
                //此方法还有其他应用场景, 比如写一个超链接<a href="tel:110">联系我们</a>, 当点击该超链接时,可以在此方法中获取链接地址tel:110, 解析该地址,获取电话号码, 然后跳转到本地打电话页面, 而不是加载网页, 从而实现了webView和本地代码的交互
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                handler.proceed();  // 接受所有网站的证书
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 进度发生变化
                progressbar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

                if (!TextUtils.isEmpty(title)) {
                    mTitle.setText(title);
                } else {
                    mTitle.setText("华夏信财");
                }
            }
        });
        mWebView.addJavascriptInterface(new JsCallback() {
            @JavascriptInterface//注意:此处一定要加该注解,否则在4.1+系统上运行失败
            @Override
            public void doLogin(String s) {
                JsUrl = s;
                toDoLogin(s);
                AdsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mWebView.canGoBack()) {
                            mWebView.goBack();
                        }
                    }
                });
            }

            @JavascriptInterface
            @Override
            public void doBack(String s) {
                if ("product".equals(s) || "account".equals(s)) {
                    finish();
                }
            }

            @JavascriptInterface
            @Override
            public void doLogining() {
                Intent intent = new Intent(AdsActivity.this, LoginActivity.class);
                intent.putExtra("startType", LoginActivity.NoRestartMain);
                startActivity(intent);
                ToastUtil.ToastShort(AdsActivity.this, "请先登录");
            }

            @JavascriptInterface
            @Override
            public void doShare(String str) {
                if (!BtnUtils.isFastDoubleClick()) return;
                if (!TextUtils.isEmpty(json)) {
                    toShare();
                } else {
                    Intent intent = new Intent(AdsActivity.this, LoginActivity.class);
                    intent.putExtra("startType", LoginActivity.NoRestartMain);
                    intent.putExtra("todo", "share");
                    startActivity(intent);
                    ToastUtil.ToastShort(AdsActivity.this, "请先登录");
                }
            }

            @JavascriptInterface
            @Override
            public void doShare() {
                if (!BtnUtils.isFastDoubleClick()) return;
                if (!TextUtils.isEmpty(json)) {
                    toShare();
                } else {
                    Intent intent = new Intent(AdsActivity.this, LoginActivity.class);
                    intent.putExtra("startType", LoginActivity.NoRestartMain);
                    intent.putExtra("todo", "share");
                    startActivity(intent);
                    ToastUtil.ToastShort(AdsActivity.this, "请先登录");
                }
            }

            @JavascriptInterface
            @Override
            public void doProductDetail(String pid) {
                EventBus.getDefault().post(new ActionEvent(pid));
            }

            @JavascriptInterface
            @Override
            public void doProductList() {
                EventBus.getDefault().post(new MainEvent(1).setLoginType(MainEvent.FROMFINDPASSWORD));
                Intent in = new Intent(AdsActivity.this, MainActivity2.class);
                startActivity(in);
            }

            @JavascriptInterface
            @Override
            public void doProductList(String type) {
                doProductList();
            }

            @JavascriptInterface
            @Override
            public void doRegister() {
                startActivity(new Intent(AdsActivity.this, RegisterActivity.class).
                        putExtra("from", AuthenticationActivity.FROM_Action38));
            }

            @JavascriptInterface
            @Override
            public void doRegister(String type) {
                startActivity(new Intent(AdsActivity.this, RegisterActivity.class).
                        putExtra("from", AuthenticationActivity.FROM_Action38));
            }

            @JavascriptInterface
            @Override
            public void doCertification() {
                startActivity(new Intent(AdsActivity.this, AuthenticationActivity.class).
                        putExtra("from", AuthenticationActivity.FROM_Action38));
            }

            @JavascriptInterface
            @Override
            public void doCertification(String type) {
                startActivity(new Intent(AdsActivity.this, AuthenticationActivity.class).
                        putExtra("from", AuthenticationActivity.FROM_Action38));
            }

        }, "android");//参1是回调接口的实现;参2是js回调对象的名称
    }

    private String JsUrl;

    @Override
    protected void toShare() {

//        if (TextUtils.isEmpty(shareUrl)) return;

        startActivity(new Intent(AdsActivity.this, ShareActivity.class).
                putExtra("title", TextUtils.isEmpty(shareTitle) ? "邀友" : shareTitle).
                putExtra("des", TextUtils.isEmpty(shareDes) ? "邀友" : shareDes).
                putExtra("url", TextUtils.isEmpty(shareUrl) ? "邀友" : shareUrl).
                putExtra("img", TextUtils.isEmpty(shareImg) ? "" : shareImg));
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void onEventMainThread(AdsEvent event) {
        json = SPUtils.geTinstance().getUid();
        int todo = event.todo;
        if (AdsEvent.TODO_SHARE == todo) {
//            UserInfo info = SPUtils.geTinstance().getUserInfo();
//            if (null != info)
//                url = url + "&uid=" + info.getUid() + "&fid=" + info.getFid() + "&invitedCode=" + info.getInvitedCode();
//            mWebView.loadUrl(url);
            toShare();
        } else if (AdsEvent.TODO_JSCallback == todo) {
//            mWebView.loadUrl("javascript:HuaXia.setToken(1)");//"+SPUtils.geTinstance().getToken()+",1,1,1"+"
            HashMap<String, String> map = new HashMap<>();
            map.put("back_url", JsUrl);
            mApiManager.returnBackUrl(map, new SimpleCallback<String>() {
                @Override
                public void onNext(String s) {
                    mWebView.loadUrl(s);
                }

                @Override
                public void onError() {
                }
            });
        } else if (AdsEvent.TODO_Reflush == todo) {
            progressbar.setVisibility(View.VISIBLE);
            addUidToUrl();
            mWebView.loadUrl(url);
            mWebView.reload();
        }
    }
//
//    private void doThumbs() {
//        if (thumbs == 1) {
//            mApiManager.doThumbs1(content.getId() + "", new SimpleCallback<String>() {
//                @Override
//                public void onNext(String b) {
//
//                }
//
//                @Override
//                public void onError() {
//                }
//            });
//        } else if (2 == thumbs) {
//            mApiManager.doThumbs2(content.getId() + "", new SimpleCallback<String>() {
//                @Override
//                public void onNext(String b) {
//
//                }
//
//                @Override
//                public void onError() {
//                }
//            });
//        }
//    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(SPUtils.geTinstance().getUid())) {
            toDoLogin(null);
            ToastUtil.ToastShort(AdsActivity.this, "请先登录");
            return;
        }
//        switch (view.getId()) {
//            case R.id.tv_bad:
//                if (2 == thumbs) {
//                    tv_bad.setText(content.getNoGoods() + "");
//                    tv_good.setText(content.getGoods() + "");
//                    thumbs = 0;
//                } else {
//                    tv_bad.setText(content.getNoGoods() + 1 + "");
//                    tv_good.setText(content.getGoods() + "");
//                    thumbs = 2;
//                }
//                break;
//            case R.id.tv_good:
//                if (1 == thumbs) {
//                    tv_good.setText(content.getGoods() + "");
//                    tv_bad.setText(content.getNoGoods() + "");
//                    thumbs = 0;
//                } else {
//                    tv_good.setText(content.getGoods() + 1 + "");
//                    tv_bad.setText(content.getNoGoods() + "");
//                    thumbs = 1;
//                }
//                break;
//        }
    }

    public void toDoLogin(String s) {
        Intent intent = new Intent(AdsActivity.this, LoginActivity.class);
        intent.putExtra("startType", LoginActivity.NoRestartMain);
        if (!TextUtils.isEmpty(s)) {
            intent.putExtra("todo", "webview");
        }
        startActivity(intent);
    }

    //定义回调接口
    public interface JsCallback {
        public void doLogin(String s);//基金登录

        public void doBack(String s);

        public void doLogining();

        public void doShare(String str);

        public void doShare();

        public void doProductDetail(String pid);

        public void doProductList();

        public void doProductList(String type);//产品列表

        public void doRegister();//注册

        public void doRegister(String type);//注册

        public void doCertification();

        public void doCertification(String type);//实名认证
    }

    public void getFundUrl() {
        Api.getClient().getFundMoney(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<String>(this) {
                    @Override
                    public void onSuccess(String s) {
                        if (mWebView != null && !TextUtils.isEmpty(s)) {
                            mWebView.loadUrl(s);
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });

    }

    //获取分享信息
    public void getInviteShareUrl() {
        Api.getClientNo().getIndexAdsLists("18", "2").compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<IndexAds>>(this) {
                    @Override
                    public void onSuccess(List<IndexAds> indexAdses) {
                        if (indexAdses != null && indexAdses.size() > 0) {
                            if (indexAdses.get(0).getShareVo() != null) {
                                shareTitle = indexAdses.get(0).getShareVo().getShareTitle();
                                shareDes = indexAdses.get(0).getShareVo().getShareContents();
                                shareUrl = indexAdses.get(0).getShareVo().getActivityUrl();
                                shareImg = indexAdses.get(0).getShareVo().getRealShareIcon();

                                UserInfo info = SPUtils.geTinstance().getUserInfo();
                                if (null == info) {
                                    url = indexAdses.get(0).getConnectUrl();
                                    if (!TextUtils.isEmpty(url)) {
                                        if (url.contains("?")) {
                                            url = url + "&reqFrom=android&channel=" + Constants.TypeChannel;
                                        } else {
                                            url = url + "?reqFrom=android&channel=" + Constants.TypeChannel;
                                        }
                                    }
                                } else {
                                    url = indexAdses.get(0).getConnectUrl();
                                    if (!TextUtils.isEmpty(url)) {
                                        if (url.contains("?")) {
                                            url = url + "&reqFrom=android" + "&uid=" + info.getUid() + "&fid=" + info.getFid() + "&invitedCode=" + info.getInvitedCode() + "&channel=" + Constants.TypeChannel;
                                        } else {
                                            url = url + "?reqFrom=android" + "&uid=" + info.getUid() + "&fid=" + info.getFid() + "&invitedCode=" + info.getInvitedCode() + "&channel=" + Constants.TypeChannel;
                                        }
                                    }
                                }
                                mWebView.loadUrl(url);
                            }
                        }
                    }
                });
    }
}
