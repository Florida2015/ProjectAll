package com.huaxia.finance.constant;

import android.os.Environment;

import com.framwork.Utils.FileUtils;
import com.huaxia.finance.DMApplication;

import java.io.File;

/**
 * Created by houwen.lai on 2015/11/18.
 */
public class DMConstant {

//    public static final String UrlHuaxiaBase = "http://m.huaxiafinance.com/";//公网环境
//    public static final String UrlHuaxiaBase = "http://192.168.11.48:8089/#/";//https测试环境
    public static final String UrlHuaxiaBase = "http://192.168.11.39:8082/huaxia-front/";//测试环境
//    public static final String UrlHuaxiaBase = "http://192.168.11.29:8080/huaxia-front/";//单机测试
//    public static final String UrlHuaxiaBase = "http://qiangwei261.imwork.net:16342/huaxia-front/";
//    public static final String UrlHuaxiaBase = "http://192.168.8.221:8080/huaxia-front/";

    public static final String UrlHuaxiaDisplay = "&display=1";
    public static final String UrlHuaxia = UrlHuaxiaBase+"?full=0&display=1";//是否显示底部导航栏
    public static final String UrlHuaxia_d = UrlHuaxiaBase+"?full=0";
    public static final String UrlMangeMoney = UrlHuaxiaBase+"product/initproduct.htm?full=1&display=1";
    public static final String UrlMangeMoney_d =UrlHuaxiaBase+"product/initproduct.htm?full=1";
    public static final String UrlMine = UrlHuaxiaBase+"order/myhuaxia.htm?full=2&display=1";//登录成功的我的页面
    public static final String UrlMine_d = UrlHuaxiaBase+"order/myhuaxia.htm?full=2";//
    public static final String UrlMine_more = UrlHuaxiaBase+"more/initmoresetting.htm?full=2";//我的更多
    public static final String UrlMore =UrlHuaxiaBase+"more/initmore.htm?full=3&display=1";
    public static final String UrlMore_d =UrlHuaxiaBase+"more/initmore.htm?full=3";

    public static final String Urllogin = UrlHuaxiaBase+"login/initlogin.htm";//登录
    public static final String Urlregister =UrlHuaxiaBase+"account/initregister.htm";//注册
    public static final String Urlforget =UrlHuaxiaBase+"account/forgetPassword.htm";//忘记密码
    public static final String UrlDisclaimer =UrlHuaxiaBase+"about_disclaimer.html";//免责声明
    public static final String UrlPivacy =UrlHuaxiaBase+"about_privacy.html";//隐私说明
    public static final String UrlProblem =UrlHuaxiaBase+ "more_common_problem.html";//常见问题
    public static final String UrlAbout =UrlHuaxiaBase+ "weixin/shareActivity.htm?activityId=8996779735607201712097";//关于花虾

    public static final String UrlAgreement =UrlHuaxiaBase+"protocol/regist.pdf";
    public static final String UrlWork = "workAgreement.pdf";
    public static final String UrlMangemoney = UrlHuaxiaBase+"product/initproduct.htm";//理财频道


    public static String APK_FILE = getDownloadFilePath_d() ;//+ "/huaxia.apk"
    public static String UPDATE_URL ="";//下载地址

    public static final String APP_CACAHE_DIRNAME ="/huaxia/webcache";//网页缓存地址

    public static final String web_path = FileUtils.getSdPath() + "/huaxia/web";//保存网页
    public static final String web_path_huaxia ="huaxia.html";
    public static final String web_path_mangemoney = "mangemoney.html";
    public static final String web_path_mine = "mine.html";
    public static final String web_path_more = "more.html";


    /**
     * 获取下载路径
     *
     * @return
     */
    public static String getDownloadFilePath() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File cacheDir = new File(android.os.Environment.getExternalStorageDirectory().getPath()+ "/huaxiafinance");//
            cacheDir.mkdir();
            File cacheDir2 = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/huaxiafinance/upgrade");//
            cacheDir2.mkdir();
            return cacheDir2.getPath();
        } else {
            File cacheDir = DMApplication.getInstance().getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            return cacheDir.getPath();
        }
    }
    public static String getDownloadFilePath_d() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/huaxiafinance/");//
            cacheDir.mkdir();
            File cacheDir2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/huaxiafinance/upgrade/");//
            cacheDir2.mkdir();
            return cacheDir2.getPath();
        } else {
            File cacheDir = DMApplication.getInstance().getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();
            return cacheDir.getPath();
        }
    }

    public static boolean flagWebViewError = false;//是否加载错误的网页
    public static boolean flagHuaxia = true;//花虾
    public static boolean flagMangeMoney = true;//理财
    public static boolean flagMine = true;//我的
    public static boolean flagMore = true;//更多

    public static boolean flagCode = true;//验证码
}
