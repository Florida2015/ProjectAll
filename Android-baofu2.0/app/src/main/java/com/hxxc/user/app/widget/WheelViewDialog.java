package com.hxxc.user.app.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.hxxc.user.app.R;
import com.hxxc.user.app.widget.wheel.WheelUtils;

public class WheelViewDialog extends Dialog{

	private Context mContext;
	public OnProvinceCityClickListener mListener;
	private WheelUtils wheelUtils;
	private String mProvince;
	private String mCity;

	public WheelViewDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public WheelViewDialog(Context context, int themeResId) {
		super(context, themeResId);
	}

	public WheelViewDialog(Context context) {
		super(context, R.style.AddressDialog);
		this.mContext = context;
		wheelUtils = new WheelUtils((Activity) mContext);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		wheelUtils.init(this);
		
		Window window = getWindow();
		// 获取到窗体的属性
		LayoutParams params = window.getAttributes();
		
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = LayoutParams.MATCH_PARENT;
		// 让对话框展示到屏幕的下边
		params.gravity = Gravity.BOTTOM;

		window.setAttributes(params);
		
		if (!TextUtils.isEmpty(mProvince) && !TextUtils.isEmpty(mCity)) {
			wheelUtils.setWheelNum(mProvince, mCity);
		}
	}
	
	public void setWheelNum(String province,String city){
		this.mProvince = province;
		this.mCity = city;
	}
	
	public void setOnProvinceCityClickListener(OnProvinceCityClickListener listener){
		this.mListener = listener;
	}
	
	public interface OnProvinceCityClickListener{
		void onProvinceCityClick(String province, String city);
	}
}
