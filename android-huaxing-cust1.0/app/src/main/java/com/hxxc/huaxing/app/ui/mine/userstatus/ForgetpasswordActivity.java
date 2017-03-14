package com.hxxc.huaxing.app.ui.mine.userstatus;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.contract.SendCodeContract;
import com.hxxc.huaxing.app.ui.mine.userstatus.presenter.SendCodePresenter;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.KeyBoradUtils;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.utils.ToastUtil;
import com.hxxc.huaxing.app.wedgit.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/26.
 * 忘记密码 页
 */

public class ForgetpasswordActivity extends BaseActivity<SendCodePresenter,SendCodeContract.Model> implements SendCodeContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_forget_phone)
    EditText ed_forget_phone;
    @BindView(R.id.ed_forget_code)
    EditText ed_forget_code;
    @BindView(R.id.tv_forget_send_code)
    TextView tv_forget_send_code;
    @BindView(R.id.progressbar_send_code)
    ProgressBar progressbar_send_code;

    private String phone;
    private String code;

    private Dialog dialog2;
    private String mPhone;

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (ed_forget_phone!= null) KeyBoradUtils.hideSoftInput(mContext,ed_forget_phone);

    }

    @Override
    public int getLayoutId() {
        return R.layout.user_forgetpassword;
    }

    @Override
    public void initView() {
        toolbar_title.setText("验证手机");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (ed_forget_phone!= null) KeyBoradUtils.hideSoftInput(mContext,ed_forget_phone);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_forget_next,R.id.tv_forget_send_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_forget_send_code://验证码
                sendCode();
                break;
            case R.id.btn_forget_next://下一步
                if (BtnUtils.isFastDoubleClick())
                RequestData();
                break;
        }
    }

    private void sendCode() {
        String mobile = ed_forget_phone.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            toast("请输入手机号码");
            return;
        }

        //请求验证码
        tv_forget_send_code.setVisibility(View.GONE);
        progressbar_send_code.setVisibility(View.VISIBLE);
        mPresenter.SendCode(mContext,mobile, UserInfoConfig.TYPE_FindPass);

    }

    public void RequestData(){
        phone = ed_forget_phone.getText().toString().trim();
        code = ed_forget_code.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }

        if(TextUtils.isEmpty(code)){
            toast("请输入验证码");
            return;
        }

        if (!CommonUtil.isMobileNoAll(phone)) {
            ToastUtil.ToastShort(this, "请输入正确的手机号码");
            return;
        }
//        if(!phone.equals(mPhone)){
//            toast("请重新获取验证码");
//            return;
//        }

        SharedPreUtils.getInstanse().putKeyValue(mContext,UserInfoConfig.LoginPhone,phone);
        showMyDialog();
        Api.getClient().getFindPassNext(phone,code).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<String>>() {
            @Override
            public void onCompleted() {
                closeDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeDialog();
                ed_forget_phone.setEnabled(true);
                toast("请求异常");
            }

            @Override
            public void onNext(BaseBean<String> stringBaseBean) {
                closeDialog();
                ed_forget_phone.setEnabled(true);
                if (stringBaseBean.isSuccess()){
                    startActivity(new Intent(mContext,SetingPasswordActivity.class).putExtra("mobile",phone).putExtra("code",code));
                }else{
                    toast(stringBaseBean.getErrMsg());
                }
            }
        });

    }



    @Override
    public void toSendCode() {
        tv_forget_send_code.setVisibility(View.VISIBLE);
        progressbar_send_code.setVisibility(View.GONE);
        mPhone = ed_forget_phone.getText().toString();
        toast("验证码已发送，请注意查收");
        startThread();
    }

    @Override
    public void showErrorMessage(String erro) {
        tv_forget_send_code.setVisibility(View.VISIBLE);
        progressbar_send_code.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(erro))
        toast(erro);
    }

    private void startThread() {
//		Message msg = new Message();
//		msg.what = 1;
//		handler.sendMessage(msg);
        new Thread(){
            @Override
            public void run() {
                int i = 60 ;
                while(i>0){
                    i--;
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = i+"秒后重新发送";
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
                    tv_forget_send_code.setEnabled(true);
                    tv_forget_send_code.setText("点击重新获取");
                    break;
                case 1:
                    tv_forget_send_code.setEnabled(false);
                    break;
                case 2:
                    tv_forget_send_code.setEnabled(false);
                    tv_forget_send_code.setText(msg.obj.toString());
                    break;
                case 3:
                    tv_forget_send_code.setText(msg.obj.toString());
                    break;
            }
        }
    };

    private void showMyDialog() {
        dialog2 = new Dialog(this, R.style.loadingDialogTheme);
        dialog2.setContentView(new LoadingView(this));
        dialog2.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        dialog2.show();
    }
    private void closeDialog() {
        if (null != dialog2) {
            if (dialog2.isShowing()) {
                dialog2.dismiss();
            }
        }
    }
}
