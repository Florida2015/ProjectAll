package com.huaxia.finance.request;

import android.app.Activity;

import com.framwork.asychttpclient.RequestParams;

import java.util.Map;

/**
 * Created by Administrator on 2016/1/20.
 */
public class BaseRequestParams extends RequestParams {

    public Activity activity;

    public BaseRequestParams(Activity activity) {
        this.activity= activity;
        init();
    }

    public BaseRequestParams(Map<String, String> source) {
        super(source);
        init();
    }

    public BaseRequestParams(String key, String value) {
        super(key, value);
        init();
    }

    public BaseRequestParams(Object... keysAndValues) {
        super(keysAndValues);
        init();
    }

    /**
     * 基本信息
     */
    public void init(){
//        if (this.activity!=null){
//            Map<String,String> map=ApplicationUtils.getDeviceInfo(this.activity);
//            this.put("uuid", ApplicationUtils.getDeviceInfo(this.activity).get("uuid"));//
//            this.put("device", ApplicationUtils.getDeviceInfo(this.activity).get("device"));//
//            this.put("os", ApplicationUtils.getDeviceInfo(this.activity).get("os"));//
//            this.put("osv", ApplicationUtils.getDeviceInfo(this.activity).get("osv"));//
//            this.put("dpi", ApplicationUtils.getDeviceInfo(this.activity).get("dpi"));//
//            this.put("net", ApplicationUtils.getDeviceInfo(this.activity).get("net"));//
//            this.put("source", "huaxia");//
//        }
    }
}
