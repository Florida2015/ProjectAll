package com.huaxia.finance.mangemoneydm;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.UrlConstants;
import com.umeng.analytics.MobclickAgent;

/**
 * 产品详情
 * Created by houwen.lai on 2016/1/25.
 */
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener{
    private final String mPageName = ProductDetailActivity.class.getSimpleName();

    private WebView mWebView;
    private ProgressBar progressbar;//进度条

    private String url;
    private String productId;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.pull_refresh_webview);
        productId = getIntent().getStringExtra("productId");
        findAllViews();

    }

    public void findAllViews(){
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText("产品详情");
        img_btn_title_back.setOnClickListener(this);

        mWebView = (WebView) findViewById(R.id.pull_webview);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.d("api_product_detail=" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbar.setProgress(newProgress);
                if (newProgress > 95) {
                    progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
//                tv_title_bar.setText(title);
                MyLog.d("api_product_detail_title=" + title);
                super.onReceivedTitle(view, title);
            }
        });
        StringBuffer url=new StringBuffer(UrlConstants.urlBase_web);
        url.append(UrlConstants.urlProductDetail);
        url.append(productId);
        if (NetWorkUtils.isNetworkConnected(this)) {
            relative_no_work.setVisibility(View.GONE);
            mWebView.loadUrl(url.toString());
            MyLog.d("api_product_detail_url en=" + url.toString());
        }else{
            relative_no_work.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setImageResource(R.drawable.icon_no_wifi);
            tv_reloading.setVisibility(View.VISIBLE);
            tv_reloading.setText("网络出现问题啦!");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_btn_title_back:
                if (mWebView.canGoBack()){
                    tv_title_bar.setText("产品详情");
                    mWebView.goBack();
                }else finish();
//                overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_in);
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            if (mWebView.canGoBack()){
                tv_title_bar.setText("产品详情");
                mWebView.goBack();
            }else finish();
        }
        return true;
    }
}
