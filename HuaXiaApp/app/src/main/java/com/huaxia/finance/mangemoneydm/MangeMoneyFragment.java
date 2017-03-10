package com.huaxia.finance.mangemoneydm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.framwork.Utils.DateUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.NetWorkUtils;
import com.framwork.pullrefresh.ui.PullToRefreshBase;
import com.framwork.pullrefresh.ui.PullToRefreshWebView;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.DMConstant;

/**
 * 功能：理财页面
 * Created by houwen.lai on 2015/11/18.
 *
 *
 */
public class MangeMoneyFragment extends Fragment {

    private WebView mWebView;
    private PullToRefreshWebView mPullWebView;

    /**
     * 网络
     */
    private LinearLayout linear_no_network;//
    private Button btn_reloading;
    private ProgressBar probar_no_work;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (container == null) {
            // Currently in a layout without a container, so no
            // reason to create our view.
            return null;
        }
        LayoutInflater myInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = myInflater.inflate(R.layout.menu_mangemoney_fragment, container,
                false);
        initViews(view);
        return view;
    }

    public WebView getmWebView(){
        return mWebView;
    }

    public void initViews(View mview){
        mPullWebView = (PullToRefreshWebView) mview.findViewById(R.id.pull_webview_mangemoney);//new PullToRefreshWebView(this);
        mPullWebView.setPullLoadEnabled(false);

        linear_no_network = (LinearLayout) mview.findViewById(R.id.linear_no_network);
        btn_reloading = (Button) mview.findViewById(R.id.btn_reloading);
        probar_no_work = (ProgressBar) mview.findViewById(R.id.probar_no_work);

        //网络判断
        getNetworkState();

        linear_no_network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetWorkUtils.isNetworkConnected(getActivity())){
                    mPullWebView.setVisibility(View.GONE);
                    linear_no_network.setVisibility(View.VISIBLE);
                    btn_reloading.setVisibility(View.VISIBLE);
                    probar_no_work.setVisibility(View.GONE);
                }else {
                    btn_reloading.setVisibility(View.GONE);
                    probar_no_work.setVisibility(View.VISIBLE);
                    mWebView.loadUrl(DMConstant.UrlMangeMoney);
                }
            }
        });


        mPullWebView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {
                MyLog.d("api_mangemoney_url="+DMConstant.UrlMangeMoney);

//                if (NetWorkUtils.isNetworkConnected(MenuActivity.getInstance())){
//                    mWebView.loadUrl(DMConstant.UrlMangeMoney);
//                }else{
//                    getNetworkState();
//                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {


            }
        });

        mWebView = mPullWebView.getRefreshableView();
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.d("api_url=>" + url);
                mWebView.loadUrl("javascript:hide_footer()");
                if (!TextUtils.isEmpty(url)) {
                    if (url.contains("activityId") || url.contains("productId")) {
//                        MenuActivity.getInstance().startActivityForResult(new Intent(MenuActivity.getInstance(), WebActivity.class).putExtra("url", MenuActivity.getInstance().injectIsParams(url)), MenuActivity.RequestCodeHuaxia);
                        return true;
                    } else return false;
                } else return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (DMConstant.flagMangeMoney) {
                    mPullWebView.setVisibility(View.GONE);
                    linear_no_network.setVisibility(View.VISIBLE);
                    btn_reloading.setVisibility(View.GONE);
                    probar_no_work.setVisibility(View.VISIBLE);
                }
                DMConstant.flagWebViewError=false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!DMConstant.flagWebViewError){
                    DMConstant.flagMangeMoney = false;
                    mPullWebView.setVisibility(View.VISIBLE);
                    linear_no_network.setVisibility(View.GONE);
                }else  DMConstant.flagMangeMoney = true;
//                ToastUtils.showShortView(MenuActivity.getInstance(), "数据已刷新", R.layout.toast_view, 0);
                mWebView.loadUrl("javascript:hide_footer()");
                mPullWebView.onPullDownRefreshComplete();
                setLastUpdateTime();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                DMConstant.flagWebViewError=true;
                MyLog.d("api_url_failing=" + failingUrl);
                mPullWebView.setVisibility(View.GONE);
                linear_no_network.setVisibility(View.VISIBLE);
                btn_reloading.setVisibility(View.VISIBLE);
                probar_no_work.setVisibility(View.GONE);
            }
        });

        WebSettings webseting = mWebView.getSettings();
        webseting.setJavaScriptEnabled(true);
        webseting.setDomStorageEnabled(true);
        webseting.setAppCacheMaxSize(1024 * 1024 * 8);//设置缓冲大小，我设的是8M
        String appCacheDir = DMApplication.getInstance().getDir("cache", Context.MODE_PRIVATE).getPath();
        webseting.setAppCachePath(appCacheDir);
        webseting.setAllowFileAccess(true);
        webseting.setAppCacheEnabled(true);
//        if (NetWorkUtils.isNetworkConnected(MenuActivity.getInstance())){
//            webseting.setCacheMode(WebSettings.LOAD_DEFAULT);
//        }else{
//            webseting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }

        mWebView.loadUrl(DMConstant.UrlMangeMoney);
        setLastUpdateTime();

    }

    private void setLastUpdateTime() {
        String text = DateUtils.getInstanse().getNowDate(DateUtils.YYYYMMDDHHMMSS);
        mPullWebView.setLastUpdatedLabel(text);
    }


    //网络判断
    public void getNetworkState(){
        if (!NetWorkUtils.isNetworkConnected(getActivity())){
            mPullWebView.setVisibility(View.GONE);
            linear_no_network.setVisibility(View.VISIBLE);
            btn_reloading.setVisibility(View.VISIBLE);
            probar_no_work.setVisibility(View.GONE);
        }else {
            mPullWebView.setVisibility(View.VISIBLE);
            linear_no_network.setVisibility(View.GONE);
        }
    }


}
