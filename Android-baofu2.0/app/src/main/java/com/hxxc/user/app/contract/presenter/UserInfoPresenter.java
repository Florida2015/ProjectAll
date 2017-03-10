package com.hxxc.user.app.contract.presenter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.contract.UserInfoV;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.StorageUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.utils.UserHeadImages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.hxxc.user.app.widget.CustomUploadBoard.FROM_CAMERA;
import static com.hxxc.user.app.widget.CustomUploadBoard.FROM_PICTURE;

/**
 * Created by chenqun on 2016/10/27.
 */

public class UserInfoPresenter extends RxBasePresenter {

    private  UserInfoV mView;
    private String fileName;

    public UserInfoPresenter(UserInfoV view) {
        mView = view;
    }

    @Override
    public void subscribe() {
//        getUserInfo2();
    }
    @Override
    public void unsubscribe() {
        mView = null;
        super.unsubscribe();
    }

    @Override
    public void doReflush() {
    }

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

                //3压缩图片到本地
                FileOutputStream b = null;
                File file = new File(StorageUtils.getSdcardpath(a));
                if (!file.exists())
                    file.mkdirs();// 创建文件夹
                String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                fileName = StorageUtils.getSdcardpath(a) + name;
                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
                bitmap.recycle();
                //上传图片
                upload(fileName);
            } catch (Exception e) {
                Toast.makeText(a.getApplicationContext(), "上传图片失败", Toast.LENGTH_SHORT).show();
                LogUtil.e(e.getMessage());
            }
        }
    }

    //上传头像
    private void upload(String fileName) {
//        RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), SPUtils.geTinstance().getUid() + "");
//        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), SharedPreUtils.getInstanse().getToken() + "");

        File file = new File(fileName);
        MultipartBody.Part part = null;
        if (file.exists()) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            part = MultipartBody.Part.createFormData("headIcon", file.getName(), fileBody);
        }
        mApiManager.updateUserIcon(SPUtils.geTinstance().getUid(),SPUtils.geTinstance().getToken(),  part, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                Midhandler.getUserInfo(new Midhandler.OnGetUserInfo() {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        mView.onUploadIcon(userInfo);
                    }
                });
            }

            @Override
            public void onError() {
                ToastUtil.ToastShort(HXXCApplication.getInstance(),"头像上传失败");
            }
        });
    }

    private void getUserInfo2() {
        mApiManager.getUserInfoByUid(SPUtils.geTinstance().getUid(), new SimpleCallback<UserInfo>() {

            @Override
            public void onNext(UserInfo userInfo) {
                mView.onReflushUserInfo(userInfo);
                SPUtils.geTinstance().setUserInfo(userInfo);
            }

            @Override
            public void onError() {

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
        parmas.put("uid",SPUtils.geTinstance().getUid());
        mApiManager.updateUserInfoByUid(parmas, new SimpleCallback<String>() {

            @Override
            public void onNext(String s) {
            }

            @Override
            public void onError() {
            }
        });
    }

    public static void getDatas() {
        getCityDatas();
        getDictInfo(Constants.SEX);//性别
        getDictInfo(Constants.MARRIAGE);//婚姻状况
        getDictInfo(Constants.EDUCATION);//教育类型
    }

    private static void getDictInfo(String type) {
        mApiManager.getDictInfo(type, new SimpleCallback<String>() {

            @Override
            public void onNext(String str) {
                if (type.equals("sex")) {
                    SPUtils.geTinstance().put(Constants.Cache_Sex, str);
                } else if (type.equals("marriage")) {
                    SPUtils.geTinstance().put(Constants.Cache_Marriage, str);
                } else {
                    SPUtils.geTinstance().put(Constants.Cache_Education, str);
                }
                LogUtil.e("缓存数据*********************"+str);
            }

            @Override
            public void onError() {
                LogUtil.e("缓存数据失败");
            }
        });
    }

    private static void getCityDatas() {
        mApiManager.getUserAreaList(new SimpleCallback<String>() {
            @Override
            public void onNext(String provinces) {
                LogUtil.e("缓存数据*********************"+provinces);
                SPUtils.geTinstance().put(Constants.Cache_Province, provinces);
            }

            @Override
            public void onError() {
                LogUtil.e("缓存数据失败");
            }
        });
    }
}
