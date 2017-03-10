package com.framwork.Utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/11/23.
 */
public class StringsUtils {

    /**
     *   encoding = UTF-8 ISO-8859-1
     */
    public static String getBytetoString(byte[] bytes,String encoding){
        try {
            return new String(bytes,encoding).toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *   encoding = UTF-8 ISO-8859-1
     */
    public static String getBytetoString(byte[] bytes){
        if (bytes==null||bytes.length==0)return null;
        try {
            if (bytes.length>10)
            return new String(bytes,"UTF-8").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *   encoding = UTF-8 ISO-8859-1
     * @return
     */
    public static byte[] getStringtoBytes(String strings,String encoding){
        try {
            return strings.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断url是否有效apk
     * @param url
     * @return
     */
    public static String getUrlApkName(String url){
        if(TextUtils.isEmpty(url)){
            return "";
        }else{
            if (url.contains(".apk")){
                String temp = new String(url);
                return url.substring(temp.lastIndexOf("/")+1,temp.length());
            }
        }
        return null;
    }

    /**
     * 添加  ，
     * @param
     * @return
     */
    public static String getMoneyGap(String money){
        StringBuffer text = new StringBuffer(money);
        int index = text.toString().length();
        if (TextUtils.isEmpty(text))return money+"";
        if (0<text.length()&&text.length()<=3){
            return text.toString();
        }else if(3<text.length()&&text.length()<=6){
            return text.insert(index-3,",").toString();
        }else if(6<text.length()&&text.length()<=9){
            text.insert(index-6,",").toString();
            index = text.toString().length();
            return text.insert(index-3,",").toString();
        }else if(9<text.length()&&text.length()<=12){
            text.insert(index-9,",").toString();
            index = text.toString().length();
            text.insert(index-6,",").toString();
            index = text.toString().length();
            return text.insert(index-3,",").toString();
        }else return  money+"";
    }

    /**
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 判断字符串是否中文
     */
    public static boolean getNameIs(String name){
        String regex = "^[\\u4E00-\\u9FA5]*$";
        boolean flag = false;
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(name);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        MyLog.d("api_name="+flag);
       return flag;
    }

}
