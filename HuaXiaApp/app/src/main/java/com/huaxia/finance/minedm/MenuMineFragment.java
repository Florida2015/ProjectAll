package com.huaxia.finance.minedm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.pullrefresh.ui.PullToRefreshBase;
import com.framwork.pullrefresh.ui.PullToRefreshWebView;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.DMConstant;
import com.huaxia.finance.constant.UserConstant;

/**
 * 功能：我的页面
 * Created by houwen.lai on 2015/11/18.
 *
 */
public class MenuMineFragment extends Fragment {

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
        View view = myInflater.inflate(R.layout.menu_mine_fragment, container,
                false);
        initViews(view);
        return view;
    }

    public WebView getmWebView(){
        return mWebView;
    }

    public void initViews(View mview){
        mPullWebView = (PullToRefreshWebView) mview.findViewById(R.id.pull_webview_mine);//new PullToRefreshWebView(this);
        mPullWebView.setPullLoadEnabled(false);

        linear_no_network = (LinearLayout) mview.findViewById(R.id.linear_no_network);
        btn_reloading = (Button) mview.findViewById(R.id.btn_reloading);
        probar_no_work = (ProgressBar) mview.findViewById(R.id.probar_no_work);

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
                    mWebView.loadUrl(DMConstant.UrlMine);
                }
            }
        });


        mPullWebView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {

//                mWebView.loadUrl(DMConstant.UrlMine);
//                if (NetWorkUtils.isNetworkConnected(MenuActivity.getInstance())) {
//                    if (!TextUtils.isEmpty(DMApplication.token)) {
//                        MyLog.d("api_mine_url=" + DMConstant.UrlMine + "&token=" + DMApplication.token);
//                        mWebView.loadUrl(DMConstant.UrlMine + "&token=" + DMApplication.token);
//                    } else {
//                        MyLog.d("api_mine_url=" + DMConstant.UrlMine);
//                        mWebView.loadUrl(DMConstant.UrlMine);
//                    }
//                }else getNetworkState();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {


            }
        });

        mWebView = mPullWebView.getRefreshableView();
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.d("api_url=>" + url);
//                mWebView.loadUrl("javascript:hide_footer()");
                if (!TextUtils.isEmpty(url)) {
//                    if (url.contains("activityId") || url.contains("productId")||url.contains(DMConstant.UrlMine_more)||url.contains("myorderlist")||url.contains("state=")) {
//                        MenuActivity.getInstance().startActivityForResult(new Intent(MenuActivity.getInstance(), WebActivity.class).putExtra("url", MenuActivity.getInstance().injectIsParams(url)).putExtra("flag",true), MenuActivity.RequestCodeHuaxia);
//                        return true;
//                    }else if(url.contains("tel:")){
//                        PhoneUtils.showTel(MenuActivity.getInstance(), url);
//                        return true;
//                    }else return false;
                    if (url.contains("tel:")){
//                        PhoneUtils.showTel(MenuActivity.getInstance(), url);
                        return true;
                    }else if(url.equals(DMConstant.Urllogin)){
                        DMApplication.isLoginFlag=false;
                        SharedPreferencesUtils.getInstanse().putKeyValue(getActivity(), UserConstant.key_token,"");
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        return true;
                    }else if(DMConstant.UrlMine.contains(url)){
                        return false;
                    }else{
//                        MenuActivity.getInstance().startActivityForResult(new Intent(MenuActivity.getInstance(), WebActivity.class).putExtra("url", MenuActivity.getInstance().injectIsParams(url)).putExtra("flag",true), MenuActivity.RequestCodeHuaxia);
                        return true;
                    }
                }else  return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!url.contains("display=")) {
                    if (!TextUtils.isEmpty(DMApplication.token)) {
                        url = DMConstant.UrlMine + "&token=" + DMApplication.token;
                    } else {
                        url = DMConstant.UrlMine;
                    }
                }
                MyLog.d("api_url_start="+url);
                super.onPageStarted(view, url, favicon);
                mWebView.loadUrl("javascript:hide_footer()");
                if (DMConstant.flagMine) {
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
                    DMConstant.flagMine = false;
                    mPullWebView.setVisibility(View.VISIBLE);
                    linear_no_network.setVisibility(View.GONE);
                }else DMConstant.flagMine = true;
//                ToastUtils.showShortView(MenuActivity.getInstance(), "数据已刷新", R.layout.toast_view, 0);
                mWebView.loadUrl("javascript:hide_footer()");
                mPullWebView.onPullDownRefreshComplete();
                setLastUpdateTime();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                DMConstant.flagWebViewError=true;
                MyLog.d("api_url_failing="+failingUrl);
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
        if (!TextUtils.isEmpty(DMApplication.token)){
            mWebView.loadUrl(DMConstant.UrlMine+"&token="+DMApplication.token);
        }else{
            mWebView.loadUrl(DMConstant.UrlMine);
        }
        MyLog.d("api_mine_url="+DMConstant.UrlMine);
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
