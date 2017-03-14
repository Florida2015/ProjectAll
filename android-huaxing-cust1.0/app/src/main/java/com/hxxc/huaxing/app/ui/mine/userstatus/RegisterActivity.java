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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.AgreementBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.WebActivity;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.mine.financial.BindingFinancialActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.contract.RegisterContract;
import com.hxxc.huaxing.app.ui.mine.userstatus.presenter.RegisterPresenter;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.KeyBoradUtils;
import com.hxxc.huaxing.app.utils.ToastUtil;
import com.hxxc.huaxing.app.wedgit.LoadingView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/26.
 * 注册 页
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter,RegisterContract.Model> implements RegisterContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_register_phone)
    EditText ed_register_phone;
//    @BindView(R.id.ed_register_code_pic)
//    EditText ed_register_code_pic;
    @BindView(R.id.ed_register_code)
    EditText ed_register_code;
//    @BindView(R.id.img_register_pic)
//    ImageView img_register_pic;


    @BindView(R.id.ed_set_pass)
    EditText ed_set_pass;
    @BindView(R.id.ed_set_pass_again)
    EditText ed_set_pass_again;

    @BindView(R.id.ed_set_code)
    EditText ed_set_code;

    @BindView(R.id.tv_send_code)
    TextView tv_send_code;
    @BindView(R.id.progressbar_send_code)
    ProgressBar progressbar_send_code;

    @BindView(R.id.ch_box_register)
    CheckBox ch_box_register;

//    SendCodePresenter sendCodePresenter;//
//    SendCodeContract sendCodeContract;

    String mobile;
    String code;
    String pass;
    String pass_again;
    String pCode;

    private Dialog dialog2;

    public static RegisterActivity registerActivity = null;

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (ed_register_phone!= null) KeyBoradUtils.hideSoftInput(mContext,ed_register_phone);

    }

    @Override
    public int getLayoutId() {
        return R.layout.user_register;
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

        registerActivity = this;
        AppManager.getAppManager().addActivity(this);

//        sendCodePresenter = new SendCodePresenter();
//        ImageLoader.getInstanse(this).DisplayImageNoCache(new StringBuffer(HttpConfig.BASE_URL).append(HttpConfig.HttpImgCode).toString(),img_register_pic,R.drawable.white_e_account);

    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (ed_register_phone!= null) KeyBoradUtils.hideSoftInput(mContext,ed_register_phone);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.img_register_pic,R.id.tv_send_code,R.id.btn_register_r,R.id.tv_register_agreement})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_register_pic://图形验证码
//                if (BtnUtils.isFastDoubleClick()) ImageLoader.getInstanse(this).DisplayImageNoCache(new StringBuffer(HttpConfig.BASE_URL).append(HttpConfig.HttpImgCode).toString(),img_register_pic,R.drawable.white_e_account);
                break;
            case R.id.tv_send_code://发送验证码
                if (BtnUtils.isFastDoubleClick())sendCode();
                break;
            case R.id.btn_register_r://注册
                if (BtnUtils.isFastDoubleClick())doStep();
                break;
            case R.id.tv_register_agreement://服务协议
                if (BtnUtils.isFastDoubleClick())getByAgrementType("2");
                break;
        }
    }

    private void sendCode() {
        mobile = ed_register_phone.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            toast("请输入手机号");
            return;
        }

        tv_send_code.setEnabled(false);
        //请求验证码
        tv_send_code.setVisibility(View.GONE);
        progressbar_send_code.setVisibility(View.VISIBLE);
        mPresenter.SendCode(mContext,mobile, UserInfoConfig.TYPE_Register);

    }

    //服务器返回的验证码
    private String serverCode = "";
    private void doStep(){
        mobile = ed_register_phone.getText().toString();
        if(TextUtils.isEmpty(mobile)){
            toast(R.string.text_register_phone);
            return;
        }

        if(!CommonUtil.isMobileNoAll(mobile)){
            ToastUtil.ToastShort(this, "请输入正确的手机号码");
            return;
        }

        code = ed_register_code.getText().toString();
        if(TextUtils.isEmpty(code)){
            toast(R.string.text_register_code);
            return;
        }

        pass = ed_set_pass.getText().toString().trim();

        pass_again = ed_set_pass_again.getText().toString().trim();

        if (TextUtils.isEmpty(pass)){
            toast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(pass_again)){
            toast("请输入确认密码");
            return;
        }

        if (!CommonUtil.isNumerLetter(pass)){
            toast(mContext.getResources().getString(R.string.text_pass));
            return;
        }
        if (!CommonUtil.isNumerLetter(pass_again)){
            toast(mContext.getResources().getString(R.string.text_pass));
            return;
        }

        if (!pass.equals(pass_again)){
            toast("两次密码输入不一致");
            return;
        }

        pCode = ed_set_code.getText().toString().trim();

        if (!ch_box_register.isChecked()){
            toast("请选择服务协议");
            return;
        }

        //TODO
//        startActivity(new Intent(this,BindingFinancialActivity.class));

        tv_send_code.setEnabled(false);
        //请求注册
        showMyDialog();
        mPresenter.registernext(mContext,mobile,code,pass,pass_again,pCode);

    }


    @Override
    public void toSendCode() {
        tv_send_code.setVisibility(View.VISIBLE);
        progressbar_send_code.setVisibility(View.GONE);
        toast("验证码已发送，请注意查收");
        tv_send_code.setEnabled(false);
        startThread();
    }

    @Override
    public void toRegisterNext() {
        closeDialog();
        tv_send_code.setEnabled(true);
        Intent intent =new Intent(this,BindingFinancialActivity.class);
        intent.putExtra("mobile",mobile);
        intent.putExtra("code",code);
        intent.putExtra("pass",pass);
        intent.putExtra("pass_again",pass_again);
        intent.putExtra("pCode",pCode);

        //注册返回结果
        startActivity(intent);

    }

    @Override
    public void showErrorMessage(String erro) {
        tv_send_code.setVisibility(View.VISIBLE);
        progressbar_send_code.setVisibility(View.GONE);
        closeDialog();
        tv_send_code.setEnabled(true);
        if (!TextUtils.isEmpty(erro))toast(erro);
    }

    private void startThread() {
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
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
                    tv_send_code.setEnabled(true);
                    tv_send_code.setText("点击重新获取");
                    break;
                case 1:
                    tv_send_code.setEnabled(false);
                    break;
                case 2:
                    tv_send_code.setEnabled(false);
                    tv_send_code.setText(msg.obj.toString());
                    break;
                case 3:
                    tv_send_code.setText(msg.obj.toString());
                    break;
            }
        }
    };


    /**
     *
     * 根据协议类型获取协议列表协议类型:
     * 【1、安全保障 2、注册协议 3、风险揭示书 4、保密协议 5、隐私政策、6、关于我们 7.购买协议】
     */
    public void getByAgrementType(String type){
        Api.getClient().getPubSelectByAgrementType(type).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<AgreementBean>>(mContext) {
                              @Override
                              public void onSuccess(List<AgreementBean> agreementBeen) {
                                  if (agreementBeen!=null&&agreementBeen.size()>0){
//                                if (type.equals("1")){
                                      startActivity(new Intent(mContext, WebActivity.class).putExtra("title",agreementBeen.get(0).getAgreementName()).
                                              putExtra("url", agreementBeen.get(0).getMobileViewUrl()).putExtra("flag",false));
//                                }else if(type.equals("6")){
//                                    getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title","关于我们").
//                                     putExtra("url", HttpConfig.Pic_URL+UserInfoConfig.WebUrl_abountus).putExtra("flag",false));
//                                }
                                  }
                              }

                              @Override
                              public void onFail(String fail) {
                                  if (!TextUtils.isEmpty(fail)) ToastUtil.ToastShort(mContext,fail);
                              }
                          }

                );

    }

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
