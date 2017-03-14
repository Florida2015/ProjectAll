package com.hxxc.huaxing.app.ui.wealth.productdetail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.data.bean.ProductInfo;
import com.hxxc.huaxing.app.data.bean.UserInfoBean;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.dialogfragment.DialogMineOpenEFragment;
import com.hxxc.huaxing.app.ui.dialogfragment.dialoglistener.OnOpenEListener;
import com.hxxc.huaxing.app.ui.mine.account.OpenEAccountActivity;
import com.hxxc.huaxing.app.ui.mine.userstatus.LoginActivity;
import com.hxxc.huaxing.app.ui.wealth.Fragment1;
import com.hxxc.huaxing.app.ui.wealth.buyproduct.InvestActivity;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.wedgit.verticalpager.DragLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenqun on 2016/9/22.
 * 标的详情
 */

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener, ProductdetailV, OnOpenEListener {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.button_buy)
    TextView button_buy;
    @BindView(R.id.button_phone)
    TextView button_phone;

    @BindView(R.id.draglayout)
    DragLayout draglayout;
    private String mPid;
    private VerticalFragment1 fragment1;
    private VerticalFragment2 fragment2;
    private ProductDetailPresenter mPresenter;
    private ProductInfo mDatas;
    private int mType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    public void initView() {
        mPid = getIntent().getStringExtra("pid");
        mType = getIntent().getIntExtra("type", Fragment1.Type_Wealth);

        back.setVisibility(View.VISIBLE);
        toolbar_title.setText("标的详情");
        fragment1 = VerticalFragment1.newInstance(mType);
        fragment2 = VerticalFragment2.newInstance(mType);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.first, fragment1).add(R.id.second, fragment2)
                .commit();
        DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {

            }
        };
        draglayout.setNextPageListener(nextIntf);
    }

    @Override
    public void initPresenter() {
        mPresenter = new ProductDetailPresenter(this);
    }

    @OnClick({R.id.back, R.id.button_buy, R.id.button_phone})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.button_buy:
//                if (!TextUtils.isEmpty(SharedPreUtils.getInstanse().getToken()) || !TextUtils.isEmpty(SharedPreUtils.getInstanse().getUid())) {
//                    if (mDatas == null) return;
//                    Intent in = new Intent(this, InvestActivity.class);
//                    in.putExtra("pid", mPid);
//                    in.putExtra("product", mDatas);
//                    in.putExtra("type", mType);
//                    startActivity(in);
//                } else {
//                    startActivity(new Intent(mContext, LoginActivity.class));
//                }
                toConfirm();
                break;
            case R.id.button_phone:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4000-466-600"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;
        }
    }

    private void toConfirm() {
        // 判断E账户是否开通
        UserInfoBean bean = SharedPreUtils.getInstanse().getUserInfo(this);
        if (null == bean) {//未登录
            startActivity(new Intent(this, LoginActivity.class));
        } else {//已登录
            if (bean.getIsOpenEaccount() == 1) {//TODO 1表示已开通E账号
                if (mDatas == null) return;
                Intent intent = new Intent(this, InvestActivity.class);
                intent.putExtra("pid", mPid);
                intent.putExtra("product", mDatas);
                intent.putExtra("type", mType);
                startActivity(intent);
            } else {//未开通E账号
                DialogMineOpenEFragment dialogMineOpenEFragment = new DialogMineOpenEFragment().newInstance();
                dialogMineOpenEFragment.show(getSupportFragmentManager(), "updateAppDialogFragment");
            }
        }
    }

    public void doReflush() {
        mPresenter.getDatas(mPid);
    }

    @Override
    public void onSuccess(ProductInfo datas, boolean isLoadingmore) {
        if (null == datas) {
            fragment1.onRefreshEnd();
            return;
        }
        this.mDatas = datas;
        fragment1.initViews(datas);
        fragment2.initData(datas, mPid);

        if (mDatas.getStatus() == 1) {
            if (mDatas.getSurplus() == null || mDatas.getSurplus().floatValue() <= 0) {
                button_buy.setBackgroundColor(getResources().getColor(R.color.black_aaaa));
                button_buy.setText("已满额");
                button_buy.setEnabled(false);
            } else {
                button_buy.setBackgroundColor(getResources().getColor(R.color.blue_text));
                button_buy.setText("立即加入");
                button_buy.setEnabled(true);
            }

        } else {
            button_buy.setBackgroundColor(getResources().getColor(R.color.black_aaaa));
            button_buy.setText(mDatas.getStatusText());
            button_buy.setEnabled(false);
        }
    }

    @Override
    public void onError() {
        fragment1.onRefreshEnd();
    }

    //开通E账号
    @Override
    public void onOpenEAccount(int resId) {
        startActivity(new Intent(this, OpenEAccountActivity.class));
    }
}
