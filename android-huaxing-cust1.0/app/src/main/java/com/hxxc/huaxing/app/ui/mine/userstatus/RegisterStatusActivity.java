package com.hxxc.huaxing.app.ui.mine.userstatus;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.home.HomeActivity;
import com.hxxc.huaxing.app.ui.mine.UserInfoPresenter;
import com.hxxc.huaxing.app.ui.mine.financial.BindingFinancialActivity;
import com.hxxc.huaxing.app.ui.mine.financial.SearchFinancialActivity;
import com.hxxc.huaxing.app.utils.SharedPreUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/27.
 * 注册状态 页
 */

public class RegisterStatusActivity extends BaseActivity {

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
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        startThread();

        UserInfoPresenter.getUserInfo();//注册成功刷新数据
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @OnClick({R.id.btn_register_home,R.id.btn_register_person})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_register_home://返回首页
                finishLoginBingActivity();
//                AppManager.getAppManager().finishActivity(SearchFinancialActivity.class);
//                AppManager.getAppManager().finishActivity(BindingFinancialActivity.class);
//                AppManager.getAppManager().finishActivity(LoginActivity.class);
                SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus,"0");
                UserInfoConfig.getRegisterCode = "0";

                finish();
                break;
            case R.id.btn_register_person://个人中心
                finishLoginBingActivity();
//                AppManager.getAppManager().finishActivity(SearchFinancialActivity.class);
//                AppManager.getAppManager().finishActivity(BindingFinancialActivity.class);
//                AppManager.getAppManager().finishActivity(LoginActivity.class);
                SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus,"2");
                UserInfoConfig.getRegisterCode = "2";
                finish();
                break;
        }
    }

    private void startThread() {
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
        new Thread(){
            @Override
            public void run() {
                int i = 8 ;
                while(i>0){
                    i--;
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = i+"秒";
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
                case 0:
                    tv_register_status_count.setText("0秒");
//                    btn_register_home.setEnabled(true);
//                    btn_register_person.setEnabled(true);
                    finishLoginBingActivity();
//                    AppManager.getAppManager().finishActivity(SearchFinancialActivity.class);
//                    AppManager.getAppManager().finishActivity(BindingFinancialActivity.class);
//                    AppManager.getAppManager().finishActivity(LoginActivity.class);
                    SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.UserRegisterStatus,"2");
                    UserInfoConfig.getRegisterCode = "2";
                    finish();
                    break;
                case 1:
                    tv_register_status_count.setText(msg.obj+"");
//                    btn_register_home.setEnabled(false);
//                    btn_register_person.setEnabled(false);
                    break;
            }
        }
    };

    /**
     *
     */
    public void finishLoginBingActivity(){
        if (LoginActivity.loginActivity!=null){
            LoginActivity.loginActivity.finish();
            LoginActivity.loginActivity=null;
        }
        if (RegisterActivity.registerActivity!=null){
            RegisterActivity.registerActivity.finish();
            RegisterActivity.registerActivity=null;
        }
        if (BindingFinancialActivity.bindingFinancialActivity!=null){
            BindingFinancialActivity.bindingFinancialActivity.finish();
            BindingFinancialActivity.bindingFinancialActivity=null;
        }
        if (SearchFinancialActivity.searchFinancialActivity!=null){
            SearchFinancialActivity.searchFinancialActivity.finish();
            SearchFinancialActivity.searchFinancialActivity=null;
        }

    }

}
