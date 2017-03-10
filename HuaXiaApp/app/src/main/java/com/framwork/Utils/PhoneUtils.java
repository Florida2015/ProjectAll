package com.framwork.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 1 on 2015/10/16.
 */
public class PhoneUtils {

    private static PhoneUtils instance;

    public PhoneUtils() {

    }

    public static PhoneUtils getInstance() {
        if (instance == null) {
            instance = new PhoneUtils();
        }
        return instance;
    }



    /**
     * 拨打电话
     * @param context
     * @param phone
     */
    public static void showTel(Activity context, String phone) {
        Uri uri = Uri.parse("tel:" + phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }


    /**
     * 使用正则表达式进行表单验证
     *
     * @param str
     * @param regex
     * @return
     */
    public boolean check(String str, String regex) {
        boolean flag = false;
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    /**
     * 验证非空
     *
     * @return
     */
    public boolean checkNotEmputy(String notEmputy) {
        String regex = "^\\s*$";
        return check(notEmputy, regex) ? false : true;
    }

    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public boolean checkCellphone(String cellphone) {
        // String regex =
        // "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-4,5-9]))\\d{8}$";
        String regex = "^1[34578][0-9]{9}$";
        return check(cellphone, regex);
    }
    public boolean isPhone(Context mContext, String phone) {
        boolean flag = false;
        if (!checkNotEmputy(phone)) {
            ToastUtils.showShort("请输入有效的手机号码");
            return false;
        }
        if (checkCellphone(phone)) {
            flag = true;
        } else {
            flag = false;
            ToastUtils.showShort("请输入有效的手机号码");
        }
        return flag;
    }

    public String Phone2xing(String phoneNumber){
        if (checkCellphone(phoneNumber)){
            return new StringBuilder(phoneNumber.substring(0,3)).append("****").append(phoneNumber.substring(phoneNumber.length()-4,phoneNumber.length())).toString();
        }else return "";
    }

    public String Cert2xing(String certNO){
        if (!TextUtils.isEmpty(certNO)){
            if (certNO.length()==15)
            return new StringBuilder(certNO.substring(0,4)).append("********").append(certNO.substring(certNO.length()-3,certNO.length())).toString();
            else if(certNO.length()==18)return new StringBuilder(certNO.substring(0,4)).append("***********").append(certNO.substring(certNO.length()-3,certNO.length())).toString();
            else return "";
        }else return "";
    }
}
