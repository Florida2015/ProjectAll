package com.hxxc.user.app.utils;

import android.text.TextUtils;

import com.hxxc.user.app.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenqun on 2016/6/21.
 */
public class RestUtils {

    public static Map<String, String> setParamss(Map<String, String> map) {
        Map<String, String> params = new HashMap<>();
        if (null != map) {
            params.putAll(map);
        }
        LogUtils.e(params.size() + "************************/********************" + params.toString());
        params.put("token", SPUtils.geTinstance().getToken());
        params.put("channel", Constants.TypeChannel);
        if(TextUtils.isEmpty(params.get("uid"))){
            params.put("uid", SPUtils.geTinstance().getUid());
        }
        return params;
    }
}
