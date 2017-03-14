package com.hxxc.huaxing.app.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.ui.home.HomeActivity;
import com.hxxc.huaxing.app.utils.SharedPreUtils;


/**
 * Created by Administrator on 2016/9/21.
 * 启动页
 */
public class SplashActivity extends Activity {
    private boolean is;
    private boolean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        b = SharedPreUtils.getInstanse().get(this, UserInfoConfig.isFrist, true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                init();
            }
        }, 1000);
    }

    private void init() {
        if (b) {
            SharedPreUtils.getInstanse().putKeyValue(this, UserInfoConfig.isFrist, false);
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
