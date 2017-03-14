package com.hxxc.huaxing.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {


    /**
     * Check if a network available
     */
    public static boolean isNetworkAvailable2(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                connected = ni.isConnected();
            }
        }
        return connected;
    }

    public static final java.text.DecimalFormat myformat = new java.text.DecimalFormat("###,##0.0000");

    //将数字转成 3,000,000样式
    public static String toMoneyType(double d) {
        String format = myformat.format(d);
        int i = format.lastIndexOf(".");
        String c = format.substring(i + 1, i + 2);
        String b = format.substring(i + 2, i + 3);

        if ("0".equals(c) && "0".equals(b)) {
            return format.substring(0, i);
        } else if (!"0".equals(c) && "0".equals(b)) {
            return format.substring(0, i + 2);
        } else {
            return format.substring(0, i + 3);
        }
    }

    //校验手机号码
    public static final boolean isMobileNo(String mobiles) {
        Pattern p = Pattern.compile("^((1[3,5,7,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //校验手机号码
    public static final boolean isMobileNoAll(String mobiles) {
//		Pattern p = Pattern.compile("^(([0-9]))\\d{10}$");
//		Matcher m = p.matcher(mobiles);
//		return m.matches();
        if (TextUtils.isEmpty(mobiles)) return false;
        return mobiles.length() == 11;
    }

    //校验密码
    public static final boolean isPWD(String pwd) {
        Pattern p = Pattern.compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    //校验密码
    public static final boolean isPWD2(String pwd) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9]{6,20}$");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    /**
     * 数字与字母组合判断
     *
     * @param mobiles
     * @return
     */
    public static final boolean isPWDVerfy(String mobiles) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,36}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断数字字母
     *
     * @param str
     * @return
     */
    public static boolean isNumerLetter(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Pattern pattern_a = Pattern.compile("[a-zA-Z]*");
        Pattern pattern_b = Pattern.compile("[a-zA-Z0-9]*");
        Matcher isNum = pattern.matcher(str);
        Matcher isNum_a = pattern_a.matcher(str);
        Matcher isNum_b = pattern_b.matcher(str);
        boolean a = isNum.matches();
        boolean b = isNum_a.matches();
        boolean c = isNum_b.matches();
        if ((isPWDVerfy(str) || isNum_a.matches()) && (str.length() >= 6 && str.length() <= 36)) {
            return true;
        }
        return false;
    }

    public static void showKeyBoard(Activity a) {
        View view = a.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
        }
    }

    public static void hideKeyBoard(Activity a) {
        View view = a.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //弹吐司
    public static void showSafeToast(final Activity activity, final String msg) {
        if (Thread.currentThread().getName().equals("main")) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        } else {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //将数据转化成货币格式
    public static String toMoneyType(Long money) {
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String num = format.format(money);
        String newNum = num.substring(0, num.indexOf("."));
        int a = newNum.indexOf("￥") + 1;
        num = newNum.substring(a);
        return trimA(num);
    }

    //将数据转化成货币格式
    public static String toMoneyType(Float money) {
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String num = format.format(money);
        String newNum = num.substring(0, num.indexOf("."));
        int a = newNum.indexOf("￥") + 1;
        num = newNum.substring(a);
        return trimA(num);
    }

    //将数据转化成货币格式
    public static String toMoneyType(Double money) {
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String num = format.format(money);
        String newNum = num.substring(0, num.indexOf("."));
        int a = newNum.indexOf("￥") + 1;
        num = newNum.substring(a);
        return trimA(num);
    }

    public static String trimA(String a) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length(); i++) {
            if ((a.charAt(i) >= '0' && a.charAt(i) <= '9') || a.charAt(i) == '.' || a.charAt(i) == ',') {
                sb.append(a.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 添加  ，
     *
     * @param
     * @return
     */
    public static String getMoneyGap(String moneys) {
        String money = "";

        if (TextUtils.isEmpty(moneys)) return "0.00";
        if (moneys.indexOf(".") > 0)
            money = moneys.substring(0, moneys.indexOf("."));
        else money = moneys;

        StringBuffer text = new StringBuffer(money);
        int index = text.toString().length();
        if (0 < text.length() && text.length() <= 3) {
            return text.toString();
        } else if (3 < text.length() && text.length() <= 6) {
            return text.insert(index - 3, ",").toString();
        } else if (6 < text.length() && text.length() <= 9) {
            text.insert(index - 6, ",").toString();
            index = text.toString().length();
            return text.insert(index - 3, ",").toString();
        } else if (9 < text.length() && text.length() <= 12) {
            text.insert(index - 9, ",").toString();
            index = text.toString().length();
            text.insert(index - 6, ",").toString();
            index = text.toString().length();
            return text.insert(index - 3, ",").toString();
        } else return money + "";
    }

    public static void showInfoDialog(Context context, String message) {
        showInfoDialog(context, message, "提示", "确定", null);
    }

    public static void showInfoDialog(Context context, String message,
                                      String titleStr, String positiveStr,
                                      DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setTitle(titleStr);
        localBuilder.setMessage(message);
        if (onClickListener == null)
            onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            };
        localBuilder.setPositiveButton(positiveStr, onClickListener);
        localBuilder.show();
    }

    public static String md5(String paramString) {
        String returnStr;
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramString.getBytes());
            returnStr = byteToHexString(localMessageDigest.digest());
            return returnStr;
        } catch (Exception e) {
            return paramString;
        }
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return 0;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return 1;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo)
                                    || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return 2;
                            }
                            return 3;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */

    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Long date) {
        if (null != date) {
            Date date1 = new Date();
            date1.setTime(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date1);
        }
        return "";
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenPicHeight(int screenWidth, Bitmap bitmap) {
        int picWidth = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
        int picScreenHeight = 0;
        picScreenHeight = (screenWidth * picHeight) / picWidth;
        return picScreenHeight;
    }

    private static Drawable createDrawable(Drawable d, Paint p) {

        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap b = bd.getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(bd.getIntrinsicWidth(),
                bd.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(b, 0, 0, p); // 关键代码，使用新的Paint画原图，

        return new BitmapDrawable(bitmap);
    }

    /**
     * 设置Selector。 本次只增加点击变暗的效果，注释的代码为更多的效果
     */
    public static StateListDrawable createSLD(Context context, Drawable drawable) {
        StateListDrawable bg = new StateListDrawable();
        int brightness = 50 - 127;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        // p.setColor(0x60111111); //Paint ARGB色值，A = 0x40 不透明。RGB222222 暗色

        Drawable normal = drawable;
        Drawable pressed = createDrawable(drawable, paint);
        // p = new Paint();
        // p.setColor(0x8000FF00);
        // Drawable focused = createDrawable(drawable, p);
        // p = new Paint();
        // p.setColor(0x800000FF);
        // Drawable unable = createDrawable(drawable, p);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed,}, pressed);
        bg.addState(new int[]{android.R.attr.state_focused,}, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        // bg.addState(new int[] { android.R.attr.state_enabled,
        // android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_selected}, pressed);
        // View.FOCUSED_STATE_SET
        // bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // // View.WINDOW_FOCUSED_STATE_SET
        // bg.addState(new int[] { android.R.attr.state_window_focused },
        // unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }

    private static boolean hasMethod(String methodName, Method[] method) {
        for (Method m : method) {
            if (methodName.equals(m.getName())) {
                return true;
            }
        }
        return false;
    }

}
