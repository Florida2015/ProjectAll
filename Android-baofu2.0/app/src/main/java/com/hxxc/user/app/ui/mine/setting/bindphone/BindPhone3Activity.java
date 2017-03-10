package com.hxxc.user.app.ui.mine.setting.bindphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.user.app.Event.CloseBindPhoneEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.StringUtil;
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

public class BindPhone3Activity extends ToolbarActivity implements SendCodeView.ICode {


    @BindView(R.id.tv_phone)
    TextView tv_phone;
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


    private String code;
    private UserInfo mUser;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bindphone3;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("身份校验");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mUser = SPUtils.geTinstance().getUserInfo();
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        tv_phone.setText("原手机号：" + StringUtil.getRatStr2(mUser.getMobile()));
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
                ToastUtil.showSafeToast(BindPhone3Activity.this, "验证码发送成功");
                code = "t";
            }

            @Override
            public void onFailure(String t) {
            }
        });

    }

    //下一步操作
    private void doNext() {
        if (TextUtils.isEmpty(code)) {
            toast("请获取验证码");
            return;
        }
        final String sCode = et_code.getText().toString();
        if (TextUtils.isEmpty(sCode)) {
            toast("请输入验证码");
            return;
        }
        button_step.begin();
        HashMap<String, String> map = new HashMap<>();
        map.put("oldAccount", mUser.getMobile());
        map.put("oldCode", sCode);
        mApiManager.updateAccounOnNext(map, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                button_step.finish();
                Intent intent = new Intent(BindPhone3Activity.this, BindPhone4Activity.class);
                intent.putExtra("oldAccount", mUser.getMobile());
                intent.putExtra("oldCode", sCode);
                intent.putExtra("type", BindPhone4Activity.TYPE_A);
                startActivity(intent);
            }

            @Override
            public void onError() {
                button_step.finish();
            }
        });
    }

    @Override
    public String getMobile() {
        return mUser.getMobile();
    }

    public void onEventMainThread(CloseBindPhoneEvent event) {
        finish();
    }
}
