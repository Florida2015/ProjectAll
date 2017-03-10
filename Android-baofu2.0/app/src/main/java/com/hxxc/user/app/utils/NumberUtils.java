package com.hxxc.user.app.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenqun on 2016/6/23.
 * 对数字操作的工具类
 */
public class NumberUtils {
    public static double mul(float v1,int v2){
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static double mul(double v1,int v2){
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static  BigDecimal formatNum(BigDecimal b){
        if(null != b) {
            String string = b.toString();
            int i = string.indexOf(".");
            if (i == -1) {
                return b;
            }
            String string2 = string.substring(0, i + 3);

            return new BigDecimal(string2);
        }else{
            return new BigDecimal(0);
        }
    }


    public static String Phone2xing(String phoneNumber){
        if (CommonUtil.isMobileNoAll(phoneNumber)){
            return new StringBuilder(phoneNumber.substring(0,3)).append("****").append(phoneNumber.substring(phoneNumber.length()-4,phoneNumber.length())).toString();
        }else return "";
    }
}
