package com.hxxc.user.app.ui.mine.assetsrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huaxiafinance.www.crecyclerview.cindicatorview.CIndicatorView;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.PaymentRecordsBean;
import com.hxxc.user.app.rest.ApiManager;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.BaseActivity2;
import com.hxxc.user.app.ui.mine.setting.HelpCenterActivity;
import com.hxxc.user.app.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqun on 2017/2/22.
 * 回款记录
 */

public class BackAssetsRecordActivity2 extends BaseActivity2 {
    private String[] titles = {"待回款本金", "待回款收益"};
    private TextView num_1;
    private TextView num_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncollectedincome);
        findViewById(R.id.ll_title_view).setVisibility(View.GONE);

        LinearLayout ll_content = (LinearLayout) findViewById(R.id.ll_content);

        ImageButton toolbar_iv_share = (ImageButton) findViewById(R.id.toolbar_iv_share);
        toolbar_iv_share.setVisibility(View.VISIBLE);
        toolbar_iv_share.setImageResource(R.mipmap.ico_doubt);
        toolbar_iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BackAssetsRecordActivity2.this, HelpCenterActivity.class));
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(BackAssetsRecordFragment.newInstance(0));
        fragments.add(BackAssetsRecordFragment.newInstance(1));
        ll_content.addView(new CIndicatorView(this).initDatas(fragments, titles, getSupportFragmentManager()));
        setTitle("回款记录");

        num_1 = (TextView) findViewById(R.id.num_1);
        num_2 = (TextView) findViewById(R.id.num_2);

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
