package com.hxxc.huaxing.app.ui.mine.account;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.LogUtil;

import org.apache.http.util.EncodingUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/11.
 * 跳转到开通E账户
 */

public class WebOpenEaccountActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.back)
    ImageButton back;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swiperefresh_web;
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

        if (mSwipeRefreshLayout!=null&&getIntent().hasExtra("flag")&&!getIntent().getBooleanExtra("flag",true)) {
            mSwipeRefreshLayout.setEnabled(false);
            swiperefresh_web.setEnabled(false);
        }else{
            mSwipeRefreshLayout.setEnabled(true);
            swiperefresh_web.setEnabled(true);}

        swiperefresh_web.setRefreshing(false);
        swiperefresh_web.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String url = getIntent().getStringExtra("url");
                //        webView.loadUrl(url);
                //post访问需要提交的参数
                String postDate = "txtName=zzz&QueryTypeLst=1&CertificateTxt=dsds";
                //通过EncodingUtils.getBytes(data, charset)方法进行转换
                LogUtil.d("data==url="+url+getIntent().getStringExtra("data"));
//        LogUtil.d("data2="+EncodingUtils.getBytes(getIntent().getStringExtra("data"), "utf-8"));
//        webView.postUrl(url, EncodingUtils.getBytes(getIntent().getStringExtra("data"), "utf-8"));

                webView.loadDataWithBaseURL(url,getIntent().getStringExtra("data"),"text/html; charset=UTF-8",null,null);
            }
        });

        initWebView();

    }

    @Override
    public void initPresenter() {

    }

    private void initWebView() {
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
        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存：
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存：
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){

            @SuppressLint("NewApi")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                LogUtil.d("web_shouldOverrideUrlLoading="+request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                LogUtil.d("web_shouldInterceptRequest="+url);
                if (!TextUtils.isEmpty(url)&&url.contains("huaxin_page/notify")){
                    AppManager.getAppManager().finishActivity(RechargeActivity.class);
                    AppManager.getAppManager().finishActivity(WithdrawalsActivity.class);
                    finish();
                    return null;
                }else  return super.shouldInterceptRequest(view, url);
            }
        });

//
//        webView.loadUrl(url);
        //post访问需要提交的参数
        String postDate = "txtName=zzz&QueryTypeLst=1&CertificateTxt=dsds";
        //通过EncodingUtils.getBytes(data, charset)方法进行转换
        LogUtil.d("data==url="+url+getIntent().getStringExtra("data"));
//        LogUtil.d("data2="+EncodingUtils.getBytes(getIntent().getStringExtra("data"), "utf-8"));
//        webView.postUrl(url, EncodingUtils.getBytes(getIntent().getStringExtra("data"), "utf-8"));

        webView.loadDataWithBaseURL(url,getIntent().getStringExtra("data"),"text/html; charset=UTF-8",null,null);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressbar.setVisibility(View.GONE);
                    swiperefresh_web.setRefreshing(false);
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

    @OnClick({R.id.back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.back:
                if (webView.canGoBack())webView.goBack();
                else finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack())webView.goBack();
            else {
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
//        webView.reload();
        setRefresh(false);
    }

}
