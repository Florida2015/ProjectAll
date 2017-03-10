package com.hxxc.user.app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hxxc.user.app.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chenqun on 2016/6/23.
 */
public class ImageUtils {

    private static volatile ImageUtils mImageUtils;
    private int mWindowWidth;
    private int mWindowHeight;

    public static ImageUtils getInstance() {
        ImageUtils utils = mImageUtils;
        if (null == utils) {
            synchronized (ImageUtils.class) {
                utils = mImageUtils;
                if (null == utils) {
                    utils = new ImageUtils();
                    mImageUtils = utils;
                }
            }
        }
        return utils;
    }

    public void initScreenParams(Activity activity) {
        mWindowWidth = DisplayUtil.getWindowWidth(activity);
        mWindowHeight = DisplayUtil.getWindowHeight(activity);
    }

    public void clearCache() {
        imageLoader.clearMemoryCache();
        imageLoader.clearDiskCache();
    }

    public void clearMemoryCache() {
        imageLoader.clearMemoryCache();
    }

    public ImageLoader imageLoader = ImageLoader.getInstance();
    //默认配置
    public static DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new RoundedBitmapDisplayer(10))  // 设置成圆角图片
            .showStubImage(R.drawable.department)          // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.drawable.department)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.department)       // 设置图片加载或解码过程中发生错误显示的图片;
            .build();

    public static DisplayImageOptions mOptionsNoRound = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public static DisplayImageOptions mOptionsForIndex = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new FadeInBitmapDisplayer(300))
            .build();

    public static DisplayImageOptions mOptionsNoCache = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.default_financier_pic)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.default_financier_pic)       // 设置图片加载或解码过程中发生错误显示的图片
            .build();
    //理财师头像
    public static DisplayImageOptions mOptionsForFinancer = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.default_financier_pic)  // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.default_financier_pic)       // 设置图片加载或解码过程中发生错误显示的图片
            .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                          // 设置下载的图片是否缓存在SD卡中
            .imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public void displayImage(String path, ImageView view) {
        imageLoader.displayImage(path, view, mOptions);
    }

    public void displayImage(String path, ImageView view, DisplayImageOptions options) {
        imageLoader.displayImage(path, view, options);
    }

    public void displayImage(String path, ImageView view, DisplayImageOptions options, ImageLoadingListener listener) {
        imageLoader.displayImage(path, view, options, listener);
    }

    public int getmWindowWidth() {
        return mWindowWidth;
    }

    public int getmWindowHeight() {
        return mWindowHeight;
    }

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        public static AnimateFirstDisplayListener listener;

        public static AnimateFirstDisplayListener getInstance() {
            if (null == listener) {
                listener = new AnimateFirstDisplayListener();
            }
            return listener;
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 1000);
                    displayedImages.add(imageUri);
                }
            }
        }
    }


    public static Bitmap getSmallBitmap2(Context context, InputStream is, int width, int height) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] bys = new byte[1024 * 2];
        try {
            while ((len = is.read(bys)) != -1) {
                outputStream.write(bys);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options;
        try {
            // 尝试读取图片
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.size(), options);

        } catch (Exception ex) {
            Toast.makeText(context.getApplicationContext(), "无法读取该图片，请选择其他图片", Toast.LENGTH_SHORT).show();
            return null;
        }

        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, outputStream.size(), options);
        if (bm == null) {
            return null;
        }
        return bm;
    }


    //壓縮圖片
    public static Bitmap getSmallBitmap(String filePath, int width, int height) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if (bm == null) {
            return null;
        }
//        int degree = readPictureDegree(filePath);
//        bm = rotateBitmap(bm,degree) ;
        FileOutputStream baos = null;
        try {
//            baos = new FileOutputStream(filePath);
//            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bm;
    }

    //獲取圖片要旋轉的度數
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    //旋轉圖片
    public static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(-rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    //計算壓縮比例
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }

    public static Bitmap returnBitMap(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (null == myFileUrl) {
            return null;
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static void diaplayView(final View view, final String url, final int defaultImage) {


        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (view instanceof ImageView) {
                    if (msg.obj != null) {
                        try {
                            Bitmap bitmap = (Bitmap) msg.obj;
                            ((ImageView) view).setImageBitmap(bitmap);
                        } catch (Exception e) {
                            ((ImageView) view).setImageResource(defaultImage);
                        }
                    } else {
                        ((ImageView) view).setImageResource(defaultImage);
                    }
                } else {
                    if (msg.obj != null) {
                        try {
                            Bitmap bitmap = (Bitmap) msg.obj;
                            Drawable drawable = new BitmapDrawable(bitmap);
                            view.setBackgroundDrawable(drawable);
                        } catch (Exception e) {
                            view.setBackgroundResource(defaultImage);
                        }
                    } else {
                        view.setBackgroundResource(defaultImage);
                    }


                }
            }
        };
        if (TextUtils.isEmpty(url)) {
            return;
        }

        new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = returnBitMap(url);
                Message msg = new Message();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        }.start();

    }

    /**
     * base64 图片字符串 转换成 bitmap图片
     *
     * @param string
     * @return
     */
    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存图片
     *
     * @param src    源图片
     * @param file   要保存到的文件
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(Bitmap src, File file) {
        if (src == null || !createOrExistsFile(file)) return false;
        System.out.println(src.getWidth() + ", " + src.getHeight());
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(Bitmap.CompressFormat.JPEG, 100, os);
            if (!src.isRecycled()) src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
}
