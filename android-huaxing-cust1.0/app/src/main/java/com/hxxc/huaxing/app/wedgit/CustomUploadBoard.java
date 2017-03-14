package com.hxxc.huaxing.app.wedgit;

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

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.ui.mine.MineInformationActivity;
import com.hxxc.huaxing.app.utils.LogUtil;

/**
 *
 */
public class CustomUploadBoard extends PopupWindow implements OnClickListener {

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
                mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA}, MineInformationActivity.PERMISSION_CAMERA);
                LogUtil.e("takephoto**********************1");
            } else {
                startTakePhone();
                LogUtil.e("takephoto**********************2");
            }
        } else {
            startTakePhone();
        }
    }

    public void startTakePhone() {
        LogUtil.e("takephoto**********************4");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.startActivityForResult(intent, MineInformationActivity.FROM_CAMERA);
        dismiss();
    }

    private void nativeCar() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(intent, MineInformationActivity.FROM_PICTURE);
        dismiss();
    }
}
