package com.huaxia.finance.constant;

/**
 * Created by houwen.lai on 2016/1/23.
 */
public class StateConstant {


    public static final String Status_success = "000";//
    public static final String Status_login = "003";//
    public static final String Status_Order_010 = "010 ";// 绑卡失败
    public static final String Status_Order_040 = "040";//处理中
    public static final String Status_Order_030 = "030";//订单处理失败短信验证码输入错误
    public static final String Status_Order_022 = "022";//购买失败
    public static final String Status_Order_30 = "30";//本期产品未开始售卖
    public static final String Status_Order_031 = "31";//本期产品已结束售卖
    public static final String Status_Order_028 = "028";//小于最低可购买额度
    public static final String Status_Order_025 = "025";//出产品最大可购买额度
    public static final String Status_Order_032 = "032";//此用户已经认证
    public static final String Status_Order_035 = "035";//新手产品仅限未投资的用户购买
    public static final String Status_Order_024 = "024";//可购买金额不足
    public static final String Status_Order_041 = "041";//此卡已成功认证
    public static final String Status_Order_029 = "029";//用户下无此优惠券
    public static final String Status_Order_036 = "036";//优惠券未到使用期
    public static final String Status_Order_033 = "033";//优惠券已过期
    public static final String Status_Order_034 = "034";//购买金额未达到优惠券使用门槛

}
