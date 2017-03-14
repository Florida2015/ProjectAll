package com.hxxc.huaxing.app.retrofit;

import com.hxxc.huaxing.app.HttpConfig;
import com.hxxc.huaxing.app.data.bean.AdsBean;
import com.hxxc.huaxing.app.data.bean.AdviserBean;
import com.hxxc.huaxing.app.data.bean.AgreementBean;
import com.hxxc.huaxing.app.data.bean.AppUpdateBean;
import com.hxxc.huaxing.app.data.bean.AssetsBean;
import com.hxxc.huaxing.app.data.bean.AutoInvestBean;
import com.hxxc.huaxing.app.data.bean.BankItemBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.CapitalDetailBean;
import com.hxxc.huaxing.app.data.bean.City;
import com.hxxc.huaxing.app.data.bean.FinancialDetailBean;
import com.hxxc.huaxing.app.data.bean.InvestHistoryBean;
import com.hxxc.huaxing.app.data.bean.InvestQueryBean;
import com.hxxc.huaxing.app.data.bean.LendDetailItemBean;
import com.hxxc.huaxing.app.data.bean.LendItemBean;
import com.hxxc.huaxing.app.data.bean.NotifyBean;
import com.hxxc.huaxing.app.data.bean.OpenAccountBean;
import com.hxxc.huaxing.app.data.bean.ProductBean;
import com.hxxc.huaxing.app.data.bean.ProductInfo;
import com.hxxc.huaxing.app.data.bean.UpdateBean;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;

import java.math.BigDecimal;
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
 * Created by Administrator on 2016/9/20.
 * application 对apiservice 初始化
 * retrofit 网络请求
 * api 接口
 */
public interface ApiService {

    /**
     * API 登录接口
     *
     * @param mobile
     * @param password
     * @return
     */
    @POST(HttpConfig.HttpLOGIN)
    Observable<String> getUsersLogin(@Query("mobile") String mobile,
                                     @Query("password") String password);

    /**
     * 退出登录
     *
     * @param uid
     * @param token
     * @return
     */
    @POST(HttpConfig.HttpExitLogin)
    Observable<String> getExitLogin(@Query("uid") String uid,
                                    @Query("token") String token);

    /**
     * 发送验证码
     * 请输入验证码类型，取值范围【01：手动下单成功，02：自动下单成功，03：募集完成，开始起息，
     * 04：资金撤标/银行主动撤标，05：未募集完/银行主动流标，06：还款付息，07：提前还款，
     * 08：债权转让，09：转让失败，10：注册，11：找回密码，12：修改密码，13：修改邮箱】
     *
     * @param mobile
     * @param type
     * @return
     */
    @POST(HttpConfig.HttpSendCode)
    Observable<BaseBean<String>> getSendCode(@Query("mobile") String mobile,
                                             @Query("type") String type);

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
    @POST(HttpConfig.HttpRegisterNext)
    Observable<BaseBean<String>> getRegisterNext(@Query("mobile") String mobile,
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
     * @param      请输入渠道。范围【0:wap、1:ios、2:android、3:pc】
     * @return
     */
    @POST(HttpConfig.HttpRegister)
    Observable<String> getRegister(@Query("mobile") String mobile,
                                   @Query("smsCode") String smsCode,
                                   @Query("password") String password,
                                   @Query("repwd") String repwd,
                                   @Query("fid") String fid,
                                   @Query("invitedCode") String invitedCode);//邀请码

    /**
     * 找回密码下一步
     *
     * @param mobile
     * @param smsCode
     * @return
     */
    @POST(HttpConfig.HttpFindPassNext)
    Observable<BaseBean<String>> getFindPassNext(@Query("mobile") String mobile,
                                                 @Query("smsCode") String smsCode);

    /**
     * 找回密码
     *
     * @param mobile
     * @param smsCode
     * @param password
     * @param repwd
     * @return
     */
    @POST(HttpConfig.HttpFindPass)
    Observable<BaseBean<String>> getFindPass(@Query("mobile") String mobile,
                                             @Query("smsCode") String smsCode,
                                             @Query("password") String password,
                                             @Query("repwd") String repwd);

    /**
     * 财富列表
     *
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpConfig.getProductList)
    Observable<BaseBean<List<ProductBean>>> getProductList(@Query("bidType") String bidType,
                                                           @Query("status") String status,
                                                           @Query("page") String page,
                                                           @Query("rows") String rows);

    /**
     * 首页产品列表
     *
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpConfig.getIndexProductList)
    Observable<BaseBean<List<ProductBean>>> getIndexProductList(@Query("page") String page,
                                                                @Query("rows") String rows);

    /**
     * 首页广告
     * @param type
     * @param platform
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpConfig.getIndexAdsList)
    Observable<BaseBean<List<AdsBean>>> getIndexAdsList(@Query("type") String type,
                                                        @Query("platform") String platform,
                                                        @Query("page") String page,
                                                        @Query("rows") String rows);

    /**
     * 根据pid获取产品详情
     *
     * @return
     */
    @POST(HttpConfig.getProductDefaultById)
    Observable<BaseBean<ProductInfo>> getProductDefaultById(@Query("id") String pid);

    /**
     * 注册时用的获取财富顾问列表
     *
     * @param showType  显示类型[home(首页),product(产品),city(城市),register(注册)]
     * @param cityName
     * @param searchKey
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpConfig.HttpFplist)
    Observable<BaseBean<List<AdviserBean>>> getFpList(@Query("showType") String showType,
                                                      @Query("cityName") String cityName,
                                                      @Query("searchKey") String searchKey,
                                                      @Query("page") int page,
                                                      @Query("rows") int rows);

    /**
     * 设置移动用户设备号
     *
     * @param uid
     * @param deviceToken
     * @return
     */
    @POST(HttpConfig.HttpUserDeviceToken)
    Observable<BaseBean<List<AdviserBean>>> getUserDeviceToken(@Query("uid") String uid,
                                                               @Query("deviceToken") String deviceToken);

    /**
     * 获取城市列表
     *
     * @return
     */
    @POST(HttpConfig.getAreaList)
    Observable<BaseBean<List<City>>> getAreaList();

    /**
     * 获取用户地区列表(省份/城市的二级菜单)
     *BaseBean<List<Province>>
     * @return
     */
    @POST(HttpConfig.getUserCityAreaList)
    Observable<String> getUserCityAreaList();


    /**
     * 创建订单
     *
     * @param uid
     * @param pid
     * @param money
     * @return
     */
    @POST(HttpConfig.createOrderInfo)
    Observable<BaseBean<String>> createOrderInfo(@Query("uid") String uid,
                                                 @Query("pid") String pid,
                                                 @Query("money") String money);

    /**
     * 支付订单
     * @param orderNo
     * @return
     */
    @POST(HttpConfig.dpPay)
    Observable<BaseBean<OpenAccountBean>> payOrderInfo(@Query("uid") String uid,
                                                       @Query("orderNo") String orderNo);

    /**
     * E账户充值
     *
     * @param uid
     * @param aMount  交易金额
     * @return
     */
    @POST(HttpConfig.HttpUserRecharge)
    Observable<BaseBean<OpenAccountBean>> getUserRecharge(@Query("uid") String uid,
                                                 @Query("aMount") String aMount);

    /**
     * E账户提现
     *
     * @param uid
     * @param aMount  交易金额
     * @return
     */
    @POST(HttpConfig.HttpUserWithDrawals)
    Observable<BaseBean<OpenAccountBean>> getUserWithDrawals(@Query("uid") String uid,
                                                    @Query("aMount") String aMount);


    /**
     * 根据用户ID查询用户订单列表
     *
     * @param uid
     * @param status 1还款中 2投标中 3已流标 4已结清
     *                1募集中（投标中） 2 计息(还款中) 3流标 4结清
     *
    1募集中(投标中)
    2计息中(还款中)
    3.流标
    4已结清

     * @param page
     * @param rows
     */
    @POST(HttpConfig.HttpOrderInfoListByUid)
    Observable<BaseBean<List<LendItemBean>>> getOrderInfoListByUid(@Query("uid") String uid,
                                                                   @Query("status") String status,
                                                                   @Query("page") int page,
                                                                   @Query("rows") int rows);

    /**
     * 根据订单号查询订单详情
     * 出借详情
     * @param orderNo
     * @return
     */
    @POST(HttpConfig.HttpOrderInfoByOrderNoDetail)
    Observable<BaseBean<LendDetailItemBean>> getOrderInfoByOrderNoDetail(@Query("orderNo") String orderNo);

    /**
     * 债权转让申请
     *
     * @param orderNo
     * @param  【0:wap、1:ios、2:android、3:pc】
     * @return
     */
    @POST(HttpConfig.HttpAppleCreditAssigment)
    Observable<BaseBean<OpenAccountBean>> getAppleCreditAssigment(@Query("uid") String uid,
                                                                @Query("orderNo") String orderNo);

    /**
     * 用户开通E账户接口
     * @param uid
     * @param userNameNo 用户真实姓名(无编码)
     * @param identityCardNo 用户证件号(无编码)
     * @return
     */
    @POST(HttpConfig.HttpUserOpenAccount)
    Observable<BaseBean<OpenAccountBean>> getUserOpenAccount(@Query("uid") String uid,
                                                             @Query("userNameNo") String userNameNo,
                                                             @Query("identityCardNo") String identityCardNo);

    /**
     * 获取用户信息
     *
     * @param uid
     * @return
     */
    @POST(HttpConfig.HttpGetUserInfo)
    Observable<BaseBean<UserInfoBean>> getUserInfo(@Query("uid") String uid);


    /**
     * 获取用户资产信息
     * @param uid
     * @return
     */
    @POST(HttpConfig.HttpUserAssets)
    Observable<BaseBean<AssetsBean>> getUserAssets(@Query("uid") String uid);

    /**
     * 更新用户信息
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(HttpConfig.updateUserInfo)
    Observable<BaseBean<String>> updateUserInfo(@FieldMap Map<String, String> options);

    /**
     * 根据字典类型获取字典列表
     *BaseBean<List<Type>>
     * @param type
     * @return
     */
    @POST(HttpConfig.getDictList)
    Observable<String> getDictList(@Query("type") String type);

    /**
     * 获取交易明细
     * @param uid
     * @param page
     * @param size
     * @return
     */
    @POST(HttpConfig.HttpFinancialDetails)
    Observable<BaseBean<List<CapitalDetailBean>>> getFinancialDetails(@Query("uid") String uid,
                                                                      @Query("page") int page,
                                                                      @Query("size") int size);

    /**
     * 根据产品ID查询产品的投资记录
     * @param pid
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpConfig.HttpGetInvestmentHistoryByPid)
    Observable<BaseBean<List<InvestHistoryBean>>> getInvestmentHistoryByPid(@Query("pid") String pid,
                                                                            @Query("page") int page,
                                                                            @Query("rows") int rows);
    /**
     * 获取理财师信息
     * @param uid
     * @return
     */
    @POST(HttpConfig.HttpUserFinancialDetail)
    Observable<BaseBean<FinancialDetailBean>> getUserFinancialDetail(@Query("uid") String uid);

    /**
     * E账户绑卡
     * @param uid
     * @return
     */
    @POST(HttpConfig.HttpUserBindCard)
    Observable<BaseBean<OpenAccountBean>> getUserBindCard(@Query("uid") String uid);

    /**
     * 更新用户头像
     * @param uid
     * @param file
     * @return
     */
    @Multipart
    @POST(HttpConfig.HttpUpdateUserIcon)
    Observable<BaseBean<String>> updateUserIcon(@Query("uid") String uid,
                                                @Query("token") String token,
                                                @Query("channel") String channel,
                                                @Part MultipartBody.Part file);//

    /**
     * 获取用户消息列表
     * @param uid
     * @param page
     * @param rows
     * @return
     */
    @POST(HttpConfig.HttpSelectUserMsgList)
    Observable<BaseBean<List<NotifyBean>>> getSelectUserMsgList(@Query("uid") String uid,
                                                          @Query("page") int page,
                                                          @Query("rows") int rows);

    /**
     * 删除用户指定的消息
     * @param uid
     * @param msgId
     * @return
     */
    @POST(HttpConfig.HttpDelUserMsg)
    Observable<BaseBean<String>> getDelUserMsg(@Query("uid") String uid,
                                                     @Query("msgId") String msgId);

    /**
     * 更新用户密码
     * @param uid
     * @param oldPwd
     * @param repwd
     * @return
     */
    @POST(HttpConfig.HttpUpdatePass)
    Observable<BaseBean<String>> getUpdatePass(@Query("uid") String uid,
                                               @Query("oldPwd") String oldPwd,
                                               @Query("newPwd") String newPwd,
                                               @Query("rePwd") String repwd);

    /**
     * 读取用户指定的消息
     * @param uid
     * @param msgId
     * @return
     */
    @POST(HttpConfig.HttpReadUserMsg)
    Observable<BaseBean<NotifyBean>> getReadUserMsg(@Query("uid") String uid,
                                               @Query("msgId") String msgId);

    /**
     * 查询自动投标期限列表
     * @return
     */
    @POST(HttpConfig.HttpAutoInvestQuery)
    Observable<BaseBean<List<InvestQueryBean>>> getAutoInvestQuery();

    /**
     * 查询自动投标年化率列表
     * @return
     */
    @POST(HttpConfig.HttpDictInvestValue)
    Observable<BaseBean<String[]>> getDictInvestValue();

    /**
     * 设置自动投标标准
     * @param uid
     * @param isLimitInvestAmount 是否限制投标金额【1.是 0. 否】
     * @param minInvestAmount 最小单笔投资金额
     * @param maxInvestAmount 最大单笔投资金额
     * @param loanPeriod 借款期限ID
     * @param isLimitYearRate 是否限制年化收益【1.是 0. 否】
     * @param minYearRate 最低年化
     * @param maxYearRate 最高年化
     * @param isAutoInvestStatus 自动投标状态【1.是 0. 否】
     * @return
     * @return
     */
    @POST(HttpConfig.HttpAutoInvest)
    Observable<BaseBean<String>> getAutoInvest(@Query("uid") String uid,
                                               @Query("isLimitInvestAmount") String isLimitInvestAmount,
                                               @Query("minInvestAmount") String minInvestAmount,
                                               @Query("maxInvestAmount") String maxInvestAmount,
                                               @Query("loanPeriod") String loanPeriod ,
                                               @Query("isLimitYearRate") String isLimitYearRate,
                                               @Query("minYearRate") String minYearRate,
                                               @Query("maxYearRate") String maxYearRate,
                                               @Query("isAutoInvestStatus") String isAutoInvestStatus);


    /**
     * 查询当前用户自动投标配置信息
     * @param uid
     * @return
     */
    @POST(HttpConfig.HttpSelectAutoInvest)
    Observable<BaseBean<AutoInvestBean>> getSelectAutoInvest(@Query("uid") String uid);

    /**
     * 根据协议类型获取协议列表协议类型:
     * 【1、安全保障 2、注册协议 3、风险揭示书 4、保密协议 5、隐私政策、6、关于我们
     * 7、银行卡充值引导  8、E账户余额充值引导引导 9、提现图文引导 10.购买协议
     * @param agreementType
     * @return
     */
    @POST(HttpConfig.HttpPubSelectByAgrementType)
    Observable<BaseBean<List<AgreementBean>>> getPubSelectByAgrementType(@Query("agreementType") String agreementType);

    /**
     * 获取app更新信息
     * @param versionCode 6
     * @param versionName Android-USER
     * 版本名称(Android-USER:安卓用户端、Android-FINANCIAL:安卓理财师端、IOS-USER:ios用户端、IOS-FINANCIAL:ios理财师端)
     * @return
     */
    @POST(HttpConfig.HttpPubUserAppUpdate)
    Observable<BaseBean<UpdateBean>> getAppUpdate(@Query("versionCode") String versionCode,
                                                    @Query("versionName") String versionName);


//    @Headers("User-Agent: Retrofit2.0Tutorial-App")
//    @GET("/search/users")
//    Call<GitResult> getUsersNamedTom(@Query("q") String name);
//
//    @POST("/user/create")
//    Call<Item> createUser(@Body String name, @Body String email);
//
//    @PUT("/user/{id}/update")
//    Call<Item> updateUser(@Path("id") String id, @Body Item user);

//    @GET("/search/users")
//    Observable<String> getUsersNamedTom_1(@Query("q") String name);

}
