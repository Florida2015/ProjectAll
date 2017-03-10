package com.hxxc.user.app.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.huaxiafinance.lc.bottomindicator.IOnTab3Click;
import com.huaxiafinance.lc.bottomindicator.IconTabPageIndicator;
import com.huaxiafinance.lc.bottomindicator.viewpager.CustomViewPager;
import com.hxxc.user.app.ActivityList;
import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.ActionEvent;
import com.hxxc.user.app.Event.DownloadEvent;
import com.hxxc.user.app.Event.ExitLoginEvent;
import com.hxxc.user.app.Event.IMPushEvent;
import com.hxxc.user.app.Event.KICKOUTEvent;
import com.hxxc.user.app.Event.LoginEvent;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.Event.MineEvent;
import com.hxxc.user.app.Event.PushEvent;
import com.hxxc.user.app.Event.UnreadMessageEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AppUpdateBean;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.contract.MainContract;
import com.hxxc.user.app.contract.presenter.MainPresenter;
import com.hxxc.user.app.contract.presenter.UserInfoPresenter;
import com.hxxc.user.app.ui.adapter.MainAdapter;
import com.hxxc.user.app.ui.base.BaseActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.ui.mine.gesture.UnlockGesturePasswordActivity;
import com.hxxc.user.app.ui.mine.invitation.InvitationFriendsActivity;
import com.hxxc.user.app.ui.mine.redpackage.RedPackageActivity;
import com.hxxc.user.app.ui.pager.BasePager;
import com.hxxc.user.app.ui.pager.HomePager;
import com.hxxc.user.app.ui.pager.PagerFactory;
import com.hxxc.user.app.ui.product.ProductDetailActivity;
import com.hxxc.user.app.ui.user.LoginActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.ImUtils;
import com.hxxc.user.app.utils.ImageUtils;
import com.hxxc.user.app.utils.LogUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.AppUpdateDialog;
import com.umeng.message.PushAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.hxxc.user.app.rest.Api.token;
import static com.hxxc.user.app.utils.DisplayUtil.getStatusBarHeight;

/**
 * Created by chenqun on 2016/8/17.
 */
public class MainActivity2 extends BaseActivity implements ViewPager.OnPageChangeListener, MainContract.View, IOnTab3Click {
    public static final int FROMMINEACTIVITY = 3;

    private static final int locationPermission = 12;

    @BindView(R.id.indicator)
    IconTabPageIndicator mIndicator;
    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;

    private String json;
    private BroadcastReceiver receiver;

    public BDLocationListener myListener = new MyLocationListener();
    private MainPresenter mPresenter;
    private Dialog updateDialog;
    private AppUpdateDialog appUpdateDialog;

    private UserInfo mUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setdrawableActivity(this, 0);//初始化并处理statusbar
        ActivityList.addMainActivity(this);
        ButterKnife.bind(this);

        EventBus.getDefault().registerSticky(this);
        init();
    }

    private void init() {
        ImageUtils.getInstance().initScreenParams(this);
        mPresenter = new MainPresenter(this);
        json = SPUtils.geTinstance(this).getUid();

        initViews();

        //刷新缓存数据
        UserInfoPresenter.getDatas();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initOther();
                if (!TextUtils.isEmpty(json)) {
                    //刷新理财师数据
                    Midhandler.refreshFinanceInfo(null);
                }
                initLocationPermission();//获取定位权限
                if (null != mPresenter)
                    mPresenter.subscribe();

                if (0 == mIndicator.getCurrentIndex()) {
                    PagerFactory.getPager(MainActivity2.this, 0).initData();
                    PagerFactory.getPager(MainActivity2.this, 0).isLoading = true;
                }
            }
        }, 700);
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
            LogUtils.e("location*******************************" + e.getMessage());
        }
    }

    @Override
    public boolean onTab3Click() {
        if (TextUtils.isEmpty(json)) {
            Intent in = new Intent(MainActivity2.this, LoginActivity.class);
            in.putExtra("from", FROMMINEACTIVITY);
            startActivity(in);
            return true;
        } else {
            if (HXXCApplication.getInstance().getIsInBackground() && HXXCApplication.getInstance().getLockPatternUtils().isPatternSaved(MainActivity2.this) && null != mUser && mUser.getIsGesturePwd() == 1) {
                LogUtils.e("点击button3");
                Intent pwdIn = new Intent(MainActivity2.this, UnlockGesturePasswordActivity.class);
                startActivity(pwdIn);
                return true;
            }
        }
        return false;
    }

    public void setStatusBarViews(float mAlpha) {
        statusBarView.setAlpha(mAlpha);
    }

    private View statusBarView;

    //定位监听器
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            SPUtils.geTinstance().put(Constants.LASTCITY, location.getCity());
            SPUtils.geTinstance().put(Constants.LASTPROVINCE, location.getProvince());
            SPUtils.geTinstance().put(Constants.Latitude, location.getLatitude() + "");
            SPUtils.geTinstance().put(Constants.Longitude, location.getLongitude() + "");
            mLocationClient.unRegisterLocationListener(myListener);
            mLocationClient.stop();
        }
    }

    private void initOther() {
        if (!TextUtils.isEmpty(json)) {
            // 初始化锁屏工具
            HXXCApplication.getInstance().initLockPatternUtils();
            //设置用户信息提供者
            ImUtils.getInstance().setUserInfoProvider();

            //设置单点登录
            ImUtils.getInstance().setImConnectionListener();
            //获取聊天token，并且打开连接
            getChatToken();

        }
    }

    private void getChatToken() {
        String chatToken = SPUtils.geTinstance().getImToken();
        if (TextUtils.isEmpty(chatToken)) {
            mPresenter.getChatToken();
        } else {
            connectIm(chatToken);
        }
    }

    private void initViews() {
        MainAdapter myAdapter = new MainAdapter(this);
        mViewPager.setAdapter(myAdapter);
//        mViewPager.setOffscreenPageLimit(4);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnTab3ClickListener(this);
    }

    @Override
    protected void onDestroy() {
        ActivityList.removeMainActivity();
//        PagerFactory.getPager(this, mIndicator.getCurrentIndex()).onPause();//在前端显示的pager先onPause后onDestory
        PagerFactory.onDestory();

        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
        mPresenter.unsubscribe();
        mPresenter = null;
        ImUtils.getInstance().onImDisconnection();
        //解除注册
        if (null != receiver) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        final BasePager pager = PagerFactory.getPager(this, position);
        if (!pager.isLoading) {
            pager.isLoading = true;
            pager.initData();
        }
        PagerFactory.getPager(this, position).onResume();
        PagerFactory.getPager(this, mIndicator.getOldeIndex()).onPause();

        switch (position) {
            case 0:
                statusBarView.setBackgroundColor(Color.parseColor("#1f80d1"));
                statusBarView.setAlpha(((HomePager) PagerFactory.getPager(MainActivity2.this, 0)).mAlpha);
                break;
            case 3:
                statusBarView.setBackgroundColor(Color.parseColor("#1f80d1"));
                statusBarView.setAlpha(1);
                break;
            default:
                statusBarView.setBackgroundColor(Color.parseColor("#d5d5d5"));
                statusBarView.setAlpha(1);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //双击退出
    private static long time_exit = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time_exit > 1500) {
            time_exit = System.currentTimeMillis();
            ToastUtil.ToastShort(MainActivity2.this, "再按一次退出");
        } else {
            ActivityList.removeMainActivity();
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PagerFactory.getPager(this, mIndicator.getCurrentIndex()).onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        json = SPUtils.geTinstance().getUid();
        mUser = SPUtils.geTinstance().getUserInfo();
        if (!TextUtils.isEmpty(json) && null != mUser) {
            try {
                LogUtils.e("打开锁屏界面<IsInBackground==" + HXXCApplication.getInstance().getIsInBackground() + "；index==" + mIndicator.getCurrentIndex() + "；hasLock==" + HXXCApplication.getInstance().getLockPatternUtils().isPatternSaved(MainActivity2.this));
                if (HXXCApplication.getInstance().getIsInBackground() && mIndicator.getCurrentIndex() == 3 && HXXCApplication.getInstance().getLockPatternUtils() != null && HXXCApplication.getInstance().getLockPatternUtils().isPatternSaved(MainActivity2.this) && mUser.getIsGesturePwd() == 1) {
                    Intent pwdIn = new Intent(this, UnlockGesturePasswordActivity.class);
                    startActivity(pwdIn);
                    return;
                }
            } catch (Exception e) {
                LogUtils.e("打开锁屏界面失败==" + e.getMessage());
            }
        }
        PagerFactory.getPager(this, mIndicator.getCurrentIndex()).onResume();

    }

    @Override
    public void showUpdateDialog(AppUpdateBean updateBean) {
        updateDialog = new Dialog(MainActivity2.this, R.style.loadingDialogTheme);
        appUpdateDialog = new AppUpdateDialog(MainActivity2.this, updateBean, updateDialog);
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
            }
        }, 400);
    }

    private int num = 0;

    @Override
    public void connectIm(String token) {
        if (getApplicationInfo().packageName.equals(CommonUtil.getCurProcessName(getApplicationContext()))) {
            RongIM.connect(token, connectCallback);
        }
    }

    private static final RongIMClient.ConnectCallback connectCallback = new RongIMClient.ConnectCallback() {
        @Override
        public void onTokenIncorrect() {
            LogUtils.e("2**************************连接失败-token==" + token + "不正确");
            EventBus.getDefault().post(new MainEvent(0).setLoginType(MainEvent.IM_CONNECT));
        }

        @Override
        public void onSuccess(String s) {
            LogUtils.e("2**************************连接成功==" + s);
            SPUtils.geTinstance().setImId(s);
            ImUtils.getInstance().setOnReceiveUnreadCountChangedListener();
        }

        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {
            LogUtils.e("Im连接错误,错误码是--》" + errorCode);
        }
    };

    private void pushConfig() {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                //（需在注册成功后调用）
                mPresenter.updateDevice(PushAgent.getInstance(MainActivity2.this).getRegistrationId());
            }
        });
    }

    @Override
    public void onReflushEnd() {
    }

    public void onEventMainThread(final PushEvent event) {
        LogUtils.e("pushevent==" + event.toString());
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                json = SPUtils.geTinstance().getUid();
                if (!TextUtils.isEmpty(event.page)) {
                    Integer integer = Integer.valueOf(event.page);
                    Intent in = new Intent();
                    switch (integer) {
                        case 0://首页
                            mIndicator.setCurrentItem(0);
                            in.setClass(MainActivity2.this, MainActivity2.class);
                            startActivity(in);
                            break;
                        case 1://产品列表页
                            mIndicator.setCurrentItem(1);
                            in.setClass(MainActivity2.this, MainActivity2.class);
                            startActivity(in);
                            break;
                        case 2://产品详情
                            if (!TextUtils.isEmpty(event.data)) {
                                Intent intent = new Intent(MainActivity2.this, ProductDetailActivity.class);
                                intent.putExtra("pid", event.data);
                                startActivity(intent);
                            }
                            break;
                        case 3://我的
                            if (!TextUtils.isEmpty(json)) {
                                mIndicator.setCurrentItem(3);
                                in.setClass(MainActivity2.this, MainActivity2.class);
                                startActivity(in);
                            } else {
                                in.setClass(MainActivity2.this, LoginActivity.class);
                                in.putExtra("from", FROMMINEACTIVITY);
                                startActivity(in);
                            }
                            break;
                        case 4://我的红包

                            if (!TextUtils.isEmpty(json)) {
                                in.setClass(MainActivity2.this, RedPackageActivity.class);
                                startActivity(in);
                            } else {
                                in.setClass(MainActivity2.this, LoginActivity.class);
                                in.putExtra("from", FROMMINEACTIVITY);
                                in.putExtra("to", Constants.REDPACKAGEACTIVITY);
                                startActivity(in);
                            }
                            break;
                        case 5://活动页
                            if (!TextUtils.isEmpty(event.data)) {
                                in.setClass(MainActivity2.this, AdsActivity.class);
                                in.putExtra("title", "华夏信财");
                                in.putExtra("url", event.data);
                                startActivity(in);
                            }
                            break;
                        case 6://邀请好友页
                            if (!TextUtils.isEmpty(json)) {
                                in.setClass(MainActivity2.this, InvitationFriendsActivity.class);
                                startActivity(in);
                            } else {
                                in.setClass(MainActivity2.this, LoginActivity.class);
                                in.putExtra("from", FROMMINEACTIVITY);
                                in.putExtra("to", Constants.INVITEFRIENDACTIVITY);
                                startActivity(in);
                            }
                            break;
                    }
                }
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        EventBus.getDefault().removeStickyEvent(event);
                    }
                }, 200);
            }
        }, 60);
    }

    public void onEventMainThread(final MainEvent event) {
        if (event.loginType == MainEvent.IM_CONNECT) {
            if (num < 1) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (null != mPresenter)
                            mPresenter.getChatToken();
                    }
                }, 2000);
            }
            num++;

        } else if (event.loginType == MainEvent.FROM_MINE_PAGER) {//设置添加屏幕的背景透明度
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = event.alpha;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(lp);
        } else if (event.loginType == MainEvent.STATUS_BAR) {
            setStatusBarViews(event.alpha);
        } else if (event.loginType == MainEvent.BACK) {
            if (mIndicator.getCurrentIndex() == 3) {
                mIndicator.setCurrentItem(0);
            }
        } else if (event.loginType == MainEvent.FROMFINDPASSWORD) {
            mIndicator.setCurrentItem(event.item);
        } else {
            json = SPUtils.geTinstance().getUid();
            //上传设备token到服务器
            pushConfig();
            initOther();
            //TODO 监听进入后台后是否需要打开密码锁界面
            HXXCApplication.getInstance().setIsInBackground(false);
            if (event.item >= 0) {
                mIndicator.setCurrentItem(event.item);
            }
            if (!TextUtils.isEmpty(event.to)) {

                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        Intent in = new Intent();
                        if (Constants.INVITEFRIENDACTIVITY.equals(event.to)) {
                            in.setClass(MainActivity2.this, InvitationFriendsActivity.class);
                        } else if (Constants.REDPACKAGEACTIVITY.equals(event.to)) {
                            in.setClass(MainActivity2.this, RedPackageActivity.class);
                        }
                        startActivity(in);
                    }
                });
            }
        }
    }


    public void onEventMainThread(ExitLoginEvent event) {
        json = null;
        if (null != mIndicator) {
            mIndicator.setCurrentItem(0);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PagerFactory.onExitLoginEvent(event);
            }
        });
    }

    public void onEventMainThread(final KICKOUTEvent event) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (event.from == KICKOUTEvent.FROM_IM) {
                    ToastUtil.ToastShort(MainActivity2.this, "账号已在其它设备上登录");
                }
                Midhandler.exitLogin(MainActivity2.this);
            }
        }, 1000);
    }

    public void onEventMainThread(final IMPushEvent event) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(json)) {
                    ImUtils.getInstance().startPrivateChat(MainActivity2.this);
                } else {
                    Intent in = new Intent(MainActivity2.this, LoginActivity.class);
                    startActivity(in);
                }

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().removeStickyEvent(event);
                    }
                }, 200);
            }
        }, 60);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("tabIndex", mIndicator.getCurrentIndex());
        super.onSaveInstanceState(outState);
    }

    public void onEventMainThread(LoginEvent event) {
        PagerFactory.onLoginEvent(event);
    }

    public void onEventMainThread(ActionEvent event) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("pid", event.pid);
        startActivity(intent);
    }

    /*mine event 刷新我的资产数据*/
    public void onEventMainThread(MineEvent event) {
        PagerFactory.reflushPage3();
    }

    public void onEventMainThread(DownloadEvent event) {
        appUpdateDialog.setProgress(event.progress);
    }

    public void setdrawableActivity(AppCompatActivity activity, int drawable) {
        View statusView = createStatusBarViewdrawable(activity, drawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(statusView);
//            setRootViewdrawable(activity);
    }

    public void remove(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.removeView(statusBarView);
//        setRootViewdrawable(activity);
    }

    private View createStatusBarViewdrawable(AppCompatActivity activity, int drawable) {
        statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.parseColor("#ff1f80d1"));
        statusBarView.setAlpha(0);
//        statusBarView.setBackgroundDrawable(activity.getResources().getDrawable(drawable));
        return statusBarView;
    }

//    private static void setRootViewdrawable(Activity activity) {
//        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//        rootView.setBackgroundColor(Color.parseColor("#00000000"));
//        rootView.setFitsSystemWindows(true);
//        rootView.setClipToPadding(true);
//    }

    public static final Handler mHandler = new Handler();

    public void onEventMainThread(UnreadMessageEvent event) {
        PagerFactory.onUnreadMessageEvent(this, event.unread);
    }
}
