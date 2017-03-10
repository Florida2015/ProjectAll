package com.huaxia.finance.umengpush;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.framwork.Utils.ApplicationUtils;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.mangemoneydm.OrderDetailActivity;
import com.huaxia.finance.mangemoneydm.OrderListActvity;
import com.huaxia.finance.mangemoneydm.ProductExplainActivity;
import com.huaxia.finance.minedm.CashVoucherActivity;
import com.huaxia.finance.recommenddm.ActivtiesWebActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.common.message.Log;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.HashMap;
import java.util.Map;

//使用自定义的NotificationHandler，来结合友盟统计处理消息通知
//参考http://bbs.umeng.com/thread-11112-1-1.html
public class CustomNotificationHandler extends UmengNotificationClickHandler {
	
	private static final String TAG = CustomNotificationHandler.class.getName();
	
	@Override
	public void dismissNotification(Context context, UMessage msg) {
		Log.d(TAG, "dismissNotification__msg="+msg.getRaw().toString());
		super.dismissNotification(context, msg);
		MobclickAgent.onEvent(context, "dismiss_notification");
	}
	
	@Override
	public void launchApp(Context context, UMessage msg) {
		Log.d(TAG, "launchApp__="+msg.getRaw().toString());
		super.launchApp(context, msg);
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "launch_app");
		MobclickAgent.onEvent(context, "click_notification", map);
	}
	
	@Override
	public void openActivity(Context context, UMessage msg) {
		Log.d(TAG, "openActivity__=" + msg.getRaw().toString());
		Log.d(TAG, "openActivity__msgtype=" + msg.extra.get("type").toString());
		Log.d(TAG, "openActivity__msgkey=" + msg.extra.get("key").toString());
		String type = msg.extra.get("type").toString().trim();
		String key =msg.extra.get("key").toString().trim();
		if (TextUtils.isEmpty(type)){
			if (!ApplicationUtils.isApplicationBroughtToBackground(context)){

			}else{//首页
				Intent intent = new Intent(context, MenuTwoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("flagLogin", "menu");
				context.startActivity(intent);
			}
		}else{//
			if (!ApplicationUtils.isApplicationBroughtToBackground(context)){//app top
				if (!ApplicationUtils.getTopActivityName(context).contains("com.huaxia.finance.MenuTwoActivity")) {

					StartActivity(false,context,type,key);

				}
			}else{

				StartActivity(true,context, type, key);

			}
		}

//	super.openActivity(context, msg);

//				Map<String,String> map = new HashMap<String, String>();
//				map.put("type","value");
//				//value = home 首页 product产品说明页 orderlist订单列表 couponlist优惠券列表 activity活动页 productlist产品列表页
//				//activitylist活动列表页 orderdetail订单详情页 mine我的页面
//				map.put("key", "");//参数


		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "open_activity");
		MobclickAgent.onEvent(context, "click_notification", map);

	}


	public void StartActivity(boolean flag,Context mContext,String type,String key){
		if (TextUtils.isEmpty(type))return;
		Intent intentM = new Intent(mContext,MenuTwoActivity.class);
		intentM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		int temp =0;
		if(type.equals("product")){//product产品说明页
			intent.setClass(mContext, ProductExplainActivity.class);
			intent.putExtra("produnctId", key);
		}else if(type.equals("orderlist")){//orderlist 订单列表
			intent.setClass(mContext, OrderListActvity.class);
			temp =3;
		}else if(type.equals("couponlist")){//couponlist 优惠券列表
			intent.setClass(mContext, CashVoucherActivity.class);
			temp =3;
		}else if(type.equals("activity")){//activity 活动页
			intent.setClass(mContext, ActivtiesWebActivity.class);
			intent.putExtra("activityId",key);
		}else if(type.equals("productlist")){//productlist 产品列表页
			temp =1;
		}else if(type.equals("activitylist")){//activitylist 活动列表页
			temp =2;
		}else if(type.equals("orderdetail")){//orderdetail 订单详情页
			intent.setClass(mContext, OrderDetailActivity.class);
			intent.putExtra("orderId", key);
			temp =3;
		}else if(type.equals("mine")){//mine 我的页面
			temp =3;
		}
		if (flag){
			intentM.putExtra("index",temp);
			mContext.startActivity(intentM);
		}else{
			if (MenuTwoActivity.getInstance()!=null)MenuTwoActivity.getInstance().setTab(temp);
		}
		if (intent.getClass()!=null) mContext.startActivity(intent);
	}

//	try {
//		if (!ApplicationUtils.isApplicationBroughtToBackground(context)){
//			if (!ApplicationUtils.getTopActivityName(context).contains(msg.activity)) {
//				Intent intent = new Intent(context,Class.forName(msg.activity));
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.putExtra("produnctId", msg.extra.get("productId"));
//				intent.putExtra("activityId", msg.extra.get("activityId"));
//				intent.putExtra("flagLogin", "menu");
//				context.startActivity(intent);
//			}
//		}else{
//			Intent intentM = new Intent(context,Class.forName("com.huaxia.finance.MenuTwoActivity"));
//			intentM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			Intent intent = new Intent(context,Class.forName(msg.activity));
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent.putExtra("produnctId", msg.extra.get("productId"));
//			intent.putExtra("activityId", msg.extra.get("activityId"));
//			intent.putExtra("flagLogin", "menu");
//			context.startActivity(intentM);
//			context.startActivity(intent);
//		}
//	} catch (ClassNotFoundException e) {
//		e.printStackTrace();
//	}
	
	@Override
	public void openUrl(Context context, UMessage msg) {
		Log.d(TAG, "openUrl__="+msg.getRaw().toString());
		super.openUrl(context, msg);
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "open_url");
		MobclickAgent.onEvent(context, "click_notification", map);
	}
	
	@Override
	public void dealWithCustomAction(Context context, UMessage msg) {
		Log.d(TAG, "dealWithCustomAction__="+msg.getRaw().toString());
		super.dealWithCustomAction(context, msg);
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "custom_action");
		MobclickAgent.onEvent(context, "click_notification", map);
	}
	
	@Override
	public void autoUpdate(Context context, UMessage msg) {
		Log.d(TAG, "autoUpdate");
		super.autoUpdate(context, msg);
		Map<String, String> map = new HashMap<String, String>();
		map.put("action", "auto_update");
		MobclickAgent.onEvent(context, "click_notification", map);
	}

}
