package com.hxxc.user.app.ui.user;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.Event.MineEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.financial.BindingFinancialActivity;
import com.hxxc.user.app.ui.financial.SearchFinancialActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.SharedPreUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by houwen.lai on 2016/9/27.
 * 注册状态 页
 */

public class RegisterStatusActivity extends BaseRxActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_register_status_count)
    TextView tv_register_status_count;
    @BindView(R.id.btn_register_home)
    Button btn_register_home;
    @BindView(R.id.btn_register_person)
    Button btn_register_person;


    @Override
    public int getLayoutId() {
        return R.layout.user_register_status;
    }

    @Override
    public void initView() {
        toolbar_title.setText("注册");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        toolbar.setNavigationIcon(null);


//        UserInfoPresenter.getUserInfo();//注册成功刷新数据
        startThread();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK) return true;
        else return super.onKeyDown(keyCode,event);
    }

    @OnClick({R.id.btn_register_home, R.id.btn_register_person})
    public void onClick(View view) {
        if (BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.btn_register_home://返回首页
//                AppManager.getAppManager().finishActivity(SearchFinancialActivity.class);
//                AppManager.getAppManager().finishActivity(BindingFinancialActivity.class);
//                AppManager.getAppManager().finishActivity(RegisterActivity.class);
//                AppManager.getAppManager().finishActivity(LoginActivity.class);
                    finishLoginBingActivity();
                    EventBus.getDefault().post(new MainEvent(0));
                    EventBus.getDefault().post(new MineEvent());//刷新我的界面
                    SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus, "0");
                    finish();
                    break;
                case R.id.btn_register_person://个人中心
//                AppManager.getAppManager().finishActivity(SearchFinancialActivity.class);
//                AppManager.getAppManager().finishActivity(BindingFinancialActivity.class);
//                AppManager.getAppManager().finishActivity(RegisterActivity.class);
//                AppManager.getAppManager().finishActivity(LoginActivity.class);
                    finishLoginBingActivity();
                    EventBus.getDefault().post(new MainEvent(3));
                    EventBus.getDefault().post(new MineEvent());//刷新我的界面
                    SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus, "2");
                    finish();
                    break;
            }
        }
    }

    private void startThread() {
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
        new Thread() {
            @Override
            public void run() {
                int i = 8;
                while (i > 0) {
                    i--;
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = i + "秒";
                    handler.sendMessage(msg);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0://返回首页
                    tv_register_status_count.setText("0秒");
//                    AppManager.getAppManager().finishActivity(SearchFinancialActivity.class);
//                    AppManager.getAppManager().finishActivity(BindingFinancialActivity.class);
//                    AppManager.getAppManager().finishActivity(RegisterActivity.class);
//                    AppManager.getAppManager().finishActivity(LoginActivity.class);
                    finishLoginBingActivity();
                    EventBus.getDefault().post(new MainEvent(0));
                    EventBus.getDefault().post(new MineEvent());//刷新我的界面
                    SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus, "0");
                    finish();
                    break;
                case 1:
                    tv_register_status_count.setText(msg.obj + "");
                    break;
            }
        }
    };

    /**
     *
     */
    public void finishLoginBingActivity() {
        if (LoginActivity.loginActivity != null) {
            LoginActivity.loginActivity.finish();
            LoginActivity.loginActivity = null;
        }
        if (RegisterActivity.registerActivity != null) {
            RegisterActivity.registerActivity.finish();
            RegisterActivity.registerActivity = null;
        }
        if (BindingFinancialActivity.bindingFinancialActivity != null) {
            BindingFinancialActivity.bindingFinancialActivity.finish();
            BindingFinancialActivity.bindingFinancialActivity = null;
        }
        if (SearchFinancialActivity.searchFinancialActivity != null) {
            SearchFinancialActivity.searchFinancialActivity.finish();
            SearchFinancialActivity.searchFinancialActivity = null;
        }

    }
}
