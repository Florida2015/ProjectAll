package com.hxxc.user.app.rest;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.bean.Account;
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
import com.hxxc.user.app.bean.PaymentRecordsBean;
import com.hxxc.user.app.bean.Product;
import com.hxxc.user.app.bean.ProductInfo;
import com.hxxc.user.app.bean.RedPackagePayBean;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.data.bean.ShareByTypeBean;
import com.hxxc.user.app.rest.rx.BaseResponseFunc;
import com.hxxc.user.app.rest.rx.ExceptionSubscriber;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.RestUtils;
import com.hxxc.user.app.utils.SPUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenqun on 2016/7/7.
 */
public class ApiManager {
    private static ApiManager apiManager;
    private final ApiService apiService;
    private final ApiService apiService2;

    public ApiManager() {
        apiService = HttpRequest.getInstance().getApiService();
        apiService2 = HttpRequest.getInstance().getApiService2();
    }

    public static ApiManager getInstance() {
        if (null == apiManager) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    // 2.登录
    public Subscription login(String mobile, String pwd, SimpleCallback<UserInfo> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("account", mobile);
        map.put("password", pwd);
        Subscription s = apiService2.login(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<UserInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<UserInfo>(callback));
        return s;
    }

    // 3.获取用户理财师
    public Subscription getFinancialPlannerByFid(String uid, SimpleCallback<FinancialPlanner> callback) {
        Subscription s = apiService2.getFinancialPlannerByFid(RestUtils.setParamss(null))
                .flatMap(new BaseResponseFunc<FinancialPlanner>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<FinancialPlanner>(callback));
        return s;
    }

    // 4.获取用户信息
    public Subscription getUserInfoByUid(String uid, SimpleCallback<UserInfo> callback) {
        Subscription s = apiService2.getUserInfoByUid(RestUtils.setParamss(null))
                .flatMap(new BaseResponseFunc<UserInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<UserInfo>(callback));
        return s;
    }

    // 5.获取app更新信息
    public Subscription updateApp(String versionCode, String versionName, SimpleCallback<AppUpdateBean> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("versionCode", versionCode);
        map.put("versionName", versionName);
        Subscription s = apiService2.updateApp(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<AppUpdateBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<AppUpdateBean>(callback));
        return s;
    }

    // 6.更新设备号
    public Subscription updateDevice(String uid, String deviceToken, SimpleCallback<String> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("deviceToken", deviceToken);
        Subscription s = apiService2.updateDevice(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    // 7.查询主界面显示bar
    public Subscription getIndexAdsList(String type, String platform,String page, String row,SimpleCallback<List<IndexAds>> callback) {
        Subscription s = apiService.getIndexAdsList2(type,platform,page,row)
                .flatMap(new BaseResponseFunc<List<IndexAds>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<IndexAds>>(callback));
        return s;
    }

    // 获取产品列表
    public Subscription getProductList(String type, String page, String row, SimpleCallback<List<Product>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("rows", row);
        Subscription s = apiService2.getProductList(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<List<Product>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<Product>>(callback));
        return s;
    }

    // 获取产品列表2
    public Subscription getProductList2(String type, String page, String row, SimpleCallback<String> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        map.put("rows", row);
        Subscription s = apiService2.getProductList2(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    // 9.获取首页产品列表
    public Subscription getIndexProductList(String uid, String page, String row, SimpleCallback<List<Product>> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", row);
        Subscription s = apiService2.getIndexProductList(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<List<Product>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<Product>>(callback));
        return s;
    }

    // 11.发送验证码
    public Subscription sendCode(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.sendCode(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    // 19.获取城市列表
    public Subscription getCityList(SimpleCallback<List<City>> callback) {
        Subscription s = apiService.getCityList(SPUtils.geTinstance().getUid())
                .flatMap(new BaseResponseFunc<List<City>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<City>>(callback));
        return s;
    }

    // 20.获取门店列表
    public Subscription getDepartmentList(String cityName, String page, String rows, SimpleCallback<List<Department>> callback) {
        Subscription s = apiService.getDepartmentList(SPUtils.geTinstance().getUid(), cityName, page, rows)
                .flatMap(new BaseResponseFunc<List<Department>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<Department>>(callback));
        return s;
    }

    // 21.修改用户信息
    public Subscription updateUserInfoByUid(Map<String, String> parmas, SimpleCallback<String> callback) {
        parmas = RestUtils.setParamss(parmas);
        Subscription s = apiService2.updateUserInfoByUid(parmas)
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }


    // 25.意见反馈
    public Subscription feedback(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.feedback(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }


    // 33.获取用户聊天token
    public Subscription getChatUserTokenInfo(String uid, SimpleCallback<String> callback) {
        Subscription s = apiService.getChatUserTokenInfo(uid)
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    // 34.实名认证
    public Subscription doAuthentication(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.doAuthentication(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    // 35.绑卡
    public Subscription bingCard(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.bingCard(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

//    /**
//     * 根 据uid用户月签到
//     * @param params
//     * @param callback
//     * @return
//     */
//    public Subscription getMemberUserSignListForMonth(Map<String, String> params, SimpleCallback<List<Map<String,SignCalBean>>> callback) {
//        params = RestUtils.setParamss(params);
//        Subscription s = apiService.getMemberUserSignListForMonth(params)
//                .flatMap(new BaseResponseFunc<List<Map<String, SignCalBean>>>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ExceptionSubscriber<List<Map<String, SignCalBean>>>(callback));
//        return s;
//    }

    /**
     * 根据类型获取新闻
     *
     * @param callback
     * @return
     */
    public Subscription selectNewsList(String type, String page, String rows, SimpleCallback<List<ContentBean>> callback) {
        Subscription s = apiService.selectNewsList(SPUtils.geTinstance().getUid(), type, page, rows)
                .flatMap(new BaseResponseFunc<List<ContentBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<ContentBean>>(callback));
        return s;
    }

    /**
     * 获取省份城市二级列表
     *
     * @param callback
     * @return
     */
    public Subscription getUserAreaList(SimpleCallback<String> callback) {
        Subscription s = apiService.getUserCityAreaList(SPUtils.geTinstance().getUid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 获取字典列表
     *
     * @param type
     * @param callback
     * @return
     */
    public Subscription getDictInfo(String type, SimpleCallback<String> callback) {
        Subscription s = apiService.getDictInfo(SPUtils.geTinstance().getUid(), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 获取产品详情
     *
     * @param id
     * @param callback
     * @return
     */
    public Subscription getProductDetail(String id, SimpleCallback<ProductInfo> callback) {
        Subscription s = apiService.getProductDetail(SPUtils.geTinstance().getUid(), id)
                .flatMap(new BaseResponseFunc<ProductInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<ProductInfo>(callback));
        return s;
    }

    //顶
    public Subscription doThumbs1(String type, SimpleCallback<String> callback) {
        Subscription s = apiService.doThumbs1(SPUtils.geTinstance().getUid(), type)
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    //踩
    public Subscription doThumbs2(String type, SimpleCallback<String> callback) {
        Subscription s = apiService.doThumbs2(SPUtils.geTinstance().getUid(), type)
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 获取产品投资记录
     *
     * @param callback
     * @return
     */
    public Subscription getInvestmentHistoryByPid(String pid, String page, String rows, SimpleCallback<List<InvestHistoryBean>> callback) {
        Subscription s = apiService.getInvestmentHistoryByPid(SPUtils.geTinstance().getUid(), pid, page, rows)
                .flatMap(new BaseResponseFunc<List<InvestHistoryBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<InvestHistoryBean>>(callback));
        return s;
    }

    /**
     * 修改密码1
     *
     * @param callback
     * @return
     */
    public Subscription updateUserPassword(String uid, String oldPwd, String newPwd, String rePwd, SimpleCallback<String> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("oldPwd", oldPwd);
        map.put("newPwd", newPwd);
        map.put("rePwd", rePwd);
        Subscription s = apiService2.updateUserPassword(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 找回密码
     *
     * @param callback
     * @return
     */
    public Subscription getFindPass(String mobile, String smsCode, String password, String repwd, SimpleCallback<String> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("smsCode", smsCode);
        map.put("password", password);
        map.put("repwd", repwd);
        Subscription s = apiService2.getFindPass(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 创建订单
     *
     * @param uid
     * @param pid
     * @param money
     * @param callback
     * @return
     */
    public Subscription createOrder(String uid, String pid, String money, SimpleCallback<OrderInfo> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        map.put("money", money);
        Subscription s = apiService2.createOrder(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<OrderInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<OrderInfo>(callback));
        return s;
    }

    /**
     * 支付
     *
     * @param transId
     * @param sms_code
     * @param callback
     * @return
     */
    public Subscription doPay(String transId, String sms_code, SimpleCallback<String> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("transId", transId);
        map.put("sms_code", sms_code);
        Subscription s = apiService2.doPay(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

//    /**
//     * 账户余额充足的情况下支付
//     * @param callback
//     * @return
//     */
//    public Subscription doBalancePay(String orderNo,String rId ,String code , SimpleCallback<String> callback) {
//        Subscription s = apiService.doBalancePay(orderNo,rId,code)
//                .flatMap(new BaseResponseFunc<String>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ExceptionSubscriber<String>(callback));
//        return s;
//    }


    /**
     * 发送宝付支付验证码
     *
     * @param callback
     * @return
     */
    public Subscription sendCodeForPay(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.sendCodeForPay(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

//    /**
//     * 发送余额支付验证码
//     * @param params
//     * @param callback
//     * @return
//     */
//    public Subscription sendCodeForBalancePay(Map<String, String> params, SimpleCallback<String> callback) {
//        params = RestUtils.setParamss(params);
//        Subscription s = apiService2.sendCodeForBalancePay(params)
//                .flatMap(new BaseResponseFunc<String>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new ExceptionSubscriber<String>(callback));
//        return s;
//    }

    /**
     * 获取绑定邮箱验证码
     *
     * @param options
     * @param callback
     * @return
     */
    public Subscription sendCodeForEmail(Map<String, String> options, SimpleCallback<String> callback) {
        Subscription s = apiService2.sendCodeForEmail(RestUtils.setParamss(options))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 上传头像
     *
     * @param file
     * @param callback
     * @return
     */
    public Subscription updateUserIcon(String uid, String token, MultipartBody.Part file, SimpleCallback<String> callback) {
        Subscription s = apiService2.updateUserIcon(uid, token, Constants.TypeChannel, file)
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 获取账户列表
     *
     * @param uid
     * @param callback
     * @return
     */
    public Subscription querySubAccountList(String uid, SimpleCallback<List<Account>> callback) {
        Subscription s = apiService2.querySubAccountList(RestUtils.setParamss(null))
                .flatMap(new BaseResponseFunc<List<Account>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<Account>>(callback));
        return s;
    }

    /**
     * 发送绑卡验证码
     *
     * @param options
     * @param callback
     * @return
     */
    public Subscription sendCodeForBindCard(Map<String, String> options, SimpleCallback<String> callback) {
        Subscription s = apiService2.sendCodeForBindCard(RestUtils.setParamss(options))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 绑邮箱
     *
     * @param callback
     * @return
     */
    public Subscription bindEmail(String uid, String code, String email, SimpleCallback<String> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("email", email);
        Subscription s = apiService2.bindEmail(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 找回密码next
     *
     * @param mobile
     * @param smsCode
     * @param callback
     * @return
     */
    public Subscription getFindPassNext(String mobile, String smsCode, SimpleCallback<String> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("smsCode", smsCode);
        Subscription s = apiService2.getFindPassNext(RestUtils.setParamss(map))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 获取可变字典列表
     *
     * @param type
     * @param callback
     * @return
     */
    public Subscription getDictList(String type, SimpleCallback<List<HelpCenterBean>> callback) {
        Subscription s = apiService.getDictList(SPUtils.geTinstance().getUid(), type)
                .flatMap(new BaseResponseFunc<List<HelpCenterBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<HelpCenterBean>>(callback));
        return s;
    }

    /**
     * 获取常见问题
     *
     * @param type
     * @param callback
     * @return
     */
    public Subscription httpGetFaqList(String type, SimpleCallback<List<HelpCenterItemBean>> callback) {
        Subscription s = apiService.httpGetFaqList(SPUtils.geTinstance().getUid(), type)
                .flatMap(new BaseResponseFunc<List<HelpCenterItemBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<HelpCenterItemBean>>(callback));
        return s;
    }

    /**
     * 获取积分商城url
     *
     * @param callback
     * @return
     */
    public Subscription getMailUrl(String requestedPage, SimpleCallback<String> callback) {
        Subscription s = apiService.getMailUrl(SPUtils.geTinstance().getUid(), requestedPage)
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    /**
     * 获取活动中心列表
     *
     * @param page
     * @param rows
     * @param callback
     * @return
     */
    public Subscription getActionCenter(String page, String rows, SimpleCallback<List<ActionCenterBean>> callback) {
        Subscription s = apiService.getActionCenter(SPUtils.geTinstance().getUid(), page, rows)
                .flatMap(new BaseResponseFunc<List<ActionCenterBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<ActionCenterBean>>(callback));
        return s;
    }

    /**
     * 保存手势密码
     *
     * @param callback
     * @return
     */
    public Subscription saveGesturePwd(String gesturePwd, SimpleCallback<Boolean> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("gesturePwd", gesturePwd);
        Subscription s = apiService2.saveGesturePwd(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<Boolean>(callback));
        return s;
    }

    /**
     * 关闭手势密码
     *
     * @param callback
     * @return
     */
    public Subscription closeGesturePwd(SimpleCallback<Boolean> callback) {
        Subscription s = apiService2.closeGesturePwd(RestUtils.setParamss(new HashMap<String, String>()))
                .flatMap(new BaseResponseFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<Boolean>(callback));
        return s;
    }

    //获取银行配置列表
    public Subscription getBankConfigList(String payChannel, SimpleCallback<List<BankInfo>> callback) {
        Subscription s = apiService.getBankConfigList(SPUtils.geTinstance().getUid(), payChannel)
                .flatMap(new BaseResponseFunc<List<BankInfo>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<BankInfo>>(callback));
        return s;
    }

    //获取通知提醒
    public Subscription getNotifiAlert(Map<String, String> params, SimpleCallback<NoticeBean> callback) {
        Subscription s = apiService2.getNotifiAlert(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<NoticeBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<NoticeBean>(callback));
        return s;
    }

    //获取通知列表
    public Subscription getUserMessageList(Map<String, String> params, SimpleCallback<List<NoticeBean.UserMessageVoBean>> callback) {
        Subscription s = apiService2.getUserMessageList(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<List<NoticeBean.UserMessageVoBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<NoticeBean.UserMessageVoBean>>(callback));
        return s;
    }

    //读取用户通知
    public Subscription readUserMessage(Map<String, String> params, SimpleCallback<Boolean> callback) {
        Subscription s = apiService2.readUserMessage(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<Boolean>(callback));
        return s;
    }

    //读取所有用户通知
    public Subscription readAllUserMessage(Map<String, String> params, SimpleCallback<Boolean> callback) {
        Subscription s = apiService2.readAllUserMessage(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<Boolean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<Boolean>(callback));
        return s;
    }

    //获取支付可用红包
    public Subscription findCanPayUserReward(Map<String, String> params, SimpleCallback<List<RedPackagePayBean>> callback) {
        Subscription s = apiService2.findCanPayUserReward(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<List<RedPackagePayBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<RedPackagePayBean>>(callback));
        return s;
    }

    //个人用户修改账号的下一步按钮
    public Subscription updateAccounOnNext(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.UpdateAccounOnNext(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    //个人用户修改账号
    public Subscription updateAccout(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.UpdateAccout(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }


    //返回基金回调地址
    public Subscription returnBackUrl(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.returnBackUrl(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    //取消风险测评
    public Subscription closeRiskTest(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.closeRiskTest(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    //获取协议列表
    public Subscription getAgreementTemplateList(Map<String, String> params, SimpleCallback<List<Agreement>> callback) {
        Subscription s = apiService2.getAgreementTemplateList(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<List<Agreement>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<Agreement>>(callback));
        return s;
    }

    //获取分享列表
    public Subscription getSelectShareByType(String type, SimpleCallback<List<ShareByTypeBean>> callback) {
        Subscription s = apiService.getSelectShareByType(type)
                .flatMap(new BaseResponseFunc<List<ShareByTypeBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<List<ShareByTypeBean>>(callback));
        return s;
    }

    //获取订单剩余时间
    public Subscription getOrderSurplusTime(Map<String, String> params, SimpleCallback<String> callback) {
        Subscription s = apiService2.getOrderSurplusTime(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<String>(callback));
        return s;
    }

    //用户回款记录
    public Subscription getPaymentRecords(Map<String, String> params, SimpleCallback<PaymentRecordsBean> callback) {
        Subscription s = apiService2.getPaymentRecords(RestUtils.setParamss(params))
                .flatMap(new BaseResponseFunc<PaymentRecordsBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ExceptionSubscriber<PaymentRecordsBean>(callback));
        return s;
    }
}
