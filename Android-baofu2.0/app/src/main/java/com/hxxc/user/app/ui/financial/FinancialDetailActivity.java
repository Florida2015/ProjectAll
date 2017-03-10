package com.hxxc.user.app.ui.financial;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.FinancialPlanner;
import com.hxxc.user.app.listener.IFinanceCallback;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.ImUtils;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.widget.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by houwen.lai on 2016/10/28.
 * 我的理财师
 */

public class FinancialDetailActivity extends BaseRxActivity {

    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.toolbar_title)
//    TextView toolbar_title;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.img_head)
    CircleImageView img_head;
    @BindView(R.id.tv_head_name)
    TextView tv_head_name;
    @BindView(R.id.tv_head_userno)
    TextView tv_head_userno;

    @BindView(R.id.tv_sum_money)
    TextView tv_sum_money;
    @BindView(R.id.tv_service_count)
    TextView tv_service_count;

    @BindView(R.id.tv_finan_center)
    TextView tv_finan_center;
    @BindView(R.id.tv_finan_email)
    TextView tv_finan_email;
    @BindView(R.id.tv_finan_phone)
    TextView tv_finan_phone;

    @BindView(R.id.btn_financial_message)
    Button btn_financial_message;
    @BindView(R.id.btn_financial_mobile)
    Button btn_financial_mobile;

    @Override
    public int getLayoutId() {
        return R.layout.financial_detial;
    }

    @Override
    public void initView() {
//        toolbar_title.setText("");
//        setSupportActionBar(toolbar);
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowTitleEnabled(false);
//        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FinancialPlanner financer = SPUtils.geTinstance().getFinancer();
        if (null != financer) {
            financialData(financer);
        }
        img_head.post(new Runnable() {
            @Override
            public void run() {
                Midhandler.refreshFinanceInfo(new IFinanceCallback() {
                    @Override
                    public void refreshFinance(FinancialPlanner financialPlanner) {
                        financialData(financialPlanner);
                    }
                });
            }
        });
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

    @OnClick({R.id.btn_financial_message, R.id.btn_financial_mobile})
    public void onClick(View view) {
        if (view.getId() == R.id.btn_financial_mobile) {
//            String phoneNum = (String) tv_finan_phone.getText();
//            if (TextUtils.isEmpty(phoneNum)) return;

            try {//Intent.ACTION_DIAL  Intent.ACTION_CALL
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4000-466-600"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 111);
                        return;
                    }
                }
                startActivity(intent);
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        }
        if (view.getId() == R.id.btn_financial_message) {//融云
            ImUtils.getInstance().startPrivateChat(this);
        }
    }

    public void financialData(FinancialPlanner bean) {
        if (bean == null) return;

        Picasso.with(mContext)
                .load(bean.getRealIcon())
                .placeholder(R.drawable.default_financier_pic)
                .error(R.drawable.default_financier_pic)
                .into(img_head);

        tv_head_name.setText(bean.getFname());
        tv_head_userno.setText(bean.getFinancialno());

        tv_sum_money.setText(CommonUtil.moneyType(bean.getInvestmentamout() / 10000f));

        tv_service_count.setText("" + bean.getServicecount());

        if (bean.getSysDepartmentVo() != null) {
            tv_finan_center.setText(bean.getSysDepartmentVo().getName());
        }
        tv_finan_email.setText(bean.getEmail());

        tv_finan_phone.setText(bean.getCountryPhone());
//        tv_finan_phone.setText(bean.getMobile());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4000-466-600")));
            }
        }
    }
}
