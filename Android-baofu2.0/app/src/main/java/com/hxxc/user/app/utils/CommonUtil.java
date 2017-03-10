package com.hxxc.user.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hxxc.user.app.HXXCApplication;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.format;
import static com.hxxc.user.app.R.id.tv_num_3;
import static com.hxxc.user.app.R.id.tv_wan;

public class CommonUtil {

    public static String checkMoney2(float money) {
        if (money - ((int) money) > 0) {
            return money + "";
        } else {
            return ((int) money) + "";
        }
    }

    //将数字转成 ***万
    public static String checkMoney(long money) {
        if (money < 10000) {
            return money + "";
        }
        if (((money / 10000f) - (money / 10000)) > 0) {
            String format = myformat.format(money / 10000f);
            int i = format.lastIndexOf(".");
            return format.substring(0, i + 3) + "万";
        } else {
            return money / 10000 + "万";
        }
    }

    public static final java.text.DecimalFormat myformat = new java.text.DecimalFormat("###,##0.0000");

    //将数字转成 3,000,000样式
    public static String moneyType(double d) {
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

    public static String moneyType2(double d) {
        String format = myformat.format(d);
        int i = format.lastIndexOf(".");
        return format.substring(0, i + 3);
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static int confirmGender(String identitycard) {
        int gender = 0;
        if (identitycard.length() == 18) {
            if (Integer.valueOf(identitycard.charAt(16) + "") % 2 == 1) {
                gender = 0;
            } else {
                gender = 1;
            }
        } else if (identitycard.length() == 15) {
            if (Integer.valueOf(identitycard.charAt(14) + "") % 2 == 1) {
                gender = 0;
            } else {
                gender = 1;
            }
        }
        return gender;
    }

    public static int getTotalHeightofListView(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            try {
                View mView = mAdapter.getView(i, null, listView);
                mView.measure(
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                //mView.measure(0, 0);
                totalHeight += mView.getMeasuredHeight();
            } catch (Exception e) {
                totalHeight += DisplayUtil.dip2px(HXXCApplication.getContext(), 110);
            }
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));
        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        totalHeight += (listView.getDividerHeight() * (mAdapter.getCount() - 1));
//        listView.setLayoutParams(params);  
//        listView.requestLayout();      
        return totalHeight;
    }

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

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(final String source) {
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isNotEmojiCharacter(codePoint)) {
                buf.append(codePoint);
            }
        }
        return buf.toString();
    }

    private static boolean isNotEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
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

    public static final boolean isEmailNo(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //校验手机号码
    public static final boolean isMobileNo(String mobiles) {
//        Pattern p = Pattern.compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
        if (mobiles != null && mobiles.length() == 11) {
            return true;
        }
        return false;
    }

    //校验手机号码
    public static final boolean isMobileNoAll(String mobiles) {
        Pattern p = Pattern.compile("^(([0-9]))\\d{10}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //校验密码
    public static final boolean isPWD(String pwd) {
        Pattern p = Pattern.compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    public static final boolean isPWD2(String mobiles) {
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,36}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    // 将数据转化成货币格式
    public static String toMoneyType(Long money) {
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String num = format.format(money);
        String newNum = num.substring(0, num.indexOf("."));
        int a = newNum.indexOf("￥") + 1;
        num = newNum.substring(a);
        return trimA(num);
    }

    public static String toMoneyType(Double money) {
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String num = format.format(money);
        String newNum = num.substring(0, num.indexOf("."));
        int a = newNum.indexOf("￥") + 1;
        num = newNum.substring(a);
        return trimA(num);
    }

    public static String toMoneyType(float money) {
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String num = format.format(money);
        String newNum = num.substring(0, num.indexOf("."));
        int a = newNum.indexOf("￥") + 1;
        num = newNum.substring(a);
        return trimA(num);
    }

    //判断字符串是否只包含数字0-9和‘.’;
    public static String trimA(String a) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < a.length(); i++) {
            if ((a.charAt(i) >= '0' && a.charAt(i) <= '9') || a.charAt(i) == '.' || a.charAt(i) == ',') {
                sb.append(a.charAt(i));
            }
        }
        return sb.toString();
    }

    // 将数据转化成货币格式
    public static String toMoneyType(Float money) {
        // 想要转换成指定国家的货币格式
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        String num = format.format(money);
        String newNum = num.substring(0, num.indexOf("."));
        int a = newNum.indexOf("￥") + 1;
        num = newNum.substring(a);
        return trimA(num);
    }

    /**
     * 创建一个图片
     *
     * @param contentColor 内部填充颜色
     * @param strokeColor  描边颜色
     * @param radius       圆角
     */
    public static GradientDrawable createDrawable(int contentColor,
                                                  int strokeColor, int radius) {
        GradientDrawable drawable = new GradientDrawable(); // 生成Shape
        drawable.setGradientType(GradientDrawable.RECTANGLE); // 设置矩形
        drawable.setColor(contentColor);// 内容区域的颜色
        drawable.setStroke(1, strokeColor); // 四周描边,描边后四角真正为圆角，不会出现黑色阴影。如果父窗体是可以滑动的，需要把父View设置setScrollCache(false)
        drawable.setCornerRadius(radius); // 设置四角都为圆角
        return drawable;
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
            hexString.append(hex.toLowerCase());
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

    public static String getStringDate(Date date) {
        Date currentTime = date;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static Date parseDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = formatter.parse(date);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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

        Drawable normal = drawable;
        Drawable pressed = createDrawable(drawable, paint);
        bg.addState(new int[]{android.R.attr.state_pressed,}, pressed);
        bg.addState(new int[]{android.R.attr.state_focused,}, pressed);
        bg.addState(new int[]{android.R.attr.state_selected}, pressed);
        bg.addState(new int[]{}, normal);
        return bg;
    }

    public static Bitmap getImageFromAssetsFile(Context ct, String fileName) {
        Bitmap image = null;
        AssetManager am = ct.getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    // public static <Params, Progress, Result> void executeAsyncTask(
    // AsyncTask<Params, Progress, Result> task, Params... params) {
    // if (Build.VERSION.SDK_INT >= 11) {
    // task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    // } else {
    // task.execute(params);
    // }
    // }

    public static String getUploadtime(long created) {
        StringBuffer when = new StringBuffer();
        int difference_seconds;
        int difference_minutes;
        int difference_hours;
        int difference_days;
        int difference_months;
        long curTime = System.currentTimeMillis();
        difference_months = (int) (((curTime / 2592000) % 12) - ((created / 2592000) % 12));
        if (difference_months > 0) {
            when.append(difference_months + "月");
        }

        difference_days = (int) (((curTime / 86400) % 30) - ((created / 86400) % 30));
        if (difference_days > 0) {
            when.append(difference_days + "天");
        }

        difference_hours = (int) (((curTime / 3600) % 24) - ((created / 3600) % 24));
        if (difference_hours > 0) {
            when.append(difference_hours + "小时");
        }

        difference_minutes = (int) (((curTime / 60) % 60) - ((created / 60) % 60));
        if (difference_minutes > 0) {
            when.append(difference_minutes + "分钟");
        }

        difference_seconds = (int) ((curTime % 60) - (created % 60));
        if (difference_seconds > 0) {
            when.append(difference_seconds + "秒");
        }

        return when.append("前").toString();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Long date) {
        if (null != date) {
            Date date1 = new Date();
            date1.setTime(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date1);
        }
        return "";
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate1(Date date) {
        if (null != date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
        return "";
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate1(Long date) {
        if (null != date) {
            Date date1 = new Date();
            date1.setTime(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date1);
        }
        return "";
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
        if (!isNum.matches() || isNum_a.matches() || isNum_b.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 添加  ，
     *
     * @param
     * @return
     */
    public static String getMoneyGap(String moneys) {
        String money = "";

        if (TextUtils.isEmpty(moneys)) return money + "";
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
}
