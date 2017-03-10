package com.hxxc.user.app.ui.pager.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.ExitLoginEvent;
import com.hxxc.user.app.Event.LoginEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.R;
import com.hxxc.user.app.contract.presenter.ProductPresenter;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.ui.pager.BasePager;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.widget.MyWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

/**
 * Created by chenqun on 2016/11/18.
 */

public class ProductPager3 extends BasePager {
    @BindView(R.id.progressbar2)
    ProgressBar progressbar2;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.ll_webcontainer)
    FrameLayout ll_webcontainer;

    //    @BindView(R.id.webview)
    private MyWebView webview;
    private Unbinder unbinder;
    private String mUrl;

    public ProductPager3(Context context) {
        super(context);
        ll_webcontainer.removeAllViews();
        ll_webcontainer.addView(webview, 0);
        if (mSwipeRefreshLayout != null) {
//            mSwipeRefreshLayout.addView(webview, 0);
            mSwipeRefreshLayout.setScrollUpChild(webview);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.model_webview, null);
        unbinder = ButterKnife.bind(this, view);
        inits();
        return view;
    }

    @Override
    public void initData() {
        setRefresh(true);
        onRefresh();
    }

    private void inits() {
        progressbar.setVisibility(View.GONE);
        if (null == webview) {
            webview = new MyWebView(HXXCApplication.getContext());
        }
        initWebView();
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        Subscription s = mApiManager.getProductList2(ProductPresenter.TYPE_JIJIN + "", "1", "10", new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                mUrl = s;
                loadUrl();
                setRefresh(false);
            }

            @Override
            public void onError() {
                setRefresh(false);
            }
        });
        addSubscription(s);
    }

    private void loadUrl() {
        if (null != webview) {
//            webview.loadUrl("https://www.baidu.com/");
            progressbar2.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(SPUtils.geTinstance().getUid()))
                webview.loadUrl(mUrl + "?uid=" + SPUtils.geTinstance().getUid() + "&channel=" + Constants.TypeChannel);
            else
                webview.loadUrl(mUrl + "?channel=" + Constants.TypeChannel);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = webview.getSettings();
//        settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);// 支持js功能
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);//H5使用了在浏览器本地存储功能就必须加这句
        webview.setWebViewClient(new WebViewClient() {
            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar2.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                progressbar2.setVisibility(View.GONE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
            // 所有链接跳转会走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent in = new Intent(mContext, AdsActivity.class);
                in.putExtra("url", url);
                in.putExtra("title", Constants.TEXT_JiJin);
                mContext.startActivity(in);
//                view.loadUrl(url);// 在跳转链接时强制在当前webview中加载
                //此方法还有其他应用场景, 比如写一个超链接<a href="tel:110">联系我们</a>, 当点击该超链接时,可以在此方法中获取链接地址tel:110, 解析该地址,获取电话号码, 然后跳转到本地打电话页面, 而不是加载网页, 从而实现了webView和本地代码的交互
                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // 进度发生变化
                progressbar2.setProgress(newProgress);
            }
        });
    }

    @Override
    public void onExitLoginEvent(ExitLoginEvent event) {
        super.onExitLoginEvent(event);
        webview.loadUrl("www.baidu.com");
        loadUrl();
    }

    @Override
    public void onLoginEvent(LoginEvent event) {
        super.onLoginEvent(event);
        loadUrl();
    }
}
