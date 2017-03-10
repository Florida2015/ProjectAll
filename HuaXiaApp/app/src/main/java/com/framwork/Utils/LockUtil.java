package com.framwork.Utils;

import android.util.Base64;

import java.security.MessageDigest;

/**
 * 加密 方式
 * Created by houwen.lai on 2016/1/14.
 */
public class LockUtil {


    /***
     * SHA加密 生成40位SHA码
     * @param 待加密字符串
     * @return 返回40位SHA码
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * base64加密
     * @return
     */
    public static String base64encode(String s) throws Exception{
        return new String(Base64.encode(s.getBytes(), Base64.DEFAULT));
    }
    public static String base64encode2string(String s) throws Exception{
        return Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
    }

    /**
     * base64 解密
     * @param s
     * @return
     */
    public static String base64decode(String s){
       return new String(Base64.decode(s.getBytes(),Base64.DEFAULT));
    }

    /**
     * md5 加密
     * @param input
     * @return
     */
    public static byte[] md5encode(byte[] input){
        byte[] digestedValue = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input);
            digestedValue = md.digest();
        }catch(Exception e){
            e.printStackTrace();
        }
        return digestedValue;
    }
    public static String md5encode(String input){
        byte[] digestedValue = null;
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes("UTF-8"));
            digestedValue = md.digest();
        }catch(Exception e){
            e.printStackTrace();
        }
        return digestedValue.toString();
    }

}
