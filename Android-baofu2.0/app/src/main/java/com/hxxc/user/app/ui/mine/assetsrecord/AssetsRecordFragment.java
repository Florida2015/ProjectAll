package com.hxxc.user.app.ui.mine.assetsrecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.AccountInfo;
import com.hxxc.user.app.listener.AssetsFragmentListener;
import com.hxxc.user.app.ui.base.BaseRxFragment;
import com.hxxc.user.app.ui.dialogfragment.SignDialogListener;
import com.hxxc.user.app.ui.mine.invitation.InvitationListFragment;
import com.hxxc.user.app.ui.mine.tradelist.TradeListActivity;
import com.hxxc.user.app.ui.order.OrderListActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.DateUtil;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.widget.piechart_2.IPieElement;
import com.hxxc.user.app.widget.piechart_2.PieChart;
import com.hxxc.user.app.widget.piechart_2.TestEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2017/2/14.
 * 资产分析
 *
 */

public class AssetsRecordFragment extends BaseRxFragment{

    @BindView(R.id.img_left)
    ImageView img_left;
    @BindView(R.id.img_riht)
    ImageView img_riht;

    @BindView(R.id.pieChart)
    PieChart pieChart;

    @BindView(R.id.tv_account_balance)
    TextView tv_account_balance;

    @BindView(R.id.tv_calender)
    TextView tv_calender;

    @BindView(R.id.relative_collect)
    RelativeLayout relative_collect;
    @BindView(R.id.tv_collect_p)
    TextView tv_collect_p;
    @BindView(R.id.tv_collect_principal_proportion)
    TextView tv_collect_principal_proportion;
    @BindView(R.id.tv_collect_principal)
    TextView tv_collect_principal;

    @BindView(R.id.relative_frozen)
    RelativeLayout relative_frozen;
    @BindView(R.id.tv_frozen_p)
    TextView tv_frozen_p;
    @BindView(R.id.tv_frozen_principal_proportion)
    TextView tv_frozen_principal_proportion;
    @BindView(R.id.tv_frozen_principal)
    TextView tv_frozen_principal;

    @BindView(R.id.relative_collect_income)
    RelativeLayout relative_collect_income;
    @BindView(R.id.tv_collect_income_p)
    TextView tv_collect_income_p;
    @BindView(R.id.tv_collect_income_proportion)
    TextView tv_collect_income_proportion;
    @BindView(R.id.tv_collect_income_principal)
    TextView tv_collect_income_principal;

    private boolean flag = true;

    private AccountInfo accountInfo;

    AssetsFragmentListener listener ;


    public static AssetsRecordFragment newInstance(boolean flag,AccountInfo accountInfo) {
        Bundle arguments = new Bundle();
        arguments.putBoolean("flag",flag);
        arguments.putSerializable("account",accountInfo);
        AssetsRecordFragment fragment = new AssetsRecordFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (AssetsFragmentListener) getActivity();
    }

    @Override
    protected void initDagger() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.assets_record_fragment;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {
        flag = getArguments().getBoolean("flag");
        accountInfo = (AccountInfo) getArguments().getSerializable("account");

        if (flag){//账号总资产
            setAssetsData(accountInfo);
        }else{//总收益
            setAssetsIncomeData(accountInfo);
        }

    }
//         MoneyUtil.formatMoney(accountInfo.getCumulativeProfit(), 2, ROUND_FLOOR),
//         MoneyUtil.formatMoney(accountInfo.getTatalAmount(), 2, ROUND_FLOOR),
//         MoneyUtil.formatMoney(accountInfo.getYesterdayProfit(), 2, ROUND_FLOOR),
//         MoneyUtil.formatMoney(accountInfo.getRemainAmount(), 2, ROUND_FLOOR));//设置金额
    /**
     *  账号总资产
     */
    public void setAssetsData(AccountInfo assetsData){

        img_left.setEnabled(false);
        img_riht.setEnabled(true);
        img_left.setImageResource(R.mipmap.left_n);
        img_riht.setImageResource(R.mipmap.right_s);

        String sum_award = "0.00";
        if (assetsData==null){
            sum_award="0.00";
        }else {
            sum_award = MoneyUtil.addComma(assetsData.getTatalAmount(),2,ROUND_FLOOR);
        }

        SpannableStringBuilder sp = new SpannableStringBuilder(""+sum_award+"\n账户总资产(元)");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, sum_award.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white_d2e5)), sum_award.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(18, true), 0, sum_award.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        sp.setSpan(new AbsoluteSizeSpan(12, true),sum_award.length(), sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_account_balance.setText(sp);

        tv_collect_p.setText("待收本金");
        tv_frozen_p.setText("冻结资金");
        tv_collect_income_p.setText("待收收益");

        float sum = 1f;
        if(assetsData==null){
            initData(100f,
                    0.01f,
                    0.01f);

            tv_collect_principal_proportion.setText("0%");
            tv_collect_principal.setText("0.00");

            tv_frozen_principal_proportion.setText( "0%");
            tv_frozen_principal.setText("0.00");

            tv_collect_income_proportion.setText("0%");
            tv_collect_income_principal.setText("0.00");
        }else{
            sum = assetsData.getTatalAmount().floatValue()+0.01f;

            if (sum<0.011f||(assetsData.getPrincipalAmount().floatValue()<0.01f&&assetsData.getFrozenAmount().floatValue()<0.01f&&assetsData.getCollectProfit().floatValue()<0.01f))
            initData(100f,0.01f,0.01f);
           else if(assetsData.getPrincipalAmount().floatValue()<0.01f&&assetsData.getFrozenAmount().floatValue()<0.01f&&assetsData.getCollectProfit().floatValue()<0.01f)
                initData(100f,0.01f,0.01f);
            else  initData(100*(assetsData.getPrincipalAmount().floatValue()/sum),
                    100*(assetsData.getFrozenAmount().floatValue()/sum),
                    100*(assetsData.getCollectProfit().floatValue()/sum));

            String principalAmount = DateUtil.getInstanse().getOne(100*(assetsData.getPrincipalAmount().floatValue()/sum));
            tv_collect_principal_proportion.setText(sum<0.011f?"0.0%":principalAmount + "%");
            tv_collect_principal.setText(MoneyUtil.formatMoney(accountInfo.getPrincipalAmount(), 2, ROUND_FLOOR));

            String frozenAmount = DateUtil.getInstanse().getOne(100*(assetsData.getFrozenAmount().floatValue()/sum));
            tv_frozen_principal_proportion.setText(sum<0.011f?"0.0%":frozenAmount + "%");
            tv_frozen_principal.setText(MoneyUtil.formatMoney(accountInfo.getFrozenAmount(), 2, ROUND_FLOOR));

            String collectProfit = DateUtil.getInstanse().getOne(100*(assetsData.getCollectProfit().floatValue()/sum));
            tv_collect_income_proportion.setText(sum<0.011f?"0.0%":collectProfit + "%");
            tv_collect_income_principal.setText(MoneyUtil.formatMoney(accountInfo.getCollectProfit(), 2, ROUND_FLOOR));
        }

    }
    /**
     *  总收益
     */
    public void setAssetsIncomeData(AccountInfo account){
        img_left.setEnabled(true);
        img_riht.setEnabled(false);
        img_left.setImageResource(R.mipmap.left_s);
        img_riht.setImageResource(R.mipmap.right_n);

        String sum_award = "0.00";
        if (account==null){
            sum_award="0.00";
        }else{
            sum_award = MoneyUtil.addComma(account.getTatalProfit(),2,ROUND_FLOOR);
        }

        SpannableStringBuilder sp = new SpannableStringBuilder(""+sum_award+"\n预计总收益(元)");
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, sum_award.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white_d2e5)), sum_award.length(), sp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体颜色
        sp.setSpan(new AbsoluteSizeSpan(22, true), 0, sum_award.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        sp.setSpan(new AbsoluteSizeSpan(12, true),sum_award.length(), sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //字体大小
        tv_account_balance.setText(sp);

        tv_collect_p.setText("待收收益");
        tv_frozen_p.setText("到账收益");
        tv_collect_income_p.setText("未赚收益");

        if (account==null){
            initDataIn(100f,0.01f,0.01f);

            tv_collect_principal_proportion.setText("0%");
            tv_collect_principal.setText("0.00");

            tv_frozen_principal_proportion.setText("0%");
            tv_frozen_principal.setText("0.00");

            tv_collect_income_proportion.setText("0%");
            tv_collect_income_principal.setText("0.00");
        }else {
            float sum = account.getTatalProfit().floatValue()+0.01f;
            if (sum<0.011f||(account.getCollectProfit().floatValue()<0.01f&&account.getArrivalProfit().floatValue()<0.01f&&account.getUncollectedTotalProfit().floatValue()<0.01f))
                initDataIn(100f,0.01f,0.01f);
            else if(account.getCollectProfit().floatValue()<0.01f&&account.getArrivalProfit().floatValue()<0.01f&&account.getUncollectedTotalProfit().floatValue()<0.01f)
                initDataIn(100f,0.01f,0.01f);
            else  initDataIn(100*(account.getCollectProfit().floatValue()/sum),
                    100*(account.getArrivalProfit().floatValue()/sum),
                    100*(account.getUncollectedTotalProfit().floatValue()/sum));

            String principalAmount = DateUtil.getInstanse().getOne(100*(account.getCollectProfit().floatValue()/sum));

            tv_collect_principal_proportion.setText(sum<0.011f?"0.0%": principalAmount+ "%");
            tv_collect_principal.setText(MoneyUtil.formatMoney(accountInfo.getCollectProfit(), 2, ROUND_FLOOR));

            String frozenAmount = DateUtil.getInstanse().getOne(100*(account.getArrivalProfit().floatValue()/sum));
            tv_frozen_principal_proportion.setText(sum<0.011f?"0.0%": frozenAmount + "%");
            tv_frozen_principal.setText(MoneyUtil.formatMoney(accountInfo.getArrivalProfit(), 2, ROUND_FLOOR));

            String collectProfit = DateUtil.getInstanse().getOne(100*(account.getUncollectedTotalProfit().floatValue()/sum));
            tv_collect_income_proportion.setText(sum<0.011f?"0.0%": collectProfit+ "%");
            tv_collect_income_principal.setText(MoneyUtil.formatMoney(accountInfo.getUncollectedTotalProfit(), 2, ROUND_FLOOR));
        }

    }

    @OnClick({R.id.tv_calender,R.id.img_left,R.id.img_riht,R.id.relative_collect,R.id.relative_frozen,R.id.relative_collect_income})
    public void onClick(View view){
//        if (!BtnUtils.isFastDoubleClick())return;
        switch (view.getId()){
            case R.id.tv_calender://回款记录
                startActivity(new Intent(mContext, BackAssetsRecordActivity2.class));
                break;
            case R.id.img_left:
                listener.onClickId(true);
                break;
            case R.id.img_riht:
                listener.onClickId(false);
                break;

//            case R.id.tv_calender:
//                startActivity(new Intent(mContext,ClanderActivity.class));
//
//                break;
            case R.id.relative_collect:
                if (flag){//待收本金 调到 订单列表 持有中
                    startActivity(new Intent(mContext, OrderListActivity.class).putExtra("index",0));

                }else{//待收收益
//                    startActivity(new Intent(mContext, UnCollectedIncomeActivity2.class));
                }
                break;
            case R.id.relative_frozen:
                if (flag){//冻结资金 调到 订单列表 退出中
                    startActivity(new Intent(mContext, OrderListActivity.class).putExtra("index",1));

                }else{//到账收益 调到 交易记录 收入
                    startActivity(new Intent(mContext, TradeListActivity.class).putExtra("index",1));
                }
                break;
            case R.id.relative_collect_income:
                if (flag){//待收收益
//                    startActivity(new Intent(mContext, UnCollectedIncomeActivity2.class));

                }else{//未赚收益

                }
                break;
        }
    }

    private void initData(float value_1,float value_2,float value_3){
        TestEntity entity=new TestEntity(value_1<0.1f?0.1f:value_1,"#FFffff");//
        TestEntity entity1=new TestEntity(value_2<0.1f?0.1f:value_2,"#fffbb02b");//
        TestEntity entity2=new TestEntity(value_3<0.1f?0.1f:value_3,"#ff29cdb5");//
        List<IPieElement> list=new ArrayList<>();
        list.add(entity);
        list.add(entity1);
        list.add(entity2);
        pieChart.setData(list);
    }
    private void initDataIn(float value_1,float value_2,float value_3){
        TestEntity entity=new TestEntity(value_1<0.1f?0.1f:value_1,"#FFffff");//
        TestEntity entity1=new TestEntity(value_2<0.1f?0.1f:value_2,"#fffbb02b");//
        TestEntity entity2=new TestEntity(value_3<0.1f?0.1f:value_3,"#ff29cdb5");//
        List<IPieElement> list=new ArrayList<>();
        list.add(entity);
        list.add(entity1);
        list.add(entity2);
        pieChart.setData(list);
    }
}
