package com.hxxc.huaxing.app.ui;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenqun on 2016/10/11.
 */

public class WebActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.back)
    ImageButton back;

    @BindView(R.id.web_activity)
    WebView webView;
    @BindView(R.id.progress_web)
    ProgressBar progressbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        back.setVisibility(View.VISIBLE);
        String title = getIntent().getStringExtra("title");
        if(TextUtils.isEmpty(title)){
            title = "财划算";
        }
        toolbar_title.setText(title);
        initWebView();
    }

    private void initWebView() {
        if (mSwipeRefreshLayout!=null&&getIntent().hasExtra("flag")&&!getIntent().getBooleanExtra("flag",true))
            mSwipeRefreshLayout.setEnabled(false);
        else mSwipeRefreshLayout.setEnabled(true);

        String url = getIntent().getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setAllowFileAccess(true);
//      webView.getSettings().setPluginsEnabled(true);support flash
//        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);//support zoom
        webView.getSettings().setUseWideViewPort(true);// 这个很关键
        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.requestFocus();
        webView.getSettings().setDefaultTextEncodingName("utf-8") ;
        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存：
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;//禁止超链接 调转
            }
        });

        if (getIntent().hasExtra("url")&&!TextUtils.isEmpty(getIntent().getStringExtra("url"))){
            webView.loadUrl(url);
        }

        if (getIntent().hasExtra("data")&&!TextUtils.isEmpty(getIntent().getStringExtra("data"))){
            webView.loadData(getIntent().getStringExtra("data"),"text/html; charset=UTF-8",null);
        }

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressbar.setVisibility(View.GONE);
                } else {
                    if (progressbar.getVisibility() == View.GONE)
                        progressbar.setVisibility(View.VISIBLE);
                    progressbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        LogUtil.d("densityDpi = " + mDensity);
        if (mDensity == 240) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if(mDensity == 120) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if(mDensity == DisplayMetrics.DENSITY_XHIGH){
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }else if (mDensity == DisplayMetrics.DENSITY_TV){
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
    }

    @Override
    public void initPresenter() {

    }

    @OnClick({R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        setRefresh(false);
        webView.reload();
    }
}
