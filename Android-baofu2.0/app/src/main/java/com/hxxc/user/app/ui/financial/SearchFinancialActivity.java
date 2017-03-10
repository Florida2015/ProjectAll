package com.hxxc.user.app.ui.financial;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hxxc.user.app.AppManager;
import com.hxxc.user.app.Event.MainEvent;
import com.hxxc.user.app.HXXCApplication;
import com.hxxc.user.app.Midhandler;
import com.hxxc.user.app.R;
import com.hxxc.user.app.UserInfoConfig;
import com.hxxc.user.app.adapter.CommonAdapter;
import com.hxxc.user.app.bean.UserInfo;
import com.hxxc.user.app.data.bean.AdviserBean;
import com.hxxc.user.app.rest.Api;
import com.hxxc.user.app.rest.BaseSubscriber;
import com.hxxc.user.app.rest.RxApiThread;
import com.hxxc.user.app.ui.MainActivity2;
import com.hxxc.user.app.ui.base.BaseRxActivity;
import com.hxxc.user.app.utils.BtnUtils;
import com.hxxc.user.app.utils.CommonUtil;
import com.hxxc.user.app.utils.LogUtil;
import com.hxxc.user.app.utils.SPUtils;
import com.hxxc.user.app.utils.SharedPreUtils;
import com.hxxc.user.app.widget.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by houwen.lai on 2016/9/27.
 * 搜索顾问页
 */

public class SearchFinancialActivity extends BaseRxActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ed_search_financial)
    EditText ed_search_financial;
    @BindView(R.id.btn_search_financial)
    Button btn_search_financial;

    @BindView(R.id.listview_search_financial)
    ListView listview;

    private boolean FlagSearch = true;//搜索

    private String areaId;
    private Dialog dialog;
    private String fid;
    private String mobile;
    private String code;
    private String pass;
    private String pass_again;
    private String pCode;
    private TextView btn_sure;

    public static SearchFinancialActivity searchFinancialActivity = null;

    @Override
    public int getLayoutId() {
        return R.layout.financial_search_financial;
    }

    @Override
    public void initView() {
        searchFinancialActivity = this;
        mobile = getIntent().getStringExtra("mobile");
        code = getIntent().getStringExtra("code");
        pass = getIntent().getStringExtra("pass");
        pass_again = getIntent().getStringExtra("pass_again");
        pCode = getIntent().getStringExtra("pCode");
        toolbar_title.setText("搜索顾问");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        AppManager.getAppManager().addActivity(this);

        areaId = getIntent().getStringExtra("areaId");

        ed_search_financial.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    FlagSearch = false;
                    btn_search_financial.setText("搜索");
                    btn_delete_search.setText("");
                } else {
                    btn_search_financial.setText("取消");
                    btn_delete_search.setText("清除历史记录");
                    FlagSearch = true;
                    searchHistory();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listview.setOnItemClickListener(this);

        view_delete = View.inflate(mContext, R.layout.financial_search_delete_his, null);
        btn_delete_search = (Button) view_delete.findViewById(R.id.btn_delete_search);
        view_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清除历史记录
                historyList.clear();
                SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.TypeSearchHistory, HXXCApplication.getInstance().getGson().toJson(historyList));
                searchHisttoryAdapter.notifyDataSetChanged();
            }
        });

        listview.addFooterView(view_delete);

        String history = SharedPreUtils.getInstanse().getKeyValue(mContext, UserInfoConfig.TypeSearchHistory);

        if (historyList == null) historyList = new ArrayList<String>();
        historyList.clear();
        if (!TextUtils.isEmpty(history))
            historyList = HXXCApplication.getInstance().getGson().fromJson(history, new TypeToken<List<String>>() {
            }.getType());

        searchHistory();

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

    @OnClick(R.id.btn_search_financial)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search_financial://搜索
                if (BtnUtils.isFastDoubleClick()) {
                    if (btn_search_financial.getText().toString().contains("搜索")) {
                        FlagSearch = false;
                        searchFinancial(ed_search_financial.getText().toString().trim());
                    } else ed_search_financial.setText("");
                }

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //搜索
        if (FlagSearch) {
            FlagSearch = false;
            ed_search_financial.setText(historyList.get(i));
            searchFinancial(historyList.get(i));

        } else {//理财师信息

            //是否选择该理财师做顾问

            LogUtil.d("顾问 mobile=" + mobile + "_code=" + code + "_pass=" + pass + "_pass_again=" + pass_again + "_pCode=" + pCode);

            Intent intent = new Intent(mContext, DialogBindingFinancial.class);
            intent.putExtra("mobile", mobile);
            intent.putExtra("code", code);
            intent.putExtra("pass", pass);
            intent.putExtra("pass_again", pass_again);
            intent.putExtra("pCode", pCode);
            intent.putExtra("fid", "" + searchFinancialAdapter.getAdapterListData().get(i).getFid());
            intent.putExtra("fname", "" + searchFinancialAdapter.getAdapterListData().get(i).getFname());
            intent.putExtra("from", getIntent().getIntExtra("from",-1));
            mContext.startActivity(intent);
//            fid = "" + searchFinancialAdapter.getAdapterListData().get(i).getFid();
//            showMyDialog();
        }
    }

    private void showMyDialog() {
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
                        EventBus.getDefault().post(new MainEvent(3));
                        startActivity(new Intent(SearchFinancialActivity.this, MainActivity2.class));
                    }

                    @Override
                    public void onFail(String fail) {
                        btn_sure.setEnabled(true);
                        dialog.dismiss();
                    }
                });
    }

    public void searchFinancial(String text) {
        //请求数据
//        searchHisttoryAdapter.notifyDataSetChanged();
        if (!TextUtils.isEmpty(text)) historyList.add(0, text);

        SharedPreUtils.getInstanse().putKeyValue(mContext, UserInfoConfig.TypeSearchHistory, HXXCApplication.getInstance().getGson().toJson(historyList));

        Api.getClient().getFpList(UserInfoConfig.ShowType_register, "", text, 1, 10).compose(RxApiThread.convert()).
                subscribe(new BaseSubscriber<List<AdviserBean>>(mContext) {
                    @Override
                    public void onSuccess(List<AdviserBean> adviserBeen) {
                        setSearchFinancialAdapter(adviserBeen);
                    }

                    @Override
                    public void onFail(String fail) {
                        btn_delete_search.setText("没有相关顾问");
                    }
                });
    }

    private String[] history = new String[]{"王小闲1", "王小闲2", "王小闲3"};
    SearchFinancialHisttoryAdapter searchHisttoryAdapter;
    private List<String> historyList;
    private View view_delete;
    private Button btn_delete_search;

    /**
     * 历史搜索记录
     */
    public void searchHistory() {


        LogUtil.d("搜索顾问" + HXXCApplication.getInstance().getGson().toJson(historyList));
        if (null == searchHisttoryAdapter) {
            searchHisttoryAdapter = new SearchFinancialHisttoryAdapter(mContext, historyList, R.layout.financial_search_item);
        }
        listview.setAdapter(searchHisttoryAdapter);
    }

    /**
     * 搜索历史
     */
    public class SearchFinancialHisttoryAdapter extends CommonAdapter<String> {

        public SearchFinancialHisttoryAdapter(Context context, List<String> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(ViewHolder helper, String item, int position) {
            super.convert(helper, item, position);
            if (position % 2 == 1) {
                helper.getConvertView().setBackgroundColor(Color.parseColor("#FaFaFa"));
            } else helper.getConvertView().setBackgroundColor(Color.WHITE);

            helper.setText(R.id.tv_search_financial_name, item);

        }
    }

    SearchFinancialAdapter searchFinancialAdapter;

    public void setSearchFinancialAdapter(List<AdviserBean> list) {
        if (list != null && list.size() > 0)
            btn_delete_search.setText("");
        else btn_delete_search.setText("没有相关顾问");
        searchFinancialAdapter = new SearchFinancialAdapter(mContext, list, R.layout.adapter_finance_item);
        listview.setAdapter(searchFinancialAdapter);
    }

    /**
     * 搜索理财师
     */
    public class SearchFinancialAdapter extends CommonAdapter<AdviserBean> {


        public SearchFinancialAdapter(Context context, List<AdviserBean> list, int layoutId) {
            super(context, list, layoutId);

        }

        @Override
        public void convert(ViewHolder helper, AdviserBean item, int position) {
            super.convert(helper, item, position);

            LinearLayout linear_binding_financial = helper.getView(R.id.linear_binding_financial);

            TextView tv_recommend = helper.getView(R.id.tv_recommend);
            View line_recommend = helper.getView(R.id.line_recommend);

            CircleImageView img_icon = helper.getView(R.id.img_icon);

            TextView name_text = helper.getView(R.id.name_text);
            TextView service_count_text = helper.getView(R.id.service_count_text);
            TextView finance_no_text = helper.getView(R.id.finance_no_text);
            TextView amout_text = helper.getView(R.id.amout_text);

            ImageView is_check_img = helper.getView(R.id.is_check_img);

            if (position == 0) {
                tv_recommend.setVisibility(View.VISIBLE);
                line_recommend.setVisibility(View.VISIBLE);
            } else {
                tv_recommend.setVisibility(View.GONE);
                line_recommend.setVisibility(View.GONE);
            }
            is_check_img.setVisibility(View.GONE);

            if (position % 2 == 0)
                linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            else
                linear_binding_financial.setBackgroundColor(mContext.getResources().getColor(R.color.white_fafa));

            Picasso.with(mContext)
                    .load(item.getIcon())
                    .placeholder(R.drawable.default_financier_pic)
                    .error(R.drawable.default_financier_pic)
                    .into(img_icon);

            name_text.setText(item.getFname());
            finance_no_text.setText(item.getFinancialno());
            service_count_text.setText(item.getServicecount() + "位");
            amout_text.setText(CommonUtil.moneyType(item.getInvestmentamout().doubleValue() / 10000f) + "万");
        }
    }
}
