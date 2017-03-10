package com.framwork.Utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * 网络状态判断
 * 
 * @author LiaoBin
 * 
 */
public class NetWorkUtils {

	public static boolean isSuccess = false;

	/**
	 * 获取链接类型
	 */
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	/**
	 * 判断是否是手机移动数据网络
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断是否是wifi网络
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断是否网络可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断是否网络可用(严谨方法)
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkAvailable(Context context) {

		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity == null) {
				return false;
			}
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {

				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 判断是否网络可用
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkAvailable_00(Context context) {
		ConnectivityManager cm = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE));
		if (cm != null) {
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null && info.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gps是否打开
	 * 
	 * @param context
	 * @return
	 */
	public boolean isGpsEnabled(Context context) {
		LocationManager locationManager = ((LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE));
		List<String> accessibleProviders = locationManager.getProviders(true);
		return accessibleProviders != null && accessibleProviders.size() > 0;
	}

	/**
	 * 判断当前网络的各种状态
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetWorkType(Context context) {
        if (context==null)return null;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return null;
		}
		NetworkInfo info = connectivity.getActiveNetworkInfo();

		if (info != null && info.isConnected()) {// Intent is user

			if (info.getState() == NetworkInfo.State.CONNECTED) {
				String name = info.getTypeName();// "mobile","WIFI","MOBILE","wifi"
				
				if (info.getType()== ConnectivityManager.TYPE_WIFI) {
					return "wifi";
				}else if(info.getType()== ConnectivityManager.TYPE_MOBILE){
					if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS
							|| info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
							|| info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
						return "2G";
					} else if(info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE){
						return "4G";
					}else {
						return "3G";
					}
				}else {
					return "unknow";
				}
//				MyLog.d(":当前网络名称：" + name);
			} else {// no Intent
				return null;
//				MyLog.d(":没有可用网络！");
			}
		} else {// no Intent
			return null;
//			MyLog.d(":没有可用网络！");
		}
		
	}

}
