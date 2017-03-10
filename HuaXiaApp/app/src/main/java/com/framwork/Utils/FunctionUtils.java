package com.framwork.Utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by houwne.lai on 2016/2/5.
 */
public class FunctionUtils {

    public static String hideCertNo(String certNo){
        if (TextUtils.isEmpty(certNo))return null;
        if (certNo.length()>=16){
            return certNo.substring(0,4)+" xxxx xxxx xxxx "+certNo.substring(certNo.length()-4,certNo.length());
        }else return null;
    }

    /**
     * 判断数字字母
     * @param str
     * @return
     */
    public static boolean isNumerLetter(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Pattern pattern_a = Pattern.compile("[a-zA-Z]*");
        Matcher isNum = pattern.matcher(str);
        Matcher isNum_a = pattern_a.matcher(str);
        if(!isNum.matches()&&!isNum_a.matches()){
            return true;
        }
        return false;
    }


}
