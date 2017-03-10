package com.huaxia.finance.request;

import android.app.Activity;

import com.framwork.Utils.MyLog;
import com.framwork.Utils.StringsUtils;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.constant.UrlConstants;

import org.apache.http.Header;

/**
 * 请求 产品列表 数据
 * Created by houwen.lai on 2016/1/22.
 */
public class ProductListRequest  {


    private static Activity mActivitiy;

    private static ProductListRequest instance = null;

    public static ProductListRequest getInstance(Activity activity){
        mActivitiy = activity;
        if (instance==null){
            instance = new ProductListRequest();
        }
        return instance;
    }


    /**
     * start 页
     * size 该页数据条数
     * 产品列表数据
     */
    public void setHttpPostRequest(int start,int size){
        BaseRequestParams params = new BaseRequestParams();
        params.put("start",""+start);
        params.put("size", "" + size);
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlProductList);

        MyLog.d("api_url=" + url.toString());
//        params.put("cookie","");
        DMApplication.getInstance().getHttpClient(mActivitiy).post(mActivitiy, url.toString(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);
                    MyLog.d("api_heads_header=" + headers.toString());
                    if (statusCode == 200) {
//                        BaseModel<ArrayList<ProductItemModel>> baseModel = BaseModel.prase(resultDate);
//                        if (baseModel.getStatus().equals(StateConstant.Status_success)){
//
//                        }
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
