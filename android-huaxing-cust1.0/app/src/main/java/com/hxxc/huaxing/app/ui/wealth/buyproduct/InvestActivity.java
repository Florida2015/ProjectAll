package com.hxxc.huaxing.app.ui.wealth.buyproduct;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.AssetsBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.OpenAccountBean;
import com.hxxc.huaxing.app.data.bean.ProductInfo;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.mine.account.RechargeActivity;
import com.hxxc.huaxing.app.ui.mine.account.WebOpenEaccountActivity;
import com.hxxc.huaxing.app.ui.wealth.Fragment1;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.CommonUtil;
import com.hxxc.huaxing.app.utils.DisplayUtil;
import com.hxxc.huaxing.app.utils.MoneyUtil;
import com.hxxc.huaxing.app.utils.ToastUtil;
import com.hxxc.huaxing.app.wedgit.LoadingView;
import com.hxxc.huaxing.app.wedgit.MyCheckBox;
import com.hxxc.huaxing.app.wedgit.RulerView;
import com.nineoldandroids.animation.ValueAnimator;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

import static com.hxxc.huaxing.app.R.id.tv_money;
import static com.hxxc.huaxing.app.utils.CommonUtil.myformat;
import static com.hxxc.huaxing.app.utils.DisplayUtil.getStatusBarHeight;
import static java.math.BigDecimal.ROUND_DOWN;

/**
 * Created by chenqun on 2016/9/26.
 */

public class InvestActivity extends BaseActivity {
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.back)
    ImageButton back;

    @BindView(R.id.ll_rootview)//根布局
            LinearLayout mRootview;
    @BindView(R.id.ll_top)//头部需要做动画的布局
            LinearLayout ll_top;

    @BindView(R.id.rulerview)//刻度尺
            RulerView mRulerView;
    @BindView(tv_money)//出借金额
            EditText mTvMoney;

    @BindView(R.id.tv_surplus_invest)//剩余可投
            TextView tv_surplus_invest;
    @BindView(R.id.tv_surplus_yu_e)//可用余额
            TextView tv_surplus_yu_e;
    @BindView(R.id.tv_surplus_congzhi)//[充值]
            TextView tv_surplus_congzhi;
    @BindView(R.id.tv_income)//预期收益
            TextView tv_income;
    @BindView(R.id.tv_agreement)//《购买服务协议》
            TextView tv_agreement;
    @BindView(R.id.checkBox)//
            MyCheckBox checkBox;

    @BindView(R.id.tv_pay)//支付
            FancyButton tv_pay;

    private int mPartHeight;
    private double mSurplus;
    private String mPid;
    private double mMinAmount;
    private double mMaxAmount;

    private Dialog dialog2;
    private ProductInfo mProduct;

    @Override
    public int getLayoutId() {
        return R.layout.activity_invest;
    }

    private int money = 0;

    private final RulerView.OnValueChangeListener mLintener1 = new RulerView.OnValueChangeListener() {

        @Override
        public void onValueChange(int intVal, int fltval) {
            money = intVal + fltval;
            mTvMoney.setText((money < 0 ? 0 : money) + "");
        }
    };

    @Override
    public void initView() {
        back.setVisibility(View.VISIBLE);
        toolbar_title.setText("立即出借");
        Intent intent = getIntent();

        mPid = intent.getStringExtra("pid");
        mProduct = (ProductInfo) intent.getSerializableExtra("product");

        int type = intent.getIntExtra("type", Fragment1.Type_Wealth);
        if (type == Fragment1.Type_Zhaiquan)
            mSurplus = (null == mProduct.getAmount() ? 0 : mProduct.getAmount().doubleValue());//剩余可投
        else
            mSurplus = (null == mProduct.getSurplus() ? 0 : mProduct.getSurplus().doubleValue());//剩余可投

        mMinAmount = null == mProduct.getMinAmount() ? 0 : mProduct.getMinAmount().doubleValue();
        mMaxAmount = null == mProduct.getMaxAmount() ? 0 : mProduct.getMaxAmount().doubleValue();

        int periods = mProduct.getPeriodMonth();
        float rate = mProduct.getYearRate().floatValue();

        tv_surplus_invest.setText(CommonUtil.toMoneyType(mSurplus));

        mRulerView.setStartValue(0);

        int maxAmount = (int) Math.ceil(mMaxAmount / 10000d) * 10000;
        mRulerView.setEndValue(maxAmount < 100000 ? 100000 : maxAmount);//maxAmount < 100000 ? 100000 : maxAmount

        mRulerView.setPartitionWidthInDP(36.7f * 2.0f);
        mRulerView.setPartitionValue(10000);
        mRulerView.setSmallPartitionCount(10);
//        mRulerView.setOriginValue(0);
//        mRulerView.setOriginValueSmall(0);
//        mRulerView.setValueChangeListener(mLintener1);
        mRulerView.setOnChangeListener(new RulerView.OnChangeListener() {
            @Override
            public void onValueChange(String text) {
                if (TextUtils.isEmpty(text)) {
                    mTvMoney.setText("");
                    return;
                }
                mTvMoney.setText(text);
            }
        });
        mRulerView.setDatas(0);

        mTvMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvMoney.setSelection(s.length());
                if (TextUtils.isEmpty(s.toString()) || 0 == new BigDecimal(s.toString()).doubleValue()) {
                    tv_income.setText("￥" + 0);
                    return;
                }
                BigDecimal money = new BigDecimal(s.toString());
                if (type == Fragment1.Type_Wealth)
                    tv_income.setText("￥" + jisuan(new BigDecimal(rate), money, new BigDecimal(periods)));
                mRulerView.setDatas(money.intValue());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mTvMoney.clearFocus();
        mTvMoney.setText(mMinAmount + "");
        mTvMoney.setSelection(mTvMoney.getText().toString().length());
        mRootview.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        ll_top.post(new Runnable() {
            @Override
            public void run() {
                mPartHeight = ll_top.getHeight();
            }
        });

        InputFilter[] filter = new InputFilter[1];
        if (type == Fragment1.Type_Zhaiquan) {
            mTvMoney.setEnabled(false);
            mRulerView.setEnabled(false);
            filter[0] = new InputFilter.LengthFilter(13);
        } else {
            filter[0] = new InputFilter.LengthFilter(10);
        }
        mTvMoney.setFilters(filter);
        getUserFunds();//获取用户资产

        if (type == Fragment1.Type_Zhaiquan && null != mProduct && null != mProduct.getExpectedProfit()) {
            tv_income.post(new Runnable() {
                @Override
                public void run() {
                    tv_income.setText("￥" + CommonUtil.toMoneyType(mProduct.getExpectedProfit().doubleValue()));
                }
            });
        }
    }

    //其他 计算收益
    private String jisuan(BigDecimal rate, BigDecimal money, BigDecimal periods) {
        float value = (rate.floatValue() * money.floatValue() * periods.floatValue() / 12f);
        String format = myformat.format(value);
        int i = format.lastIndexOf(".");
        return format.substring(0, i + 3);
    }

    @Override
    public void initPresenter() {

    }

    // 软键盘的显示状态
    private boolean ShowKeyboard = false;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            // 应用可以显示的区域。此处包括应用占用的区域，包括标题栏不包括状态栏
            Rect r = new Rect();
            mRootview.getWindowVisibleDisplayFrame(r);
            // 键盘最小高度
            int minKeyboardHeight = 150;
            // 获取状态栏高度
            int statusBarHeight = getStatusBarHeight(mContext);
            // 屏幕高度,不含虚拟按键的高度
            int screenHeight = mRootview.getRootView().getHeight();
            // 在不显示软键盘时，height等于状态栏的高度
            int height = screenHeight - (r.bottom - r.top);

            if (ShowKeyboard) {
                // 如果软键盘是弹出的状态，并且height小于等于状态栏高度，
                // 说明这时软键盘已经收起
                if (height - statusBarHeight < minKeyboardHeight) {
                    ShowKeyboard = false;

//                    doListAnimal(-mPartHeight, 0);

                }
            } else {
                // 如果软键盘是收起的状态，并且height大于状态栏高度，
                // 说明这时软键盘已经弹出
                if (height - statusBarHeight > minKeyboardHeight) {
                    ShowKeyboard = true;
//                    doListAnimal(0, -mPartHeight);

                    ll_top.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.smoothScrollBy(0, ll_top.getMeasuredHeight() + DisplayUtil.dip2px(HXXCApplication.getContext(), 20));
                        }
                    });
                }
            }
        }
    };

    @SuppressLint("NewApi")
    protected void doListAnimal(final float f, final float t) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ll_top.clearAnimation();
                ValueAnimator animator = ValueAnimator.ofFloat(f, t);
                // 设置作用对象
                animator.setTarget(ll_top);
                // 设置执行时间(1000ms)
                animator.setDuration(500);
                // 添加动画更新监听
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // 获取当前值
                        Float mValue = (Float) animation.getAnimatedValue();
//                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_top.getLayoutParams();
//                        layoutParams.setMargins(0, mValue.intValue(), 0, 0);
                        ll_top.setPadding(0, mValue.intValue(), 0, 0);
//                        ll_top.setLayoutParams(layoutParams);
                        ll_top.invalidate();
                    }
                });
                // 开始动画
                animator.start();
            }
        });

    }

    private double selfNum = 100000;

    @OnClick({R.id.back, R.id.tv_surplus_congzhi, R.id.tv_agreement, R.id.tv_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_surplus_congzhi://[充值]
                startActivity(new Intent(this, RechargeActivity.class));
                break;
            case R.id.tv_agreement://《购买服务协议》
                break;
            case R.id.tv_pay://支付
                if (BtnUtils.isFastDoubleClick()) {
                    double i = new BigDecimal(mTvMoney.getText().toString()).doubleValue();

                    if (i > mMaxAmount) {
                        toast("单笔最高金额不超过" + mMaxAmount + "元");
                        return;
                    }
                    if (i > mSurplus) {
                        toast("标的剩余金额不足");
                        return;
                    }
                    if (i > selfNum) {
                        toast("账户余额不足,请充值");
                        return;
                    }
                    if (i < mMinAmount) {
                        toast("起投金额不低于" + mMinAmount + "元");
                        return;
                    }
                    createOrderInfo();
                }
                break;
        }
    }

    //创建订单
    private void createOrderInfo() {
        showMyDialog();
        Api.getClient().createOrderInfo(Api.uid, mPid, mProduct.getBidType() == 2 ? mProduct.getMinAmount().doubleValue() + "" : mTvMoney.getText().toString()).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean<String> stringBaseBean) {
                if (stringBaseBean.isSuccess()) {
//                    String str = stringBaseBean.getModel();
//                    Intent intent = new Intent(InvestActivity.this, WebActivity.class);
//                    intent.putExtra("url", HttpConfig.dpPay + "?orderNo=" + str);
//                    intent.putExtra("title", "支付");
//                    startActivity(intent);
                    payOrderInfo(stringBaseBean.getModel());
                } else {
                    closeDialog();
                    toast(stringBaseBean.getErrMsg());
                }
            }
        });
    }

    //订单支付 跳转 华兴h5
    private void payOrderInfo(String orderNo) {
        Api.getClient().payOrderInfo(Api.uid, orderNo).compose(RxApiThread.convert()).subscribe(new BaseSubscriber<OpenAccountBean>(mContext) {
            @Override
            public void onSuccess(OpenAccountBean baseBean) {
                closeDialog();
                if (!TextUtils.isEmpty(baseBean.getBaseUrl()))
                    startActivity(new Intent(InvestActivity.this, WebOpenEaccountActivity.class).putExtra("url", baseBean.getBaseUrl()).putExtra("title", "支付").putExtra("data", baseBean.getHtmlStr()));

            }

            @Override
            public void onFail(String fail) {
                closeDialog();
                if (!TextUtils.isEmpty(fail)) ToastUtil.ToastShort(mContext, fail);
            }
        });
    }

    /**
     * 获取用户资产信息
     */
    public void getUserFunds() {
        Api.getClient().getUserAssets(Api.uid).compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<AssetsBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseBean<AssetsBean> baseBean) {
                if (baseBean.isSuccess() && baseBean.getModel() != null) {
                    selfNum = baseBean.getModel().getAvailablebal().doubleValue();
                    tv_surplus_yu_e.setText(MoneyUtil.addComma(baseBean.getModel().getAvailablebal(), 2, ROUND_DOWN) + "元");
                } else {
                    selfNum = 0;
                    tv_surplus_yu_e.setText("0.00元");
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
