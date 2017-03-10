package com.hxxc.user.app.rest.download;

import android.os.Handler;
import android.os.Message;

import com.hxxc.user.app.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
	
	private String url;
	private String localfile;
	private Handler mHandler;// 消息处理器  
	
	public Downloader(String urlstr, String localfile,Handler mHandler){
		this.url = urlstr;  
        this.localfile = localfile;  
        this.mHandler = mHandler;  
	}
	
	
	public void start(){
		new MyThread(url).start();
	}
	
	public class MyThread extends Thread {
		
		private String urlstr;
		public MyThread(String urlstr) {
			this.urlstr = urlstr;
		}
		@Override
		public void run() {
			HttpURLConnection connection = null;
			InputStream is = null;
			FileOutputStream out = null;
			try {

				URL url = new URL(urlstr);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Connection", "Keep-Alive");
//				connection.setRequestProperty("Accept-Encoding", "identity");
				connection.connect();
				out = new FileOutputStream(new File(localfile));
				// 将要下载的文件写到保存在保存路径下的文件中

				int size = connection.getContentLength();

				is = connection.getInputStream();
				byte[] buffer = new byte[4096];
				int length = -1;
				int compeleteSize = 0 ;
				while ((length = is.read(buffer)) != -1) {
					out.write(buffer, 0, length);
					compeleteSize += length;
					// 更新数据库中的下载信息
					// 用消息将下载信息传给进度条，对进度条进行更新
					int pro = (compeleteSize*100/size);
					LogUtils.e("当前==="+compeleteSize+",总大小:=="+size+",进度==="+pro);
					Message message = Message.obtain();
					message.what = 1;
					message.arg1 = pro;
					mHandler.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(null != is){
						is.close();
					}
					if(null != out){
						out.close();
					}
					if(null != connection){
						connection.disconnect();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(10);
			}

		}
	}
}
