package com.framwork.lazylist;

import android.content.Context;

import java.io.File;

/**
 * 
 * @author wwt 类说明：文件缓存
 */
public class FileCache {

	private File cacheDir;

	/**
	 * 方法说明:在SDScread新建缓存文件
	 * 
	 * @param context
	 */
	public FileCache(Context context) {
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "LazyList");
		else
			cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	// file size
	public long getFileCacheSize() {
		return cacheDir.length();
	}

	/**
	 * 类说明：通过路径得到文件file
	 * 
	 * @param url
	 * @return
	 */
	public File getFile(String url) {
		// I identify images by hashcode. Not a perfect solution, good for the demo.
		String filename = String.valueOf(url.hashCode());
		// Another possible solution (thanks to grantland)
		// String filename = URLEncoder.encode(url);
		File f = new File(cacheDir, filename);
		return f;

	}

	/**
	 * 类说明：清除SDcard缓存文件
	 */
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}