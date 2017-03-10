package com.hxxc.user.app.ui.mine.setting;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.DownloadEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AppUpdateBean;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.ApplicationUtils;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.widget.AppUpdateDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import rx.Subscription;

/**
 * Created by chenqun on 2016/10/28.
 */

public class AboutUsActivity extends ToolbarActivity {
    @BindView(R.id.app_update)
    RelativeLayout app_update;
    @BindView(R.id.tv_version_text)
    TextView tv_version_text;
    @BindView(R.id.iv_arr)
    ImageView iv_arr;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    private Dialog dialog;
    //    private int lastVersion;
    private int versionCode;
    private Dialog updateDialog;
    private AppUpdateDialog appUpdateDialog;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_aboutus;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("关于我们");
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        try {
//            lastVersion = SPUtils.geTinstance().get(Constants.LASTVERSION, 0);
            versionCode = HXXCApplication.getContext().getPackageManager().getPackageInfo(HXXCApplication.getContext().getPackageName(), 0).versionCode;
            LogUtils.e(";versionCode==" + versionCode);
//            if (lastVersion != 0 && lastVersion <= versionCode) {
//                tv_version_text.setVisibility(View.VISIBLE);
//                iv_arr.setVisibility(View.GONE);
//            } else {
//                tv_version_text.setVisibility(View.GONE);
//                iv_arr.setVisibility(View.VISIBLE);
//            }
            tv_version_text.setVisibility(View.VISIBLE);
            tv_version_text.setText("V" + ApplicationUtils.getVersionName(this));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_3, R.id.app_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_3:
                if (dialog == null) {
                    dialog = new Dialog(this, R.style.loadingDialogTheme);
                    View inflate = View.inflate(this, R.layout.dialog_hint, null);
                    TextView tv_des = (TextView) inflate.findViewById(R.id.tv_des);
                    tv_des.setText("确定要拨打热线？");
                    inflate.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    inflate.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4000-466-600"));
                            if (ActivityCompat.checkSelfPermission(AboutUsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(inflate);
                }
                dialog.show();
                break;
            case R.id.app_update:
                doUpdate();
                break;
        }
    }

    private void doUpdate() {
//        if (lastVersion != 0 && lastVersion <= versionCode) {
//            return;
//        }
        app_update.setEnabled(false);
//        iv_arr.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
        tv_version_text.setVisibility(View.INVISIBLE);
        Subscription s = mApiManager
                .updateApp(ApplicationUtils.getVersionName(this) + "", Constants.UPDATE_ANDROID, new SimpleCallback<AppUpdateBean>() {
                    @Override
                    public void onNext(AppUpdateBean appUpdateBean) {

                        if (null != appUpdateBean) {
//                            SPUtils.geTinstance().put(Constants.LASTVERSION, appUpdateBean.versionsId);
                            showUpdateDialog(appUpdateBean);
                        } else {
//                            iv_arr.setVisibility(View.GONE);
                            app_update.setEnabled(false);
                            tv_version_text.setText("已是最新版");
                            tv_version_text.setVisibility(View.VISIBLE);
                            progressbar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        app_update.setEnabled(true);
                        tv_version_text.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                    }
                });
        addSubscription(s);
    }

    private void showUpdateDialog(AppUpdateBean updateBean) {
        if (null == updateDialog) {
            updateDialog = new Dialog(AboutUsActivity.this, R.style.loadingDialogTheme);
        }
        appUpdateDialog = new AppUpdateDialog(AboutUsActivity.this, updateBean, updateDialog);
        updateDialog.setContentView(appUpdateDialog);
        updateDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        appUpdateDialog.postDelayed(new Runnable() {

            @Override
            public void run() {
                updateDialog.show();
//                iv_arr.setVisibility(View.VISIBLE);
                app_update.setEnabled(true);
                progressbar.setVisibility(View.GONE);
                tv_version_text.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    public void onEventMainThread(DownloadEvent event) {
        appUpdateDialog.setProgress(event.progress);
    }
}
