package com.hxxc.huaxing.app.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.UpdateBean;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;
import com.hxxc.huaxing.app.data.entity.TabEntity;
import com.hxxc.huaxing.app.data.event.ReflushUserInfoEvent;
import com.hxxc.huaxing.app.ui.HuaXingFragment.HuaXingFragment;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener.OnOpenEListener;
import com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener.OnUpdateAppListener;
import com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener.UpdateAppDialogFragment;
import com.hxxc.huaxing.app.ui.mine.MineFragment;
import com.hxxc.huaxing.app.ui.mine.UserInfoPresenter;
import com.hxxc.huaxing.app.ui.mine.account.OpenEAccountActivity;
import com.hxxc.huaxing.app.ui.mine.appupdata.AppUpdataContract;
import com.hxxc.huaxing.app.ui.mine.appupdata.AppUpdataPresenter;
import com.hxxc.huaxing.app.ui.mine.userstatus.LoginActivity;
import com.hxxc.huaxing.app.ui.wealth.WealthFragment;
import com.hxxc.huaxing.app.utils.ApplicationUtils;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

import static com.hxxc.huaxing.app.utils.ApplicationUtils.getStatusBarHeight;

/**
 * Created by Administrator on 2016/9/21.
 * 主页
 * 首页 财富 我的
 */
public class HomeActivity extends BaseActivity<AppUpdataPresenter, AppUpdataContract.Model> implements OnOpenEListener, AppUpdataContract.View, OnUpdateAppListener {
    private static final int locationPermission = 12;
    private final int Request_login = 0x001;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    public boolean isTranslucentBar() {
        return false;
    }

    public static HomeActivity homeActivity = null;

    public static final String TAG = "MainActivity";


    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"首页", "财富", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_n, R.mipmap.tab_wealth_n,
            R.mipmap.tab_user_n
    };
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_s, R.mipmap.tab_wealth_s,
            R.mipmap.tab_user_s
    };
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @BindView(R.id.commonTabLayout)
    CommonTabLayout commonTabLayout;
    @BindView(R.id.rl_content)
    RelativeLayout rl_content;

    private HuaXingFragment huaXingFragment;
    private WealthFragment wealthFragment;
    private MineFragment mineFragment;

    @Override
    public void beforeOnCreate() {
        super.beforeOnCreate();
        setdrawableActivity(this, 0);//初始化并处理statusbar
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void initView() {
        homeActivity = this;
        commonTabLayoutInint();
//        if(DisplayUtil.checkDeviceHasNavigationBar(this)){//检查设备是否有虚拟按键；比如华为手机有
//            int bottomStatusBarHeight = DisplayUtil.getBottomStatusBarHeight(this);//获取虚拟导航栏的高度
//            rl_content.setPadding(0,0,0,bottomStatusBarHeight);
//        }
        UserInfoPresenter.getDatas();//获取用户信息中缓存数据
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initLocationPermission();//获取定位权限
            }
        });
        //App升级
        mPresenter.toAppUpdata(mContext,"home", ApplicationUtils.getVersionCode(this),UserInfoConfig.Type_User);
    }

    private void initLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationPermission);
            } else {
                initLocation();
            }
        } else {
            initLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == locationPermission) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initLocation();
            }
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean flag_login = SharedPreUtils.getInstanse().getKeyValue_b(mContext, UserInfoConfig.FlagLogin);
        UserInfoConfig.getRegisterCode = SharedPreUtils.getInstanse().getKeyValue(mContext, UserInfoConfig.UserRegisterStatus);
        UserInfoConfig.getLogout = SharedPreUtils.getInstanse().getKeyValue(mContext, UserInfoConfig.type_Logout);

        if (flag_login && !TextUtils.isEmpty(UserInfoConfig.getRegisterCode) && UserInfoConfig.getRegisterCode.equals("0")) {
            SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus, "-1");
            UserInfoConfig.getRegisterCode = "-1";
            setCommonTabLayoutIndex(0);
        } else if (flag_login && !TextUtils.isEmpty(UserInfoConfig.getRegisterCode) && UserInfoConfig.getRegisterCode.equals("2")) {
            SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus, "-1");
            UserInfoConfig.getRegisterCode = "-1";
            setCommonTabLayoutIndex(2);
            if (mineFragment != null) {
                mineFragment.onRefresh();
            }
        }else if(!flag_login && !TextUtils.isEmpty(UserInfoConfig.getLogout) && UserInfoConfig.getLogout.equals("1")){
            SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.type_Logout,"0");
            setCommonTabLayoutIndex(0);
        }

    }

    private void commonTabLayoutInint() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        huaXingFragment = HuaXingFragment.newInstance("huaxing");
        wealthFragment = WealthFragment.newInstance("wealth");
        mineFragment = MineFragment.newInstance("mine");

        mFragments.add(huaXingFragment);
        mFragments.add(wealthFragment);
        mFragments.add(mineFragment);

        commonTabLayout.setTabData(mTabEntities, this, R.id.context_fragment, mFragments);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                LogUtil.d("onTabSelect position= " + position);
                if (position == 0) {
//                    setTheme();
                }
                //设置statusbar
                setCommonTabLayoutIndex(position);

            }

            @Override
            public void onTabReselect(int position) {
                LogUtil.d("onTabReselect position= " + position);
                if (position == 0) {

                }
            }
        });

//        commonTabLayout.getNextFocusDownId()

        commonTabLayout.setCurrentTab(0);
        //显示未读红点 eg
//        commonTabLayout.showDot(1);
//        commonTabLayout.hideMsg(1);
        //两位数
//        commonTabLayout.showMsg(0, 55);
//        commonTabLayout.setMsgMargin(0, -5, 5);
        //三位数
//        commonTabLayout.showMsg(1, 100);
//        commonTabLayout.setMsgMargin(1, -5, 5);

    }

    public void setCommonTabLayoutIndex(int index) {
        switch (index) {
            case 0:
                commonTabLayout.setCurrentTab(index);
                statusBarView.setBackgroundColor(Color.parseColor("#d5d5d5"));
                statusBarView.setAlpha(huaXingFragment.mAlpha);
                break;
            case 1:
                commonTabLayout.setCurrentTab(index);
                statusBarView.setBackgroundColor(Color.parseColor("#d5d5d5"));
                statusBarView.setAlpha(1);
                break;
            case 2:
                judgementLogin();
                break;
        }
    }


    private View statusBarView;

    public void setdrawableActivity(Activity activity, int drawable) {
        View statusView = createStatusBarViewdrawable(activity, drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            setRootViewdrawable(activity);
        }
    }

    public void remove(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.removeView(statusBarView);
        setRootViewdrawable(activity);
    }

    private View createStatusBarViewdrawable(Activity activity, int drawable) {
        statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.parseColor("#d5d5d5"));
        statusBarView.setAlpha(0);
//        statusBarView.setBackgroundDrawable(activity.getResources().getDrawable(drawable));
        return statusBarView;
    }

    private static void setRootViewdrawable(Activity activity) {
//        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//        rootView.setBackgroundColor(Color.parseColor("#00000000"));
//        rootView.setFitsSystemWindows(true);
//        rootView.setClipToPadding(true);
    }

    public void setStatusBarViews(float alpha) {
        statusBarView.setAlpha(alpha);
    }

    /**
     * 判断是否登录
     */
    public void judgementLogin() {
        boolean flagLogin = SharedPreUtils.getInstanse().getKeyValue_b(mContext, UserInfoConfig.FlagLogin);
//        flagLogin = true;
        if (flagLogin) {
            commonTabLayout.setCurrentTab(2);
            statusBarView.setBackgroundColor(Color.parseColor("#d5d5d5"));
            statusBarView.setAlpha(1);
            if (mineFragment != null) {
                mineFragment.refreshData();
            }
        } else {
            startActivityForResult(new Intent(mContext, LoginActivity.class), Request_login);
        }
    }

    /**
     * 开通E账号
     *
     * @param resId
     */
    @Override
    public void onOpenEAccount(int resId) {
        //开通E账号
        startActivity(new Intent(mContext, OpenEAccountActivity.class));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("home_onActivityResult");
        if (requestCode == Request_login && resultCode == RESULT_OK) {
            commonTabLayout.setCurrentTab(2);
            statusBarView.setBackgroundColor(Color.parseColor("#d5d5d5"));
            statusBarView.setAlpha(1);
            if (mineFragment != null) {
                mineFragment.refreshData();
            }
        }
        LogUtil.d("home_onActivityResult userinfo");
        if (requestCode == UserInfoConfig.Request_user_info && resultCode == RESULT_OK) {

            setCommonTabLayoutIndex(0);
        }
    }

    public LocationClient mLocationClient = null;

    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //设置定位SDK的定位方式
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        try {
            mLocationClient.start();
        } catch (Exception e) {
        }
    }


    //定位监听器
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            SharedPreUtils.getInstanse().putKeyValue(getApplicationContext(), UserInfoConfig.LASTCITY, location.getCity());
            SharedPreUtils.getInstanse().putKeyValue(getApplicationContext(), UserInfoConfig.LASTPROVINCE, location.getProvince());
            SharedPreUtils.getInstanse().putKeyValue(getApplicationContext(), UserInfoConfig.Latitude, location.getLatitude() + "");
            SharedPreUtils.getInstanse().putKeyValue(getApplicationContext(), UserInfoConfig.Longitude, location.getLongitude() + "");
            mLocationClient.unRegisterLocationListener(myListener);
            mLocationClient.stop();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReflushUserInfoEvent event) {
        UserInfoBean bean = event.userInfoBean;
    }

    UpdateBean updateBean;
    UpdateAppDialogFragment updateAppDialogFragment;

    @Override
    public void onUpdateApk(int resId) {
        switch (resId) {
            case R.id.btn_sure://
            case R.id.btn_fouse://强更
                if (updateBean != null) {
                    UserInfoConfig.UPDATE_URL = updateBean.getUrl();
                    Intent mIntent = new Intent();//mContext,DownloadService.class
                    mIntent.setAction("com.huaxia.finance.service.start");//你定义的service的action
                    mIntent.setPackage(getPackageName());//这里你需要设置你应用的包名
                    mIntent.putExtra("url", updateBean.getUrl());
                    mIntent.putExtra("versionName", updateBean.getVersionCode());
                    startService(mIntent);
                    LogUtil.d("api_下载apk,更新=" + updateBean.getUrl());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void toAppUpdata(String type, UpdateBean updateBean) {
        this.updateBean = updateBean;
        if (updateAppDialogFragment != null && updateAppDialogFragment.isVisible()) return;
        //判断是否升级
        if (updateBean.getIsForceUpdate() == 1) {//强更
            UpdateAppDialogFragment updateAppDialogFragment = new UpdateAppDialogFragment().newInstance(true, updateBean.getContents(), updateBean.getVersionCode());
            updateAppDialogFragment.show(getFragmentManager(), "updateAppDialogFragment");
        } else {
            if (updateBean.getIsUpdate().equals("1")) {//!UserInfoConfig.FlagUpdata
                updateAppDialogFragment = new UpdateAppDialogFragment().newInstance(false, updateBean.getContents(), updateBean.getVersionCode());
                updateAppDialogFragment.show(getFragmentManager(), "updateAppDialogFragment");
            }
        }
    }

    @Override
    public void showMsg(String msg) {
        toast(msg);
    }


    private static long time_exit = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time_exit > 2000) {
                time_exit = System.currentTimeMillis();
                ToastUtil.ToastShort(this, "再按一次退出");
                return false;
            } else {
                AppManager.getAppManager().finishAllActivity();
                finish();
                return true;
            }
        }
        return false;
    }

}
