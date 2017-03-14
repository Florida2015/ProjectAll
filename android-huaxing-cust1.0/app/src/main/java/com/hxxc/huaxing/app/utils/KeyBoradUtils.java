package com.hxxc.huaxing.app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 系统工具
 * 对软键盘的处理
 * @author wwt 2014.10.28 11:44
 */
public class KeyBoradUtils {

	// 判断 获取输入法打开的状态
	public static boolean isKeyBoardShow(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
		return isOpen;
	}

	// 调用隐藏系统默认的输入法
	public static void getSystemInputMethod(Context context) {
		((InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(((Activity) (context))
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		// (WidgetSearchActivity是当前的Activity)
	}

	// 如果输入法在窗口上显示
	public static void showInputMethod(Context context) {
		if (isKeyBoardShow(context)) {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 显示软键盘
	 * @param context
	 */
	public static void showSoftInput(Context context,View view) {
		if (!isKeyBoardShow(context)) {//
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, 0);
		}
	}
	/**
	 * 隐藏软键盘
	 * @param context
     */
	public static void hideSoftInput(Context context,View view) {
		if (isKeyBoardShow(context)) {//显示
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

}
