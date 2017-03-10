package com.hxxc.user.app.ui.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * Created by houwen.lai on 2016/12/9.
 * 我的  基金页
 */

public class FundActivity extends BaseRxActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_back)
    Button toolbar_back;

    @BindView(R.id.swiperefresh_web)
    SwipeRefreshLayout swiperefresh_web;
    @BindView(R.id.web_activity)
    WebView webView;
    @BindView(R.id.progress_web)
    ProgressBar progressbar;

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public int getLayoutId() {
        return R.layout.web_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("基金");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        swiperefresh_web.setColorSchemeResources(android.R.color.holo_blue_bright);
        swiperefresh_web.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFundUrl();
            }
        });

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
        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存：
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressbar.setVisibility(View.GONE);
                } else {
                    swiperefresh_web.setRefreshing(false);
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
        getFundUrl();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (webView.canGoBack()){
                    webView.goBack();
                    return false;
                }else{
                    finish();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();
                return false;
            }else {
                finish();
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        }
        return false;
    }


    public void getFundUrl(){

        Api.getClient().getFundMoney(Api.uid).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<String>(mContext) {
                   @Override
                public void onSuccess(String s) {
                    if (webView!=null&&!TextUtils.isEmpty(s)){
                        webView.loadUrl(s);
                    }
                }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });

    }

}
