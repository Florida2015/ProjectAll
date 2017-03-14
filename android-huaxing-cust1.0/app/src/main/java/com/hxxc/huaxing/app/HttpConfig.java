package com.hxxc.huaxing.app;

/**
 * Created by Administrator on 2016/9/21.
 * 各接口API
 */
public class HttpConfig {

    //    public static final String BASE_URL =  "https://caihsopen.huaxiafinance.com/caihs-web-open-platform/";
    //生产环境
//    public static final String BASE_URL = "https://lc.huaxiafinance.com/bespeakServices/";//
    //SIT环境
//    public static final String BASE_URL = "https://lcsitn.huaxiafinance.com/caihs-web-open-platform/";
    //UAT环境
    public static final String BASE_URL = "https://lcuatn.huaxiafinance.com/caihs-web-open-platform/";

//    public static final String BASE_URL = "http://192.168.11.144:8080/caihs-web-open-platform/";

//    public static final String BASE_URL = "http://192.168.11.93:8080/caifu-web-open-platform-bank/";
    //吴一磊
//    public static final String BASE_URL = "http://192.168.9.132:8080/caifu-web-open-platform-bank/";
//罗震
//    public static final String BASE_URL =  "http://192.168.12.253:8080/caihs-web-open-platform/";

    //张兵
//    public static final String BASE_URL = "http://192.168.12.141:8081/caihs-web-open-platform/";
    //zhou hai
//    public static final String BASE_URL = "http://192.168.11.144:8080/caihs-web-open-platform/";


    //关于我们 安全保障
    public static final String Pic_URL = "https://chssitwap.huaxiafinance.com/";

    //192.168.9.132:8080
    //http://192.168.11.93:8080/caifu-web-open-platform-bank/
    //登录
    public static final String HttpLOGIN = "user/pub/userLogin";
    //退出登录
    public static final String HttpExitLogin = "user/pri/exitLogin";
    //发送图形验证码
    public static final String HttpImgCode = "user/pub/getImgValidCode";

    //注册 发送验证码
    public static final String HttpSendCode = "user/pub/getSmsValidCodeApp";//user/pub/getSmsValidCode
    //注册下一步
    public static final String HttpRegisterNext = "user/pub/registOneNext";
    //注册
    public static final String HttpRegister = "user/pub/userRegist";
    //找回密码下一步
    public static final String HttpFindPassNext = "user/pub/findPwdOneNext";
    //找回密码
    public static final String HttpFindPass = "user/pub/findPassword";

    //注册时用的获取财富顾问列表
    public static final String HttpFplist = "user/pub/selectFpList";

    //首页广告
    public static final String getIndexAdsList = "user/pub/selectIndexAdsList";

    //财富列表
    public static final String getProductList = "product/getProductList";

    //首页产品列表
    public static final String getIndexProductList = "product/getIndexProductList";


    //根据pid获取产品详情
    public static final String getProductDefaultById = "product/getProductDefaultById";

    //POST / 设置移动用户设备号
    public static final String HttpUserDeviceToken = "user/pri/setUserDeviceToken";

    //获取城市列表
    public static final String getAreaList = "dict/getFpCityAreaList";

    //获取用户地区列表(省份/城市的二级菜单)
    public static final String getUserCityAreaList = "dict/getUserAreaList";

    //创建订单
    public static final String createOrderInfo = "orderinfo/createOrderInfo";

    //支付
    public static final String dpPay = "orderinfo/doPay";

    //E账户充值
    public static final String HttpUserRecharge = "user/pri/rcg/userRecharge";

    //E账户提现
    public static final String HttpUserWithDrawals = "user/pri/wds/userWithdrawals";

    //根据用户ID查询用户订单列表
    public static final String HttpOrderInfoListByUid = "orderinfo/getOrderInfoListByUid";

    //根据订单号查询订单详情
    public static final String HttpOrderInfoByOrderNoDetail = "orderinfo/findOrderInfoByOrderNo";

    //债权转让申请
    public static final String HttpAppleCreditAssigment = "orderinfo/applyCreditAssigment";

    //用户开通E账户接口
    public static final String HttpUserOpenAccount = "user/pri/opact/userOpenAccount";

    //获取用户信息
    public static final String HttpGetUserInfo = "user/pri/getUserInfo";

    //根据字典类型获取字典列表
    public static final String getDictList = "dict/getDictList";

    //更新用户信息
    public static final String updateUserInfo = "user/pri/updateUserInfo";

    //获取用户资产信息
    public static final String HttpUserAssets = "user/pri/getUserAssets";

    //获取交易明细
    public static final String HttpFinancialDetails = "trade/getFinancialDetails";

    //根据产品ID查询产品的投资记录
    public static final String HttpGetInvestmentHistoryByPid = "orderinfo/getInvestmentHistoryByPid";

    //更新用户头像
    public static final String HttpUpdateUserIcon = "user/pri/uploadUserHeadIcon";

    //获取理财师信息
    public static final String HttpUserFinancialDetail = "user/pri/getUserFp";

    //E账户绑卡
    public static final String HttpUserBindCard = "user/pri/bindcard/userBindCard";

    //获取用户消息列表
    public static final String HttpSelectUserMsgList = "user/pri/selectUserMsgList";

    //删除用户指定的消息
    public static final String HttpDelUserMsg = "user/pri/delUserMsg";

    //获取图片url
    public static final String HttpReadImage = BASE_URL+"user/pub/readImage";

    //更新用户密码
    public static final String HttpUpdatePass = "user/pri/updateUserPassword";

    //读取用户指定的消息
    public static final String HttpReadUserMsg = "user/pri/readUserMsg";

    //查询自动投标期限列表
    public static final String HttpAutoInvestQuery = "userAutoInvest/queryPeriodConfig";

    //查询自动投标年化率列表
    public static final String HttpDictInvestValue = "dict/selectAutoInvestValue";

    //设置自动投标标准
    public static final String HttpAutoInvest = "userAutoInvest/setUserAutoInvest";

    //查询当前用户自动投标配置信息
    public static final String HttpSelectAutoInvest = "userAutoInvest/selectCurrentUserAutoInvestConfig";

    //根据协议类型获取协议列表协议类型:【1、安全保障 2、注册协议 3、风险揭示书 4、保密协议 5、隐私政策、6、关于我们】
    public static final String HttpPubSelectByAgrementType = "user/pub/selectByAgreementType";

    //App升级
    public static final String HttpPubUserAppUpdate = "user/pub/isUpdate";

}
