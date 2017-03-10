package com.huaxia.finance.mangemoneydm;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.request.BaseRequestParams;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.Header;

/**
 * 支付订单 出借协议和服务协议
 * 订单详情 服务协议 债券组合
 * Created by houwen.lai on 2016/3/1.
 */
public class AgreetBondWebActivity extends BaseActivity{
    private final String mPageName = AgreetBondWebActivity.class.getSimpleName();

    private WebView mWebView;

    private ProgressBar progressbar;//进度条

    private String textTitle;
    private String url;

    private String agreement;

    private String orderid;

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
        setBaseContentView(R.layout.more_web);
        textTitle = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)){
            MyLog.d("api=" + url);
        }

        agreement = getIntent().getStringExtra("agreement");

        orderid = getIntent().getStringExtra("orderid");

        findAllViews();
    }

    public void findAllViews(){
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_back.setVisibility(View.VISIBLE);
        img_btn_title_right.setVisibility(View.GONE);
        tv_title_bar.setText(textTitle);
        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView= (WebView) findViewById(R.id.webview_more);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.getSettings().setSupportZoom(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbar.setProgress(newProgress);
                if (newProgress > 95) {
                    progressbar.setVisibility(View.GONE);
                }
            }
        });


        if (NetWorkUtils.isNetworkConnected(this)) {
            relative_no_work.setVisibility(View.GONE);
            if (TextUtils.isEmpty(orderid)) {
                mWebView.loadUrl(url);
            } else {
                setAgreementHttpRequest();
            }
        }else{
            relative_no_work.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setImageResource(R.drawable.icon_no_wifi);
            tv_reloading.setVisibility(View.VISIBLE);
            tv_reloading.setText("网络出现问题啦!");
        }

    }

    public void setAgreementHttpRequest(){
        final BaseRequestParams params = new BaseRequestParams();
        params.put("orderid", orderid);//
        String url = UrlConstants.urlBase+UrlConstants.urlOrderAgrement+orderid;
        MyLog.d("api_agrement_url=" +url);
        DMApplication.getInstance().getHttpClient(this).get(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<String> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<String>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {

                            mWebView.getSettings().setDefaultTextEncodingName("utf-8") ;
                            mWebView.loadData(baseModel.getData(), "text/html; charset=UTF-8", null);//text/html; charset=UTF-8

                        } else {
                            ToastUtils.showShort(baseModel.getMsg());

                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");

            }
        });
    }

}
