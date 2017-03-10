package com.hxxc.user.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.IndexAds;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.utils.SPUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 欢迎界面
 */
public class SplashActivity extends Activity {

    private ImageView iv_content;

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        EventBus.getDefault().register(this);
        //百度统计
//        StatService.setDebugOn(true);
        // setSendLogStrategy已经@deprecated，建议使用新的start接口
        // 如果没有页面和自定义事件统计埋点，此代码一定要设置，否则无法完成统计
        // 进程第一次执行此代码，会导致发送上次缓存的统计数据；若无上次缓存数据，则发送空启动日志
        // 由于多进程等可能造成Application多次执行，建议此代码不要埋点在Application中，否则可能造成启动次数偏高
        // 建议此代码埋点在统计路径触发的第一个页面中，若可能存在多个则建议都埋点
        StatService.start(this);

        iv_content = (ImageView) findViewById(R.id.iv_content);

        init();
    }

    private void init() {
//        EventBus.getDefault().post(new AppEvent());
        ApiManager.getInstance().getIndexAdsList(Constants.TYPE_Start_Page + "", "2", "1", "5", new SimpleCallback<List<IndexAds>>() {
            @Override
            public void onNext(final List<IndexAds> indexAdses) {
                if (null != indexAdses && indexAdses.size() > 0) {
                    Picasso.with(SplashActivity.this).load(indexAdses.get(0).getRealPictureSourceUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).error(R.drawable.splash_layers).into(iv_content);
                }
            }

            @Override
            public void onError() {
            }
        });
        toApp();
    }

    private void toApp() {
        /**
         * 在欢迎界面停留1秒钟
         */
        boolean is = SPUtils.geTinstance(HXXCApplication.getContext()).get(Constants.isFrist, true);
        iv_content.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (is) {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    SPUtils.geTinstance(HXXCApplication.getContext()).put(Constants.isFrist, false);
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity2.class));
                }
                finish();
            }
        }, 700);
    }

//    public void onEventMainThread(SplashEvent event) {
//        toApp();
//    }


    //        String path = StorageUtils.getSdcardpath(SplashActivity.this) + Constants.SPLASH_NAME;
//        if (new File(path).exists())
//            Picasso.with(SplashActivity.this).load(path).memoryPolicy(MemoryPolicy.NO_STORE,MemoryPolicy.NO_CACHE).error(R.drawable.splash_layers).placeholder(R.drawable.splash_layers).into(iv_content);
//
//        Midhandler.getAds(Constants.TYPE_Start_Page, new Midhandler.OnGetAds() {
//            @Override
//            public void onNext(List<IndexAds> indexAdses) {
//                if (null != indexAdses && indexAdses.size() > 0) {
//                    try {
//                        Bitmap bitmap = Picasso.with(SplashActivity.this).load(indexAdses.get(0).getRealPictureSourceUrl()).get();
//                        if (bitmap != null) {
//                            iv_content.setImageBitmap(bitmap);
//                            ImageUtils.save(bitmap, new File(path));
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });


    @Override
    public void onBackPressed() {
    }
}
