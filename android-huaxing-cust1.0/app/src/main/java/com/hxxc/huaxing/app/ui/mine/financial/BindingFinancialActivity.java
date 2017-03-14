package com.hxxc.huaxing.app.ui.mine.financial;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hxxc.huaxing.app.AppManager;
import com.hxxc.huaxing.app.R;
import com.hxxc.huaxing.app.UserInfoConfig;
import com.hxxc.huaxing.app.ui.base.BaseActivity;
import com.hxxc.huaxing.app.ui.mine.financial.vh.BindFinancialVH;
import com.hxxc.huaxing.app.ui.search.SearchActivity;
import com.hxxc.huaxing.app.utils.BtnUtils;
import com.hxxc.huaxing.app.utils.LogUtil;
import com.hxxc.huaxing.app.utils.SharedPreUtils;
import com.hxxc.huaxing.app.wedgit.trecyclerview.TRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 2016/9/27.
 * 绑定理财师
 */

public class BindingFinancialActivity extends BaseActivity {
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

    public static BindingFinancialActivity bindingFinancialActivity = null;

    @Override
    public int getLayoutId() {
        return R.layout.financial_binding;
    }

    @Override
    public void initView() {
        areaId = SharedPreUtils.getInstanse().getKeyValue(getApplicationContext(), UserInfoConfig.LASTCITY, "上海市");
        tv_location_address.setText(areaId);
        toolbar_title.setText("绑定顾问");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        bindingFinancialActivity = this;
        AppManager.getAppManager().addActivity(this);

        mXRecyclerView = (TRecyclerView) findViewById(R.id.trecycler_listview);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mXRecyclerView.setParam("showType",UserInfoConfig.ShowType_register);
        mXRecyclerView.setParam("cityName",areaId);
        mXRecyclerView.setParam("searchKey","");
        mXRecyclerView.setView(BindFinancialVH.class);
        if (mXRecyclerView != null) mXRecyclerView.fetch();

    }

    public static TRecyclerView getmXRecyclerView(){
        return mXRecyclerView;
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
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
                    startActivityForResult(intent, RequestCode);
                }
                break;
            case R.id.tv_search_financial://搜索顾问
                if (BtnUtils.isFastDoubleClick()){
                    Intent intent = new Intent(mContext,SearchFinancialActivity.class);
                    intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
                    intent.putExtra("code",getIntent().getStringExtra("code"));
                    intent.putExtra("pass",getIntent().getStringExtra("pass"));
                    intent.putExtra("pass_again",getIntent().getStringExtra("pass_again"));
                    intent.putExtra("pCode",getIntent().getStringExtra("pCode"));
                    intent.putExtra("areaId",areaId);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCode && resultCode == RESULT_OK){
            String city = data.getStringExtra("city");
            if(!TextUtils.isEmpty(city)){
                LogUtil.d("onActivityResult city="+city);
                tv_location_address.setText(city);
                //TODO 刷新数据
                mXRecyclerView.setParam("cityName",city);
                mXRecyclerView.reFetch();
            }
        }
    }
}
