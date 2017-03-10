package com.huaxia.finance.huaxiamd;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
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
import com.umeng.analytics.MobclickAgent;

/**
 * 功能：花虾 首页
 * Created by houwen.lai on 2015/11/18.
 *
 */
public class HuaXiaFragment extends Fragment{

    private WebView mWebView;
    private PullToRefreshWebView mPullWebView;

    private String mPageName;
    int mNum;

    private boolean mFlag = false;//是否屏蔽超链接

    private String title;//

    /**
     * 网络
     */
    private LinearLayout linear_no_network;//
    private Button btn_reloading;
    private ProgressBar probar_no_work;

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
    }
    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        mPageName = String.format("fragment %d", mNum);
    }

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
        View view = myInflater.inflate(R.layout.menu_huaxia_fragment, container,
                false);
        initViews(view);
        return view;
    }

    public WebView getmWebView(){
        return mWebView;
    }

    public void initViews(View mview){
        mPullWebView = (PullToRefreshWebView) mview.findViewById(R.id.pull_webview);//new PullToRefreshWebView(this);
        mPullWebView.setPullLoadEnabled(false);
        mWebView = mPullWebView.getRefreshableView();

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
                    mWebView.loadUrl(DMConstant.UrlHuaxia);
                }
            }
        });

        mPullWebView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {

//                if (NetWorkUtils.isNetworkConnected(MenuActivity.getInstance())) {
//                   mWebView.loadDataWithBaseURL(null, "","text/html", "utf-8",null);
//                   mWebView.loadUrl(DMConstant.UrlHuaxia);
//                }else {
//                    getNetworkState();
////                    StringBuffer murl = new StringBuffer("file:");
////                    murl.append(DMConstant.web_path).append("/").append(DMConstant.web_path_huaxia);
////                    MyLog.d("api_onpagestarted=" + murl.toString());
////                    mWebView.loadUrl(murl.toString());
//                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {


            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                MyLog.d("api_url=>" + url);
                mWebView.loadUrl("javascript:hide_footer()");
                if (!TextUtils.isEmpty(url)) {
//                    if (url.contains("activityId") || url.contains("productId")) {
//                        MenuActivity.getInstance().startActivityForResult(new Intent(MenuActivity.getInstance(), WebActivity.class).putExtra("url", MenuActivity.getInstance().injectIsParams(url)), MenuActivity.RequestCodeHuaxia);
//                        return true;
//                    }else if(url.equals(DMConstant.UrlMangeMoney_d)){
//                        MenuActivity.getInstance().setTab(1);
//                        return true;
//                    }else if(url.contains("tel:")){
//                        PhoneUtils.showTel(MenuActivity.getInstance(),url);
//                        return true;
//                    }  else return false;
                    if(url.equals(DMConstant.UrlMangeMoney_d)){
//                        MenuActivity.getInstance().setTab(1);
                        return true;
                    }else if(url.contains("tel:")){
//                        PhoneUtils.showTel(MenuActivity.getInstance(),url);
                        return true;
                    }else if(url.contains(DMConstant.UrlHuaxia)){
                        return false;
                    }else{
//                        MenuActivity.getInstance().startActivityForResult(new Intent(MenuActivity.getInstance(), WebActivity.class).putExtra("url", MenuActivity.getInstance().injectIsParams(url)), MenuActivity.RequestCodeHuaxia);
                        return true;
                    }
                } else return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                StringBuffer murl = new StringBuffer("file:");
//                murl.append(DMConstant.web_path).append("/").append(DMConstant.web_path_huaxia);
//                MyLog.d("api_onpagestarted=" + murl.toString());
//                super.onPageStarted(view, murl.toString(), favicon);
                super.onPageStarted(view,url,favicon);
                if (DMConstant.flagHuaxia) {
                    mPullWebView.setVisibility(View.GONE);
                    linear_no_network.setVisibility(View.VISIBLE);
                    btn_reloading.setVisibility(View.GONE);
                    probar_no_work.setVisibility(View.VISIBLE);
                }
                DMConstant.flagWebViewError=false;
            }

            public void onPageFinished(WebView view, String url) {
                if (!DMConstant.flagWebViewError){
                    DMConstant.flagHuaxia=false;
                    mPullWebView.setVisibility(View.VISIBLE);
                    linear_no_network.setVisibility(View.GONE);
                }else DMConstant.flagHuaxia=true;
//                ToastUtils.showShortView(MenuActivity.getInstance(),"数据已刷新",R.layout.toast_view,0);
                mWebView.loadUrl("javascript:hide_footer()");
                MyLog.d("api_url_d=>" + url);
                mPullWebView.onPullDownRefreshComplete();
                setLastUpdateTime();
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
            //            //api21
//            @SuppressLint("NewApi")
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                if (request!=null&&request.getUrl()!=null){
//                    String scheme = request.getUrl().getScheme().trim();
//                    MyLog.d("api_url_should_new=>"+scheme);
////                    if (scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https")) {
//                    if (scheme.contains("activityId")||scheme.contains("productId")){
//                        MenuActivity.getInstance().startActivityForResult(new Intent(MenuActivity.getInstance(),WebActivity.class).putExtra("url", injectIsParams(scheme)),MenuActivity.RequestCodeHuaxia);
//                    }else{
//                        try {
//                            URL url = new URL(MenuActivity.getInstance().injectIsParams(request.getUrl().toString()));
//                            URLConnection connection = url.openConnection();
//                            return new WebResourceResponse(connection.getContentType(), connection.getHeaderField("encoding"), connection.getInputStream());
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                return null;
//
////                return super.shouldInterceptRequest(view, request);
//            }
//
            @Override
              public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                MyLog.d("api_url_viewxxx=>"+url);

                return super.shouldInterceptRequest(view,url);

            }
        });

        WebSettings webseting = mWebView.getSettings();

        webseting.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webseting.setLoadWithOverviewMode(true);//自适应屏
        webseting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

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

//        mWebView.loadUrl(DMConstant.UrlHuaxia);
//        if (NetWorkUtils.isNetworkConnected(MenuActivity.getInstance()))
            mWebView.loadUrl(DMConstant.UrlHuaxia);
//        else {
//            StringBuffer murl = new StringBuffer("file:");
//            murl.append(DMConstant.web_path).append("/").append(DMConstant.web_path_huaxia);
//            MyLog.d("api_onpagestarted=" + murl.toString());
//            mWebView.loadUrl(murl.toString());
//        }
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
