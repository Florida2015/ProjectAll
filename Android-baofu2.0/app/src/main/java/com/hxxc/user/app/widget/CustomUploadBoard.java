package com.hxxc.user.app.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.hxxc.user.app.R;
import com.hxxc.user.app.utils.LogUtils;

/**
 *
 */
public class CustomUploadBoard extends PopupWindow implements OnClickListener {
    public static final int PERMISSION_CAMERA = 11;
    public static final int FROM_CAMERA = 110;
    public static final int FROM_PICTURE = 111;
    private Activity mActivity;

    public CustomUploadBoard(Activity activity) {
        super(activity);
        this.mActivity = activity;
        initView(activity);
    }

    public void show() {
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_upload_layout, null);
        rootView.findViewById(R.id.take_phone_btn).setOnClickListener(this);
        rootView.findViewById(R.id.native_btn).setOnClickListener(this);
        rootView.findViewById(R.id.close_btn).setOnClickListener(this);
        setContentView(rootView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setTouchable(true);

    }

    @Override
    public void dismiss() {
        mActivity = null;
        super.dismiss();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.take_phone_btn:
                takePhone();
                break;
            case R.id.native_btn:
                nativeCar();
                break;
            case R.id.close_btn:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void takePhone() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mActivity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        PERMISSION_CAMERA);
                LogUtils.e("takephoto**********************1");
            } else {
                startTakePhoto();
                LogUtils.e("takephoto**********************2");
            }
        } else {
            startTakePhoto();
        }
    }

    public void startTakePhoto() {
        LogUtils.e("takephoto**********************4");
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mActivity.startActivityForResult(intent, FROM_CAMERA);
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void nativeCar() {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            mActivity.startActivityForResult(intent, FROM_PICTURE);
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
