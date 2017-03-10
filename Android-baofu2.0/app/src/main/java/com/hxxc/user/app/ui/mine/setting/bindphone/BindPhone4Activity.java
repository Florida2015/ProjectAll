package com.hxxc.user.app.ui.mine.setting.bindphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.hxxc.user.app.Event.AccountSafeEvent;
import com.hxxc.user.app.Event.BindPhoneEvent;
import com.hxxc.user.app.Event.CloseBindPhoneEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyClickButton;
import com.hxxc.user.app.widget.SendCodeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/12/7.
 */

public class BindPhone4Activity extends ToolbarActivity implements SendCodeView.ICode {
    public static final int TYPE_A = 1;//原始账号自定更新
    public static final int TYPE_B = 2;//人工审核更新

    @BindView(R.id.et_phone)
    EditText et_phone;
    //    @BindView(R.id.et_img_code)
//    EditText et_img_code;
    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.code_text)
    SendCodeView code_text;

    @BindView(R.id.button_step)
    MyClickButton button_step;
    private String mPhone;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bindphone4;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("设置手机号");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        button_step.setOnMyClickListener(new MyClickButton.MyClickListener() {
            @Override
            public void onMyClickListener() {
                doNext();
            }
        });

        Map<String, String> params = new HashMap<>();
        params.put("type", "27");
        code_text.initDatas(this, 0, params, new SendCodeView.MyOnClickListener() {

            @Override
            public boolean onPre() {
                return false;
            }

            @Override
            public void onSuccess(String t) {
                ToastUtil.showSafeToast(BindPhone4Activity.this, "验证码发送成功");
                mPhone = et_phone.getText().toString();
            }

            @Override
            public void onFailure(String t) {

            }
        });

    }

    //下一步操作
    private void doNext() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", TYPE_A);
        String oldAccount = intent.getStringExtra("oldAccount");
        String oldCode = intent.getStringExtra("oldCode");

        String phone = et_phone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(mPhone)) {
            toast("请获取验证码");
            return;
        }
        String code = et_code.getText().toString();
        if (TextUtils.isEmpty(code)) {
            toast("请输入验证码");
            return;
        }
        if (!phone.equals(mPhone)) {
            toast("请重新获取验证码");
            return;
        }
        button_step.begin();
        HashMap<String, String> map = new HashMap<>();
        map.put("upMethod", type + "");
        map.put("oldAccount", oldAccount + "");
        map.put("oldCode", oldCode + "");
        map.put("newAccount", phone);
        map.put("newCode", code);
        mApiManager.updateAccout(map, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                button_step.finish();
                startActivity(new Intent(BindPhone4Activity.this, BindPhoneSucessActivity.class));
                SPUtils.geTinstance().setUserName(phone);
                EventBus.getDefault().post(new CloseBindPhoneEvent());
                Midhandler.getUserInfo(new Midhandler.OnGetUserInfo() {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        EventBus.getDefault().post(new BindPhoneEvent());
                        EventBus.getDefault().post(new AccountSafeEvent());
                        finish();
                    }
                });
            }

            @Override
            public void onError() {
                button_step.finish();
            }
        });
    }

    @Override
    public String getMobile() {
        return et_phone.getText().toString();
    }


}
