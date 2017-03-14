package com.hxxc.huaxing.app;

import android.os.Environment;

import com.hxxc.huaxing.app.utils.SharedPreUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/9/21.
 *
 */
public class UserInfoConfig {
    public static final int ROWS = 8;
    public static final String LASTCITY = "lastcity";
    public static final String LASTPROVINCE = "lastProvince";
    public static final String Latitude = "Latitude";
    public static final String Longitude = "Longitude";

    public static String spToken ="sptoken";//token
    public static String spUid ="spuid";//uid
    public static String spOpenEaccount ="isOpenEaccount";//isOpenEaccount
    public static String spEaccountClick ="isEaccountClick";//是否点击


    public static String APK_FILE =  getDownloadFilePath_d();
    public static String getDownloadFilePath_d() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/huaxing/");//
            cacheDir.mkdir();
            File cacheDir2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/huaxing/upgrade/");//
            cacheDir2.mkdir();
            return cacheDir2.getPath();
        } else {
            File cacheDir = HXXCApplication.getInstance().getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            return cacheDir.getPath();
        }
    }

    public static final String LoginPhone = "login_phone";//登录账号
    public static final String[] datas = new String[]{"不限","1-3个月","3-6个月","6-9个月","9-12个月"};
    public static final String FlagLogin = "flag_login";//是否登录
//    *  * 请输入验证码类型，取值范围【01：手动下单成功，02：自动下单成功，03：募集完成，开始起息，
//            * 04：资金撤标/银行主动撤标，05：未募集完/银行主动流标，06：还款付息，07：提前还款，
//            * 08：债权转让，09：转让失败，10：注册，11：找回密码，12：修改密码，13：修改邮箱】
    public static final String TYPE_Register = "10";//短信验证码 注册
    public static final String TYPE_FindPass = "11";//短信验证码 找回密码

    public static final String UserInfo = "userinfo";//用户信息
    public static final String UserRegisterStatus = "userregisterstatus";//用户的信息
    public static final String ShowType_home = "home";//showType 显示类型[home(首页),product(产品),city(城市),register(注册)]
    public static final String ShowType_product = "product";
    public static final String ShowType_city = "city";
    public static final String ShowType_register = "register";

    public static final String type_Logout = "register";//退出登录

    public static final String TypeSearchHistory = "typesearchhistory";//搜索历史记录

    public static final String SEX = "sex";//性别
    public static final String MARRIAGE = "marriage";//婚姻状况
    public static final String EDUCATION = "education";//教育类型

    public static final String Birthday = "birthday";//生日
    public static final String HrAreaInfoId = "hrAreaInfoId";//户籍地
    public static final String Domicile = "domicile";//户籍地址
    public static final String Occupation = "occupation";//职业
    public static final String LiveAreaInfoId = "liveAreaInfoId";//居住地
    public static final String Address = "address";//居住地址
    public static final int TypeChannel = 2;//请输入渠道。范围【0:wap、1:ios、2:android、3:pc】

    public static final String Cache_Province = "CacheProvince";//省市缓存
    public static final String Cache_Sex = "CacheSex";//性别缓存
    public static final String Cache_Marriage = "CacheMarriage";//婚姻缓存
    public static final String Cache_Education= "CacheEducation";//教育水平缓存

    public static final int Request_user_info = 0x0003;//

    public static boolean FlagUpdata = false;///升级


    public static String UPDATE_URL = "http://download.apk8.com/down4/soft/hyzb.apk";//升级

    public static final String isFrist = "isFrist";
    public static final String PLATFORM = "app";
    public static int getOpenAcount = 0;//是否开通E账号

    public static String getRegisterCode = "0";//注册状态调转

    public static String getLogout = "0";//退出

    //errorCode 异常code
    public static final String ErrorCode_login_out = "50010";//访问受限
    //关于我们
    public static final String WebUrl_abountus =  "views/about/abountUs.html";
    //安全保障
    public static final String WebUrl_abountsafe = "views/safe/safe.html";

    //权限受限
    public static final String http_error_out = "0035";
    //APP升级
    public static final String Type_User = "Android-USER";

}
