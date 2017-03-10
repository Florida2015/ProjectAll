package com.huaxia.finance.request;

import android.app.Activity;

import com.framwork.Utils.FileUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.asychttpclient.RequestParams;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.constant.DMConstant;

import org.apache.http.Header;

/**
 * Created by Administrator on 2015/11/20.
 */
public class WebRequest {


    private static Activity mActivitiy;

    private static WebRequest instance = null;

    public static WebRequest getInstance(Activity activity){
        mActivitiy = activity;
        if (instance==null){
            instance = new WebRequest();
        }
        return instance;
    }

    /**
     *
     */
    public void setHttpRequest(String url, final String name){

        RequestParams params = new RequestParams();

//        AsyncHttpClient client  = new AsyncHttpClient();
//        params.put("key","word");
//        client.addHeader("header", "header");
        MyLog.d("api_url="+url);
//        DMApplication.getInstance().getHttpClient().set
        params.put("cookie","");
        DMApplication.getInstance().getHttpClient(mActivitiy).post(mActivitiy, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    MyLog.d("api_heads_header=" + headers.toString());
                    if (statusCode == 200) {
                        FileUtils.getInstance().writeFileToSdcardByFileOutputStream(DMConstant.web_path, name, responseBody);
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");


            }
        });

    }


}
