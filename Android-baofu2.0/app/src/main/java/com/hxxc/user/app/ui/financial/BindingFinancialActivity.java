package com.hxxc.user.app.ui.financial;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.Event.BindingFinancialEvent;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.ui.discovery.search.SearchActivity;
import com.hxxc.user.app.ui.user.RegisterActivity;
import com.hxxc.user.app.ui.user.RegisterStatusActivity;
import com.hxxc.user.app.ui.vh.BindFinancialVH;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.SharedPreUtils;
import com.hxxc.user.app.widget.trecyclerview.TRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 2016/9/27.
 * 绑定理财师
 */

public class BindingFinancialActivity extends BaseRxActivity {
    public static int RequestCode = 10;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_location_address)
    TextView tv_location_address;
    @BindView(R.id.tv_search_financial)
    TextView tv_search_financial;

//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerview;
//    @BindView(R.id.empty_view)
//    FrameLayout empty_view;


    public static TRecyclerView mXRecyclerView;

    @BindView(R.id.do_step)
    FancyButton do_step;

    private String areaId;
    private String mobile;
    private String code;
    private String pass;
    private String pass_again;
    private String pCode;
    private TextView btn_sure;

    public static BindingFinancialActivity bindingFinancialActivity = null;

    @Override
    public int getLayoutId() {
        return R.layout.financial_binding;
    }

    @Override
    public void initView() {
        mobile = getIntent().getStringExtra("mobile");
        code = getIntent().getStringExtra("code");
        pass = getIntent().getStringExtra("pass");
        pass_again = getIntent().getStringExtra("pass_again");
        pCode = getIntent().getStringExtra("pCode");
        bindingFinancialActivity = this;

        EventBus.getDefault().register(this);
        areaId = SharedPreUtils.getInstanse().getKeyValue(getApplicationContext(), UserInfoConfig.LASTCITY, "上海市");
        tv_location_address.setText(areaId);
        toolbar_title.setText("绑定顾问");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        AppManager.getAppManager().addActivity(this);

        mXRecyclerView = (TRecyclerView) findViewById(R.id.trecycler_listview);
        initRecyclerView();

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initRecyclerView() {
        mXRecyclerView.setParam("showType", UserInfoConfig.ShowType_register);
        mXRecyclerView.setParam("cityName", areaId);
        mXRecyclerView.setParam("searchKey", "");
        mXRecyclerView.setView(BindFinancialVH.class);

        if (mXRecyclerView != null) mXRecyclerView.fetch();

    }

    public static TRecyclerView getmXRecyclerView() {
        return mXRecyclerView;
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

    @OnClick({R.id.tv_location_address, R.id.tv_search_financial})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location_address://选择省市
                if (BtnUtils.isFastDoubleClick()) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra("currCity", tv_location_address.getText().toString());
                    startActivityForResult(intent, RequestCode);
                }
                break;
            case R.id.tv_search_financial://搜索顾问
                if (BtnUtils.isFastDoubleClick()) {
                    Intent intent = new Intent(mContext, SearchFinancialActivity.class);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("code", code);
                    intent.putExtra("pass", pass);
                    intent.putExtra("pass_again", pass_again);
                    intent.putExtra("pCode", pCode);
                    intent.putExtra("areaId", areaId);
                    intent.putExtra("from", getIntent().getIntExtra("from",-1));
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode && resultCode == RESULT_OK) {
            String city = data.getStringExtra("city");
            if (!TextUtils.isEmpty(city)) {
                LogUtil.d("onActivityResult city=" + city);
                tv_location_address.setText(city);
                //TODO 刷新数据
                mXRecyclerView.setParam("cityName", city);
                mXRecyclerView.reFetch();
            }
        }
    }

    private String fid;
    private Dialog dialog;

    public void onEventMainThread(final BindingFinancialEvent event) {
        fid = event.fid + "";
        if (dialog == null) {
            dialog = new Dialog(mContext, R.style.loadingDialogTheme);
            View inflate = View.inflate(mContext, R.layout.dialog_hint, null);
            TextView tv_des = (TextView) inflate.findViewById(R.id.tv_des);
            inflate.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != dialog) {
                        dialog.dismiss();
                    }
                }
            });
            tv_des.setText("确认绑定该顾问作为您的理财师?");
            btn_sure = (TextView) inflate.findViewById(R.id.btn_sure);
            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_sure.setEnabled(false);
                    doRegister();
                }
            });
            dialog.setContentView(inflate);
        }
        dialog.show();
    }

    private void doRegister() {
        Api.getClient().getRegister(mobile, code, pass, pass_again, fid, pCode).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<UserInfo>(this) {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        btn_sure.setEnabled(true);
                        dialog.dismiss();
                        Api.uid = userInfo.getUid() + "";
                        Api.token = userInfo.getToken();
                        SPUtils.geTinstance().setLoginCache(userInfo.getUid() + "", userInfo.getToken(), mobile, pass);
                        // 获取用户信息
                        Midhandler.getUserInfo();
                        // 获取理财师信息
                        Midhandler.getFinanceInfo();
//                        EventBus.getDefault().post(new MainEvent(3));
                        startActivity(new Intent(BindingFinancialActivity.this, RegisterStatusActivity.class));
                        AppManager.getAppManager().finishActivity(RegisterActivity.class);
                        BindingFinancialActivity.this.finish();
                    }

                    @Override
                    public void onFail(String fail) {
                        btn_sure.setEnabled(true);
                        dialog.dismiss();
                    }
                });
    }
}
