package com.hxxc.huaxing.app.ui.mine.account;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.AgreementBean;
import com.hxxc.huaxing.app.data.bean.AssetsBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.OpenAccountBean;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.WebActivity;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.LoginActivity;
import com.hxxc.huaxing.app.utils.MoneyUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.utils.ToastUtil;
import com.hxxc.huaxing.app.wedgit.LoadingView;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by Administrator on 2016/9/26.
 * 充值页
 */

public class RechargeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_recharge_money)
    TextView tv_recharge_money;
    @BindView(R.id.ed_recharge_money)
    EditText ed_recharge_money;
    @BindView(R.id.btn_recharge)
    FancyButton btn_recharge;

    private Dialog dialog2;

    @Override
    public int getLayoutId() {
        return R.layout.account_recharge_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("充值");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


        if (getIntent().hasExtra("money") && !TextUtils.isEmpty(getIntent().getStringExtra("money")))
            tv_recharge_money.setText(getIntent().getStringExtra("money") + "元");
        else tv_recharge_money.setText("0.00元");

        getUserFunds();
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_recharge,R.id.tv_recharge_url})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_recharge) {//充值
            rechargeMoney();
        }
        if(view.getId() == R.id.tv_recharge_bank_url){//银行卡充值引导
            getByAgrementType("7");
        }
        if(view.getId() == R.id.tv_recharge_url){//充值引导
            getByAgrementType("8");
        }
    }

    /**
     * 充值
     */
    public void rechargeMoney() {
        String charge = ed_recharge_money.getText().toString().trim();
        if (TextUtils.isEmpty(charge)) {
            toast("请输入充值金额");
            return;
        }
        int isOpen = SharedPreUtils.getInstanse().getKeyValue_i(mContext, UserInfoConfig.spOpenEaccount);
        if (isOpen != 1) {
            //开通E账号
            startActivity(new Intent(mContext, OpenEAccountActivity.class));
            return;
        }
        //绑卡
        UserInfoBean bean = SharedPreUtils.getInstanse().getUserInfo(mContext);
        if (bean!=null&&bean.getBindcardStatus()==0){
            getEaccountBind();
            return;
        }

        showMyDialog();
        Api.getClient().getUserRecharge(Api.uid, charge).
                compose(RxApiThread.convert()).subscribe(new Subscriber<BaseBean<OpenAccountBean>>() {
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
            public void onNext(BaseBean<OpenAccountBean> baseBean) {
                closeDialog();
                if (baseBean.isSuccess()) {
                    if (!TextUtils.isEmpty(baseBean.getModel().getBaseUrl()))
                        startActivity(new Intent(RechargeActivity.this, WebOpenEaccountActivity.class).
                                putExtra("url", baseBean.getModel().getBaseUrl()).putExtra("title", "充值").
                                putExtra("data", baseBean.getModel().getHtmlStr()).putExtra("flag", false));
                } else {
                    if (!TextUtils.isEmpty(baseBean.getErrMsg())) toast(baseBean.getErrMsg());
                    if (baseBean.getStatusCode().equals(UserInfoConfig.http_error_out)) {
                        ToastUtil.ToastShort(mContext, getResources().getString(R.string.text_login_out));
                        AppManager.getAppManager().ExitLogin();
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                }
            }
        });

    }
    /**
     * E账户绑卡
     */
    public void getEaccountBind(){
        showMyDialog();
        Api.getClient().getUserBindCard(Api.uid).compose(RxApiThread.convert()).
                subscribe(new Subscriber<BaseBean<OpenAccountBean>>() {
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
                    public void onNext(BaseBean<OpenAccountBean> baseBean) {
                        closeDialog();
                        if (baseBean.isSuccess()){
                            //跳转华兴h5
                            if (!TextUtils.isEmpty(baseBean.getModel().getBaseUrl()))
                                startActivity(new Intent(mContext, WebOpenEaccountActivity.class).putExtra("url", baseBean.getModel().getBaseUrl()).
                                        putExtra("title", "绑卡").putExtra("data", baseBean.getModel().getHtmlStr()).putExtra("flag",false));
                        }else {
                            if (!TextUtils.isEmpty(baseBean.getErrMsg())) toast(baseBean.getErrMsg());
                        }
                    }
                });
    }
    private void showMyDialog() {
        if (dialog2!=null&&dialog2.isShowing())return;
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

    /**
     * 获取用户资产信息
     */
    public void getUserFunds() {
        Api.getClient().getUserAssets(Api.uid).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<AssetsBean>(mContext) {
                    @Override
                    public void onSuccess(AssetsBean assetsBean) {
                        if (assetsBean != null) {
                            tv_recharge_money.setText(MoneyUtil.addComma(assetsBean.getAvailablebal(), 2, BigDecimal.ROUND_DOWN) + "元");
                            return;
                        }
                    }

                    @Override
                    public void onFail(String fail) {
                        tv_recharge_money.setText("0.00元");
                    }
                });
    }

    /**
     * 根据协议类型获取协议列表协议类型:
     * 【1、安全保障 2、注册协议 3、风险揭示书 4、保密协议 5、隐私政策、6、关于我们 7.购买协议】
     * 7、银行卡充值引导  8、E账户余额充值引导引导 9、提现图文引导 10.购买协议
     */
    public void getByAgrementType(String type) {
        Api.getClient().getPubSelectByAgrementType(type).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<AgreementBean>>(mContext) {
                              @Override
                              public void onSuccess(List<AgreementBean> agreementBeen) {
                                  if (agreementBeen != null && agreementBeen.size() > 0) {
//                                if (type.equals("1")){
                                      startActivity(new Intent(mContext, WebActivity.class).putExtra("title", agreementBeen.get(0).getAgreementName()).
                                              putExtra("url", agreementBeen.get(0).getMobileViewUrl()).putExtra("flag", false));
//                                }else if(type.equals("6")){
//                                    getActivity().startActivity(new Intent(getActivity(), WebActivity.class).putExtra("title","关于我们").
//                                     putExtra("url", HttpConfig.Pic_URL+UserInfoConfig.WebUrl_abountus).putExtra("flag",false));
//                                }
                                  }
                              }

                              @Override
                              public void onFail(String fail) {
                                  if (!TextUtils.isEmpty(fail)) ToastUtil.ToastShort(mContext, fail);
                              }
                          }

                );
    }
}
