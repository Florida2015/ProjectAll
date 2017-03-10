package com.hxxc.user.app.ui.mine.assetsrecord;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.PaymentRecordsBean;
import com.hxxc.user.app.data.entity.UnCollectedIncomeEntity;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.BaseActivity2;
import com.hxxc.user.app.utils.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqun on 2017/2/22.
 */

public class UnCollectedIncomeActivity2 extends BaseActivity2 {

    @BindView(R.id.num_1)
     TextView num_1;
    @BindView(R.id.num_2)
     TextView num_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncollectedincome);

        LinearLayout ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_content.addView(new CRecyclerView(this)
                .setParams("status", 2 + "")
                .setRow(10)
                .setView(UnCollectedIncomeEntity.class)
                .start());

        setTitle("待收收益");

        ButterKnife.bind(this);
//        num_1 = (TextView) findViewById(R.id.num_1);
//        num_2 = (TextView) findViewById(R.id.num_2);

        getData();
    }

    private void getData() {
        ApiManager.getInstance().getPaymentRecords(null, new SimpleCallback<PaymentRecordsBean>() {
            @Override
            public void onNext(PaymentRecordsBean paymentRecordsBean) {
                if (null == paymentRecordsBean) return;
                num_1.setText(CommonUtil.moneyType(paymentRecordsBean.getReturnPrincipalCount() == null ? 0 : paymentRecordsBean.getReturnPrincipalCount().doubleValue()));
                num_2.setText(CommonUtil.moneyType(paymentRecordsBean.getReturnPaymentCount() == null ? 0 : paymentRecordsBean.getReturnPaymentCount().doubleValue()));
            }

            @Override
            public void onError() {

            }
        });
    }
}
