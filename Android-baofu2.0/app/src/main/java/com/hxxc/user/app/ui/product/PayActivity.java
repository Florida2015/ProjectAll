package com.hxxc.user.app.ui.product;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxxc.user.app.Event.InvestEvent;
import com.hxxc.user.app.Event.MineEvent;
import com.hxxc.user.app.Event.OrderSurplusTimeEvent;
import com.hxxc.user.app.Event.PayEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.Account;
import com.hxxc.user.app.bean.OrderInfo;
import com.hxxc.user.app.bean.RedPackagePayBean;
import com.hxxc.user.app.contract.PayV;
import com.hxxc.user.app.contract.presenter.PayPresenter;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.product.dialog.BankCardListDialog;
import com.hxxc.user.app.ui.product.dialog.RedPackageDialog;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.StringUtil;
import com.hxxc.user.app.utils.ThreadManager;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.LeftAndRightTextView;
import com.hxxc.user.app.widget.MyClickButton;
import com.hxxc.user.app.widget.SendCodeView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/10/27.
 */

public class PayActivity extends ToolbarActivity implements SendCodeView.ICode, PayV {
    private static final String text1 = "请在 ";
    private static final String text2 = " 分钟之内支付，否则订单将自动取消";

    @BindView(R.id.tv_suplus_time)
    TextView tv_suplus_time;

    @BindView(R.id.iv_bank)
    ImageView iv_bank;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.tv_bank_describe)
    TextView tv_bank_describe;

    @BindView(R.id.tv_right)
    TextView tv_right;//充值金额

    //    @BindView(R.id.tv_surplus_account)//可用余额
//            TextView tv_surplus_account;
    @BindView(R.id.tv_redpackage)//红包抵扣
            LeftAndRightTextView tv_redpackage;
    @BindView(R.id.tv_pay_num)//实际支付
            LeftAndRightTextView tv_pay_num;

//    @BindView(R.id.checkBox)//是否使用账户余额
//            MyCheckBox checkBox;

    @BindView(R.id.step_btn)
    MyClickButton step_btn;
    private BankCardListDialog dialog;
    private RedPackageDialog dialog2;
    private OrderInfo mOrder;
    private double mSurplus;
    private Dialog traderDialog;
    private EditText et_code;
    private String transId;
    private PayPresenter presenter;
    private MyClickButton btn_pay_dialog;
    private RedPackagePayBean mRedPackage;
    private Account mAccount1;
    private SendCodeView get_code_text;
    private TextView tv_dialog_money;
    private List<RedPackagePayBean> mRedPackageDatas;
    //    private TextView tv_time;
    private ImageView iv_close;
    private ThreadManager.ThreadPoolProxy threadPoolProxy;
    private Dialog systemDialog;
    private boolean isPaying = false;//是否正在支付
    private boolean isTimeOut = false;//是否超时

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("支付订单");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        presenter = new PayPresenter(this);
        mOrder = (OrderInfo) getIntent().getSerializableExtra("order");

        presenter.querySubAccountList();
        if (null != mOrder) {
            presenter.queryRedPackageList(mOrder.getOrderNo());
            presenter.getOrderSuplusTime(mOrder.getOrderNo());
        }

        initView();
    }

    @Override
    protected void onDestroy() {

        if (null != threadPoolProxy) {
            threadPoolProxy.stop();
            threadPoolProxy.shutdown();
        }

        if (null != get_code_text) {
            get_code_text.onDestory();
        }

        EventBus.getDefault().unregister(this);
        if (dialog2 != null && dialog2.isShowing()) {
            dialog2.dismiss();
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (traderDialog != null && traderDialog.isShowing()) {
            traderDialog.dismiss();
        }

        if (systemDialog != null && systemDialog.isShowing()) {
            systemDialog.dismiss();
        }
        super.onDestroy();
    }

    private void initView() {
        setSurplusTime("05:00");
//        checkBox.setType(1);
//        checkBox.setToggle(false);
        if (null == mOrder) {
            return;
        }
        step_btn.setOnMyClickListener(new MyClickButton.MyClickListener() {
            @Override
            public void onMyClickListener() {
                //TODO 支付
                doPay();
            }
        });
        tv_right.setText(CommonUtil.moneyType2(mOrder.getMoney())+"元");//出借金额
//        tv_surplus_account.setText(CommonUtil.moneyType(mSurplus));//账户余额
        tv_redpackage.setRightText("");//红包抵扣
        tv_pay_num.setRightText(CommonUtil.moneyType2(mRedPackage == null ? mOrder.getMoney() : mOrder.getMoney() - mRedPackage.getMoney())+"元");//实际支付
    }


    @OnClick({R.id.rl_pay_type, R.id.tv_redpackage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_redpackage:
                if (null == dialog2) {
                    dialog2 = new RedPackageDialog(this);
                }
                if (!dialog2.isShowing()) dialog2.show();
                break;
            case R.id.rl_pay_type://TODO 选择支付方式
                if (null == dialog) {
                    dialog = new BankCardListDialog(this);
                }
                if (!dialog.isShowing()) dialog.show();
                break;
        }
    }

    //支付
    private void doPay() {
        //TODO 如果账户余额足够，直接支付
//        if(mOrder.getMoney() > mAccount1.getSingleLimit()){
//            toast("超出银行卡限额");
//            return;
//        }

        step_btn.begin();
        if (null == traderDialog) {
            traderDialog = new Dialog(this, R.style.loadingDialogTheme) {
                @Override
                public void onBackPressed() {
                }
            };
            traderDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if (null != get_code_text) {
                        get_code_text.mOnDestory = true;
                    }
                }
            });
            View inflate = View.inflate(this, R.layout.dialog_traders_password, null);
            tv_dialog_money = (TextView) inflate.findViewById(R.id.tv_money);
            TextView tv_phone = (TextView) inflate.findViewById(R.id.tv_phone);
            btn_pay_dialog = (MyClickButton) inflate.findViewById(R.id.step_btn);
            iv_close = (ImageView) inflate.findViewById(R.id.iv_close);
            et_code = (EditText) inflate.findViewById(R.id.et_code);
            get_code_text = (SendCodeView) inflate.findViewById(R.id.get_code_text);
//            tv_time = (TextView) inflate.findViewById(tv_time);

            tv_phone.setText("验证码发送至：" + StringUtil.getRatStr2(mAccount1.getMobile()));

            get_code_text.initDatas(this, 0, null, new SendCodeView.MyOnClickListener() {
                @Override
                public boolean onPre() {
//                    if (checkBox.toogle && mOrder != null && mSurplus >= mOrder.getMoney()) {
//                        get_code_text.setNetType(SendCodeView.BALANCE_PAY);
//                        Map<String, String> params = new HashMap<>();
//                        params.put("orderNo", mOrder.getOrderNo());
//                        get_code_text.setParams(params);
//                    }else{
                    get_code_text.setNetType(SendCodeView.PAY);
                    Map<String, String> params = new HashMap<>();
                    params.put("uid", SPUtils.geTinstance().getUid());
                    params.put("orderNo", mOrder.getOrderNo());
                    params.put("iid", mAccount1.getIid() + "");
                    params.put("rId", mRedPackage == null ? "" : mRedPackage.getId() + "");
                    get_code_text.setParams(params);
//                    }

                    return false;
                }

                @Override
                public void onSuccess(String t) {
                    transId = t;
                    ToastUtil.showSafeToast(PayActivity.this, "验证码发送成功");
                }

                @Override
                public void onFailure(String t) {

                }
            });

            iv_close.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != traderDialog) {
                        traderDialog.dismiss();
                    }
                }
            });
            btn_pay_dialog.setOnMyClickListener(new MyClickButton.MyClickListener() {
                @Override
                public void onMyClickListener() {
                    String code = et_code.getText().toString();
                    if (TextUtils.isEmpty(code)) {
                        toast("请输入验证码");
                        return;
                    }
                    if (TextUtils.isEmpty(transId)) {
                        toast("请获取验证码");
                        return;
                    }
//                    if(get_code_text.getNetType() == SendCodeView.PAY){
                    Map<String, String> params = get_code_text.getParams();
                    String iid = params.get("iid");
                    String rId = params.get("rId");
                    if (!(mAccount1.getIid() + "").equals(iid)) {
                        toast("请重新获取验证码");
                        return;
                    }
                    if (TextUtils.isEmpty(rId)) {
                        if (null != mRedPackage) {
                            toast("请重新获取验证码");
                            return;
                        }
                    } else {
                        if (null == mRedPackage || !rId.equals(mRedPackage.getId() + "")) {
                            toast("请重新获取验证码");
                            return;
                        }
                    }
                    hideKeyBoard();
                    btn_pay_dialog.begin();
                    iv_close.setEnabled(false);
                    presenter.doPay(transId, code);
                    isPaying = true;
//                    }else{
//                        step_btn.begin();
//                        presenter.doBalancePay(mOrder.getOrderNo(), mRedPackage==null?"":mRedPackage.getId()+"", code);
//                    }
                }
            });
            traderDialog.setContentView(inflate);


        }
        tv_dialog_money.setText(CommonUtil.moneyType2(mRedPackage == null ? mOrder.getMoney() : mOrder.getMoney() - mRedPackage.getMoney()));
        traderDialog.show();
        step_btn.finish();
    }

    @Override
    public String getMobile() {
        return "";
    }

    @Override
    public void onPaySuccess() {
        toast("支付成功");
        Intent in = new Intent(PayActivity.this, PaySuccessedActivity.class);
        in.putExtra("orderNo", mOrder.getOrderNo());
        startActivity(in);
        EventBus.getDefault().post(new InvestEvent(InvestEvent.TYPE_FINISH));
        //跟新我的资产数据
        EventBus.getDefault().post(new MineEvent());
        //跟新理财师的数据
        Midhandler.getFinanceInfo();
        if (null != btn_pay_dialog) {
            btn_pay_dialog.finish();
        }
        if (null != iv_close) {
            iv_close.setEnabled(false);
        }
        step_btn.finish();
        PayActivity.this.finish();

//        isPaying = false;
    }

    @Override
    public void onPayFailure() {
        if (null != btn_pay_dialog) {
            btn_pay_dialog.finish();
        }
        if (null != iv_close) {
            iv_close.setEnabled(true);
        }
        step_btn.finish();

        isPaying = false;
        if (isTimeOut) {
            gameOver();
        }
    }

    @Override
    public void onGetAccountList(List<Account> datas) {
        if (null == dialog) {
            dialog = new BankCardListDialog(this);
        }
        if (null != datas && datas.size() > 0) {
            for (int a = 0; a < datas.size(); a++) {
                if (datas.get(a).getIsDefaultCard() == 1) {
                    mAccount1 = datas.get(a);
                    initBankText();
                }
            }
        }

        dialog.setDatas(datas);
    }

    @Override
    public void onReflushAccountList(List<Account> datas) {
        if (null == dialog) {
            dialog = new BankCardListDialog(this);
        }
        if (null != datas && datas.size() > 0) {
            mAccount1 = datas.get(0);
            initBankText();
        }
        dialog.setDatas(datas, 0);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onGetRedPackageList(List<RedPackagePayBean> datas) {
        if (null == dialog2) {
            dialog2 = new RedPackageDialog(this);
        }
        dialog2.setDatas(datas);

        mRedPackageDatas = datas;
        initRedPackageTextView();
    }

    @Override
    public void startCountTime(long aLong) {
        threadPoolProxy = ThreadManager.getSinglePool("order");
        threadPoolProxy.execute(new Runnable() {
            @Override
            public void run() {
                long t = aLong;
                try {
                    while (t > 0) {
                        LogUtil.e("订单剩余时间-定时器=" + aLong + "/" + t);
                        Thread.sleep(1000);
                        EventBus.getDefault().post(new OrderSurplusTimeEvent(t));
                        t -= 1;
                    }
                    Thread.sleep(1000);
                    EventBus.getDefault().post(new OrderSurplusTimeEvent(-1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void initRedPackageTextView() {
        if (null == mRedPackageDatas || mRedPackageDatas.size() == 0) {
            tv_redpackage.setRightText("暂无可用红包");
        } else {
            tv_redpackage.setRightText(mRedPackageDatas.size() + "个可用红包");
        }
    }

    //设置头部银行卡
    private void initBankText() {
        if (null != mAccount1) {
            Picasso.with(this).load(mAccount1.getMobileLogoUrl()).error(R.drawable.abc).placeholder(R.drawable.abc).into(iv_bank);
            tv_bank_name.setText(mAccount1.getBank() + "（尾号" + mAccount1.getBankCard().substring(mAccount1.getBankCard().length() - 4, mAccount1.getBankCard().length()) + ")");
            tv_bank_describe.setText("单笔限额" + CommonUtil.checkMoney2(mAccount1.getSingleWLimit().floatValue()) + "万，单日限额" + CommonUtil.checkMoney2(mAccount1.getDailyWLimit().floatValue()) + "万");
        }
    }

    public void onEventMainThread(final PayEvent event) {
        if (event.type == PayEvent.TYPE_REFLUSH_ACCOUNT) {
            presenter.reflushSubAccountList();
        } else if (event.type == PayEvent.TYPE_RED) {
            mRedPackage = event.redPackageItemBean;
            if (null == mRedPackage) {//不使用红包的情况
                initRedPackageTextView();
            } else {
                tv_redpackage.setRightText("-"+CommonUtil.moneyType(mRedPackage.getMoney())+"元");
            }
            tv_pay_num.setRightText(CommonUtil.moneyType2(mRedPackage == null ? mOrder.getMoney() : mOrder.getMoney() - mRedPackage.getMoney())+"元");//实际支付
        } else {
            mAccount1 = event.account;
            initBankText();
        }
    }

    public void onEventMainThread(final OrderSurplusTimeEvent event) {
        if (event.surplusTime < 0) {
            gameOver();
            tv_suplus_time.setText("已过支付时间，订单已被自动取消！");
            return;
        }
//        if (null != tv_time) {
//            tv_time.setText(DateUtil.getTimeFormat2(event.surplusTime));
//        }
        setSurplusTime(DateUtil.getTimeFormat2(event.surplusTime));
    }


    private void setSurplusTime(String timeFormat2) {
        String str = text1 + timeFormat2 + text2;
        SpannableStringBuilder style = new SpannableStringBuilder(str);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#ff1f80d1")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.WHITE), 3, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//getResources().getColor(R.color.text_blue)
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#ff1f80d1")), 8, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_suplus_time.setText(style);
    }

    @Override
    public void gameOver() {
        isTimeOut = true;
        if (isPaying) return;
        if (null == systemDialog) {
            systemDialog = new Dialog(this, R.style.loadingDialogTheme) {
                @Override
                public void onBackPressed() {
                }
            };
            View inflate = View.inflate(this, R.layout.dialog_feedback, null);
            TextView tv_1 = (TextView) inflate.findViewById(R.id.tv_1);
            TextView tv_2 = (TextView) inflate.findViewById(R.id.tv_2);
            tv_2.setVisibility(View.GONE);
            tv_1.setText("订单已被取消，请重新下单");
            MyClickButton sure = (MyClickButton) inflate.findViewById(R.id.sure);
            sure.setText("知道了");
            sure.setOnMyClickListener(new MyClickButton.MyClickListener() {
                @Override
                public void onMyClickListener() {
                    systemDialog.dismiss();
                    PayActivity.this.finish();
                }
            });
            systemDialog.setContentView(inflate);
        }
        if (!systemDialog.isShowing()) systemDialog.show();
    }

    public void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et_code.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
