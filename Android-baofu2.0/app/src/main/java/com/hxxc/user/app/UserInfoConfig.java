package com.hxxc.user.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by houwen.lai on 2016/10/25.
 */

public class UserInfoConfig {

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
    public static String getDownloadFilePath() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/huaxing/");//
            cacheDir.mkdir();
            return cacheDir.getPath();
        } else {
            File cacheDir = HXXCApplication.getInstance().getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            return cacheDir.getPath();
        }
    }

    public static String spToken ="sptoken";//token
    public static String spUid ="spuid";//uid

    public static final int ROWS = 8;
    public static final String LASTCITY = "lastcity";
    public static final String LASTPROVINCE = "lastProvince";
    public static final String Latitude = "Latitude";
    public static final String Longitude = "Longitude";

    public static final String LoginPhone = "login_phone";//登录账号
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

    public static final String TypeSearchHistory = "typesearchhistory";//搜索历史记录

    public static final String TypeChannel = "android";//请输入渠道。范围【0:wap、1:ios、2:android、3:pc】


    public static int[] tabs = new int[]{R.mipmap.icon_sign_in,R.mipmap.icon_red_envelope,R.mipmap.icon_bill,R.mipmap.icon_order,
            R.mipmap.icon_account_security,R.mipmap.icon_friends,R.mipmap.icon_bank_card,R.mipmap.icon_consultant};

    public static String[] tabsTitle = new String[]{"签到","红包","账单","订单","账户安全","邀友","银行卡","我的顾问"};//

    //我的 icon
    public static int[] tabsList = new int[]{R.drawable.ico_prompt,R.drawable.ico_name,R.drawable.ico_my_red,R.drawable.ico_product};

    public static boolean FlagUpdata = false;///升级

    public static String UPDATE_URL = "http://download.apk8.com/down4/soft/hyzb.apk";//升级

    public static String URL_fund = "http://onefund.zzwealth.cn/";//基金

    //type 分享类型(user_regist:用户注册、user_auth:用户实名、user_first_buy:用户首投、invite_regist:邀请注册、
      //       invite_auth:邀请认证、invite_acc_buy:邀请好友累计出借、buy:购买产品、prize:抽奖、share:通用、fp:理财师
    public static String ShareTpye_user_regist = "user_regist";
    public static String ShareTpye_user_auth = "user_auth";
    public static String ShareTpye_user_first_buy = "user_first_buy";
    public static String ShareTpye_invite_regist = "invite_regist";
    public static String ShareTpye_invite_auth = "invite_auth";
    public static String ShareTpye_invite_acc_buy = "invite_acc_buy";
    public static String ShareTpye_buy = "buy";
    public static String ShareTpye_prize = "prize";
    public static String ShareTpye_share = "share";
    public static String ShareTpye_fp = "fp";

    public static String Contract = "合同";
    public static String Creditor = "债权";
    public static String text_cridt = "creditor";

    /**
     * 分享
     * 微信
     */
    public static final String APP_ID_weixin = "wx8d2bdc995e638769";//微信
    public static String APP_secret_wx = "af024a50df0216731c4c1fc59b683af6";
    //微博
    public static final String APP_KEY_sina = "2280410056";//测试
    //qq分享
    public static final String  APP_ID_qq = "1105151719";
    public static final String qq_appKey = "UnQXEUB4MgdA5hrV";
    public static String pathIcon_qq = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";//file:///android_asset/login_logo.png
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     *
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.sina.com";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     *
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     *
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     *
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
