package com.framwork.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * 系统工具
 * 
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
}
