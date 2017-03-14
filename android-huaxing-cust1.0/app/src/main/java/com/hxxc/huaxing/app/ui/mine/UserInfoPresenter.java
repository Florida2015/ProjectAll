package com.hxxc.huaxing.app.ui.mine;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;
import com.hxxc.huaxing.app.data.event.ReflushUserInfoEvent;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.RxBasePresenter;
import com.hxxc.huaxing.app.ui.mine.userstatus.UserInfoV;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.utils.StorageUtils;
import com.hxxc.huaxing.app.utils.UserHeadImages;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.hxxc.huaxing.app.ui.mine.MineInformationActivity.FROM_CAMERA;
import static com.hxxc.huaxing.app.ui.mine.MineInformationActivity.FROM_PICTURE;

/**
 * 用户信息接口操作类
 * Created by chenqun on 2016/10/12.
 */

public class UserInfoPresenter extends RxBasePresenter {

    private final UserInfoV mView;

    public UserInfoPresenter(UserInfoV view) {
        mView = view;
    }

    @Override
    public void doReflush() {
        getUserInfo2();
    }

    public static void getDatas() {
        getCityDatas();
        getDictInfo(UserInfoConfig.SEX);//性别
        getDictInfo(UserInfoConfig.MARRIAGE);//婚姻状况
        getDictInfo(UserInfoConfig.EDUCATION);//教育类型
    }

    public static void getCityDatas() {
        mApi.getUserCityAreaList().compose(RxApiThread.convert()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onNext(String listBaseBean) {
                LogUtil.d("city_list=" + listBaseBean.toString());
                SharedPreUtils.getInstanse().putKeyValue(HXXCApplication.getInstance(), UserInfoConfig.Cache_Province, listBaseBean);
            }
        });
    }

    public static void getUserInfo() {
        Api.getClient().getUserInfo(SharedPreUtils.getInstanse().getUid()).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<UserInfoBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onNext(BaseBean<UserInfoBean> stringBaseBean) {
                SharedPreUtils.getInstanse().putKeyValue(HXXCApplication.getInstance(), UserInfoConfig.UserInfo, new Gson().toJson(stringBaseBean));
                EventBus.getDefault().post(new ReflushUserInfoEvent(stringBaseBean.getModel()));
            }
        });
    }

    public void getUserInfo2() {
        Api.getClient().getUserInfo(SharedPreUtils.getInstanse().getUid()).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<UserInfoBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onNext(BaseBean<UserInfoBean> stringBaseBean) {
                mView.onReflushUserInfo(stringBaseBean.getModel());
                SharedPreUtils.getInstanse().putKeyValue(HXXCApplication.getInstance(), UserInfoConfig.UserInfo, new Gson().toJson(stringBaseBean));
            }
        });
    }

    public static void getDictInfo(String str) {
        Api.getClient().getDictList(str).compose(RxApiThread.convert()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onNext(String types) {
                if (str.equals("sex")) {
                    SharedPreUtils.getInstanse().putKeyValue(HXXCApplication.getInstance(), UserInfoConfig.Cache_Sex, types);
                } else if (str.equals("marriage")) {
                    SharedPreUtils.getInstanse().putKeyValue(HXXCApplication.getInstance(), UserInfoConfig.Cache_Marriage, types);
                } else {
                    SharedPreUtils.getInstanse().putKeyValue(HXXCApplication.getInstance(), UserInfoConfig.Cache_Education, types);
                }
            }
        });
    }

    //更新用户信息
//    Map<String, String> parmas = new HashMap<>();
//    parmas.put("versionCode", versionCode + "");
//    parmas.put("aaa", "user");

    //sex               性别(sex字典value)
    // birthday         生日(yyyy-MM-dd)
    // hrAreaInfoId     户籍地(用户城市列表的value)
    // domicile         户籍地址
    // education        教育水平(education字典value)
    // marriage         婚姻状态(marriage字典value)
    // occupation       职业
    // liveAreaInfoId   居住地(用户城市列表的value)
    // address          居住地址
    public void updateUserInfo(Map<String, String> parmas) {
        parmas.put("uid", SharedPreUtils.getInstanse().getUid());
        parmas.put("token", SharedPreUtils.getInstanse().getToken());
        parmas.put("channel", "2");
        LogUtil.i(parmas.toString());
        Api.getClientNo().updateUserInfo(parmas).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean<String> stringBaseBean) {
                if (stringBaseBean.isSuccess()) {

                } else {
                    Toast.makeText(HXXCApplication.getInstance(), stringBaseBean.getErrMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String fileName;

    /**
     * 从系统相机或者图库获取到图片，处理图片，上传图片
     *
     * @param a
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void onTakePhotos(Activity a, int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && (requestCode == FROM_CAMERA || requestCode == FROM_PICTURE)) {
            String imagePath;
            Bitmap bitmap;

            //3压缩图片到本地
            FileOutputStream b = null;
            try {
                Uri uri = data.getData();
                ContentResolver cr = a.getContentResolver();

                //1获取到图片资源
                if (requestCode == FROM_CAMERA) {
                    imagePath = UserHeadImages.readBitmapFromCameraResult(a, data);
                } else {
                    imagePath = UserHeadImages.readBitmapFromAlbumResult(a, data);
                }

                //2压缩图片
                if (!TextUtils.isEmpty(imagePath)) {
                    bitmap = UserHeadImages.getSmallBitmap2(a, new FileInputStream(new File(imagePath)), 180, 180);
                } else {
                    bitmap = UserHeadImages.getSmallBitmap2(a, cr.openInputStream(uri), 180, 180);
                }

                if (null == bitmap) {
                    Toast.makeText(a.getApplicationContext(), "无法读取该图片，请选择其他图片", Toast.LENGTH_SHORT).show();
                    return;
                }

                File file = new File(StorageUtils.getSdcardpath(a));
                if (!file.exists())
                    file.mkdirs();// 创建文件夹
                String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                fileName = StorageUtils.getSdcardpath(a) + name;

                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                bitmap.recycle();
                //上传图片
                upload(fileName);
            } catch (Exception e) {
                Toast.makeText(a.getApplicationContext(), "上传图片失败", Toast.LENGTH_SHORT).show();
                Log.e("Exception", e.getMessage(), e);
            } finally {
                try {
                    if (null != b) {
                        b.flush();
                        b.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //上传头像
    private void upload(String fileName) {
        try {
            RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), SharedPreUtils.getInstanse().getUid() + "");
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), SharedPreUtils.getInstanse().getToken() + "");
            LogUtil.e("pic=" + fileName);
            File file = new File(fileName);
            MultipartBody.Part part = null;
            if (file.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                part = MultipartBody.Part.createFormData("headIcon", file.getName(), fileBody);
            }
            Api.getClientNo().updateUserIcon(Api.uid, Api.token, "2", part).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<String>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(HXXCApplication.getInstance(), "头像上传失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(BaseBean<String> stringBaseBean) {
                    if (stringBaseBean.isSuccess()) {
                        mView.onUploadIcon(fileName);
                    } else {
                        Toast.makeText(HXXCApplication.getInstance(), "头像上传失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(HXXCApplication.getInstance(), "头像上传失败", Toast.LENGTH_SHORT).show();
            LogUtil.e("头像上传失败=" + e.getMessage());
        }

    }
}
