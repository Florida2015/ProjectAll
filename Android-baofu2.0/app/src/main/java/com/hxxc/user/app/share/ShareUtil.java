//package com.hxxc.user.app.share;
//
//import android.app.Activity;
//import android.text.TextUtils;
//
//import com.hxxc.user.app.Constants;
//import com.hxxc.user.app.bean.UserInfo;
//import com.hxxc.user.app.rest.ApiManager;
//import com.hxxc.user.app.rest.rx.SimpleCallback;
//import com.hxxc.user.app.utils.LogUtils;
//import com.hxxc.user.app.utils.SPUtils;
//import com.umeng.socialize.controller.UMServiceFactory;
//import com.umeng.socialize.controller.UMSocialService;
//import com.umeng.socialize.media.QQShareContent;
//import com.umeng.socialize.media.QZoneShareContent;
//import com.umeng.socialize.media.SinaShareContent;
//import com.umeng.socialize.media.UMImage;
//import com.umeng.socialize.sso.QZoneSsoHandler;
//import com.umeng.socialize.sso.UMQQSsoHandler;
//import com.umeng.socialize.weixin.controller.UMWXHandler;
//import com.umeng.socialize.weixin.media.CircleShareContent;
//import com.umeng.socialize.weixin.media.WeiXinShareContent;
//
//public class ShareUtil {
//
//	private Activity activity;
//
//	private final UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
//
//	public ShareUtil(Activity activity) {
//		this.activity = activity;
//		configPlatforms();
//	}
//
//
//	public ShareUtil setShareContentPre(final int image, final String title, final String content, final String url){
//		final UserInfo bean = SPUtils.geTinstance().getUserInfo();
//		if(null != bean){
//			setShareContent(image,title, content, url+"?invCode="+bean.getInvitedCode()+"&fid="+bean.getFid());
//			ApiManager.getInstance()
//					.getUserFid(bean.getUid()+"", new SimpleCallback<String>() {
//						@Override
//						public void onNext(String s) {
//							LogUtils.e("Fid=="+s);
//							if(!TextUtils.isEmpty(s)){
//								setShareContent(image,title, content, url+"?invCode="+bean.getInvitedCode()+"&fid="+s);
//							}
//						}
//
//						@Override
//						public void onError() {
//							LogUtils.e("Fid==onError/uid=="+bean.getUid());
//						}
//					});
//		}else{
//			setShareContent(image,title, content, url);
//		}
//
//		return this;
//	}
//
//	private void configPlatforms() {
//		// 添加新浪SSO授权
////		mController.getConfig().setSsoHandler(new SinaSsoHandler());
//		// 添加腾讯微博SSO授权
////		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//
//
//		String qq_appId = Constants.qq_appId;
//		String qq_appKey = Constants.qq_appKey;
//		try {
//			// 添加QQ支持, 并且设置QQ分享内容的target url
//			UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, qq_appId,qq_appKey);
//			qqSsoHandler.addToSocialSDK();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			// 添加QZone平台
//			QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, qq_appId,qq_appKey);
//			qZoneSsoHandler.addToSocialSDK();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 添加微信、微信朋友圈平台
//		String wx_appId = Constants.wx_appId;
//		String wx_appSecret = Constants.wx_appSecret;
//		// 添加微信平台
//		try {
//			UMWXHandler wxHandler = new UMWXHandler(activity, wx_appId,wx_appSecret);
//			wxHandler.showCompressToast(false);
//			wxHandler.addToSocialSDK();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// 支持微信朋友圈
//		try {
//			UMWXHandler wxCircleHandler = new UMWXHandler(activity, wx_appId,wx_appSecret);
//			wxCircleHandler.setToCircle(true);
//			wxCircleHandler.addToSocialSDK();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 调用postShare分享。跳转至分享编辑页，然后再分享
//	 */
//	public void postShare() {
//		new CustomShareBoardDialog(activity).show();
//	}
//
//	private void setShareContent(int image,String title, String content, String url) {
//		// 配置SSO
////		mController.getConfig().setSsoHandler(new SinaSsoHandler());
////		mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//		mController.getConfig().closeToast();
//		// 微信
//		WeiXinShareContent weixinContent = new WeiXinShareContent();
//		weixinContent.setShareContent(content);
//		weixinContent.setTitle(title);
//		weixinContent.setShareImage(new UMImage(activity, image));
//		weixinContent.setTargetUrl(url);
//		mController.setShareMedia(weixinContent);
//		// 设置朋友圈分享的内容
//		CircleShareContent circleMedia = new CircleShareContent();
//		circleMedia.setShareContent(content);
//		circleMedia.setTitle(title);
//		circleMedia.setTargetUrl(url);
//		circleMedia.setShareImage(new UMImage(activity, image));
//		mController.setShareMedia(circleMedia);
//		// 设置QQ空间分享内容
//		QZoneShareContent qzone = new QZoneShareContent();
//		qzone.setShareContent(content);
//		qzone.setTargetUrl(url);
//		qzone.setTitle(title);
//		qzone.setShareImage(new UMImage(activity, image));
//		mController.setShareMedia(qzone);
//		// qq分享设置
//		QQShareContent qqShareContent = new QQShareContent();
//		qqShareContent.setShareContent(content);
//		qqShareContent.setTitle(title);
//		qqShareContent.setTargetUrl(url);
//		qqShareContent.setShareImage(new UMImage(activity, image));
//		mController.setShareMedia(qqShareContent);
//
//		// 新浪微博
//		SinaShareContent sinaContent = new SinaShareContent();
//		sinaContent.setShareContent(content+url);
//		sinaContent.setTitle(title);
//		sinaContent.setTargetUrl(url);
//		sinaContent.setShareImage(new UMImage(activity, image));
//		mController.setShareMedia(sinaContent);
//	}
//}
