package com.huaxia.finance.minedm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huaxia.finance.R;

/**
 * 功能： 注册成功界面
 * Created by Administrator on 2015/11/23.
 */
public class RegisterSuccessActivity extends Activity {

    private Button btn_login_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_success_acitvity);
        btn_login_success = (Button)findViewById(R.id.btn_login_success);
        btn_login_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入花虾主页
//                MenuActivity.getInstance().setTab(0);
//                finish();
            }
        });
    }
}
