package com.hxxc.user.app.ui.mine.tradelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxxc.user.app.Event.CyclerEvent;
import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.data.bean.TradeRecordBean;
import com.hxxc.user.app.ui.vh.TradeVH;
import com.hxxc.user.app.utils.MoneyUtil;
import com.hxxc.user.app.widget.trecyclerview.BaseViewHolder;
import com.hxxc.user.app.widget.trecyclerview.TRecyclerView;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by houwen.lai on 2016/11/8.
 *
 * 交易记录
 */

public class TradeFragment extends Fragment {

    private TRecyclerView mXRecyclerView;

    private RelativeLayout relative_integral_month;
    private TextView tv_integral_month;
    private TextView tv_integral_useing;
    private TextView tv_integral_getting;

    private String status;

    public static TradeFragment newInstance(String orderStatus) {
        Bundle arguments = new Bundle();
        arguments.putString("status",orderStatus);
        TradeFragment fragment = new TradeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mXRecyclerView = new TRecyclerView(getActivity());
//        mXRecyclerView.setParam("trade",getArguments().getString("status"));
//        mXRecyclerView.setView(TradeVH.class);
//        return mXRecyclerView;

        if (container == null) {
            // Currently in a layout without a container, so no
            // reason to create our view.
            return null;
        }
        LayoutInflater myInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = myInflater.inflate(R.layout.trade_fragment, container,
                false);

        tv_integral_month = (TextView) view.findViewById(R.id.tv_integral_month);
        tv_integral_useing= (TextView) view.findViewById(R.id.tv_integral_useing);
        tv_integral_getting= (TextView) view.findViewById(R.id.tv_integral_getting);

        mXRecyclerView = (TRecyclerView) view.findViewById(R.id.tvCyclerView);
        mXRecyclerView.setParam("trade",getArguments().getString("status"));

        mXRecyclerView.setView(TradeVH.class);
        status = getArguments().getString("status");
        mXRecyclerView.setReturnFirstPosition(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mXRecyclerView != null) mXRecyclerView.fetch();

        if (mXRecyclerView.getAdapter()==null)return;


        mXRecyclerView.mRxManage.on(CyclerEvent.class.getSimpleName(), (o) ->  setFirstVisiablePosition(o) );
    }

    //处理订阅事件
    public void setFirstVisiablePosition(Object o) {

        int index = ((CyclerEvent)o).getFirstVisiablePosition();
        Log.e("cyc",""+index);

        if (mXRecyclerView.getAdapter()!=null&&mXRecyclerView.getAdapter().getPositionData(index)!=null){
            TradeRecordBean bean = (TradeRecordBean) mXRecyclerView.getAdapter().getPositionData(index);
            tv_integral_month.setText(bean.getMonth()+"月累计");
            tv_integral_useing.setText("回款:"+MoneyUtil.addComma(bean.getBackMoney(),2,ROUND_FLOOR));
            tv_integral_getting.setText("出借:"+ MoneyUtil.addComma(bean.getPayMoney(),2,ROUND_FLOOR));

        }

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
