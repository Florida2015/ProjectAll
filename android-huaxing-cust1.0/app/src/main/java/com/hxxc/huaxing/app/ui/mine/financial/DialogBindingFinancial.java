package com.hxxc.huaxing.app.ui.mine.financial;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.mine.userstatus.LoginActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.RegisterActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.RegisterStatusActivity;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.wedgit.LoadingView;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/10/9.
 * 绑定顾问 对话框
 */

public class DialogBindingFinancial extends Activity implements View.OnClickListener{

    private LinearLayout linear_dialog_binding;
    private Button btn_binding_cancal;
    private Button btn_binding_ok;

    private TextView tv_dialog_text;

    String mobile;
    String code ;
    String pass ;
    String pass_again ;
    String pCode ;
    String fid;

    private Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_binding_financial);

         mobile = getIntent().getStringExtra("mobile");
         code = getIntent().getStringExtra("code");
         pass = getIntent().getStringExtra("pass");
         pass_again = getIntent().getStringExtra("pass_again");
         pCode = getIntent().getStringExtra("pCode");
        fid = getIntent().getStringExtra("fid");

        linear_dialog_binding = (LinearLayout) findViewById(R.id.linear_dialog_binding);
        btn_binding_cancal = (Button) findViewById(R.id.btn_binding_cancal);
        btn_binding_ok = (Button) findViewById(R.id.btn_binding_ok);
        tv_dialog_text = (TextView) findViewById(R.id.tv_dialog_text);
        btn_binding_cancal.setOnClickListener(this);
        btn_binding_ok.setOnClickListener(this);

        String name = getIntent().hasExtra("name")?getIntent().getStringExtra("name"):"";

        tv_dialog_text.setText("确认绑定 "+name+" 顾问作为您的理财师?");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_binding_cancal:
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.btn_binding_ok:
                if (BtnUtils.isFastDoubleClick())RequestData();

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            return true;
        }
        return false;
    }

    public void RequestData(){
        showMyDialog();
        Api.getClient().getRegister(mobile,code,pass,pass_again,fid,pCode).
                compose(RxApiThread.convert()).
                subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                closeDialog();
            }

            @Override
            public void onError(Throwable e) {
                closeDialog();
                Toast.makeText(DialogBindingFinancial.this,"注册异常",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String string) {
                closeDialog();
                BaseBean<UserInfoBean> baseBean = HXXCApplication.getInstance().getGson().fromJson(string,new TypeToken<BaseBean<UserInfoBean>>() {}.getType());

                if (baseBean.isSuccess()){
                    SharedPreUtils.getInstanse().putKeyValue(DialogBindingFinancial.this,UserInfoConfig.FlagLogin,true);

                    AppManager.getAppManager().finishActivity(LoginActivity.class);
                    AppManager.getAppManager().finishActivity(RegisterActivity.class);
                    AppManager.getAppManager().finishActivity(BindingFinancialActivity.class);
                    SharedPreUtils.getInstanse().putKeyValue(DialogBindingFinancial.this,UserInfoConfig.spToken,""+baseBean.getModel().getToken());
                    SharedPreUtils.getInstanse().putKeyValue(DialogBindingFinancial.this,UserInfoConfig.spUid,""+baseBean.getModel().getUid());
                    startActivity(new Intent(DialogBindingFinancial.this, RegisterStatusActivity.class));
                    finish();
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }else {
                    Toast.makeText(DialogBindingFinancial.this,""+baseBean.getErrMsg(),Toast.LENGTH_SHORT).show();
                    finish();
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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
