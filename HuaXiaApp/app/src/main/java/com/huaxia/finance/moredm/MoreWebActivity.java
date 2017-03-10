package com.huaxia.finance.moredm;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * 更多 web
 * Created by houwen.lai on 2016/2/18.
 */
public class MoreWebActivity extends BaseActivity {
    private final String mPageName = MoreWebActivity.class.getSimpleName();

    private WebView mWebView;

    private ProgressBar progressbar;//进度条

    private String textTitle;
    private String url;

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
            MyLog.d("api="+url);
        }

        findAllViews();
    }

    public void findAllViews(){
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
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
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbar.setProgress(newProgress);
                if (newProgress>95){
                    progressbar.setVisibility(View.GONE);
                }
            }
        });

        //请求数据
        if (NetWorkUtils.isNetworkConnected(this)){
            relative_no_work.setVisibility(View.GONE);
            mWebView.loadUrl(url);
        }else{
            relative_no_work.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.VISIBLE);
            img_empty.setImageResource(R.drawable.icon_no_wifi);
            tv_reloading.setVisibility(View.VISIBLE);
            tv_reloading.setText("网络出现问题啦!");
        }
    }

}
