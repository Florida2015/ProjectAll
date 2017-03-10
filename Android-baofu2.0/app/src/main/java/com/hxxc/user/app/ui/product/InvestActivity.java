package com.hxxc.user.app.ui.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.InvestEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Agreement;
import com.hxxc.user.app.bean.OrderInfo;
import com.hxxc.user.app.bean.ProductInfo;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.MyCheckBox;
import com.hxxc.user.app.widget.MyClickButton;
import com.hxxc.user.app.widget.SelectView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static com.hxxc.user.app.utils.CommonUtil.myformat;

/**
 * Created by chenqun on 2016/10/27.
 */

public class InvestActivity extends ToolbarActivity {
    @BindView(R.id.tv_surplus_invest)//剩余可投
            TextView tv_surplus_invest;
    @BindView(R.id.rulerview)//刻度尺
            SelectView mRulerView;
    @BindView(R.id.tv_money)//出借金额
            EditText mTvMoney;
    @BindView(R.id.tv_income)//预期收益
            TextView tv_income;

    @BindView(R.id.checkBox)//
            MyCheckBox checkBox;

    @BindView(R.id.step_btn)
    MyClickButton step_btn;

    private long mSurplus;
    private long mMinAmount;
    private long mMaxAmount;

    private UserInfo user;
    private ProductInfo product;
    //    private Dialog traderDialog;
    private EditText et_pwd;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_invest;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("申请加入");
    }

    private int money;
//    private final RulerView.OnValueChangeListener mLintener1 = new RulerView.OnValueChangeListener() {
//
//        @Override
//        public void onValueChange(int intVal, int fltval) {
//            money = intVal + fltval;
//            mTvMoney.setText((money <= 0 ? "0" : money) + "");
//        }
//    };

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.tv_agreement, R.id.tv_agreement2})
    public void onClick(View view) {
        if (BtnUtils.isFastDoubleClick()) {
            switch (view.getId()) {
                case R.id.tv_agreement://TODO
                    if (TextUtils.isEmpty(product.getProduct().getHtmlContactUrl())) break;
                    Intent intent = new Intent(InvestActivity.this, AdsActivity.class);
                    intent.putExtra("title", "出借服务协议");
                    intent.putExtra("url", product.getProduct().getHtmlContactUrl());
                    startActivity(intent);
                    break;
                case R.id.tv_agreement2:
                    HashMap<String, String> map = new HashMap<>();
                    map.put("greementType", Constants.Agreement_Fengxian);
                    mApiManager.getAgreementTemplateList(map, new SimpleCallback<List<Agreement>>() {
                        @Override
                        public void onNext(List<Agreement> agreements) {
                            if (null != agreements && agreements.size() > 0) {
                                Agreement agreement = agreements.get(0);
                                if (TextUtils.isEmpty(agreement.getMobileViewUrl())) {
                                    return;
                                }
                                Intent intent = new Intent(InvestActivity.this, AdsActivity.class);
                                intent.putExtra("title", agreement.getAgreementName());
                                intent.putExtra("url", agreement.getMobileViewUrl());
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        mRulerView.clearAnimation();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        product = (ProductInfo) intent.getSerializableExtra("product");
        mSurplus = product.getSurplus();//剩余可投
        mMinAmount = product.getMinAmount();
        mMaxAmount = product.getMaxAmount();
        initView();
    }

    private void initView() {
        checkBox.setType(1);
        tv_surplus_invest.setText(CommonUtil.moneyType2(mSurplus) + "元");

//        mRulerView.setStartValue(0);
//        mRulerView.setEndValue(mMaxAmount);
//        mRulerView.setPartitionWidthInDP(36.7f * 2.0f);
//        mRulerView.setPartitionValue(10000);
//        mRulerView.setSmallPartitionCount(10);
//        mRulerView.setOriginValue(0);
//        mRulerView.setOriginValueSmall(0);
//        mRulerView.setValueChangeListener(mLintener1);
        List<String> mList = new ArrayList<>();

        long maxAmount = mMaxAmount;
        if (maxAmount < 50000) maxAmount = 50000;
        for (int i = 0; i <= maxAmount / 10000; i += 1)//maxAmount/10000
        {
            mList.add(i + "");
        }

        mRulerView.showValue(mList, new SelectView.onSelect() {
            @Override
            public void onSelectItem(String value) {
                mTvMoney.setText(value);
            }
        });

        final BigDecimal rate = new BigDecimal(product.getYearRate());
        final BigDecimal periods = new BigDecimal(product.getPeriodDay());

        mTvMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvMoney.setSelection(s.length());
                if (TextUtils.isEmpty(s.toString()) || 0 == Float.valueOf(s.toString())) {
                    tv_income.setText("￥" + 0);
                    return;
                }
                BigDecimal money = new BigDecimal(s.toString());
                tv_income.setText("￥" + jisuan(rate, money, periods) + "元");
                mRulerView.setCurrentValue(money.floatValue() / 10000f);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        mRulerView.post(new Runnable() {
//            @Override
//            public void run() {
//                mRulerView.setOriginValue(mMinAmount == 0 ? 0 : mMinAmount / 10000 * 10000);
//                mRulerView.setOriginValueSmall(mMinAmount == 0 ? 0 :((mMinAmount - mMinAmount / 10000 * 10000) - (mMinAmount - mMinAmount / 1000 * 1000))/1000);
//            }
//        });

        step_btn.setOnMyClickListener(new MyClickButton.MyClickListener() {
            @Override
            public void onMyClickListener() {
                submitOrder();
//                Intent intent = new Intent(InvestActivity.this, PayActivity.class);
//                startActivity(intent);
            }
        });
        mRulerView.post(new Runnable() {
            @Override
            public void run() {
                mRulerView.setCurrentValue(mMinAmount / 10000);
                mTvMoney.setText(mMinAmount + "");
                mTvMoney.setSelection(mTvMoney.getText().toString().length());
            }
        });
    }

    //其他 计算收益
    private String jisuan(BigDecimal rate, BigDecimal money, BigDecimal periods) {
        float value = (rate.floatValue() * money.floatValue() * periods.floatValue() / 360f);
        String format = myformat.format(value);
        int i = format.lastIndexOf(".");
        return format.substring(0, i + 3);
    }

    private void submitOrder() {
        String money = mTvMoney.getText().toString().trim();
        step_btn.begin();
        todoConfirm(money);
    }

    //判断是否认证
    private void todoConfirm(String money) {
        user = SPUtils.geTinstance().getUserInfo();
        if (null != user) {
            Intent intent = new Intent();
            //判断是否认证
            if (user.getRnaStatus() != 0) {//已经实名认证
                todoConfirmMyBankcard(money);
            } else {//没有实名认证
                ToastUtil.showSafeToast(this, "请认证");
                intent.setClass(this, AuthenticationActivity.class);
                intent.putExtra("from", AuthenticationActivity.FROM_Buy);
                startActivityForResult(intent, 1);
                step_btn.finish();
            }
        } else {
            step_btn.finish();
        }
    }

    //判断是否绑卡
    private void todoConfirmMyBankcard(String money) {
        if (user.getBindcardStatus() == 1) {
            confirmNum(money);
        } else {
            ToastUtil.showSafeToast(this, "请绑定银行卡");
            Intent intent = new Intent(this, BaofuBingCardActivity.class);
            intent.putExtra("type", BaofuBingCardActivity.TYPE_TO_DPAY);
            startActivity(intent);
            step_btn.finish();
        }
    }

    private void confirmNum(String money) {
        step_btn.finish();
        try {
            if (TextUtils.isEmpty(money) || 0 == Long.parseLong(money)) {
                ToastUtil.ToastShort(this, "请输入出借金额");
                return;
            }
            long m = Long.parseLong(money);
            if ((m - mMinAmount) % 10000 != 0) {
                ToastUtil.ToastShort(this, "出借金额需为1万元的整数倍");
                return;
            }

            if (m < mMinAmount) {
                if (mMinAmount >= 10000) {
                    if (((mMinAmount / 10000f) - (mMinAmount / 10000)) > 0) {
                        ToastUtil.ToastShort(this, "出借金额需高于等于" + mMinAmount / 10000f + "万元");
                    } else {
                        ToastUtil.ToastShort(this, "出借金额需高于等于" + mMinAmount / 10000 + "万元");
                    }

                } else {
                    ToastUtil.ToastShort(this, "出借金额需高于等于" + mMinAmount + "元");
                }
                return;
            }

            if (m > mMaxAmount) {
                ToastUtil.ToastShort(this, "出借金额超过上限");
                return;
            }

            if (m > mSurplus) {
                ToastUtil.ToastShort(this, "出借金额超过可投");
                return;
            }

            if (!checkBox.toogle) {
                ToastUtil.ToastShort(this, "请阅读并同意《购买服务协议》");
                return;
            }

            confirmAccount(m);
        } catch (Exception e) {
            if (e instanceof NumberFormatException) {
                ToastUtil.ToastShort(this, "请输入正确的数字");
            }
        }
    }

    private void confirmAccount(long money) {
        //TODO 先下单
        step_btn.begin();
        ApiManager.getInstance().createOrder(SPUtils.geTinstance().getUid(), product.getId() + "", money + "", new SimpleCallback<OrderInfo>() {
            @Override
            public void onNext(OrderInfo orderInfo) {
//                //获取我的账户信息
//                ApiManager.getInstance().getMyAccountInfo(SPUtils.geTinstance().getUid(), new SimpleCallback<AccountInfo>() {
//                    @Override
//                    public void onNext(AccountInfo accountInfo) {
                Intent intent = new Intent(InvestActivity.this, PayActivity.class);
                intent.putExtra("order", orderInfo);
                startActivity(intent);
                step_btn.finish();
//                    }
//
//                    @Override
//                    public void onError() {
//                        step_btn.finish();
//                    }
//                });
            }

            @Override
            public void onError() {
                step_btn.finish();
            }
        });


    }

    public void onEventMainThread(InvestEvent event) {
        if (event.type == InvestEvent.TYPE_PAY) {
            String str = mTvMoney.getText().toString().trim();
            step_btn.begin();
            confirmNum(str);
        } else if (event.type == InvestEvent.TYPE_FINISH) {
            finish();
        }
    }
}
