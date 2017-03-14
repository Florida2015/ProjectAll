package com.hxxc.huaxing.app.ui.mine.lend;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.adapter.CommonAdapter;
import com.hxxc.huaxing.app.data.bean.LendDetailItemBean;
import com.hxxc.huaxing.app.data.bean.LendDetailPaymentHistoryBean;
import com.hxxc.huaxing.app.ui.base.BaseFragment;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.MoneyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by Administrator on 2016/10/10.
 * 出借详情 回款记录
 *
 */

public class LendPaymentHistoryFragment extends BaseFragment {

//    private TRecyclerView mXRecyclerView;

    public static LendPaymentHistoryFragment newInstance(LendDetailItemBean beans) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("beans",beans);
        LendPaymentHistoryFragment fragment = new LendPaymentHistoryFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @BindView(R.id.tv_history_data)
    TextView tv_history_data;
    @BindView(R.id.tv_history_money)
    TextView tv_history_money;
    @BindView(R.id.tv_history_interest)
    TextView tv_history_interest;
    @BindView(R.id.tv_history_time)
    TextView tv_history_time;
    @BindView(R.id.tv_history_status)
    TextView tv_history_status;

    @BindView(R.id.tv_history_temp)
    TextView tv_history_temp;

    @BindView(R.id.lend_detail_history)
    ListView listview;

    List<LendDetailPaymentHistoryBean> lists;
    LendHistoryAdpter lendStatusAdpter;

    @Override
    protected void initDagger() {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.lend_detail_history_fragment;
    }

    @Override
    protected void initViewsAndEvents(View rootView) {

        tv_history_data.setText("期次");
        tv_history_money.setText("本金");
        tv_history_interest.setText("利息");
        tv_history_time.setText("时间");
        tv_history_status.setText("状态");

        listview.setDividerHeight(0);

        if (lists==null)lists = new ArrayList<LendDetailPaymentHistoryBean>();

        lists = ((LendDetailItemBean)getArguments().getSerializable("beans")).getPaymentHistory();

        if (lists!=null) LogUtil.d("lend detail = "+lists.toString());

        if (lists!=null&&lists.size()>0){
            tv_history_temp.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
        }else{
            tv_history_temp.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
        }

        lendStatusAdpter = new LendHistoryAdpter(getActivity(),lists,R.layout.lend_detail_history_item);
        listview.setAdapter(lendStatusAdpter);

    }

    /**
     *
     */
    public class LendHistoryAdpter extends CommonAdapter<LendDetailPaymentHistoryBean> {

        public LendHistoryAdpter(Context context, List<LendDetailPaymentHistoryBean> list, int layoutId) {
            super(context, list, layoutId);


        }

        @Override
        public void convert(ViewHolder helper, LendDetailPaymentHistoryBean item, int position) {
            super.convert(helper, item, position);

            if (position%2==0) helper.getView(R.id.linear_history_item).setBackgroundColor(getActivity().getResources().getColor(R.color.white_fafa));
            else helper.getView(R.id.linear_history_item).setBackgroundColor(getActivity().getResources().getColor(R.color.white));

            helper.setText(R.id.tv_history_data,item.getCurrPeriod()+"/"+ (TextUtils.isEmpty(item.getPeriods())?"":item.getPeriods()));

//            helper.setText(R.id.tv_history_money, MoneyUtil.addComma(item.getCapital(),0,ROUND_FLOOR));
//            helper.setText(R.id.tv_history_interest,MoneyUtil.addComma(item.getInterest(),2,ROUND_FLOOR));

            helper.setText(R.id.tv_history_money,TextUtils.isEmpty(item.getPayCapitalStr())?"0.00":item.getPayCapitalStr());
            helper.setText(R.id.tv_history_interest,TextUtils.isEmpty(item.getPayInterestStr())?"0.00":item.getPayInterestStr());

            helper.setText(R.id.tv_history_time, DateUtil.getmstodate(item.getUpdateTime(),DateUtil.YYYYMMDD));

            helper.setText(R.id.tv_history_status,TextUtils.isEmpty(item.getIsPayValue())?"":item.getIsPayValue());

            if(item.getIsPay()==2){//1付息中（还款中）2付息成功 （已还款）
                ((TextView)helper.getView(R.id.tv_history_status)).setTextColor(mContext.getResources().getColor(R.color.orange_e6c6));
            }else {
                ((TextView)helper.getView(R.id.tv_history_status)).setTextColor(mContext.getResources().getColor(R.color.black_aaaa));
            }

        }
    }

}
