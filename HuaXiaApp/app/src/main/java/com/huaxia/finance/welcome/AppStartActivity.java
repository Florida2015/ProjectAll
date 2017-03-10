package com.huaxia.finance.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.framwork.Utils.ApplicationUtils;
import com.framwork.Utils.FileUtils;
import com.framwork.Utils.SharedPreferencesUtils;
import com.huaxia.finance.DMApplication;
import com.huaxia.finance.MenuTwoActivity;
import com.huaxia.finance.R;
import com.huaxia.finance.constant.UserConstant;
import com.umeng.analytics.MobclickAgent;

/**
 * 启动页面
 * Created by houwen.lai on 2015/11/17.
 */
public class AppStartActivity extends FragmentActivity {
    private final String mPageName = AppStartActivity.class.getSimpleName();

    private View view;
    private Context mContext;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_start_activity);

        //用crc32对classes.dex文件的完整性进行校验
//        String apkPath = getPackageCodePath();
//        Long dexCrc = Long.parseLong(getString(R.string.classesdex_crc));
//        try{
//            ZipFile zipfile = new ZipFile(apkPath);
//            ZipEntry dexentry = zipfile.getEntry("classes.dex");
//            Log.i("verification", "classes.dexcrc=" + dexentry.getCrc());
//            if(dexentry.getCrc() != dexCrc){
//                Log.i("verification", "Dexhas been modified!");
//                ToastUtils.showLong("程序被恶意攻击了");
//                finish();
//            }else{
//                Log.i("verification","Dex hasn't been modified!");
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        //用哈希值对整个apk完整性进行校验
//        MessageDigest msgDigest = null;
//        try {
//            msgDigest = MessageDigest.getInstance("SHA-1");
//            byte[] bytes = new byte[1024];
//            int byteCount;
//            FileInputStream fis = new FileInputStream(new File(apkPath));
//            while ((byteCount = fis.read(bytes)) > 0)
//            {
//                msgDigest.update(bytes, 0, byteCount);
//            }
//            BigInteger bi = new BigInteger(1, msgDigest.digest());
//            String sha = bi.toString(16);
//            fis.close();
//            //这里添加从服务器中获取哈希值然后进行对比校验
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        mContext = this;
        view =  findViewById(R.id.linear_app_start);
        splash(view);

        DMApplication.devicedmessage = ApplicationUtils.getDeviceInfo(this);

        FileUtils.getInstance().makeFolder(UserConstant.pathProject);
    }

    private void splash(final View view) {
        if (view == null) {
            return;
        }
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(1500);
        aa.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                toTurn();
            }
        });
        view.startAnimation(aa);
    }

    private void toTurn() {//
        if(SharedPreferencesUtils.getInstanse().getFirstUser(this)) {
            startActivity(new Intent(AppStartActivity.this,MenuTwoActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.hold);
            this.finish();
        }else{
            startActivity(new Intent(AppStartActivity.this,GuideActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.hold);
            this.finish();
        }
    }

}
