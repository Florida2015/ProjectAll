package com.hxxc.user.app.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hxxc.user.app.BuildConfig;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.listener.HttpLoggingInterceptor;
import com.hxxc.user.app.utils.SPUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by chenqun on 2016/6/21.
 */
public class HttpRequest {

    //生产环境
//    public static String baseUrl = "https://lc.huaxiafinance.com/caifu-web-open-platform/";
//    public static String indexBaseUrl = "https://wap.huaxiafinance.com/";
    //开发环境
//    public static String baseUrl = "https://lccftest.huaxiafinance.com/caifu-web-open-platform/";
//    public static String indexBaseUrl = "https://wapcftest.huaxiafinance.com/";

    //uat环境
//    public static String baseUrl = "https://lcuatn.huaxiafinance.com/caifu-web-open-platform/";
//    public static String indexBaseUrl = "https://lcuatwap.huaxiafinance.com/";

    //开发环境
//    public static String baseUrl = "http://192.168.11.127:8080/caifu-web-open-platform/";
//    public static String indexBaseUrl = "http://192.168.11.127:8080/";

    //sit环境
    public static String baseUrl = "https://lcsitn.huaxiafinance.com/caifu-web-open-platform/";
    public static String indexBaseUrl = "https://lcsitwapn.huaxiafinance.com/";
    ////

    //app常见问题
    public static String problem = indexBaseUrl + "#/product/problem";
    public static String problem2 = indexBaseUrl + "app/productProblemApp.html";
    //风险备用金
    public static String riskFund = indexBaseUrl + "#/riskFund";
    //精选文章，平台公告
    public static String httpActionCenter = indexBaseUrl + "#/find/message";
    //获取门店详情地图
    public static final String getDepartmentMap = baseUrl + "department/getDepartmentMap";

    //风险测评
    public static String riskTest = "#/setting/riskTest";

    //积分商城
    public static String UrlMail = indexBaseUrl + "#/shopping";//url


    //分享出去的链接
    public static String shareLocation;
    //協議地址
    public static String xieYi;
    //首页wap地址-会员中心
    public static String indexurl1;
    //首页wap地址-邀请好友
    public static String indexurl2 = "activity/shareIndex.html";
    //首页wap地址-安全保障
    public static String indexurl3 = "#/safety/control?reqFrom=mobile";
    //注册服务协议
    public static String Agreement_register = "#/user/registagreement";

    //获取积分商城url
    public static final String httpMail = "member/getMallWapUrl";//

    //签到规则
    public static final String httpUrlSign = "#/signRulesApp";//

    //积分规则
    public static final String httpUrlInteger = "#/integrationApp";

    //有奖任务  积分规则
    public static final String httpUrlMsgType = "#/menu/msg?menutype=3";

    //获取协议列表
    public static final String httpGetAgreementTemplateList = "usershare/getAgreementTemplateList";
    //取消风险测评
    public static final String closeRiskTest = "usershare/closeQuiz";
    //发送验证码
    public static final String httpGetCode = "usershare/getAppSmsValidCode";
    //获取用户理财师
    public static final String httpGetUserPlanner = "usershare/getUserFpByKey";
    //获取用户信息
    public static final String httpGetUserInfo = "usershare/getUserInfo";
    //更新设备号
    public static final String httpUpdateDeviceToken = "usershare/setUserDeviceToken";
    //修改密码
    public static final String httpUpdateUserPassword = "usershare/updateUserPassword";
    //退出登录
    public static final String HttpExitLogin = "usershare/exitLogin";
    //更新用户头像
    public static final String httpUpdateUserIcon = "usershare/uploadUserHeadIcon";
    //获取绑定邮箱验证码
    public static final String httpSendCodeForEmail = "usershare/getEmailValidCode";
    //获取聊天token
    public static final String httpChatToken = "usershare/getUserChartTokenInfo";
    //开通手势密码
    public static final String httpOpenGesturePwd = "usershare/openGesturePwd";
    //关闭手势密码
    public static final String httpCloseGesturePwd = "usershare/closeGesturePwd";

    //获取首页产品列表
    public static final String httpGetAppIndexProductList = "product/getAppIndexProductList";
    //获取产品列表
    public static final String httpGetProductList = "product/getProductList";
    //获取产品详情
    public static final String httpGetProductDetail = "product/getProductDefaultById";
    //获取产品投资记录
    public static final String httpGetInvestHistory = "product/getInvestmentHistoryByPid";

    //根据类型获取新闻
    public static final String httpSelectNewsList = "content/selectNewsList";
    //查询主界面显示bar
    public static final String httpSelectIndexAdsList = "content/selectIndexAdsList";
    //顶
    public static final String httpDoThumbs1 = "content/topNews";
    //踩
    public static final String httpDoThumbs2 = "content/stepOnNews";
    //APP升级更新
    public static final String httpAppUpdateInfo = "content/tranLast";


    //获取省市列表
    public static final String httpGetUserAreaList = "dict/getUserAreaList";
    //获取字典类型
    public static final String httpGetEnumDictList = "dict/getEnumDictList";
    //获取字典类型
    public static final String httpGetDictList = "dict/getDictList";
    //获取省市列表
    public static final String httpGetCityList = "dict/getFpCityList";
    //获取银行配置列表
    public static final String httpGetBankConfigList = "dict/getBankConfigList";


    //更新用户信息
    public static final String httpUpdateUserInfo = "gruser/updateGrUserInfo";
    //登录
    public static final String httpLogin = "usershare/userLogin";
    //注册
    public static final String httpRegist = "gruser/grUserRegist";
    //注册下一步
    public static final String httpRegistNext = "gruser/grUserRegistOneNext";
    //实名认证
    public static final String httpAuth = "gruser/grUserIdentityAuth";
    //绑定邮箱
    public static final String httpBindEmail = "gruser/grUserBindEmail";
    //找回密码下一步
    public static final String httpFindPassNext = "gruser/grFindPwdOneNext";
    //找回密码
    public static final String httpFindPass = "gruser/grUserFindPassword";
    //个人用户修改账号
    public static final String httpUpdateAccout = "gruser/grUpdateAccount";
    //个人用户修改账号的下一步按钮
    public static final String httpUpdateAccounOnNext = "gruser/grUpdateAccountOneNext";

    //门店列表
    public static final String httpGetDepartmentList = "department/getDepartmentList";


    //根据任务类别查询会员任务信息
    public static final String httpGetMemberSingTask = "member/getMemberSingleTask";
    //通过uid查询会员特权信息
    public static final String httpFindMemberPrivilege = "member/findMemberPrivilege";
    //我 的 根据token获取会员基本信息
    public static final String httpGetMemberDetail = "member/getMemberByToken";
    //根据token获取会员基本信息
    public static final String httpLoadMemberByToken = "member/loadMemberByToken";

    //创建订单
    public static final String httpOrderSurplusTime = "order/queryOrderSurplusTime";
    //创建订单
    public static final String httpCreateOrder = "order/createOrder";
    //支付
    public static final String httpDoPay = "order/doApiPay";
    //账户余额充足的情况下支付
    public static final String httpDoBalancePay = "order/doBalancePay";
    //发送宝付支付验证码
    public static final String httpSendCodeForPay = "order/sendApiSMS";
    //发送余额支付验证码
    public static final String httpSendCodeForBalancePay = "order/sendBalancePaySMS";


    //活动中心
    public static final String httpGetActionCenter = "activity/findActivityRecordList";

    //我的账户信息
    public static final String httpGetMyAccountInfo = "account/myAccountInfo";
    //查询用户子账户列表
    public static final String httpQuerySubAccountList = "account/querySubAccountList";
    //发送绑卡验证码
    public static final String httpSendCodeForBindcard = "account/sendBindCardSMS";
    //绑卡
    public static final String httpBindCard = "account/bingCard";

    //待回款收益列表
    public static final String httpGetReturnPaymentList = "account/getReturnPaymentList";
    //待回款本金列表
    public static final String httpGetPrincipalPaymentList = "account/getPrincipalPaymentList";
    //用户回款记录
    public static final String httpGetPaymentRecords = "account/getPaymentRecords";

    //获取指定月份的回款收益列表
    public static final String httpGetBackPaymentList = "account/getBackPaymentList";

    //获取红包列表
    public static final String httpGetRedPackageList = "userReward/findUserRewardByType";
    //获取支付可用红包
    public static final String httpFindCanPayUserReward = "userReward/findCanPayUserReward";

    //意见反馈
    public static final String httpFeedback = "helpCenter/addFeedBack";
    //获取常见问题列表
    public static final String httpGetFaqList = "helpCenter/getFaqList";

    //提现
    public static final String httpDoWithDrawals = "account/doWithdrawals";
    //查询用户订单
    public static final String httpOrderListByUid = "order/getOrderListByUid";

    //获取续投产品列表,没有分页
    public static final String httpGetXuTouList = "product/getXuTouProductList";
    //分页查询会员交易记录信息
    public static final String httpQueryMemberRecordList = "member/queryMemberRecordListPage";
    //查询订单详情
    public static final String httpGetOrderDetail = "order/getOrderDefaultByOrderNo";
    //获取用户交易记录
    public static final String httpGetUserTradeList = "transaction/getUserTransactionFlowing";
    //交易记录详情
    public static final String httpGetTransactionFlowing = "transaction/getTransactionFlowingDefault";

    //删除用户通知
    public static final String httpDelUserMessage = "usermsg/delUserMessageByIdAndUid";
    //获取通知提醒
    public static final String httpGetNotifiAlert = "usermsg/getNotifiAlert";
    //获取通知列表
    public static final String httpGetUserMessageList = "usermsg/getUserMessageList";
    //读取用户通知
    public static final String httpReadUserMessage = "usermsg/readUserMessage";
    //读取所有用户通知
    public static final String httpReadAllUserMessage = "usermsg/resdAllUserMsg";
    //根据uid获取用户动态消息列表(包含已读的)-
    public static final String httpGetUserdynamicList = "usermsg/getUserDynamicBizInfoList";
    //根据dybId和uid忽略动态消息
    public static final String httpIgnoewUserDynamic = "usermsg/ignoreUserDynamicBizInfo";
    //邀请好友  获取用户邀请详细
    public static final String httpGetUsrInviteCount = "usershare/getUserInviteDetail";
    //邀请好友  查询当前用户的邀请记录中的奖励记录
    public static final String httpFindInvitedCountMoney = "userReward/findInvitedCountMonery";
    //邀请好友  获取用户邀请列表
    public static final String httpGetUserInvitedList = "usershare/getUserInvitedRelationList";
    //订单详情 订单续投
    public static final String httpOrderContinued = "order/orderContinued";
    //我银行卡 设置为默认卡
    public static final String httpAccountDefault = "account/setDefault";
    //我 根据dybId和uid读取动态消息-(需要登陆)
    public static final String httpReadUserBizinfo = "usermsg/readUserDynamicBizInfo";
    //我的基金资产
    public static final String httpFundMoney = "fund/myFundMoney";
    //返回基金回调地址
    public static final String httpReturnBackUrl = "fund/returnBackUrl";
    //获取账单路劲、最新期数
    public static final String httpContractBillNew = "contact/getContractBillNew";
    //根读取账单
    public static final String httpContractFile = "contact/getContractFile";
    //获取分享列表
    public static final String httpSelectShareByType = "content/selectShareByType";
    //获取指定月份的回款收益列表
    public static final String httpAccountBackPayment = "account/getBackPayment";

    //获取指定月份的日历
    public static final String httpAccountCalenderList = "account/getReturnPaymentCalendar";


    private final ApiService service;
    private final ApiService service2;
    public static HttpRequest rest;

    public static HttpRequest getInstance() {
        if (null == rest) {
            rest = new HttpRequest();
        }
        return rest;
    }

    public ApiService getApiService() {
        return service;
    }

    public ApiService getApiService2() {
        return service2;
    }

    public HttpRequest() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        FormBody responseBody = new FormBody.Builder()
                                .add("token", SPUtils.geTinstance().getToken())
                                .add("channel", Constants.TypeChannel)
                                .build();
                        Request request = chain.request().newBuilder()
//                                .addHeader("token", Api.token)
//                                .addHeader("channel", Constants.TypeChannel)
                                .post(responseBody)
                                .build();
                        //设置请求头
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        OkHttpClient clientNoInterceptor = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder()
                //序列化对象成json时序列化为空的键。没有这句就不会序列化为空的键
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        Retrofit retrofitNoInterceptor = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))

//                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(clientNoInterceptor)
                .build();

        service = retrofit.create(ApiService.class);
        service2 = retrofitNoInterceptor.create(ApiService.class);
    }

    //request中文件转换器
   /* static class FileRequestBodyConverterFactory extends Converter.Factory {
        @Override
        public Converter<File, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            return new FileRequestBodyConverter();
        }
    }

    static class FileRequestBodyConverter implements Converter<File, RequestBody> {

        @Override
        public RequestBody convert(File file) throws IOException {
            return RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        }
    }*/
    //通过addConverterFactory(new FileRequestBodyConverterFactory())添加次转化器
    //    ********************************************************************************************************************************

  /*  static class Result {
        int code;//等价于 err
        String body;//等价于 content
        String msg;//等价于 message
    }

    static class RawResult {
        int err;
        String content;
        String message;
    }
    //response中Json转换
    static class ArbitraryResponseBodyConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return super.responseBodyConverter(type, annotations, retrofit);
        }
    }

    static class ArbitraryResponseBodyConverter implements Converter<ResponseBody, Result> {

        @Override
        public Result convert(ResponseBody value) throws IOException {
            RawResult rawResult = new Gson().fromJson(value.string(), RawResult.class);
            Result result = new Result();
            result.body = rawResult.content;
            result.code = rawResult.err;
            result.msg = rawResult.message;
            return result;
        }
    }*/

}
