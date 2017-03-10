package com.hxxc.user.app.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import com.hxxc.user.app.Event.AccountSafeEvent;
import com.hxxc.user.app.Event.AdsEvent;
import com.hxxc.user.app.Event.MineEvent;
import com.hxxc.user.app.Event.UserInfoEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyClickButton;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 认证界面
 * Created by chenqun on 2016/6/27.
 * <p>
 * 参数 ：from （来自哪个界面）
 * 值：以下四个静态参数
 */
public class AuthenticationActivity extends ToolbarActivity {
    public static final int FROM_MyBankCard = 5;//我的银行卡页面
    public static final int FROM_UserInfo = 6;//用户信息页面
    public static final int FROM_AccountSafe = 7;//账户安全页面
    public static final int FROM_Buy = 8;//购买页面
    public static final int FROM_Action38 = 0x00011111;//购买页面

    @BindView(R.id.name_edit)
    EditText name_edit;
    @BindView(R.id.id_edit)
    EditText id_edit;
    @BindView(R.id.submit_btn)
    MyClickButton submit_btn;


    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("实名认证");
    }

    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        from = getIntent().getIntExtra("from", 0);
        initView();
    }

    private void initView() {
        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter() {

            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        filter[1] = new InputFilter.LengthFilter(20);
        name_edit.setFilters(filter);
        submit_btn.setOnMyClickListener(new MyClickButton.MyClickListener() {

            @Override
            public void onMyClickListener() {
                doAuthentication();
            }
        });
    }

    private void doAuthentication() {
        final String realname = name_edit.getText().toString().trim();
        final String identitycard = id_edit.getText().toString().trim();
        if ("".equals(realname)) {
            ToastUtil.showSafeToast(this, "请填写真实姓名");
            return;
        }
        if ("".equals(identitycard)) {
            ToastUtil.showSafeToast(this, "请填写身份证号");
            return;
        }
        submit_btn.begin();
        int gender = CommonUtil.confirmGender(identitycard);
        Map<String, String> params = new HashMap<>();
        params.put("idCard", identitycard);
        params.put("realName", realname);
        params.put("uid", SPUtils.geTinstance().getUid());

        mApiManager.doAuthentication(params, new SimpleCallback<String>() {
            @Override
            public void onNext(String s) {
                Midhandler.getUserInfo(new Midhandler.OnGetUserInfo() {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        if (FROM_UserInfo == from) {
                            EventBus.getDefault().post(new UserInfoEvent(realname, identitycard));
                        } else if (FROM_AccountSafe == from) {
                            EventBus.getDefault().post(new AccountSafeEvent());
                        }
                        EventBus.getDefault().post(new MineEvent());
                        if (from == FROM_Buy || from == FROM_MyBankCard) {
                            ToastUtil.showSafeToast(AuthenticationActivity.this, "认证成功，请绑定银行卡");
                            goBingCardActivity();
                        } else {
                            ToastUtil.showSafeToast(AuthenticationActivity.this, "认证成功");
                        }

                        if (from == FROM_Action38)
                            EventBus.getDefault().post(new AdsEvent(AdsEvent.TODO_Reflush));

                        submit_btn.finish();
                        AuthenticationActivity.this.finish();
                    }
                });
            }

            @Override
            public void onError() {
                submit_btn.finish();
            }
        });
    }

    private void goBingCardActivity() {
        Intent intent = new Intent(this, BaofuBingCardActivity.class);
        if (from == FROM_Buy) {
            intent.putExtra("type", BaofuBingCardActivity.TYPE_TO_DPAY);
        }
        startActivity(intent);
    }
}
