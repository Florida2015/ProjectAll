package com.framwork.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huaxia.finance.DMApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 重构ToastUtils类
 * @author Carl
 * 
 */
public class ToastUtils extends Activity{

	private final static Context context = DMApplication.getInstance();
//    private final static Context context =null;

	private static Toast toast;
	private static long flagToast=0;
	
	public enum ToastDisplayTime {
		TOAST_DISPLAY_LONG,
		TOAST_DISPLAY_SHORT
	}
	
	private ToastUtils() {
	}
	

	@SuppressLint("ShowToast")
	private static void checkToast() {
		if (context!=null) {
			toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
		}
	}

	private static void setToastDisplayTime(ToastDisplayTime time){
		if(time== ToastDisplayTime.TOAST_DISPLAY_LONG)
			toast.setDuration(Toast.LENGTH_LONG);
		else {
			toast.setDuration(Toast.LENGTH_SHORT);
		}
	}

    private static void show(Activity activity,String msg, ToastDisplayTime time,View view,int yfloat) {
        toast = Toast.makeText(activity, null, Toast.LENGTH_SHORT);
        toast.setText(msg);
        setToastDisplayTime(time);
        toast.setGravity(Gravity.TOP, 0, yfloat);
        toast.setView(view);
        if (System.currentTimeMillis()-flagToast>2000) {
            flagToast=System.currentTimeMillis();
            toast.show();
        }
    }
	
	private static void show(String msg, ToastDisplayTime time) {
		if(TextUtils.isEmpty(msg))return;
		checkToast();
		toast.setText(msg);
		setToastDisplayTime(time);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		if (System.currentTimeMillis()-flagToast>2000) {
			flagToast=System.currentTimeMillis();
			toast.show();
		}
	}

	private static void show(int msg, ToastDisplayTime time) {
		checkToast();
		toast.setText(msg);
		setToastDisplayTime(time);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		if (System.currentTimeMillis()-flagToast>2000) {
			flagToast=System.currentTimeMillis();
			toast.show();
		}
	}

	public static void showLong(String msg) {
		show(msg, ToastDisplayTime.TOAST_DISPLAY_SHORT);
	}

	public static void showLong(int msg) {
		show(msg, ToastDisplayTime.TOAST_DISPLAY_SHORT);
	}
	
	public static void showShort(String msg) {
		show(msg, ToastDisplayTime.TOAST_DISPLAY_SHORT);
	}

    public static void showShortView(Activity activity, String msg,int layoutId,int yfloat) {
        View view = activity.getLayoutInflater().inflate(layoutId,null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        show(activity,msg, ToastDisplayTime.TOAST_DISPLAY_SHORT, view, yfloat);
    }

	public static void showShort(int msg) {
		show(msg, ToastDisplayTime.TOAST_DISPLAY_SHORT);
	}
	/**
	 * 自定义URL编码
	 * @param parentStr 需要编码的字符串
	 * @return 编码后字符串
	 */
public static String encodeString(String parentStr){
	if(TextUtils.isEmpty(parentStr))
		return "";
	StringBuffer strBuf=new StringBuffer();
	String parentArr[]=parentStr.split("&");
	if(parentArr.length<=0)
		return "";
	for(int i=0;i<parentArr.length;i++){
		String childStr=parentArr[i];
		String childArr[]=childStr.split("=");
		if(childArr.length==2){
			String key=childArr[0].trim();
			String value=childArr[1].trim();
			try {
				strBuf.append("&"+key+"="+URLEncoder.encode(value,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}
		}
	}
	return strBuf.toString();
}
}
