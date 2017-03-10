package com.hxxc.user.app.rest;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.bean.Account;
import com.hxxc.user.app.bean.AccountInfo;
import com.hxxc.user.app.bean.ActionCenterBean;
import com.hxxc.user.app.bean.Agreement;
import com.hxxc.user.app.bean.AppUpdateBean;
import com.hxxc.user.app.bean.BankInfo;
import com.hxxc.user.app.bean.City;
import com.hxxc.user.app.bean.ContentBean;
import com.hxxc.user.app.bean.Department;
import com.hxxc.user.app.bean.FinancialPlanner;
import com.hxxc.user.app.bean.HelpCenterBean;
import com.hxxc.user.app.bean.HelpCenterItemBean;
import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.bean.InvestHistoryBean;
import com.hxxc.user.app.bean.NoticeBean;
import com.hxxc.user.app.bean.OrderInfo;
import com.hxxc.user.app.bean.PaymentBean;
import com.hxxc.user.app.bean.PaymentRecordsBean;
import com.hxxc.user.app.bean.Product;
import com.hxxc.user.app.bean.ProductInfo;
import com.hxxc.user.app.bean.RedPackagePayBean;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.data.bean.AdviserBean;
import com.hxxc.user.app.data.bean.ClanderBean;
import com.hxxc.user.app.data.bean.ContractBillNewBean;
import com.hxxc.user.app.data.bean.IntegralBean;
import com.hxxc.user.app.data.bean.IntegralRecordListBean;
import com.hxxc.user.app.data.bean.InvitaionRewardBean;
import com.hxxc.user.app.data.bean.InvitationListBean;
import com.hxxc.user.app.data.bean.InvitationRecordBean;
import com.hxxc.user.app.data.bean.MemberCenterBean;
import com.hxxc.user.app.data.bean.MemberCenterDetailBean;
import com.hxxc.user.app.data.bean.MemberCenterPrivilegeBean;
import com.hxxc.user.app.data.bean.OrderContundBean;
import com.hxxc.user.app.data.bean.OrderDetailBean;
import com.hxxc.user.app.data.bean.OrderItemBean;
import com.hxxc.user.app.data.bean.RedPackageItemBean;
import com.hxxc.user.app.data.bean.ShareByTypeBean;
import com.hxxc.user.app.data.bean.SignCalBean;
import com.hxxc.user.app.data.bean.TradeRecordBean;
import com.hxxc.user.app.data.bean.UserDynamicBean;
import com.hxxc.user.app.data.bean.WithDrawBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenqun on 2016/6/21.
 */
public interface ApiService {

    /**
     * 登录
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpLogin)
    Observable<BaseResult<UserInfo>> login(@FieldMap Map<String, String> options);

    /**
     * financialplanner/myFinancial.do
     * financialplanner/findFinancialPlannerByFid.do
     * 获取用户理财师
     *
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetUserPlanner)
    Observable<BaseResult<FinancialPlanner>> getFinancialPlannerByFid(@FieldMap Map<String, String> options);

    /**
     * 获取用户信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetUserInfo)
    Observable<BaseResult<UserInfo>> getUserInfoByUid(@FieldMap Map<String, String> options);


    /**
     * 获取app更新信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpAppUpdateInfo)
    Observable<BaseResult<AppUpdateBean>> updateApp(@FieldMap Map<String, String> options);

    /**
     * 更新设备号
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpUpdateDeviceToken)
    Observable<BaseResult<String>> updateDevice(@FieldMap Map<String, String> options);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpUpdateUserPassword)
    Observable<BaseResult<String>> updateUserPassword(@FieldMap Map<String, String> options);

    /**
     * 查询主界面显示bar
     * 展示方位[ 1、PC首页顶部 2、PC首页底部 3、PC会员中心顶部 4、app广告栏 5、app启动页
     * 6、app底部栏首页 7、app底部栏产品 8、app底部栏发现 9、app底部栏账户
     * 10、app浮动弹窗 11、WAP广告栏 12、WAP底部栏首页 13、WAP底部栏产品 14、WAP底部栏发现
     * 15、WAP底部栏账户 16、WAP浮动弹窗 17、WAP邀友广告栏 ]
     *
     * @return
     */

    @POST(HttpRequest.httpSelectIndexAdsList)
    Observable<BaseResult<List<IndexAds>>> getIndexAdsList2(@Query("type") String type,
                                                            @Query("platform") String platform,
                                                            @Query("page") String page,
                                                            @Query("rows") String rows);

    /**
     * 展示方位[ 1、PC首页顶部 2、PC首页底部 3、PC会员中心顶部 4、app广告栏 5、app启动页
     * 6、app底部栏首页 7、app底部栏产品 8、app底部栏发现 9、app底部栏账户 10、app浮动弹窗
     * 11、WAP广告栏 12、WAP底部栏首页 13、WAP底部栏产品 14、WAP底部栏发现 15、WAP底部栏账户
     * 16、WAP浮动弹窗 17、WAP邀友广告栏 ]
     *
     * @param type
     * @param platform 所属平台【 0:pc 1:wap 2:app】
     * @return
     */
    @POST(HttpRequest.httpSelectIndexAdsList)
    Observable<BaseResult<List<IndexAds>>> getIndexAdsLists(@Query("type") String type,
                                                            @Query("platform") String platform);

    /**
     * 获取首页产品列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetAppIndexProductList)
    Observable<BaseResult<List<Product>>> getIndexProductList(@FieldMap Map<String, String> options);

    /**
     * 获取产品列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetProductList)
    Observable<BaseResult<List<Product>>> getProductList(@FieldMap Map<String, String> options);

    /**
     * 获取产品列表2
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetProductList)
    Observable<BaseResult<String>> getProductList2(@FieldMap Map<String, String> options);

    /**
     * 获取产品详情
     *
     * @param id
     * @return
     */
    @POST(HttpRequest.httpGetProductDetail)
    Observable<BaseResult<ProductInfo>> getProductDetail(@Query("uid") String uid,
                                                         @Query("id") String id);

    /**
     * 根据类型获取新闻
     *
     * @return
     */

    @POST(HttpRequest.httpSelectNewsList)
    Observable<BaseResult<List<ContentBean>>> selectNewsList(@Query("uid") String uid,
                                                             @Query("type") String type,
                                                             @Query("page") String page,
                                                             @Query("rows") String rows);


    /**
     * 发送验证码
     *
     * @param options 10：注册，11：找回密码，13：修改邮箱 27:用户修改账号
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetCode)
    Observable<BaseResult<String>> sendCode(@FieldMap Map<String, String> options);

    /**
     * 绑定邮箱
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpBindEmail)
    Observable<BaseResult<String>> bindEmail(@FieldMap Map<String, String> options);

    /**
     * 个人用户修改账号
     *
     * @param options uid
     *                upMethod 账号更新方式(1:原始账号自定更新，2:人工审核更新)
     *                oldAccount 原始手机账号(账号更新方式为1必传，否则可选)
     *                oldCode  原始手机账号验证码(账号更新方式为1必传，否则可选)
     *                newAccount  新手机账号
     *                newCode 新手机账号短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpUpdateAccout)
    Observable<BaseResult<String>> UpdateAccout(@FieldMap Map<String, String> options);

    /**
     * 个人用户修改账号的下一步按钮
     *
     * @param options uid
     *                oldAccount 原始手机账号
     *                oldCode 原始手机账号短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpUpdateAccounOnNext)
    Observable<BaseResult<String>> UpdateAccounOnNext(@FieldMap Map<String, String> options);


    /**
     * 获取城市列表
     *
     * @return
     */

    @POST(HttpRequest.httpGetCityList)
    Observable<BaseResult<List<City>>> getCityList(@Query("uid") String uid);

    /**
     * 获取门店列表
     *
     * @return
     */
    @POST(HttpRequest.httpGetDepartmentList)
    Observable<BaseResult<List<Department>>> getDepartmentList(@Query("uid") String uid,
                                                               @Query("cityName") String cityName,
                                                               @Query("page") String page,
                                                               @Query("rows") String rows);

    /**
     * 修改用户信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpUpdateUserInfo)
    Observable<BaseResult<String>> updateUserInfoByUid(@FieldMap Map<String, String> options);

    /**
     * 意见反馈
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpFeedback)
    Observable<BaseResult<String>> feedback(@FieldMap Map<String, String> options);

    /**
     * 获取用户聊天token
     *
     * @return
     */

    @POST(HttpRequest.httpChatToken)
    Observable<BaseResult<String>> getChatUserTokenInfo(@Query("uid") String uid);

    /**
     * 实名认证
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpAuth)
    Observable<BaseResult<String>> doAuthentication(@FieldMap Map<String, String> options);

    /**
     * 绑卡
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpBindCard)
    Observable<BaseResult<String>> bingCard(@FieldMap Map<String, String> options);


    /**
     * 退出登录
     *
     * @param uid
     * @return
     */
    @POST(HttpRequest.HttpExitLogin)
    Observable<String> getExitLogin(@Query("uid") String uid);


    /**
     * 注册 下一步
     *
     * @param mobile
     * @param smsCode
     * @param password
     * @param repwd
     * @param invitedCode
     * @return
     */
    @POST(HttpRequest.httpRegistNext)
    Observable<BaseResult<String>> getRegisterNext(@Query("mobile") String mobile,
                                                   @Query("smsCode") String smsCode,
                                                   @Query("password") String password,
                                                   @Query("repwd") String repwd,
                                                   @Query("invitedCode") String invitedCode);//邀请码

    /**
     * 绑定顾问 注册提交
     *
     * @param mobile
     * @param smsCode
     * @param password
     * @param repwd
     * @param fid         请选择您的财富顾问
     * @param invitedCode 请输入邀请码(可选)
     * @return
     */
    @POST(HttpRequest.httpRegist)
    Observable<BaseResult<UserInfo>> getRegister(@Query("mobile") String mobile,
                                                 @Query("smsCode") String smsCode,
                                                 @Query("password") String password,
                                                 @Query("repwd") String repwd,
                                                 @Query("fid") String fid,
                                                 @Query("invitedCode") String invitedCode);

    /**
     * 找回密码下一步
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpFindPassNext)
    Observable<BaseResult<String>> getFindPassNext(@FieldMap Map<String, String> options);

    /**
     * 找回密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpFindPass)
    Observable<BaseResult<String>> getFindPass(@FieldMap Map<String, String> options);

    /**
     * 注册时用的获取的财富顾问列表
     *
     * @param showType  显示类型[home(首页),product(产品),city(城市),register(注册)]
     * @param cityName
     * @param searchKey
     * @return
     */
    @POST("financialPlanner/selectFpList")
    Observable<BaseResult<List<AdviserBean>>> getFpList(@Query("showType") String showType,
                                                        @Query("cityName") String cityName,
                                                        @Query("searchKey") String searchKey,
                                                        @Query("page") int page,
                                                        @Query("rows") int rows);

    /**
     * 注册时用的获取随机的财富顾问列表
     *
     * @param showType  显示类型[home(首页),product(产品),city(城市),register(注册)]
     * @param cityName
     * @param searchKey
     * @return
     */
    @POST("financialPlanner/selectRmFpList")
    Observable<BaseResult<List<AdviserBean>>> getFpListRandom(@Query("showType") String showType,
                                                              @Query("cityName") String cityName,
                                                              @Query("searchKey") String searchKey);

    /**
     * 获取用户地区列表(省份/城市的二级菜单)
     * BaseBean<List<Province>>
     *
     * @return
     */
    @POST(HttpRequest.httpGetUserAreaList)
    Observable<String> getUserCityAreaList(@Query("uid") String uid);

    /**
     * 红包列表
     *
     * @param uid
     * @param status 红包类型状态[0:不可用 、1:可用 、2：已过期]
     * @param page
     * @param rows
     * @return
     */
    @POST("userReward/findUserRewardByType")
    Observable<BaseResult<List<RedPackageItemBean>>> getRedPackageList(@Query("uid") String uid,
                                                                       @Query("status") String status,
                                                                       @Query("page") int page,
                                                                       @Query("rows") int rows);

    /**
     * 获取当期月份
     *
     * @return
     */
    @POST("member/getNowMonth")
    Observable<BaseResult<String>> getMemberNowMonth(@Query("uid") String uid);

    /**
     * POST  根据uid用户月签到
     * token
     * uid
     * channel
     *
     * @return
     */
    @POST("member/getUserSignListForMonth")
    Observable<BaseResult<List<Map<String, SignCalBean>>>> getMemberUserSignListForMonth(@Query("uid") String uid);

    /**
     * POST /member/userSign 用户签到
     *
     * @return
     */
    @POST("member/userSign")
    Observable<BaseResult<SignCalBean>> getMemberUserSignIn(@Query("uid") String uid);

    /**
     * 获取字典类型 类型(sex:性别、education:教育水平、marriage:婚姻状态)
     *
     * @return
     */
    @POST(HttpRequest.httpGetEnumDictList)
    Observable<String> getDictInfo(@Query("uid") String uid,
                                   @Query("type") String type);

    /**
     * 获取字典类型 类型(faq_hy:会员帮助中心常见问题、faq_help:用户帮助中心常见问题、faq_cj:出借常见问题、faq_red:红包常见为题、faq_tx:提现常见问题)
     *
     * @return
     */
    @POST(HttpRequest.httpGetDictList)
    Observable<BaseResult<List<HelpCenterBean>>> getDictList(
            @Query("uid") String uid,
            @Query("type") String type);

    //顶
    @POST(HttpRequest.httpDoThumbs1)
    Observable<BaseResult<String>> doThumbs1(@Query("uid") String uid,
                                             @Query("id") String type);

    //踩
    @POST(HttpRequest.httpDoThumbs2)
    Observable<BaseResult<String>> doThumbs2(@Query("uid") String uid,
                                             @Query("id") String type);

    /**
     * 获取产品投资记录
     *
     * @param pid
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpRequest.httpGetInvestHistory)
    Observable<BaseResult<List<InvestHistoryBean>>> getInvestmentHistoryByPid(@Query("uid") String uid,
                                                                              @Query("pid") String pid,
                                                                              @Query("page") String page,
                                                                              @Query("rows") String rows);

    /**
     * 根据任务类别查询会员任务信息
     * uid
     * taskType 0 成长任务 1 每日任务
     * token
     * channel
     *
     * @param
     * @return
     */
    @POST(HttpRequest.httpGetMemberSingTask)
    Observable<BaseResult<List<MemberCenterBean>>> getGetMemberSingTask(@Query("uid") String uid,
                                                                        @Query("taskType") Integer taskType);

    /**
     * 通过uid查询会员特权信息
     *
     * @param uid
     * @return
     */
    @POST(HttpRequest.httpFindMemberPrivilege)
    Observable<BaseResult<List<MemberCenterPrivilegeBean>>> getFindMemberPrivilege(@Query("uid") String uid);

    /**
     * 根据token获取会员基本信息
     *
     * @return
     */
    @POST(HttpRequest.httpGetMemberDetail)
    Observable<BaseResult<MemberCenterDetailBean>> getGetMemberDetail(@Query("uid") String uid);

    /**
     * 会员中心 获取会员信息
     *
     * @param
     * @return
     */
    @POST(HttpRequest.httpLoadMemberByToken)
    Observable<BaseResult<MemberCenterDetailBean>> getLoadMemberByToken(@Query("uid") String uid);

    /**
     * 创建订单
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpCreateOrder)
    Observable<BaseResult<OrderInfo>> createOrder(@FieldMap Map<String, String> options);


    /**
     * 支付
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpDoPay)
    Observable<BaseResult<String>> doPay(@FieldMap Map<String, String> options);
//    /**
//     * 账户余额充足的情况下支付
//     * @return
//     */
//
//    @POST(HttpRequest.httpDoBalancePay)
//    Observable<BaseResult<String>> doBalancePay(@Query("orderNo") String orderNo,
//                                                @Query("rId") String rId,
//                                                @Query("code") String code);

    /**
     * 活动中心
     *
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpRequest.httpGetActionCenter)
    Observable<BaseResult<List<ActionCenterBean>>> getActionCenter(@Query("uid") String uid,
                                                                   @Query("page") String page,
                                                                   @Query("rows") String rows);

    /**
     * 我的账户信息
     *
     * @param uid
     * @return
     */
    @POST(HttpRequest.httpGetMyAccountInfo)
    Observable<BaseResult<AccountInfo>> getMyAccountInfo(@Query("uid") String uid);

    /**
     * 发送宝付支付验证码
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpSendCodeForPay)
    Observable<BaseResult<String>> sendCodeForPay(@FieldMap Map<String, String> options);

//    /**
//     * 发送余额支付验证码
//     * @param options
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(HttpRequest.httpSendCodeForBalancePay)
//    Observable<BaseResult<String>> sendCodeForBalancePay(@FieldMap Map<String, String> options);

    /**
     * 发送绑卡验证码
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpSendCodeForBindcard)
    Observable<BaseResult<String>> sendCodeForBindCard(@FieldMap Map<String, String> options);

    /**
     * 发送绑定邮箱验证码
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpSendCodeForEmail)
    Observable<BaseResult<String>> sendCodeForEmail(@FieldMap Map<String, String> options);

    /**
     * 更新用户头像
     *
     * @param file
     * @return
     */
    @Multipart
    @POST(HttpRequest.httpUpdateUserIcon)
    Observable<BaseResult<String>> updateUserIcon(@Query("uid") String uid,
                                                  @Query("token") String token,
                                                  @Query("channel") String channel,
                                                  @Part MultipartBody.Part file);

    /**
     * 查询用户子账户列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpQuerySubAccountList)
    Observable<BaseResult<List<Account>>> querySubAccountList(@FieldMap Map<String, String> options);


    /**
     * 提现
     *
     * @param uid
     * @param money
     * @return
     */
    @POST(HttpRequest.httpDoWithDrawals)
    Observable<BaseResult<WithDrawBean>> getDoWithDrawals(@Query("uid") String uid,
                                                          @Query("money") String money);


    /**
     * 获取常见问题列表
     *
     * @param type
     * @return
     */
    @POST(HttpRequest.httpGetFaqList)
    Observable<BaseResult<List<HelpCenterItemBean>>> httpGetFaqList(@Query("uid") String uid,
                                                                    @Query("type") String type);

    /**
     * 查询用户订单
     *
     * @param uid
     * @param orderStatus
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpRequest.httpOrderListByUid)
    Observable<BaseResult<List<OrderItemBean>>> getOrderListByUid(@Query("uid") String uid,
                                                                  @Query("orderStatus") String orderStatus,
                                                                  @Query("page") int page,
                                                                  @Query("rows") int rows);

    /**
     * 获取积分商城url
     *
     * @return
     */
    @POST(HttpRequest.httpMail)
    Observable<BaseResult<String>> getMailUrl(@Query("uid") String uid,
                                              @Query("requestedPage") String requestedPage);

    /**
     * 保存手势密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpOpenGesturePwd)
    Observable<BaseResult<Boolean>> saveGesturePwd(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(HttpRequest.httpCloseGesturePwd)
    Observable<BaseResult<Boolean>> closeGesturePwd(@FieldMap Map<String, String> options);

    /**
     * 获取续投产品列表,没有分页
     *
     * @return
     */
    @POST(HttpRequest.httpGetXuTouList)
    Observable<BaseResult<List<OrderContundBean>>> getGetXuTouList(@Query("type") String type);

    /**
     * 分页查询会员交易记录信息
     *
     * @param uid
     * @param type
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpRequest.httpQueryMemberRecordList)
    Observable<BaseResult<IntegralBean<List<IntegralRecordListBean>>>> getQueryMemberRecordList(@Query("uid") String uid,
                                                                                                @Query("type") String type,
                                                                                                @Query("page") int page,
                                                                                                @Query("rows") int rows);

    /**
     * 查询订单详情
     *
     * @param type
     * @return
     */
    @POST(HttpRequest.httpGetOrderDetail)
    Observable<BaseResult<OrderDetailBean>> getGetOrderDetail(@Query("uid") String uid,
                                                              @Query("orderNo") String type);

    /**
     * 获取银行配置列表 1.api,2 .wangyin  3.b2bwangyin
     *
     * @param payChannel
     * @return
     */
    @POST(HttpRequest.httpGetBankConfigList)
    Observable<BaseResult<List<BankInfo>>> getBankConfigList(@Query("uid") String uid,
                                                             @Query("payMethod") String payChannel);

    /**
     * 获取用户交易记录
     *
     * @param uid
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpRequest.httpGetUserTradeList)
    Observable<BaseResult<List<TradeRecordBean>>> getGetUserTradeList(@Query("uid") String uid,
                                                                      @Query("trade") String trade,
                                                                      @Query("page") int page,
                                                                      @Query("rows") int rows);


    /**
     * 交易记录详情
     *
     * @param id
     * @return
     */
    @POST(HttpRequest.httpGetTransactionFlowing)
    Observable<BaseResult<TradeRecordBean>> getGetTransactionFlowing(@Query("uid") String uid,
                                                                     @Query("id") String id);

    /**
     * 获取通知提醒
     * 读取所有用户通知条数
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetNotifiAlert)
    Observable<BaseResult<NoticeBean>> getNotifiAlert(@FieldMap Map<String, String> options);

    /**
     * 获取通知列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetUserMessageList)
    Observable<BaseResult<List<NoticeBean.UserMessageVoBean>>> getUserMessageList(@FieldMap Map<String, String> options);

    //读取用户通知
    @FormUrlEncoded
    @POST(HttpRequest.httpReadUserMessage)
    Observable<BaseResult<Boolean>> readUserMessage(@FieldMap Map<String, String> options);

    //读取所有用户通知
    @FormUrlEncoded
    @POST(HttpRequest.httpReadAllUserMessage)
    Observable<BaseResult<Boolean>> readAllUserMessage(@FieldMap Map<String, String> options);

    //获取支付可用红包
    @FormUrlEncoded
    @POST(HttpRequest.httpFindCanPayUserReward)
    Observable<BaseResult<List<RedPackagePayBean>>> findCanPayUserReward(@FieldMap Map<String, String> options);

    /**
     * 根据uid获取用户动态消息列表
     *
     * @param uid
     * @param page
     * @param rows
     * @return
     */
//    @FormUrlEncoded
    @POST(HttpRequest.httpGetUserdynamicList)
    Observable<BaseResult<List<UserDynamicBean>>> getGetUserdynamicList(@Query("uid") String uid,
                                                                        @Query("page") int page,
                                                                        @Query("rows") int rows);

    /**
     * 根据dybId和uid忽略动态消息
     *
     * @param uid
     * @param dybId
     * @return
     */
    @POST(HttpRequest.httpIgnoewUserDynamic)
    Observable<BaseResult<String>> getIgnoewUserDynamic(@Query("uid") String uid,
                                                        @Query("dybId") String dybId);

    /**
     * 我 根据dybId和uid读取动态消息-(需要登陆)
     *
     * @param uid
     * @param dybId
     * @return
     */
    @POST(HttpRequest.httpReadUserBizinfo)
    Observable<BaseResult<String>> getReadUserBizinfo(@Query("uid") String uid,
                                                      @Query("dybId") String dybId);

    /**
     * 邀请好友  获取用户邀请详细
     *
     * @param uid
     * @return
     */
    @POST(HttpRequest.httpGetUsrInviteCount)
    Observable<BaseResult<InvitationRecordBean>> getGetUsrInviteCount(@Query("uid") String uid);

    /**
     * 邀请好友  查询当前用户的邀请记录中的奖励记录
     *
     * @param uid
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpRequest.httpFindInvitedCountMoney)
    Observable<BaseResult<List<InvitaionRewardBean>>> getFindInvitedCountMoney(@Query("uid") String uid,
                                                                               @Query("page") int page,
                                                                               @Query("rows") int rows);

    /**
     * 邀请好友  获取用户邀请列表
     *
     * @param uid
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpRequest.httpGetUserInvitedList)
    Observable<BaseResult<List<InvitationListBean>>> getGetUserInvitedList(@Query("uid") String uid,
                                                                           @Query("page") int page,
                                                                           @Query("rows") int rows);

    /**
     * 订单详情 订单续投
     *
     * @param orderNo
     * @param status        1.续投 0.取消续投
     * @param conInvestType 续投类型【1.本金续投 2.本息续投】
     * @param pid           续投产品Id
     * @return
     */
    @POST(HttpRequest.httpOrderContinued)
    Observable<BaseResult<String>> getOrderContinued(@Query("uid") String uid,
                                                     @Query("orderNo") String orderNo,
                                                     @Query("status") String status,
                                                     @Query("conInvestType") String conInvestType,
                                                     @Query("pid") String pid);

    /**
     * 订单详情 订单不续投
     *
     * @param orderNo
     * @param status
     * @return
     */
    @POST(HttpRequest.httpOrderContinued)
    Observable<BaseResult<String>> getOrderContinuedNo(@Query("orderNo") String orderNo,
                                                       @Query("status") String status);

    /**
     * 我银行卡 设置为默认卡
     *
     * @param uid
     * @param iid
     * @return
     */
    @POST(HttpRequest.httpAccountDefault)
    Observable<BaseResult<String>> getAccountDefault(@Query("uid") String uid,
                                                     @Query("iid") String iid);


    /**
     * 我的基金资产
     *
     * @param uid
     * @return
     */
    @POST(HttpRequest.httpFundMoney)
    Observable<BaseResult<String>> getFundMoney(@Query("uid") String uid);

    /**
     * 返回基金回调地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpReturnBackUrl)
    Observable<BaseResult<String>> returnBackUrl(@FieldMap Map<String, String> options);

    /**
     * 取消风险测评
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.closeRiskTest)
    Observable<BaseResult<String>> closeRiskTest(@FieldMap Map<String, String> options);

    /**
     * 获取协议列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpRequest.httpGetAgreementTemplateList)
    Observable<BaseResult<List<Agreement>>> getAgreementTemplateList(@FieldMap Map<String, String> options);

    /**
     * 获取分享列表
     *
     * @param type type 分享类型(user_regist:用户注册、user_auth:用户实名、user_first_buy:用户首投、invite_regist:邀请注册、
     *             invite_auth:邀请认证、invite_acc_buy:邀请好友累计出借、buy:购买产品、prize:抽奖、share:通用、fp:理财师
     * @return
     */
    @POST(HttpRequest.httpSelectShareByType)
    Observable<BaseResult<List<ShareByTypeBean>>> getSelectShareByType(@Query("type") String type);

    /**
     * 获取账单路劲、最新期数
     *
     * @param uid
     * @param orderNo
     * @return
     */
    @POST(HttpRequest.httpContractBillNew)
    Observable<BaseResult<ContractBillNewBean>> getContractBillNew(@Query("uid") String uid,
                                                                   @Query("orderNo") String orderNo);

    /**
     * 根读取账单
     *
     * @param uid
     * @param reqUrl 债权返回的url
     * @return
     */
    @POST(HttpRequest.httpContractFile)
    Observable<byte[]> getContractFile(@Query("uid") String uid,
                                       @Query("reqUrl") String reqUrl);

    //获取订单剩余时间
    @FormUrlEncoded
    @POST(HttpRequest.httpOrderSurplusTime)
    Observable<BaseResult<String>> getOrderSurplusTime(@FieldMap Map<String, String> options);

    //待回款收益列表
    @POST(HttpRequest.httpGetReturnPaymentList)
    Observable<BaseResult<List<PaymentBean>>> getReturnPaymentList(@Query("uid") String uid,
                                                                   @Query("page") String page,
                                                                   @Query("rows") String rows);

    //待回款本金列表
    @POST(HttpRequest.httpGetPrincipalPaymentList)
    Observable<BaseResult<List<PaymentBean>>> getPrincipalPaymentList(@Query("uid") String uid,
                                                                      @Query("page") String page,
                                                                      @Query("rows") String rows);

    //获取指定月份的回款收益列表
    @POST(HttpRequest.httpGetBackPaymentList)
    Observable<BaseResult<List<PaymentBean>>> getBackPaymentList(@Query("uid") String uid,
                                                                      @Query("page") String page,
                                                                      @Query("rows") String rows);


    //用户回款记录
    @FormUrlEncoded
    @POST(HttpRequest.httpGetPaymentRecords)
    Observable<BaseResult<PaymentRecordsBean>> getPaymentRecords(@FieldMap Map<String, String> options);

    /**
     * 获取指定月份的回款收益列表
     * @param uid
     * @param end_time
     * @return
     */
//    @FormUrlEncoded
    @POST(HttpRequest.httpAccountBackPayment)
    Observable<BaseResult<PaymentBean>> getAccountBackPaymentList(@Query("uid") String uid,
                                                                        @Query("end_time") String end_time);

    /**
     * 获取指定月份的日历
     * @param uid
     * @param end_time
     * @return
     */
//    @FormUrlEncoded
    @POST(HttpRequest.httpAccountCalenderList)
    Observable<BaseResult<List<ClanderBean>>> getAccountCalenderList(@Query("uid") String uid,
                                                                     @Query("end_time") String end_time);

}
