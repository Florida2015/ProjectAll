package com.hxxc.user.app.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AppUpdateBean;
import com.hxxc.user.app.rest.download.DownloadService;
import com.hxxc.user.app.utils.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUpdateDialog extends FrameLayout implements OnClickListener {

    private AppUpdateBean bean;
    private Context mContext;
    private Dialog dialog;
    private LinearLayout ll_progress;
    private TextView tv_per;
    private ProgressBar progress;
    private LinearLayout ll_button;

    public AppUpdateDialog(Context context, AppUpdateBean updateBean, Dialog dialog) {
        super(context);
        this.dialog = dialog;
        this.mContext = context;
        this.bean = updateBean;
        init();
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        View.inflate(mContext, R.layout.dialog_app_update, this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        TextView tv_version = (TextView) findViewById(R.id.tv_version);
        TextView tv_size = (TextView) findViewById(R.id.tv_size);
        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        TextView tv_content_text = (TextView) findViewById(R.id.tv_content_text);
        TextView btn_cancle = (TextView) findViewById(R.id.btn_cancle);
        TextView btn_sure = (TextView) findViewById(R.id.btn_sure);

        ll_button = (LinearLayout) findViewById(R.id.ll_button);
        ll_progress = (LinearLayout) findViewById(R.id.ll_progress);
        tv_per = (TextView) findViewById(R.id.tv_per);
        progress = (ProgressBar) findViewById(R.id.progress);

        if (null != bean) {
            tv_version.setText("最新版本：V" + bean.versionCode);
//		tv_size.setText("新版本大小："+DateUtil.getPlusTime(bean.createDate));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = new Date();
            date.setTime(bean.createDate);
            tv_time.setText("更新时间：" + sdf.format(date));
            String content = bean.contents.replaceAll(";", "\n");
            tv_content_text.setText(content);

            if (bean.isForceUpdate == 1) {//强制更新
                btn_cancle.setVisibility(View.GONE);
            } else {
                btn_cancle.setVisibility(View.VISIBLE);
            }
        }

        btn_cancle.setOnClickListener(this);
        btn_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancle:
                SPUtils.geTinstance().put(Constants.IGNORE_VERSIONCODE, bean.versionsId);
                dismissDialog();
                break;

            case R.id.btn_sure:

                if (bean.isForceUpdate == 1) {//强制更新
                    //TODO
                    ll_button.setVisibility(GONE);
                    ll_progress.setVisibility(VISIBLE);
                } else {
                    dismissDialog();
                }
                doDownload();
                SPUtils.geTinstance().put(Constants.IGNORE_VERSIONCODE, bean.versionsId);
                break;
        }
    }

    public void setProgress(final int pro) {
        ((Activity) mContext).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progress.setProgress(pro);
                tv_per.setText(pro + "%");
            }
        });
        if (100 == pro) {
            progress.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ll_button.setVisibility(VISIBLE);
                    ll_progress.setVisibility(GONE);
                }
            }, 1000);

        }
    }

    private void dismissDialog() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });
    }

    private void doDownload() {
        Intent intent = new Intent(mContext.getApplicationContext(), DownloadService.class);
        intent.putExtra(Constants.Is_Force_Update, bean.isForceUpdate == 1);
        intent.putExtra(Constants.UPDATEURL, bean.url);
        (mContext).startService(intent);
    }
}
