package com.hxxc.user.app.ui.product;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hxxc.user.app.R;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.base.BaseFragment2;
import com.hxxc.user.app.widget.verticalpager.CustWebView;

import butterknife.BindView;

/**
 * Created by chenqun on 2017/1/6.
 */

public class CFragment2 extends BaseFragment2 {
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.ll_webcontainer)
    FrameLayout ll_webcontainer;

    private CustWebView webview;

    @Override
    protected int getContentViewID() {
        return R.layout.model_webview;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        if (null == webview) {
            webview = new CustWebView(getContext());
        }
        initWebView();
        ll_webcontainer.removeAllViews();
        ll_webcontainer.addView(webview, 0);

        webview.setType(5);
        if (mSwipeRefreshLayout != null) {

//            mSwipeRefreshLayout.addView(webview, 0);
            mSwipeRefreshLayout.setScrollUpChild(webview);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void initDatas() {
        progressbar.setVisibility(View.VISIBLE);
        webview.loadUrl(HttpRequest.problem2);//HttpRequest.problem
//        webview.loadUrl("http://192.168.8.237:8080/app/productProblemApp.html");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = webview.getSettings();
//        settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);// 支持js功能
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setDomStorageEnabled(true);//H5使用了在浏览器本地存储功能就必须加这句
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        settings.setDefaultTextEncodingName("UTF-8");

        webview.setWebViewClient(new WebViewClient());
        webview.setWebViewClient(new WebViewClient() {
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
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }
}
