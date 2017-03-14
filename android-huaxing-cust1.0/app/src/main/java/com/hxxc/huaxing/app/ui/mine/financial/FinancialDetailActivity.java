package com.hxxc.huaxing.app.ui.mine.financial;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.BaseBean;
import com.hxxc.huaxing.app.data.bean.FinancialDetailBean;
import com.hxxc.huaxing.app.retrofit.Api;
import com.hxxc.huaxing.app.retrofit.BaseSubscriber;
import com.hxxc.huaxing.app.retrofit.RxApiThread;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.utils.DateUtil;
import com.hxxc.huaxing.app.utils.MoneyUtil;
import com.hxxc.huaxing.app.wedgit.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static java.math.BigDecimal.ROUND_FLOOR;

/**
 * Created by Administrator on 2016/9/27.
 * 理财师信息
 */

public class FinancialDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

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

    @Override
    public int getLayoutId() {
        return R.layout.mine_financial_detail;
    }

    @Override
    public void initView() {
        toolbar_title.setText("我的理财师");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getFinancialDetail();
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

    @OnClick(R.id.btn_finan_phone)
    public void onClick(View view){
        if (view.getId()==R.id.btn_finan_phone){
            String phoneNum = (String) tv_finan_phone.getText();
            if (TextUtils.isEmpty(phoneNum))return;
            Uri uri = Uri.parse("tel:" + phoneNum);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);
        }
    }

    public void financialData(FinancialDetailBean bean){
        if (bean==null)return;

        Picasso.with(mContext)
                .load(bean.getIcon())
                .placeholder(R.mipmap.icon_my_head)
                .error(R.mipmap.icon_my_head)
                .into(img_head);

        tv_head_name.setText(bean.getFname());
        tv_head_userno.setText(bean.getFinancialno());

        tv_sum_money.setText(MoneyUtil.addComma(bean.getInvestmentamout(),2,ROUND_FLOOR));
        tv_service_count.setText(""+bean.getServicecount());

        tv_finan_center.setText(bean.getSysDepartmentVo().getName());
        tv_finan_email.setText(bean.getEmail());
        tv_finan_phone.setText(bean.getMobile());

    }

    public void getFinancialDetail(){
        Api.getClient().getUserFinancialDetail(Api.uid).
                compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<FinancialDetailBean>(mContext) {
                    @Override
                    public void onSuccess(FinancialDetailBean financialDetailBean) {
                        financialData(financialDetailBean);
                    }

                    @Override
                    public void onFail(String fail) {

                    }
                });
    }

}
