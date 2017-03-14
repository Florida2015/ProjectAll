package com.hxxc.huaxing.app.ui.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.HXXCApplication;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.data.bean.AssetsBean;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseFragment;
import com.hxxc.huaxing.app.ui.dialogfragment.DialogMineOpenEFragment;
import com.hxxc.huaxing.app.ui.home.HomeActivity;
import com.hxxc.huaxing.app.ui.mine.account.EAcountActivity;
import com.hxxc.huaxing.app.ui.mine.account.OpenEAccountActivity;
import com.hxxc.huaxing.app.ui.mine.account.RechargeActivity;
import com.hxxc.huaxing.app.ui.mine.account.WithdrawalsActivity;
import com.hxxc.huaxing.app.ui.mine.autobid.OpenAutoBidActivity;
import com.hxxc.huaxing.app.ui.mine.financial.FinancialDetailActivity;
import com.hxxc.huaxing.app.ui.mine.lend.MineLendActivity;
import com.hxxc.huaxing.app.ui.mine.more.MineMoreActivity;
import com.hxxc.huaxing.app.ui.mine.notify.NotifyActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.LoginActivity;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.DisplayUtil;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.MoneyUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.utils.ToastUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static java.math.BigDecimal.ROUND_DOWN;

/**
 * Created by houwen.lai on 2016/9/21.
 * 我的
 */
public class MineFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.tv_recharge)
    TextView tv_recharge;
    @BindView(R.id.tv_withdraw_cash)
    TextView tv_withdraw_cash;

    @BindView(R.id.swipe_refresh_layout_mine)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.tv_income_money)
    TextView tv_income_money;
    @BindView(R.id.tv_total_assets)
    TextView tv_total_assets;
    @BindView(R.id.tv_remainder_money)
    TextView tv_remainder_money;


    private int[] resouce_mine = new int[]{R.mipmap.home_code,R.mipmap.home_code,R.mipmap.home_code,
            R.mipmap.home_code,R.mipmap.home_code,R.mipmap.home_code};//资源文件

    private String[] type = new String[]{"我的出借","资金明细","自动投标","E账户","我的理财师","关于我们"};

    public static MineFragment newInstance(String users) {
        Bundle arguments = new Bundle();
        arguments.putString("user",users);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void initDagger() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.home_mine_fragment1;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = DisplayUtil.getStatusBarHeight(mContext);
            view.setPadding(0, statusBarHeight, 0, 0);
        }

        refreshLayout.setColorSchemeResources(R.color.orange_BE7F,
                R.color.orange_BE7F);//, android.R.color.holo_orange_light,
//        android.R.color.holo_red_light
        refreshLayout.setOnRefreshListener(this);
//        refreshLayout.setRefreshing(true);
//        refreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//            }
//        }, 300);
        onRefresh();
    }


    @OnClick({R.id.linear_capitail,R.id.toolbar_mine,R.id.toolbar_setting,R.id.toolbar_warning,R.id.tv_recharge,R.id.tv_withdraw_cash,
            R.id.tv_recharge_out,R.id.tv_funds_detail,R.id.tv_auto_bid,R.id.tv_e_account,
            R.id.tv_my_financial,R.id.tv_about_us})
    public void onClick(View view){
        Intent mIntent = new Intent();
        switch (view.getId()){
            case R.id.linear_capitail://我的资产
                mIntent.setClass(getActivity(),MineFundsActivity.class);
                startActivity(mIntent);
                break;
            case R.id.toolbar_mine://个人信息SharedPreUtils.getInstanse().putKeyValue(mContext,UserInfoConfig.FlagLogin,true);
                if (SharedPreUtils.getInstanse().getKeyValue_b(getActivity(), UserInfoConfig.FlagLogin)){
                    mIntent.setClass(getActivity(),MineInformationActivity.class);
                    getActivity().startActivityForResult(mIntent,UserInfoConfig.Request_user_info);
                }else {

                }
                break;
            case R.id.toolbar_setting://设置

                break;
            case R.id.toolbar_warning://提示
                startActivity(new Intent(getActivity(), NotifyActivity.class));

                break;
            case R.id.tv_recharge://充值
                mIntent.setClass(getActivity(),RechargeActivity.class);
                if (assetsBeans!=null){
                    mIntent.putExtra("money", MoneyUtil.addComma(assetsBeans.getAvailablebal(),2,ROUND_DOWN));
                }else mIntent.putExtra("money", "0.00");
                startActivity(mIntent);
                break;
            case R.id.tv_withdraw_cash://提现
                mIntent.setClass(getActivity(), WithdrawalsActivity.class);
                if (assetsBeans!=null) {
                    mIntent.putExtra("money", MoneyUtil.addComma(assetsBeans.getAvailablebal(),2,ROUND_DOWN));
                    mIntent.putExtra("money1", MoneyUtil.addComma(assetsBeans.getFrozbl(),2,ROUND_DOWN));
                }else{
                    mIntent.putExtra("money", "0.00");
                    mIntent.putExtra("money1", "0.00");
                }
                startActivity(mIntent);
                break;
            case R.id.tv_recharge_out://我的出借
                mIntent.setClass(getActivity(),MineLendActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_funds_detail://资金明细
                mIntent.setClass(getActivity(),CapitalDetailActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_auto_bid://自动投标
//                mIntent.setClass(getActivity(),OpenAutoBidActivity.class);
//                startActivity(mIntent);
                if (BtnUtils.isFastDoubleClick())
                ToastUtil.ToastShort(getActivity(),"自动投标开发中...");
                break;
            case R.id.tv_e_account://E账户
                if (BtnUtils.isFastDoubleClick()){
                    InitOpenE();
//                    getUserFunds();
                }
                break;
            case R.id.tv_my_financial://我的理财师
                mIntent.setClass(getActivity(),FinancialDetailActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tv_about_us://更多
                mIntent.setClass(getActivity(),MineMoreActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        //刷新数据
        refreshLayout.setRefreshing(false);
//        refreshLayout.postDelayed(() -> refreshLayout.setRefreshing(false), 1000);
        getUserFunds();
        refreshData();
    }

    public void initAssetsData(BigDecimal incomeMoney, BigDecimal total_assets, BigDecimal remainder_money){
        tv_income_money.setText(MoneyUtil.addComma(incomeMoney,2,ROUND_DOWN));
        tv_total_assets.setText(MoneyUtil.addComma(total_assets,2,ROUND_DOWN));
        tv_remainder_money.setText(MoneyUtil.addComma(remainder_money,2,ROUND_DOWN));
    }

    boolean flag = true;
    /**
     * 是否开通E账号
     *
     */
    public void InitOpenE(){
        int isOpen = SharedPreUtils.getInstanse().getKeyValue_i(getActivity(),UserInfoConfig.spOpenEaccount);
        LogUtil.d("ddd="+isOpen+"__getopenAccount="+UserInfoConfig.getOpenAcount);
        if (isOpen == 1){
            Intent mIntent = new Intent();
            mIntent.setClass(getActivity(),EAcountActivity.class);
            startActivity(mIntent);
        }else {
//            SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.spEaccountClick, 1);
//            DialogMineOpenEFragment dialogMineOpenEFragment =  new DialogMineOpenEFragment().newInstance();
//            dialogMineOpenEFragment.show(getFragmentManager(), "updateAppDialogFragment");
            //开通E账号
            startActivity(new Intent(mContext, OpenEAccountActivity.class));
        }
    }
    AssetsBean assetsBeans;
    /**
     * 获取用户资产信息
     */
    public void getUserFunds(){
        if (TextUtils.isEmpty(Api.uid))return;
        Api.getClient().getUserAssets(Api.uid).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<AssetsBean>(getActivity()) {
                    @Override
                    public void onSuccess(AssetsBean assetsBean) {
                        assetsBeans = assetsBean;
                        if (assetsBean!=null){
                            initAssetsData(assetsBean.getExpectedReturn(),assetsBean.getAccountTotalAssets(),assetsBean.getAvailablebal());
                        }else  initAssetsData(BigDecimal.valueOf(0.00),BigDecimal.valueOf(0.00),BigDecimal.valueOf(0.00));
                    }

                    @Override
                    public void onFail(String fail) {
                        initAssetsData(BigDecimal.valueOf(0.00),BigDecimal.valueOf(0.00),BigDecimal.valueOf(0.00));
                    }
                });
    }

    public void refreshData() {
        if (TextUtils.isEmpty(Api.uid))return;
        Api.getClient().getUserInfo(Api.uid).
                compose(RxApiThread.convert()).
                subscribe( new Subscriber<BaseBean<UserInfoBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onNext(BaseBean<UserInfoBean> stringBaseBean) {
                SharedPreUtils.getInstanse().putKeyValue(HXXCApplication.getInstance(), UserInfoConfig.UserInfo, new Gson().toJson(stringBaseBean));
                if (stringBaseBean.getStatusCode().equals(UserInfoConfig.http_error_out)||stringBaseBean.getStatusCode().equals(UserInfoConfig.ErrorCode_login_out)){
                    ToastUtil.ToastShort(getActivity(),getActivity().getResources().getString(R.string.text_login_out));
                    if (HomeActivity.homeActivity!=null) HomeActivity.homeActivity.setCommonTabLayoutIndex(0);
                    AppManager.getAppManager().ExitLogin();
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                if(null != stringBaseBean){
                    SharedPreUtils.getInstanse().putKeyValue(mContext,UserInfoConfig.spOpenEaccount,stringBaseBean.getModel().getIsOpenEaccount());
                    UserInfoConfig.getOpenAcount = stringBaseBean.getModel().getIsOpenEaccount();
                    int isclick = SharedPreUtils.getInstanse().getKeyValue_i(mContext, UserInfoConfig.spEaccountClick);
                    if (UserInfoConfig.getOpenAcount==0&&isclick!=1){
                        SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.spEaccountClick, 1);
                        DialogMineOpenEFragment dialogMineOpenEFragment =  new DialogMineOpenEFragment().newInstance();
                        dialogMineOpenEFragment.show(getFragmentManager(), "updateAppDialogFragment");
                    }
                }
            }
        });
    }
}
