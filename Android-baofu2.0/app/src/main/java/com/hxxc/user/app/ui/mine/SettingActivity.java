package com.hxxc.user.app.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.SettingEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.FinancialPlanner;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.financial.FinancialDetailActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.ui.mine.bankcard.BankCardListActivity;
import com.hxxc.user.app.ui.mine.membercenter.MemberCenterActivity;
import com.hxxc.user.app.ui.mine.setting.AboutUsActivity;
import com.hxxc.user.app.ui.mine.setting.FeedbackActivity;
import com.hxxc.user.app.ui.mine.setting.HelpCenterActivity;
import com.hxxc.user.app.ui.product.AuthenticationActivity;
import com.hxxc.user.app.ui.product.BaofuBingCardActivity;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.ToastUtil;
import com.hxxc.user.app.widget.LeftAndRightTextView;
import com.hxxc.user.app.widget.MyClickButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by chenqun on 2016/10/27.
 * 账户设置
 */

public class SettingActivity extends ToolbarActivity {
    @BindView(R.id.tv_vip)//会员中心
            TextView tv_vip;
    @BindView(R.id.tv_account)//账户安全
            TextView tv_account;
    @BindView(R.id.tv_bank)//银行卡
            TextView tv_bank;
    @BindView(R.id.tv_financer)//我的顾问
            TextView tv_financer;

    @BindView(R.id.item_ceping)//我的顾问
            LeftAndRightTextView item_ceping;

    @BindView(R.id.exit_login_btn)//退出登录
            MyClickButton exit_login_btn;
    private Dialog systemDialog;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("账户设置");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        exit_login_btn.setOnMyClickListener(new MyClickButton.MyClickListener() {
            @Override
            public void onMyClickListener() {
                showExitDialog();
            }
        });
        initText();
    }

    private void initText() {
        UserInfo userInfo = SPUtils.geTinstance().getUserInfo();
        if (null != userInfo) {
            tv_vip.setText(userInfo.getMemberIntegralCaifuVO()==null?"":userInfo.getMemberIntegralCaifuVO().getIdentityName());
            tv_account.setText(userInfo.getSecurityLevel());
            tv_bank.setText(userInfo.getBandCardCount() + "张");

            if (userInfo.getIsQuiz() == 0)
                item_ceping.setRightText("未测评");
            else if (userInfo.getQuizUserResult() != null && userInfo.getQuizUserResult().getQuizResultConfig() != null)
                item_ceping.setRightText(userInfo.getQuizUserResult().getQuizResultConfig().getCustTypeStr());
            else item_ceping.setRightText("");
        }
        FinancialPlanner financer = SPUtils.geTinstance().getFinancer();
        if (null != financer) {
            tv_financer.setText(financer.getFname());
        }
    }

    private void showExitDialog() {
        if (null == systemDialog) {
            systemDialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(SettingActivity.this, R.layout.exit_dialog, null);
            TextView tv_quxiao = (TextView) inflate.findViewById(R.id.tv_quxiao);
            TextView tv_queding = (TextView) inflate.findViewById(R.id.tv_queding);

            tv_quxiao.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != systemDialog) {
                        systemDialog.dismiss();
                    }

                }
            });
            tv_queding.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Midhandler.exitLogin(SettingActivity.this);
                    if (null != systemDialog) {
                        systemDialog.dismiss();
                    }
                    SettingActivity.this.finish();
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
    }

    @OnClick({R.id.item_1, R.id.item_2, R.id.item_3, R.id.item_4, R.id.item_5
            , R.id.item_6, R.id.item_7, R.id.item_8, R.id.item_ceping})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_1://个人信息
                startActivity(new Intent(SettingActivity.this, UserInfoActivity.class));
                break;
            case R.id.item_2://会员中心
                startActivity(new Intent(this, MemberCenterActivity.class));
                break;
            case R.id.item_3://账户安全
                startActivity(new Intent(SettingActivity.this, SafeProtectActivity.class));
                break;
            case R.id.item_4://银行卡
                UserInfo userInfo = SPUtils.geTinstance().getUserInfo();
                if (userInfo!=null&&userInfo.getRnaStatus() == 0) {
                    ToastUtil.showSafeToast(this, "请认证");
                    startActivity(new Intent(SettingActivity.this, AuthenticationActivity.class).putExtra("from", AuthenticationActivity.FROM_MyBankCard));
                }else if(userInfo!=null&&userInfo.getRnaStatus() == 1&&userInfo.getBindcardStatus()==0){
                    startActivity(new Intent(SettingActivity.this, BaofuBingCardActivity.class));
                }else  if(userInfo!=null&&userInfo.getRnaStatus() == 1&&userInfo.getBindcardStatus()==1) {
                    startActivity(new Intent(SettingActivity.this, BankCardListActivity.class));
                }
                break;
            case R.id.item_5://帮助中心
                startActivity(new Intent(SettingActivity.this, HelpCenterActivity.class));
                break;
            case R.id.item_6://意见反馈
                startActivity(new Intent(SettingActivity.this, FeedbackActivity.class));
                break;
            case R.id.item_7://我的顾问
                startActivity(new Intent(SettingActivity.this, FinancialDetailActivity.class));
                break;
            case R.id.item_8://关于华夏信财
                startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
                break;
            case R.id.item_ceping://TODO 风险测评
                UserInfo info = SPUtils.geTinstance().getUserInfo();
                Intent intent = new Intent(SettingActivity.this, AdsActivity.class);
                intent.putExtra("title", "风险测评");
                intent.putExtra("url", HttpRequest.indexBaseUrl + HttpRequest.riskTest + "?ifTest=" + info.getIsQuiz() + "&uid=" + info.getUid() + "&token=" + info.getToken() + "&channel=" + Constants.TypeChannel);
                startActivityForResult(intent, 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {//来自风险测评，刷新数据
            Midhandler.getUserInfo(null);
        }
    }

    public void onEventMainThread(SettingEvent event) {
        initText();
    }
}
