package com.framwork.Utils;
import android.text.TextUtils;

/**
 * 
 * @author wwt
 * 
 */
public class RequestUtils {

	// 判断状态码
	public static boolean statusCode(int statusCode) {
		if (statusCode <= 0) {
            ToastUtils.showShort( "请求超时，请稍后再试!");
			return false;
		}
		if (statusCode >= 200 && statusCode < 300) {
			return true;
		}
        if (statusCode >= 300 && statusCode < 500){
            return true;
        }
        if (statusCode>=500){
            ToastUtils.showShort("服务器数据错误，请稍后再试!");
        }
		return false;
	}

    /**
     * 判断字符串是否为 html 格式
     */
    public static boolean isHtml5ToString(String isHtml){
        if (TextUtils.isEmpty(isHtml)) return true;
        if (isHtml.contains("<html>")){
//            ToastUtils.showShort("数据格式有误");
            return true;
        }else
            return false;
    }

}
