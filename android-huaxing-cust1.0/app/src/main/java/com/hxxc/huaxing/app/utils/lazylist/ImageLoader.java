package com.hxxc.huaxing.app.utils.lazylist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.ImageView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.utils.FileUtils;
import com.hxxc.huaxing.app.utils.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author wwt 类说明：图片缓存
 * 权限
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_SETTINGS" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
 */
public class ImageLoader {

	private static final String TAG = "ImageLoader";

	private int stub_id = R.mipmap.default_financier_pic;// 默认图片

	MemoryCache memoryCache = new MemoryCache();
	FileCache fileCache;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService executorService;// ExecutorService线程池
	Handler handler = new Handler();// handler to display images in UI thread
	Context context;

	private static ImageLoader imageLoader = null;

	public static ImageLoader getInstanse(Context context) {
		if (imageLoader == null) {
			imageLoader = new ImageLoader(context);
		}
		return imageLoader;
	}

	public ImageLoader(Context context) {
		this.context = context;
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5); // 创建线程池 5个线程
	}

	public ImageLoader(Context context, int def_img) {
		this.context = context;
		fileCache = new FileCache(context);
		executorService = Executors.newFixedThreadPool(5); // 创建线程池 5个线程
		this.stub_id = def_img;
	}

	/**
	 * 方法说明：控件显示图片
	 * 
	 * @param url
	 * @param imageView
	 */
	public void DisplayImage(String url, ImageView imageView) {
		imageViews.put(imageView, url); //
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
//			if (!ApplicationUtils.getNetworkType(context).equals("WiFi")
//					|| !UserConstants.FlagWifi) {// 移动网络
//				return;
//			}
			queuePhoto(url, imageView);
			imageView.setImageResource(stub_id);
		}
	}

	/**
	 *
	 * @param url
	 * @param imageView
     */
	public void DisplayImageNoCache(String url, ImageView imageView,int resid) {
		imageViews.put(imageView, url);
		queuePhoto(url, imageView,false);
		imageView.setImageResource(resid);
	}

	public void DisplayImageNoCache(String url, ImageView imageView) {
		imageViews.put(imageView, url);
		queuePhoto(url, imageView,false);
	}


	public void DisplayImage(String url, ImageView imageView, int resid) {
		if (imageView==null) {
			return;
		}
		this.stub_id = resid;
		imageViews.put(imageView, url); //
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
//			if (ApplicationUtils.getNetworkType(context).equals("WiFi")
//					|| (UserConstants.FlagWifi && (ApplicationUtils
//							.getNetworkType(context).equals("2G") || ApplicationUtils
//							.getNetworkType(context).equals("3G")))) {
				queuePhoto(url, imageView);
				imageView.setImageResource(resid);
//			} else {
//				imageView.setImageResource(resid);
//			}
		}
	}
	public void DisplayImage(String url, ImageView imageView, int resid,boolean flag) {
		if (imageView==null) {
			return;
		}
		this.stub_id = resid;
		imageViews.put(imageView, url); //
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			if (flag) imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
//			else imageView.setImageBitmap(bitmap);
		} else {
//			if (ApplicationUtils.getNetworkType(context).equals("WiFi")
//					|| (UserConstants.FlagWifi && (ApplicationUtils
//							.getNetworkType(context).equals("2G") || ApplicationUtils
//							.getNetworkType(context).equals("3G")))) {
			queuePhoto(url, imageView);
//			imageView.setImageResource(resid);
			if (flag) imageView.setBackgroundResource(resid);
//			else imageView.setImageBitmap(bitmap);
//			} else {
//				imageView.setImageResource(resid);
//			}
		}
	}
	/**
	 * 
	 * @param url
	 * @param imageView
	 */
	private void queuePhoto(String url, ImageView imageView) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p)); // 线程池处理一个任务
	}

	private void queuePhoto(String url, ImageView imageView,boolean flag) {
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		executorService.submit(new PhotosLoader(p,flag)); // 线程池处理一个任务
	}

	/**
	 * 方法说明：通过链接地址URL取得图片
	 * 
	 * @param url
	 * @return
	 */
	private Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);

			LogUtil.d("img_url="+url);

			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			conn.disconnect();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}

	private Bitmap getBitmap(String url,boolean flag) {
		File f = fileCache.getFile(url);

		// from SD cache
		Bitmap b = decodeFile(f);
		if (flag && b != null)
			return b;

		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);

			LogUtil.d("img_url="+url);

			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			conn.disconnect();
			bitmap = decodeFile(f);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memoryCache.clear();
			return null;
		}
	}
	/**
	 * 方法说明：从文件file得到图片
	 * 
	 * @param f
	 *            解码图像尺度,减少内存消耗
	 * @return
	 */
	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;//设置图片的
//			while (true) {
//				if (width_tmp / 2 < REQUIRED_SIZE
//						|| height_tmp / 2 < REQUIRED_SIZE)
//					break;
//				width_tmp /= 2;
//				height_tmp /= 2;
//				scale *= 2;
//			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();
			return bitmap;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 任务队列
	 */
	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public ImageView imageView;

		public PhotoToLoad(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	/**
	 * 内部类：开线程网络请求图片
	 * 
	 * @author wwt
	 * 
	 */
	class PhotosLoader implements Runnable {
		PhotoToLoad photoToLoad;
		boolean flags=true;

		PhotosLoader(PhotoToLoad photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		PhotosLoader(PhotoToLoad photoToLoad,boolean flag) {
			this.photoToLoad = photoToLoad;
			this.flags = flag;
		}

		@Override
		public void run() {
			try {
				if (flags&&imageViewReused(photoToLoad))
					return;
				Bitmap bmp = getBitmap(photoToLoad.url,flags);
				memoryCache.put(photoToLoad.url, bmp);
				if (flags&&imageViewReused(photoToLoad))
					return;
				BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad,flags); //
				handler.post(bd);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}

	boolean imageViewReused(PhotoToLoad photoToLoad) {
		String tag = imageViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoad photoToLoad;
		boolean falgs=true;

		public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
			bitmap = b;
			photoToLoad = p;
		}
		public BitmapDisplayer(Bitmap b, PhotoToLoad p,boolean flag) {
			bitmap = b;
			photoToLoad = p;
			this.falgs = flag;
		}

		public void run() {
			if (this.falgs&&imageViewReused(photoToLoad))
				return;
			if (bitmap != null) {
				photoToLoad.imageView.setImageBitmap(bitmap);				
			} else
				photoToLoad.imageView.setImageResource(stub_id);
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

	public String getImageLoaderSize() {
		long imgSize = memoryCache.getMemoryCacheSize()
				+ fileCache.getFileCacheSize();
		return FileUtils.getInstance().FormetFileSize(imgSize);
	}


}
