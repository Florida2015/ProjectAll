package com.huaxia.finance.mangemoneydm;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.ToastUtils;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 *
 * Created by houwen.lai on 2016/2/4.
 */
public class PayWebActivity extends BaseActivity{
    private final String mPageName = PayWebActivity.class.getSimpleName();

    private WebView webview_pay;

    private String url;

    private String temp;

    private ImageButton imgbtn_back;

    private String orderId;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x11://
                    PayWebActivity.this.finish();
                    break;
                case 0x12://订单列表
                    if (temp.equals("1")){
                        ApproveActivity.finshApproveActivity();
                        PayOrderActivity.finshLoginActivity();
                        ProductExplainActivity.finshLoginActivity();
//                        ToastUtils.showShort("下单成功");
                        MenuTwoActivity.getInstance().setTab(3);
                        startActivity(new Intent(PayWebActivity.this,OrderListActvity.class).putExtra("type",3).putExtra("orderId",orderId));
                    }else{
                        ToastUtils.showShort("操作成功");
                        ApproveActivity.finshApproveActivity();
                    }
                    PayWebActivity.this.finish();
                    break;
            }
        }
    };


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
        setBaseContentView(R.layout.web_pay);

        relative_title_bar.setVisibility(View.GONE);
        relative_no_work.setVisibility(View.VISIBLE);
        img_empty.setVisibility(View.GONE);
        tv_reloading.setVisibility(View.GONE);
        ballview.setVisibility(View.VISIBLE);

        temp = getIntent().getStringExtra("temp");
        url = getIntent().getStringExtra("url");
//        url = "https://testmobile.payeco.com/ppi/h5/plugin/itf.do?Version=2.0.0&MerchantId=002020000008&MerchOrderId=HXW05161852798&Amount=800&TradeTime=20160305161852&OrderId=9113204052944194a554312409a0c860&VerifyTime=E05E5B092F104011B73E28EAD86C7F721C7A30FF67D6F8BB5D5AD4CA25A645D793EE0AB81787BC17&Sign=UTFaH6qNTt6mVH/L47zIfHhLLxRNB3ncJDan3CEVZUBJcxCuUrF9cwrr6gvjPcGZGu1cKrneyUZ1dxh+ddgIFrMFdOIi9PQL7dp4BWy5ReDJ9hsTFkKPWwthz3tmB6Eluo4BdEpNSHVJY2IASahq5a/kCgM2RR3YuyJIpuaRd9w=&tradeId=h5Init&TradeCode=PayOrder";
        orderId = getIntent().getStringExtra("orderId");
        MyLog.d("api_pay_url=" + url);
        imgbtn_back = (ImageButton) findViewById(R.id.imgbtn_back);
        webview_pay = (WebView) findViewById(R.id.webview_pay);
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview_pay.canGoBack()) {
                    webview_pay.goBack();
                }else finish();
            }
        });


        webview_pay.getSettings().setJavaScriptEnabled(true);
        webview_pay.getSettings().setDomStorageEnabled(true);
        webview_pay.getSettings().setLoadWithOverviewMode(true);//自适应屏
        webview_pay.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webview_pay.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                MyLog.d("api_loading=" + url);
//                return super.shouldOverrideUrlLoading(view, url);
//                if (url.contains("huaxia-front/more/isAuthSucess")) {//
//                    handler.sendEmptyMessageDelayed(0x11,300);
//                    return false;
//                } else if (url.contains("ppi/h5/pay/result/itf.do")) {//订单列表
//                    handler.sendEmptyMessageDelayed(0x12,300);
//                    return false;
//                }
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                MyLog.d("api_request=" + request.getUrl().getQueryParameterNames().toString());
//                if (request.getUrl().getQueryParameterNames().size() > 2) {
//                    MyLog.d("api_request_Sign=" + request.getUrl().getQueryParameter("Sign"));
//                    MyLog.d("api_request_Status=" + request.getUrl().getQueryParameter("Status"));
//                    MyLog.d("api_request_MerchantId=" + request.getUrl().getQueryParameter("MerchantId"));
//                    MyLog.d("api_request_Amount=" + request.getUrl().getQueryParameter("Amount"));
//                    MyLog.d("api_request_OrderId=" + request.getUrl().getQueryParameter("OrderId"));
//                    MyLog.d("api_request_MerchOrderId=" + request.getUrl().getQueryParameter("MerchOrderId"));
//                    MyLog.d("api_request_respCode=" + request.getUrl().getQueryParameter("respCode"));
//                }
                return super.shouldInterceptRequest(view, request);
            }
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                MyLog.d("api_url_web=>" + url);
                if (url.contains("payment/ispaysucess")) {//huaxia-front/more/isAuthSucess
                    handler.sendEmptyMessageDelayed(0x11,300);
                } else if (url.contains("payment/isAuthSucess")) {//ppi/h5/pay/result/itf.do订单列表
                    handler.sendEmptyMessageDelayed(0x12,300);
                }
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
//                MyLog.d("api_url_load=>" + url);
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
//                handler.proceed();
            }
        });

        webview_pay.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress>95) relative_no_work.setVisibility(View.GONE);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        webview_pay.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            if (webview_pay.canGoBack()){
                webview_pay.goBack();
            }else finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
