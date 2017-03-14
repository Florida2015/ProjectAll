package com.hxxc.huaxing.app.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UserHeadImages {
	static final int DEF_IMAGE_SIZE = 250;

    /**
     * 从相册返回结果中取图片
     *
     * @param data
     *            相册返回结果
     * @return 返回结果图片位置
     */
    public static String readBitmapFromAlbumResult(Context context, Intent data) {
        Uri imageUri = data.getData();
        if (imageUri == null) {
            return null;
        } else if (imageUri.toString().startsWith("file:///")) {
            try {
                // 尝试读取图片
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imageUri.getPath(), options);

                return imageUri.getPath();
            } catch (Exception ex) {
                Toast.makeText(context.getApplicationContext(), "无法读取该图片，请选择其他图片", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            String[] projection = { MediaStore.MediaColumns.DATA };
            CursorLoader cursorLoader = new CursorLoader(context, imageUri, projection, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);
            cursor.close();

            return selectedImagePath;
//            return clipAndCompressImage(context, selectedImagePath);
        }
    }

    /**
     * 从相机结果取照片
     *
     * @param data
     *            相机返回结果
     */
    public static String readBitmapFromCameraResult(Context context, Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        if (thumbnail != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            File destination = new File(StorageUtils.getCachePath(context), System.currentTimeMillis() + ".jpg");
            FileOutputStream fo = null;
            try {
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
//                new LogUtil().e("readBitmapFromCameraResult compress failed", e);
            } finally {
                closeSilent(fo);
            }
            thumbnail.recycle();
            return destination.getAbsolutePath();
        } else { // 若未返回图片，则直接读取期图片Uri信息
            return readBitmapFromAlbumResult(context, data);
        }
    }

    /**
     * 压缩裁剪图片为正方形
     *
     * @param originalImagePath
     *            图片路径
     * @return 压缩裁剪后的图片位置
     */
    public static String clipAndCompressImage(Context context, String originalImagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(originalImagePath, options);
        final int REQUIRED_SIZE = DEF_IMAGE_SIZE;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE) {
            scale *= 2;
        }
        int degree = readPictureDegree(originalImagePath);
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(originalImagePath, options);
        // 裁剪成正方形
        if (bm.getWidth() != bm.getHeight()) {
            int size = Math.min(bm.getWidth(), bm.getHeight());

            int x = (bm.getWidth() - size) / 2;
            int y = (bm.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(bm, x, y, size, size);
            if (squaredBitmap != bm) {
                bm.recycle();
            }
            bm = squaredBitmap;
        }
        if (degree != 0) {
            Bitmap bp = rotateImageView(degree, bm);
            if(bp != bm){
                bm.recycle();
            }
            bm = bp;
        }

        File imageSavePos = new File(context.getCacheDir(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageSavePos);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSilent(fos);
        }

        return imageSavePos.getAbsolutePath();
    }

    /**
     * 旋转图片
     *
     * @param angle
     *            angle
     *
     * @param bitmap
     *            bitmap
     *
     * @return Bitmap
     */
    private static Bitmap rotateImageView(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return degree旋转的角度
     */
    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;// SUPPRESS CHECKSTYLE
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;// SUPPRESS CHECKSTYLE
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;// SUPPRESS CHECKSTYLE
                break;
            default:
                break;
            }
        } catch (IOException e) {
            LogUtil.i("readPictureDegree failed");
        }
        return degree;
    }

    /**
     * 裁剪图片为圆形
     * Transformation類是picasso圖片加載庫的轉換類
     */
//    public static class MRoundImageTransformation implements Transformation {
//        @Override
//        public Bitmap transform(Bitmap source) {
//            int size = Math.min(source.getWidth(), source.getHeight());
//
//            int x = (source.getWidth() - size) / 2;
//            int y = (source.getHeight() - size) / 2;
//
//            Bitmap.Config sourceConfig = source.getConfig();
//            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
//            if (squaredBitmap != source) {
//                source.recycle();
//            }
//
//            Bitmap bitmap = Bitmap.createBitmap(size, size, sourceConfig);
//
//            Canvas canvas = new Canvas(bitmap);
//            Paint paint = new Paint();
//            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP,
//                    BitmapShader.TileMode.CLAMP);
//            paint.setShader(shader);
//            paint.setAntiAlias(true);
//
//            float r = size / 2f;
//            canvas.drawCircle(r, r, r, paint);
//
//            squaredBitmap.recycle();
//            return bitmap;
//        }
//
//        @Override
//        public String key() {
//            return "circle";
//        }
//    }

    /**
     * 裁剪图片为正方形
     */
//    public static class MSquareImageTransformation implements Transformation {
//        @Override
//        public Bitmap transform(Bitmap source) {
//            int size = Math.min(source.getWidth(), source.getHeight());
//
//            int x = (source.getWidth() - size) / 2;
//            int y = (source.getHeight() - size) / 2;
//
//            Bitmap.Config sourceConfig = source.getConfig();
//            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
//            if (squaredBitmap != source) {
//                source.recycle();
//            }
//
//            Bitmap bitmap = Bitmap.createBitmap(size, size, sourceConfig);
//
//            Canvas canvas = new Canvas(bitmap);
//            Paint paint = new Paint();
//            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP,
//                    BitmapShader.TileMode.CLAMP);
//            paint.setShader(shader);
//            paint.setAntiAlias(true);
//
////            float r = size / 2f;
////            canvas.drawCircle(r, r, r, paint);
//            canvas.drawRect(0, 0, size, size, paint);
//
//            squaredBitmap.recycle();
//            return bitmap;
//        }
//
//        @Override
//        public String key() {
//            return "square";
//        }
//    }



    private static void closeSilent(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                // null
            }
        }
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

//        int degree = readPictureDegree(filePath);
//        bm = rotateImageView(degree,bm) ;

        return bm;
    }
}
