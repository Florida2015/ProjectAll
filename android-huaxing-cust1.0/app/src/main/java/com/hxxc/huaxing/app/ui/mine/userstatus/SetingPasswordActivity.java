package com.hxxc.huaxing.app.ui.mine.userstatus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.KeyBoradUtils;
import com.hxxc.huaxing.app.wedgit.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/27.
 * 设置密码 页
 */

public class SetingPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_seting_password)
    EditText ed_seting_password;
    @BindView(R.id.ed_seting_password_again)
    EditText ed_seting_password_again;
    @BindView(R.id.btn_seting_pass)
    FancyButton btn_seting_pass;

    private Dialog dialog2;

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (ed_seting_password!= null) KeyBoradUtils.hideSoftInput(mContext,ed_seting_password);

    }

    @Override
    public int getLayoutId() {
        return R.layout.user_seting_pass;
    }

    @Override
    public void initView() {
        toolbar_title.setText("设置密码");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


    }

    @Override
    public void initPresenter() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (ed_seting_password!= null) KeyBoradUtils.hideSoftInput(mContext,ed_seting_password);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_seting_pass)
    public void onClick(View view){
        if (view.getId()==R.id.btn_seting_pass&& BtnUtils.isFastDoubleClick()){
            setPassRequest();
        }
    }

    public void setPassRequest(){
        String pass = ed_seting_password.getText().toString().trim();
        String pass_again = ed_seting_password_again.getText().toString().trim();
        if (TextUtils.isEmpty(pass)|| !CommonUtil.isNumerLetter(pass)){
            toast(R.string.text_pass);
            return;
        }
        if (TextUtils.isEmpty(pass_again)|| !CommonUtil.isNumerLetter(pass_again)){
            toast(R.string.text_pass);
            return;
        }
        //请求数据
        showMyDialog();
        Api.getClient().getFindPass(getIntent().getStringExtra("mobile"),getIntent().getStringExtra("code"),pass,pass_again).
                compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<String>>() {
            @Override
            public void onCompleted() {
                closeDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeDialog();
                toast("数据异常");
            }

            @Override
            public void onNext(BaseBean<String> stringBaseBean) {
                closeDialog();
                if (stringBaseBean.isSuccess()){
                    toast("密码设置成功");
                    AppManager.getAppManager().finishActivity(ForgetpasswordActivity.class);
                    finish();
                }else{
                    toast(stringBaseBean.getErrMsg());
                }
            }
        });

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
