package com.hxxc.user.app.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/11/11.
 * 金额工具类,主要是金额的格式化,金额的加、减
 */

public class MoneyUtil {

    public static DecimalFormat fnum = new DecimalFormat("##0.00000000000000000000");

    public static String point = "##0";//取整
    public static String point_1 = "##0.0";//取一位小数
    public static String point_2 = "##0.00";//取二位小数
    public static String point_3 = "##0.000";//取三位小数

    /**
     * 格式化金额
     * @param value
     * @return
     */
    public static String formatMoney(String value){
        if(value == null || value == "" ){
            value = "0.00";
        }
        return fnum.format(new BigDecimal(value));
    }

    /**
     *
     * @param value
     * @param newScale  小数点个数
     * @param roundMode
     * ROUND_CEILING
    大于等于该数的那个最近值
    ROUND_DOWN
    正数是小于等于该数的那个最近数，负数是大于等于该数的那个最近数
    ROUND_FLOOR
    小于等于该数的那个值
    ROUND_HALF_DOWN
    五舍六入
    ROUND_HALF_EVEN
    向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP ，如果是偶数，使用ROUND_HALF_DOWN
    ROUND_HALF_UP
    四舍五入
    ROUND_UNNECESSARY
    计算结果是精确的，不需要舍入模式
    ROUND_UP
    和ROUND_DOWN相反
     * @return 字符串 加逗号
     * eg.MoneyUtil.formatMoney("15112321.15263",1,ROUND_FLOOR)
     */
    public static String formatMoney(String value,int newScale,int roundMode){
        if(value == null || value == "" ){
            value = "0.00";
        }
        return  new DecimalFormat().format(new BigDecimal(value).setScale(newScale,roundMode));
    }
    public static String formatMoney(BigDecimal value,int newScale,int roundMode){
        return  new DecimalFormat(point_2).format(value.setScale(newScale,roundMode));
    }
    public static String formatMoney(BigDecimal value,int newScale,int roundMode,String type){
        if (value==null)return "0";
        else
            return  new DecimalFormat(type).format(value.setScale(newScale,roundMode));
    }
    /**
     * 取几位小数点
     * @param value 值
     * @param type 类型
     * @return
     */
    public static String formatDecimal(double value,String type){
        return  new DecimalFormat(type).format(value);
    }
    /**
     * 金额相加
     * @param valueStr 基础值
     * @param addStr 被加数
     * @return
     */
    public static String moneyAdd(String valueStr,String addStr){
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal augend = new BigDecimal(addStr);
        return fnum.format(value.add(augend));
    }

    /**
     * 金额相加
     * @param value 基础值
     * @param augend 被加数
     * @return
     */
    public static BigDecimal moneyAdd(BigDecimal value,BigDecimal augend){
        return value.add(augend);
    }

    /**
     * 金额相减
     * @param valueStr 基础值
     * @param minusStr 减数
     * @return
     */
    public static String moneySub(String valueStr,String minusStr){
        BigDecimal value= new BigDecimal(valueStr);
        BigDecimal subtrahend = new BigDecimal(minusStr);
        return fnum.format(value.subtract(subtrahend));
    }

    /**
     * 金额相减
     * @param value 基础值
     * @param subtrahend 减数
     * @return
     */
    public static BigDecimal moneySub(BigDecimal value,BigDecimal subtrahend){
        return value.subtract(subtrahend);
    }


    /**
     * 金额相乘
     * @param valueStr 基础值
     * @param mulStr 被乘数
     * @return
     */
    public static String moneyMul(String valueStr,String mulStr){
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(mulStr);
        return fnum.format(value.multiply(mulValue));
    }

    /**
     * 金额相乘
     * @param value 基础值
     * @param mulValue 被乘数
     * @return
     */
    public static BigDecimal moneyMul(BigDecimal value,BigDecimal mulValue){
        return value.multiply(mulValue);
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数
     * @param valueStr 基础值
     * @param divideStr 被乘数
     * @return
     */
    public static String moneydiv(String valueStr,String divideStr){
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal divideValue = new BigDecimal(divideStr);
        return fnum.format(value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 金额相除 <br/>
     * 精确小位小数
     * @param value 基础值
     * @param divideValue 被乘数
     * @return
     */
    public static BigDecimal moneydiv(BigDecimal value,BigDecimal divideValue){
        return value.divide(divideValue, 2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 值比较大小
     * <br/>如果valueStr大于等于compValueStr,则返回true,否则返回false
     *  true 代表可用余额不足
     * @param valueStr (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static boolean moneyComp(String valueStr,String compValueStr){
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal compValue = new BigDecimal(compValueStr);
        //0:等于    >0:大于    <0:小于
        int result = value.compareTo(compValue);
        if(result >= 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 值比较大小
     * <br/>如果valueStr大于等于compValueStr,则返回true,否则返回false
     *  true 代表可用余额不足
     * @param valueStr (需要消费金额)
     * @param compValueStr (可使用金额)
     * @return
     */
    public static boolean moneyComp(BigDecimal valueStr,BigDecimal compValueStr){
        //0:等于    >0:大于    <0:小于
        int result = valueStr.compareTo(compValueStr);
        if(result >= 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 金额乘以，省去小数点
     * @param valueStr 基础值
     * @return
     */
    public static String moneyMulOfNotPoint (String valueStr, String divideStr){
        BigDecimal value = new BigDecimal(valueStr);
        BigDecimal mulValue = new BigDecimal(divideStr);
        valueStr = fnum.format(value.multiply(mulValue));
        return fnum.format(value.multiply(mulValue)).substring(0, valueStr.length()-3);
    }

    /**
     * 给金额加逗号切割
     * @param str
     * @return
     */
    public static String addComma(String str) {
        try {
            String banNum = "";
            if (str.contains(".")) {
                String[] arr = str.split("\\.");
                if (arr.length == 2) {
                    str = arr[0];
                    banNum = "." + arr[1];
                }
            }
            // 将传进数字反转
            String reverseStr = new StringBuilder(str).reverse().toString();
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {
                if (i * 3 + 3 > reverseStr.length()) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
            }
            // 将[789,456,] 中最后一个[,]去除
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length() - 1);
            }
            // 将数字重新反转
            String resultStr = new StringBuilder(strTemp).reverse().toString();
            resultStr += banNum;
            return resultStr;
        }catch(Exception e){
            return str;
        }

    }
    public static String addComma(BigDecimal value,int newScale,int roundMode) {
        if (value==null)return "0.00";
        String str = formatMoney(value,newScale,roundMode);
        try {
            String banNum = "";
            if (str.contains(".")) {
                String[] arr = str.split("\\.");
                if (arr.length == 2) {
                    str = arr[0];
                    banNum = "." + arr[1];
                }
            }
            // 将传进数字反转
            String reverseStr = new StringBuilder(str).reverse().toString();
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {
                if (i * 3 + 3 > reverseStr.length()) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
            }
            // 将[789,456,] 中最后一个[,]去除
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length() - 1);
            }
            // 将数字重新反转
            String resultStr = new StringBuilder(strTemp).reverse().toString();
            resultStr += banNum;
            return resultStr;
        }catch(Exception e){
            return str;
        }

    }
    public static String addComma(BigDecimal value,int newScale,int roundMode,String type) {
        if (value==null)return "0.00";
        String str = formatMoney(value,newScale,roundMode,type);
        try {
            String banNum = "";
            if (str.contains(".")) {
                String[] arr = str.split("\\.");
                if (arr.length == 2) {
                    str = arr[0];
                    banNum = "." + arr[1];
                }
            }
            // 将传进数字反转
            String reverseStr = new StringBuilder(str).reverse().toString();
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {
                if (i * 3 + 3 > reverseStr.length()) {
                    strTemp += reverseStr.substring(i * 3, reverseStr.length());
                    break;
                }
                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
            }
            // 将[789,456,] 中最后一个[,]去除
            if (strTemp.endsWith(",")) {
                strTemp = strTemp.substring(0, strTemp.length() - 1);
            }
            // 将数字重新反转
            String resultStr = new StringBuilder(strTemp).reverse().toString();
            resultStr += banNum;
            return resultStr;
        }catch(Exception e){
            return str;
        }

    }
}
