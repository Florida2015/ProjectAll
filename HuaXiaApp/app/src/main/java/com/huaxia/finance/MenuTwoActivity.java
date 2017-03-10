package com.huaxia.finance;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.Utils.ApplicationUtils;
import com.framwork.Utils.FileUtils;
import com.framwork.Utils.MyLog;
import com.framwork.Utils.SharedPreferencesUtils;
import com.framwork.Utils.StringsUtils;
import com.framwork.Utils.ToastUtils;
import com.framwork.asychttpclient.AsyncHttpClient;
import com.framwork.asychttpclient.AsyncHttpResponseHandler;
import com.framwork.asychttpclient.FileAsyncHttpResponseHandler;
import com.framwork.widget.BottomBar;
import com.google.gson.reflect.TypeToken;
import com.huaxia.finance.activities.ActivitiesFragment;
import com.huaxia.finance.constant.DMConstant;
import com.huaxia.finance.constant.StateConstant;
import com.huaxia.finance.constant.UrlConstants;
import com.huaxia.finance.constant.UserConstant;
import com.huaxia.finance.mangemoneydm.ProductListFragment;
import com.huaxia.finance.minedm.LoginActivity;
import com.huaxia.finance.minedm.MineFragment;
import com.huaxia.finance.model.BaseModel;
import com.huaxia.finance.model.UpdateModel;
import com.huaxia.finance.model.UserInfoModel;
import com.huaxia.finance.moredm.MoreFragment;
import com.huaxia.finance.recommenddm.RecommendFragment;
import com.huaxia.finance.request.BaseRequestParams;
import com.huaxia.finance.service.DownloadService;
import com.huaxia.finance.widgetutils.dialog.UpdateAppDialogFragment;
import com.huaxia.finance.widgetutils.dialog.minterface.OnUpdateAppListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import org.apache.http.Header;

import java.io.File;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

/**
 * Created by houwen.lai on 2016/1/19.
 */
public class MenuTwoActivity extends AppCompatActivity implements OnUpdateAppListener {
    private final String mPageName = MenuTwoActivity.class.getSimpleName();

    private static MenuTwoActivity instance = null;

    //    private FrameLayout frame_layout;
    private BottomBar bottomBar;

    private RelativeLayout include_menu;
    public ImageButton img_btn_title_back;
    public TextView tv_title_bar;
    public ImageButton img_btn_title_right;

    // context
    public static Context mContext;
    private static Fragment currunt;

    private RecommendFragment recommendFragment;
    private ProductListFragment productListFragment;
    private ActivitiesFragment activitiesFragment;
    private MineFragment mineFragment;
    private MoreFragment moreFragment;

    private final int ResultCode = 0x001;
    private final int RequestCode = 0x002;
    public static final int RequestCodeHuaxia = 0x000;

    public static MenuTwoActivity getInstance() {
        if (instance != null) {
            return instance;
        } else return null;
    }

    public RelativeLayout getRelative_title_bar() {
        return (RelativeLayout) findViewById(R.id.relative_title_bar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onDestroy() {
//        unbindService(conn);
        super.onDestroy();
    }

    private int index_temp = 0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RequestCode && resultCode == RESULT_OK) {
            setTab(index_temp);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public PushAgent mPushAgent;
    public IUmengRegisterCallback mRegisterCallback = new IUmengRegisterCallback() {

        @Override
        public void onRegistered(String registrationId) {
            // TODO Auto-generated method stub
            MyLog.d("push_onRegistered="+registrationId);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        instance = this;
        mContext=this;
        DMConstant.flagHuaxia = true;
        DMConstant.flagMangeMoney = true;
        DMConstant.flagMine = true;
        DMConstant.flagMore = true;

        index_temp = getIntent().getIntExtra("index",0);

        DMApplication.getInstance().isNoticeFlag = false;

        mPushAgent = PushAgent.getInstance(this);
        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);//呼吸灯
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);//振动
        mPushAgent.onAppStart();
//开启推送并设置注册的回调处理
        mPushAgent.enable(mRegisterCallback);
//        mPushAgent.enable();

        if (TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_token))) {
            DMApplication.token = "";
            DMApplication.isLoginFlag = false;
        } else {
            DMApplication.token = SharedPreferencesUtils.getInstanse().getKeyValue(this, UserConstant.key_token);
            DMApplication.isLoginFlag = true;
        }
        findViews();
    }

    private void findViews() {
        include_menu=  (RelativeLayout)findViewById(R.id.relative_title_bar);
//        relative_title_bar = (RelativeLayout) findViewById(R.id.relative_title_bar);
        img_btn_title_back = (ImageButton) findViewById(R.id.img_btn_title_back);
        tv_title_bar = (TextView) findViewById(R.id.tv_title_bar);
        tv_title_bar.setTextColor(getResources().getColor(R.color.black_3333));
        img_btn_title_right = (ImageButton) findViewById(R.id.img_btn_title_right);
        img_btn_title_back.setVisibility(View.GONE);
        img_btn_title_right.setVisibility(View.GONE);

        recommendFragment = new RecommendFragment();
        productListFragment = new ProductListFragment();
        activitiesFragment = new ActivitiesFragment();
        mineFragment = new MineFragment();
        moreFragment = new MoreFragment();

        currunt = moreFragment;

        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setOnItemChangedListener(new BottomBar.OnItemChangedListener() {

            @Override
            public void onItemChanged(int index) {
                // TODO Auto-generated method stub
                showDetails(index);
//                saveWebDate(index);
            }
        });

        setTab(index_temp);
//        saveWebDate(0);

//        getTokenValied();//判断token是否有效

//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        UserConstant.mhight=wm.getDefaultDisplay().getHeight();//屏幕高度

        UserConstant.bhight = ApplicationUtils.getStatusBarHeight(this);
        MyLog.d("api_statusbar_hight=" + UserConstant.bhight);

        //app更新
//        UmengUpdateAgent.setDefault();
//        UpdateConfig.setDebug(true);
//        UmengUpdateAgent.setUpdateAutoPopup(true);
//        UmengUpdateAgent.setUpdateOnlyWifi(false);
//        UpdateExampleConfig.setUpdateOnlyWifi(false);
//        UmengUpdateAgent.setDeltaUpdate(false);//全量更新
//        //UpdateStatus.STYLE_DIALOG
//        UmengUpdateAgent.setUpdateUIStyle(UpdateStatus.STYLE_DIALOG);//UpdateStatus.STYLE_NOTIFICATION
//        UmengUpdateAgent.update(this);
        FileUtils.getInstance().makeFolder(UserConstant.pathProject);

        String device_token = UmengRegistrar.getRegistrationId(this);
        MyLog.d("push_device_token=" + device_token);
        RequestUpDate(false);
        setUserHttpRequest();

        MyLog.d("api___=="+getDeviceInfo(this));

    }

    public void setTab(int index) {
        if (instance!=null){
            bottomBar.setButtomBarId(index);
            showDetails(index);
        }
    }

    public void removeFragment(int index) {
        Fragment rfragment = null;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction().setTransition(
                        TRANSIT_FRAGMENT_FADE);
        switch (index) {
            case 0://
                rfragment = recommendFragment;
                break;
            case 1://
                rfragment = productListFragment;
                break;
            case 2://
                rfragment = activitiesFragment;
                break;
            case 3://
                rfragment = mineFragment;
                break;
            case 4://
                rfragment = moreFragment;
                break;
        }
        if (rfragment.isAdded()) {
            transaction.remove(rfragment).commitAllowingStateLoss();
        }
    }

    public void showDetails(int index) {
        switch (index) {
            case 0://推荐  页
                index_temp = 0;
//                if (BtnUtils.isFastDoubleClick()) return;
                include_menu.setVisibility(View.GONE);
                switchContent(recommendFragment);
                break;
            case 1://投资 页面
                index_temp = 1;
//                if (BtnUtils.isFastDoubleClick()) return;
                include_menu.setVisibility(View.VISIBLE);
                tv_title_bar.setText("产品列表");
                switchContent(productListFragment);
                break;
            case 2://活动 页面
                index_temp = 2;
                include_menu.setVisibility(View.VISIBLE);
                tv_title_bar.setText("活动");
                switchContent(activitiesFragment);
                break;
            case 3://我的  页面
//                if (BtnUtils.isFastDoubleClick()) return;
                include_menu.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(SharedPreferencesUtils.getInstanse().getKeyValue(this,UserConstant.key_token))) {
                    tv_title_bar.setText("我的");
                    index_temp = 3;
                    switchContent(mineFragment);
                } else {//跳转登录界面
                    startActivityForResult(new Intent(MenuTwoActivity.this, LoginActivity.class).putExtra("flagLogin", "menu"), RequestCode);
                    bottomBar.setButtomBarId(index_temp);
                }
                break;
            case 4://更多  页面
                index_temp = 4;
//                if (BtnUtils.isFastDoubleClick()) return;
                include_menu.setVisibility(View.VISIBLE);
                tv_title_bar.setText("更多");
                switchContent(moreFragment);//
                break;
        }
    }
//    android.support.v4.app.FragmentTransaction transaction;

    public void switchContent(Fragment to) {
        Fragment from;
        if (currunt != null && currunt != to) {
            from = currunt;
            currunt = to;//this.getSupportFragmentManager()
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction().setTransition(
                            TRANSIT_FRAGMENT_FADE);
            if (!to.isAdded()) { // 先判断是否被add过
                if (!from.isAdded()) {
                    transaction.add(R.id.frame_content, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(from).add(R.id.frame_content, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                }
//                transaction.hide(from).add(R.id.frame_content, to).commitAllowingStateLoss(); //
                // 隐藏当前的fragment，add下一个到Activity中
            } else {
                //commit(x)可能报异常：http://www.cnblogs.com/zgz345/archive/2013/03/04/2942553.html
                transaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 返回键退回
            DMConstant.flagCode = false;
            DMApplication.getInstance().isNoticeFlag = false;
            MobclickAgent.onKillProcess(mContext);
            finish();
        }
        return true;
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onDismiss() {

    }

    @Override
    public void onUpdateApk(int resId) {
        switch (resId){
            case R.id.btn_update_fouse://
            case R.id.btn_update_now://强制更新更新
                if (updateModel!=null){
                    DMConstant.UPDATE_URL = updateModel.getUrl();
                    Intent mIntent = new Intent();//mContext,DownloadService.class
                    mIntent.setAction("com.huaxia.finance.service.start");//你定义的service的action
                    mIntent.setPackage(getPackageName());//这里你需要设置你应用的包名
                    mIntent.putExtra("url", updateModel.getUrl());
                    mIntent.putExtra("versionName", updateModel.getVersionName());
                    startService(mIntent);
                    MyLog.d("api_下载apk,更新=" + updateModel.getUrl());

//                    String murl = updateModel.getUrl();
//                    Uri uri = Uri.parse(murl);
//                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(it);
                }
                break;
            default:
                // 返回键退回
                DMConstant.flagCode = false;
                DMApplication.getInstance().isNoticeFlag = false;
                finish();
                break;
        }
    }

    UpdateModel updateModel;
    /**
     *
     */
    public void RequestUpDate(final boolean flag){
        final BaseRequestParams params = new BaseRequestParams();
        params.put("type","1");//app类型 1是android 0 是IOS
        params.put("versionCode", "" + ApplicationUtils.getVersionCode(this));
        StringBuffer url = new StringBuffer(UrlConstants.urlBase);
        url.append(UrlConstants.urlUiUpdateApp);
        MyLog.d("api_url=" + url.toString() + "_code=" + ApplicationUtils.getVersionCode(this));
        DMApplication.getInstance().getHttpClient(this).get(this, url.toString().trim(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String resultDate = StringsUtils.getBytetoString(responseBody, "UTF-8");
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + resultDate);
                    if (statusCode == 200) {
                        BaseModel<UpdateModel> baseModel = DMApplication.getInstance().getGson().fromJson(resultDate, new TypeToken<BaseModel<UpdateModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            updateModel = baseModel.getData();
//                            updateModel.setContents(getResources().getString(R.string.text_update));
//                            updateModel.setIsForceUpdate(0);
                            if (updateModel.getIsForceUpdate() == 0) {

                                UpdateAppDialogFragment updateAppDialogFragment = new UpdateAppDialogFragment().newInstance(updateModel.getContents(), true);
                                updateAppDialogFragment.show(getFragmentManager(), "updateAppDialogFragment");

                            } else {
                                UpdateAppDialogFragment updateAppDialogFragment = new UpdateAppDialogFragment().newInstance(updateModel.getContents(), false);
                                updateAppDialogFragment.show(getFragmentManager(), "updateAppDialogFragment");

//                                showUpdate(mContext,"",baseModel.getData().getContents());
                            }
                        } else {
                            if (flag&&!TextUtils.isEmpty(baseModel.getMsg())) ToastUtils.showShort(baseModel.getMsg());
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");

            }
        });
    }

    /**
     * 用户信息
     */
    public void setUserHttpRequest(){
        BaseRequestParams params = new BaseRequestParams();
        String url = UrlConstants.urlBase+UrlConstants.urlAccountUserInfo;
        MyLog.d("api_=" + url);
        DMApplication.getInstance().getHttpClient(this).post(this, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String reqUrl, int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = StringsUtils.getBytetoString(responseBody);
                    MyLog.d("api_onSuccess=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=" + StringsUtils.getBytetoString(responseBody, "UTF-8"));
                    if (statusCode == 200) {
                        BaseModel<UserInfoModel> baseModel = DMApplication.getInstance().getGson().fromJson(result, new TypeToken<BaseModel<UserInfoModel>>() {
                        }.getType());
                        if (baseModel.getStatus().equals(StateConstant.Status_success)) {
                            if (mPushAgent != null) {
                                MyLog.d("push_=_id=" + mPushAgent.getRegistrationId());
//                                mPushAgent.addExclusiveAlias(baseModel.getData().getAccountId(), UserConstant.AliasHuaxia);
                                mPushAgent.setExclusiveAlias(baseModel.getData().getAccountId(), UserConstant.AliasHuaxia);
                            }
                        } else if (baseModel.getStatus().equals(StateConstant.Status_login)) {
                            DMApplication.isLoginFlag = false;
                            DMApplication.token = "";
                            SharedPreferencesUtils.getInstanse().putKeyValue(MenuTwoActivity.this, UserConstant.key_token, "");

                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(String reqUrl, int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("api_onFailure=" + reqUrl + "\nstatusCode=" + statusCode + "\nrespones=");

            }
        });
    }

    @SuppressLint("NewApi")
    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
//            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
//                result = true;
//            }
        } else {
            PackageManager pm = context.getPackageManager();

            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = null;

            if (checkPermission(context, android.Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();

            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }


            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
