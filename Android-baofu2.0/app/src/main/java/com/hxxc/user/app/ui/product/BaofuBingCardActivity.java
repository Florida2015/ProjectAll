package com.hxxc.user.app.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.BankCardEvent;
import com.hxxc.user.app.Event.BindBankEvent;
import com.hxxc.user.app.Event.InvestEvent;
import com.hxxc.user.app.Event.PayEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.BankInfo;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.discovery.search.SelectActivity;
import com.hxxc.user.app.ui.product.dialog.BankCardListDialogForAdd;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.StringUtil;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyClickButton;
import com.hxxc.user.app.widget.SendCodeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import rx.Subscription;

/**
 * 绑卡页面
 * Created by chenqun on 2016/6/27.
 * <p>
 * 参数 ：type （类型）
 * 值： TYPE_TO_DPAY      来自投资界面，自动打开下一步支付界面；否则就不填
 * TYPE_FROM_PAY           刷新支付界面我的账户列表
 */
public class BaofuBingCardActivity extends ToolbarActivity implements SendCodeView.ICode {
    public static final int TYPE_TO_DPAY = 10;
    public static final int TYPE_FROM_PAY = 100;

    @BindView(R.id.name_text)
    TextView name_text;
    @BindView(R.id.bank_text)
    TextView bank_text;
    @BindView(R.id.bank_layout)
    RelativeLayout bank_layout;

    @BindView(R.id.bank_card_edit)
    EditText bank_card_edit;
    @BindView(R.id.bank_address_text)
    TextView bank_address_text;
    @BindView(R.id.bank_address_layout)
    RelativeLayout bank_address_layout;

    @BindView(R.id.branch_edit)
    EditText branch_edit;
    @BindView(R.id.code_edit)
    EditText code_edit;
    @BindView(R.id.mobile_edit)
    EditText mobile_edit;
    @BindView(R.id.get_code_text)
    SendCodeView get_code_text;
    @BindView(R.id.submit_btn)
    MyClickButton submit_btn;

    @BindView(R.id.iv_bank)
    ImageView iv_bank;
    @BindView(R.id.iv_bank_address)
    ImageView iv_bank_address;


    private BankCardListDialogForAdd dialog;
    private BankInfo mBankInfo = null;
    private String provinceCode;
    private String cityCode;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bingcard;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("添加银行卡");
    }

    @Override
    protected void onDestroy() {
        get_code_text.onDestory();
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    protected String mMobile;
    private String mCode;
    private int type;

    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        UserInfo user = SPUtils.geTinstance().getUserInfo();
        name_text.setText(StringUtil.getRatStr3(user.getUserName()));
        //初始化验证码按钮
        get_code_text.setNetType(SendCodeView.BINDCARD);
        get_code_text.initDatas(this, 0, null, new SendCodeView.MyOnClickListener() {

            @Override
            public boolean onPre() {
                final String bank = bank_text.getText().toString().trim();
                String mobile = mobile_edit.getText().toString();
                String bankcard = bank_card_edit.getText().toString();
                if (null == mBankInfo) {
                    ToastUtil.ToastShort(BaofuBingCardActivity.this, "请选择银行");
                    return true;
                }

                //如果用户没有填卡号直接返回
                if (TextUtils.isEmpty(bankcard)) {
                    ToastUtil.ToastShort(BaofuBingCardActivity.this, "请输入银行卡号");
                    return true;
                }

                if (TextUtils.isEmpty(cityCode)) {
                    ToastUtil.ToastShort(BaofuBingCardActivity.this, "请选择银行所在地");
                    return true;
                }
                final String branch = branch_edit.getText().toString().trim();
                if (TextUtils.isEmpty(branch)) {
                    ToastUtil.ToastShort(BaofuBingCardActivity.this, "请输入开户支行名称");
                    return true;
                }

                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.ToastShort(BaofuBingCardActivity.this, "请输入银行预留手机号码");
                    return true;
                }
                if (!CommonUtil.isMobileNo(mobile)) {
                    ToastUtil.ToastShort(BaofuBingCardActivity.this, "请输入正确的手机号码");
                    return true;
                }
                Map<String, String> params = new HashMap<>();
                params.put("uid", SPUtils.geTinstance().getUid());
                params.put("mobile", mobile);
                params.put("bankNo", mBankInfo.getBankNo());
                params.put("bankCard", bankcard);
                params.put("bankProvince", provinceCode);
                params.put("bankcity", cityCode);
                params.put("branch", branch);
                get_code_text.setParams(params);
                return false;
            }

            @Override
            public void onSuccess(String t) {
                mCode = t;
                ToastUtil.showSafeToast(BaofuBingCardActivity.this, "验证码发送成功");
                mMobile = mobile_edit.getText().toString();
            }

            @Override
            public void onFailure(String t) {

            }
        });
        submit_btn.setOnMyClickListener(new MyClickButton.MyClickListener() {

            @Override
            public void onMyClickListener() {
                submit();
            }
        });
        getBankList();
    }


    private void submit() {
        final String bank = bank_text.getText().toString().trim();
        final String bankcard = bank_card_edit.getText().toString().trim();
        final String branch = branch_edit.getText().toString().trim();
        final String bankcity = bank_address_text.getText().toString().trim();
        final String mobile = mobile_edit.getText().toString();
        String code = code_edit.getText().toString().trim();

        if (null == mBankInfo) {
            ToastUtil.ToastShort(this, "请选择开户银行");
            return;
        }
        if (TextUtils.isEmpty(bankcard)) {
            ToastUtil.ToastShort(this, "请填写银行卡号");
            return;
        }

        if (TextUtils.isEmpty(cityCode)) {
            ToastUtil.ToastShort(this, "请选择开户省市");
            return;
        }
        if (TextUtils.isEmpty(branch)) {
            ToastUtil.ToastShort(this, "请填写开户支行");
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtil.ToastShort(this, "请填写银行预留手机号");
            return;
        }
        if (TextUtils.isEmpty(mCode)) {
            ToastUtil.ToastShort(this, "请获取验证码");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.ToastShort(this, "请输入验证码");
            return;
        }

//		if (!TextUtils.isEmpty(mCode) &&!code.equals(mCode)) {
//			CommonUtil.showSafeToast(BaofuBingCardActivity.this, "验证码不正确");
//			return;
//		}

        submit_btn.begin();
        Map<String, String> params = new HashMap<>();
        params.put("uid", SPUtils.geTinstance().getUid());
        params.put("bankCard", bankcard);
        params.put("smsCode", code);
        params.put("transId", mCode);
        Subscription s = mApiManager.bingCard(params, new SimpleCallback<String>() {
            @Override
            public void onNext(String string) {
                Midhandler.getUserInfo(new Midhandler.OnGetUserInfo() {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        if (type == TYPE_TO_DPAY) {
                            EventBus.getDefault().post(new InvestEvent(InvestEvent.TYPE_PAY));//发送给购买界面自动打开支付界面
                        } else if (type == TYPE_FROM_PAY) {
                            EventBus.getDefault().post(new PayEvent(PayEvent.TYPE_REFLUSH_ACCOUNT));//刷新支付界面支付账户列表
                        } else {
                            EventBus.getDefault().post(new BankCardEvent());//发送给我的银行卡界面修改界面
                        }
                        ToastUtil.ToastShort(BaofuBingCardActivity.this, "绑卡成功");
                        submit_btn.finish();
                        finish();
                    }
                });
            }

            @Override
            public void onError() {
                submit_btn.finish();
            }
        });
        addSubscription(s);
    }

    @Override
    public String getMobile() {
        return mobile_edit.getText().toString();
    }

    @OnClick({R.id.bank_layout, R.id.bank_address_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bank_layout:
                bankList();
                break;
            case R.id.bank_address_layout://开户省市
                startActivity(new Intent(this, SelectActivity.class));
                break;
        }
    }

    private void bankList() {
        if (null == dialog) {
            dialog = new BankCardListDialogForAdd(this);
        }
        dialog.show();
    }

    private void getBankList() {
        mApiManager.getBankConfigList(Constants.API, new SimpleCallback<List<BankInfo>>() {

            @Override
            public void onNext(List<BankInfo> bankInfos) {
                if (null == dialog) {
                    dialog = new BankCardListDialogForAdd(BaofuBingCardActivity.this);
                }
                dialog.setDatas(bankInfos);
            }

            @Override
            public void onError() {

            }
        });
    }


    public void onEventMainThread(BindBankEvent event) {
        if (null != event.mBankInfo) {
            this.mBankInfo = event.mBankInfo;
            bank_text.setText(mBankInfo.getBankName());
        } else {
            if (event.provinceName.equals(event.cityName)) {
                bank_address_text.setText(event.provinceName);
            } else {
                bank_address_text.setText(event.provinceName + " " + event.cityName);
            }

            provinceCode = event.provinceCode;
            cityCode = event.cityCode;
        }
    }
}
