package com.hxxc.user.app.ui.product;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hxxc.user.app.Constants;
import com.hxxc.user.app.Event.InvestEvent;
import com.hxxc.user.app.Event.ProductDetailEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.bean.ProductInfo;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.contract.ProductDetailContract;
import com.hxxc.user.app.contract.presenter.ProductDetailPresenter;
import com.hxxc.user.app.rest.HttpRequest;
import com.hxxc.user.app.rest.rx.SimpleCallback;
import com.hxxc.user.app.ui.base.ToolbarActivity;
import com.hxxc.user.app.ui.index.AdsActivity;
import com.hxxc.user.app.ui.product.dialog.ProductDetailDialog;
import com.hxxc.user.app.ui.user.LoginActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.widget.verticalpager.DragLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * Created by chenqun on 2016/10/25.
 */

public class ProductDetailActivity extends ToolbarActivity implements ProductDetailContract.View {
    @BindView(R.id.button_buy)
    TextView button_buy;
    @BindView(R.id.draglayout)
    DragLayout draglayout;
    private VerticalFragment1 fragment1;
    private VerticalFragment2 fragment2;
    private ProductInfo mDatas;

    private String mPid;
    private ProductDetailPresenter presenter;
    private ProductDetailDialog dialog;
    private Dialog systemDialog;
    private UserInfo mUser;

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void setTitle() {
        mTitle.setText("产品详情");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        presenter = new ProductDetailPresenter(this);
        mPid = getIntent().getStringExtra("pid");
        mUser = SPUtils.geTinstance().getUserInfo();
        initView();
    }

    @Override
    protected void onDestroy() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        fragment1 = VerticalFragment1.newInstance();
        fragment2 = VerticalFragment2.newInstance();
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

    @OnClick({R.id.button_buy, R.id.button_phone})
    public void onClick(View v) {
        if (BtnUtils.isFastDoubleClick()) {
            if (TextUtils.isEmpty(SPUtils.geTinstance().getUid())) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("startType", LoginActivity.NoRestartMain);
                startActivity(intent);
            } else {
                switch (v.getId()) {
                    case R.id.button_buy:
                        //TODO
                        if (mDatas != null && mDatas.getStatus() == 1 && isQuiz()) {
                            Intent intent = new Intent(this, InvestActivity.class);
                            intent.putExtra("product", mDatas);
                            startActivity(intent);
                        }
                        break;
                    case R.id.button_phone:
                        if (null == dialog) {
                            dialog = new ProductDetailDialog(this);
                        }
                        dialog.show();
                        break;
                }
            }
        }
    }

    private boolean isQuiz() {
        mUser = SPUtils.geTinstance().getUserInfo();
        if (mUser.getIsQuiz() == 0 && !SPUtils.geTinstance().get(Constants.IsRiskTest, false)) {
            showExitDialog();
            return false;
        }
        return true;
    }

    private void showExitDialog() {
        if (null == systemDialog) {
            systemDialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(ProductDetailActivity.this, R.layout.exit_dialog, null);
            TextView content = (TextView) inflate.findViewById(R.id.content);
            content.setText("尚未进行风险测评。");
            TextView tv_quxiao = (TextView) inflate.findViewById(R.id.tv_quxiao);
            TextView tv_queding = (TextView) inflate.findViewById(R.id.tv_queding);
            tv_queding.setText("去测评");
            tv_quxiao.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != systemDialog) {
                        systemDialog.dismiss();
                    }
                    mApiManager.closeRiskTest(null, new SimpleCallback<String>() {
                        @Override
                        public void onNext(String s) {
                            Midhandler.getUserInfo(null);
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            });
            tv_queding.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != systemDialog) {
                        systemDialog.dismiss();
                    }
                    Intent intent = new Intent(ProductDetailActivity.this, AdsActivity.class);
                    intent.putExtra("title", "风险测评");
                    intent.putExtra("url", HttpRequest.indexBaseUrl + HttpRequest.riskTest + "?ifTest=" + mUser.getIsQuiz() + "&uid=" + mUser.getUid() + "&token=" + mUser.getToken() + "&channel=" + Constants.TypeChannel);
                    startActivity(intent);
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
        SPUtils.geTinstance().put(Constants.IsRiskTest, true);
    }

    public void doReflush() {
        presenter.getDatas(mPid);
    }

    @Override
    public void onGetDatas(ProductInfo datas) {
        if (null == datas) {
            fragment1.onRefreshEnd();
            return;
        }
        this.mDatas = datas;
        fragment1.initViews(datas);
        fragment2.initData(datas, mPid);
        fragment1.onRefreshEnd();

        if (datas.getStatus() == 1) {
            button_buy.setBackgroundColor(getResources().getColor(R.color.blue_text));
            button_buy.setText("立即加入");
            button_buy.setEnabled(true);
        } else if (datas.getStatus() == 0) {
            button_buy.setBackgroundColor(getResources().getColor(R.color.blue_text_low));
            button_buy.setText("等待加入");
            button_buy.setEnabled(false);
        } else {
            button_buy.setBackgroundColor(getResources().getColor(R.color.grey_text));
            button_buy.setText("已满额");
            button_buy.setEnabled(false);
        }
    }

    public void onEventMainThread(InvestEvent event) {
        if (event.type == InvestEvent.TYPE_FINISH) {
            finish();
        }
    }

    public void onEventMainThread(ProductDetailEvent event) {
        fragment1.setTimeText(event.time);
    }
}
