package com.framwork.Utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by  on 2015/1/13.
 */
public class EncodeUtils {

    /**
     * 自定义URL编码
     * @param parentStr 需要编码的字符串
     * @return 编码后字符串
     */
    public static String encodeString(String parentStr){
        if(TextUtils.isEmpty(parentStr))
            return "";
        StringBuffer strBuf=new StringBuffer();
        String parentArr[]=parentStr.split("&");
        if(parentArr.length<=0)
            return "";
        for(int i=0;i<parentArr.length;i++){
            String childStr=parentArr[i];
            String childArr[]=childStr.split("=");
            if(childArr.length==2){
                String key=childArr[0].trim();
                String value=childArr[1].trim();
                try {
                    strBuf.append("&"+key+"="+ URLEncoder.encode(value, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "";
                }
            }
        }
        return strBuf.toString();
    }



}
