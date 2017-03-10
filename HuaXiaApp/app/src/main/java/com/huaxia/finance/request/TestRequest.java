package com.huaxia.finance.request;

import android.app.Activity;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.asychttpclient.RequestParams;
import com.huaxia.finance.DMApplication;

import org.apache.http.Header;

/**
 * Created by Administrator on 2016/1/18.
 */
public class TestRequest {

    private static Activity mActivitiy;

    private static TestRequest instance = null;

    public static TestRequest getInstance(Activity activity){
        mActivitiy = activity;
        if (instance==null){
            instance = new TestRequest();
        }
        return instance;
    }

    /**
     *
     */
    public void setHttpRequest(String url, final String name){
        RequestParams params = new RequestParams();
        MyLog.d("api_url=" + url);
//        params.put("cookie","");
        DMApplication.getInstance().getHttpClient(mActivitiy).post(mActivitiy, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    MyLog.d("api_heads_header=" + headers.toString());
                    if (statusCode == 200) {

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
