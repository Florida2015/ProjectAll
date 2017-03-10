package com.hxxc.user.app.ui.mine.web;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
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
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.data.bean.ContractBillNewBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.FilesUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

import butterknife.BindView;
import rx.Subscriber;

/**
 * Created by houwen.lai on 2016/8/18.
 * web h5
 */
public class WebActivity extends BaseRxActivity {

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

    public static  final int Type_Nomal_Toolbar = 0;
    public static  final int Type_Extra_Toolbar = 1;
    @Override
    public void initView() {
        int intExtra = getIntent().getIntExtra("toolbarType", Type_Nomal_Toolbar);
        if(Type_Extra_Toolbar == intExtra){
            toolbar.setBackgroundResource(R.color.blue_title);
            toolbar.setNavigationIcon(R.mipmap.nav_back);
            toolbar_title.setTextColor(Color.WHITE);
        }

        toolbar_title.setText(getIntent().getStringExtra("title"));
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        if (swiperefresh_web!=null&&getIntent().hasExtra("flag")&&!getIntent().getBooleanExtra("flag",true))
            swiperefresh_web.setEnabled(false);
        else swiperefresh_web.setEnabled(true);

        swiperefresh_web.setColorSchemeResources(android.R.color.holo_blue_bright);
        swiperefresh_web.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        String url = getIntent().getStringExtra("url");

        if (!TextUtils.isEmpty(url)) LogUtil.d("webactivity url = "+url);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);

//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setAllowFileAccess(true);
//      webView.getSettings().setPluginsEnabled(true);support flash
//        webView.getSettings().setUseWideViewPort(true);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024*1024*8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);

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

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbar_title.setText(title);
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

        if (getIntent().getStringExtra("title").contains(UserInfoConfig.Creditor)){//债权
            //getIntent().getStringExtra("title").contains(UserInfoConfig.Contract
//            getContractBillNew(getIntent().getStringExtra("orderNo"));
//            getContractFile(url);

            StringBuffer stringBuffer = new StringBuffer(HttpRequest.baseUrl);
            stringBuffer.append(HttpRequest.httpContractFile).append("?reqUrl=").append(url)
                    .append("&token=").append(Api.token).append("&uid=").append(Api.uid).append("&channel=android");
            LogUtil.d("债权url = "+stringBuffer.toString());
            Uri uri = Uri.parse(stringBuffer.toString());
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
            return;
        }

        webView.loadUrl(url);

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

    /**
     * 获取债权 链接
     */
    private String orderNo;
    public void getContractBillNew(String orderNo){
        this.orderNo=orderNo;
        if (TextUtils.isEmpty(orderNo))return;
        Api.getClient().getContractBillNew(Api.uid,orderNo).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<ContractBillNewBean>(mContext) {
                    @Override
                    public void onSuccess(ContractBillNewBean contractBillNewBean) {
                        //https://lcsitn.huaxiafinance.com/caifu-web-open-platform/contact/getContractFile?reqUrl=bill%2F2016111516295100001%2F2264online.pdf&token=35c19ad9c0f061c092c1f87c898caeb9241&uid=241&channel=android
                        if (contractBillNewBean!=null){
//                        getContractFile(contractBillNewBean.getBillPath());

                        StringBuffer stringBuffer = new StringBuffer(HttpRequest.baseUrl);
                        stringBuffer.append(HttpRequest.httpContractFile).append("?reqUrl=").append(contractBillNewBean.getBillPath())
                                .append("&token=").append(Api.token).append("&uid=").append(Api.uid).append("&channel=android");
                        LogUtil.d("债权url = "+stringBuffer.toString());
                        Uri uri = Uri.parse(stringBuffer.toString());
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);
                        }
                    }
                });
    }

    /**
     * 获取债权
     * @param reqUrl
     */
    public void getContractFile(String reqUrl){
        Api.getClient().getContractFile(Api.uid,reqUrl).compose(RxApiThread.convert()).subscribe(new Subscriber<byte[]>() {
            @Override
            public void onCompleted() {
                LogUtil.d("pathurl=onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("pathurl=onError="+e.getMessage());
            }

            @Override
            public void onNext(byte[] bytes) {
                LogUtil.d("pathurl=sucesss");
                FilesUtil.getInstance().makeFolder(UserInfoConfig.getDownloadFilePath()+UserInfoConfig.text_cridt+"/");
                FilesUtil.getInstance().writeFileToSdcardByFileOutputStream(UserInfoConfig.getDownloadFilePath(),UserInfoConfig.text_cridt+"_"+orderNo+".pdf",bytes);


            }
        });
    }

}
