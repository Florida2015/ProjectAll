package com.hxxc.user.app.ui.mine.withdraw;

import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AccountInfo;
import com.hxxc.user.app.data.bean.WithDrawBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Subscriber;

/**
 * Created by houwen.lai on 2016/11/8.
 * 提现
 */

public class WithDrawActivity extends BaseRxActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_look_detail)
    TextView tv_look_detail;
    @BindView(R.id.tv_balance_withdraw)
    TextView tv_balance_withdraw;
    @BindView(R.id.relative_bank_choise)
    RelativeLayout relative_bank_choise;
    @BindView(R.id.img_icon_bank)
    ImageView img_icon_bank;
    @BindView(R.id.tv_icon_bank)
    TextView tv_icon_bank;
    @BindView(R.id.tv_bank_context)
    TextView tv_bank_context;
    @BindView(R.id.tv_withdraw_money)
    TextView tv_withdraw_money;

    @BindView(R.id.ed_withdraw)
    EditText ed_withdraw;
    @BindView(R.id.tv_waring_withdraw)
    TextView tv_waring_withdraw;

    @BindView(R.id.btn_apple_withdraw)
    FancyButton btn_apple_withdraw;



    @Override
    public int getLayoutId() {
        return R.layout.with_draw_activity;
    }

    @Override
    public void initView() {
        toolbar_title.setText("账户提现");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        getAccountMessage();
        setInitData();
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

    @OnClick({R.id.tv_look_detail,R.id.relative_bank_choise,R.id.btn_apple_withdraw})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_look_detail://查看明细
                if (BtnUtils.isFastDoubleClick()){

                }
                break;
            case R.id.relative_bank_choise://选择支付卡
                if (BtnUtils.isFastDoubleClick()){

                }
                break;
            case R.id.btn_apple_withdraw://提现
                if (BtnUtils.isFastDoubleClick()){
                    setWithDraw();
                }
                break;
        }

    }

    public void setInitData(){

        String remaind_money = "6,1000";
        final SpannableStringBuilder sp = new SpannableStringBuilder(remaind_money+"\n账户余额(元)");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, remaind_money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_fbb0)), remaind_money.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(16, true), 0,remaind_money.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        sp.setSpan(new AbsoluteSizeSpan(12, true), remaind_money.length(), sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_balance_withdraw.setText(sp);

        //可支付银行卡信息

//                Picasso.with(mContext)
//                .load("url")
//                .placeholder(R.mipmap.icon_my_head)
//                .error(R.mipmap.icon_my_head)
//                .into( img_icon_bank);

        tv_icon_bank.setText("工商银行");
        tv_bank_context.setText("尾号1236");
        tv_withdraw_money.setText("可提现：1200元");

        tv_waring_withdraw.setText("单笔提现50以上，提现免手续费。\n提现到账时间一般不超过3个工作日，节假日顺延。");
    }


    /**
     * 获取我的账户信息
     */
    public void getAccountMessage(){
        Api.getClient().getMyAccountInfo("1").compose(RxApiThread.convert()).subscribe(new Subscriber<BaseResult<AccountInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.d("数据异常");
            }

            @Override
            public void onNext(BaseResult<AccountInfo> baseResult) {
                LogUtil.d("onNext="+baseResult.getData());
                if (baseResult.getSuccess()){
                    String remaind_money = "6,1000"+baseResult.getData().getRemainAmount();
                    final SpannableStringBuilder sp = new SpannableStringBuilder(remaind_money+"\n账户余额(元)");
                    sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, remaind_money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                    sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_fbb0)), remaind_money.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
                    sp.setSpan(new AbsoluteSizeSpan(16, true), 0,remaind_money.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
                    sp.setSpan(new AbsoluteSizeSpan(12, true), remaind_money.length(), sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
                    tv_balance_withdraw.setText(sp);
                }else{

                }
            }
        });


    }

/**
 * 提现
 */
    public void setWithDraw(){
        Api.getClient().getDoWithDrawals("1","1200").compose(RxApiThread.convert()).subscribe(new Subscriber<BaseResult<WithDrawBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResult<WithDrawBean> baseResult) {
                if (baseResult.getSuccess()){

                }else {

                }
            }
        });



    }


}
