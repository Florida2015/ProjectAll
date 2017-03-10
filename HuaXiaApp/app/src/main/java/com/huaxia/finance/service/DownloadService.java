package com.huaxia.finance.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.framwork.Utils.ApplicationUtils;
import com.framwork.Utils.FileUtils;
import com.framwork.Utils.MyLog;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.DMConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author  类说明：开服务下载apk
 */
public class DownloadService extends Service {

	private String name;
	protected static final String TAG = "api_DownloadService";
	// 需要下载的文件大小
	int fileSize;
	// 当前下载量
	int downLoadFileSize;

	NotificationManager mNotificationManager;
	NotificationCompat.Builder mBuilder;
	private String title = "花虾金融";

	private int contentCount = 0;
	private Handler downloadNotifHandler = new Handler() {
		public void handleMessage(Message msg) {
			Thread.currentThread();
			if (!Thread.interrupted()) {
				switch (msg.what) {
				case 0:
					mBuilder.setProgress(100, contentCount, false);
					mNotificationManager.notify(1,mBuilder.build());
					break;
				case 1:
					if (contentCount == 100) {
						mBuilder.setContentText("花虾金融下载完成");
						mNotificationManager.notify(1,mBuilder.build());
						installApk();
						stopSelf();
					}
					break;
				case 2:
					showNotification();
					stopSelf();
					break;
				default:
					break;
				}
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * The notification is the icon and associated expanded entry in the status
	 * bar.
	 */
	protected void showNotification() {
		CharSequence from = "花虾金融更新失败";
		CharSequence message = "下载超时，请稍后重试！";
		mBuilder.setContentTitle(from);
		mBuilder.setContentText(message);
		mNotificationManager.notify(1,mBuilder.build());
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onDestroy() {
		mNotificationManager.cancel(0);
		super.onDestroy();
	}
	//通过binder实现调用者client与Service之间的通信
	private ServiceBinder binder = new ServiceBinder();
	//此方法是为了可以在Acitity中获得服务的实例
	public class ServiceBinder extends Binder {
		public DownloadService getService() {
			return DownloadService.this;
		}
	}

	File file;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		MyLog.d("api_down_url__onStartCommand");
		if (intent!=null){
			DMConstant.UPDATE_URL  = intent.getStringExtra("url");
			name = intent.getStringExtra("versionName");
			MyLog.d("api_down_url=" + DMConstant.UPDATE_URL +"_name="+name);
			name = "huaxiafinance_"+name+".apk";

			initView();//FileUtils.getUrlFileName(DMConstant.UPDATE_URL)

			if (FileUtils.isFileNameExist("/mnt/sdcard/huaxiafinance/upgrade/",name)){
				contentCount=100;
				sendNotifMsg(1);
			}else  {
				file = FileUtils.getInstance().makeFile("/mnt/sdcard/huaxiafinance/upgrade/",name);
				DMConstant.APK_FILE = file.getAbsolutePath();
				downloadApkFile(name);
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	protected void initView() {
		CharSequence noticeText = "花虾金融下载更新...";
		mBuilder = new NotificationCompat.Builder(this);
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		mBuilder.setContentIntent(pendingIntent);// 设置通知栏点击意图
		mBuilder.setContentTitle(title);// 设置通知栏标题
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//悬挂式Notification，5.0后显示
			mBuilder.setContentText(noticeText).setFullScreenIntent(pendingIntent, true);
			mBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
			mBuilder.setSmallIcon(R.drawable.icon_small_logo);// 设置通知小ICON（5.0必须采用白色透明图片）
		}else{
			mBuilder.setSmallIcon(R.drawable.icon_small_logo);// 设置通知小ICON
			mBuilder.setContentText(noticeText);
		}
//        mBuilder.setProgress(100,16,false);
		mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_umeng));// 设置通知大ICON
		mBuilder.setTicker(noticeText); // 通知首次出现在通知栏，带上升动画效果的
		mBuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
		mBuilder.setPriority(NotificationCompat.PRIORITY_MAX); // 设置该通知优先级
		mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);//在任何情况下都显示，不受锁屏影响。
		mBuilder.setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消
		mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = mBuilder.build();//API 16
		mNotificationManager.notify(1, notification);

	}

	protected void downloadApkFile(final String fName) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
//				File file = new File(DMConstant.getDownloadFilePath());//new StringBuffer("com.huaxia.finance_").append(name).append(".apk").toString()
				if (file==null){
					file = FileUtils.getInstance().makeFile("/mnt/sdcard/huaxiafinance/upgrade/",fName);//FileUtils.getUrlFileName(DMConstant.UPDATE_URL)
					DMConstant.APK_FILE = file.getAbsolutePath();
				}
				try {
					URL url = new URL(DMConstant.UPDATE_URL);
					MyLog.d(TAG, "下载：" + url);
//					MyLog.d(TAG, "apkFile=" + DMConstant.getDownloadFilePath());//DMConstant.APK_FILE
//					File file1= FileUtils.getInstance().makeFile(DMConstant.getDownloadFilePath(),new StringBuffer("com.huaxia.finance_").append(name).append(".apk").toString());
					try {
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.setRequestMethod("GET");
						conn.setConnectTimeout(5000);
						if (200 == conn.getResponseCode()) {
							InputStream is = conn.getInputStream();
//							conn.setRequestProperty("Accept-Encoding", "identity");
							// 获得文件大小
							fileSize = conn.getContentLength();
//							fileSize = 8*1024*1024;

							MyLog.d("api_fileSize=="+fileSize);

							// 开始下载apk文件
//							MyLog.d("down_="+new StringBuffer(DMConstant.getDownloadFilePath()).append("/com.huaxia.finance_").append(name).append(".apk").toString());
//							File file1= FileUtils.getInstance().makeFile(DMConstant.APK_FILE,new StringBuffer("com.huaxia.finance_").append(name).append(".apk").toString());
//							DMConstant.APK_FILE=new StringBuffer(DMConstant.getDownloadFilePath()).append("/com.huaxia.finance_").append(name).append(".apk").toString();
							FileOutputStream fos = new FileOutputStream(file);////DMConstant.APK_FILE
							conn.connect();
							 byte buf[] = new byte[1024];
//							byte buf[] = new byte[2048];
							downLoadFileSize = 0;
							// 初始化
							sendNotifMsg(0);
							do {
								int numread = is.read(buf);
//								MyLog.d(TAG, "numread:" + numread);
								if (numread <= 0)
									break;
								fos.write(buf, 0, numread);
								downLoadFileSize += numread;
								long result = downLoadFileSize * 100 / fileSize;
								sendNotifMsg(0);
								contentCount = (int) result;
								MyLog.d("api_count="+result+"_downLoadFileSize="+downLoadFileSize+"_fileSize="+fileSize);
								// 更新进度条
							} while (contentCount < 100);
							contentCount=100;
							// 通知下载完成
							fos.close();
							is.close();
							conn.disconnect();
							sendNotifMsg(1);
						} else {
							MyLog.d(TAG,
									"conn.getResponseCode()"
											+ conn.getResponseCode());
							sendNotifMsg(2);// 下载出现错误
						}
					} catch (Exception e) {
						MyLog.d(TAG, "url error - url");
						e.printStackTrace();
						sendNotifMsg(2);// 下载出现错误
					}
				} catch (MalformedURLException e1) {
					MyLog.d(TAG, "url error");
					e1.printStackTrace();
					sendNotifMsg(2);// 下载出现错误
				}
			}
		});
		thread.start();
	}

	public void sendNotifMsg(int num) {
		Message message = new Message();
		message.what = num;
		downloadNotifHandler.sendMessage(message);
	}
	public void sendNotifMsg(int num,long time) {
		Message message = new Message();
		message.what = num;
		downloadNotifHandler.sendMessageDelayed(message, time);
	}

	public void installApk() {
        ApplicationUtils.installApk(this, DMConstant.APK_FILE);
		MyLog.d(TAG, "安装目录：" + DMConstant.APK_FILE);
		File file = new File(DMConstant.APK_FILE);
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
		android.os.Process.killProcess(android.os.Process.myPid());
		onDestroy();
	}
}
