package com.huaxia.finance.minedm;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.huaxia.finance.R;
import com.huaxia.finance.base.BaseActivity;
import com.huaxia.finance.constant.UrlConstants;
import com.umeng.analytics.MobclickAgent;

/**
 * 功能：协议界面
 * Created by houwen.lai on 2015/11/23.
 */
public class AgreementActivity extends BaseActivity {
    private final String mPageName = AgreementActivity.class.getSimpleName();

    public static final String agreenment = "regist_agreement.pdf";

    private WebView webview_agreement;

    private ProgressBar progressbar;//进度条

//    PDFView pdfView;

    Integer pageNumber = 1;
    String pdfName = agreenment;

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
        setBaseContentView(R.layout.agreement_activity);

        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        img_btn_title_right= (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_right.setVisibility(View.GONE);
        img_btn_title_back.setVisibility(View.VISIBLE);

        webview_agreement = (WebView) findViewById(R.id.webview_agreement);
//        pdfView = (PDFView) findViewById(R.id.pdfView);

        img_btn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
            }
        });

        tv_title_bar.setText("注册协议");

        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        webview_agreement.getSettings().setJavaScriptEnabled(true);
        webview_agreement.getSettings().setDomStorageEnabled(true);
        webview_agreement.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webview_agreement.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressbar.setProgress(newProgress);
                if (newProgress > 95) {
                    progressbar.setVisibility(View.GONE);
                }
            }
        });

        webview_agreement.loadUrl(UrlConstants.urlBase_web+UrlConstants.urlRegistAgrement);

//        display(agreenment, true);
    }


//    private void display(String assetFileName, boolean jumpToFirstPage) {
//        if (jumpToFirstPage) pageNumber = 1;
//        setTitle(pdfName = assetFileName);
//
//        pdfView.fromAsset(assetFileName)
//                .defaultPage(pageNumber).swipeVertical(true)
//                .onPageChange(this)
//                .onPageChange(this)
//                .load();
//    }

//    @Override
//    public void onPageChanged(int page, int pageCount) {
//        pageNumber = page;
////        setTitle(format("%s %s / %s", pdfName, page, pageCount));
//    }




}
