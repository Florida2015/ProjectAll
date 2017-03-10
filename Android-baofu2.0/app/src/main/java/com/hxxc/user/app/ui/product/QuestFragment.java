package com.hxxc.user.app.ui.product;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hxxc.user.app.R;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.base.BaseRxFragment;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/1/11.
 */

public class QuestFragment extends BaseRxFragment {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void initDagger() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.model_webview_1;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {

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
        progressbar.setVisibility(View.VISIBLE);
        webview.loadUrl(HttpRequest.problem);//HttpRequest.problem
    }


}
