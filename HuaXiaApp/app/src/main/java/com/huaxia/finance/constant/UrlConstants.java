package com.huaxia.finance.constant;

/**
 * 功能：url
 * Created by houwen.lai on 2015/11/25.
 */
public class UrlConstants {
//https://mtest.huaxiafinance.com/
    public static final String urlBase = "https://m.huaxiafinance.com/huaxia-front/";//公网环境
//    public static final String urlBase = "http://huaxiatest.huaxiafinance.com/huaxia-front/";//https测试url
//    public static final String urlBase = "http://192.168.11.29:8082/huaxia-front/";//开发url
//    public static final String urlBase = "http://192.168.11.39:6080/huaxia-front/";//测试url
//    public static final String urlBase = "https://mtest.huaxiafinance.com/huaxia-front/";//开发url
//public static final String urlBase = "http://192.168.8.239:8080/huaxia-front/";// wyurl
//public static final String urlBase = "http://hxtest1.huaxiafinance.com/huaxia-front/";//测试url
    public static final String urlBase_web ="https://m.huaxiafinance.com/#/";//https://m.huaxiafinance.com/#/
    // http://huaxiatest.huaxiafinance.com/#/  预生产环境
    public static final String urlBase_logo ="https://m.huaxiafinance.com/#/";
    //http://hxtest1.huaxiafinance.com
    //http://192.168.8.239:8080/#/ //w y
    //http://192.168.11.40:8089/#/   测试web
    //http://192.168.11.48:8089/#/
    //http://192.168.11.40:8089/#/

    //http://192.168.8.177:8090/#/ 开发web
    //http://192.168.11.46:18080/huaxia-front/
    //http://mtest.huaxiafinance.com/huaxia-front/  测试环境

    /**
     * 花虾2.0 App url
     * http://192.168.11.31/
     */
//    public static final String urlBase ="http://192.168.11.31/";//http://192.168.11.29:8082/huaxia-front/

    public static final String urlLogin = "account/login";//登录
//    public static final String urlValidatePhone = "account/validateAccountMobile.htm";//验证号码有限
    public static final String urlValidatePhone = "account/validatePhone";//发送验证码
    public static final String urlSendSms = "account/sendsms";//验证手机号是否注册
    public static final String urlRegister = "account/sign";//注册
    public static final String urlForget = "account/forgetPasswd";//忘记密码
    public static final String urlResetPass = "account/resetPasswd";//修改密码
    public static final String urlGetToken = "account/gettoken.htm";//token过期验证
    public static final String urlBankList = "bank/bankList";//银行卡列表
    public static final String urlUserBankList ="payment/userBankList";//用户的银行卡列表
    public static final String urlUserSetDefCard ="payment/setDefCard";//设置默认银行卡
    public static final String urlPaymentSendSms ="payment/sendSms";//购买产品验证码
    public static final String urlFindCashVoucher ="activity/findCashVoucher";//根据用户id和产品分类获取优惠券
    public static final String urlFindCashVoucherByStatus ="activity/findCashVoucherByStatus";//根据用户id和优惠券状态获取优惠券
    public static final String urlPaymentFirstPay ="payment/firstPay";//绑卡支付操作
    public static final String urlPaymentPay ="payment/pay";//已经绑过卡再支付
    public static final String urlPaymentVerfication ="payment/idVerification";//实名认证
    public static final String urlOrderGetOrders ="order/getOrders";//status 根据状态查询订单列表
    public static final String urlAccountUserInfo ="account/userInfo";//更具token获取用户信息
    public static final String urlAccountDebtPortfolio ="account/debtPortfolio/";//订单详情s债券组合
    public static final String urlAgreementOrder = "agreement/order/";//花虾金融服务协议
    public static final String urlAgreementProduct = "agreement/product/";//花虾金融服务协议
    public static final String urlProductInsurance = "product/insurance/";//产品说明页
    public static final String urlWechat = "activity/weChatShare";//根据网页地址获取微信sdk签名
    public static final String urlOrderAgrement = "order/agreement/";//协议详情
    public static final String urlOrderDebt = "order/debt/";//债券组合
    public static final String urlRegistAgrement = "regist/arggeement";//注册协议

    /**
     * 花虾2.0 App url
     */
    public static final String urlBanner ="activity/getActivityOnHome";//获取首页banner轮播活动
    public static final String urlOnHome = "product/onHome";//获取首页产品
    public static final String urlProductList = "product/list?";//获取产品列表
    public static final String urlProductDetail = "product/message/";//获取产品详情
    public static final String urlProduct = "product/";//获取产品说明页详情
    public static final String urlActivitiesList = "activity/getActivitiy";//活动列表
    public static final String urlActivity = "activity/";//活动详情

    public static final String urlOrderInfo = "order/orderInfo";//订单详情
    public static final String urlArgeementProduct = "agreement/product/";//支付金额 出借协议服务协议
    public static final String urlDisclaimer ="disclaimer";//免责声明
    public static final String urlPivacy ="privacy";//隐私说明
    public static final String urlProblem ="commonProblem";//常见问题
    public static final String urlAbout ="about/us";//关于花虾about/us
    public static final String urlNoticeList ="ui/notice";//通知列表
    public static final String urlNoticeDetail ="ui/getNoticeByNo";//通知详情
    public static final String urlUiUpdateApp ="ui/updateApp";//app 升级

    public static final String urlOrderSaveOrderRebuy = "order/saveOrderReBuy";// 开启或关闭订单续投

    public static final String banner_img_1 ="http://m.huaxiafinance.com:80/resources/images/banner.png";
    public static final String banner_img_2 ="http://m.huaxiafinance.com:80/resources/images/banner6.jpg";
    public static final String banner_img_3 ="http://m.huaxiafinance.com:80/resources/images/banner2.jpg";

}
